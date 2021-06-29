package com.example.postpc_noamk_ex8;

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
    public void setNewSandwiches(List<CalculationRootsNumber> newNumber) {
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
        // 1. inflate xml file to create view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext()); // converting xml to views
        // todo write xml
//        View view = inflater.inflate(R.id.item_sandwich, parent, false);
        // 2. create a new view holder and pass the view to the constructor
//        return new SandwichViewHolder(view);

        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull CalculationRootsViewHolder holder, int position) {
        // present id of sandwich
        CalculationRootsNumber currentSandwich = numbers.get(position);
        holder.textView.setText(currentSandwich.toString());

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 2 options: 1)
                // 1) delete when click
//                database.delete(currentSandwich.toString()); // todo change from currentSandwich.tostring
                // 2)
                if (listenerToClicks != null) {
                    listenerToClicks.onItemClick(currentSandwich);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return numbers.size();
    }
}
