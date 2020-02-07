package app_utility;

/*
 * Created by Vj on 10-Apr-17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.autochip.trufrost.ac.AllProductsFragment;
import com.autochip.trufrost.ac.R;

import java.util.ArrayList;

import static app_utility.StaticReferenceClass.SEND_SECOND_SC_TO_ALL_FRAGMENT;
import static app_utility.StaticReferenceClass.UPDATE_SUB_HEADING;


public class SecondSCPopupWindow {
    private Context context;

    public PopupWindow mPopupWindowSort;

    private LinearLayout llTVParent;

    private TextView[] tvSecondSCNames;

    private String[] sortNamesArray;
    private RadioGroup rgDialogSort;

    private int nCheckBoxFlag = 0;

    private OnFragmentInteractionListener onFragmentInteractionListener;

    private RadioButton[] radioButton;

    private  ArrayList<String> alSecondSC;


    public SecondSCPopupWindow(Context context, ArrayList<String> alSecondSC, OnFragmentInteractionListener onFragmentInteractionListener) {
        this.context = context;
        this.alSecondSC = alSecondSC;
        this.onFragmentInteractionListener = onFragmentInteractionListener;
        onCreateView();
    }

    private void onCreateView() {
        View mFilterMenuLayout = View.inflate(context, R.layout.dialog_second_sc, null);

        mPopupWindowSort = new PopupWindow(mFilterMenuLayout, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

        llTVParent = mFilterMenuLayout.findViewById(R.id.ll_tv_parent);
        tvSecondSCNames = new TextView[alSecondSC.size()];

        for (int i = 0; i <alSecondSC.size(); i++) {
            addDynamicContents(i);
            final int finalI = i;
            tvSecondSCNames[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sSecondCategoryName = tvSecondSCNames[finalI].getText().toString();
                    AllProductsFragment.mListener.onFragmentCalled(SEND_SECOND_SC_TO_ALL_FRAGMENT, sSecondCategoryName);
                    onFragmentInteractionListener.onActivityCalled(UPDATE_SUB_HEADING, sSecondCategoryName);
                    mPopupWindowSort.dismiss();
                }
            });
        }
    }

    private void addDynamicContents(int i) {
        tvSecondSCNames[i] = new TextView(context);
        tvSecondSCNames[i].setTextAppearance(context, R.style.DialogText);
        tvSecondSCNames[i].setText(alSecondSC.get(i));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,7,0,0);
        tvSecondSCNames[i].setLayoutParams(params);
        tvSecondSCNames[i].setTextColor(ResourcesCompat.getColor(context.getResources(), android.R.color.white, null));

        llTVParent.addView(tvSecondSCNames[i], new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }
}
