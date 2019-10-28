package com.example.sqlitetugas3.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.example.sqlitetugas3.Util.Config;
import com.example.sqlitetugas3.pojo.Toko;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TokoQuery {

    private Context context;

    public TokoQuery(Context context){
        this.context = context;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public long insertToko(Toko toko){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_TOKO_NAME, toko.getTokoName());
        contentValues.put(Config.COLUMN_TOKO_ALAMAT, toko.getTokoAlamat());
        contentValues.put(Config.COLUMN_TOKO_NOTELP, toko.getTokoNoTelp());

        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_TOKO, null, contentValues);
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public List<Toko> getAllToko(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_TOKO, null, null, null, null, null, null, null);

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<Toko> tokoList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TOKO_ID));
                        String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TOKO_NAME));
                        String alamat = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TOKO_ALAMAT));
                        String noTelp = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TOKO_NOTELP));

                        tokoList.add(new Toko(id,name,alamat,noTelp));
                    }   while (cursor.moveToNext());

                    return tokoList;
                }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public Toko getTokoByID(long id){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        Toko toko = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_TOKO, null,
                    Config.COLUMN_TOKO_ID + " = ? ", new String[]{String.valueOf(id)},
                    null, null, null);

            if(cursor.moveToFirst()){
                int idToko = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_TOKO_ID));
                String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TOKO_NAME));
                String alamat = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TOKO_ALAMAT));
                String noTelp = cursor.getString(cursor.getColumnIndex(Config.COLUMN_TOKO_NOTELP));

                toko = new Toko(idToko,name,alamat,noTelp);
            }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return toko;
    }

    public long updateToko(Toko toko){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_TOKO_NAME, toko.getTokoName());
        contentValues.put(Config.COLUMN_TOKO_ALAMAT, toko.getTokoAlamat());
        contentValues.put(Config.COLUMN_TOKO_NOTELP, toko.getTokoNoTelp());

        try {
            rowCount = sqLiteDatabase.update(Config.TABLE_TOKO, contentValues,
                    Config.COLUMN_TOKO_ID + " = ? ",
                    new String[] {String.valueOf(toko.getId())});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public long deleteTokoByID(long id) {
        long deletedRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = sqLiteDatabase.delete(Config.TABLE_TOKO,
                    Config.COLUMN_TOKO_ID + " = ? ",
                    new String[]{ String.valueOf(id)});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deletedRowCount;
    }

    public boolean deleteAllToko(){
        boolean deleteStatus = false;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            //for "1" delete() method returns number of deleted rows
            //if you don't want row count just use delete(TABLE_NAME, null, null)
            sqLiteDatabase.delete(Config.TABLE_TOKO, null, null);

            long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_TOKO);

            if(count==0)
                deleteStatus = true;

        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deleteStatus;
    }

}
