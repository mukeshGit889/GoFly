package com.gofly.bus.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.bus.fragment.BusDetailFragment;
import com.gofly.main.adapter.common.CommonRecyclerAdapter;
import com.gofly.main.adapter.common.CommonViewHolder;
import com.gofly.model.parsingModel.bus.DropOffBean;
import com.gofly.model.parsingModel.bus.PickUpBean;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class DropingPointAdapter extends CommonRecyclerAdapter
{

    int row_index=-1;
    BusDetailFragment busDetailFragment;
    List<DropOffBean> list;
    List<JSONObject> dropoffpointlist;

    public DropingPointAdapter(BusDetailFragment busDetailFragment, List<DropOffBean> list, List<JSONObject> dropoffpointlist)
    {

        this.busDetailFragment = busDetailFragment;
        this.list = list;
        this.dropoffpointlist=dropoffpointlist;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        return new CommonViewHolder(viewGroup, R.layout.boarding_point_row) {
            @Override
            public void onItemSelected(int position) {
                row_index=position;
                notifyDataSetChanged();
                busDetailFragment.dropOffAddress(position);
                busDetailFragment.dropofflistdata(dropoffpointlist.get(position),list.get(position).getDropoffCode());
            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position)
    {
        View view = holder.getView();
        TextView boad_name,boad_timings ;
        ImageView checkImg;
        boad_name=view.findViewById(R.id.boad_name);
        boad_timings=view.findViewById(R.id.boad_timings);
        checkImg=view.findViewById(R.id.iv_chkImg);

        boad_name.setText(list.get(position).getDropoffName());
        boad_timings.setText(getHourMin(list.get(position).getDropoffTime()));
        if(row_index==position)
        {
            checkImg.setImageResource(R.drawable.checked_img);



        }
        else
        {
            checkImg.setImageResource(R.drawable.uncheck_img);


        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static String getHourMin(String example){
        example = example.replace("T"," ");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            java.util.Date date = format.parse(example);
            SimpleDateFormat format11 = new SimpleDateFormat("hh:mm a");
            String rightdate = format11.format(date);
            return rightdate;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
}

