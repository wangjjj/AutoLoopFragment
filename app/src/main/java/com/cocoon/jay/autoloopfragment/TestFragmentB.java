package com.cocoon.jay.autoloopfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/9/29 0029.
 */

public class TestFragmentB extends Fragment implements View.OnClickListener{


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_test_b, null);

        v.findViewById(R.id.tv_1).setOnClickListener(this);
        v.findViewById(R.id.tv_2).setOnClickListener(this);
        v.findViewById(R.id.tv_3).setOnClickListener(this);
        v.findViewById(R.id.tv_4).setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getActivity(), ((TextView)view).getText().toString(), Toast.LENGTH_LONG).show();
    }
}
