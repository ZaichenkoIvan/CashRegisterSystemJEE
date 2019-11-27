package main.java.context;

import main.java.command.Command;
import main.java.command.impl.*;
import main.java.dao.*;
import main.java.dao.daoimpl.*;
import main.java.service.CheckService;
import main.java.service.GoodsService;
import main.java.service.ReportService;
import main.java.service.UserService;
import main.java.service.impl.CheckServiceImpl;
import main.java.service.impl.GoodsServiceImpl;
import main.java.service.impl.ReportServiceImpl;
import main.java.service.impl.UserServiceImpl;

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

//    private static final AdminMapper ADMIN_MAPPER = new AdminMapper();
//    private static final BusMapper BUS_MAPPER = new BusMapper();
//    private static final DriverMapper DRIVER_MAPPER = new DriverMapper();
//    private static final RouteMapper ROUTE_MAPPER = new RouteMapper();
//    private static final ScheduleMapper SCHEDULE_MAPPER = new ScheduleMapper();
//    private static final UserMapper USER_MAPPER = new UserMapper();

//    private static final EncoderPassword ENCODER_PASSWORD = new EncoderPassword();

//    private static final AdminValidator ADMIN_VALIDATOR = new AdminValidator();
//    private static final BusValidator BUS_VALIDATOR = new BusValidator();
//    private static final DriverValidator DRIVER_VALIDATOR = new DriverValidator();
//    private static final RouteValidator ROUTE_VALIDATOR = new RouteValidator();

    private static final CheckService CHECK_SERVICE = new CheckServiceImpl(GOODS_DAO, CHECK_DAO, CHECK_SPEC_DAO);
    private static final ReportService REPORT_SERVICE = new ReportServiceImpl(REPORT_DAO);
    private static final GoodsService GOODS_SERVICE = new GoodsServiceImpl(GOODS_DAO);
    private static final UserService USER_SERVICE = new UserServiceImpl(USER_DAO, USER_TYPE_DAO);

    public static Map<String, Command> getNameCommandToCommands() {
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

    public static CheckService getCheckService() {
        return CHECK_SERVICE;
    }

    public static ReportService getReportService() {
        return REPORT_SERVICE;
    }

    public static GoodsService getGoodsService() {
        return GOODS_SERVICE;
    }

    public UserService getUserService() {
        return USER_SERVICE;
    }
}