package com.common.view;

import com.kartum.R;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;


public class CustomCenteredPrimaryDrawerItem extends PrimaryDrawerItem {

    @Override
    public int getLayoutRes() {
        return R.layout.drawer_item;
    }

    @Override
    public int getType() {
        return R.id.tvDrawerItemName;
    }

}
