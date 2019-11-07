package project.model.service.impl;

import org.apache.log4j.Logger;
import project.model.dao.InvoiceDao;
import project.model.domain.Invoice;
import project.model.entity.InvoiceEntity;
import project.model.exception.InvalidEntityCreation;
import project.model.service.InvoiceService;
import project.model.service.mapper.InvoiceMapper;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InvoiceServiceImpl implements InvoiceService {
    private static final Logger LOGGER = Logger.getLogger(InvoiceServiceImpl.class);

    private final InvoiceDao invoiceDao;
    private final InvoiceMapper mapper;

    public InvoiceServiceImpl(InvoiceDao invoiceDao, InvoiceMapper mapper) {
        this.invoiceDao = invoiceDao;
        this.mapper = mapper;
    }

    @Override
    public boolean createInvoice(Invoice Invoice) {
        if (Objects.isNull(Invoice) ) {
            LOGGER.warn("Invoice is not valid");
            throw new InvalidEntityCreation("Invoice is not valid");
        }

        return invoiceDao.save(mapper.mapInvoiceToInvoiceEntity(Invoice));
    }

    @Override
    public List<Invoice> findAll(int currentPage, int recordsPerPage) {
        List<InvoiceEntity> result = invoiceDao.findAll(currentPage, recordsPerPage);

        return result.isEmpty() ? Collections.emptyList()
                : result.stream()
                .map(mapper::mapInvoiceEntityToInvoice)
                .collect(Collectors.toList());
    }

    @Override
    public int getNumberOfRows() {
        return invoiceDao.getNumberOfRows();
    }
}
