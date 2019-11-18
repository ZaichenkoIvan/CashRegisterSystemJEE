package com.epam.project.config;

import com.epam.project.commands.Command;
import com.epam.project.commands.implementation.*;
import com.epam.project.dao.*;
import com.epam.project.dao.implementation.*;
import com.epam.project.service.*;
import com.epam.project.service.implementation.*;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContextInjector {
    private static final PoolConnector CONNECTOR = new PoolConnector();

    private static final UserDao USER_DAO = new UserDaoImpl(CONNECTOR.getConnection());
    private static final InvoiceDao INVOICE_DAO = new InvoiceDaoImpl(CONNECTOR.getConnection());
    private static final PaymentDao PAYMENT_DAO = new PaymentDaoImpl(CONNECTOR.getConnection());
    private static final ProductDao PRODUCT_DAO = new ProductDaoImpl(CONNECTOR.getConnection());
    private static final TransactionDao TRANSACTION_DAO = new TransactionDaoImpl(CONNECTOR.getConnection());

    private static final UserService USER_SERVICE = new UserServiceImpl(USER_DAO);
    private static final PaymentService PAYMENT_SERVICE = new PaymentServiceImpl(PAYMENT_DAO, PRODUCT_DAO);
    private static final ProductService PRODUCT_SERVICE = new ProductServiceImpl(PRODUCT_DAO);
    private static final TransactionService TRANSACTION_SERVICE = new TransactionServiceImpl(TRANSACTION_DAO);
    private static final InvoiceService INVOICE_SERVICE = new InvoiceServiceImpl(INVOICE_DAO, PAYMENT_DAO,
            PRODUCT_DAO, TRANSACTION_DAO, TRANSACTION_SERVICE, PRODUCT_SERVICE);

    private static final Map<String, Command> NAME_COMMAND_TO_COMMANDS = initCommand();

    public Map<String, Command> getCommands() {
        return NAME_COMMAND_TO_COMMANDS;
    }

    private static Map<String, Command> initCommand() {
        Map<String, Command> commandNameToCommand = new HashMap<>();
        /** Commands available for User */
        commandNameToCommand.put("enter", new CommandOpenLoginPage());
        commandNameToCommand.put("login", new CommandValidateUser(USER_SERVICE));
        commandNameToCommand.put("logout", new CommandLogout());
        commandNameToCommand.put("main", new CommandOpenMainPage(PRODUCT_SERVICE));
        commandNameToCommand.put("usersCart", new CommandOpenUsersCart(INVOICE_SERVICE));
        commandNameToCommand.put("addProductToCart", new CommandAddToCart());
        commandNameToCommand.put("removeProductFromCart", new CommandRemoveFromCart());
        commandNameToCommand.put("createInvoice", new CommandCreateInvoice(INVOICE_SERVICE));
        commandNameToCommand.put("createInvoiceAndPay", new CommandCreateInvoiceAndPay(INVOICE_SERVICE));
        commandNameToCommand.put("addNewUser", new CommandOpenRegistrationPage());
        commandNameToCommand.put("registerNewUser", new CommandSaveNewUser(USER_SERVICE));
        commandNameToCommand.put("showUserProfile", new CommandOpenUserProfilePage());
        commandNameToCommand.put("saveUserProfile", new CommandSaveUserProfile(USER_SERVICE));
        /** Commands available for Administration */
        commandNameToCommand.put("administration", new CommandOpenAdminPage());
        commandNameToCommand.put("manageInvoices", new CommandOpenInvoiceMngPage(INVOICE_SERVICE));
        commandNameToCommand.put("manageUsers", new CommandOpenUserMngPage(USER_SERVICE));
        commandNameToCommand.put("manageProducts", new CommandOpenProductMngPage(PRODUCT_SERVICE));
        commandNameToCommand.put("manageTransactions", new CommandOpenTransMngPage(TRANSACTION_SERVICE));
        commandNameToCommand.put("showInvoiceDetails", new CommandOpenInvDetailsPage(INVOICE_SERVICE, PRODUCT_SERVICE));
        commandNameToCommand.put("removeProductFromInvoice", new CommandRemoveFromInvoice(INVOICE_SERVICE, PRODUCT_SERVICE));
        commandNameToCommand.put("closeInvoice", new CommandCloseInvoice(INVOICE_SERVICE));
        commandNameToCommand.put("cancelInvoice", new CommandCancelInvoice(INVOICE_SERVICE));
        commandNameToCommand.put("addNewProductPage", new CommandOpenNewProductPage());
        commandNameToCommand.put("saveNewProduct", new CommandSaveNewProduct(PRODUCT_SERVICE));
        commandNameToCommand.put("editProductPage", new CommandOpenEditProductPage(PRODUCT_SERVICE));
        commandNameToCommand.put("updateProduct", new CommandUpdateProduct(PRODUCT_SERVICE));
        commandNameToCommand.put("deleteProduct", new CommandDeleteProduct(PRODUCT_SERVICE));
        commandNameToCommand.put("addNewPayment", new CommandAddNewPayment(INVOICE_SERVICE, PRODUCT_SERVICE, PAYMENT_SERVICE));
        commandNameToCommand.put("confirmPayment", new CommandConfirmPayment(INVOICE_SERVICE));
        commandNameToCommand.put("changeLang", new CommandChangeLang());
        return commandNameToCommand;
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

    public static UserService getUserService() {
        return USER_SERVICE;
    }

    public static PaymentService getPaymentService() {
        return PAYMENT_SERVICE;
    }

    public static ProductService getProductService() {
        return PRODUCT_SERVICE;
    }

    public static TransactionService getTransactionService() {
        return TRANSACTION_SERVICE;
    }

    public static InvoiceService getInvoiceService() {
        return INVOICE_SERVICE;
    }
}
