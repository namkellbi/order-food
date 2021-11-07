package vn.fpt.orderfood.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AlertDialog;

import vn.fpt.orderfood.MainActivity;
import vn.fpt.orderfood.R;
import vn.fpt.orderfood.common.MessageConstants;

public class Session {
    public static final String PREFER_NAME = "Food_Order";
    final int PRIVATE_MODE = 0;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    public Session(Context context) {
        try {
            this._context = context;
            pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
            editor = pref.edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setCount(String id, int value, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(id, value);
        editor.apply();
    }

    public static int getCount(String id, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(id, 0);
    }

    public String getData(String id) {
        return pref.getString(id, "");
    }

    public String getCoordinates(String id) {
        return pref.getString(id, "0");
    }

    public void setData(String id, String val) {
        editor.putString(id, val);
        editor.commit();
    }

    public void setBoolean(String id, boolean val) {
        editor.putBoolean(id, val);
        editor.commit();
    }

    public boolean getBoolean(String id) {
        return pref.getBoolean(id, false);
    }

    public void createUserLoginSession(int id, String username, String password, String email, String phoneNumber, String address, String status,String roleId) {
        editor.putBoolean(MessageConstants.IS_USER_LOGIN, true);
        editor.putInt(MessageConstants.ID, id);
        editor.putString(MessageConstants.USERNAME, username);
        editor.putString(MessageConstants.PASSWORD, password);
        editor.putString(MessageConstants.EMAIL, email);
        editor.putString(MessageConstants.PHONENUMBER, phoneNumber);
        editor.putString(MessageConstants.ADDRESS, address);
        editor.putString(MessageConstants.STATUS, status);
        editor.putString(MessageConstants.ROLEID, roleId);
        editor.commit();
    }

    public void logoutUser(Activity activity) {
        editor.clear();
        editor.commit();

        new Session(_context).setBoolean("is_first_time", true);

        Intent i = new Intent(activity, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(MessageConstants.FROM, "");
        activity.startActivity(i);
        activity.finish();
    }

    public void logoutUserConfirmation(final Activity activity) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(_context);
        alertDialog.setTitle(R.string.logout);
        alertDialog.setMessage(R.string.logout_msg);
        alertDialog.setCancelable(false);
        final AlertDialog alertDialog1 = alertDialog.create();

        // Setting OK Button
        alertDialog.setPositiveButton(R.string.yes, (dialog, which) -> {
            editor.clear();
            editor.commit();

            new Session(_context).setBoolean("is_first_time", true);

            Intent i = new Intent(activity, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra(MessageConstants.FROM, "");
            activity.startActivity(i);
            activity.finish();
        });

        alertDialog.setNegativeButton(R.string.no, (dialog, which) -> alertDialog1.dismiss());
        // Showing Alert Message
        alertDialog.show();

    }
}
