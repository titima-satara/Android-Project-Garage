package com.example.garage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.garage.db.AppDatabase;
import com.example.garage.util.AppExecutors;

public class Edit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent i = getIntent();
        String carRegistration = i.getExtras().getString("carRegistration");
        String carOwner = i.getExtras().getString("carOwner");
        String username = i.getExtras().getString("username");
        String iDcard = i.getExtras().getString("iDcard");
        String phoneNumber = i.getExtras().getString("phoneNumber");
        String carBrand = i.getExtras().getString("carBrand");
        String carModel = i.getExtras().getString("carModel");

        EditText carRegistration_editText = (EditText) findViewById(R.id.carRegistration_editTextTextPersonName);
        EditText carOwner_editText = (EditText) findViewById(R.id.carOwner_editTextTextPersonName);
        EditText username_editText = (EditText) findViewById(R.id.username_editTextTextPersonName);
        EditText iDcard_editText = (EditText) findViewById(R.id.iDcard_editTextTextPersonName);
        EditText phoneNumber_editText = (EditText) findViewById(R.id.phoneNumber_editTextTextPersonName);
        EditText carBrand_editText = (EditText) findViewById(R.id.carBrand_editTextTextPersonName);
        EditText carModel_editText = (EditText) findViewById(R.id.carModel_editTextTextPersonName);

        // : Set editText
        carRegistration_editText.setText(carRegistration);
        carOwner_editText.setText(carOwner);
        username_editText.setText(username);
        iDcard_editText.setText(iDcard);
        phoneNumber_editText.setText(phoneNumber);
        carBrand_editText.setText(carBrand);
        carModel_editText.setText(carModel);

        Button back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button Edit_buttonE = (Button) findViewById(R.id.add_buttonSR);
        Edit_buttonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Edit.this); // : ยืนยันการแก้ไข
                dialog.setTitle(R.string.confirm_correction);
                dialog.setMessage(R.string.have_you);
                dialog.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final AppExecutors executors = new AppExecutors();
                        executors.diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                AppDatabase db = AppDatabase.getInstance(Edit.this);
                                db.garageDao().updateCarDetails(carRegistration,  // : แก้ไขข้อมูล
                                        carRegistration_editText.getText().toString(),
                                        carOwner_editText.getText().toString(),
                                        username_editText.getText().toString(),
                                        iDcard_editText.getText().toString(),
                                        phoneNumber_editText.getText().toString(),
                                        carBrand_editText.getText().toString(),
                                        carModel_editText.getText().toString());
                            }
                        });
                        Intent i = new Intent(Edit.this,Details.class);
                        i.putExtra("carRegistration",carRegistration_editText.getText().toString());
                        startActivity(i);
                    }
                });
                dialog.setNegativeButton(R.string.no,null);
                dialog.show();
            }
        });

    }
}