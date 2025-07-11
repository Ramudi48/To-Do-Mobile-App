package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.Todo;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private List<Todo> todoList = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(Todo todo);
        void onDeleteClick(Todo todo);
        void onCheckboxToggle(Todo todo, boolean isChecked);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setTodos(List<Todo> todos) {
        this.todoList = todos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.title.setText(todo.getTitle());
        holder.date.setText("Due: " + todo.getDueDate());
        holder.completed.setChecked(todo.isCompleted);

        // Edit on item click
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(todo);
        });

        // Delete button
        holder.deleteBtn.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(todo);
        });

        // Checkbox toggle
        holder.completed.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) listener.onCheckboxToggle(todo, isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    static class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView title, date;
        CheckBox completed;
        ImageButton deleteBtn;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            date = itemView.findViewById(R.id.textViewDueDate);
            completed = itemView.findViewById(R.id.checkBoxCompleted);
            deleteBtn = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
