package com.gofly.carhire;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.main.adapter.common.CommonRecyclerAdapter;
import com.gofly.main.adapter.common.CommonViewHolder;
import com.gofly.model.parsingModel.VehicleModelTypeResponse;

import java.util.ArrayList;

public class VehicleDetailsAdapter extends CommonRecyclerAdapter {


    ArrayList<VehicleModelTypeResponse>vehicleDetailsList;
    CarHireFragment carHireFragment;
    String modelName;


    public VehicleDetailsAdapter(ArrayList<VehicleModelTypeResponse>vehicleDetailsList,String modelName, CarHireFragment carHireFragment) {
        this.vehicleDetailsList = vehicleDetailsList;
        this.carHireFragment = carHireFragment;
        this.modelName=modelName;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.vehicle_details_row) {
            @Override
            public void onItemSelected(int position) {
            carHireFragment.getVehicleType(vehicleDetailsList.get(position).getVehicle_id(),vehicleDetailsList.get(position).getVehicle_name());

            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {

        View lView = holder.getView();

        TextView   tv_vehicleName;


        tv_vehicleName = lView.findViewById(R.id.tv_vehicleName);
        if (modelName.equals(vehicleDetailsList.get(position).getVehicle_model()))
        {
            tv_vehicleName.setText(vehicleDetailsList.get(position).getVehicle_name());
        }











    }

    @Override
    public int getItemCount() {
        return vehicleDetailsList.size();
    }
}
