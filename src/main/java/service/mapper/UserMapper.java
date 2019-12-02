package main.java.service.mapper;

import main.java.domain.User;
import main.java.domain.UserType;
import main.java.entity.UserEntity;
import main.java.entity.UserTypeEntity;

import java.util.Objects;

public class UserMapper {
    private UserTypeMapper userTypeMapper;

    public UserMapper(UserTypeMapper userTypeMapper) {
        this.userTypeMapper = userTypeMapper;
    }

    public User userEntityToUser(UserEntity userEntity) {
        if (Objects.isNull(userEntity)) {
            return null;
        }

        UserType userType = userTypeMapper.userTypeEntityToUserType(userEntity.getUserType());

        User user = new User();
        user.setId(userEntity.getId());
        user.setLogin(userEntity.getLogin());
        user.setPassword(userEntity.getPassword());
        user.setName(userEntity.getName());
        user.setIdUserType(userEntity.getIdUserType());
        user.setUserType(userType);

        return user;
    }

    public UserEntity userToUserEntity(User user) {

        if (Objects.isNull(user)) {
            return null;
        }

        UserTypeEntity userType = userTypeMapper.userTypeToUserTypeEntity(user.getUserType());

        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setLogin(user.getLogin());
        userEntity.setName(user.getName());
        userEntity.setPassword(user.getPassword());
        userEntity.setIdUserType(user.getIdUserType());
        userEntity.setUserType(userType);

        return userEntity;
    }

}
