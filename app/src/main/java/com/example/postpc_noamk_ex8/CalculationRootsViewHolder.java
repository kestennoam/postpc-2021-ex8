package com.example.postpc_noamk_ex8;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalculationRootsViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;
    public final View rootView; // public that anyone could talk with it

    public CalculationRootsViewHolder(@NonNull View itemView) {
        super(itemView);
        rootView = itemView;
//        this.textView = itemView.findViewById() //todo complete xml
    }
}
