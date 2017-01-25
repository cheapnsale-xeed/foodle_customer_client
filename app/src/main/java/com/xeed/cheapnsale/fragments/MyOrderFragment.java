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
import com.xeed.cheapnsale.adapter.MyOrderCurrentAdapter;
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.service.model.Order;

import java.util.ArrayList;

import javax.inject.Inject;

public class MyOrderFragment extends Fragment {

    RecyclerView recyclerView;

    @Inject
    public CheapnsaleService cheapnsaleService;

    MyOrderCurrentAdapter myOrderCurrentAdapter;
    ArrayList<Order> myOrder = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Application) getActivity().getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_my_order_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.my_order_ready_recycler_view);

        myOrderCurrentAdapter = new MyOrderCurrentAdapter(getContext(), myOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(myOrderCurrentAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                myOrder = cheapnsaleService.getMyOrder(((Application) getActivity().getApplication()).getUserEmail());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                myOrderCurrentAdapter.updateData(myOrder);
            }
        }.execute();
    }
}