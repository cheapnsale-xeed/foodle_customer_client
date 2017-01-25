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
import android.widget.Toast;

import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.adapter.StoreListAdapter;
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.service.model.Store;

import java.util.ArrayList;

import javax.inject.Inject;


public class StoreListFragment extends Fragment {

    @Inject
    public CheapnsaleService cheapnsaleService;

    private NMapLocationManager mMapLocationManager;
    private StoreListAdapter storeListAdapter;
    private RecyclerView recyclerView;

    ArrayList<Store> stores;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Application) getActivity().getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.tab_store_list_fragment, container, false);

        storeListAdapter = new StoreListAdapter(getContext(), new ArrayList<Store>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(storeListAdapter);

        mMapLocationManager = new NMapLocationManager(container.getContext());
        mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener);

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

        ((Application) getActivity().getApplication()).getCart().clearCartItems();
    }

    private final NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener = new NMapLocationManager.OnLocationChangeListener() {

        @Override
        public boolean onLocationChanged(NMapLocationManager locationManager, NGeoPoint myLocation) {

            for (int i = 0; i < stores.size(); ++i) {
                if (mMapLocationManager.getMyLocation() != null) {
                    stores.get(i).setDistanceToStore((int) NGeoPoint.getDistance(mMapLocationManager.getMyLocation(), new NGeoPoint(stores.get(i).getGpsCoordinatesLong(), stores.get(i).getGpsCoordinatesLat())));
                }
            }
            storeListAdapter.notifyDataSetChanged();

            return true;
        }

        @Override
        public void onLocationUpdateTimeout(NMapLocationManager locationManager) {
            Toast.makeText(recyclerView.getContext(), "Your current location is temporarily unavailable.", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onLocationUnavailableArea(NMapLocationManager locationManager, NGeoPoint myLocation) {
            Toast.makeText(recyclerView.getContext(), "Your current location is unavailable area.", Toast.LENGTH_LONG).show();
        }

    };
}

