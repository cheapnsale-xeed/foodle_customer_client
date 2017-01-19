package com.xeed.cheapnsale.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
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
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.service.model.Menu;
import com.xeed.cheapnsale.service.model.Store;

import java.util.ArrayList;

import javax.inject.Inject;

public class ExpandableMenuListFragment extends Fragment {

    RecyclerView recyclerView;

    @Inject
    public CheapnsaleService cheapnsaleService;
    public Store store;
    private ExpandableMenuListAdapter expandableMenuListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Application) getActivity().getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        expandableMenuListAdapter = new ExpandableMenuListAdapter(getContext(), new ArrayList<Menu>());

        recyclerView = (RecyclerView) inflater.inflate(R.layout.tab_menu_list_view, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(expandableMenuListAdapter);

        return recyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();

        expandableMenuListAdapter.initCartFooterLayout();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                store = cheapnsaleService.getStore(store.getId());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(store != null){
                    expandableMenuListAdapter.updateData(store.getMenus());
                }
            }
        }.execute();


    }
}