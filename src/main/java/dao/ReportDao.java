package main.java.dao;

import main.java.service.Report;

import java.util.Optional;

public interface ReportDao {

    Optional<Report> getDataReport();

    Optional<Report> getDataZReport();
}
