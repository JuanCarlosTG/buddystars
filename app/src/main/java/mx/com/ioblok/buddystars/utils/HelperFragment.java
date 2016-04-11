package mx.com.ioblok.buddystars.utils;

import android.app.Activity;
import android.app.ListFragment;

/**
 * Created by omar on 4/11/16.
 */
public class HelperFragment extends ListFragment {

    OnHelperFragmentSelectedListener helperFragmentCallBack;

    // Container Activity must implement this interface
    public interface OnHelperFragmentSelectedListener {
        public void onFragmentSelected(int position);
    }

}
