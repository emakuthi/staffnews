package models;

import java.util.Objects;

public class Staff {
    private String name;
    private int id;

    public Staff(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Staff)) return false;
        Staff staff = (Staff) o;
        return id == staff.id &&
                Objects.equals(name, staff.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

}