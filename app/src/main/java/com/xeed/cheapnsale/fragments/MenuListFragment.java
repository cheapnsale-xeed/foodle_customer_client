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
import com.xeed.cheapnsale.adapter.MenuListAdapter;
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.service.model.Menu;
import com.xeed.cheapnsale.service.model.Store;

import java.util.ArrayList;

import javax.inject.Inject;

public class MenuListFragment extends Fragment {

    @Inject
    public CheapnsaleService cheapnsaleService;

    public Store store;
    private MenuListAdapter menuListAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Application) getActivity().getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        linearLayoutManager = new LinearLayoutManager(getContext());
        menuListAdapter = new MenuListAdapter(getContext(), linearLayoutManager, new ArrayList<Menu>());

        recyclerView = (RecyclerView) inflater.inflate(R.layout.tab_menu_list_view, container, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(menuListAdapter);

        return recyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();

        menuListAdapter.initCartFooterLayout();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                store = cheapnsaleService.getStore(store.getId());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(store != null){
                    menuListAdapter.updateData(store.getMenus());
                }
            }
        }.execute();


    }
}