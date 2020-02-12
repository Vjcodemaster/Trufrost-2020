package app_utility;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesClass {

    SharedPreferences sharedPreferences;
    private Context _context;

    private static final String APP_PREFERENCES = "TRUFROST_PREFERENCES";

    private static final int PRIVATE_MODE = 0;

    private static final String IS_DATA_DOWNLOADED = "IS_DATA_DOWNLOADED";
    private static final String IMAGES_PATH = "IMAGES_PATH";

    //private static final String KEY_TAG_ID = "tagID";
    SharedPreferences.Editor editor;

    // Constructor
    public SharedPreferencesClass(Context context) {
        this._context = context;

        sharedPreferences = _context.getSharedPreferences(APP_PREFERENCES, PRIVATE_MODE);
        editor = sharedPreferences.edit();
        //editor.apply();
    }

    public void setDownloadStatus(boolean bValue){
        editor.putBoolean(IS_DATA_DOWNLOADED, bValue);
        editor.apply();
    }

    public boolean getDownloadStatus(){
        return sharedPreferences.getBoolean(IS_DATA_DOWNLOADED, false);
    }

    public void setImagesPath(String sImagesPath){
        editor.putString(IMAGES_PATH, sImagesPath);
        editor.apply();
    }

    public String getImagesPath(){
        return sharedPreferences.getString(IMAGES_PATH, null);
    }
}
