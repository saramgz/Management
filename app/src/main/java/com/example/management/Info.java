package com.example.management;

public class Info {

    public String subject,category,date,explain;
    String uid,email,status;
    int money;

    public Info(){}

    public Info(String subject, String category, String date, String explain, String uid, String email, String status, int money) {
        this.subject = subject;
        this.category = category;
        this.date = date;
        this.explain = explain;
        this.uid = uid;
        this.email = email;
        this.status = status;
        this.money = money;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}