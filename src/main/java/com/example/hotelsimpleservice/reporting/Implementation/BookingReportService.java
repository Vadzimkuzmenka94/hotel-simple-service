package com.example.hotelsimpleservice.reporting.Implementation;

import com.example.hotelsimpleservice.model.Booking;
import com.example.hotelsimpleservice.reporting.ReportService;
import com.example.hotelsimpleservice.repository.BookingRepository;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class BookingReportService implements ReportService {
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingReportService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public void generateExcel(HttpServletResponse response) throws IOException {
        try (ServletOutputStream outputStream = response.getOutputStream();

             HSSFWorkbook workbook = new HSSFWorkbook()) {
            List<Booking> bookings = (List<Booking>) bookingRepository.findAll();
            HSSFSheet sheet = workbook.createSheet("Bookings info");
            HSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("id");
            row.createCell(1).setCellValue("name");
            row.createCell(2).setCellValue("duration");
            row.createCell(3).setCellValue("cost");
            row.createCell(4).setCellValue("currency");
            row.createCell(5).setCellValue("room_number");
            row.createCell(6).setCellValue("customer_id");
            row.createCell(7).setCellValue("date");
            row.createCell(8).setCellValue("start_booking");
            row.createCell(9).setCellValue("finish_booking");
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
}
