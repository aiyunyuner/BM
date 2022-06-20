package com.micaros.bm.pojo;

public class User {
    private int id;
    private String name;
    private String uid;
    private String password;
    private String sex;
    private String phone;
    private String birthday;

    public User() {
    }

    public User(int id, String name, String uid, String password, String sex, String phone, String birthday) {
        this.id = id;
        this.name = name;
        this.uid = uid;
        this.password = password;
        this.sex = sex;
        this.phone = phone;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uid='" + uid + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
