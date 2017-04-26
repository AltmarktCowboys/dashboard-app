package com.spacecowboys.codegames.dashboardapp.tools;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;
import org.jboss.resteasy.client.jaxrs.internal.ClientConfiguration;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by EDraser on 26.04.17.
 */
public class RestClient {

    private final Logger logger = Logger.getLogger(RestClient.class);

    private Map<String, String> headers;
    private String uri;

    private String lastErrorMessage;
    private int lastStatus;

    public RestClient(Map<String, String> headers, String uriRequest) {
        this.headers = headers;
        this.uri = uriRequest;
    }

    public <T> T getResult(Class<T> returnType) {
        return getResult(getBuilder().buildGet(), returnType);
    }

    public <T> T getResult(Map<String, Object> queryParams, Class<T> returnType) {
        if (queryParams == null) {
            return null;
        }
        return getResult(getBuilder(queryParams).buildGet(), returnType);
    }

    private <T> T getResult(Invocation invocation, Class<T> clazz) {
        if (invocation != null) {
            try {

                logger.info(String.format("invoke response for uri: {} ", uri));
                Response response = invocation.invoke();

                lastStatus = response.getStatus();
                logger.debug(String.format("finished invoking response with status code: {}", lastStatus));

                if (lastStatus == HttpServletResponse.SC_OK) {
                    try {
                        String result = response.readEntity(String.class);
                        return mapResult(result, clazz);
                    } catch (IOException e) {
                        lastErrorMessage = e.getMessage();
                    }
                }
            } catch (ProcessingException | WebApplicationException pe) {
                lastErrorMessage = pe.getMessage();
                lastStatus = HttpServletResponse.SC_EXPECTATION_FAILED;
            }

        }
        return null;
    }

    private <T> T mapResult(String jsonString, Class<T> returnType) throws IOException {
        if (returnType.equals(String.class)) {
            return (T) jsonString;
        } else {
            ObjectMapper objectMapper = create();
            return objectMapper.readValue(jsonString, returnType);
        }
    }

    private ObjectMapper create() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    private Invocation.Builder getBuilder() {
        return getBuilder(new LinkedHashMap<>());
    }

    private Invocation.Builder getBuilder(Map<String, Object> parameters) {
        WebTarget webTarget = ClientBuilder.newClient().target(uri);

        // add the query parameters
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            if (isValidQueryParam(entry.getKey(), entry.getValue())) {
                webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
            }
        }

        Invocation.Builder invocationBuilder = webTarget
                .request(MediaType.APPLICATION_JSON);

        // set the headers
        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                invocationBuilder = invocationBuilder.header(header.getKey(), header.getValue());
            }
        }

        return invocationBuilder;
    }

    private boolean isValidQueryParam(String paramName, Object paramValue) {
        if (paramValue == null) {
            return false;
        }

        if ((paramValue instanceof String) && !((String) paramValue).isEmpty()) {
            return true;
        }
        if ((paramValue instanceof Integer)) {
            return true;
        }
        return (paramValue instanceof LocalDateTime);
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    public int getLastStatus() {
        return lastStatus;
    }

}
