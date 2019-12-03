package service.impl;

import dao.ReportDao;
import domain.Report;
import exception.NotCreateReportRuntimeException;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceImplTest {

    private static final Report Z_REPORT = getZReport();
    private static final Report X_REPORT = getXReport();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private ReportDao repository;

    @InjectMocks
    private ReportServiceImpl service;

    @After
    public void resetMock() {
        reset(repository);
    }

    @Test
    public void shouldReturnXReport() {
        when(repository.getDataReport()).thenReturn(Optional.of(X_REPORT));

        Report report = service.getDataReport();

        assertThat(report, is(X_REPORT));
        verify(repository).getDataReport();
    }

    @Test
    public void shouldReturnZReport() {
        when(repository.getDataZReport()).thenReturn(Optional.of(Z_REPORT));

        Report report = service.getDataZReport();

        assertThat(report, is(Z_REPORT));
        verify(repository).getDataZReport();
    }

    @Test
    public void shouldThrowNotCreateReportRuntimeExceptionIfDontCreatingXReport() {
        exception.expect(NotCreateReportRuntimeException.class);
        when(repository.getDataReport()).thenReturn(Optional.empty());

        service.getDataReport();
    }

    @Test
    public void shouldThrowNotCreateReportRuntimeExceptionIfDontCreatingZReport() {
        exception.expect(NotCreateReportRuntimeException.class);
        when(repository.getDataZReport()).thenReturn(Optional.empty());

        service.getDataZReport();
    }

    private static Report getXReport() {
        Report report = new Report();
        report.setNumber(1);
        report.setCountCheck(1);
        report.setCountCancelCheck(1);
        report.setSumNdsTotal(100);
        return report;
    }

    private static Report getZReport() {
        Report report = new Report();
        report.setNumber(1);
        report.setCountCheck(1);
        report.setCountCancelCheck(1);
        report.setSumNdsTotal(100);
        report.setSumTotal(100);
        report.setNdsTotalA(100);
        report.setNdsTotalB(100);
        report.setNdsTotalC(100);
        report.setTotalA(100);
        report.setTotalB(100);
        report.setTotalC(100);
        return report;
    }
}