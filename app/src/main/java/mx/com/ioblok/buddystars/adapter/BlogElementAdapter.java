package mx.com.ioblok.buddystars.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mx.com.ioblok.buddystars.R;

/**
 * Created by kreativeco on 22/02/16.
 */
public class BlogElementAdapter extends RecyclerView.Adapter<BlogElementAdapter.BlogViewHolder> {

    private JSONArray jsonArrayBlog;
    Activity activity;
    private static Context context;

    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageBlog;
        public TextView txtBlog;

        public BlogViewHolder(View itemView) {
            super(itemView);

            CardView cardViewItem = (CardView) itemView;
            cardViewItem.setCardBackgroundColor(Color.argb(0, 255, 255, 255));

            imageBlog = (ImageView) cardViewItem.findViewById(R.id.image_blog);
            txtBlog = (TextView) cardViewItem.findViewById(R.id.txt_blog);

            /*btnContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMenuActions(v);
                }
            });*/
        }

        public void showMenuActions(View v){

        }

    }

    public BlogElementAdapter(JSONArray blog, Activity activity) {
        this.jsonArrayBlog = blog;
        this.activity = activity;
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

            String blogTitle = blog.getString("title");
            String blogDescriotion = blog.getString("description");
            String blogReleased = blog.getString("released");
            String blogThumb = blog.getString("full");


            blogThumb = blogThumb.equals("null") ? "http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg" : blogThumb;
            Glide.with(activity).load(blogThumb).into(holder.imageBlog);

            /*Glide.with(activity).load(photoString).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.farmerImage) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.farmerImage.setImageDrawable(circularBitmapDrawable);
                }
            });*/

            holder.txtBlog.setText(blogTitle);

        } catch (JSONException jsonE) {

        }

    }

    @Override
    public int getItemCount() {
        return jsonArrayBlog.length();
    }

    private void showMenuActions(){
    }

}
