package com.example.postpc_noamk_ex8.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.postpc_noamk_ex8.models.CalculationRootsNumber;
import com.google.gson.Gson;

public class CalculateRootsWorker extends Worker {
    private static final long DEFAULT_NUMBER = 0;

    public CalculateRootsWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("CalculationRoots", "Start work ");

        String calculationId = getInputData().getString("number_to_calculate");

        if (calculationId == null || calculationId.equals("")) {
            Log.e("CalculationRoots", "error with calculation id ");
            return Result.failure();
        }

        CalculationRootsNumber number = new Gson().fromJson(calculationId, CalculationRootsNumber.class);


        // non-positive
        if (number.getNumber() <= 0) {
            Log.e("CalculateRootsService", "can't calculate roots for non-positive input" + number);
            return Result.failure();
        }

        // 2
        if (number.getNumber() == 2) {
            number.setToPrime();
            Log.d("CalculateRootsService", "number is 2");
        }

        // even
        else if (number.getNumber() % 2 == 0) {
            number.setToNotPrime(number.getNumber() / 2, 2);
            Log.d("CalculateRootsService", "number is even, number: " + number);
        } else {
            // calculating roots (enough until square without even numbers)
            for (long i = 3; i <= Math.sqrt(number.getNumber()); i += 2) {
                setProgressAsync(new Data.Builder()
                        .putLong("progress", i * i)
                        .build());
                // check if dividing number
                if (number.getNumber() % i == 0) {

                    number.setToNotPrime(number.getNumber() / i, i);
                    Log.d("CalculateRootsService", "number has roots, number: " + number +
                            "  root1: " + (number.getNumber() / i) + " root2: " + i);
                    break;
                }
            }

            Log.d("CalculateRootsService", "Check " + number);
            // prime number
            if (number.getRoot1() == 0) {
                number.setToPrime();
                Log.d("CalculateRootsService", "number is prime, number: " + number);
            }
        }

        return Result.success(new Data.Builder()
                .putString("number_to_calculate", new Gson().toJson(number))
                .build());
    }

}
