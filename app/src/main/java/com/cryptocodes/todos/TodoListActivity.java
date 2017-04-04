package com.cryptocodes.todos;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cryptocodes.todos.data.DatabaseHelper;
import com.cryptocodes.todos.data.TodosContract;

public class TodoListActivity extends AppCompatActivity {

    String[] itemname ={
            "Get theatre tickets",
            "Order pizza for tonight",
            "Buy groceries",
            "Running session at 19.30",
            "Call Uncle Sam"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ListView lv=(ListView) findViewById(R.id.lvTodos);
//adds the custom layout
        lv.setAdapter(new ArrayAdapter<>(this, R.layout.todo_list_item,
                R.id.tvNote, itemname));
//adds the click event to the listView, reading the content
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Intent intent = new Intent(TodoListActivity.this, TodoActivity.class);
                String content= (String) lv.getItemAtPosition(pos);
                intent.putExtra("Content", content);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void CreateTodo() {
        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        // Adding a dummy to-do to the DB
        String query = "INSERT INTO todos ("
                + TodosContract.TodosEntry.COLUMN_TEXT + ", "
                + TodosContract.TodosEntry.COLUMN_CATEGORY + ", "
                + TodosContract.TodosEntry.COLUMN_CREATED + ", "
                + TodosContract.TodosEntry.COLUMN_EXPIRED + ", "
                + TodosContract.TodosEntry.COLUMN_DONE +")"
                + " VALUES (\"Go to the gym\", 1, \"2016-01-01\", \"\", 0)";

        db.execSQL(query);
    }
}