package models;

import java.util.Objects;

public class User {
    private String name;
    private String number;
    private String designation;
    private int id;

    public User(String name, String number, String designation) {

        this.name = name;
        this.number = number;
        this. designation = designation;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() { return number; }

    public void setNumber(String number) { this.number = number; }

    public String getDesignation() { return designation; }

    public void setDesignation(String designation) { this.designation = designation; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() &&
                getName().equals(user.getName()) &&
                getNumber().equals(user.getNumber()) &&
                getDesignation().equals(user.getDesignation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getNumber(), getDesignation(), getId());
    }

}