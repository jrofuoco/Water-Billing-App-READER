package com.example.opencvandfirebase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bill {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String connectionId;
    private String billData;
    private boolean isSynced;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getConnectionId() { return connectionId; }
    public void setConnectionId(String connectionId) { this.connectionId = connectionId; }
    public String getBillData() { return billData; }
    public void setBillData(String billData) { this.billData = billData; }
    public boolean isSynced() { return isSynced; }
    public void setSynced(boolean synced) { isSynced = synced; }
}

