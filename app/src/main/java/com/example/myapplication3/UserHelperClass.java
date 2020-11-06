package com.example.myapplication3;
public class UserHelperClass {
    String name, username, password,profession;
    public UserHelperClass() {
    }
    public UserHelperClass(String name, String username, String password,String profession) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.profession=profession;

    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getProfession() {
        return profession;
    }
    public void setProfession(String password) {
        this.profession = profession;
    }
}