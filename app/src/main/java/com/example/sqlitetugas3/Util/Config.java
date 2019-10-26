package com.example.sqlitetugas3.Util;

public class Config {

    public static final String DATABASE_NAME = "db_product";

    //column names of product table
    public static final String TABLE_PRODUCT = "tb_product";
    public static final String COLUMN_PRODUCT_ID = "_id";
    public static final String COLUMN_PRODUCT_IDCATEGORYPRODUCT = "id_category_product";
    public static final String COLUMN_PRODUCT_IDTOKO = "id_toko";
    public static final String COLUMN_PRODUCT_NAME = "name";
    public static final String COLUMN_PRODUCT_JENIS = "jenis_produk";
    public static final String COLUMN_PRODUCT_HARGA = "harga";
    public static final String COLUMN_PRODUCT_MERK = "merk";
    public static final String COLUMN_PRODUCT_QTY = "qty";
    public static final String COLUMN_PRODUCT_DESC = "deskripsi";

    //column names of product table
    public static final String TABLE_DEVISI = "tb_devisi";
    public static final String COLUMN_DEVISI_ID = "_id";
    public static final String COLUMN_DEVISI_NAME = "name";

    //column names of product table
    public static final String TABLE_CATEGORY_PRODUCT = "tb_category_product";
    public static final String COLUMN_CATEGORY_PRODUCT_ID = "_id";
    public static final String COLUMN_CATEGORY_PRODUCT_NAME = "name";

    //column names of product table
    public static final String TABLE_PEGAWAI = "tb_pegawai";
    public static final String COLUMN_PEGAWAI_ID = "_id";
    public static final String COLUMN_PEGAWAI_IDTOKO = "id_toko";
    public static final String COLUMN_PEGAWAI_IDDEVISI = "id_devisi";
    public static final String COLUMN_PEGAWAI_NAME = "name";
    public static final String COLUMN_PEGAWAI_ALAMAT = "alamat";
    public static final String COLUMN_PEGAWAI_NOTELP = "no_telp";

    //column names of product table
    public static final String TABLE_TOKO = "tb_toko";
    public static final String COLUMN_TOKO_ID = "_id";
    public static final String COLUMN_TOKO_NAME = "name";
    public static final String COLUMN_TOKO_ALAMAT = "alamat";
    public static final String COLUMN_TOKO_NOTELP = "no_telp";


    //others for general purpose key-value pair data
    public static final String TITLE = "title";
    public static final String CREATE_PRODUCT = "create_product";
    public static final String UPDATE_PRODUCT = "update_product";

    public static final String CREATE_TOKO = "create_toko";
    public static final String UPDATE_TOKO = "update_toko";

    public static final String CREATE_PEGAWAI = "create_pegawai";
    public static final String UPDATE_PEGAWAI = "update_pegawai";

    public static final String CREATE_CATEGORY_PRODUCT = "create_category_product";
    public static final String UPDATE_CATEGORY_PRODUCT = "update_category_product";

    public static final String CREATE_DEVISI = "create_devisi";
    public static final String UPDATE_DEVISI = "update_devisi";
}
