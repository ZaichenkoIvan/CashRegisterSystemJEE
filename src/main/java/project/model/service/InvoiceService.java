package project.model.service;

import project.model.domain.Invoice;

import java.util.List;

public interface InvoiceService {
    boolean createInvoice(Invoice Invoice);

    List<Invoice> findAll(int currentPage, int recordsPerPage);

    int getNumberOfRows();
}
