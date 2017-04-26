package com.spacecowboys.codegames.dashboardapp.model.oneclick;

import com.google.common.base.Strings;
import com.spacecowboys.codegames.dashboardapp.configuration.Configuration;
import org.junit.Assert;
import org.junit.Test;
import org.wildfly.swarm.Swarm;

/**
 * Created by Uwe.Mattner on 26.04.2017.
 */
public class OneClickPrincipalTest {
    @Test
    public void load() throws Exception {
        Swarm swarm = new Swarm();
        Configuration cfg = Configuration.getInstance();
        if(Strings.isNullOrEmpty(cfg.getOneClickTestUrl())) {
            return;
        }

        OneClickCredentials oneClickCredentials =
                new OneClickCredentials(cfg.getOneClickTestAccessNumber(),
                        cfg.getOneClickTestLoginName(),
                        cfg.getOneClickTestPassword(),
                        cfg.getOneClickTestUrl());
        OAuthToken auth = oneClickCredentials.auth();
        Assert.assertNotNull(auth);

        OneClickPrincipal oneClickPrincipal = OneClickPrincipal.load(oneClickCredentials);
        Assert.assertNotNull(oneClickPrincipal);
    }

}