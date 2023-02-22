package com.example.hotelsimpleservice.reporting.implementation;

import com.example.hotelsimpleservice.model.Booking;
import com.example.hotelsimpleservice.reporting.ReportService;
import com.example.hotelsimpleservice.repository.BookingRepository;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class BookingReportService implements ReportService {
    private final String ID = "id";
    private final String NAME = "name";
    private final String DURATION = "duration";
    private final String COST = "cost";
    private final String CURRENCY = "currency";
    private final String ROOM_NUMBER = "room_number";
    private final String CUSTOMER_ID = "customer_id";
    private final String DATE = "date";
    private final String START_BOOKING = "start_booking";
    private final String FINISH_BOOKING = "finish_booking";
    private final String SHEET_NAME = "Bookings info";
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingReportService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public void generateExcel(HttpServletResponse response) throws IOException { // вынести в абстрактный класс
        try (ServletOutputStream outputStream = response.getOutputStream();

             HSSFWorkbook workbook = new HSSFWorkbook()) {
            List<Booking> bookings = (List<Booking>) bookingRepository.findAll();
            HSSFSheet sheet = workbook.createSheet(SHEET_NAME);
            Row row = createRow(sheet, 0, ID, NAME, DURATION, COST, CURRENCY, ROOM_NUMBER, CUSTOMER_ID, DATE, START_BOOKING, FINISH_BOOKING);
            HSSFFont font = workbook.createFont();
            font.setBold(true);
            HSSFCellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            for (int i = 0; i < 10; i++) {
                row.getCell(i).setCellStyle(style);
            }
            int dataRowIndex = 1;
            for (Booking booking : bookings) {
                HSSFRow dataRow = sheet.createRow(dataRowIndex);
                dataRow.createCell(0).setCellValue(booking.getId());
                dataRow.createCell(1).setCellValue(booking.getName());
                dataRow.createCell(2).setCellValue(booking.getDuration());
                dataRow.createCell(3).setCellValue(booking.getCost());
                dataRow.createCell(4).setCellValue(booking.getCurrency());
                dataRow.createCell(5).setCellValue(booking.getRoomNumber());
                dataRow.createCell(6).setCellValue(1);
                dataRow.createCell(7).setCellValue(booking.getDate());
                dataRow.createCell(8).setCellValue(booking.getStartBooking());
                dataRow.createCell(9).setCellValue(booking.getFinishBooking());
                dataRowIndex++;
            }
            workbook.write(outputStream);
        }
    }

    @Override
    public Row createRow(HSSFSheet sheet, int rowNum, Object... data) {
        HSSFRow row = sheet.createRow(rowNum);
        int cellNum = 0;
        for (Object cellData : data) {
            row.createCell(cellNum++).setCellValue(String.valueOf(cellData));
        }
        return row;
    }
}