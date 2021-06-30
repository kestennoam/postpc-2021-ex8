package com.example.postpc_noamk_ex8;

import com.example.postpc_noamk_ex8.models.CalculationRootsNumber;
import com.example.postpc_noamk_ex8.models.Database;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestsEx8 {

    @Test
    public void when_addingItem_then_ListShouldHasItem() {
        // setup
        Database db = Database.getInstance();
        assertEquals(0, db.getAllCalculations().size());

        // test
        db.add(new CalculationRootsNumber(10));

        // verify
        assertEquals(1, db.getAllCalculations().size());
    }

    // couldn't succeed handle more tests with roboelectrics... sorry


}