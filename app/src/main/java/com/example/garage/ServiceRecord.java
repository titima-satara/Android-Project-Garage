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
import com.example.garage.model.CarDetails;
import com.example.garage.util.AppExecutors;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ServiceRecord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_record);

        Intent i = getIntent();
        String carRegistration = i.getExtras().getString("carRegistration");
        String carOwner = i.getExtras().getString("carOwner");
        String username = i.getExtras().getString("username");
        String iDcard = i.getExtras().getString("iDcard");
        String phoneNumber = i.getExtras().getString("phoneNumber");
        String carBrand = i.getExtras().getString("carBrand");
        String carModel = i.getExtras().getString("carModel");

        Button back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ServiceRecord.this,Details.class);
                i.putExtra("carRegistration",carRegistration);
                startActivity(i);
            }
        });

        EditText dateofService__editText = findViewById(R.id.DateofService__editTextTextPersonName);
        EditText service_editText = findViewById(R.id.service_editTextTextPersonName);

        Button add_buttonSR = (Button) findViewById(R.id.add_buttonSR);
        add_buttonSR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GregorianCalendar gc = new GregorianCalendar();
                if(dateofService__editText.getText().toString().isEmpty() || service_editText.getText().toString().isEmpty()){ // : เช็คค่าในช่อง DateofService__editText , service_editText ไม่ให้เป็น null
                    boolean checkDateofService = false;
                    boolean checkService = false;
                    if(dateofService__editText.getText().toString().isEmpty())
                        checkDateofService = true;
                    if(service_editText.getText().toString().isEmpty())
                        checkService = true;
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ServiceRecord.this);
                    dialog.setTitle(R.string.add_user_information);
                    StringBuilder builder = new StringBuilder();
                    builder.append(getString(R.string.please_fill));
                    if(checkDateofService) // ถ้าว่างจะเพิ่มใน builder
                        builder.append(getString(R.string.date_of_service_n));
                    if(checkService) // ถ้าว่างจะเพิ่มใน builder
                        builder.append(getString(R.string.service_n));
                    dialog.setMessage(builder);
                    dialog.setPositiveButton(R.string.ok,null);
                    dialog.show();
                }else if(dateofService__editText.getText().toString().charAt(2) != '-' || dateofService__editText.getText().toString().charAt(5) != '-'){ // : เช็ครูปแบบวันที่
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ServiceRecord.this);
                    dialog.setTitle(R.string.add_user_information);
                    dialog.setMessage(R.string.service_format);
                    dialog.setPositiveButton(R.string.ok,null);
                    dialog.show();
                }else if(Integer.parseInt(dateofService__editText.getText().toString().substring(0,2)) > gc.get(Calendar.DATE) || Integer.parseInt(dateofService__editText.getText().toString().substring(3,5)) > gc.get(Calendar.MONTH)
                        || Integer.parseInt(dateofService__editText.getText().toString().substring(6,10)) > gc.get(Calendar.YEAR) + 543){ // : เช็ควัน เดือน ปี ว่ามากกว่าปัจจุบันหรือไม่
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ServiceRecord.this);
                    dialog.setTitle(R.string.add_user_information);
                    StringBuilder builder = new StringBuilder();
                    builder.append(getString(R.string.follows));
                    if(Integer.parseInt(dateofService__editText.getText().toString().substring(0,2)) > gc.get(Calendar.DATE)) // : ถ้ามากกว่าต้องแก้ไข
                        builder.append(getString(R.string.date));
                    if(Integer.parseInt(dateofService__editText.getText().toString().substring(3,5)) > gc.get(Calendar.MONTH)) // : ถ้ามากกว่าต้องแก้ไข
                        builder.append(getString(R.string.month));
                    if(Integer.parseInt(dateofService__editText.getText().toString().substring(6,10)) > gc.get(Calendar.YEAR) + 543) // : ถ้ามากกว่าต้องแก้ไข
                        builder.append(getString(R.string.year));
                    dialog.setMessage(builder);
                    dialog.setPositiveButton(R.string.ok,null);
                    dialog.show();
                }
                else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ServiceRecord.this);
                    dialog.setTitle(R.string.confirm_correction);
                    dialog.setMessage(R.string.have_you);
                    dialog.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final AppExecutors executors = new AppExecutors();
                            executors.diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    AppDatabase db = AppDatabase.getInstance(ServiceRecord.this);
                                    final CarDetails carDetails = new CarDetails(0,carRegistration,
                                        carOwner,
                                        username,
                                        iDcard,
                                        phoneNumber,
                                        carBrand,
                                        carModel,
                                        dateofService__editText.getText().toString(),
                                        service_editText.getText().toString());
                                    db.garageDao().addCarDetails(carDetails); // : เพิ่มการใช้บริการใหม่ใหม่
                                }
                            });
                            Intent i = new Intent(ServiceRecord.this,ServiceDetails.class);
                            i.putExtra("carRegistration",carRegistration);
                            startActivity(i);
                        }
                    });
                    dialog.setNegativeButton(R.string.no,null);
                    dialog.show();
                }
            }
        });
    }
}