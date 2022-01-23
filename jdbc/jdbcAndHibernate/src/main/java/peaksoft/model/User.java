package peaksoft.model;

import javax.persistence.*;

@Table
public class User {

    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column
    private Byte age;

    public User() {
    }

    public User(String name, String lastName, Byte age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    @Override
    public String toString() {
        System.out.println(ANSI_YELLOW + "N: " + ANSI_RESET + id + "\n" +
                           ANSI_YELLOW + "Name: " + ANSI_RESET + name + "\n" +
                           ANSI_YELLOW + "LastName: " + ANSI_RESET + lastName + "\n" +
                           ANSI_YELLOW + "Age: " + ANSI_RESET + age + "\n" +
                           ANSI_YELLOW + "-------------------------------" + ANSI_RESET);
        return "";
    }
}