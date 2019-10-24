package ua.cashregister.model.entity;

import java.util.Objects;

public class User {
    private final int id;
    private final String surname;
    private final String email;
    private final String password;
    private final ROLE role;

    public User(UserBuilder builder) {
        this.id = builder.id;
        this.surname = builder.surname;
        this.email = builder.email;
        this.password = builder.password;
        this.role = builder.role;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    public ROLE getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surname, email, password, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", surname='" + surname +
                ", email=" + email +
                ", password=" + password +
                ", role=" + role +
                '}';
    }

    public static final class UserBuilder {
        private int id;
        private String surname;
        private String email;
        private String password;
        private ROLE role;

        private UserBuilder() {
        }

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder withId(int id) {
            this.id = id;
            return this;
        }

        public UserBuilder withSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder withRole(ROLE role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
