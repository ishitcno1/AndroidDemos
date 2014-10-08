package com.edinstudio.app.demos.network.retrofit;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by albert on 10/8/14.
 */
public interface MyService {
    @GET("/posts")
    void listPosts(Callback<List<Post>> cb);

    @GET("/posts/{id}")
    void post(@Path("id") String id, Callback<Post> cb);

    @POST("/posts")
    void addPost(@Body Post post, Callback<Post> cb);

    @PUT("/posts/{id}")
    void updatePost(@Path("id") String id, @Body Post post, Callback<Post> cb);

    @PATCH("/posts/{id}")
    void modifyPost(@Path("id") String id, @Body Post post, Callback<Post> cb);

    @DELETE("/posts/{id}")
    void delPost(@Path("id") String id, Callback<Object> cb);
}
