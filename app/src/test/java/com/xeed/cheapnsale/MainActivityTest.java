package com.xeed.cheapnsale;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    private MainActivity mainActivity;

    @Mock
    private ViewPager mockViewPager;
    private TabLayout tabLayout;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();

        tabLayout = (TabLayout) mainActivity.findViewById(R.id.tab_layout);
    }

    @Test
    public void whenActivityIsStarted_thenShowCheapnsaleTitle() throws Exception {
        TextView cheapnsaleTitleText = (TextView) mainActivity.findViewById(R.id.main_tool_bar_title);
        assertThat(cheapnsaleTitleText.getText()).isEqualTo("싸다싸");
    }

    @Test
    public void whenActivityIsStarted_thenShowFirstFragmentTitle() throws Exception {
        assertThat(tabLayout.getSelectedTabPosition()).isEqualTo(0);
        assertThat(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getText()).isEqualTo("전체보기");
    }

    @Test
    public void whenTabChange_thenFragmentTitleChange() throws Exception {
        mainActivity.viewPager.setCurrentItem(1);
        assertThat(tabLayout.getSelectedTabPosition()).isEqualTo(1);
        assertThat(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getText()).isEqualTo("내 주문(0)");

        mainActivity.viewPager.setCurrentItem(0);
        assertThat(tabLayout.getSelectedTabPosition()).isEqualTo(0);
        assertThat(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getText()).isEqualTo("전체보기");
    }
}