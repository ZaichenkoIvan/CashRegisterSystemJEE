package main.java.entity;

import java.util.Objects;

public class UserTypeEntity {
    private Long id;
    private String type;
    private String description;

    public UserTypeEntity() {
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
        UserTypeEntity userType = (UserTypeEntity) o;
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
        return "UserTypeEntity{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
