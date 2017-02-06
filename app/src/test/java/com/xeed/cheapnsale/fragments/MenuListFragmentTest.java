package com.xeed.cheapnsale.fragments;

import android.support.v7.widget.RecyclerView;

import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.R;
import com.xeed.cheapnsale.service.model.Menu;
import com.xeed.cheapnsale.service.model.Store;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MenuListFragmentTest {

    private MenuListFragment menuListFragment;

    @Before
    public void setUp() throws Exception {
        menuListFragment = new MenuListFragment();
        menuListFragment.setStore(makeMockStore());

        SupportFragmentTestUtil.startFragment(menuListFragment);
    }

    @Test
    public void whenFragmentOnResume_theShowStoreMenuCountIsCorrectly() throws Exception {
        when(menuListFragment.cheapnsaleService.getStore(anyString())).thenReturn(makeMockStore());
        menuListFragment.setStore(makeMockStore());

        menuListFragment.onResume();
        RecyclerView recyclerView = (RecyclerView) menuListFragment.getView().findViewById(R.id.recycler_menu_list);
        assertThat(recyclerView.getAdapter().getItemCount()).isEqualTo(2);
    }


    private Store makeMockStore() {
        Store store = new Store();
        store.setId("mock 1");
        store.setCategory("mock category");
        store.setRegNum("111-111-111");
        store.setName("mock store");
        store.setPaymentType("바로결제");
        store.setAvgPrepTime("20");
        store.setMainImageUrl("http://www.mock.com/mock.img");
        store.setPhoneNumber("010-1234-5678");
        store.setGpsCoordinatesLat(37.517646d);
        store.setGpsCoordinatesLong(127.101843d);
        store.setDistanceToStore(456);

        ArrayList<Menu> menus = new ArrayList<>();
        Menu menu = new Menu();
        menu.setMenuId("1");
        menu.setMenuName("TEST MENU1");
        menu.setMenuPrice(2000);
        menu.setMenuImg("http://mock1.png");
        menus.add(menu);

        menu = new Menu();
        menu.setMenuId("2");
        menu.setMenuName("TEST MENU2");
        menu.setMenuPrice(3000);
        menu.setMenuImg("http://mock2.png");
        menus.add(menu);

        store.setMenus(menus);

        return store;
    }
}