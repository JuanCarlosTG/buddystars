package mx.com.ioblok.buddystars;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import mx.com.ioblok.buddystars.customviews.CustomTextViewRegular;

/**
 * Created by noisedan on 4/6/15.
 */
public class SectionActivity extends Activity {


	/*------------*/
    /* PROPERTIES */

    static public int STATUS_BAR_COLOR = Color.parseColor("#000000");
    int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

	/*----------------*/
	/* CUSTOM METHODS */

    public void setStatusBarColor(int color) {

        /*if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {

            View statusBar = (View) findViewById(R.id.status_bar);
            if (statusBar == null) return;
            statusBar.setVisibility(View.GONE);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            View statusBar = (View) findViewById(R.id.status_bar);
            if (statusBar == null) return;

            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int statusBarHeight = getStatusBarHeight();
            statusBar.getLayoutParams().height = statusBarHeight;
            statusBar.setBackgroundColor(color);

        }*/
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void setTitle(String title) {
        CustomTextViewRegular txtTitle = (CustomTextViewRegular) findViewById(R.id.txt_title);
        if (txtTitle != null) {
            txtTitle.setText(title);
        }
    }

    public void setMainImage(int drawable) {
        ImageView mainImageHome = (ImageView) findViewById(R.id.image_header);
        if (mainImageHome != null) {
            mainImageHome.setBackgroundResource(drawable);
        }
    }





	/*--------------*/
	/* CLICK EVENTS */

    public void clickBack(View v) {
        finish();
        overridePendingTransition(R.anim.slide_right_from, R.anim.slide_right);
    }

    @Override
    public void onBackPressed() {
        clickBack(null);
    }

}
