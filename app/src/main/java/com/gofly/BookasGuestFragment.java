package com.gofly;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gofly.main.fragment.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookasGuestFragment extends BaseFragment {

    public BookasGuestFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_bookas_guest;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


    }

  /*  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(, container, false);
    }*/
}
