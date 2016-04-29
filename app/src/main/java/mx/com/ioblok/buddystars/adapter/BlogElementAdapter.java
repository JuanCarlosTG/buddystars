package mx.com.ioblok.buddystars.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.customviews.CustomTextViewRegular;
import mx.com.ioblok.buddystars.news.FBLoginActivity;
import mx.com.ioblok.buddystars.utils.Constants;

/**
 * Created by kreativeco on 22/02/16.
 */
public class BlogElementAdapter extends RecyclerView.Adapter<BlogElementAdapter.BlogViewHolder> {

    private JSONArray jsonArrayBlog;
    Activity activity;
    private static Activity blogActivity;
    private static Context context;

    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageBlog;
        public CustomTextViewRegular txtBlog;
        public String descriptionBlog;
        public String urlImageBlog;
        public String titleBlog;
        public String linkBlog;

        public BlogViewHolder(View itemView) {
            super(itemView);

            CardView cardViewItem = (CardView) itemView;
            cardViewItem.setCardBackgroundColor(Color.argb(0, 255, 255, 255));

            imageBlog = (ImageView) cardViewItem.findViewById(R.id.image_blog);
            txtBlog = (CustomTextViewRegular) cardViewItem.findViewById(R.id.txt_blog);

            cardViewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constants.setSpotDescription(descriptionBlog);
                    Constants.setSpotFullImage(urlImageBlog);
                    Constants.setSpotTitle(titleBlog);
                    Constants.setSpotLink(linkBlog);

                    Intent fbLogin = new Intent(blogActivity, FBLoginActivity.class);
                    blogActivity.startActivity(fbLogin);
                }
            });
        }

    }

    public BlogElementAdapter(JSONArray blog, Activity activity) {
        this.jsonArrayBlog = blog;
        this.activity = activity;
        blogActivity = activity;
        this.context = activity.getBaseContext();
    }

    @Override
    public BlogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View blogView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_blog, parent, false);
        return new BlogViewHolder(blogView);
    }

    @Override
    public void onBindViewHolder(final BlogViewHolder holder, int position) {

        try {
            JSONObject blog = jsonArrayBlog.getJSONObject(position);


            String id = blog.getString("id");
            String blogTitle = blog.getString("title");
            String blogDescription = blog.getString("description");
            String blogReleased = blog.getString("released");
            String blogFull = blog.getString("full");
            String blogLink = blog.getString("link");

            blogFull = blogFull.equals("null") ? "http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg" : blogFull;
            Glide.with(activity).load(blogFull).into(holder.imageBlog);

            holder.txtBlog.setText(blogTitle);
            holder.descriptionBlog = blogDescription;
            holder.urlImageBlog = blogFull;
            holder.titleBlog = blogTitle;
            holder.linkBlog = blogLink;

        } catch (JSONException jsonE) {

        }

    }

    @Override
    public int getItemCount() {
        return jsonArrayBlog.length();
    }

}
