package com.example.activity.chapter15_menu_javacode;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends ListActivity {

    private static final String[] items = { "lorem", "ipsum", "dolor", "sit",
            "amet", "consectetuer", "adipiscing", "elit", "morbi", "vel",
            "ligula", "vitae", "arcu", "aliquet", "mollis", "etiam", "vel",
            "erat", "placerat", "ante", "porttitor", "sodales", "pellentesque",
            "augue", "purus" };
    public static final int MENU_ADD = Menu.FIRST + 1;
    public static final int MENU_RESET = Menu.FIRST + 2;
    public static final int MENU_CAP = Menu.FIRST + 3;
    public static final int MENU_REMOVE = Menu.FIRST + 4;
    ArrayList<String> words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAdapter();
        registerForContextMenu(getListView());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // icon doesn't work with higher version than 3.0
        menu.add(Menu.NONE, MENU_ADD, Menu.NONE, "Add").setIcon(R.drawable.rsz_icon_add);
        menu.add(Menu.NONE, MENU_RESET, Menu.NONE, "Reset").setIcon(R.drawable.rsz_icon_reset);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case MENU_ADD:
                add();
                return true;
            case MENU_RESET:
                initAdapter();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, MENU_CAP, Menu.NONE, "Capitalize");
        menu.add(Menu.NONE, MENU_REMOVE, Menu.NONE, "Remove");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
        int id = item.getItemId();
        switch (id) {
            case MENU_REMOVE:
                adapter.remove(words.get(info.position));
                return true;
            case MENU_CAP:
                String word = words.get(info.position);
                word = word.toUpperCase();
                adapter.remove(words.get(info.position));
                adapter.insert(word, info.position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void initAdapter() {
        words = new ArrayList<>();
        for (String s : items) {
            words.add(s);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, words);
        setListAdapter(adapter);
    }

    private void add(){
        final View addView = getLayoutInflater().inflate(R.layout.add, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Add a word").setView(addView).
                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
                        EditText edtWord = (EditText) addView.findViewById(R.id.edtWord);
                        adapter.add(edtWord.getText().toString());
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }
}
