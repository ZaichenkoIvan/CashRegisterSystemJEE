package main.java.service.mapper;

import main.java.domain.Check;
import main.java.entity.CheckEntity;

import java.util.Objects;

public class CheckMapper {

    public Check checkEntityToCheck(CheckEntity checkEntity) {
        if (Objects.isNull(checkEntity)) {
            return null;
        }

        Check check = new Check();
        check.setId(checkEntity.getId());
        check.setTotal(checkEntity.getTotal());
        check.setCrtime(checkEntity.getCrtime());
        check.setCanceled(checkEntity.getCanceled());
        check.setDiscount(checkEntity.getDiscount());
        check.setCreator(checkEntity.getCreator());
        check.setRegistration(checkEntity.getRegistration());

        return check;
    }

    public CheckEntity checkToCheckEntity(Check check) {
        if (Objects.isNull(check)) {
            return null;
        }

        CheckEntity checkEntity = new CheckEntity();
        checkEntity.setId(check.getId());
        checkEntity.setCrtime(check.getCrtime());
        checkEntity.setTotal(check.getTotal());
        checkEntity.setDiscount(check.getDiscount());
        checkEntity.setCanceled(check.getCanceled());
        checkEntity.setRegistration(check.getRegistration());
        checkEntity.setCreator(check.getCreator());

        return checkEntity;
    }
}
