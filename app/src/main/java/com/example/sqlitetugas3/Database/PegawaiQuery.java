package com.example.sqlitetugas3.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.example.sqlitetugas3.Util.Config;
import com.example.sqlitetugas3.pojo.Pegawai;
import com.example.sqlitetugas3.pojo.Toko;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PegawaiQuery {

    private Context context;

    public PegawaiQuery(Context context){
        this.context = context;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public long insertPegawai(Pegawai pegawai){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_PEGAWAI_TOKO, pegawai.getPegawaiToko());
        contentValues.put(Config.COLUMN_PEGAWAI_DEVISI, pegawai.getPegawaiDevisi());
        contentValues.put(Config.COLUMN_PEGAWAI_NAME, pegawai.getPegawaiName());
        contentValues.put(Config.COLUMN_PEGAWAI_ALAMAT, pegawai.getPegawaiAlamat());
        contentValues.put(Config.COLUMN_PEGAWAI_NOTELP, pegawai.getPegawaiNoTelp());

        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_PEGAWAI, null, contentValues);
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public List<Pegawai> getAllPegawai(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_PEGAWAI, null, null, null, null, null, null, null);

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<Pegawai> pegawaiList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PEGAWAI_ID));
                        String toko = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PEGAWAI_TOKO));
                        String devisi = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PEGAWAI_DEVISI));
                        String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PEGAWAI_NAME));
                        String alamat = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PEGAWAI_ALAMAT));
                        String noTelp = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PEGAWAI_NOTELP));

                        pegawaiList.add(new Pegawai(id,toko,devisi,name,alamat,noTelp));
                    }   while (cursor.moveToNext());

                    return pegawaiList;
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

    public Pegawai getPegawaiByID(long id){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        Pegawai pegawai = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_PEGAWAI, null,
                    Config.COLUMN_PEGAWAI_ID + " = ? ", new String[]{String.valueOf(id)},
                    null, null, null);

            if(cursor.moveToFirst()){
                int idPegawai = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PEGAWAI_ID));
                String toko = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PEGAWAI_TOKO));
                String devisi = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PEGAWAI_DEVISI));
                String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PEGAWAI_NAME));
                String alamat = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PEGAWAI_ALAMAT));
                String noTelp = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PEGAWAI_NOTELP));

                pegawai = new Pegawai(idPegawai,toko,devisi,name,alamat,noTelp);
            }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return pegawai;
    }

    public long updatePegawai(Pegawai pegawai){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_PEGAWAI_TOKO, pegawai.getPegawaiToko());
        contentValues.put(Config.COLUMN_PEGAWAI_DEVISI, pegawai.getPegawaiDevisi());
        contentValues.put(Config.COLUMN_PEGAWAI_NAME, pegawai.getPegawaiName());
        contentValues.put(Config.COLUMN_PEGAWAI_ALAMAT, pegawai.getPegawaiAlamat());
        contentValues.put(Config.COLUMN_PEGAWAI_NOTELP, pegawai.getPegawaiNoTelp());

        try {
            rowCount = sqLiteDatabase.update(Config.TABLE_PEGAWAI, contentValues,
                    Config.COLUMN_PEGAWAI_ID + " = ? ",
                    new String[] {String.valueOf(pegawai.getId())});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public long deletePegawaiByID(long id) {
        long deletedRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = sqLiteDatabase.delete(Config.TABLE_PEGAWAI,
                    Config.COLUMN_PEGAWAI_ID + " = ? ",
                    new String[]{ String.valueOf(id)});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deletedRowCount;
    }

    public boolean deleteAllPegawai(){
        boolean deleteStatus = false;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            //for "1" delete() method returns number of deleted rows
            //if you don't want row count just use delete(TABLE_NAME, null, null)
            sqLiteDatabase.delete(Config.TABLE_PEGAWAI, null, null);

            long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_PEGAWAI);

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
