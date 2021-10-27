package vn.fpt.orderfood.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "orderfood";
    public static int VERSION = 1;

    //TABLE USER
    public static final String TABLE_USER = "user";
    public static final String COLUMN_USERID = "userId";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PHONE = "phoneNumber";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_STATUS = "status";
    public static final String ROLE_ID = "roleId";

    //TABLE STATISTIC
    public static final String TABLE_STATISTIC = "statistic";
    public static final String COLUMN_STATISTIC_ID = "statisticId";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_NETINCOME = "netIncome";

    //TABLE ROLE
    public static final String TABLE_ROLE = "role";
    public static final String COLUMN_ROLE_ID = "roleId";
    public static final String COLUMN_ROLE_NAME = "roleName";
    public static final String COLUMN_ROLE_DESCRIPTION = "roleDescription";

    //TODO : Viết cho các bảng còn lại


    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //USER
        String sqlUser = "CREATE TABLE "+TABLE_USER+" ("
                +COLUMN_USERID+" INTEGER PRIMARY KEY, "
                +COLUMN_USERNAME+" TEXT, "
                +COLUMN_PASSWORD+" TEXT, "
                +COLUMN_PHONE+" TEXT, "
                +COLUMN_EMAIL+" TEXT, "
                +COLUMN_ADDRESS+" TEXT, "
                +COLUMN_STATUS+" INTEGER, "
                +ROLE_ID+" INTEGER)";


     //TODO : Viết cho các bảng còn lại
        db.execSQL(sqlUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
