package com.xeed.cheapnsale.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.adapter.ExpandableMenuListAdapter;
import com.xeed.cheapnsale.vo.MenuItems;

import java.util.ArrayList;
import java.util.List;

public class ExpandableMenuListFragment extends Fragment {

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        recyclerView = (RecyclerView) inflater.inflate(R.layout.tab_menu_list_view, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ExpandableMenuListAdapter(makeDummyData()));

        return recyclerView;
    }

    private List<MenuItems> makeDummyData(){
        List<MenuItems> list = new ArrayList<>();
        MenuItems item;
        for (int i = 0; i < 10; i ++) {
            item = new MenuItems(0, "Item = " + i, "22,000원", "");
            list.add(item);
        }

        return list;
    }


}