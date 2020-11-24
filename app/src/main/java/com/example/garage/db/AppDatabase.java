package com.example.garage.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.garage.model.CarDetails;
import com.example.garage.util.AppExecutors;


@Database(entities = {CarDetails.class}, exportSchema = false,version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = "AppDatabase";
    private static final String DB_NAME = "garage.db";

    private  static  AppDatabase sInstance;

    public  abstract  garageDao garageDao();

    public static synchronized AppDatabase getInstance(final Context context){
        if(sInstance == null){
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,
                    DB_NAME).addCallback(new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    insertInitialData(context);
                }
            }).build();
        }
        return sInstance;
    }

    private static void insertInitialData(final Context context) {
        AppExecutors executors = new AppExecutors();
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = getInstance(context);
                db.garageDao().addCarDetails(
                        new CarDetails(0,"กก 1111","A","UserA","1111111111111","0911111111","BMW","BMW X1"
                                ,"18-11-2563","ตรวจสภาพ"),
                        new CarDetails(
                                1,"งง 9999","A","UserA","1111111111111","0911111111","Mercedes-Benz","Mercedes-AMG GT C Roadster"
                                ,"18-11-2563","ตรวจสภาพ")
                        /*,new CarDetails(
                                2,"งง 9999","A","UserA","1111111111111","0911111111","Mercedes-Benz","Mercedes-AMG GT C Roadster"
                                ,"20-11-2563","ตรวจสภาพ"),
                        new CarDetails(
                                3,"งง 5555","B","UserB","2222222222222","0922222222","BMW","BMW 8"
                                ,"20-11-2563","ตรวจสภาพ")*/
                );
            }
        });
    }
}
