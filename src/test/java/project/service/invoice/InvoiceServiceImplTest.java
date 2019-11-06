package project.service.invoice;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import project.model.dao.InvoiceDao;
import project.model.domain.Invoice;
import project.model.entity.InvoiceEntity;
import project.model.exception.InvalidEntityCreation;
import project.model.service.impl.InvoiceServiceImpl;
import project.model.service.mapper.InvoiceMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceServiceImplTest {
    private static final Invoice invoice = Invoice.builder().withId(1).build();
    private static final List<InvoiceEntity> entities = Arrays.asList(
            InvoiceEntity.builder().withId(1).build(),
            InvoiceEntity.builder().withId(2).build());
    private static final List<Invoice> invoices = Arrays.asList(invoice, invoice);

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private InvoiceDao invoiceDao;

    @Mock
    private InvoiceMapper mapper;

    @InjectMocks
    private InvoiceServiceImpl service;

    @After
    public void resetMock() {
        reset(invoiceDao);
        reset(mapper);
    }

    @Test
    public void shouldCreateinvoice() {
        when(mapper.mapInvoiceToInvoiceEntity(any(Invoice.class))).thenReturn(entities.get(1));
        when(invoiceDao.save(any(InvoiceEntity.class))).thenReturn(true);

        assertTrue(service.createInvoice(invoice));
    }

    @Test
    public void shouldThrowInvalidEntityCreationWithNullinvoice() {
        exception.expect(InvalidEntityCreation.class);
        exception.expectMessage("Invoice is not valid");

        service.createInvoice(null);
    }

    @Test
    public void shouldShowAllinvoices() {
        when(invoiceDao.findAll()).thenReturn(entities);
        when(mapper.mapInvoiceEntityToInvoice(any(InvoiceEntity.class))).thenReturn(invoice);

        List<Invoice> actual = service.findAllInvoices();

        assertEquals(invoices, actual);
    }

    @Test
    public void shouldReturnEmptyList() {
        when(invoiceDao.findAll()).thenReturn(Collections.emptyList());

        List<Invoice> actual = service.findAllInvoices();

        assertEquals(Collections.emptyList(), actual);
    }
}