package com.qinicy.tools.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foxconn.crd.common.activity.BaseActivity;
import com.qinicy.tools.R;
import com.qinicy.tools.data.entities.PhoneNumEntity;
import com.qinicy.tools.presentation.Interactor.ICheckPhoneNumInteractor;
import com.qinicy.tools.presentation.presenter.ICheckPhoneNumPresenter;
import com.qinicy.tools.presentation.presenter.impl.CheckPhoneNumPresenter;
import com.qinicy.tools.ui.view.ICheckPhoneNumInfoView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends BaseActivity implements ICheckPhoneNumInfoView {

    private AppCompatAutoCompleteTextView appCompatAutoCompleteTextView;
    private TextInputLayout textInputLayout;
    private AppCompatButton queryBT;
    private ICheckPhoneNumPresenter mCheckPhoneNumPresenter;
    private LinearLayout linearLayout;
    private TextView text_province,text_city,text_areacode,text_zip,text_company,text_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }



    @Override
    public void init() {
        findViews();
        mCheckPhoneNumPresenter = new CheckPhoneNumPresenter(this, this);
        mCheckPhoneNumPresenter.init();
    }

    private void findViews() {
        appCompatAutoCompleteTextView = findView(R.id.editview_phone_number);
        queryBT = findView(R.id.bt_query);
        textInputLayout = findView(R.id.textLayout);
        text_province = findView(R.id.text_province);
        text_city = findView(R.id.text_city);
        text_areacode = findView(R.id.text_areacode);
        text_zip = findView(R.id.text_zip);
        text_company = findView(R.id.text_company);
        text_card = findView(R.id.text_card);

        appCompatAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if (count==11) {
                    textInputLayout.setError("最多输入11位电话号码");
                }

               

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {




            }

            @Override
            public void afterTextChanged(Editable s) {
//                int length = s.length();
//                int st = 0;
//                if (length>0)
//                    st = length-1;
//                s.delete(st,length);
            }
        });
        queryBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = appCompatAutoCompleteTextView.getText().toString();
                int number = Integer.parseInt(text);
                mCheckPhoneNumPresenter.checkPhoneNumInfo(number);
            }
        });
    }

    @Override
    public void setPhoneNumInfo(PhoneNumEntity phoneNumEntity) {

        linearLayout = findView(R.id.layout_info);
        linearLayout.setVisibility(View.VISIBLE);

        text_province.setText(phoneNumEntity.getProvince());
        text_city.setText(phoneNumEntity.getCity());
        text_areacode.setText(phoneNumEntity.getAreacode());
        text_zip.setText(phoneNumEntity.getZip());
        text_company.setText(phoneNumEntity.getCompany());
        text_card.setText(phoneNumEntity.getCard());


    }

    private boolean isNumber(CharSequence str){
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
