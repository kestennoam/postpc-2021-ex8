package com.example.postpc_noamk_ex8.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.postpc_noamk_ex8.models.CalculationRootsNumber;

public class CalculateRootsWorker extends Worker {
    private static final long DEFAULT_NUMBER = 0;

    public CalculateRootsWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        final long numberToCalculateRootsFor = getInputData().getLong("number_to_calculate", 0);
        CalculationRootsNumber number = new CalculationRootsNumber(numberToCalculateRootsFor);

        // non-positive
        if (numberToCalculateRootsFor <= 0) {
            Log.e("CalculateRootsService", "can't calculate roots for non-positive input" + numberToCalculateRootsFor);
            //todo complete
        }

        // 2
        if (numberToCalculateRootsFor == 2) {
            //todo complete
        }

        // even
        else if (numberToCalculateRootsFor % 2 == 0) {
            //todo complete
        }

        // calculating roots (enough until square without even numbers)
        for (long i = 3; i <= Math.sqrt(numberToCalculateRootsFor); i += 2) {
            // check if dividing number
            if (numberToCalculateRootsFor % i == 0) {
                System.out.println("has roots");
                // todo complete

            }
        }

        // prime number
        //todo complete


        return Result.success(
                new Data.Builder()
                        .putLong("original_number", number.getNumber())
                        .putBoolean("is_prime", number.isPrime())
                        .putLong("root1", number.getRoot1())
                        .putLong("root2", number.getRoot2())
                        .build()
        );
    }

}
