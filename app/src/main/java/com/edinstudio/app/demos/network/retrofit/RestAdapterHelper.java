package com.edinstudio.app.demos.network.retrofit;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by albert on 10/8/14.
 */
public class RestAdapterHelper {
    /**
     * work with JSONPlaceholder
     * JSONPlaceholder is a simple fake REST API for testing and prototyping.
     */
    private static final String URL = "http://192.168.1.62:3000";
    private static MyService service;

    public static MyService getService() {
        if (service == null) {
            RequestInterceptor interceptor = new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("User-Agent", System.getProperty("http.agent"));
                }
            };

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(URL)
                    .setClient(new OkClient())
                    .setRequestInterceptor(interceptor)
                    .build();
            service = restAdapter.create(MyService.class);
        }

        return service;
    }
}
