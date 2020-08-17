package com.rmart.profile.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rmart.R;

import java.util.Objects;


public class EditMyProfileFragment extends BaseMyProfileFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public EditMyProfileFragment() {
    }

    public static EditMyProfileFragment newInstance(String param1, String param2) {
        EditMyProfileFragment fragment = new EditMyProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.edit_my_profile));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_my_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.submit).setOnClickListener(this);
        view.findViewById(R.id.update_location).setOnClickListener(this);
        setMapView(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_location:
                mListener.gotoMapView();
                break;
            case R.id.submit:
                Objects.requireNonNull(getActivity()).onBackPressed();
                break;
        }
    }
}