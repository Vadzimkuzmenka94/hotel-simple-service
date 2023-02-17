package com.example.hotelsimpleservice.reporting;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ReportService {
    public void generateExcel(HttpServletResponse response)throws IOException;
}
