package android.example.com.cartel;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreference {

    private static String PREF_USER_NAME;
    private static String PREF_NAME;
    private static String PREF_USER_PHONENUMBER;
    private static String PREF_USER_NATIONALID;
    private static String PREF_USER_EMAIL;



    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserData(Context ctx, String[] userData)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userData[0]);
        editor.putString(PREF_NAME, userData[1]);
        editor.putString(PREF_USER_PHONENUMBER, userData[2]);
        editor.putString(PREF_USER_NATIONALID, userData[3]);
        editor.putString(PREF_USER_EMAIL, userData[4]);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static String getPrefName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_NAME, "");
    }

    public static String getPrefUserPhonenumber(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_PHONENUMBER, "");
    }

    public static String getPrefUserNationalid(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NATIONALID, "");
    }

    public static String getPrefUserEmail(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_EMAIL, "");
    }
}
