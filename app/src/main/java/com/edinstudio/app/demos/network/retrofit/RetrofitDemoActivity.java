package com.edinstudio.app.demos.network.retrofit;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.edinstudio.app.demos.R;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by albert on 10/8/14.
 */
public class RetrofitDemoActivity extends Activity implements View.OnClickListener {
    private BaseAdapter adapter;
    private List<Post> data = new ArrayList<Post>();

    private Callback<List<Post>> postsCB = new Callback<List<Post>>() {
        @Override
        public void success(List<Post> posts, Response response) {
            data.clear();
            data.addAll(posts);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void failure(RetrofitError error) {
            error.printStackTrace();
        }
    };

    private Callback<Post> postCB = new Callback<Post>() {
        @Override
        public void success(Post post, Response response) {
            data.clear();
            data.add(post);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void failure(RetrofitError error) {
            error.printStackTrace();
        }
    };

    private Callback<Object> delPostCB = new Callback<Object>() {
        @Override
        public void success(Object o, Response response) {
            if (response.getStatus() == 204) {
                Toast.makeText(RetrofitDemoActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            error.printStackTrace();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_retrofit_demo);

        ListView lvPosts = (ListView) findViewById(R.id.lv_posts);
        adapter = new MySimpleAdapter();
        lvPosts.setAdapter(adapter);

        findViewById(R.id.btn_get_posts).setOnClickListener(this);
        findViewById(R.id.btn_get_post).setOnClickListener(this);
        findViewById(R.id.btn_add_post).setOnClickListener(this);
        findViewById(R.id.btn_update_post).setOnClickListener(this);
        findViewById(R.id.btn_modify_post).setOnClickListener(this);
        findViewById(R.id.btn_del_post).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_get_posts) {
            RestAdapterHelper.getService().listPosts(postsCB);
        } else if (id == R.id.btn_get_post) {
            RestAdapterHelper.getService().post("1", postCB);
        } else if (id == R.id.btn_add_post) {
            Post post = new Post();
            post.setTitle("Custom Title");
            post.setBody("Custom Body");
            post.setUserId("1");
            RestAdapterHelper.getService().addPost(post, postCB);
        } else if (id == R.id.btn_update_post) {
            Post post = new Post();
            post.setTitle("Updated Title");
            post.setBody("Updated Body");
            post.setUserId("1");
            post.setId("1");
            RestAdapterHelper.getService().updatePost("1", post, postCB);
        } else if (id == R.id.btn_modify_post) {
            Post post = new Post();
            post.setTitle("Modified Title");
            RestAdapterHelper.getService().modifyPost("1", post, postCB);
        } else if (id == R.id.btn_del_post) {
            RestAdapterHelper.getService().delPost("1", delPostCB);
        }
    }

    private class MySimpleAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(RetrofitDemoActivity.this).inflate(android.R.layout.simple_list_item_2, parent, false);
            }

            Post post = data.get(position);
            TextView tvTitle = (TextView) convertView.findViewById(android.R.id.text1);
            tvTitle.setText(post.getTitle());
            TextView tvBody = (TextView) convertView.findViewById(android.R.id.text2);
            tvBody.setText(post.getBody());

            return convertView;
        }
    }
}
