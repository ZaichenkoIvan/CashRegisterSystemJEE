package service.impl;

import dao.ReportDao;
import domain.Report;
import exception.NotCreateReportRuntimeException;
import service.ReportService;

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
