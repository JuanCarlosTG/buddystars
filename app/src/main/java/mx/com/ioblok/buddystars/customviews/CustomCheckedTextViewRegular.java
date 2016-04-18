package mx.com.ioblok.buddystars.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckedTextView;
import android.widget.TextView;

/**
 * Created by omar on 4/18/16.
 */
public class CustomCheckedTextViewRegular extends CheckedTextView {

    public CustomCheckedTextViewRegular(Context context) {
        super(context);
        init();
    }

    public CustomCheckedTextViewRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomCheckedTextViewRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomCheckedTextViewRegular(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "TelefonicaText-Regular.ttf");
        setTypeface(tf, 1);
    }

}
