package com.example.garage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.garage.db.AppDatabase;
import com.example.garage.model.CarDetails;
import com.example.garage.util.AppExecutors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText carRegistration_editText = (EditText) findViewById(R.id.carRegistration_editTextTextPersonName);

        Button searchBtn = (Button) findViewById(R.id.search_button);
        AppExecutors executors = new AppExecutors();
        AppDatabase db = AppDatabase.getInstance(MainActivity.this);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String carRegistration = carRegistration_editText.getText().toString();
                boolean check = true;
                for(int i = 0 ;i<carRegistration.length();i++){
                    if(carRegistration.charAt(i) == ' ' || carRegistration.charAt(i) == '-'){ // : หาช่องว่าง หรือ -
                        check = false;
                        break;
                    }
                }
                if(carRegistration.isEmpty()){ // : ถ้าไม่ได้ใส่ทะเบียนรถ
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle(R.string.car_registration);
                    dialog.setMessage(R.string.you_have);
                    dialog.setPositiveButton(R.string.ok,null);
                    dialog.show();
                }else {
                    if(check){ // : ถ้าไม่มีช่องว่าง หรือ -
                        executors.diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                String carRegistration2 = db.garageDao().getCarRegistration(carRegistration);
                                System.out.println("carRegistration2[0] :"+ carRegistration2);
                                if(carRegistration.equals(carRegistration2)){ // : ถ้าเจอในฐานข้อมูล
                                    Intent i = new Intent(MainActivity.this,Details.class);
                                    i.putExtra("carRegistration",carRegistration);
                                    startActivity(i);
                                }else{ // : ถ้าไม่เจอในฐานข้อมูล
                                    Intent i = new Intent(MainActivity.this,AddCar.class);
                                    i.putExtra("carRegistration",carRegistration);
                                    startActivity(i);
                                }
                            }
                        });
                    }else{
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this); // : ถ้ามีช่องว่าง หรือ - ให้แก้ไข
                        dialog.setTitle(R.string.car_registration);
                        dialog.setMessage(R.string.do_not);
                        dialog.setPositiveButton(R.string.ok,null);
                        dialog.show();
                    }
                }
                //final boolean[] noDataFound = {false};

                // if(noDataFound[0]) ทำงานก่อนที่ executors.diskIO().execute จะทำงานเสร็จ
                /*System.out.println("noDataFound[0] :"+noDataFound[0]);
                if(noDataFound[0]){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("No data found.");
                    dialog.setMessage("The vehicle registration was not found.");
                    dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    dialog.setNegativeButton("NO",null);
                    dialog.show();
                }*/
            }
        });
    }

}