package com.example.management;

public class Info {

    public String money,subject,category,date,explain;
    String uid,email,status;

    public Info(){}
    public Info(String money, String subject, String category, String date, String explain) {
        this.money = money;
        this.subject = subject;
        this.category = category;
        this.date = date;
        this.explain = explain;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
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
}