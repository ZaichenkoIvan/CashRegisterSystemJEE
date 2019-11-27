package main.java.dao.daoimpl;

import main.java.dao.CheckDao;
import main.java.dao.FiscalDao;
import main.java.dao.PoolConnection;
import main.java.dao.ReportDao;
import main.java.entity.Fiscal;
import main.java.service.Report;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class ReportDaoImpl implements ReportDao {
    private static final Logger LOGGER = Logger.getLogger(ReportDaoImpl.class);

    private static final String QueryXReport = "SELECT current_timestamp() AS printtime," +
            "	cancel.countcanceled," +
            "	SUM(COUNT(DISTINCT c.id)) OVER() AS countcheck," +
            "	s.nds," +
            "	SUM(s.total) AS total," +
            "	round(SUM(s.ndstotal), 2) AS ndstotal, " +
            "	round(SUM(SUM(s.total)) OVER(), 2) AS sumtotal, " +
            "	round(SUM(SUM(s.ndstotal)) OVER(), 2) AS sumndstotal " +
            "	FROM checkspec s" +
            "	INNER JOIN cashreg.check c ON c.id = s.id_check" +
            "	LEFT JOIN (SELECT COUNT(c1.canceled) AS countcanceled FROM cashreg.check c1 " +
            "			 		WHERE c1.canceled = 1 /*AND cast(c1.crtime as date) = current_date()*/) cancel ON true" +
            "	WHERE c.canceled = 0 AND s.canceled = 0 /*AND cast(c.crtime as date) = current_date()*/" +    //закоментировано для debug-а
            "	GROUP BY s.nds, cancel.countcanceled";

    private static String QueryZReport = "SELECT current_timestamp() AS printtime," +
            "	(SELECT COUNT(c1.canceled) FROM cashreg.check c1 " +
            "		WHERE c1.canceled = 1 /*AND cast(c1.crtime as date) = current_date()*/) AS countcanceled, " +
            "	SUM(COUNT(DISTINCT c.id)) OVER() AS countcheck," +
            "	SUM(CASE WHEN s.nds = 20 THEN s.total ELSE 0 END) AS totalA," +
            "	round(SUM(CASE WHEN s.nds = 20 THEN s.ndstotal ELSE 0 END), 2) AS ndstotalA," +
            "	SUM(CASE WHEN s.nds = 7 THEN s.total ELSE 0 END) AS totalB," +
            "	round(SUM(CASE WHEN s.nds = 7 THEN s.ndstotal ELSE 0 END), 2) AS ndstotalB," +
            "	SUM(CASE WHEN s.nds = 0 THEN s.total ELSE 0 END) AS totalC," +
            "	round(SUM(CASE WHEN s.nds = 0 THEN s.ndstotal ELSE 0 END), 2) AS ndstotalC," +
            "	SUM(SUM(s.total)) OVER() AS sumtotal, " +
            "	round(SUM(SUM(s.ndstotal)) OVER(), 2) AS sumndstotal " +
            "	FROM checkspec s" +
            "	INNER JOIN cashreg.check c ON c.id = s.id_check" +
            "	WHERE c.canceled = 0 AND s.canceled = 0 /*AND cast(c.crtime as date) = current_date()*/" +    //закоментировано для debug-а
            "		AND c.registration IS NULL";

    private PoolConnection poolConnection;
    private CheckDao checkDao;
    private FiscalDao fiscalDao;

    public ReportDaoImpl(PoolConnection poolConnection, CheckDao checkDao, FiscalDao fiscalDao) {
        this.poolConnection = poolConnection;
        this.checkDao = checkDao;
        this.fiscalDao = fiscalDao;
    }

    @Override
    public Optional<Report> getDataReport() {
        Report rep = new Report();
        try (Connection connection = poolConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(QueryXReport);

            List<Report.Detail> detail = rep.getDetail();
            while (rs.next()) {
                rep.setPrinttime(rs.getTimestamp("printtime"));
                rep.setCountCheck(rs.getInt("countcheck"));
                rep.setCountCancelCheck(rs.getInt("countcanceled"));
                rep.setSumTotal(rs.getDouble("sumtotal"));
                rep.setSumNdsTotal(rs.getDouble("sumndstotal"));
                detail.add(rep.new Detail(rs.getInt("nds"),
                        rs.getDouble("ndstotal"),
                        rs.getDouble("total")));
            }
        } catch (SQLException e) {
            LOGGER.error("Не удалось сформировать X-отчет", e);
        }
        return Optional.of(rep);
    }

    @Override
    public Optional<Report> getDataZReport() {

        Report zReport = null;
        try (Connection connection = poolConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(QueryZReport);
            while (rs.next()) {
                Fiscal fiscal = new Fiscal();
                fiscal.setTotal(rs.getDouble("sumtotal"));
                Long[] repNumber = new Long[1];
                checkDao.update(1);
                repNumber[0] = fiscalDao.insert(fiscal);
                zReport = new Report().new Builder()
                        .addNumber(repNumber[0])
                        .addPrinttime(rs.getTimestamp("printtime"))
                        .addCountCheck(rs.getInt("countcheck"))
                        .addCountCancelCheck(rs.getInt("countcanceled"))
                        .addSumTotal(rs.getDouble("sumtotal"))
                        .addSumNdsTotal(rs.getDouble("sumndstotal"))
                        .addTotalA(rs.getDouble("totalA"))
                        .addTotalB(rs.getDouble("totalB"))
                        .addTotalC(rs.getDouble("totalC"))
                        .addNdsTotalA(rs.getDouble("ndstotalA"))
                        .addNdsTotalB(rs.getDouble("ndstotalB"))
                        .addNdsTotalC(rs.getDouble("ndstotalC"))
                        .build();
            }
        } catch (SQLException e) {
            LOGGER.error("Не удалось сформировать Z-отчет", e);
        }
        return Optional.ofNullable(zReport);
    }
}