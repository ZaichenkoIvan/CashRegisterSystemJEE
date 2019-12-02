package command.impl;

import command.Command;
import domain.Goods;
import service.GoodsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GoodsCommand implements Command {
    private final GoodsService goodsService;

    public GoodsCommand(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        if (req.getParameter("btnSaveGood") != null) {
            int code = Integer.valueOf(req.getParameter("code"));
            String name = req.getParameter("name");
            Long goodsId = goodsService.addGoods(code, name, Double.valueOf(req.getParameter("quant")),
                    Double.valueOf(req.getParameter("price")), req.getParameter("measure"), req.getParameter("comments"));
            if (goodsId > 0) {
                req.setAttribute("addedGood", name);
            } else {
                req.setAttribute("addedGood", null);
                req.setAttribute("existsCode", code);
            }
        }
        if (req.getParameter("btnChangeGoods") != null) {
            try {
                String changequant = req.getParameter("changequant");
                Double newQuant = (!changequant.isEmpty() ? Double.valueOf(changequant) : null);
                String changeprice = req.getParameter("changeprice");
                Double newPrice = (!changeprice.isEmpty() ? Double.valueOf(changeprice) : null);

                goodsService.changeGoods(Integer.valueOf(req.getParameter("changecode")), newQuant, newPrice);
            } catch (NumberFormatException e) {
                req.setAttribute("wronginput", true);
            }
        }
        int page = 1;
        if (req.getParameter("page") != null) {
            try {
                page = Integer.parseInt(req.getParameter("page"));
            } catch (NumberFormatException e) {
                req.setAttribute("wronginput", true);
            }
        }
        int recordsPerPage = 10;
        List<Goods> goods = goodsService.view(page, recordsPerPage);
        req.setAttribute("viewGoods", goods);
        req.setAttribute("currentPage", page);
        long countGoods = goodsService.count();
        req.setAttribute("maxPages", countGoods / recordsPerPage + Math.min(countGoods % recordsPerPage, 1));
        return null;
    }
}
