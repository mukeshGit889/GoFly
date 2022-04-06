package com.gofly.sight_seeing.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.gofly.R;
import com.gofly.main.fragment.BaseFragment;
import com.gofly.sight_seeing.sightseeing_adapters.SightseeingListAdapter;
import com.gofly.sight_seeing.sightseeing_adapters.SightseeingListBean;
import com.gofly.utils.webservice.ApiConstants;
import com.gofly.utils.webservice.WebInterface;
import com.gofly.utils.webservice.WebServiceController;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.OnClick;

public class SightSeeingListFragment extends BaseFragment implements WebInterface, SightseeingListAdapter.onItemClicked
{
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    ArrayList<SightseeingListBean> list_al;
    SightseeingListAdapter adapter;

    @BindView(R.id.searchView)
    SearchView searchView;

    @BindView(R.id.location_name)
    TextView locationName;

    String location;

    @SuppressLint("ValidFragment")
    public SightSeeingListFragment(String location) {
        this.location=location;
    }

    public SightSeeingListFragment() {
    }

    @OnClick(R.id.back_button)
    public void goBack() {
        getActivity().onBackPressed();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.sight_seeing_list_layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initVariables();

        locationName.setText(location);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                adapter.getFilter().filter(s);
                return false;
            }
        });
    }

    private void initVariables() {
        list_al = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SightseeingListAdapter(getActivity(), list_al,this);
        recyclerView.setAdapter(adapter);
        callWebservice();
    }

    private void callWebservice() {
        WebServiceController lController = new WebServiceController(getContext(), this);
        String param = "?booking_source="+apiConstants.BOOKING_SOURCE+"&search_id=" + getArguments().getInt("search_id") + "&op=load";
        lController.getRequest(ApiConstants.SIGHT_SEEING_SEARCH_LIST + param, 1, true);

    }


    @Override
    public void onSelectSS(String ID, String Token) {

        Bundle lBundle = new Bundle();
        lBundle.putInt("search_id", getArguments().getInt("search_id"));
        lBundle.putString("product_code", ID);
        lBundle.putString("token", Token);
        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame, new SightSeeingDetailsFragment(), lBundle, true);


    }

    @Override
    public void getResponse(String response, int flag) {

        if (flag == 1) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getInt("status") == 1) {
                    list_al.clear();
                   /* budgetTx.setText("Budget " + jsonObject.getJSONObject("filters")
                            .getJSONObject("p").getString("min") + "-"
                            + jsonObject.getJSONObject("filters")
                            .getJSONObject("p").getString("max"));

                    packageCountTx.setText(jsonObject.getInt("total_result_count")+" Packages");
*/

                    JSONArray dataArray = jsonObject.getJSONObject("data").
                            getJSONObject("SSSearchResult").
                            getJSONArray("SightSeeingResults");
                    if (dataArray.length() > 0) {
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject datObj = dataArray.getJSONObject(i);
                            SightseeingListBean bean = new SightseeingListBean(
                                    datObj.getString("ProductName"),
                                    datObj.getString("ProductCode"),
                                    datObj.getString("ImageUrl"),
                                    datObj.getString("ImageHisUrl"),
                                    datObj.getString("DestinationName"),
                                    datObj.getJSONObject("Price").getString("TotalDisplayFare"),
                                    datObj.getString("Description"),
                                    datObj.getString("Duration"),
                                    datObj.getString("ResultToken"),
                                    datObj.getString("ReviewCount"),
                                    datObj.getString("StarRating"),
                                    datObj.getBoolean("Cancellation_available"));
                            list_al.add(bean);
                        }
                        adapter.notifyDataSetChanged();

                    }
                } else {

                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

}
