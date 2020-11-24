package com.example.garage.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "CarDetails") // ควรทำ 2 ตาราง
public class CarDetails {
    @PrimaryKey(autoGenerate = true)
    public final int id;

    @ColumnInfo(name = "carRegistration")
    public final String carRegistration;

    @ColumnInfo(name = "carOwner")
    public final String carOwner;

    @ColumnInfo(name = "username")
    public final String username;

    @ColumnInfo(name = "iDcard")
    public final String iDcard;

    @ColumnInfo(name = "phoneNumber")
    public final String phoneNumber;

    @ColumnInfo(name = "carBrand")
    public final String carBrand;

    @ColumnInfo(name = "carModel")
    public final String carModel;

    @ColumnInfo(name = "dateOfService")
    public final String dateOfService;

    @ColumnInfo(name = "serviceReceived")
    public final String serviceReceived;

    public CarDetails(int id, String carRegistration, String carOwner, String username, String iDcard, String phoneNumber, String carBrand, String carModel, String dateOfService, String serviceReceived) {
        this.id = id;
        this.carRegistration = carRegistration;
        this.carOwner = carOwner;
        this.username = username;
        this.iDcard = iDcard;
        this.phoneNumber = phoneNumber;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.dateOfService = dateOfService;
        this.serviceReceived = serviceReceived;
    }
}
