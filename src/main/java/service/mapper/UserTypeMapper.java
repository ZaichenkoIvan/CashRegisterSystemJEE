package service.mapper;

import domain.UserType;
import entity.UserTypeEntity;

import java.util.Objects;

public class UserTypeMapper {
    UserType userTypeEntityToUserType(UserTypeEntity userTypeEntity) {
        if (Objects.isNull(userTypeEntity)) {
            return null;
        }

        UserType userType = new UserType();
        userType.setId(userTypeEntity.getId());
        userType.setType(userTypeEntity.getType());
        userType.setDescription(userTypeEntity.getDescription());
        return userType;
    }

    UserTypeEntity userTypeToUserTypeEntity(UserType userType) {
        if (Objects.isNull(userType)) {
            return null;
        }

        UserTypeEntity userTypeEntity = new UserTypeEntity();
        userTypeEntity.setId(userType.getId());
        userTypeEntity.setType(userType.getType());
        userTypeEntity.setDescription(userType.getDescription());
        return userTypeEntity;
    }
}
