package project.controller.filter;

import project.model.domain.User;
import project.model.entity.enums.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain)
            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
        final HttpSession session = req.getSession();

        if (nonNull(session.getAttribute("user"))) {
            final User user = (User) session.getAttribute("user");
            moveToMenu(req, res, user.getRole(), filterChain);
        } else {
            filterChain.doFilter(req, res);
        }
    }

    private void moveToMenu(final HttpServletRequest req, final HttpServletResponse res, final Role role,
                            final FilterChain filterChain) throws ServletException, IOException {

        if (role.equals(Role.ADMIN)) {
            filterChain.doFilter(req, res);
        } else if (role.equals(Role.USER)) {
            String contextPath = req.getRequestURI().substring(req.getContextPath().length());
            if (contextPath.contains("admin.jsp")) {
                req.getRequestDispatcher("user.jsp").forward(req, res);
            }
            filterChain.doFilter(req, res);
        } else {
            req.getRequestDispatcher("login.jsp").forward(req, res);
            filterChain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
    }

}