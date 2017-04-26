package model;

import com.spacecowboys.codegames.dashboardapp.model.oneclick.OneClickTile;
import com.spacecowboys.codegames.dashboardapp.tools.JSON;
import org.junit.Test;

/**
 * Created by EDraser on 26.04.17.
 */
public class Pojo2Json {

    @Test
    public void generateJson() {

        String json = JSON.toString(new OneClickTile(), OneClickTile.class);
        System.out.println(json);
    }
}
