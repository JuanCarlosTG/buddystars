package mx.com.ioblok.buddystars.news;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.adapter.BlogElementAdapter;
import mx.com.ioblok.buddystars.customviews.CustomTextViewRegular;
import mx.com.ioblok.buddystars.utils.Constants;
import mx.com.ioblok.buddystars.utils.WebBridge;

public class DetailBlogActivity extends Activity{

    private ImageView iv_description_blog;
    private CustomTextViewRegular tv_txt_description_video;
    private ImageButton btnBackHeader;
    private CustomTextViewRegular txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_blog);
        overridePendingTransition(R.anim.slide_left_from, R.anim.slide_left);

        iv_description_blog = (ImageView)findViewById(R.id.iv_description_blog);
        tv_txt_description_video = (CustomTextViewRegular) findViewById(R.id.tv_txt_description_video);
        txtTitle = (CustomTextViewRegular) findViewById(R.id.txt_title);

        btnBackHeader = (ImageButton) findViewById(R.id.btn_back_login);
        btnBackHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                DetailBlogActivity.this.overridePendingTransition(R.anim.slide_right_from, R.anim.slide_right);
            }
        });

        Glide.with(this).load(Constants.getSpotFullImage()).into(iv_description_blog);
        tv_txt_description_video.setText(Constants.getSpotDescription());
        txtTitle.setText(Constants.getSpotTitle());
    }

}
