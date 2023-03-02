package com.example.hotelsimpleservice.service.implementation;

import com.example.hotelsimpleservice.exceptions.AppException;
import com.example.hotelsimpleservice.exceptions.ErrorCode;
import com.example.hotelsimpleservice.model.Booking;
import com.example.hotelsimpleservice.repository.BookingRepository;
import com.example.hotelsimpleservice.service.BookingService;
import com.example.hotelsimpleservice.service.CustomerService;
import com.example.hotelsimpleservice.service.RoomService;
import com.example.hotelsimpleservice.validator.BookingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class BookingServiceImplementation implements BookingService {
    private final String BYN_CURRENCY = "BYN";
    private final String LARI_CURRENCY = "LARI";
    private final Double BYN_TO_DOLLAR_EXCHANGE_RATE = 2.63;
    private final Double LARI_TO_DOLLAR_EXCHANGE_RATE = 2.51;
    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final BookingValidator bookingValidator;
    private final EntityManager entityManager;
    private final CustomerService customerService;

    @Autowired
    public BookingServiceImplementation(BookingRepository bookingRepository, RoomService roomService,
                                        BookingValidator bookingValidator, EntityManager entityManager, CustomerService customerService) {
        this.bookingRepository = bookingRepository;
        this.roomService = roomService;
        this.bookingValidator = bookingValidator;
        this.entityManager = entityManager;
        this.customerService = customerService;
    }

    @Override
    public Booking createBooking(Booking booking) {
        bookingValidator.validate(booking);
        roomService.takeRoom(booking.getRoomNumber());
        calculateFinalPrice(booking);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        booking.setCustomer(customerService.findByLogin(authentication.getName()).get());
        booking.setName(authentication.getName());
        return bookingRepository.save(booking);
    }

    /**
     * Method for find booking by id.
     *
     * @param id
     * @return booking by id, but if user don't administrator, then return first current user's booking .
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole ('ROLE_USER')")
    @Override
    public Optional<Booking> findById(Long id) {
        if (!isAdmin()) {
            return Optional.ofNullable(findByName(getNameFromAuthentication()).get(0));
        }
        return Optional.of(bookingRepository.findById(id).
                orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public List<Booking> findAll() {
        return (List<Booking>) bookingRepository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Override
    public List<Booking> findByName(String name) {
        if (isAdmin()) {
            name = getNameFromAuthentication();
        }
        return bookingRepository.findByName(name);
    }

    @Transactional
    @Override
    public Booking updateBooking(Booking booking, Long id) {
        checkingExistenceRoom(bookingRepository.findById(id).get());
        Booking bookingInDb = bookingRepository.findById(id).get();
        detachEntity(bookingInDb);
        bookingInDb.setId(booking.getId() != null ? booking.getId() : bookingInDb.getId());
        bookingInDb.setName(booking.getName() != null ? booking.getName() : bookingInDb.getName());
        bookingInDb.setCost(booking.getCost() != null ? booking.getCost() : bookingInDb.getCost());
        bookingInDb.setDuration(booking.getDuration() != 0 ? booking.getDuration() : bookingInDb.getDuration());
        bookingInDb.setStartBooking(booking.getStartBooking() != null ? booking.getStartBooking() : bookingInDb.getStartBooking());
        bookingInDb.setFinishBooking(booking.getFinishBooking() != null ? booking.getFinishBooking() : bookingInDb.getFinishBooking());
        entityManager.merge(bookingInDb);
        return bookingInDb;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @Override
    public void deleteBooking(Long id) {
        if (bookingRepository.findById(id).isEmpty()) {
            throw new AppException(ErrorCode.BOOKING_NOT_FOUND);
        }
        entityManager.remove(bookingRepository.findById(id).get());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public List<Booking> findBookingByDifferentParameters(String name, Integer duration, Double cost, String currency, Integer room_number) {
        return bookingRepository.findBookingByDifferentParameters(name, duration,cost,currency,room_number);
    }

    @Override
    public Double calculateBookingCost(int numberOfDays, double cost) {
        return numberOfDays * cost;
    }

    @Override
    public Double recalculationToCorrectCurrency(String currency, Double dollarPrice) {
        Double price = dollarPrice;
        if (currency.equals(BYN_CURRENCY)) {
            price = dollarPrice * BYN_TO_DOLLAR_EXCHANGE_RATE;
        } else if (currency.equals(LARI_CURRENCY)) {
            price = dollarPrice * LARI_TO_DOLLAR_EXCHANGE_RATE;
        }
        return price;
    }

    @Override
    public void calculateFinalPrice(Booking booking) {
        Double finalPrice = recalculationToCorrectCurrency(booking.getCurrency(), calculateBookingCost(booking.getDuration(),
                roomService.findByRoomNumber(booking.getRoomNumber()).getCost()));
        booking.setCost(finalPrice);
    }

    public void detachEntity(Booking booking) {
        checkingExistenceRoom(booking);
        entityManager.detach(booking);
    }

    public void checkingExistenceRoom(Booking booking) {
        if (booking == null) {
            throw new AppException(ErrorCode.BOOKING_NOT_FOUND);
        }
    }

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
    }

    private String getNameFromAuthentication () {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}