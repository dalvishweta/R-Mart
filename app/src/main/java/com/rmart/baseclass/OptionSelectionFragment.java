package com.rmart.baseclass;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.rmart.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OptionSelectionFragment extends DialogFragment implements View.OnClickListener {

    private static final String ARG_TITLE = "param1";
    private static final String ARG_LIST_DATA = "param2";

    private String mTitle;
    private ArrayList<ItemData> mListData;

    public OptionSelectionFragment() {
        // Required empty public constructor
    }

    public static OptionSelectionFragment newInstance(String title, ArrayList<ItemData> data) {
        OptionSelectionFragment fragment = new OptionSelectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putSerializable(ARG_LIST_DATA, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
            /*mListData = new ArrayList<>();
            mListData.add(new ItemData("1 KGS", 0));
            mListData.add(new ItemData("2 KGS", 0));
            mListData.add(new ItemData("3 KGS", 0));
            mListData.add(new ItemData("4 KGS", 0));*/
            mListData = (ArrayList<ItemData>) getArguments().getSerializable(ARG_LIST_DATA);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        WindowManager.LayoutParams params = Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        Objects.requireNonNull(getDialog().getWindow()).setAttributes(params);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_option_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SearchView searchView = view.findViewById(R.id.searchView);
        RecyclerView resultsListField = view.findViewById(R.id.search_results_field);
        resultsListField.setHasFixedSize(false);
        OptionSelectionAdapter optionSelectionAdapter = new OptionSelectionAdapter(requireActivity(), mListData, this);
        resultsListField.setAdapter(optionSelectionAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() != 0) {
                    optionSelectionAdapter.getFilter().filter(newText);
                } else {
                    optionSelectionAdapter.updateItems(mListData);
                    optionSelectionAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        ItemData itemData = (ItemData) view.getTag();
    }
}