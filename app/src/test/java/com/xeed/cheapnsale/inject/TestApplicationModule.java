package com.xeed.cheapnsale.inject;


import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.vo.MenuItems;

import java.util.ArrayList;
import java.util.List;

public class TestApplicationModule extends ApplicationModule{

    public  TestApplicationModule(Application application){
        super(application);
    }

    @Override
    List<MenuItems> providesList() {
        List<MenuItems> list = new ArrayList<>();
        for (int i = 0; i < 3; i ++) {
            MenuItems item = new MenuItems(0, "Item = " + i, "22,000원", "");
            list.add(item);
        }

        return list;
    }
}
