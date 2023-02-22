package com.example.hotelsimpleservice.reporting;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Row;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ReportService {
    void generateExcel(HttpServletResponse response) throws IOException;

    Row createRow(HSSFSheet sheet, int rowNum, Object... data);
}