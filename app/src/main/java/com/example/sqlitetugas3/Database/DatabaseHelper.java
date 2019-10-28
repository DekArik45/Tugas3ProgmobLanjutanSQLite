package com.example.sqlitetugas3.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlitetugas3.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    // All Static variables
    private static final int DATABASE_VERSION =1 ;

    // Database Name
    private static final String DATABASE_NAME = Config.DATABASE_NAME;

    // Constructor
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static synchronized DatabaseHelper getInstance(Context context){
        if(databaseHelper==null){
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tables SQL execution
        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + Config.TABLE_PRODUCT + "("
                + Config.COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_PRODUCT_TOKO + " TEXT , "
                + Config.COLUMN_PRODUCT_CATEGORYPRODUCT + " TEXT , "
                + Config.COLUMN_PRODUCT_NAME + " TEXT , "
                + Config.COLUMN_PRODUCT_MERK + " TEXT , "
                + Config.COLUMN_PRODUCT_HARGA + " INTEGER , "
                + Config.COLUMN_PRODUCT_QTY + " INTEGER , "
                + Config.COLUMN_PRODUCT_DESC + " TEXT "
                + ");";

        Logger.d("Table create SQL: " + CREATE_PRODUCT_TABLE);

        db.execSQL(CREATE_PRODUCT_TABLE);

        // Create tables SQL execution
        String CREATE_CATEGORYPRODUCT_TABLE = "CREATE TABLE " + Config.TABLE_CATEGORY_PRODUCT + "("
                + Config.COLUMN_CATEGORY_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_CATEGORY_PRODUCT_NAME + " TEXT "
                + ");";

        Logger.d("Table create SQL: " + CREATE_CATEGORYPRODUCT_TABLE);

        db.execSQL(CREATE_CATEGORYPRODUCT_TABLE);

        // Create tables SQL execution
        String CREATE_TOKO = "CREATE TABLE " + Config.TABLE_TOKO + "("
                + Config.COLUMN_TOKO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_TOKO_NAME + " TEXT , "
                + Config.COLUMN_TOKO_ALAMAT + " TEXT , "
                + Config.COLUMN_TOKO_NOTELP + " TEXT "
                + ");";

        Logger.d("Table create SQL: " + CREATE_TOKO);

        db.execSQL(CREATE_TOKO);

        // Create tables SQL execution
        String CREATE_PEGAWAI_TABLE = "CREATE TABLE " + Config.TABLE_PEGAWAI + "("
                + Config.COLUMN_PEGAWAI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_PEGAWAI_TOKO + " TEXT , "
                + Config.COLUMN_PEGAWAI_DEVISI + " TEXT , "
                + Config.COLUMN_PEGAWAI_NAME + " TEXT , "
                + Config.COLUMN_PEGAWAI_ALAMAT + " INTEGER , "
                + Config.COLUMN_PEGAWAI_NOTELP + " INTEGER "
                + ");";

        Logger.d("Table create SQL: " + CREATE_PEGAWAI_TABLE);

        db.execSQL(CREATE_PEGAWAI_TABLE);

        // Create tables SQL execution
        String CREATE_DEVISI_TABLE = "CREATE TABLE " + Config.TABLE_DEVISI + "("
                + Config.COLUMN_DEVISI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_DEVISI_NAME + " TEXT "
                + ");";

        Logger.d("Table create SQL: " + CREATE_DEVISI_TABLE);

        db.execSQL(CREATE_DEVISI_TABLE);

        Logger.d("DB created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_PRODUCT);

        // Create tables again
        onCreate(db);
    }

}
