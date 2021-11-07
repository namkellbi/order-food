package vn.fpt.orderfood.common;

import java.util.HashMap;

public class MessageConstants {
    String SUCCESS = "MSG_SUCCESS";
    String ERROR = "MSG_ERROR";
    public static final int GRID_COLUMN = 3;
    public static String TOOLBAR_TITLE = "";
    public static int TOTAL_CART_ITEM = 0;
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String FROM = "from";
    public static final String CATEGORY_ID = "category_id";
    public static final String IS_USER_LOGIN = "is_user_login";
    public static final String USERNAME = "userName";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String PHONENUMBER = "phoneNumber";
    public static final String ADDRESS = "address";
    public static final String STATUS = "status";
    public static final String ROLEID = "roleId";
    public static HashMap<String, String> CartValues = new HashMap<>();
    //filter
    public static final CharSequence[] filterValues = {" Newest to Oldest ", " Oldest to Newest ", " Price Highest to Lowest ", " Price Lowest to Highest "};
    public static final String NEW = "new";
    public static final String OLD = "old";
    public static final String HIGH = "high";
    public static final String LOW = "low";

}
