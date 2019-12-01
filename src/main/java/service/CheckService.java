package main.java.service;

import main.java.entity.Check;
import main.java.entity.Checkspec;
import main.java.entity.User;

import java.util.List;

public interface CheckService {
    Checkspec addCheckSpec(Integer xcode, Double quant, String nds);

    void addCheck(User user, List<Checkspec> checkspecs);

    List<Checkspec> findAllCheckspecByCheckId(Long checkId);

    Check findById(Long id);

    void cancelCheck(Check check);

    void cancelCheckSpec(List<Checkspec> checkspecs, int checkspecnum, Check check);
}
