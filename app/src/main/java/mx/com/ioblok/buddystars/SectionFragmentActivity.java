package mx.com.ioblok.buddystars;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by omar on 4/10/16.
 */
public class SectionFragmentActivity extends AppCompatActivity {

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
        TextView txtTitle = (TextView) findViewById(R.id.txt_title);
        if (txtTitle != null) {
            txtTitle.setText(title);
        }
    }


}
