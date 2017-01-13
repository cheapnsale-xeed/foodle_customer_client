package com.xeed.cheapnsale.inject;


import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.service.CheapnsaleService;

import static org.mockito.Mockito.mock;

public class TestApplicationModule extends ApplicationModule{

    public  TestApplicationModule(Application application){
        super(application);
    }

    @Override
    CheapnsaleService providesCheapnsaleService() {
        return mock(CheapnsaleService.class);
    }
}
