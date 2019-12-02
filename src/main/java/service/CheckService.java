package main.java.service;

import main.java.domain.Check;
import main.java.domain.Checkspec;
import main.java.domain.User;

import java.util.List;

public interface CheckService {
    Checkspec addCheckSpec(Integer code, Double quant, String nds);

    void addCheck(User user, List<Checkspec> checkspecs);

    List<Checkspec> findAllCheckspecByCheckId(Long checkId);

    Check findById(Long id);

    void cancelCheck(Check check);

    void cancelCheckSpec(List<Checkspec> checkspecs, int checkspecnum, Check check);
}
