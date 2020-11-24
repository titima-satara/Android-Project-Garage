package com.example.garage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.garage.db.AppDatabase;
import com.example.garage.model.CarDetails;
import com.example.garage.util.AppExecutors;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent i = getIntent();
        String carRegistration = i.getExtras().getString("carRegistration");

        EditText carRegistration_editText = (EditText) findViewById(R.id.carRegistration_editTextTextPersonName);
        EditText carOwner_editText = (EditText) findViewById(R.id.carOwner_editTextTextPersonName);
        EditText username_editText = (EditText) findViewById(R.id.username_editTextTextPersonName);
        EditText iDcard_editText = (EditText) findViewById(R.id.iDcard_editTextTextPersonName);
        EditText phoneNumber_editText = (EditText) findViewById(R.id.phoneNumber_editTextTextPersonName);
        EditText carBrand_editText = (EditText) findViewById(R.id.carBrand_editTextTextPersonName);
        EditText carModel_editText = (EditText) findViewById(R.id.carModel_editTextTextPersonName);

        // : ไม่ให้แก้ไขค่าใน editText
        carRegistration_editText.setEnabled(false);
        carOwner_editText.setEnabled(false);
        username_editText.setEnabled(false);
        iDcard_editText.setEnabled(false);
        phoneNumber_editText.setEnabled(false);
        carBrand_editText.setEnabled(false);
        carModel_editText.setEnabled(false);

        final AppExecutors executors = new AppExecutors();
        final CarDetails[] carDetails = new CarDetails[1];
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getInstance(Details.this);
                carDetails[0] = db.garageDao().getCarDetails(carRegistration);
                // : Set editText
                carRegistration_editText.setText(carDetails[0].carRegistration);
                carOwner_editText.setText(carDetails[0].carOwner);
                username_editText.setText(carDetails[0].username);
                iDcard_editText.setText(carDetails[0].iDcard);
                phoneNumber_editText.setText(carDetails[0].phoneNumber);
                carBrand_editText.setText(carDetails[0].carBrand);
                carModel_editText.setText(carDetails[0].carModel);
            }
        });

        Button ServiceDetails_button = (Button) findViewById(R.id.ServiceDetails_button);
        ServiceDetails_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Details.this,ServiceDetails.class);
                i.putExtra("carRegistration",carRegistration);
                i.putExtra("carRegistrationCarDetails",carDetails[0].carRegistration);
                i.putExtra("carOwner",carDetails[0].carOwner);
                i.putExtra("username",carDetails[0].username);
                i.putExtra("iDcard",carDetails[0].iDcard);
                i.putExtra("phoneNumber",carDetails[0].phoneNumber);
                i.putExtra("carBrand",carDetails[0].carBrand);
                i.putExtra("carModel",carDetails[0].carModel);
                startActivity(i);
            }
        });

        Button home_button = (Button) findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Details.this,MainActivity.class);
                startActivity(i);
            }
        });

        Button Edit_button = (Button) findViewById(R.id.Edit_button);
        Edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Details.this,Edit.class);
                i.putExtra("carRegistration",carDetails[0].carRegistration);
                i.putExtra("carOwner",carDetails[0].carOwner);
                i.putExtra("username",carDetails[0].username);
                i.putExtra("iDcard",carDetails[0].iDcard);
                i.putExtra("phoneNumber",carDetails[0].phoneNumber);
                i.putExtra("carBrand",carDetails[0].carBrand);
                i.putExtra("carModel",carDetails[0].carModel);
                startActivity(i);
            }
        });

        Button ServiceRecord_button = (Button) findViewById(R.id.ServiceRecord_button);
        ServiceRecord_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Details.this,ServiceRecord.class);
                i.putExtra("carRegistration",carDetails[0].carRegistration);
                i.putExtra("carOwner",carDetails[0].carOwner);
                i.putExtra("username",carDetails[0].username);
                i.putExtra("iDcard",carDetails[0].iDcard);
                i.putExtra("phoneNumber",carDetails[0].phoneNumber);
                i.putExtra("carBrand",carDetails[0].carBrand);
                i.putExtra("carModel",carDetails[0].carModel);
                startActivity(i);
            }
        });
    }
}