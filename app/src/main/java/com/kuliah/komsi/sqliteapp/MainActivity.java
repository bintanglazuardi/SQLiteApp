package com.kuliah.komsi.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ImageButton buttonAdd;
    ListView listView;
    DatabaseHelper databaseHelper;
    ArrayList<Map<String, String>> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        buttonAdd = findViewById(R.id.button_add);
        databaseHelper = new DatabaseHelper(this);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        final int id = Integer.parseInt(
                                arrayList.get(i).get("id"));
                        showConfirm(id);
                        return true;
                    }
                }
        );
    }

    private void showConfirm(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hapus Data");
        builder
                .setMessage("Apakah Anda yakin ingin menghapus data ini ?")
                .setPositiveButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //DatabaseHelper.delete(id);
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadData();
    }

    //method untuk load data
    private void loadData() {
        arrayList = databaseHelper.getAllStrudents();
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, arrayList,
                android.R.layout.simple_list_item_2,    //bawaan dari android
                new String[]{"name", "alamat"},
                new int[]{android.R.id.text1,
                android.R.id.text2});
        listView.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();
    }


}
