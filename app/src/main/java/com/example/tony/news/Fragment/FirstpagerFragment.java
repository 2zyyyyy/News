package com.example.tony.news.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tony.news.R;

/**
 * Created by Tony on 16/11/18.
 */

    //主界面的Fragment
public class FirstpagerFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_layout,
                (ViewGroup) getActivity().findViewById(R.id.view_pager), false);

        return v;
    }
}
