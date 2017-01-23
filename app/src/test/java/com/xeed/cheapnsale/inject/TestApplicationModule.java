package com.xeed.cheapnsale.inject;


import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;
import com.xeed.cheapnsale.Application;
import com.xeed.cheapnsale.service.CheapnsaleService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestApplicationModule extends ApplicationModule{

    public  TestApplicationModule(Application application){
        super(application);
    }

    @Override
    CheapnsaleService providesCheapnsaleService() {
        return mock(CheapnsaleService.class);
    }

    @Override
    Picasso providesPicasso() {
        Picasso mockPicasso = mock(Picasso.class);
        RequestCreator mockRequestCreator = mock(RequestCreator.class);
        when(mockPicasso.load(anyString())).thenReturn(mockRequestCreator);
        when(mockRequestCreator.transform(any(Transformation.class))).thenReturn(mockRequestCreator);
        return mockPicasso;
    }
}
