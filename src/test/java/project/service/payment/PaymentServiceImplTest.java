package project.service.payment;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import project.model.dao.PaymentDao;
import project.model.domain.Payment;
import project.model.entity.PaymentEntity;
import project.model.exception.InvalidEntityCreation;
import project.model.service.impl.PaymentServiceImpl;
import project.model.service.mapper.PaymentMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceImplTest {
    private static final Payment PAYMENT = new Payment(1, 2, null, null);
    private static final List<PaymentEntity> PAYMENT_ENTITIES = Arrays.asList(
            new PaymentEntity(1, 2, null, null),
            new PaymentEntity(1, 2, null, null));
    private static final List<Payment> PAYMENTS = Arrays.asList(PAYMENT, PAYMENT);

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private PaymentDao paymentDao;

    @Mock
    private PaymentMapper mapper;

    @InjectMocks
    private PaymentServiceImpl service;

    @After
    public void resetMock() {
        reset(paymentDao, mapper);
    }

    @Test
    public void shouldCreatePayment() {
        when(mapper.mapPaymentToPaymentEntity(any(Payment.class))).thenReturn(PAYMENT_ENTITIES.get(1));
        when(paymentDao.save(any(PaymentEntity.class))).thenReturn(true);

        assertTrue(service.createPayment(PAYMENT));
    }

    @Test
    public void shouldThrowInvalidEntityCreationWithNullPayment() {
        exception.expect(InvalidEntityCreation.class);
        exception.expectMessage("Payment is not valid");

        service.createPayment(null);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWithNullStatus() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Parameter is not valid");

        service.findPaymentByUser(null);
    }

    @Test
    public void shouldShowAllPaymentsByUser() {
        when(paymentDao.findByUser(any(Integer.class))).thenReturn(PAYMENT_ENTITIES);
        when(mapper.mapPaymentEntityToPayment(any(PaymentEntity.class))).thenReturn(PAYMENT);

        List<Payment> actual = service.findPaymentByUser(1);

        assertEquals(PAYMENTS, actual);
    }

    @Test
    public void shouldReturnEmptyListSearchingByUser() {
        when(paymentDao.findByUser(any(Integer.class))).thenReturn(Collections.emptyList());

        List<Payment> actual = service.findPaymentByUser(1);

        assertEquals(Collections.emptyList(), actual);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWithNullUserId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Parameter is not valid");

        service.findPaymentByUser(null);
    }

    @Test
    public void shouldShowAllProducts() {
        when(paymentDao.findAll(1, 2)).thenReturn(PAYMENT_ENTITIES);
        when(mapper.mapPaymentEntityToPayment(any(PaymentEntity.class))).thenReturn(PAYMENT);

        List<Payment> actual = service.findAll(1, 2);

        assertEquals(PAYMENTS, actual);
    }
}
