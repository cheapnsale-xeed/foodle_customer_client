package com.xeed.cheapnsale.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.activity.MainActivity;
import com.xeed.cheapnsale.activity.MapActivity;
import com.xeed.cheapnsale.activity.SignUpActivity;
import com.xeed.cheapnsale.user.AWSMobileClient;
import com.xeed.cheapnsale.user.IdentityManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends Fragment {

    @BindView(R.id.image_map_button_map)
    public ImageView imageMapButtonMap;

    @BindView(R.id.image_search_button_main)
    public ImageView imageSearchButtonMain;

    @BindView(R.id.image_menu_button_main)
    public ImageView imageMenuButtonMain;

    @BindView(R.id.fragment_main)
    public LinearLayout fragmentMain;

    @Inject
    public AWSMobileClient awsMobileClient;

    private IdentityManager identityManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Application) getActivity().getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        ButterKnife.bind(this, view);

        identityManager = awsMobileClient.getIdentityManager();

        return view;
    }

    @OnClick(R.id.image_menu_button_main)
    public void onClickMenuNaviButton (View view) {
        ((MainActivity) getActivity()).selectCurrentFragment(0);

    }

    @OnClick(R.id.image_search_button_main)
    public void onClickImageSearchButton(View view) {
        identityManager.signOut();

        Intent intent = new Intent(getContext(), SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick(R.id.image_map_button_map)
    public void onClickMapLinkButton(View view) {
        Intent intent = new Intent(getContext(), MapActivity.class);
        startActivity(intent);
    }

}