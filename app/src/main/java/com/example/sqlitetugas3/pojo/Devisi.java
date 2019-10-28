package com.example.sqlitetugas3.pojo;

public class Devisi {
    private long id;
    private String devisiName;

    public Devisi() {
    }

    public Devisi(long id, String devisiName) {
        this.id = id;
        this.devisiName = devisiName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDevisiName() {
        return devisiName;
    }

    public void setDevisiName(String devisiName) {
        this.devisiName = devisiName;
    }
}
