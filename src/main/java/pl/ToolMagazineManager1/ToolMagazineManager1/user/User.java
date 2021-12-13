package pl.ToolMagazineManager1.ToolMagazineManager1.user;

import pl.ToolMagazineManager1.ToolMagazineManager1.tool.Tool;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (nullable = false, updatable = false)
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String department;
    private String position;

    @ManyToMany(mappedBy = "toolUsers")
    private List<Tool> borrowedTools = new ArrayList<>();


    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(String name, String surname, String email, String phone, String department, String position) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                '}';
    }

    public void addTool(Tool tool){
        borrowedTools.add(tool);
    }
}
