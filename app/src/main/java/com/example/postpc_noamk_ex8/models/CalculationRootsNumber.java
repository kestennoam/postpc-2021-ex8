package com.example.postpc_noamk_ex8.models;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class CalculationRootsNumber implements Serializable {
    private final String id;
    private long number;
    private boolean isPrime;
    private long root1;
    private long root2;
    private boolean isDone;
    private String workId;

    public CalculationRootsNumber(long number) {
        this.id = UUID.randomUUID().toString();
        this.number = number;
    }

    @Override
    public String toString() {
        return "CalculationRootsNumber{" +
                "id='" + id + '\'' +
                ", number=" + number +
                ", isPrime=" + isPrime +
                ", root1=" + root1 +
                ", root2=" + root2 +
                ", isDone=" + isDone +
                ", workId='" + workId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculationRootsNumber that = (CalculationRootsNumber) o;
        return id.equals(((CalculationRootsNumber) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, isPrime, root1, root2, isDone);
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

    public void setToPrime(){
        isDone = true;
        isPrime = true;
        root1 = number;
        root2 = 1;

    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }
}
