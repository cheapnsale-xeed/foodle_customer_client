package com.xeed.cheapnsale.inject;


import com.xeed.cheapnsale.Application;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class TestApplicationModule extends ApplicationModule{

    public  TestApplicationModule(Application application){
        super(application);
    }

    @Override
    List<String> providesList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i ++) {
            list.add("Item = " + i);
        }

        return list;
    }
}
