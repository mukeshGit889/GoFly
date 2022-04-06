package com.gofly.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.gofly.database.DBAdapter;
import com.gofly.utils.ApplicationPreference;
import com.gofly.utils.CommonUtils;
import com.gofly.utils.IntentAndFragmentService;
import com.gofly.utils.webservice.ApiConstants;

import butterknife.ButterKnife;

/**
 * Created by manoj on 13-02-2018.
 */

public abstract class BaseFragment extends Fragment {
    View view;
    protected abstract int getLayoutResource();

    public ApplicationPreference applicationPreference;
    public IntentAndFragmentService intentAndFragmentService;
    public CommonUtils commonUtils;
    public DBAdapter dbAdapter;
    public ApiConstants apiConstants;
    public Gson gson;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationPreference = new ApplicationPreference(getActivity());
        intentAndFragmentService = new IntentAndFragmentService();
        commonUtils = new CommonUtils();
        dbAdapter = new DBAdapter(getActivity());
        apiConstants = new ApiConstants();
        gson = new Gson();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutResource(),container,false);
        ButterKnife.bind(this,view);

        return view;
    }

}