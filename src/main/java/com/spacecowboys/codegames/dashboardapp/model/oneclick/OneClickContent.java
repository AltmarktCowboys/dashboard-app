package com.spacecowboys.codegames.dashboardapp.model.oneclick;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

import java.util.UUID;

/**
 * Created by EDraser on 26.04.17.
 */
public class OneClickContent {
    private String id;
    private String organizationId;
    private String sessionId;

    private String loginName;
    private String email;
    private String organizationName;
    private String memberType;
    private String directLink;

    private transient UUID organizationUUID;
    private transient UUID sessionUUID;


    @JsonProperty("Id")
    public String getId() {
        return id;
    }

    @JsonProperty("Id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("OrganizationId")
    public String getOrganizationId() {
        return organizationId;
    }

    @JsonProperty("OrganizationId")
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    @JsonProperty("SessionId")
    public String getSessionId() {
        return sessionId;
    }

    @JsonProperty("SessionId")
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @JsonProperty("LoginName")
    public String getLoginName() {
        return loginName;
    }

    @JsonProperty("LoginName")
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @JsonProperty("Email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("Email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("OrganizationName")
    public String getOrganizationName() {
        return organizationName;
    }

    @JsonProperty("OrganizationName")
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    @JsonProperty("MemberType")
    public String getMemberType() {
        return memberType;
    }

    @JsonProperty("MemberType")
    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    @JsonProperty("DirectLink")
    public String getDirectLink() {
        return directLink;
    }

    @JsonProperty("DirectLink")
    public void setDirectLink(String directLink) {
        this.directLink = directLink;
    }

    public UUID getOrganizationIdAsUuid() {
        if (organizationUUID == null && !Strings.isNullOrEmpty(organizationId)) {
            organizationUUID = UUID.fromString(organizationId);
        }
        return organizationUUID;
    }

    public UUID getSessionAsUuid() {
        if (sessionUUID == null && !Strings.isNullOrEmpty(sessionId)) {
            sessionUUID = UUID.fromString(sessionId);
        }
        return sessionUUID;
    }
}
