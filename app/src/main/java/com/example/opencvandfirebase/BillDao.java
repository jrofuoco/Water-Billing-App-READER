package com.example.opencvandfirebase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BillDao {
    @Insert
    void insert(Bill bill);

    @Query("SELECT * FROM Bill WHERE isSynced = 0")
    List<Bill> getAllUnsyncedBills();

    @Update
    void update(Bill bill);

    @Query("DELETE FROM Bill WHERE id = :id")
    void deleteById(int id);
}
