package com.qiming.wcq.mymapdemo.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiming.wcq.mymapdemo.R;
import com.qiming.wcq.mymapdemo.util.ProgressDialogUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    private long loadTime = 0;

    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

    }

}
