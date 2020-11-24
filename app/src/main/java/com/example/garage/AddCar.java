package com.example.garage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.garage.db.AppDatabase;
import com.example.garage.model.CarDetails;
import com.example.garage.util.AppExecutors;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddCar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        Intent i = getIntent();
        String carRegistration = i.getExtras().getString("carRegistration");

        EditText carRegistration_editText = (EditText) findViewById(R.id.carRegistration_editTextTextPersonName);
        EditText carOwner_editText = (EditText) findViewById(R.id.carOwner_editTextTextPersonName);
        EditText username_editText = (EditText) findViewById(R.id.username_editTextTextPersonName);
        EditText iDcard_editText = (EditText) findViewById(R.id.iDcard_editTextTextPersonName);
        EditText phoneNumber_editText = (EditText) findViewById(R.id.phoneNumber_editTextTextPersonName);
        EditText carBrand_editText = (EditText) findViewById(R.id.carBrand_editTextTextPersonName);
        EditText carModel_editText = (EditText) findViewById(R.id.carModel_editTextTextPersonName);
        EditText DateofService__editText = (EditText) findViewById(R.id.DateofService__editTextTextPersonName);
        EditText service_editText = (EditText) findViewById(R.id.service_editTextTextPersonName);

        carRegistration_editText.setText(carRegistration);
        carRegistration_editText.setEnabled(false);

        Button add_button = (Button) findViewById(R.id.add_buttonSR);
        /*System.out.println("----------------------------------------");
        GregorianCalendar gc1 = new GregorianCalendar();
        System.out.println("Year : " + gc1.get(Calendar.YEAR));
        System.out.println("Month : " + gc1.get(Calendar.MONTH));
        System.out.println("Day : " + gc1.get(Calendar.DATE));
        System.out.println("Hour : " + gc1.get(Calendar.HOUR));
        System.out.println("Minute : " + gc1.get(Calendar.MINUTE));
        System.out.println("Second : " + gc1.get(Calendar.SECOND));
        System.out.println("----------------------------------------");*/
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AppExecutors executors = new AppExecutors();
                GregorianCalendar gc = new GregorianCalendar();
                if(DateofService__editText.getText().toString().isEmpty() || service_editText.getText().toString().isEmpty()
                    || username_editText.getText().toString().isEmpty() || phoneNumber_editText.getText().toString().isEmpty()){ // : เช็คค่าในช่อง DateofService__editText , service_editText , username_editText , phoneNumber_editText ไม่ให้เป็น null
                    boolean checkDateofService = false;
                    boolean checkService = false;
                    boolean checkusername = false;
                    boolean checkphoneNumber = false;
                    if(DateofService__editText.getText().toString().isEmpty())
                        checkDateofService = true;
                    if(service_editText.getText().toString().isEmpty())
                        checkService = true;
                    if(username_editText.getText().toString().isEmpty())
                        checkusername= true;
                    if(phoneNumber_editText.getText().toString().isEmpty())
                        checkphoneNumber = true;
                    AlertDialog.Builder dialog = new AlertDialog.Builder(AddCar.this);
                    dialog.setTitle(R.string.add_user_information);
                    StringBuilder builder = new StringBuilder();
                    builder.append("Please fill in the fields marked with a asterisk.\n"); //R.string.please_fill แล้วเป็นตัวเลข
                    if(checkDateofService) // ถ้าว่างจะเพิ่มใน builder
                        builder.append("- Date of service.\n"); //R.string.date_of_service_n แล้วเป็นตัวเลข
                    if(checkService) // : ถ้าว่างจะเพิ่มใน builder
                        builder.append("- Service.\n"); //R.string.service_n แล้วเป็นตัวเลข
                    if(checkusername) // : ถ้าว่างจะเพิ่มใน builder
                        builder.append(getString(R.string.username_n));
                    if(checkphoneNumber) // : ถ้าว่างจะเพิ่มใน builder
                        builder.append(getString(R.string.phone_number_n));
                    dialog.setMessage(builder);
                    dialog.setPositiveButton(R.string.ok,null);
                    dialog.show();
                }else if(DateofService__editText.getText().toString().charAt(2) != '-' || DateofService__editText.getText().toString().charAt(5) != '-'){ // : เช็ครูปแบบวันที่
                    AlertDialog.Builder dialog = new AlertDialog.Builder(AddCar.this);
                    dialog.setTitle(R.string.add_user_information);
                    dialog.setMessage(R.string.service_format);
                    dialog.setPositiveButton(R.string.ok,null);
                    dialog.show();
                }else if(Integer.parseInt(DateofService__editText.getText().toString().substring(0,2)) > gc.get(Calendar.DATE) || Integer.parseInt(DateofService__editText.getText().toString().substring(3,5)) > gc.get(Calendar.MONTH)
                    || Integer.parseInt(DateofService__editText.getText().toString().substring(6,10)) > gc.get(Calendar.YEAR) + 543){ // : เช็ควัน เดือน ปี ว่ามากกว่าปัจจุบันหรือไม่
                    AlertDialog.Builder dialog = new AlertDialog.Builder(AddCar.this);
                    dialog.setTitle(R.string.add_user_information);
                    StringBuilder builder = new StringBuilder();
                    builder.append(getString(R.string.follows));
                    if(Integer.parseInt(DateofService__editText.getText().toString().substring(0,2)) > gc.get(Calendar.DATE)) // : ถ้ามากกว่าต้องแก้ไข
                        builder.append(getString(R.string.date));
                    if(Integer.parseInt(DateofService__editText.getText().toString().substring(3,5)) > gc.get(Calendar.MONTH)) // : ถ้ามากกว่าต้องแก้ไข
                        builder.append(getString(R.string.month));
                    if(Integer.parseInt(DateofService__editText.getText().toString().substring(6,10)) > gc.get(Calendar.YEAR) + 543) // : ถ้ามากกว่าต้องแก้ไข
                        builder.append(getString(R.string.year));
                    dialog.setMessage(builder);
                    dialog.setPositiveButton(R.string.ok,null);
                    dialog.show();
                } else{
                    executors.diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase db = AppDatabase.getInstance(AddCar.this);
                        final CarDetails carDetails = new CarDetails(0,carRegistration_editText.getText().toString(),
                                carOwner_editText.getText().toString(),
                                username_editText.getText().toString(),
                                iDcard_editText.getText().toString(),
                                phoneNumber_editText.getText().toString(),
                                carBrand_editText.getText().toString(),
                                carModel_editText.getText().toString(),
                                DateofService__editText.getText().toString(),
                                service_editText.getText().toString()
                                );
                        executors.diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                db.garageDao().addCarDetails(carDetails); // : พิ่ม User ใหม่
                            }
                            });
                        }
                    });
                    Intent i = new Intent(AddCar.this,Details.class);
                    i.putExtra("carRegistration",carRegistration);
                    startActivity(i);
                }
            }
        });
        Button home_buttonAC = (Button) findViewById(R.id.back_button);
        home_buttonAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddCar.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

}