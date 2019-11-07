package project.controller.command.show;

import project.controller.command.Command;
import project.model.domain.Product;
import project.model.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ProductShowCommand implements Command {
    private ProductService productService;

    public ProductShowCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int currentPage = Integer.valueOf(request.getParameter("currentPage"));
        int recordsPerPage = Integer.valueOf(request.getParameter("recordsPerPage"));

        List<Product> products = productService.findAll(currentPage, recordsPerPage);

        request.setAttribute("products", products);

        int rows = productService.getNumberOfRows();

        int nOfPages = rows / recordsPerPage;

        if (nOfPages % recordsPerPage > 0) {
            nOfPages++;
        }

        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", recordsPerPage);
        final String command = request.getParameter("commandShow");
        request.setAttribute("showProducts", command);

        return "listProducts.jsp";
    }
}
