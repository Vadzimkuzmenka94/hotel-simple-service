package com.example.hotelsimpleservice.controller.reporting_controller;

import com.example.hotelsimpleservice.reporting.implementation.BookingReportService;
import com.example.hotelsimpleservice.reporting.implementation.CustomerReportService;
import com.example.hotelsimpleservice.reporting.implementation.RoomReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final CustomerReportService customerReportService;
    private final BookingReportService bookingReportService;
    private final RoomReportService roomReportService;

    @Autowired
    public ReportController(CustomerReportService customerReportService, BookingReportService bookingReportService, RoomReportService roomReportService) {
        this.customerReportService = customerReportService;
        this.bookingReportService = bookingReportService;
        this.roomReportService = roomReportService;
    }

    @GetMapping("/show-reporting-options")
    public String reportingPage() {
        return "admin-page/reporting/show-reporting-options";
    }

    @GetMapping("/booking_exel")
    public void generateBookingExelReport(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=booking.xls";
        response.setHeader(headerKey, headerValue);
        bookingReportService.generateExcel(response);
    }

    @GetMapping("/customer_exel")
    public void generateCustomerExelReport(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=customers.xls";
        response.setHeader(headerKey, headerValue);
        customerReportService.generateExcel(response);
    }

    @GetMapping("/room_exel")
    public void generateRoomExelReport(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=rooms.xls";
        response.setHeader(headerKey, headerValue);
        roomReportService.generateExcel(response);
    }
}