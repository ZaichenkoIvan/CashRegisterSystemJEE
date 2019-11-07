package project.controller.context;

import project.controller.command.Command;
import project.controller.command.show.*;
import project.controller.command.user.LoginCommand;
import project.controller.command.user.LogoutCommand;
import project.controller.command.user.RegisterCommand;
import project.model.dao.*;
import project.model.dao.connector.PoolConnector;
import project.model.dao.impl.*;
import project.model.domain.User;
import project.model.service.*;
import project.model.service.encoder.PasswordEncoder;
import project.model.service.impl.*;
import project.model.service.mapper.*;
import project.model.service.validator.UserValidator;
import project.model.service.validator.Validator;

import java.util.HashMap;
import java.util.Map;

public final class ApplicationContextInjector {
    private static final PoolConnector CONNECTOR = new PoolConnector("database");

    private static final UserMapper USER_MAPPER = new UserMapper();
    private static final InvoiceMapper INVOICE_MAPPER = new InvoiceMapper();
    private static final PaymentMapper PAYMENT_MAPPER = new PaymentMapper();
    private static final ProductMapper PRODUCT_MAPPER = new ProductMapper();
    private static final OrderMapper ORDER_MAPPER = new OrderMapper();

    private static final UserDao USER_DAO = new UserDaoImpl(CONNECTOR);
    private static final InvoiceDao INVOICE_DAO = new InvoiceDaoImpl(CONNECTOR);
    private static final PaymentDao PAYMENT_DAO = new PaymentDaoImpl(CONNECTOR);
    private static final ProductDao PRODUCT_DAO = new ProductDaoImpl(CONNECTOR);
    private static final OrderDao ORDER_DAO = new OrderDaoImpl(CONNECTOR);

    private static final PasswordEncoder PASSWORD_ENCODER = new PasswordEncoder();

    private static final Validator<User> USER_VALIDATOR = new UserValidator();

    private static final UserService USER_SERVICE = new UserServiceImpl(USER_DAO, PASSWORD_ENCODER, USER_MAPPER, USER_VALIDATOR);
    private static final InvoiceService INVOICE_SERVICE = new InvoiceServiceImpl(INVOICE_DAO, INVOICE_MAPPER);
    private static final PaymentService PAYMENT_SERVICE = new PaymentServiceImpl(PAYMENT_DAO, PAYMENT_MAPPER);
    private static final ProductService PRODUCT_SERVICE = new ProductServiceImpl(PRODUCT_DAO, PRODUCT_MAPPER);
    private static final OrderService ORDER_SERVICE = new OrderServiceImpl(ORDER_DAO, ORDER_MAPPER);

    private static final Command LOGIN_COMMAND = new LoginCommand(USER_SERVICE);
    private static final Command LOGOUT_COMMAND = new LogoutCommand();
    private static final Command REGISTER_COMMAND = new RegisterCommand(USER_SERVICE);

    private static final Command ORDER_SHOW_COMMAND = new OrderShowCommand(ORDER_SERVICE);
    private static final Command USER_SHOW_COMMAND = new UserShowCommand(USER_SERVICE);
    private static final Command INVOICE_SHOW_COMMAND = new InvoiceShowCommand(INVOICE_SERVICE);
    private static final Command PAYMENT_SHOW_COMMAND = new PaymentShowCommand(PAYMENT_SERVICE);
    private static final Command PRODUCT_SHOW_COMMAND = new ProductShowCommand(PRODUCT_SERVICE);

    private static final Map<String, Command> USER_COMMANDS_NAME_TO_COMMAND = initUserCommand();

    private static Map<String, Command> initUserCommand() {
        Map<String, Command> userCommandNameToCommand = new HashMap<>();
        userCommandNameToCommand.put("login", LOGIN_COMMAND);
        userCommandNameToCommand.put("logout", LOGOUT_COMMAND);
        userCommandNameToCommand.put("register", REGISTER_COMMAND);
        userCommandNameToCommand.put("showUsers", USER_SHOW_COMMAND);
        userCommandNameToCommand.put("showOrders", ORDER_SHOW_COMMAND);
        userCommandNameToCommand.put("showInvoices", INVOICE_SHOW_COMMAND);
        userCommandNameToCommand.put("showPayments", PAYMENT_SHOW_COMMAND);
        userCommandNameToCommand.put("showProducts", PRODUCT_SHOW_COMMAND);

        return userCommandNameToCommand;
    }

    private static ApplicationContextInjector injector;

    private ApplicationContextInjector() {
    }

    public static ApplicationContextInjector getInstance() {
        if (injector == null) {
            synchronized (ApplicationContextInjector.class) {
                if (injector == null) {
                    injector = new ApplicationContextInjector();
                }
            }
        }
        return injector;
    }

    public UserService getUserService() {
        return USER_SERVICE;
    }

    public InvoiceService getInvoiceService() {
        return INVOICE_SERVICE;
    }

    public OrderService getOrderService() {
        return ORDER_SERVICE;
    }

    public PaymentService getPaymentService() {
        return PAYMENT_SERVICE;
    }

    public ProductService getProductService() {
        return PRODUCT_SERVICE;
    }

    public Map<String, Command> getUserCommands() {
        return USER_COMMANDS_NAME_TO_COMMAND;
    }
}
