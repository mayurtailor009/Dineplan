package com.dineplan;

import com.dineplan.model.MenuPortion;
import com.dineplan.model.OrderTag;

import java.util.ArrayList;

/**
 * Created by sandeepjoshi on 08/09/16.
 */
public interface AddOrderTag {

    public void addOrderTag(OrderTag orderTag);

    void removeOrderTag(OrderTag orderTag);

    public void addPortions(MenuPortion menuPortions);

}
