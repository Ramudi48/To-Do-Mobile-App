package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.TodoAdapter;
import com.example.myapplication.database.Todo;
import com.example.myapplication.database.TodoDatabase;
import com.example.myapplication.utils.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TodoAdapter adapter;
    FloatingActionButton fab;
    TodoDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check login
        SharedPrefManager prefManager = new SharedPrefManager(this);
        if (!prefManager.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        // Initialize views
        db = TodoDatabase.getInstance(this);
        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fabAddTodo);
        adapter = new TodoAdapter();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Observe LiveData from Room — this automatically updates the list on DB changes
        db.todoDao().getAll().observe(this, todos -> adapter.setTodos(todos));

        // Floating button to add new to-do
        fab.setOnClickListener(v -> {
            Intent i = new Intent(this, AddEditTodoActivity.class);
            startActivity(i);
        });

        //Set adapter listener for edit, delete, and checkbox toggle
        adapter.setOnItemClickListener(new TodoAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(Todo todo) {
                // Open AddEditTodoActivity with todo data for editing
                Intent intent = new Intent(MainActivity.this, AddEditTodoActivity.class);
                intent.putExtra("id", todo.id);
                intent.putExtra("title", todo.title);
                intent.putExtra("desc", todo.description);
                intent.putExtra("date", todo.dueDate);
                intent.putExtra("tag", todo.tag);
                intent.putExtra("completed", todo.isCompleted);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Todo todo) {
                // Show confirmation dialog before deleting
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this to-do?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            Executors.newSingleThreadExecutor().execute(() -> {
                                db.todoDao().delete(todo);
                            });
                        })
                        .setNegativeButton("No", null)
                        .show();
            }

            @Override
            public void onCheckboxToggle(Todo todo, boolean isChecked) {
                // Update completion status in DB
                todo.isCompleted = isChecked;
                Executors.newSingleThreadExecutor().execute(() -> {
                    db.todoDao().update(todo);
                });
            }
        });


        // Logout button
        Button logoutBtn = findViewById(R.id.btnLogout);
        logoutBtn.setOnClickListener(v -> {
            prefManager.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
