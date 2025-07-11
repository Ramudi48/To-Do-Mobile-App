package com.example.myapplication.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.database.Todo;
import com.example.myapplication.database.TodoDatabase;

import java.util.Calendar;
import java.util.concurrent.Executors;

public class AddEditTodoActivity extends AppCompatActivity {
    EditText titleET, descET, dateET, tagET;
    CheckBox completedCB;
    Button saveBtn;
    TodoDatabase db;
    int todoId = -1;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_todo);

        titleET = findViewById(R.id.editTextTitle);
        descET = findViewById(R.id.editTextDescription);
        dateET = findViewById(R.id.editTextDate);
        tagET = findViewById(R.id.editTextTag);
        completedCB = findViewById(R.id.checkBoxCompleted);
        saveBtn = findViewById(R.id.buttonSave);
        db = TodoDatabase.getInstance(this);

        calendar = Calendar.getInstance();
        dateET.setInputType(InputType.TYPE_NULL);

        // Show DatePicker dialog on click
        dateET.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddEditTodoActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        // Month is zero-based, add 1
                        selectedMonth += 1;
                        String date = selectedDay + "/" + selectedMonth + "/" + selectedYear;
                        dateET.setText(date);
                    }, year, month, day);

            datePickerDialog.show();
        });

        todoId = getIntent().getIntExtra("id", -1);

        if (todoId != -1) {
            titleET.setText(getIntent().getStringExtra("title"));
            descET.setText(getIntent().getStringExtra("desc"));
            dateET.setText(getIntent().getStringExtra("date"));
            tagET.setText(getIntent().getStringExtra("tag"));
            completedCB.setChecked(getIntent().getBooleanExtra("completed", false));
            saveBtn.setText("Update");
        }

        saveBtn.setOnClickListener(v -> {
            String title = titleET.getText().toString().trim();
            android.util.Log.d("AddEditTodo", "Saving title: '" + title + "'");

            Todo todo = new Todo();
            todo.title = title;
            todo.description = descET.getText().toString().trim();
            todo.dueDate = dateET.getText().toString().trim();
            todo.tag = tagET.getText().toString().trim();
            todo.isCompleted = completedCB.isChecked();

            if (todoId != -1) {
                todo.id = todoId;
                Executors.newSingleThreadExecutor().execute(() -> db.todoDao().update(todo));
            } else {
                Executors.newSingleThreadExecutor().execute(() -> db.todoDao().insert(todo));
            }
            finish();
        });
    }
}
