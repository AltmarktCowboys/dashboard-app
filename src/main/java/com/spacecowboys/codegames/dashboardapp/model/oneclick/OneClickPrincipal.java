package com.spacecowboys.codegames.dashboardapp.model.oneclick;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Charsets;
import com.spacecowboys.codegames.dashboardapp.configuration.Configuration;
import com.spacecowboys.codegames.dashboardapp.tools.JSON;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.jboss.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OneClickPrincipal {

    private static final Logger LOGGER = Logger.getLogger(OneClickPrincipal.class);
    // example json
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
    private transient String directLink;

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

    public static OneClickPrincipal load(OneClickCredentials oneClickCredentials) throws Exception {

        int timeout = Integer.parseInt(Configuration.getInstance().getOneClickTimeout());

        URL url = new URL(String.format("%1$s/aip/api/account/principal", oneClickCredentials.getServiceUrl()));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Host", new URL(oneClickCredentials.getOneClickUrl()).getHost());
        connection.addRequestProperty("Authorization", oneClickCredentials.getPortalToken().getAccessTokenString());
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);

        int responseCode = connection.getResponseCode();
        if(responseCode != 200) {
            LOGGER.warn("Unexpected answer HTTP Code " + responseCode);
            return null;
        }
        byte[] bytes = IOUtils.toByteArray(connection.getInputStream());
        OneClickPrincipal principal =  JSON.read(bytes, OneClickPrincipal.class);

        principal.directLink = buildDirectLink(principal, oneClickCredentials);

        return principal;
    }
    
    private static String cryptAndEncodeBase64(String textToEncrypt) throws Exception {

        byte[] key = null;
        try (FileInputStream input = new FileInputStream(Configuration.getInstance().getOneClickKeyPath())) {
            if(input != null) {
                key = IOUtils.toByteArray(input);
            }
        }

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,  new SecretKeySpec(key, "AES"), new IvParameterSpec(key));

        byte[] textToEncryptUft8Bytes = textToEncrypt.getBytes(Charsets.UTF_8);
        byte[] cryptedBytes = cipher.doFinal(textToEncryptUft8Bytes);
        String encodedBase64String = Base64.encodeBase64String(cryptedBytes);
        return encodedBase64String;
    } 

    private static String buildDirectLink(OneClickPrincipal oneClickPrincipal, OneClickCredentials oneClickCredentials) {

        try {
            long TICKS_AT_EPOCH = 621355968000000000L;
            long ticks = System.currentTimeMillis() * 10000 + TICKS_AT_EPOCH;
            // example: 636288768009520635_bbc52049-78a6-4f04-9191-cfbb29d8aaa0
            //String textToEncrypt = String.format("%d_%s", ticks, oneClickPrincipal.getId());
            String textToEncrypt = oneClickPrincipal.getId();

            //String encodedBase64String = cryptAndEncodeBase64(textToEncrypt);
            
            String encodedBase64String = Base64.encodeBase64String(textToEncrypt.getBytes(Charsets.UTF_8));
            String link = String.format("%s?nid=%s", oneClickCredentials.getOneClickUrl(), encodedBase64String);
            return link;

        } catch (Exception e) {
            LOGGER.info("could not build directlink", e);
        }

        return null;
    }

    @JsonProperty("DirectLink")
    public String getDirectLink() {
        return directLink;
    }
}
