package main.java.service;

import main.java.dao.DAOFactory;
import main.java.dao.ICheckDAO;
import main.java.dao.ICheckSpecDAO;
import main.java.dao.IGoodsDAO;
import main.java.entity.Check;
import main.java.entity.Checkspec;
import main.java.entity.Goods;
import main.java.entity.User;
import main.java.exception.NotEnoughGoodsQuantRuntimeException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class CheckService {
    private static final Logger logger = Logger.getLogger(CheckService.class);


    public static Checkspec addCheckSpec(String xcode, String xname, Double quant, String nds) throws NumberFormatException {

        Goods existsGoods = null;
        Integer code = null;
        IGoodsDAO<Goods> goodsDAO = DAOFactory.getGoodsDAO();
        if (xcode != null && !xcode.isEmpty()) {
            code = Integer.valueOf(xcode);
            existsGoods = goodsDAO.findGoods(code);
        } else if (xname != null && !xname.isEmpty()) {
            existsGoods = goodsDAO.findGoods(xname);
        }



        if (existsGoods != null) {

            if(quant>existsGoods.getQuant()){
                logger.info("Недостатня кількість товару");
                throw new NotEnoughGoodsQuantRuntimeException("Недостатня кількість товару");
            }

            Checkspec spec = new Checkspec();
            spec.setIdGood(existsGoods.getId());
            spec.setXname(existsGoods.getName());
            spec.setXcode(existsGoods.getCode());
            spec.setQuant(quant);
            spec.setPrice(existsGoods.getPrice());
            spec.setTotal(BigDecimal.valueOf(spec.getQuant()).multiply(BigDecimal.valueOf(spec.getPrice())).doubleValue());
            spec.setNds(nds != null ? Integer.valueOf(nds) : 0);
            spec.setNdstotal(BigDecimal.valueOf(spec.getTotal()).multiply(BigDecimal.valueOf(spec.getNds())).divide(new BigDecimal(100)).doubleValue());
            return spec;
        }
        return null;
    }

    public static void addCheck(User user, List<Checkspec> checkspecs) {

        ICheckDAO<Check> checkDAO = DAOFactory.getCheckDAO();
        ICheckSpecDAO<Checkspec> checkspecDAO = DAOFactory.getCheckSpecDAO();
        IGoodsDAO<Goods> goodsDAO = DAOFactory.getGoodsDAO();
        Check check = new Check();
        check.setCreator(user.getId());

        double total = checkspecs.stream().mapToDouble(o -> o.getTotal()).sum();
        check.setTotal(total);
        Long idCheck = checkDAO.insert(check);
        for (Checkspec checkspec : checkspecs
        ) {
            checkspec.setIdCheck(idCheck);
            Goods goods = goodsDAO.findById(checkspec.getIdGood());
            if (goods != null) {
                goods.setQuant(goods.getQuant() - checkspec.getQuant());
                goodsDAO.update(goods);
            }
        }
        checkspecDAO.insertAll(checkspecs);
    }
}
