package com.xeed.cheapnsale.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuNaviFragment extends Fragment {

    @BindView(R.id.image_x_button_menu_navi)
    public ImageButton imageXButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_navi_fragment, container, false);

        ButterKnife.bind(this, view);

        TextView nickMenuNavi = (TextView) view.findViewById(R.id.text_nick_menu_navi);
        Typeface face = Typeface.createFromAsset(view.getContext().getApplicationContext().getAssets(), "fonts/TmonMonsori.otf.otf");
        nickMenuNavi.setTypeface(face);
        return view;
    }

    @OnClick(R.id.image_x_button_menu_navi)
    public void onClickXButton (View view) {
        ((MainActivity) getActivity()).selectCurrentFragment(1);
    }

}
