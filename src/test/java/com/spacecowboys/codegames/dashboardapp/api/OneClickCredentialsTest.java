package com.spacecowboys.codegames.dashboardapp.api;

import com.google.common.base.Strings;
import com.spacecowboys.codegames.dashboardapp.configuration.Configuration;
import org.junit.Assert;
import org.junit.Test;
import org.wildfly.swarm.Swarm;

/**
 * Created by Uwe.Mattner on 26.04.2017.
 */
public class OneClickCredentialsTest {

    @Test
    public void auth() throws Exception {
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
    }

}