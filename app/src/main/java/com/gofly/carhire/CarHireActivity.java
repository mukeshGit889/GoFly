package com.gofly.carhire;

import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.hotel.fragment.HotelSearchFragment;
import com.gofly.interfaces.DatePickerNotify;
import com.gofly.main.activity.BaseActivity;
import com.gofly.myaccount.agent.AddAgentActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class CarHireActivity extends BaseActivity implements
        DatePickerNotify {
    Integer adultCount = 1, childCount = 0, infantCount = 0;
    AlertDialog alertDialog;
    String checkInDate="", checkOutDate="", startDate;
    @BindView(R.id.et_from) EditText et_from;
    @BindView(R.id.et_to) EditText et_to;
    @BindView(R.id.et_departure) EditText et_departure;
    @BindView(R.id.et_return) EditText et_return;
    @BindView(R.id.et_vehicleType) EditText et_vehicleType;
    @BindView(R.id.et_comments) EditText et_comments;
    @BindView(R.id.et_contactDetails) EditText et_contactDetails;
    @BindView(R.id.tv_adult) TextView tv_adult;
    @BindView(R.id.tv_child) TextView tv_child;
    @BindView(R.id.tv_infant) TextView tv_infant;

    @OnClick(R.id.iv_back)
    void backAction(){
        onBackPressed();
    }
    @OnClick(R.id.et_departure)
    void selectDepartureDate()
    {

    }

    @OnClick(R.id.et_return)
    void selectReturnDate()
    {


    }

    @OnClick(R.id.tv_adult)
    void setAdultCount()
    {
        addTraveller();
    }

    @OnClick(R.id.tv_child)
    void setChildCount(){
        addTraveller();
    }

    @OnClick(R.id.tv_infant)
    void setInfantCount(){
        addTraveller();
    }
    @OnClick(R.id.et_vehicleType)
    void openVehicleType(){
        openVehicleDialog();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_car_hire;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_car_hire);
        Calendar calendar = Calendar.getInstance();
        startDate = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)
                +"-"+calendar.get(Calendar.DAY_OF_MONTH);
        notifyDate(startDate, 1);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }
    public void addTraveller()
    {
        View view = getLayoutInflater().inflate(R.layout.add_traveller_dialog, null);

        final BottomSheetDialog dialog = new BottomSheetDialog(CarHireActivity.this);
        dialog.setContentView(view);
        dialog.show();
        final TextView tv_adult_count,tv_child_count,tv_infant_count;
        ImageView iv_add_ad,iv_add_ch,iv_add_in,iv_minus_ad,iv_minus_ch,iv_minus_in;
        tv_adult_count=dialog.findViewById(R.id.no_of_ad);
        tv_child_count=dialog.findViewById(R.id.no_of_ch);
        tv_infant_count=dialog.findViewById(R.id.no_of_in);

        iv_add_ad=dialog.findViewById(R.id.iv_plus_ad);
        iv_add_ch=dialog.findViewById(R.id.iv_plus_ch);
        iv_add_in=dialog.findViewById(R.id.iv_plus_in);

        iv_minus_ad=dialog.findViewById(R.id.iv_minus_ad);
        iv_minus_ch=dialog.findViewById(R.id.iv_minus_ch);
        iv_minus_in=dialog.findViewById(R.id.iv_minus_in);

        Button bt_done=dialog.findViewById(R.id.bt_done);

        tv_adult_count.setText(adultCount+"");
        tv_child_count.setText(childCount+"");
        tv_infant_count.setText(infantCount+"");

        iv_add_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(tv_adult_count.getText().toString())<9)
                {
                    adultCount++;
                    tv_adult_count.setText(Integer.parseInt(tv_adult_count.getText().toString()) + 1 + "");
                }
            }
        });

        iv_add_ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(tv_child_count.getText().toString())<9) {
                    childCount++;
                    tv_child_count.setText(Integer.parseInt(tv_child_count.getText().toString()) + 1 + "");
                }
            }
        });

        iv_add_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(tv_infant_count.getText().toString())<9) {
                    infantCount++;
                    tv_infant_count.setText(Integer.parseInt(tv_infant_count.getText().toString()) + 1 + "");
                }
            }
        });

        iv_minus_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(tv_adult_count.getText().toString())>0){
                    adultCount--;
                    tv_adult_count.setText(Integer.parseInt(tv_adult_count.getText().toString())-1+"");
                }

            }
        });

        iv_minus_ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Integer.parseInt(tv_child_count.getText().toString())>0) {
                    childCount--;
                    tv_child_count.setText(Integer.parseInt(tv_child_count.getText().toString()) - 1 + "");
                }
            }
        });

        iv_minus_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(tv_infant_count.getText().toString())>0) {
                    infantCount--;
                    tv_infant_count.setText(Integer.parseInt(tv_infant_count.getText().toString()) - 1 + "");
                }
            }
        });

        bt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adultCount+childCount+infantCount<1){
                    commonUtils.toastShort("Add a traveller",CarHireActivity.this);
                    return;
                }
                if(adultCount+childCount+infantCount>9){
                    commonUtils.toastShort("Max. 9 travellers can be add",CarHireActivity.this);
                    return;
                }
                adultCount=Integer.parseInt(tv_adult_count.getText().toString());
                childCount=Integer.parseInt(tv_child_count.getText().toString());
                infantCount=Integer.parseInt(tv_infant_count.getText().toString());

                tv_adult.setText(adultCount+" Adults");
                tv_child.setText(childCount+" Children");
                tv_infant.setText(infantCount+" Infants");
                dialog.dismiss();
            }
        });
    }
    public void openVehicleDialog()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CarHireActivity.this);
        LayoutInflater inflater = getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.vehicle_type_dialog, null);
        dialogBuilder.setView(dialogView);
        // dialogView.getParent().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog = dialogBuilder.create();

        alertDialog.show();

        TextView tv_indica=dialogView.findViewById(R.id.tv_indica);
        TextView tv_ritza=dialogView.findViewById(R.id.tv_ritza);
        TextView tv_etos=dialogView.findViewById(R.id.tv_etos);
        TextView tv_dzire=dialogView.findViewById(R.id.tv_dzire);
        TextView tv_xcent=dialogView.findViewById(R.id.tv_xcent);
        TextView tv_tyto=dialogView.findViewById(R.id.tv_tyto);
        TextView tv_xylo=dialogView.findViewById(R.id.tv_xylo);
        TextView tv_tavera=dialogView.findViewById(R.id.tv_tavera);
        TextView tv_temp_traveller=dialogView.findViewById(R.id.tv_temp_traveller);
        TextView tv_mini_bus_21=dialogView.findViewById(R.id.tv_mini_bus_21);
        TextView tv_mini_bus_33=dialogView.findViewById(R.id.tv_mini_bus_33);
        TextView tv_bus_40=dialogView.findViewById(R.id.tv_bus_40);
        TextView tv_bus_50=dialogView.findViewById(R.id.tv_bus_50);
        TextView tv_others=dialogView.findViewById(R.id.tv_others);





        tv_indica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_vehicleType.setText("Indica");

                alertDialog.dismiss();
            }
        });
        tv_ritza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_vehicleType.setText("Ritz");
                alertDialog.dismiss();
            }
        });

        tv_etos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_vehicleType.setText("Etios");
                alertDialog.dismiss();
            }
        });
        tv_dzire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_vehicleType.setText("Dzire");
                alertDialog.dismiss();
            }
        });
        tv_xcent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_vehicleType.setText("Xcent");
                alertDialog.dismiss();
            }
        });
        tv_tyto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_vehicleType.setText("Toyota innova Crysta");
                alertDialog.dismiss();
            }
        });
        tv_xylo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_vehicleType.setText("Xylo");
                alertDialog.dismiss();
            }
        });
        tv_tavera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_vehicleType.setText("Tavera");
                alertDialog.dismiss();
            }
        });
        tv_temp_traveller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_vehicleType.setText("Tempo Traveller(TT)");
                alertDialog.dismiss();
            }
        });
        tv_mini_bus_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_vehicleType.setText("Mini Bus 21 seater");
                alertDialog.dismiss();
            }
        });
        tv_mini_bus_33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_vehicleType.setText("Mini Bus 33 seater");
                alertDialog.dismiss();
            }
        });
        tv_bus_40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_vehicleType.setText("Bus 40 seater");
                alertDialog.dismiss();
            }
        });
        tv_bus_50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_vehicleType.setText("Bus 50 seater");
                alertDialog.dismiss();
            }
        });
        tv_others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_vehicleType.setText("Others");
                alertDialog.dismiss();
            }
        });


    }

    @Override
    public void notifyDate(String dateValue, Integer datePickerValue)
    {
        String endDate_str;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            Date endDate = dateFormat.parse(dateValue);
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMMM-dd EEEE",Locale.ENGLISH);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yy EEEE",Locale.ENGLISH);
            endDate_str = sdf.format(endDate);
            String[] splitDate = endDate_str.split(" ");
            //String[] dateValueArray = splitDate[0].split("-");

            switch (datePickerValue){
                case 1:
                    et_departure.setText(splitDate[0]+" "+splitDate[1]+"' "+splitDate[2]);

                    checkInDate = dateValue;

                    setCheckOutDate(checkInDate);

                    getDateDifference();
                    break;
                case 2:

                    // coDateValue.setText(splitDate[0]+" "+splitDate[1]+"' "+splitDate[2]);


                    checkOutDate = dateValue;
                    getDateDifference();
                    break;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setCheckOutDate(String dateValue)
    {
        String endDate_str;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try{

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(dateValue));
            calendar.add(Calendar.DATE, 1);
            dateValue = dateFormat.format(calendar.getTime());

            Date endDate = dateFormat.parse(dateValue);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yy EEEE",Locale.ENGLISH);
            endDate_str = sdf.format(endDate);
            String[] splitDate = endDate_str.split(" ");


            et_return.setText(splitDate[0]+" "+splitDate[1]+"' "+splitDate[2]);
            // coDateValue.setText(splitDate[0]+" "+splitDate[1]+"' "+splitDate[2]);

            checkOutDate = dateFormat.format(calendar.getTime());
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    private void getDateDifference() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1, date2;
        try{
            date1 = format.parse(checkInDate);
            date2 = format.parse(checkOutDate);

            int diffInDays = (int) ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24));

            if(diffInDays >= 1){
               // nightCountText.setText(String.valueOf(diffInDays)+ " Night's");
              //  nightCount = String.valueOf(diffInDays);
            }else {
                setCheckOutDate(checkInDate);
                switch (diffInDays){
                    case 0:
                        date1 = format.parse(checkInDate);
                        date2 = format.parse(checkOutDate);

                        diffInDays = (int) ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24));
                      // nightCountText.setText(String.valueOf(diffInDays)+ " Night's");
                      //  nightCount = String.valueOf(diffInDays);
                        commonUtils.toastShort(
                                "Check in and Check out date cannot be same.", CarHireActivity.this);
                        break;
                    default:
                        commonUtils.toastShort(
                                "Check out date cannot be less than Check In date.", CarHireActivity.this);
                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}