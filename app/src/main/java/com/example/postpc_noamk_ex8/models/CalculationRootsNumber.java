package com.example.postpc_noamk_ex8.models;

import java.io.Serializable;
import java.util.UUID;

public class CalculationRootsNumber implements Serializable {
    private final String id;
    private long number;
    private boolean isPrime;
    private long root1;
    private long root2;
    private boolean isDone;

    public CalculationRootsNumber(long number) {
        this.id = UUID.randomUUID().toString();
        this.number = number;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public boolean isPrime() {
        return isPrime;
    }

    public void setPrime(boolean prime) {
        isPrime = prime;
    }

    public long getRoot1() {
        return root1;
    }

    public void setRoot1(long root1) {
        this.root1 = root1;
    }

    public long getRoot2() {
        return root2;
    }

    public void setRoot2(long root2) {
        this.root2 = root2;
    }

    public String getId() {
        return id;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public void setToNotPrime(long root1, long root2) {
        isDone = true;
        isPrime = false;
        this.root1 = root1;
        this.root2 = root2;
    }
}
