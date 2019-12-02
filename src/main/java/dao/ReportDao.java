package dao;

import domain.Report;

import java.util.Optional;

public interface ReportDao {

    Optional<Report> getDataReport();

    Optional<Report> getDataZReport();
}
