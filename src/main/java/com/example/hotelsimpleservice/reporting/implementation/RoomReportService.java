package com.example.hotelsimpleservice.reporting.implementation;

import com.example.hotelsimpleservice.model.Room;
import com.example.hotelsimpleservice.reporting.ReportService;
import com.example.hotelsimpleservice.repository.RoomRepository;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class RoomReportService implements ReportService {
    private final String ID = "id";
    private final String WIFI = "wifi";
    private final String FREE_PARKING = "free_parking";
    private final String CONDITIONER = "conditioner";
    private final String FRIDGE = "fridge";
    private final String NO_SMOKING_ROOM = "no_smoking_room";
    private final String BREAKFAST = "breakfast";
    private final String COMMENT = "comment";
    private final String NUMBER_OF_BEDS = "number_of_beds";
    private final String FREE = "free";
    private final String ROOM_NUMBER = "room_number";
    private final String COST = "cost";
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
            Row row = createRow(sheet, 0, ID, WIFI, FREE_PARKING, CONDITIONER, FRIDGE, NO_SMOKING_ROOM,
                    BREAKFAST, COMMENT, NUMBER_OF_BEDS, FREE, ROOM_NUMBER, COST);
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