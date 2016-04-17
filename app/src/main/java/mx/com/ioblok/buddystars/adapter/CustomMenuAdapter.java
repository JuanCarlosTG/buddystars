package mx.com.ioblok.buddystars.adapter;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.utils.CustomTextViewMenu;

/**
 * Created by kreativeco on 27/03/16.
 */
public class CustomMenuAdapter extends ArrayAdapter<ListModelMenu> {

    Activity activity;
    public ArrayList<ListModelMenu> customListView = null;
    int resource;
    LayoutInflater inflater;

    public CustomMenuAdapter(Activity activity, int resource, ArrayList<ListModelMenu> objects) {
        super(activity, resource, objects);
        this.activity = activity;
        this.resource = resource;
        inflater = activity.getLayoutInflater();
        customListView = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ListModelMenuHolder holder = null;

        if (row == null) {
            row = inflater.inflate(resource, parent, false);
            holder = new ListModelMenuHolder();
            holder.textView = (TextView) row.findViewById(R.id.tv_name_menu);
            //holder.textView = (CustomTextViewMenu) row.findViewById(R.id.tv_name_menu);
            holder.rlParent = (RelativeLayout) row.findViewById(R.id.rl_header);
            holder.separator = row.findViewById(R.id.list_separator);
            holder.imageMenuItem = (ImageView) row.findViewById(R.id.image_menu_item);
            row.setTag(holder);
        } else {
            holder = (ListModelMenuHolder) row.getTag();
        }

        ListModelMenu listModelMenu = customListView.get(position);
        holder.textView.setText(activity.getResources().getText(listModelMenu.getNameList()));

        if(listModelMenu.getIcMenuList() != -1){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                holder.imageMenuItem.setImageDrawable(activity.getResources().getDrawable(listModelMenu.getIcMenuList(), activity.getTheme()));
            else holder.imageMenuItem.setImageDrawable(activity.getResources().getDrawable(listModelMenu.getIcMenuList()));

        }
        //holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, activity.getResources().getDimensionPixelSize( R.dimen.tv_menu_title ));
        hideSubmenu(holder, position);

        return row;
    }

    static class ListModelMenuHolder {
        //CustomTextViewMenu textView;
        TextView textView;
        ImageView imageMenuItem;
        View separator;
        RelativeLayout rlParent;
    }

    public void hideSubmenu(ListModelMenuHolder holder, int position) {

        if(position == 0 || position == 1 || position == 3 || position == 4 || position == 8){
            holder.separator.setVisibility(View.INVISIBLE);
        }

    }

    public void setTVParams(LinearLayout linearLayout){
        for(int i=0; i<((ViewGroup)linearLayout).getChildCount(); ++i) {
            View nextChild = ((ViewGroup)linearLayout).getChildAt(i);
            if(nextChild instanceof TextView){
                //((TextView) nextChild).setTextSize(TypedValue.COMPLEX_UNIT_PX, activity.getResources().getDimensionPixelSize( R.dimen.tv_sub_menu_title ));
            }
        }
    }
}
