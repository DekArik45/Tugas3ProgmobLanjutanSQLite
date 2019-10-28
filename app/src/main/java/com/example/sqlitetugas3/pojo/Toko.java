package com.example.sqlitetugas3.pojo;

public class Toko {
    private long id;
    private String tokoName,tokoAlamat,tokoNoTelp;

    public Toko(long id, String tokoName, String tokoAlamat, String tokoNoTelp) {
        this.id = id;
        this.tokoName = tokoName;
        this.tokoAlamat = tokoAlamat;
        this.tokoNoTelp = tokoNoTelp;
    }

    public Toko() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTokoName() {
        return tokoName;
    }

    public void setTokoName(String tokoName) {
        this.tokoName = tokoName;
    }

    public String getTokoAlamat() {
        return tokoAlamat;
    }

    public void setTokoAlamat(String tokoAlamat) {
        this.tokoAlamat = tokoAlamat;
    }

    public String getTokoNoTelp() {
        return tokoNoTelp;
    }

    public void setTokoNoTelp(String tokoNoTelp) {
        this.tokoNoTelp = tokoNoTelp;
    }
}
