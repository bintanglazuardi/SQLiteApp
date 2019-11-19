package com.kuliah.komsi.sqliteapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "student_db";
    private static final int DATABASE_VERSION = 1;
    //nama tabel untuk mendefinisikan kolom"nya
    private static final String TABLE_STUDENTS = "students";
    //kolomnya isi apa aja
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ALAMAT = "alamat";
    //buat konstanta query
    private static final String CREATE_TABLE_STUDENS =
            "CREATE TABLE " + TABLE_STUDENTS
                    + "("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_NAME+ " TEXT, "
                    + KEY_ALAMAT+" TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_STUDENS); //ketika pertamakali dipanggil akan membuat table students
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //untuk menangani apabila tabel pada database berubah, dengan drop table dulu baru dibuat tabel baru
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '"
        +TABLE_STUDENTS+"'");
        onCreate(sqLiteDatabase);
    }

    //long berisi jumlah yang ditambahkan, dipanggil di acttivity input
    public long addStudent(String nama, String alamat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, nama);
        values.put(KEY_ALAMAT, alamat);
        long insert = db.insert(TABLE_STUDENTS, null, values); //insert ke table students
        return insert;
    }
    //map untuk mengganti objek karena data cuma sedikit
    public ArrayList<Map<String, String>> getAllStrudents(){
        ArrayList<Map<String, String>> arrayList
                = new ArrayList<>();
        String nama = "";
        String alamat = "";
        int id = 0;
        //definisikan select query
        String selectQuery = "SELECT * FROM "+ TABLE_STUDENTS;
        SQLiteDatabase db = this.getWritableDatabase();
        //membaca kembalian dengan cursor, dibaca per row
        Cursor c = db.rawQuery(selectQuery, null);
        //agar mulai dari 0 dan mengecek apakah ada datanya
        if (c.moveToFirst()){
            //fungsi untuk insert dan retrieve
             do {
                //ambil data per kolom
                 id = c.getInt(c.getColumnIndex(KEY_ID));
                 nama = c.getString(c.getColumnIndex(KEY_NAME));
                 alamat = c.getString(c.getColumnIndex(KEY_ALAMAT));
                 Map<String, String> itemMap = new HashMap<>(); //untuk mengganti objek
                 itemMap.put(KEY_ID, id+""); //ubah indeks menjadi string
                 itemMap.put(KEY_NAME, nama);
                 itemMap.put(KEY_ALAMAT, alamat);
                 //masukkan ke array list
                 arrayList.add(itemMap);

             }while (c.moveToNext());
        }
        return arrayList;
    }

    /*public void open(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure,You wanted to make decision");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(MainActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }*/


    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_STUDENTS
                + " WHERE " + KEY_ID+"='"+id+"'";
        db.execSQL(deleteQuery);
        db.close();
    }

}
