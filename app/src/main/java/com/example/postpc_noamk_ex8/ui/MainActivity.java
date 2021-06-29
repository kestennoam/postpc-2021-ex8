package com.example.postpc_noamk_ex8.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.postpc_noamk_ex8.CalculationRootsAdapter;
import com.example.postpc_noamk_ex8.R;
import com.example.postpc_noamk_ex8.models.Database;

public class MainActivity extends AppCompatActivity {

    // data members - logic
    private Database db;
    // data members - ui
    private EditText editText;
    private ImageButton fab;
    private WorkManager workManager;
    private CalculationRootsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Main", "onCreate: Running");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Main", "onCreate: done set xml");
        // set data members
        db = Database.getInstance();
        adapter = new CalculationRootsAdapter(db);






    }
}