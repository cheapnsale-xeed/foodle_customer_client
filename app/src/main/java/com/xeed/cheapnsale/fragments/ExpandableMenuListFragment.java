package com.xeed.cheapnsale.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.adapter.ExpandableMenuListAdapter;
import com.xeed.cheapnsale.vo.MenuItems;

import java.util.List;

import javax.inject.Inject;

public class ExpandableMenuListFragment extends Fragment {

    RecyclerView recyclerView;

    @Inject
    List<MenuItems> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.tab_menu_list_fragment, container, false);

        ((Application)getActivity().getApplication()).getApplicationComponent().inject(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ExpandableMenuListAdapter(list));

        return recyclerView;
    }
}