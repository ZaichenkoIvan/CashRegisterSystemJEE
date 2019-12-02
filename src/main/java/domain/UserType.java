package main.java.domain;

import java.util.Objects;

public class UserType {
    private Long id;
    private String type;
    private String description;

    public UserType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserType userType = (UserType) o;
        return Objects.equals(id, userType.id) &&
                Objects.equals(type, userType.type) &&
                Objects.equals(description, userType.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, description);
    }

    @Override
    public String toString() {
        return "UserType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
