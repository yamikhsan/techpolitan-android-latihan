package com.studio.yami.onion.todolistapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private final static String PREFERENCES = "MAIN ACTIVITY PREFERENCES";
    private final static String TODO_LIST = "TODO LIST";

    private ArrayAdapter<String> adapter;
    private List<String> listText = new ArrayList<>();
    private Set<String> setList;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        addList();

        setList = preferences.getStringSet(TODO_LIST, setList);
        listText.addAll(setList);

        createListView();

    }

    // Buat ListView
    private void createListView(){

        ListView listItem = findViewById(R.id.list_item);
        adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, android.R.id.text1, listText
        );

        listItem.setAdapter(adapter);
        listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapter.getItem(i);
                optionDialog(value);
            }
        });
    }

    // Floating Button
    private void addList(){
        FloatingActionButton btn = findViewById(R.id.floating_add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDialog();
            }
        });
    }

    // AlertDialog Tambah Data
    private void addDialog(){

        @SuppressLint("InflateParams")
        final View view = this.getLayoutInflater().inflate(R.layout.dialog_add, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create item");
        builder.setView(view);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                EditText text = view.findViewById(R.id.input_list);
                String strText = text.getText().toString();
                if(strText.equals("")){
                    text.setError("Required");
                } else {
                    listText.add(strText);
                    adapter.notifyDataSetChanged();
                    savePreference();
                }
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    // AlertDialog Hapus Data
    private void deleteDialog(final String s){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(Html.fromHtml("Do you want to remove \"<i><b>" + s + "<i></b>\""));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listText.remove(s);
                adapter.notifyDataSetChanged();
                savePreference();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    // AlertDialog Update
    private void updateDialog(final String s){

        @SuppressLint("InflateParams")
        final View view = this.getLayoutInflater().inflate(R.layout.dialog_add, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update item");
        builder.setView(view);
        final EditText text = view.findViewById(R.id.input_list);
        text.setText(s);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String getText = text.getText().toString();
                if(getText.equals("")){
                    text.setError("Required");
                } else {
                    listText.remove(s);
                    listText.add(getText);
                    adapter.notifyDataSetChanged();
                    savePreference();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    // AlertDialog Option
    private void optionDialog(final String s){

        String[] opt = {"Update", "Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(opt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0:
                        updateDialog(s);
                        break;
                    case 1:
                        deleteDialog(s);
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void savePreference(){
        setList = new HashSet<>(listText);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(TODO_LIST, setList);
        editor.apply();
    }

}
