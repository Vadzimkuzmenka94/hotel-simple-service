package com.example.hotelsimpleservice.reporting.Implementation;

import com.example.hotelsimpleservice.model.Room;
import com.example.hotelsimpleservice.reporting.ReportService;
import com.example.hotelsimpleservice.repository.RoomRepository;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class RoomReportService implements ReportService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomReportService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void generateExcel(HttpServletResponse response) throws IOException {
        try (ServletOutputStream outputStream = response.getOutputStream();
             HSSFWorkbook workbook = new HSSFWorkbook()) {
            Iterable<Room> rooms = roomRepository.findAll();
            HSSFSheet sheet = workbook.createSheet("Room info");
            HSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("id");
            row.createCell(1).setCellValue("wifi");
            row.createCell(2).setCellValue("free_parking");
            row.createCell(3).setCellValue("conditioner");
            row.createCell(4).setCellValue("fridge");
            row.createCell(5).setCellValue("no_smoking_room");
            row.createCell(6).setCellValue("breakfast");
            row.createCell(7).setCellValue("comment");
            row.createCell(8).setCellValue("number_of_beds");
            row.createCell(9).setCellValue("free");
            row.createCell(10).setCellValue("room_number");
            row.createCell(11).setCellValue("cost");
            HSSFFont font = workbook.createFont();
            font.setBold(true);
            HSSFCellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            for (int i = 0; i < 12; i++) {
                row.getCell(i).setCellStyle(style);

                int dataRowIndex = 1;
                for (Room room : rooms) {
                    HSSFRow dataRow = sheet.createRow(dataRowIndex);
                    dataRow.createCell(0).setCellValue(room.getId());
                    dataRow.createCell(1).setCellValue(room.getWifi());
                    dataRow.createCell(2).setCellValue(room.getFreeParking());
                    dataRow.createCell(3).setCellValue(room.getConditioner());
                    dataRow.createCell(4).setCellValue(room.getFridge());
                    dataRow.createCell(5).setCellValue(room.getNoSmokingRoom());
                    dataRow.createCell(6).setCellValue(room.getBreakfast());
                    dataRow.createCell(7).setCellValue(room.getComment());
                    dataRow.createCell(8).setCellValue(room.getNumberOfBeds());
                    dataRow.createCell(9).setCellValue(room.getFree());
                    dataRow.createCell(10).setCellValue(room.getRoomNumber());
                    dataRow.createCell(11).setCellValue(room.getCost());
                    dataRowIndex++;
                }

                workbook.write(outputStream);
            }
        }
    }
}
