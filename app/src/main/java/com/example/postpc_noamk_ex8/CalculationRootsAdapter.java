package com.example.postpc_noamk_ex8;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postpc_noamk_ex8.models.CalculationRootsNumber;
import com.example.postpc_noamk_ex8.models.Database;

import java.util.ArrayList;
import java.util.List;


/**
 * adapter has 2 jobs:
 * 1) paint items
 * 2) listen to clicks on items
 */
public class CalculationRootsAdapter extends RecyclerView.Adapter<CalculationRootsViewHolder> {
    private List<CalculationRootsNumber> numbers = new ArrayList<>();
    private Database db;
    private AdapterClickListener listenerToClicks = null;

    public CalculationRootsAdapter(Database database) {
        this.db = database;
    }

    // to be called by activity when new sandwiches received from DB
    public void setNewCalculations(List<CalculationRootsNumber> newNumber) {
        numbers.clear();
        numbers.addAll(newNumber);
        notifyDataSetChanged();
    }

    public void setListenerToClicks(AdapterClickListener listenerToClicks) {
        this.listenerToClicks = listenerToClicks;
    }

    @NonNull
    @Override
    public CalculationRootsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calculation_roots_item, parent, false);
        return new CalculationRootsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalculationRootsViewHolder holder, int position) {
        if (position < 0 || position >= numbers.size()) {
            Log.e("CalculationRootsAdapter", "invalid pos: " + position);
            return;
        }
        CalculationRootsNumber calculation = numbers.get(position);
        holder.setDoneProgress((int) calculation.getNumber());

        // delete view
        holder.deleteView.setOnClickListener(v -> {
            Log.d("Adapter", "Delete button was clicked.");
            if (listenerToClicks != null) {
                listenerToClicks.onItemClick(calculation);
            } else {
                Log.d("Adapter", "Error with adapter when delete pressed");
            }
        });

        // cancel view
        holder.cancelView.setOnClickListener(v -> {
            Log.d("Adapter", "Cancel button was clicked.");
            if (listenerToClicks != null) {
                listenerToClicks.onItemClick(calculation);
            } else {
                Log.d("Adapter", "Error with adapter when cancel pressed");
            }
        });

        // holder progress
        if (calculation.isDone()) {
            holder.setCalculationInDone(calculation);
        } else {
            holder.setCalculationInProgressMode(calculation);
        }

        Database.getInstance().getCalculationsLiveDataInProgress(calculation.getId()).observeForever(holder::updateProgress);

    }

    @Override
    public int getItemCount() {
        return numbers.size();
    }
}
