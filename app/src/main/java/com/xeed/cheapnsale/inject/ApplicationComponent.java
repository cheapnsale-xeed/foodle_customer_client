package com.xeed.cheapnsale.inject;

import com.xeed.cheapnsale.fragments.ExpandableMenuListFragment;
import com.xeed.cheapnsale.fragments.MenuListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={ApplicationModule.class})
public interface ApplicationComponent {
    void inject(MenuListFragment menuListFragment);
    void inject(ExpandableMenuListFragment expandableMenuListFragment);
}
