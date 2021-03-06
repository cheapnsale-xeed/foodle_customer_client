package com.xeed.cheapnsale.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.holder.MenuListChildHolder;
import com.xeed.cheapnsale.holder.MenuListHeadHolder;
import com.xeed.cheapnsale.service.model.Menu;
import com.xeed.cheapnsale.service.model.Store;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MenuListAdapterTest {

    private MenuListAdapter menuListAdapter;
    private MenuListHeadHolder headerHolder;

    private android.app.Application application;

    @Before
    public void setUp() throws Exception {
        application = RuntimeEnvironment.application;
        menuListAdapter = new MenuListAdapter(application, new LinearLayoutManager(application) ,makeMockList(), makeMockStore());
        headerHolder = (MenuListHeadHolder) menuListAdapter.onCreateViewHolder(new LinearLayout(application), 0);
    }

    @Test
    public void whenAdapterStarts_thenHolderHaveRightItems() throws Exception {
        menuListAdapter.onBindViewHolder(headerHolder, 0);

        assertThat(headerHolder.textItemNameMenu.getText()).isEqualTo("Item = 0");
        assertThat(headerHolder.textItemPriceMenu.getText()).isEqualTo("22,000원");
    }

    @Test
    public void whenHeaderHolderClicked_thenShowChildHolderBelowHeadHolder() throws Exception {
        menuListAdapter.onBindViewHolder(headerHolder, 0);
        headerHolder.itemView.performClick();

        assertThat(menuListAdapter.getItemCount()).isEqualTo(4);

        MenuListChildHolder childHolder = (MenuListChildHolder) menuListAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 1);
        menuListAdapter.onBindViewHolder(childHolder, 1);

        assertThat(childHolder.imageMinusButtonMenu.getVisibility()).isEqualTo(View.VISIBLE);
        assertThat(childHolder.textTotalPriceMenu.getText()).isEqualTo("22,000원");
    }

    @Test
    public void whenChildHolderPlusAndMinusButtonClicked_thenItemCountIsChange() throws Exception {
        menuListAdapter.onBindViewHolder(headerHolder, 0);
        headerHolder.itemView.performClick();

        assertThat(menuListAdapter.getItemCount()).isEqualTo(4);

        MenuListChildHolder childHolder = (MenuListChildHolder) menuListAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 1);
        menuListAdapter.onBindViewHolder(childHolder, 1);

        childHolder.imagePlusButtonMenu.performClick();
        assertThat(childHolder.textItemCountMenu.getText()).isEqualTo("2");
        assertThat(childHolder.textTotalPriceMenu.getText()).isEqualTo("44,000원");

        childHolder.imageMinusButtonMenu.performClick();
        assertThat(childHolder.textItemCountMenu.getText()).isEqualTo("1");
        assertThat(childHolder.textTotalPriceMenu.getText()).isEqualTo("22,000원");

        childHolder.imageMinusButtonMenu.performClick();
        assertThat(childHolder.textItemCountMenu.getText()).isEqualTo("1");
        assertThat(childHolder.textTotalPriceMenu.getText()).isEqualTo("22,000원");

    }

    @Test
    public void whenHeaderHolderClicked_thenCheckAddCartButton() throws Exception {
        MenuListChildHolder childHolder = (MenuListChildHolder) menuListAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 1);

        Menu cartItem = new Menu();
        cartItem.setMenuName("만두");
        cartItem.setMenuPrice(5000);
        cartItem.setMenuItemCount(1);

        Application app = ((Application)childHolder.itemView.getContext().getApplicationContext());

        app.getCart().setStoreId("store_1");
        app.getCart().addCartItem(cartItem);

        menuListAdapter.onBindViewHolder(headerHolder, 0);
        headerHolder.itemView.performClick();
        menuListAdapter.onBindViewHolder(childHolder, 1);

        assertThat(childHolder.buttonOrderNowMenu.getVisibility()).isEqualTo(View.GONE);
    }

    @Test
    public void whenDirectOrderButtonClick_thenRemoveChildRecylerview() throws Exception {
        MenuListChildHolder childHolder = (MenuListChildHolder) menuListAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 1);

        Menu cartItem = new Menu();
        cartItem.setMenuId("menu_1");
        cartItem.setMenuName("만두");
        cartItem.setMenuPrice(5000);
        cartItem.setMenuItemCount(1);

        Application app = ((Application)childHolder.itemView.getContext().getApplicationContext());

        app.getCart().setStoreId("store_1");
        app.getCart().addCartItem(cartItem);

        menuListAdapter.onBindViewHolder(headerHolder, 0);
        headerHolder.itemView.performClick();
        menuListAdapter.onBindViewHolder(childHolder, 1);

        childHolder.buttonOrderNowMenu.performClick();

        for (int i=0; i< menuListAdapter.getItemCount(); i++){
            assertThat(menuListAdapter.getItemViewType(i)).isEqualTo(0);
        }
    }


    private ArrayList<Menu> makeMockList() {
        ArrayList<Menu> list = new ArrayList<>();
        for (int i = 0; i < 3; i ++) {
            Menu item = new Menu(0, ""+i,  "Item = " + i, 22000, "");
            list.add(item);
        }

        return list;
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