package app_utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "TRUFROST";

    private static final String KEY_ID = "_id";

    private static final String TABLE_ONE_MAIN_CATEGORY = "TABLE_ONE_MAIN_CATEGORY";
    private static final String TABLE_TWO_PRODUCTS = "TABLE_TWO_PRODUCTS";
    private static final String TABLE_THREE_SUB_CATEGORY = "TABLE_THREE_SUB_CATEGORY";

    //Table One
    private static final String KEY_MAIN_CATEGORY_NAME = "KEY_MAIN_CATEGORY_NAME";
    private static final String KEY_MAIN_CATEGORY_DESCRIPTION = "KEY_MAIN_CATEGORY_DESCRIPTION";
    private static final String KEY_SUB_CATEGORY_ONE_NAMES = "KEY_SUB_CATEGORY_ONE_NAMES";

    //Table Two
    private static final String KEY_SUB_CATEGORY_ONE_NAME = "KEY_SUB_CATEGORY_ONE_NAME";
    private static final String KEY_SUB_CATEGORY_TWO_NAME = "KEY_SUB_CATEGORY_TWO_NAME";
    private static final String KEY_PRODUCT_NAME = "KEY_PRODUCT_NAME";
    private static final String KEY_PRODUCT_IMAGE_PATH = "KEY_PRODUCT_IMAGE_PATH";
    private static final String KEY_PRODUCT_TECH_SPECS_KEY = "KEY_PRODUCT_TECH_SPECS_KEY";
    private static final String KEY_PRODUCT_TECH_SPECS_VALUE = "KEY_PRODUCT_TECH_SPECS_VALUE";
    private static final String KEY_PRODUCT_DESCRIPTION = "KEY_PRODUCT_DESCRIPTION";

    //Table Three
    private static final String KEY_SUB_CATEGORY_THREE_NAME = "KEY_SUB_CATEGORY_THREE_NAME";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ONE_MAIN_CATEGORY = "CREATE TABLE " + TABLE_ONE_MAIN_CATEGORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_MAIN_CATEGORY_NAME + " TEXT, "
                + KEY_MAIN_CATEGORY_DESCRIPTION + " TEXT, "
                + KEY_SUB_CATEGORY_ONE_NAMES + " TEXT)";

        String CREATE_TWO_PRODUCTS = "CREATE TABLE " + TABLE_TWO_PRODUCTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_SUB_CATEGORY_ONE_NAME + " TEXT, "
                + KEY_SUB_CATEGORY_TWO_NAME + " TEXT, "
                + KEY_PRODUCT_NAME + " TEXT, "
                + KEY_PRODUCT_IMAGE_PATH + " TEXT, "
                + KEY_PRODUCT_TECH_SPECS_KEY + " TEXT, "
                + KEY_PRODUCT_TECH_SPECS_VALUE + " TEXT, "
                + KEY_PRODUCT_DESCRIPTION + " TEXT)";

        String CREATE_THREE_SUB_CATEGORY = "CREATE TABLE " + TABLE_THREE_SUB_CATEGORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_SUB_CATEGORY_TWO_NAME + " TEXT, "
                + KEY_SUB_CATEGORY_THREE_NAME + " TEXT, "
                + KEY_PRODUCT_NAME + " TEXT, "
                + KEY_PRODUCT_IMAGE_PATH + " TEXT, "
                + KEY_PRODUCT_TECH_SPECS_KEY + " TEXT, "
                + KEY_PRODUCT_TECH_SPECS_VALUE + " TEXT, "
                + KEY_PRODUCT_DESCRIPTION + " TEXT)";

        db.execSQL(CREATE_ONE_MAIN_CATEGORY);
        db.execSQL(CREATE_TWO_PRODUCTS);
        db.execSQL(CREATE_THREE_SUB_CATEGORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ONE_MAIN_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TWO_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THREE_SUB_CATEGORY);

        // Create tables again
        onCreate(db);
    }

    public void addDataToMainCategoryTable(DatabaseHelper databaseHelper) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //values.put(KEY_ID, dataBaseHelper.getID()); // Contact Name
        values.put(KEY_MAIN_CATEGORY_NAME, databaseHelper.get_main_category_name());
        values.put(KEY_MAIN_CATEGORY_DESCRIPTION, databaseHelper.get_main_category_description());
        values.put(KEY_SUB_CATEGORY_ONE_NAMES, databaseHelper.get_sub_category_first_names());

        // Inserting Row
        db.insert(TABLE_ONE_MAIN_CATEGORY, null, values);

        db.close(); // Closing database connection
    }

    public void addDataToProductsTable(DatabaseHelper databaseHelper) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_SUB_CATEGORY_ONE_NAME, databaseHelper.get_sub_category_first_name());
        values.put(KEY_SUB_CATEGORY_TWO_NAME, databaseHelper.get_sub_category_second_name());
        values.put(KEY_PRODUCT_NAME, databaseHelper.get_product_name());
        values.put(KEY_PRODUCT_IMAGE_PATH, databaseHelper.get_product_image_path());
        values.put(KEY_PRODUCT_TECH_SPECS_KEY, databaseHelper.get_product_tech_specs());
        values.put(KEY_PRODUCT_TECH_SPECS_VALUE, databaseHelper.get_product_tech_specs_value());
        values.put(KEY_PRODUCT_DESCRIPTION, databaseHelper.get_product_description());

        // Inserting Row
        db.insert(TABLE_TWO_PRODUCTS, null, values);

        db.close(); // Closing database connection
    }

    public void addProductsToTableThreeSubCategory(DatabaseHelper databaseHelper) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_SUB_CATEGORY_TWO_NAME, databaseHelper.get_sub_category_second_name());
        values.put(KEY_SUB_CATEGORY_THREE_NAME, databaseHelper.get_sub_category_third_name());
        values.put(KEY_PRODUCT_NAME, databaseHelper.get_product_name());
        values.put(KEY_PRODUCT_IMAGE_PATH, databaseHelper.get_product_image_path());
        values.put(KEY_PRODUCT_TECH_SPECS_KEY, databaseHelper.get_product_tech_specs());
        values.put(KEY_PRODUCT_TECH_SPECS_VALUE, databaseHelper.get_product_tech_specs_value());
        values.put(KEY_PRODUCT_DESCRIPTION, databaseHelper.get_product_description());

        // Inserting Row
        db.insert(TABLE_THREE_SUB_CATEGORY, null, values);

        db.close(); // Closing database connection
    }


    public List<DatabaseHelper> getSCOneDescriptionFromMainName(String sMainCategoryName) {
        List<DatabaseHelper> dataBaseHelperList = new ArrayList<>();
        //ArrayList<String> alSubCategoryOneNames = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT " + KEY_SUB_CATEGORY_ONE_NAMES + "," + KEY_MAIN_CATEGORY_DESCRIPTION +" FROM " + TABLE_ONE_MAIN_CATEGORY + " WHERE "
                + KEY_MAIN_CATEGORY_NAME + "= '" + sMainCategoryName+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DatabaseHelper dataBaseHelper = new DatabaseHelper();
                dataBaseHelper.set_sub_category_first_names(cursor.getString(0));
                dataBaseHelper.set_main_category_description(cursor.getString(1));
                // Adding data to list
                dataBaseHelperList.add(dataBaseHelper);
                /*String s = String.valueOf(dataBaseHelperList.get(cursor.getPosition()).get_sub_category_first_names());
                alSubCategoryOneNames.add(s);*/
            } while (cursor.moveToNext());
        }

        // return recent list
        return dataBaseHelperList;
    }


    public List<String> getSCTwoFromSCOne(String sSCOneName) {
        List<DatabaseHelper> dataBaseHelperList = new ArrayList<>();
        LinkedHashSet<String> lhs = new LinkedHashSet<>();
        // Select All Query
        String selectQuery = "SELECT " + KEY_SUB_CATEGORY_TWO_NAME + " FROM " + TABLE_TWO_PRODUCTS + " WHERE "
                + KEY_SUB_CATEGORY_ONE_NAME + "= '" + sSCOneName+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DatabaseHelper dataBaseHelper = new DatabaseHelper();
                dataBaseHelper.set_sub_category_second_name(cursor.getString(0));
                // Adding data to list
                dataBaseHelperList.add(dataBaseHelper);
                String s = String.valueOf(dataBaseHelperList.get(cursor.getPosition()).get_sub_category_second_name());
                lhs.add(s);
            } while (cursor.moveToNext());
        }
        // return recent list
        return new ArrayList<>(lhs);
    }

    public List<DatabaseHelper> getProductsFromSCTwo(String sSubCategoryTwo) {
        List<DatabaseHelper> dataBaseHelperList = new ArrayList<>();
        //ArrayList<String> alSubCategoryOneNames = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT " + KEY_PRODUCT_NAME + "," + KEY_PRODUCT_IMAGE_PATH + "," + KEY_PRODUCT_TECH_SPECS_KEY +","
                + KEY_PRODUCT_TECH_SPECS_VALUE + "," + KEY_PRODUCT_DESCRIPTION + " FROM " + TABLE_TWO_PRODUCTS + " WHERE "
                + KEY_SUB_CATEGORY_TWO_NAME + "= '" + sSubCategoryTwo+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DatabaseHelper dataBaseHelper = new DatabaseHelper();
                dataBaseHelper.set_product_name(cursor.getString(0));
                dataBaseHelper.set_product_image_path(cursor.getString(1));
                dataBaseHelper.set_product_tech_specs(cursor.getString(2));
                dataBaseHelper.set_product_tech_specs_value(cursor.getString(3));
                dataBaseHelper.set_product_description(cursor.getString(4));
                // Adding data to list
                dataBaseHelperList.add(dataBaseHelper);
                /*String s = String.valueOf(dataBaseHelperList.get(cursor.getPosition()).get_sub_category_first_names());
                alSubCategoryOneNames.add(s);*/
            } while (cursor.moveToNext());
        }

        // return recent list
        return dataBaseHelperList;
    }



    public List<DatabaseHelper> getProductsFromSCThree(String sSubCategoryThree) {
        List<DatabaseHelper> dataBaseHelperList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT " + KEY_PRODUCT_NAME + "," + KEY_PRODUCT_IMAGE_PATH + "," + KEY_PRODUCT_TECH_SPECS_KEY +","
                + KEY_PRODUCT_TECH_SPECS_VALUE + KEY_PRODUCT_DESCRIPTION + " FROM " + TABLE_TWO_PRODUCTS + " WHERE "
                + KEY_SUB_CATEGORY_THREE_NAME + "= '" + sSubCategoryThree+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DatabaseHelper dataBaseHelper = new DatabaseHelper();
                dataBaseHelper.set_product_name(cursor.getString(0));
                dataBaseHelper.set_product_image_path(cursor.getString(1));
                dataBaseHelper.set_product_tech_specs(cursor.getString(2));
                dataBaseHelper.set_product_tech_specs_value(cursor.getString(3));
                dataBaseHelper.set_product_description(cursor.getString(4));
                // Adding data to list
                dataBaseHelperList.add(dataBaseHelper);
            } while (cursor.moveToNext());
        }

        // return recent list
        return dataBaseHelperList;
    }

    public int getRecordsCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_TWO_PRODUCTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);


        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

}
