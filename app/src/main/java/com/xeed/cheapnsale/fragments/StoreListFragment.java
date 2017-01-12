package com.xeed.cheapnsale.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.adapter.StoreListAdapter;
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.service.model.Store;

import java.util.ArrayList;

import javax.inject.Inject;


public class StoreListFragment extends Fragment {

    RecyclerView recyclerView;

    ArrayList<Store> stores;
    StoreListAdapter storeListAdapter;

    @Inject
    public CheapnsaleService cheapnsaleService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((Application) getActivity().getApplication()).getApplicationComponent().inject(this);

        recyclerView = (RecyclerView) inflater.inflate(R.layout.tab_store_list_fragment, container, false);

        storeListAdapter = new StoreListAdapter(new ArrayList<Store>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(storeListAdapter);

        return recyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                stores = cheapnsaleService.getStoreList();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                storeListAdapter.updateData(stores);
            }
        }.execute();
    }
}

