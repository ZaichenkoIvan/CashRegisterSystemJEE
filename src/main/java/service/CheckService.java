package service;

import domain.Check;
import domain.Checkspec;
import domain.User;

import java.util.List;

public interface CheckService {
    Checkspec addCheckSpec(String name, String code, Double quant, String nds);

    void addCheck(User user, List<Checkspec> checkspecs);

    List<Checkspec> findAllCheckspecByCheckId(Long checkId);

    Check findById(Long id);

    void cancelCheck(Check check);

    void cancelCheckSpec(List<Checkspec> checkspecs, int checkspecnum, Check check);
}
