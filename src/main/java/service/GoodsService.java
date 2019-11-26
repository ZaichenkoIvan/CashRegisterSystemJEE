package main.java.service;

import java.util.List;

import org.apache.log4j.Logger;

import main.java.dao.DAOFactory;
import main.java.dao.IGoodsDAO;
import main.java.entity.Goods;

public class GoodsService {

	private static final Logger logger = Logger.getLogger(GoodsService.class);

	public static Long addGoods(int code, String name, double quant, double price, String measure, String comments) {
		
		Goods goods = new Goods();
		goods.setCode(code);
		goods.setName(name);
		goods.setQuant(quant);
		goods.setPrice(price);
		goods.setMeasure(measure);
		goods.setComments(comments);
		IGoodsDAO<Goods> goodsDAO = DAOFactory.getGoodsDAO();
		Goods existsGood = goodsDAO.findGoods(code);
		if (existsGood != null) {
			logger.info("Товар с кодом " + code + " уже существует");
			return -1l;
		} else {
			existsGood = goodsDAO.findGoods(name);		
			if (existsGood != null) {
				logger.info("Товар " + name + " уже существует");
				return -2l;
			} else {
				logger.info("Товар добавлен");
				return goodsDAO.insert(goods);			
			}
		}
	}

	public static List<Goods> view(int page, int recordsPerPage) {
		IGoodsDAO<Goods> goodsDAO = DAOFactory.getGoodsDAO();
		List<Goods> goods = goodsDAO.findAll(page, recordsPerPage);
		return (goods.size() > 0 ? goods : null);		
	}

	public static Long count() {
		IGoodsDAO<Goods> goodsDAO = DAOFactory.getGoodsDAO();		
		return goodsDAO.count();		
	}

	public static void changeGoods(Integer changecode, Double changequant, Double changeprice) {
		IGoodsDAO<Goods> goodsDAO = DAOFactory.getGoodsDAO();
		Goods goods = goodsDAO.findGoods(changecode);
		if (goods != null) {
			if (changequant != null) {
				goods.setQuant(changequant);
			}
			if (changeprice != null) {
				goods.setPrice(changeprice);
			}
			goodsDAO.update(goods);
		}
	}
}
