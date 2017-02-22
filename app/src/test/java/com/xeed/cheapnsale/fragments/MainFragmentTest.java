package com.xeed.cheapnsale.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.activity.MainActivity;
import com.xeed.cheapnsale.activity.MapActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainFragmentTest {

    private MainFragment mainFragment;
    private MainActivity mainActivity;

    @Before
    public void setUp() throws Exception {
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
        mainFragment =  (MainFragment) mainActivity.getSupportFragmentManager().findFragmentById(R.id.fragment_main);
        SupportFragmentTestUtil.startFragment(mainFragment);


    }

    @Test
    public void whenMapButtonTab_thenGoMapActivity() throws Exception {
        ImageView mapButton = (ImageView) mainFragment.getView().findViewById(R.id.image_map_button_map);
        mapButton.performClick();

        Intent expectedIntent = new Intent(mainFragment.getActivity(), MapActivity.class);
        Intent actualIntent = shadowOf(mainFragment.getActivity()).getNextStartedActivity();

        assertThat(actualIntent.filterEquals(expectedIntent)).isTrue();
    }

    @Test
    public void whenMenuNaviButtonClick_thenShowMenuNaviFragment() throws Exception {

        ImageView menuNaviButton = (ImageView) mainFragment.getView().findViewById(R.id.image_menu_button_main);
        menuNaviButton.performClick();

        //assertThat(mainFragment.getView().findViewById(R.id.fragment_menu_navi).getVisibility()).isEqualTo(View.VISIBLE);
        //assertThat(mainFragment.getView().findViewById(R.id.fragment_menu_navi).getVisibility()).isEqualTo(View.VISIBLE);

//        assertThat(((MainActivity) mainFragment.getActivity()).mainViewPager.getCurrentItem()).isEqualTo(0);
    }
}