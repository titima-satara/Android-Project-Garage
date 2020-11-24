package com.example.garage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garage.R;
import com.example.garage.model.CarDetails;


public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {

    private Context mContext;
    private CarDetails[] mCarDetails;

    public ServiceAdapter(Context context,CarDetails[] carDetails){
        mContext = context;
        this.mCarDetails = carDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemservice,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CarDetails carDetails = mCarDetails[position];

        holder.dateofService__editText.setText(carDetails.dateOfService);
        holder.service_editText.setText(carDetails.serviceReceived);
        holder.dateofService__editText.setEnabled(false);
        holder.service_editText.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return mCarDetails.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public EditText dateofService__editText;
        public  EditText service_editText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.dateofService__editText = itemView.findViewById(R.id.DateofService__editTextTextPersonName);
            this.service_editText = itemView.findViewById(R.id.service_editTextTextPersonName);


        }
    }
}
