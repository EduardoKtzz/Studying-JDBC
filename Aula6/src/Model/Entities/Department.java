package Model.Entities;

import java.io.Serializable;
import java.util.Objects;

public class Department implements Serializable {

    //Atributos
    private Integer id;
    private String name;

    //Construtores
    public  Department() {

    }

    public Department(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    //Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Equals
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id);
    }

    //HashCode
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
