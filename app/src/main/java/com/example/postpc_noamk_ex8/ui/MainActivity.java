package com.example.postpc_noamk_ex8.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.postpc_noamk_ex8.CalculationRootsAdapter;
import com.example.postpc_noamk_ex8.CalculationRootsViewHolder;
import com.example.postpc_noamk_ex8.R;
import com.example.postpc_noamk_ex8.models.CalculationRootsNumber;
import com.example.postpc_noamk_ex8.models.Database;
import com.example.postpc_noamk_ex8.workers.CalculateRootsWorker;
import com.google.gson.Gson;

import java.util.UUID;

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
        workManager = WorkManager.getInstance(this);
        adapter = new CalculationRootsAdapter(db);
        editText = findViewById(R.id.calculation_edit_text);
        fab = findViewById(R.id.add_fab);
        fab.setEnabled(false);


        // set recycler
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        // set text
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fab.setEnabled(s.toString().trim().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // set click to listeners
        adapter.setListenerToClicks(calculation -> {
            db.delete(calculation.getId());
            adapter.setNewCalculations(db.getAllCalculations());
            adapter.notifyDataSetChanged();
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                CalculationRootsNumber calculation = new CalculationRootsNumber(Long.parseLong(editText.getText().toString()));
                db.add(calculation);
                editText.setText("");
                adapter.update();
                fab.setEnabled(false);
                runCalculationWorker(calculation);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void runCalculationWorker(CalculationRootsNumber calculation) {
        Log.d("MainActivity/RunWorker", "Start worker on: : " + calculation);
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(CalculateRootsWorker.class).
                setInputData(new Data.Builder()
                        .putString("number_to_calculate", new Gson().toJson(calculation))
                        .build())
                .build();


        workManager.enqueue(request);
//        LiveData<WorkInfo> workInfoByIdLiveData = workManager.getWorkInfoByIdLiveData(request.getId());
        calculation.setWorkId(request.getId().toString());
        System.out.println("4");

        LiveData<WorkInfo> workInfoByIdLiveData = WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(request.getId());
        workInfoByIdLiveData.observeForever(new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo != null) {
                    WorkInfo.State state = workInfo.getState();
                    long progress = calculation.getNumber();

                    if (state == WorkInfo.State.SUCCEEDED) {
                        db.edit(new Gson().fromJson(workInfo.getOutputData().
                                getString("number_to_calculate"), CalculationRootsNumber.class));
                        adapter.update();
                    } else if (state == WorkInfo.State.RUNNING) {
                        progress = workInfo.getProgress().getLong("progress", -1);
                        Log.d("Main Activity", "progress " + progress + "/" + calculation.getNumber());
                    }
                    progress = (progress != -1) ? progress : 0;
                    db.editProgressStatus(calculation.getId(), progress);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}