package mx.com.ioblok.buddystars.utils;


import java.util.ArrayList;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.adapter.ListModelMenu;

/**
 * Created by kreativeco on 11/03/16.
 */
public class Constants {

    private static boolean userOffline = false;
    private static boolean facebookLogged = false;
    private static String spotDescription = "";
    private static String spotFullImage = "";


    public static String baseURL;
    public static ArrayList<String> replaceURLVideos = new ArrayList<>();
    public static ArrayList<ListModelMenu> customListView = new ArrayList<ListModelMenu>(){{
        add(new ListModelMenu(R.string.txt_menu_data_base, R.drawable.menu_icon_database));
        add(new ListModelMenu(R.string.txt_menu_add));
        add(new ListModelMenu(R.string.txt_menu_diary));

        add(new ListModelMenu(R.string.txt_menu_register, R.drawable.menu_icon_add));
        add(new ListModelMenu(R.string.txt_menu_news));
        add(new ListModelMenu(R.string.txt_menu_portability));

        add(new ListModelMenu(R.string.txt_menu_reports, R.drawable.menu_icon_report));
        add(new ListModelMenu(R.string.txt_menu_points, R.drawable.menu_icon_points));
        add(new ListModelMenu(R.string.txt_menu_support, R.drawable.menu_icon_support));
    }};



    public static ArrayList<ListModelMenu> getCustomListView(){
        return customListView;
    }


    public static boolean isUserOffline() {
        return userOffline;
    }

    public static void setUserOffline(boolean userUnlogged) {
        Constants.userOffline = userUnlogged;
    }

    public static String getSpotDescription() {
        return spotDescription;
    }

    public static void setSpotDescription(String spotDescription) {
        Constants.spotDescription = spotDescription;
    }

    public static String getSpotFullImage() {
        return spotFullImage;
    }

    public static void setSpotFullImage(String spotFullImage) {
        Constants.spotFullImage = spotFullImage;
    }

    public static boolean isFacebookLogged() {
        return facebookLogged;
    }

    public static void setFacebookLogged(boolean facebookLogged) {
        Constants.facebookLogged = facebookLogged;
    }
}
