package com.example.hotelsimpleservice.reporting.Implementation;

import com.example.hotelsimpleservice.model.Customer;
import com.example.hotelsimpleservice.reporting.ReportService;
import com.example.hotelsimpleservice.repository.CustomerRepository;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class CustomerReportService implements ReportService {
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
            HSSFSheet sheet = workbook.createSheet("Customer info");
            HSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("customer_id");
            row.createCell(1).setCellValue("login");
            row.createCell(2).setCellValue("password");
            row.createCell(3).setCellValue("role");
            row.createCell(4).setCellValue("name");
            row.createCell(5).setCellValue("surname");
            row.createCell(6).setCellValue("email");
            row.createCell(7).setCellValue("card_number");
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
}