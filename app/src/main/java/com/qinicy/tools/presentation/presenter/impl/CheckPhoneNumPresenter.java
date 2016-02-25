package com.qinicy.tools.presentation.presenter.impl;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.qinicy.tools.data.api.ApiParser;
import com.qinicy.tools.data.api.ApiResponse;
import com.qinicy.tools.data.api.IApiCallBack;
import com.qinicy.tools.data.entities.PhoneNumEntity;
import com.qinicy.tools.presentation.Interactor.ICheckPhoneNumInteractor;
import com.qinicy.tools.presentation.Interactor.impl.CheckPhoneNumInteractor;
import com.qinicy.tools.presentation.presenter.ICheckPhoneNumPresenter;
import com.qinicy.tools.ui.view.ICheckPhoneNumInfoView;
import com.squareup.okhttp.Request;

import java.lang.reflect.Type;

/**
 * Created by qinicy on 16/2/25.
 */
public class CheckPhoneNumPresenter implements ICheckPhoneNumPresenter {
    private Context mContext;
    private ICheckPhoneNumInfoView mCheckPhoneNumInfoView;
    private ICheckPhoneNumInteractor mCheckPhoneNumInteractor;

    public CheckPhoneNumPresenter(Context mContext, ICheckPhoneNumInfoView mCheckPhoneNumInfoView) {
        this.mContext = mContext;
        this.mCheckPhoneNumInfoView = mCheckPhoneNumInfoView;
        mCheckPhoneNumInteractor = new CheckPhoneNumInteractor();
    }

    @Override
    public void checkPhoneNumInfo(int number) {

        mCheckPhoneNumInteractor.checkPhoneNum(number, new IApiCallBack() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Type typeOfT = new TypeToken<ApiResponse<PhoneNumEntity>>(){}.getType();
                ApiResponse<PhoneNumEntity> phoneNumEntityApiResponse = ApiParser.getInstance().parseResponseFrom(response,typeOfT);

                if (phoneNumEntityApiResponse!=null)
                    mCheckPhoneNumInfoView.setPhoneNumInfo(phoneNumEntityApiResponse.getResult());

            }
        });
    }

    @Override
    public void init() {

    }
}
