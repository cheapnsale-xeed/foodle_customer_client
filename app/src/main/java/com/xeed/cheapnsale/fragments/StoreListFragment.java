package com.xeed.cheapnsale.fragments;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.adapter.StoreListAdapter;
import com.xeed.cheapnsale.service.CheapnsaleService;
import com.xeed.cheapnsale.service.model.Store;
import com.xeed.cheapnsale.util.CalcDistanceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.inject.Inject;


public class StoreListFragment extends Fragment {

    @Inject
    public CheapnsaleService cheapnsaleService;

    private StoreListAdapter storeListAdapter;
    private RecyclerView recyclerView;

    public LocationManager mLocationManager;

    public ArrayList<Store> stores;
    private Location myLocation;
    private HashMap<String, Double> myLocationMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Application) getActivity().getApplication()).getApplicationComponent().inject(this);

        myLocation = ((Application) getActivity().getApplication()).getMyLocation();

        myLocationMap = new HashMap<String, Double>();
        myLocationMap.put("GPS_COORDINATES_LAT", Double.valueOf(myLocation.getLatitude()));
        myLocationMap.put("GPS_COORDINATES_LONG", Double.valueOf(myLocation.getLongitude()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.tab_store_list_fragment, container, false);

        storeListAdapter = new StoreListAdapter(getContext(), new ArrayList<Store>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(storeListAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        return recyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                stores = cheapnsaleService.getStoreListByLocation(myLocationMap);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.d("Store : ", "Create");
                sortStoreAndUpdateList().execute();
            }

        }.execute();

        ((Application) getActivity().getApplication()).getCart().clearCartItems();
    }


    private AsyncTask<Void, Void, Void> sortStoreAndUpdateList () {

        AsyncTask<Void, Void, Void> sortStoreAndUpdateTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                if (((Application) getActivity().getApplication()).getMyLocation() != null) {
                    setStoreDistance(((Application) getActivity().getApplication()).getMyLocation());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Collections.sort(stores, Store.DistanceToStoreAsc);
                storeListAdapter.updateData(stores);
            }
        };
        return sortStoreAndUpdateTask;
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d("Store, longitude", ""+location.getLongitude()); // 경도
            Log.d("Store, Latitude", ""+location.getLatitude()); // 위도

            if (stores != null) {
                setStoreDistance(location);
                storeListAdapter.notifyDataSetChanged();
            }

            if (getActivity().getApplication() != null) {
                ((Application) getActivity().getApplication()).setMyLocation(location);
                mLocationManager.removeUpdates(mLocationListener);
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    public void setStoreDistance(Location location) {
        for (int i = 0; i < stores.size(); i++) {
            stores.get(i).setDistanceToStore((int)
                    CalcDistanceUtil.calDistance(location.getLatitude(), location.getLongitude(),
                            stores.get(i).getGpsCoordinatesLat(), stores.get(i).getGpsCoordinatesLong())
            );
        }
    }
}

