package main.java.context;

import main.java.command.Command;
import main.java.command.impl.*;
import main.java.dao.*;
import main.java.dao.daoimpl.*;
import main.java.service.CheckService;
import main.java.service.GoodsService;
import main.java.service.ReportService;
import main.java.service.UserService;
import main.java.service.encoder.EncoderPassword;
import main.java.service.impl.CheckServiceImpl;
import main.java.service.impl.GoodsServiceImpl;
import main.java.service.impl.ReportServiceImpl;
import main.java.service.impl.UserServiceImpl;
import main.java.service.mapper.*;
import main.java.service.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContextInjector {
    private static final PoolConnection CONNECTOR = new PoolConnection();

    private static final CheckDao CHECK_DAO = new CheckDaoImpl(CONNECTOR);
    private static final CheckSpecDao CHECK_SPEC_DAO = new CheckSpecDaoImpl(CONNECTOR);
    private static final FiscalDao FISCAL_DAO = new FiscalDaoImpl(CONNECTOR);
    private static final GoodsDao GOODS_DAO = new GoodsDaoImpl(CONNECTOR);
    private static final UserDao USER_DAO = new UserDaoImpl(CONNECTOR);
    private static final UserTypeDao USER_TYPE_DAO = new UserTypeDaoImpl(CONNECTOR);
    private static final ReportDao REPORT_DAO = new ReportDaoImpl(CONNECTOR, CHECK_DAO, FISCAL_DAO);

    private static final EncoderPassword ENCODER_PASSWORD = new EncoderPassword();

    private static final UserValidator USER_VALIDATOR = new UserValidator();

    private static final CheckMapper CHECK_MAPPER = new CheckMapper();
    private static final CheckSpecMapper CHECK_SPEC_MAPPER = new CheckSpecMapper(CHECK_MAPPER);
    private static final GoodMapper GOOD_MAPPER = new GoodMapper();
    private static final UserTypeMapper USER_TYPE_MAPPER = new UserTypeMapper();
    private static final UserMapper USER_MAPPER = new UserMapper(USER_TYPE_MAPPER);

    private static final CheckService CHECK_SERVICE = new CheckServiceImpl(GOODS_DAO, CHECK_DAO, CHECK_SPEC_DAO,
            GOOD_MAPPER, CHECK_MAPPER, CHECK_SPEC_MAPPER);
    private static final ReportService REPORT_SERVICE = new ReportServiceImpl(REPORT_DAO);
    private static final GoodsService GOODS_SERVICE = new GoodsServiceImpl(GOODS_DAO, GOOD_MAPPER);
    private static final UserService USER_SERVICE =
            new UserServiceImpl(USER_DAO, USER_TYPE_DAO, ENCODER_PASSWORD, USER_VALIDATOR, USER_MAPPER);

    private static Map<String, Command> getNameCommandToCommands() {
        return NAME_COMMAND_TO_COMMANDS;
    }

    private static final Map<String, Command> NAME_COMMAND_TO_COMMANDS = initCommand();

    private static Map<String, Command> initCommand() {
        Map<String, Command> commandNameToCommand = new HashMap<>();
        commandNameToCommand.put("LOGIN", new LoginCommand(USER_SERVICE));
        commandNameToCommand.put("REGISTRATION", new RegistrationCommand(USER_SERVICE));
        commandNameToCommand.put("GOODS", new GoodsCommand(GOODS_SERVICE));
        commandNameToCommand.put("CHECK", new CheckCommand(CHECK_SERVICE));
        commandNameToCommand.put("CHECKSPEC", new CheckSpecCommand(CHECK_SERVICE));
        commandNameToCommand.put("CANCEL", new CancelCommand(CHECK_SERVICE, REPORT_SERVICE));
        commandNameToCommand.put("LOGOUT", new LogoutCommand());
        return commandNameToCommand;
    }

    public static Command getCommand(HttpServletRequest req) {
        switch (req.getServletPath()) {
            case "/":
            case "/login":
                if (req.getParameter("btnLogin") != null && req.getParameter("email") != null) {
                    return getNameCommandToCommands().get("LOGIN");
                }
                break;
            case "/logout":
                return getNameCommandToCommands().get("LOGOUT");
            case "/registration":
                if (req.getParameter("btnReg") != null) {
                    return getNameCommandToCommands().get("REGISTRATION");
                }
                break;
            case "/goods":
                return getNameCommandToCommands().get("GOODS");
            case "/check":
                if (req.getParameter("btnAddCheckspec") != null) {
                    return getNameCommandToCommands().get("CHECKSPEC");
                } else if (req.getParameter("btnCreateCheck") != null) {
                    return getNameCommandToCommands().get("CHECK");
                }
                break;
            case "/cancel":
                return getNameCommandToCommands().get("CANCEL");
            default:
                return null;
        }
        return null;
    }

    private static ApplicationContextInjector injector;

    private ApplicationContextInjector() {
    }

    public static void getInstance() {
        if (injector == null) {
            synchronized (ApplicationContextInjector.class) {
                if (injector == null) {
                    injector = new ApplicationContextInjector();
                }
            }
        }
    }
}
