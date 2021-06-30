package com.example.postpc_noamk_ex8.models;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Database {
    private static Database instance;
    private final Map<String, CalculationRootsNumber> calculations = new HashMap<>();
    private final MutableLiveData<List<CalculationRootsNumber>> mutableLiveData = new MutableLiveData<>();
    private final Map<String, MutableLiveData<Integer>> calculationsInProgress = new HashMap<>();


    // Constructor
    private Database() {
        mutableLiveData.setValue(new ArrayList<>());
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // add and delete
    public void delete(String calculationId) {

        if (!calculations.containsKey(calculationId)) {
            Log.d(Database.class.toString(), String.format("Calculation: %s doesn't exist", calculationId));
            return;
        }
        CalculationRootsNumber deleted = calculations.remove(calculationId);
        calculationsInProgress.remove(calculationId);
        Log.d("Database", String.format("Calculation id %s (number : %d) was deleted from db", deleted.getId(), deleted.getNumber()));
    }

    public void add(CalculationRootsNumber calculation) {
        if (calculation == null || calculation.getId() == null) {
            Log.d(Database.class.toString(), "Error while trying to add null");
            return;
        }
        calculations.put(calculation.getId(), calculation);
        calculationsInProgress.put(calculation.getId(), new MutableLiveData<>(0));
        Log.d(Database.class.toString(), String.format("Calculation: (id:%s,number:%d) was added successfully", calculation.getId(), calculation.getNumber()));
    }

    public void edit(CalculationRootsNumber calculation) {
        if (calculation == null || calculation.getId() == null) {
            Log.d(Database.class.toString(), "Error while trying to edit");
            return;
        }
        System.out.println(calculation);
        calculations.put(calculation.getId(), calculation);
        Log.d(Database.class.toString(), String.format("Calculation: (%s) edited successfully", calculation.getNumber()));
    }


    // edit
    public void editProgressStatus(String calculationId, long progress) {
        if (!calculationsInProgress.containsKey(calculationId)) {
            Log.e(Database.class.toString(), String.format("Calculation: %s doesn't exist", calculationId));
            return;
        }
        Log.d("DB/editProgressStatus" , "progress: " + progress);
        Objects.requireNonNull(calculationsInProgress.get(calculationId)).setValue((int) progress);
    }




    // getters
    public CalculationRootsNumber getCalculation(String calulationId) {
        if (!calculations.containsKey(calulationId)) {
            Log.d(Database.class.toString(), "calculatio nid doesn't exist");
            return null;
        }
        return calculations.get(calulationId);
    }

    public LiveData<Integer> getCalculationsLiveDataInProgress(String calculcationId) {
        if (!calculationsInProgress.containsKey(calculcationId)) {
            Log.e(Database.class.toString(), "livedata in progress doesn't exist");
            return null;
        }
        return calculationsInProgress.get(calculcationId);
    }

    public LiveData<List<CalculationRootsNumber>> getCalculationsLiveData() {
        return mutableLiveData;
    }

    public List<CalculationRootsNumber> getAllCalculations() {
        return new ArrayList<>(calculations.values());
    }

}
