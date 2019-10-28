package com.example.sqlitetugas3.pojo;

public class Product {
    private long id;
    private String productName,productMerk,productDesc,productCategory,productToko;
    private int productHarga,productQty;

    public Product(long id, String productName, String productMerk, String productDesc, String productCategory, String productToko, int productHarga, int productQty) {
        this.id = id;
        this.productName = productName;
        this.productMerk = productMerk;
        this.productDesc = productDesc;
        this.productCategory = productCategory;
        this.productToko = productToko;
        this.productHarga = productHarga;
        this.productQty = productQty;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductMerk() {
        return productMerk;
    }

    public void setProductMerk(String productMerk) {
        this.productMerk = productMerk;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductToko() {
        return productToko;
    }

    public void setProductToko(String productToko) {
        this.productToko = productToko;
    }

    public int getProductHarga() {
        return productHarga;
    }

    public void setProductHarga(int productHarga) {
        this.productHarga = productHarga;
    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }
}
