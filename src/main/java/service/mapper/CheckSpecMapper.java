package main.java.service.mapper;

import main.java.domain.Check;
import main.java.domain.Checkspec;
import main.java.entity.CheckEntity;
import main.java.entity.CheckspecEntity;

import java.util.Objects;

public class CheckSpecMapper {
    private CheckMapper checkMapper;

    public CheckSpecMapper(CheckMapper checkMapper) {
        this.checkMapper = checkMapper;
    }

    public Checkspec checkspecEntityToCheckspec(CheckspecEntity checkspecEntity) {
        if (Objects.isNull(checkspecEntity)) {
            return null;
        }

        Check check = checkMapper.checkEntityToCheck(checkspecEntity.getCheck());

        Checkspec checkspec = new Checkspec();
        checkspec.setId(checkspecEntity.getId());
        checkspec.setQuant(checkspecEntity.getQuant());
        checkspec.setPrice(checkspecEntity.getPrice());
        checkspec.setTotal(checkspecEntity.getTotal());
        checkspec.setNds(checkspecEntity.getNds());
        checkspec.setNdstotal(checkspecEntity.getNdstotal());
        checkspec.setCanceled(checkspecEntity.getCanceled());
        checkspec.setXcode(checkspecEntity.getXcode());
        checkspec.setXname(checkspecEntity.getXname());
        checkspec.setCheck(check);
        checkspec.setIdCheck(checkspecEntity.getIdCheck());
        checkspec.setIdGood(checkspecEntity.getIdGood());

        return checkspec;
    }

    public CheckspecEntity checkspecToCheckspecEntity(Checkspec checkspec) {
        if (Objects.isNull(checkspec)) {
            return null;
        }

        CheckEntity check = checkMapper.checkToCheckEntity(checkspec.getCheck());

        CheckspecEntity checkspecEntity = new CheckspecEntity();
        checkspecEntity.setId(checkspec.getId());
        checkspecEntity.setQuant(checkspec.getQuant());
        checkspecEntity.setPrice(checkspec.getPrice());
        checkspecEntity.setTotal(checkspec.getTotal());
        checkspecEntity.setNdstotal(checkspec.getNdstotal());
        checkspecEntity.setNds(checkspec.getNds());
        checkspecEntity.setCanceled(checkspec.getCanceled());
        checkspecEntity.setXcode(checkspec.getXcode());
        checkspecEntity.setXname(checkspec.getXname());
        checkspecEntity.setCheck(check);
        checkspecEntity.setIdGood(checkspec.getIdGood());
        checkspecEntity.setIdCheck(checkspec.getIdCheck());

        return checkspecEntity;
    }
}
