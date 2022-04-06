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
import com.gofly.myaccount.agent.AddAgentActivity;

import java.util.ArrayList;
import java.util.List;

public class VehicleModelListingAdapter extends CommonRecyclerAdapter {

    ArrayList<VehicleModelTypeResponse>vehicleDetailsList;
    ArrayList<String> vehicleModelList;
    CarHireFragment carHireFragment;
    ArrayList<VehicleModelTypeResponse>vehicleDetailsNewList;

    public VehicleModelListingAdapter(ArrayList<String>vehicleModelList,ArrayList<VehicleModelTypeResponse>vehicleDetailsList ,CarHireFragment carHireFragment) {
        this.vehicleModelList = vehicleModelList;
        this.carHireFragment = carHireFragment;
        this.vehicleDetailsList=vehicleDetailsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.vehicle_model_row) {
            @Override
            public void onItemSelected(int position) {


            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {

        View lView = holder.getView();

        TextView   tv_vehicleModel;
        RecyclerView recyclerView;


        tv_vehicleModel = lView.findViewById(R.id.tv_vehicleModel);
        recyclerView = lView.findViewById(R.id.rv_vehicleDetails);
        tv_vehicleModel.setText(vehicleModelList.get(position));
        vehicleDetailsNewList=new ArrayList<>();

        for (int i=0;i<vehicleDetailsList.size();i++)
        {
            if (vehicleModelList.get(position).equals(vehicleDetailsList.get(i).getVehicle_model()))
            {
                vehicleDetailsNewList.add(new VehicleModelTypeResponse(vehicleDetailsList.get(i).getStatus(),vehicleDetailsList.get(i).getVehicle_name(),vehicleDetailsList.get(i).getVehicle_model(),vehicleDetailsList.get(i).getVehicle_type_id(),vehicleDetailsList.get(i).getVehicle_id()));
            }
        }

        VehicleDetailsAdapter vehicleDetailsAdapter=new VehicleDetailsAdapter(vehicleDetailsNewList,vehicleModelList.get(position),carHireFragment);
        recyclerView.setAdapter(vehicleDetailsAdapter);












    }

    @Override
    public int getItemCount() {
        return vehicleModelList.size();
    }
}
