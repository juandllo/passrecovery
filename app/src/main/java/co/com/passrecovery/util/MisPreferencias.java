package co.com.passrecovery.util;

import android.content.Context;
import android.content.SharedPreferences;

public class MisPreferencias {

    private static MisPreferencias misPreferencias;
    private SharedPreferences sharedPreferences;

    public static MisPreferencias getInstance(Context context) {
        if (misPreferencias == null) {
            misPreferencias = new MisPreferencias(context);
        }
        return misPreferencias;
    }

    private MisPreferencias(Context context) {
        sharedPreferences = context.getSharedPreferences("",Context.MODE_PRIVATE);
    }

    public void saveData(String key,String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(key, value);
        prefsEditor.commit();
    }

    public String getData(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }
}
