package com.xeed.cheapnsale.activity;

import android.widget.ImageView;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    private MainActivity mainActivity;
    private ImageView menuButton;

    @Before
    public void setUp() throws Exception {
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
        menuButton = (ImageView) mainActivity.findViewById(R.id.image_menu_button_main);
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