package main.java.service.impl;

import main.java.dao.CheckDao;
import main.java.dao.FiscalDao;
import main.java.dao.PoolConnection;
import main.java.dao.ReportDao;
import main.java.entity.Fiscal;
import main.java.exception.NotCreateReportRuntimeException;
import main.java.service.Report;
import main.java.service.Report.Detail;
import main.java.service.ReportService;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
