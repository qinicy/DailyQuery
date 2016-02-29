package com.qinicy.tools.ui.activity;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.foxconn.crd.common.activity.BaseActivity;
import com.qinicy.tools.R;
import com.qinicy.tools.data.entities.PhoneNumEntity;
import com.qinicy.tools.presentation.presenter.ICheckPhoneNumPresenter;
import com.qinicy.tools.presentation.presenter.impl.CheckPhoneNumPresenter;
import com.qinicy.tools.ui.view.ICheckPhoneNumInfoView;
import com.wang.avi.AVLoadingIndicatorView;

public class MainActivity extends BaseActivity implements ICheckPhoneNumInfoView {

    private final String TAG = "MainActivity";
    private AppCompatAutoCompleteTextView appCompatAutoCompleteTextView;
    private TextInputLayout textInputLayout;
    private AppCompatImageButton queryBT;
    private ICheckPhoneNumPresenter mCheckPhoneNumPresenter;
    private View queryLayout, infoLayout;
    private AppCompatTextView phone_company;

    //anim
    private AnimatedVectorDrawable searchToBar;
    private AnimatedVectorDrawable barToSearch;
    private AnimatedVectorDrawable search_show;
    private AnimatedVectorDrawable search_hide;
    private float offset;
    private Interpolator interp;
    private int duration;
    private boolean expanded = false;
    private boolean show;


    private AVLoadingIndicatorView avloadingIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.

        getSupportActionBar().hide();


        init();
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void init() {
        findViews();

        mCheckPhoneNumPresenter = new CheckPhoneNumPresenter(this, this);
        mCheckPhoneNumPresenter.init();
    }

    public void animate(View view) {


        if (!expanded) {
            queryBT.setImageDrawable(searchToBar);
            searchToBar.start();
            queryBT.animate().translationX(0f).setDuration(duration).setInterpolator(interp);
            appCompatAutoCompleteTextView.animate().alpha(1f).setStartDelay(duration - 100).setDuration(100).setInterpolator(interp);
            appCompatAutoCompleteTextView.setEnabled(true);
        } else {
            queryBT.setImageDrawable(barToSearch);
            barToSearch.start();
            queryBT.animate().translationX(offset).setDuration(duration).setInterpolator(interp);
            appCompatAutoCompleteTextView.setAlpha(0f);
            appCompatAutoCompleteTextView.setEnabled(false);
            clearError();
        }
        expanded = !expanded;
    }

    private void toggleSearch(boolean show) {

        if (show) {

            queryBT.setImageDrawable(search_show);
            search_show.start();
        } else {
            queryBT.setImageDrawable(search_hide);
            search_hide.start();
        }
        this.show = show;
    }

    @Override
    public void loading(boolean loading) {
        if (loading) {
            avloadingIndicatorView.setVisibility(View.VISIBLE);
            queryLayout.setVisibility(View.GONE);
        } else {
            avloadingIndicatorView.setVisibility(View.GONE);
            queryLayout.setVisibility(View.VISIBLE);
        }
    }

    private void findViews() {
        appCompatAutoCompleteTextView = findView(R.id.editview_phone_number);
        queryBT = findView(R.id.bt_query);
        textInputLayout = findView(R.id.textLayout);
        avloadingIndicatorView = findView(R.id.avloadingIndicatorView);
        queryLayout = findView(R.id.layout_query);
        infoLayout = findView(R.id.layout_info);
        phone_company = findView(R.id.phone_company);
        searchToBar = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.anim_search_to_bar);

        barToSearch = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.anim_bar_to_search);
        search_show = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.anim_bar_show);
        search_hide = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.anim_bar_hide);
        interp = AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);
        duration = getResources().getInteger(R.integer.duration_bar);
        // iv is sized to hold the search_show+bar so when only showing the search_show icon, translate the
        // whole view left by half the difference to keep it centered
        offset = -71f * (int) getResources().getDisplayMetrics().scaledDensity;


        queryBT.setTranslationX(offset);

        appCompatAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if (start == 6 && start == s.length())
                    toggleSearch(true);
                else if (start < s.length()) {
                    if (infoLayout.getVisibility() == View.VISIBLE)
                        infoLayout.setVisibility(View.GONE);
                    if (start == 6)
                        toggleSearch(false);
                    if (start == 0)
                        animate(queryBT);

                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 11)
                    setError(getString(R.string.error_wrong_phone_number_start));
            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();

                if (length == 1) {
                    try {
                        int num = Integer.parseInt(String.valueOf(s.charAt(0)));
                        if (num != 1) {

                            s.delete(0, 1);
                            setError(getString(R.string.error_wrong_phone_number_start));
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                } else if (length == 12) {
                    s.delete(11, 12);
                    setError(getString(R.string.error_max11char_limit));
                }

            }
        });
        queryBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show) {
                    queryAction();
                } else
                    animate(v);
//

            }
        });
    }


    private void queryAction() {

        String text = appCompatAutoCompleteTextView.getText().toString();

        long number = 0;
        try {
            number = Long.parseLong(text);
            mCheckPhoneNumPresenter.checkPhoneNumInfo(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            textInputLayout.setError(getString(R.string.error_wrong_phone_number));
        }

    }

    @Override
    public void setPhoneNumInfo(PhoneNumEntity phoneNumEntity) {


        loading(false);
        infoLayout.setVisibility(View.VISIBLE);
        String phoneNumInfo = phoneNumEntity.getProvince() + phoneNumEntity.getCity() + phoneNumEntity.getCompany();
        phone_company.setText(phoneNumInfo);


    }


    @Override
    public void setError(String msg) {
        textInputLayout.setError(msg);

    }

    private void clearError() {
        textInputLayout.setError(null);
    }
}
