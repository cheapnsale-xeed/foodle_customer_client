package com.xeed.cheapnsale.adapter;

import android.view.View;
import android.widget.LinearLayout;

import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.BuildConfig;
import com.xeed.cheapnsale.holder.ExpandableMenuChildHolder;
import com.xeed.cheapnsale.holder.ExpandableMenuHeadHolder;
import com.xeed.cheapnsale.service.model.Menu;
import com.xeed.cheapnsale.vo.CartItem;

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
public class ExpandableMenuListAdapterTest {

    private ExpandableMenuListAdapter expandableMenuListAdapter;
    private ExpandableMenuHeadHolder headerHolder;

    @Before
    public void setUp() throws Exception {
        expandableMenuListAdapter = new ExpandableMenuListAdapter(RuntimeEnvironment.application, makeMockList());
        headerHolder = (ExpandableMenuHeadHolder) expandableMenuListAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 0);
    }

    @Test
    public void whenAdapterStarts_thenHolderHaveRightItems() throws Exception {
        expandableMenuListAdapter.onBindViewHolder(headerHolder, 0);

        assertThat(headerHolder.textItemNameMenu.getText()).isEqualTo("Item = 0");
        assertThat(headerHolder.textItemPriceMenu.getText()).isEqualTo("22,000원");
    }

    @Test
    public void whenHeaderHolderClicked_thenShowChildHolderBelowHeadHolder() throws Exception {
        expandableMenuListAdapter.onBindViewHolder(headerHolder, 0);
        headerHolder.itemView.performClick();

        assertThat(expandableMenuListAdapter.getItemCount()).isEqualTo(4);

        ExpandableMenuChildHolder childHolder = (ExpandableMenuChildHolder)expandableMenuListAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 1);
        expandableMenuListAdapter.onBindViewHolder(childHolder, 1);

        assertThat(childHolder.imageMinusButtonMenu.getVisibility()).isEqualTo(View.VISIBLE);
        assertThat(childHolder.textTotalPriceMenu.getText()).isEqualTo("22,000원");
    }

    @Test
    public void whenChildHolderPlusAndMinusButtonClicked_thenItemCountIsChange() throws Exception {
        expandableMenuListAdapter.onBindViewHolder(headerHolder, 0);
        headerHolder.itemView.performClick();

        assertThat(expandableMenuListAdapter.getItemCount()).isEqualTo(4);

        ExpandableMenuChildHolder childHolder = (ExpandableMenuChildHolder)expandableMenuListAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 1);
        expandableMenuListAdapter.onBindViewHolder(childHolder, 1);

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
        ExpandableMenuChildHolder childHolder = (ExpandableMenuChildHolder)expandableMenuListAdapter.onCreateViewHolder(new LinearLayout(RuntimeEnvironment.application), 1);

        CartItem cartItem = new CartItem();
        cartItem.setMenuName("만두");
        cartItem.setPrice(5000);
        cartItem.setCount(1);

        Application app = ((Application)childHolder.itemView.getContext().getApplicationContext());

        app.getCart().setStoreId("store_1");
        app.getCart().addCartItem(cartItem);

        expandableMenuListAdapter.onBindViewHolder(headerHolder, 0);
        headerHolder.itemView.performClick();
        expandableMenuListAdapter.onBindViewHolder(childHolder, 1);

        assertThat(childHolder.buttonOrderNowMenu.getVisibility()).isEqualTo(View.GONE);
    }

    private ArrayList<Menu> makeMockList() {
        ArrayList<Menu> list = new ArrayList<>();
        for (int i = 0; i < 3; i ++) {
            Menu item = new Menu(0, ""+i,  "Item = " + i, 22000, "");
            list.add(item);
        }

        return list;
    }

}