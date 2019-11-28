package main.java.service.impl;

import main.java.dao.ReportDao;
import main.java.exception.NotCreateReportRuntimeException;
import main.java.service.Report;
import main.java.service.ReportService;

public class ReportServiceImpl implements ReportService {
    private final ReportDao reportDao;

    public ReportServiceImpl(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    @Override
    public Report getDataReport() {
        return reportDao.getDataReport()
                .orElseThrow(() -> new NotCreateReportRuntimeException("X Report isn't created"));
    }

    @Override
    public Report getDataZReport() {
        return reportDao.getDataZReport()
                .orElseThrow(() -> new NotCreateReportRuntimeException("Z Report isn't created"));
    }
}
