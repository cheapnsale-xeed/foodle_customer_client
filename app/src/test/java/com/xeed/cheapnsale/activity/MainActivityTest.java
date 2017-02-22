package com.xeed.cheapnsale.activity;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.TestApplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    private MainActivity mainActivity;

    @Mock
    private ViewPager mockViewPager;
    private TabLayout tabLayout;
    private Application application;
    private ImageView menuButton;

    @Before
    public void setUp() throws Exception {
        application = (TestApplication) RuntimeEnvironment.application;

        Location testLocation = new Location(LocationManager.GPS_PROVIDER);
        testLocation.setLatitude(37.517646d);
        testLocation.setLongitude(127.101843d);

        application.setMyLocation(testLocation);

        MockitoAnnotations.initMocks(this);

        mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
        menuButton = (ImageView) mainActivity.findViewById(R.id.image_menu_button_main);
    }

    @Test
    public void whenMapButtonTab_thenGoMapActivity() throws Exception {
        ImageView mapButton = (ImageView) mainActivity.findViewById(R.id.image_map_button_map);
        mapButton.performClick();

        Intent expectedIntent = new Intent(mainActivity, MapActivity.class);
        Intent actualIntent = shadowOf(mainActivity).getNextStartedActivity();

        assertThat(actualIntent.filterEquals(expectedIntent)).isTrue();
    }

    @Test
    public void whenMenuButtonClick_thenShowLeftMenu() throws Exception {
        assertThat(mainActivity.getSlidingMenu().isMenuShowing()).isEqualTo(false);
        menuButton.performClick();
        assertThat(mainActivity.getSlidingMenu().isMenuShowing()).isEqualTo(true);
    }

    @Test
    public void whenCloseMenuButtonOnClick_thenLeftMenuClose() throws Exception {
        menuButton.performClick();
        assertThat(mainActivity.getSlidingMenu().isMenuShowing()).isEqualTo(true);

        ImageView closeButton = (ImageView) mainActivity.findViewById(R.id.image_x_button_menu_navi);
        closeButton.performClick();
        assertThat(mainActivity.getSlidingMenu().isMenuShowing()).isEqualTo(false);
    }

}