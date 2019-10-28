package com.example.sqlitetugas3.pojo;

public class Pegawai {
    private long id;
    private String pegawaiToko,pegawaiDevisi,pegawaiName,pegawaiAlamat,pegawaiNoTelp;

    public Pegawai() {
    }

    public Pegawai(long id, String pegawaiToko, String pegawaiDevisi, String pegawaiName, String pegawaiAlamat, String pegawaiNoTelp) {
        this.id = id;
        this.pegawaiToko = pegawaiToko;
        this.pegawaiDevisi = pegawaiDevisi;
        this.pegawaiName = pegawaiName;
        this.pegawaiAlamat = pegawaiAlamat;
        this.pegawaiNoTelp = pegawaiNoTelp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPegawaiToko() {
        return pegawaiToko;
    }

    public void setPegawaiToko(String pegawaiToko) {
        this.pegawaiToko = pegawaiToko;
    }

    public String getPegawaiDevisi() {
        return pegawaiDevisi;
    }

    public void setPegawaiDevisi(String pegawaiDevisi) {
        this.pegawaiDevisi = pegawaiDevisi;
    }

    public String getPegawaiName() {
        return pegawaiName;
    }

    public void setPegawaiName(String pegawaiName) {
        this.pegawaiName = pegawaiName;
    }

    public String getPegawaiAlamat() {
        return pegawaiAlamat;
    }

    public void setPegawaiAlamat(String pegawaiAlamat) {
        this.pegawaiAlamat = pegawaiAlamat;
    }

    public String getPegawaiNoTelp() {
        return pegawaiNoTelp;
    }

    public void setPegawaiNoTelp(String pegawaiNoTelp) {
        this.pegawaiNoTelp = pegawaiNoTelp;
    }
}
