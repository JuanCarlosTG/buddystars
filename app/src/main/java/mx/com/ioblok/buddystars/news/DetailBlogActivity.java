package mx.com.ioblok.buddystars.news;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.customviews.CustomTextViewRegular;
import mx.com.ioblok.buddystars.utils.Constants;

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

    public void clickBack(View v) {
        finish();
        overridePendingTransition(R.anim.slide_right_from, R.anim.slide_right);
    }

    @Override
    public void onBackPressed() {
        clickBack(null);
    }

}
