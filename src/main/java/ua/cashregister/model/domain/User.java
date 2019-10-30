package ua.cashregister.model.domain;

import ua.cashregister.model.domain.enums.UserRole;

import java.io.Serializable;

public class User implements Serializable {

    private final Integer id;
    private final String name;
    private final String password;
    private final String phoneNumber;
    private final String email;
    private final UserRole userRole;

    private User(UserBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.password = builder.password;
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;
        this.userRole = builder.userRole;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!name.equals(user.name)) return false;
        return password.equals(user.password);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "\nUser ID = " + id +
                "\nName: " + name +
                "\nPassword: " + password +
                "\nPhone: " + phoneNumber +
                "\ne-mail: " + email +
                "\nRole: " + userRole +
                "\n---------------------------------------------------------------------------------------------------";
    }

    public static final class UserBuilder {
        private Integer id;
        private String name;
        private String password;
        private String phoneNumber;
        private String email;
        private UserRole userRole;

        private UserBuilder() {
        }

        public UserBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public UserBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withUserRole(UserRole userRole) {
            this.userRole = userRole;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
