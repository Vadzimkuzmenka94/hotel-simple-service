package com.example.hotelsimpleservice.reporting.implementation;

import com.example.hotelsimpleservice.model.Customer;
import com.example.hotelsimpleservice.reporting.ReportService;
import com.example.hotelsimpleservice.repository.CustomerRepository;
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
public class CustomerReportService implements ReportService {
    private final String SHEET_NAME = "Customer info";
    private final String CUSTOMER_ID = "customer_id";
    private final String LOGIN = "login";
    private final String PASSWORD = "password";
    private final String ROLE = "role";
    private final String NAME = "name";
    private final String SURNAME = "surname";
    private final String EMAIL = "email";
    private final String CARD_NUMBER = "card_number";

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerReportService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void generateExcel(HttpServletResponse response) throws IOException {
        try (ServletOutputStream outputStream = response.getOutputStream();
             HSSFWorkbook workbook = new HSSFWorkbook()) {
            List<Customer> customers = customerRepository.findAll();
            HSSFSheet sheet = workbook.createSheet(SHEET_NAME);
            Row row = createRow(sheet, 0, CUSTOMER_ID, LOGIN, PASSWORD, ROLE, NAME, SURNAME, EMAIL, CARD_NUMBER);
            HSSFFont font = workbook.createFont();
            font.setBold(true);
            HSSFCellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            for (int i = 0; i < 8; i++) {
                row.getCell(i).setCellStyle(style);

                int dataRowIndex = 1;
                for (Customer customer : customers) {
                    HSSFRow dataRow = sheet.createRow(dataRowIndex);
                    dataRow.createCell(0).setCellValue(customer.getCustomer_id());
                    dataRow.createCell(1).setCellValue(customer.getLogin());
                    dataRow.createCell(2).setCellValue(customer.getPassword());
                    dataRow.createCell(3).setCellValue(customer.getRole());
                    dataRow.createCell(4).setCellValue(customer.getName());
                    dataRow.createCell(5).setCellValue(customer.getSurname());
                    dataRow.createCell(6).setCellValue(customer.getEmail());
                    dataRow.createCell(7).setCellValue(customer.getCardNumber());
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