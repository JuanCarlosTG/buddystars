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
    private static int tagFragment = -1;

    private static String registerName = "";
    private static String registerLastName = "";
    private static String registerPhone = "";
    private static String registerMail = "";
    private static String registerSchedule = "";
    private static String registerCode = "";
    private static String registerPortability = "";
    private static String registerSim = "";


    public static String baseURL;
    public static ArrayList<String> replaceURLVideos = new ArrayList<>();
    public static ArrayList<ListModelMenu> customListView = new ArrayList<ListModelMenu>(){{
        add(new ListModelMenu(R.string.txt_menu_data_base, R.drawable.menu_icon_database));
        add(new ListModelMenu(R.string.txt_menu_add));
        add(new ListModelMenu(R.string.txt_menu_diary));

        add(new ListModelMenu(R.string.txt_menu_register, R.drawable.menu_icon_add));
        add(new ListModelMenu(R.string.txt_menu_news));
        add(new ListModelMenu(R.string.txt_menu_portability));

        add(new ListModelMenu(R.string.txt_menu_register_sim));

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

    public static int getTagFragment() {
        return tagFragment;
    }

    public static void setTagFragment(int tagFragment) {
        Constants.tagFragment = tagFragment;
    }

    public static String getRegisterName() {
        return registerName;
    }

    public static void setRegisterName(String registerName) {
        Constants.registerName = registerName;
    }

    public static String getRegisterLastName() {
        return registerLastName;
    }

    public static void setRegisterLastName(String registerLastName) {
        Constants.registerLastName = registerLastName;
    }

    public static String getRegisterPhone() {
        return registerPhone;
    }

    public static void setRegisterPhone(String registerPone) {
        Constants.registerPhone = registerPone;
    }

    public static String getRegisterMail() {
        return registerMail;
    }

    public static void setRegisterMail(String registerMail) {
        Constants.registerMail = registerMail;
    }

    public static String getRegisterCode() {
        return registerCode;
    }

    public static void setRegisterCode(String registerCode) {
        Constants.registerCode = registerCode;
    }

    public static String getRegisterPortability() {
        return registerPortability;
    }

    public static void setRegisterPortability(String registerPortability) {
        Constants.registerPortability = registerPortability;
    }

    public static String getRegisterSim() {
        return registerSim;
    }

    public static void setRegisterSim(String registerSim) {
        Constants.registerSim = registerSim;
    }

    public static String getRegisterSchedule() {
        return registerSchedule;
    }

    public static void setRegisterSchedule(String registerSchedule) {
        Constants.registerSchedule = registerSchedule;
    }

    public static void clear(){
        Constants.registerName = "";
        Constants.registerLastName = "";
        Constants.registerMail = "";
        Constants.registerPhone = "";
        Constants.registerCode = "";
        Constants.registerPortability = "";
        Constants.registerSchedule = "";
        Constants.registerSim = "";
    }

}
