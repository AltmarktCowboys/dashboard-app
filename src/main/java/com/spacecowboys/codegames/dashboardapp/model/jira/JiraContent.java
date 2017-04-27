package com.spacecowboys.codegames.dashboardapp.model.jira;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EDraser on 27.04.17.
 */
public class JiraContent {

    private List<JiraIssue> issues = new ArrayList<>();

    public List<JiraIssue> getIssues() {
        return issues;
    }
}
