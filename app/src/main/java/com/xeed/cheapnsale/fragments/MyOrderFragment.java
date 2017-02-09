package com.xeed.cheapnsale.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.adapter.MyOrderCurrentAdapter;
import com.xeed.cheapnsale.adapter.MyOrderPastAdapter;
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.service.model.Order;
import com.xeed.cheapnsale.util.DateUtil;

import java.util.ArrayList;

import javax.inject.Inject;

public class MyOrderFragment extends Fragment {

    RecyclerView recyclerView_current, recyclerView_past;

    @Inject
    public CheapnsaleService cheapnsaleService;

    MyOrderCurrentAdapter myOrderCurrentAdapter;
    ArrayList<Order> myOrder = new ArrayList<>();
    public boolean isPayment = false;
    MyOrderPastAdapter myOrderPastAdapter;

    ArrayList<Order> myCurrentOrder = new ArrayList<>();
    ArrayList<Order> myPastOrder = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Application) getActivity().getApplication()).getApplicationComponent().inject(this);

        if(getActivity().getIntent().getExtras() != null && getActivity().getIntent().getExtras().get("isPayment") != null){
            isPayment = (boolean) getActivity().getIntent().getExtras().get("isPayment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_my_order_fragment, container, false);
        recyclerView_current = (RecyclerView) view.findViewById(R.id.recycler_ready_my_order);

        myOrderCurrentAdapter = new MyOrderCurrentAdapter(getContext(), myCurrentOrder);
        recyclerView_current.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_current.setAdapter(myOrderCurrentAdapter);

        recyclerView_past = (RecyclerView) view.findViewById(R.id.recycler_finish_my_order);

        myOrderPastAdapter = new MyOrderPastAdapter(getContext(), myPastOrder);
        recyclerView_past.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_past.setAdapter(myOrderPastAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (getActivity().getApplication() != null) {
                    myCurrentOrder = cheapnsaleService.getMyOrder(((Application) getActivity().getApplication()).getUserEmail());
                    myPastOrder.clear();

                    for (int i=myCurrentOrder.size() - 1; i >= 0 ; i--) {
                        // 주문의 상태가 픽업완료 일 때 과거주문내역으로
                        if(Order.STATUS.FINISH.name().equals(myCurrentOrder.get(i).getStatus())) {
                            myPastOrder.add(myCurrentOrder.get(i));
                            myCurrentOrder.remove(i);
                        // 주문의 픽업 시간이 현재보다 과거일 때 과거주문내역으로
                        } else if (DateUtil.stringToDate(myCurrentOrder.get(i).getPickupTime()).getTime() < System.currentTimeMillis()) {
                            myPastOrder.add(myCurrentOrder.get(i));
                            myCurrentOrder.remove(i);
                        }
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                myOrderCurrentAdapter.updateData(myCurrentOrder);
                myOrderPastAdapter.updateData(myPastOrder);
            }
        }.execute();

        if(isPayment){
            final MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
                    .customView(R.layout.dialog_payment_success_my_order, false).build();

            materialDialog.show();

            getActivity().getIntent().getExtras().clear();
            isPayment = false;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    materialDialog.cancel();
                }
            }, 2000);
        }
    }
}