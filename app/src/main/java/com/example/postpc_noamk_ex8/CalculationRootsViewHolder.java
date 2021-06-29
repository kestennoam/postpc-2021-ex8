package com.example.postpc_noamk_ex8;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postpc_noamk_ex8.models.CalculationRootsNumber;

public class CalculationRootsViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;
    public ImageView cancelView;
    public ImageView deleteView;
    public ProgressBar progressBar;
    public final View rootView; // public that anyone could talk with it

    public CalculationRootsViewHolder(@NonNull View itemView) {
        super(itemView);
        rootView = itemView;

        // set ui components
        textView = itemView.findViewById(R.id.text_view);
        cancelView = itemView.findViewById(R.id.cancel_view);
        deleteView = itemView.findViewById(R.id.delete_view);
        progressBar = itemView.findViewById(R.id.progress_circular);


    }

    public void updateProgress(int value) {
        progressBar.setProgress(value);
    }

    public void setDoneProgress(int value) {
        progressBar.setMax(value);
    }

    private void setDeleteOrCancel(boolean isInProgress) {
        int deleteVisibility = (isInProgress) ? View.INVISIBLE : View.VISIBLE;
        int cancelVisibility = (isInProgress) ? View.VISIBLE : View.INVISIBLE;
        deleteView.setVisibility(deleteVisibility);
        cancelView.setVisibility(cancelVisibility);
    }

    @SuppressLint("DefaultLocale")
    public void setCalculationInProgressMode(CalculationRootsNumber calculation) {
        textView.setText(String.format("Calculating Roots for %d", calculation.getNumber()));
        setDeleteOrCancel(true);
    }

    public void setCalculationInDone(CalculationRootsNumber calculation) {
        setDeleteOrCancel(false);
        @SuppressLint("DefaultLocale") String text = (!calculation.isPrime()) ?
                String.format("Number %d has roots: root1: %d, root2: %d", calculation.getNumber(), calculation.getRoot1(), calculation.getRoot2()) :
                String.format("Number %d is prime", calculation.getNumber());
    }
}
