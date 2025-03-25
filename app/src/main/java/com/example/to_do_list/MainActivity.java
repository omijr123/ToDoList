package com.example.to_do_list;  // Ensure this matches your actual package name

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editTextTask;
    Button buttonSave;
    ListView listViewRemaining, listViewCompleted;
    CheckBox checkBox;

    ArrayList<String> remainingTasks = new ArrayList<>();
    ArrayList<String> completedTasks = new ArrayList<>();
    ArrayAdapter<String> remainingAdapter;
    ArrayAdapter<String> completedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Ensure activity_main.xml exists in res/layout

        editTextTask = findViewById(R.id.editTextTask);
        buttonSave = findViewById(R.id.buttonSave);
        listViewRemaining = findViewById(R.id.listViewRemaining);
        listViewCompleted = findViewById(R.id.listViewCompleted);
        checkBox = findViewById(R.id.checkBox);

        // Set up the adapters
        remainingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, remainingTasks);
        completedAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, completedTasks);

        listViewRemaining.setAdapter(remainingAdapter);
        listViewCompleted.setAdapter(completedAdapter);

        // Save button click event
        buttonSave.setOnClickListener(v -> {
            String task = editTextTask.getText().toString().trim();
            if (!task.isEmpty()) {
                remainingTasks.add(task);
                remainingAdapter.notifyDataSetChanged();
                editTextTask.setText(""); // Clear input field
                Toast.makeText(MainActivity.this, "Task Added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Enter a task", Toast.LENGTH_SHORT).show();
            }
        });

        // Move task to completed list on item click
        listViewRemaining.setOnItemClickListener((parent, view, position, id) -> {
            String task = remainingTasks.get(position);
            remainingTasks.remove(position);
            completedTasks.add(task);
            remainingAdapter.notifyDataSetChanged();
            completedAdapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Task Completed", Toast.LENGTH_SHORT).show();
        });

        // Move task back to remaining list on item click
        listViewCompleted.setOnItemClickListener((parent, view, position, id) -> {
            String task = completedTasks.get(position);
            completedTasks.remove(position);
            remainingTasks.add(task);
            remainingAdapter.notifyDataSetChanged();
            completedAdapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Task Moved Back", Toast.LENGTH_SHORT).show();
        });

        // Checkbox to clear all completed tasks
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                completedTasks.clear();
                completedAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "All Completed Tasks Cleared", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
