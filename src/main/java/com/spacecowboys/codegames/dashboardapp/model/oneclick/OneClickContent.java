package com.spacecowboys.codegames.dashboardapp.model.oneclick;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

import java.util.UUID;

/**
 * Created by EDraser on 26.04.17.
 */
public class OneClickContent {

    // {
// "Id":"c6a42c3c-c8b5-472a-a5ce-3ff2070130e5",
// "Actor":null,
// "LoginName":"TMüller",
// "Email":"",
// "OrganizationId":"4eaf0e86-0fdd-4d37-8dda-55c613e6b15a",
// "SessionId":"eee655b0-92ba-46cb-b92b-48ff24ab1829",
// "OrganizationName":"MusterfirmaAOC P&Z",
// "MemberType":"client_employee",
// "ShouldChangePassword":true,
// "ShouldConfirmEmail":true,
// "SdnEnvironmentUrl":"https://moveon.sdn.two-clicks.de",
// "HasAvatar":false,
// "AllowedFileTypes":""
// }

    private String id;
    private String organizationId;
    private String sessionId;
    private String isChangePassword;
    private String isConfirmEmail;
    private String isAvatar;

    private String actor;
    private String loginName;
    private String email;
    private String organizationName;
    private String memberType;
    private String sdnEnvironmentUrl;
    private String allowedFileTypes;

    private transient UUID organizationUUID;
    private transient UUID sessionUUID;
    private transient Boolean shouldChangePassword;
    private transient Boolean shouldConfirmEmail;
    private transient Boolean hasAvatar;

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

    @JsonProperty("IsChangePassword")
    public String getIsChangePassword() {
        return isChangePassword;
    }

    @JsonProperty("IsChangePassword")
    public void setIsChangePassword(String isChangePassword) {
        this.isChangePassword = isChangePassword;
    }

    @JsonProperty("IsConfirmEmail")
    public String getIsConfirmEmail() {
        return isConfirmEmail;
    }

    @JsonProperty("IsConfirmEmail")
    public void setIsConfirmEmail(String isConfirmEmail) {
        this.isConfirmEmail = isConfirmEmail;
    }

    @JsonProperty("IsAvatar")
    public String getIsAvatar() {
        return isAvatar;
    }

    @JsonProperty("IsAvatar")
    public void setIsAvatar(String isAvatar) {
        this.isAvatar = isAvatar;
    }

    @JsonProperty("Actor")
    public String getActor() {
        return actor;
    }

    @JsonProperty("Actor")
    public void setActor(String actor) {
        this.actor = actor;
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

    @JsonProperty("SdnEnvironmentUrl")
    public String getSdnEnvironmentUrl() {
        return sdnEnvironmentUrl;
    }

    @JsonProperty("SdnEnvironmentUrl")
    public void setSdnEnvironmentUrl(String sdnEnvironmentUrl) {
        this.sdnEnvironmentUrl = sdnEnvironmentUrl;
    }

    @JsonProperty("AllowedFileTypes")
    public String getAllowedFileTypes() {
        return allowedFileTypes;
    }

    @JsonProperty("AllowedFileTypes")
    public void setAllowedFileTypes(String allowedFileTypes) {
        this.allowedFileTypes = allowedFileTypes;
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

    public Boolean shouldChangePassword() {
        if (shouldChangePassword == null && !Strings.isNullOrEmpty(getIsChangePassword())) {
            shouldChangePassword = Boolean.valueOf(getIsChangePassword());
        }
        return shouldChangePassword;
    }

    public Boolean shouldConfirmEmail() {
        if (shouldConfirmEmail == null && !Strings.isNullOrEmpty(getIsConfirmEmail())) {
            shouldConfirmEmail = Boolean.valueOf(getIsConfirmEmail());
        }
        return shouldConfirmEmail;
    }

    public Boolean hasAvatar() {
        if (isAvatar == null && !Strings.isNullOrEmpty(getIsAvatar())) {
            hasAvatar = Boolean.valueOf(getIsAvatar());
        }
        return hasAvatar;
    }
}
