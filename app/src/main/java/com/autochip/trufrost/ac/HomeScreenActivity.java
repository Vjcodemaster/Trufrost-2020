package com.autochip.trufrost.ac;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import app_utility.AnimationProgressBar;
import app_utility.Constants;
import app_utility.DatabaseHandler;
import app_utility.DatabaseHelper;
import app_utility.OnFragmentInteractionListener;
import app_utility.PermissionHandler;
import app_utility.SharedPreferencesClass;

import static app_utility.PermissionHandler.WRITE_PERMISSION;
import static app_utility.PermissionHandler.hasPermissions;
import static app_utility.StaticReferenceClass.WRITE_PERMISSION_CODE;

public class HomeScreenActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private RecyclerView rvMainCategory;
    private ViewPager mViewPagerSlideShow;

    HomeScreenViewPagerAdapter homeScreenViewPagerAdapter;

    OnFragmentInteractionListener mListener;

    TextView tvHeading, tvSubHeading;
    LinearLayout llHeadingParent;
    MaterialCardView mcvSubHeading;

    AnimationProgressBar circularProgressBar;
    SharedPreferencesClass sharedPreferencesClass;

    private int nPermissionFlag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mListener = this;
        sharedPreferencesClass = new SharedPreferencesClass(HomeScreenActivity.this);

        /*if (!sharedPreferencesClass.getDownloadStatus()) {
            circularProgressBar = new AnimationProgressBar(HomeScreenActivity.this);
            circularProgressBar.setCanceledOnTouchOutside(false);
            circularProgressBar.setCancelable(false);
            circularProgressBar.show();
            TrufrostAsyncTask trufrostAsyncTask = new TrufrostAsyncTask();
            trufrostAsyncTask.execute("1");
        } else {
            initViews();
            updateViews();
        }*/


    }

    public void initViews() {
        tvHeading = findViewById(R.id.tv_heading);
        tvSubHeading = findViewById(R.id.tv_sub_heading);
        mcvSubHeading = findViewById(R.id.mcv_sub_heading);
        llHeadingParent = findViewById(R.id.ll_heading);

        rvMainCategory = findViewById(R.id.rv_main_category);
        LinearLayoutManager mLinearLayoutManager;
        mLinearLayoutManager = new GridLayoutManager(HomeScreenActivity.this, 2);
        mLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvMainCategory.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        rvMainCategory.setHasFixedSize(true);
        rvMainCategory.setLayoutManager(mLinearLayoutManager);

        mViewPagerSlideShow = findViewById(R.id.viewpager_slideshow);

        mViewPagerSlideShow.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {


            }
        });

        mViewPagerSlideShow.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //nCount++;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        final Handler handler = new Handler();
        final int delay = 8000; //milliseconds

        handler.postDelayed(new Runnable() {
            public void run() {
                //do something
                if (mViewPagerSlideShow.getCurrentItem() == homeScreenViewPagerAdapter.getCount() - 1) {
                    mViewPagerSlideShow.setCurrentItem(0);
                } else
                    mViewPagerSlideShow.setCurrentItem((mViewPagerSlideShow.getCurrentItem()) + 1);
                handler.postDelayed(this, delay);
            }
        }, delay);

        /*Fragment fragmentSubCategoryFirst = SubCategoryManagerFragment.newInstance("","");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.ll_container, fragmentSubCategoryFirst);
        ft.addToBackStack(null);
        ft.commit();*/
        /*
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                // force transform with a 1 pixel nudge
                mViewPagerSlideShow.beginFakeDrag();
                mViewPagerSlideShow.fakeDragBy(0.1f);
                mViewPagerSlideShow.endFakeDrag();
            }
        };
        handler.postDelayed(r, 30);*/
    }

    public void updateViews() {
        homeScreenViewPagerAdapter = new HomeScreenViewPagerAdapter(HomeScreenActivity.this);
        mViewPagerSlideShow.setAdapter(homeScreenViewPagerAdapter);
        MainCategoryRVAdapter imageViewRVAdapter = new MainCategoryRVAdapter(rvMainCategory, mListener);
        rvMainCategory.setAdapter(imageViewRVAdapter);
    }

    private void downloadData(){
        if (!sharedPreferencesClass.getDownloadStatus()) {
            circularProgressBar = new AnimationProgressBar(HomeScreenActivity.this);
            circularProgressBar.setCanceledOnTouchOutside(false);
            circularProgressBar.setCancelable(false);
            circularProgressBar.show();
            TrufrostAsyncTask trufrostAsyncTask = new TrufrostAsyncTask();
            trufrostAsyncTask.execute("1");
        } else {
            initViews();
            updateViews();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int PERMISSION_ALL, String permissions[], int[] grantResults) {
        StringBuilder sMSG = new StringBuilder();
        if (PERMISSION_ALL == WRITE_PERMISSION_CODE) {
            for (String sPermission : permissions) {
                switch (sPermission) {
                    case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeScreenActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                //Show permission explanation dialog...
                                //showPermissionExplanation(SignInActivity.this.getResources().getString(R.string.phone_explanation));
                                //Toast.makeText(SignInActivity.this, "not given", Toast.LENGTH_SHORT).show();
                                sMSG.append("WRITE_EXTERNAL_STORAGE, ");
                                nPermissionFlag = 0;
                            } else {
                                //Never ask again selected, or device policy prohibits the app from having that permission.
                                //So, disable that feature, or fall back to another situation...
                                //@SuppressWarnings("unused") AlertDialogs alertDialogs = new AlertDialogs(HomeScreen.this, 1, mListener);
                                //Toast.makeText(SignInActivity.this, "permission never ask", Toast.LENGTH_SHORT).show();
                                //showPermissionExplanation(HomeScreenActivity.this.getResources().getString(R.string.phone_explanation));
                                sMSG.append("WRITE_EXTERNAL_STORAGE, ");
                                nPermissionFlag = 0;
                            }
                        } else {
                            downloadData();
                        }
                        break;
                }
            }
            if (!sMSG.toString().equals("") && !sMSG.toString().equals(" ")) {
                PermissionHandler permissionHandler = new PermissionHandler(HomeScreenActivity.this, 0, sMSG.toString(), nPermissionFlag);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case WRITE_PERMISSION_CODE:
                downloadData();
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (!hasPermissions(HomeScreenActivity.this, WRITE_PERMISSION)) {
            ActivityCompat.requestPermissions(HomeScreenActivity.this, WRITE_PERMISSION, WRITE_PERMISSION_CODE);
        } else {
            downloadData();
        }
    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fl_container);
            //currentFragment.getClass().getName();
            //String fragmentTag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
            if (currentFragment.getClass().getName().equals(AllProductsFragment.class.getName())) {
                mcvSubHeading.setVisibility(View.GONE);
            }
        } else
            llHeadingParent.setVisibility(View.GONE);

        super.onBackPressed();
    }

    @Override
    public void onFragmentCalled(int nCase, String sResult) {
        Constants constants = Constants.values()[nCase];
        switch (constants) {
            case OPEN_FRAGMENT_MANAGER:
                Toast.makeText(HomeScreenActivity.this, "Triggered", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onActivityCalled(int nCase, String sResult) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Constants constants = Constants.values()[nCase];
        switch (constants) {
            case OPEN_FRAGMENT_MANAGER:
                //Toast.makeText(HomeScreenActivity.this, "Triggered", Toast.LENGTH_LONG).show();
                Fragment fragmentSubCategoryFirst = SubCategoryManagerFragment.newInstance(sResult, "");
                ft.add(R.id.fl_container, fragmentSubCategoryFirst);
                ft.addToBackStack(null);
                ft.commit();
                tvHeading.setText(sResult.trim());
                llHeadingParent.setVisibility(View.VISIBLE);
                break;
            case OPEN_ALL_PRODUCTS:
                Fragment allProductsFragment = AllProductsFragment.newInstance(sResult, "");
                ft.add(R.id.fl_container, allProductsFragment);
                ft.addToBackStack(null);
                ft.commit();
                tvSubHeading.setText(sResult);
                mcvSubHeading.setVisibility(View.VISIBLE);
                break;
            case OPEN_INDIVIDUAL_PRODUCT_FRAGMENT:
                Fragment individualProductFragment = IndividualProductFragment.newInstance(sResult, "");
                ft.add(R.id.fl_container, individualProductFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
           /* case MAIN_CATEGORY_UPDATE:
                break;*/
            case SUB_CATEGORY_HEADING_UPDATE:
                tvSubHeading.setText(sResult);
                mcvSubHeading.setVisibility(View.VISIBLE);
                break;
        }
    }

    public class TrufrostAsyncTask extends AsyncTask<String, Void, String> {

        String sStorageLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "Trufrost";
        int type;
        boolean isImageFolderUnzipped;

        @Override
        protected String doInBackground(String... strings) {
            type = Integer.valueOf(strings[0]);
            switch (type) {
                case 1:
                    isImageFolderUnzipped = unzipImagesFile(HomeScreenActivity.this, "trufrost.zip", sStorageLocation, true);
                    break;
                case 2:
                    fetchDataFromJsonFile();
                    break;

            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            switch (type) {
                case 1:
                    TrufrostAsyncTask trufrostAsyncTask = new TrufrostAsyncTask();
                    trufrostAsyncTask.execute("2");
                    break;
                case 2:
                    initViews();
                    updateViews();
                    sharedPreferencesClass.setDownloadStatus(true);

                    if (circularProgressBar != null) {
                        circularProgressBar.dismiss();
                    }
                    break;
            }
        }

        private void fetchDataFromJsonFile() {
            DatabaseHandler dbHandler = new DatabaseHandler(HomeScreenActivity.this);

            ArrayList<String> alSubCategoryOneNames = new ArrayList<>();
            //ArrayList<String> alSubCategoryTwoNames = new ArrayList<>();
            //ArrayList<String> alSubCategoryThreeNames = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(loadJSONFromAsset());

                JSONArray jsonArrayItems = jsonObject.getJSONArray("items");
                for (int i = 0; i < jsonArrayItems.length(); i++) {
                    JSONObject joItems = jsonArrayItems.getJSONObject(i);
                    String sMainCategoryName = joItems.getString("main_category");
                    String sMainDescription = joItems.getString("main_description");

                    JSONArray jaSubCategoryOne = joItems.getJSONArray("sub_cat_one");
                    for (int j = 0; j < jaSubCategoryOne.length(); j++) {
                        JSONObject joSubCategoryOne = jaSubCategoryOne.getJSONObject(j);
                        String sSubCategoryOneName = joSubCategoryOne.getString("name");
                        alSubCategoryOneNames.add(sSubCategoryOneName);

                        JSONArray jaSubCategoryTwo = joSubCategoryOne.getJSONArray("sub_cat_two");
                        for (int k = 0; k < jaSubCategoryTwo.length(); k++) {
                            JSONObject joSubCategoryTwo = jaSubCategoryTwo.getJSONObject(k);
                            String sSubCategoryTwoName = joSubCategoryTwo.getString("name");
                            //alSubCategoryTwoNames.add(sSubCategoryTwoName);

                            if (joSubCategoryTwo.has("sub_cat_three")) {
                                JSONArray jaSubCategoryThree = joSubCategoryTwo.getJSONArray("sub_cat_three");
                                for (int l = 0; l < jaSubCategoryThree.length(); l++) {
                                    JSONObject joSubCategoryThree = jaSubCategoryThree.getJSONObject(l);
                                    String sSubCategoryThreeName = joSubCategoryThree.getString("name");
                                    //alSubCategoryThreeNames.add(sSubCategoryThreeName);

                                    Log.e(sMainCategoryName, "fromSub_CAT_THREE");
                                    Log.e(sMainCategoryName, sSubCategoryOneName + " " + sSubCategoryTwoName+ " " +sSubCategoryThreeName);
                                    JSONArray jaProducts = joSubCategoryThree.getJSONArray("product_name"); //joSubCategoryTwo
                                    for (int m = 0; m < jaProducts.length(); m++) {
                                        JSONObject joProducts = jaProducts.getJSONObject(m);
                                        String sProductName = joProducts.getString("name");
                                        //String sImagePath = joProducts.getString("image_path");
                                        String sProductDescription = joProducts.getString("description");
                                        String sTechSpecKey = joProducts.getString("product_heading");
                                        String sTechSpecValue = joProducts.getString("product_value");

                                        DatabaseHelper databaseHelper = new DatabaseHelper();
                                        databaseHelper.set_sub_category_second_name(sSubCategoryTwoName);
                                        databaseHelper.set_sub_category_third_name(sSubCategoryThreeName);
                                        databaseHelper.set_product_name(sProductName);
                                        databaseHelper.set_product_image_path(sStorageLocation + File.separator + sProductName + ".jpg");
                                        databaseHelper.set_product_tech_specs(sTechSpecKey);
                                        databaseHelper.set_product_tech_specs_value(sTechSpecValue);
                                        databaseHelper.set_product_description(sProductDescription);
                                        dbHandler.addProductsToTableThreeSubCategory(databaseHelper);

                                    }
                                }
                            } else {
                                Log.e(sMainCategoryName, "fromSub_CAT_TWO");
                                Log.e(sMainCategoryName, sSubCategoryOneName +" " + sSubCategoryTwoName);
                                JSONArray jaProducts = joSubCategoryTwo.getJSONArray("product_name");
                                for (int m = 0; m < jaProducts.length(); m++) {
                                    JSONObject joProducts = jaProducts.getJSONObject(m);
                                    String sProductName = joProducts.getString("name");
                                    //String sImagePath = joProducts.getString("image_path");
                                    String sProductDescription = joProducts.getString("description");
                                    String sTechSpecKey = joProducts.getString("product_heading");
                                    String sTechSpecValue = joProducts.getString("product_value");

                                    DatabaseHelper databaseHelper = new DatabaseHelper();
                                    databaseHelper.set_sub_category_first_name(sSubCategoryOneName);
                                    databaseHelper.set_sub_category_second_name(sSubCategoryTwoName);
                                    databaseHelper.set_product_name(sProductName);
                                    databaseHelper.set_product_image_path(sStorageLocation + File.separator + sProductName + ".jpg");
                                    databaseHelper.set_product_tech_specs(sTechSpecKey);
                                    databaseHelper.set_product_tech_specs_value(sTechSpecValue);
                                    databaseHelper.set_product_description(sProductDescription);
                                    dbHandler.addDataToProductsTable(databaseHelper);
                                }
                            }
                        }
                    }

                    DatabaseHelper databaseHelper = new DatabaseHelper();
                    databaseHelper.set_main_category_name(sMainCategoryName);
                    databaseHelper.set_main_category_description(sMainDescription);
                    databaseHelper.set_sub_category_first_names(TextUtils.join(",", alSubCategoryOneNames));
                    dbHandler.addDataToMainCategoryTable(databaseHelper);

                    int n = dbHandler.getRecordsCount();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * unzips the bundle zip file into Internal Storage
         *
         * @param context:         {@link Context} instance
         * @param zipFileName:     zip file name kept in assets
         * @param storageLocation: internal storage location for copying slider games
         */
        //@WorkerThread
        boolean unzipImagesFile(Context context, String zipFileName, String storageLocation, boolean fromAssets) {
            try {
                InputStream fileInputStream;
                if (fromAssets) {
                    AssetManager assetManager = context.getAssets();
                    fileInputStream = assetManager.open(zipFileName);
                } else
                    fileInputStream = new FileInputStream(context.getFilesDir().getAbsolutePath()
                            + File.separator + zipFileName);
                File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        + "/Trufrost");

                if (!storageDir.exists()) {
                    storageDir.mkdirs();
                }
                ZipInputStream in = new ZipInputStream(fileInputStream);
                ZipEntry zipEntry;
                FileOutputStream out = null;
                while ((zipEntry = in.getNextEntry()) != null) {
                    Log.v("Decompress", "Unzipping " + zipEntry.getName());
                    if (zipEntry.isDirectory()) {
                        createDirectory(storageLocation + File.separator, zipEntry.getName());
                    } else {
                        out = new FileOutputStream(storageLocation + File.separator + zipEntry.getName()); //sFolderName+ File.separator +
                        copyFile(in, out);
                    }
                }

                try {
                    in.close();
                } catch (IOException e) {
                    Log.e("tag", "Failed to copy asset file: " + zipEntry.getName(), e);
                }

                if (out != null) {
                    try {
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        Log.e("tag", "Failed to copy asset file: " + zipEntry.getName(), e);
                    }
                }
                Log.d("Unzip", "Unzipping complete. path :  " + storageLocation);

                return true;
            } catch (Exception e) {
                Log.e("Decompress", "unzip", e);
                Log.d("Unzip", "Unzipping failed");
            }
            return false;
        }

        /**
         * creates a directory if not exists
         *
         * @param directoryPath: path at which directory is to be created
         * @param name:          directory name
         */
        private void createDirectory(String directoryPath, String name) {
            File file = new File(directoryPath + name);
            if (!file.isDirectory()) {
                file.mkdirs();
            }
        }

        /**
         * copies the files contents
         *
         * @param in:  {@link InputStream} stream of input data
         * @param out: {@link OutputStream} stream of putput data
         * @throws IOException to throw IO error
         */
        private void copyFile(InputStream in, OutputStream out) throws IOException {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        }

        /*private String copyImageFromAssetFolder(String sImageName){
            String savedImagePath = null;
            try {
                InputStream is = getAssets().open("images/commercial_kitchen" +".jpg");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                savedImagePath = saveImage(bitmap, sImageName);

                // Write to SD Card
            } catch (Exception e){
                e.printStackTrace();
            }
            return savedImagePath;
        }

        private String saveImage(Bitmap image, String sImageName) {
            String savedImagePath = null;

            //Date d = new Date();
            //CharSequence s = DateFormat.format("MM-dd-yy hh-mm-ss", d.getTime());
            String imageFileName = sImageName + ".jpg";
            File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    + "/Kiosk");
            boolean success = true;
            if (!storageDir.exists()) {
                success = storageDir.mkdirs();
            }
            if (success) {
                File imageFile = new File(storageDir, imageFileName);
                savedImagePath = imageFile.getAbsolutePath();
                try {
                    OutputStream fOut = new FileOutputStream(imageFile);
                    image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                    fOut.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return savedImagePath;
        }*/

        String loadJSONFromAsset() {
            String json;
            try {
                InputStream is = getAssets().open("final_specs.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, StandardCharsets.UTF_8);
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }
    }
}
