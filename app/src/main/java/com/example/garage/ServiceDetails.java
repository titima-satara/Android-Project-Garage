package com.example.garage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.garage.adapter.ServiceAdapter;
import com.example.garage.db.AppDatabase;
import com.example.garage.model.CarDetails;
import com.example.garage.util.AppExecutors;

public class ServiceDetails extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private String carRegistration;

    @Override
    protected void onResume() {

        super.onResume();
        final AppExecutors executors = new AppExecutors();
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getInstance(ServiceDetails.this);
                final CarDetails[] carDetails = db.garageDao().getCarService(carRegistration);
                executors.diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        ServiceAdapter adapter = new ServiceAdapter(ServiceDetails.this,carDetails);
                        mRecyclerView.setAdapter(adapter);
                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        mRecyclerView = findViewById(R.id.service_RecyclerView);
        mRecyclerView.setLayoutManager((new LinearLayoutManager(ServiceDetails.this)));

        Intent i = getIntent();
        String carRegistrationNEW = i.getExtras().getString("carRegistration");
        carRegistration = carRegistrationNEW;

        TextView carRegistration_textViewSD = findViewById(R.id.carRegistration_textViewSD);
        carRegistration_textViewSD.setText("Car Registration :"+ carRegistrationNEW);

        Button home_buttonS = (Button) findViewById(R.id.home_buttonS);
        home_buttonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ServiceDetails.this,MainActivity.class);
                startActivity(i);
            }
        });

        String carRegistration = i.getExtras().getString("carRegistrationCarDetails");
        String carOwner = i.getExtras().getString("carOwner");
        String username = i.getExtras().getString("username");
        String iDcard = i.getExtras().getString("iDcard");
        String phoneNumber = i.getExtras().getString("phoneNumber");
        String carBrand = i.getExtras().getString("carBrand");
        String carModel = i.getExtras().getString("carModel");

        Button ServiceRecord_buttonS = (Button) findViewById(R.id.ServiceRecord_buttonS);
        ServiceRecord_buttonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ServiceDetails.this,ServiceRecord.class);
                i.putExtra("carRegistration",carRegistration);
                i.putExtra("carOwner",carOwner);
                i.putExtra("username",username);
                i.putExtra("iDcard",iDcard);
                i.putExtra("phoneNumber",phoneNumber);
                i.putExtra("carBrand",carBrand);
                i.putExtra("carModel",carModel);
                startActivity(i);
            }
        });

    }
}