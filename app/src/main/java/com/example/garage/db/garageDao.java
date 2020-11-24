package com.example.garage.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.garage.model.CarDetails;

@Dao
public interface garageDao {

    @Insert
    void addCarDetails(CarDetails... CarDetails);

    @Query("SELECT carRegistration FROM CarDetails WHERE carRegistration LIKE :carRegistration")
    String getCarRegistration(String carRegistration);

    @Query("SELECT * FROM CarDetails WHERE carRegistration LIKE :carRegistration")
    CarDetails getCarDetails(String carRegistration);

    @Query("SELECT * FROM CarDetails WHERE carRegistration LIKE :carRegistration")
    CarDetails[] getCarService(String carRegistration);

    @Query("UPDATE CarDetails SET carRegistration = :carRegistration," +
            "carOwner = :carOwner," +
            "username = :username," +
            "iDcard = :iDcard," +
            "phoneNumber = :phoneNumber," +
            "carBrand = :carBrand," +
            "carModel = :carModel " +
            "WHERE carRegistration = :carRegistrationOld")
    void updateCarDetails( String carRegistrationOld, String carRegistration, String carOwner, String username, String iDcard, String phoneNumber, String carBrand, String carModel);

}
