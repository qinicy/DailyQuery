package com.qinicy.tools.presentation.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.qinicy.tools.R;
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

    private final String TAG = "CheckPhoneNumPresenter";

    public CheckPhoneNumPresenter(Context mContext, ICheckPhoneNumInfoView mCheckPhoneNumInfoView) {
        this.mContext = mContext;
        this.mCheckPhoneNumInfoView = mCheckPhoneNumInfoView;
        mCheckPhoneNumInteractor = new CheckPhoneNumInteractor();
    }

    @Override
    public void checkPhoneNumInfo(Long number) {
        mCheckPhoneNumInfoView.loading(true);
        mCheckPhoneNumInteractor.checkPhoneNum(number, new IApiCallBack() {
            @Override
            public void onError(Request request, Exception e) {
                mCheckPhoneNumInfoView.loading(false);
                mCheckPhoneNumInfoView.setError(mContext.getString(R.string.error_network_fail));
            }

            @Override
            public void onResponse(String response) {
                mCheckPhoneNumInfoView.loading(false);
                Log.d(TAG, "onResponse: \n response");
                Type typeOfT = new TypeToken<ApiResponse<PhoneNumEntity>>() {
                }.getType();
                ApiResponse<PhoneNumEntity> phoneNumEntityApiResponse = ApiParser.getInstance().parseResponseFrom(response, typeOfT);

                if (phoneNumEntityApiResponse != null)
                    handlerResponse(phoneNumEntityApiResponse);
            }
        });
    }


    @Override
    public void init() {

    }

    private void setError(String msg) {
        mCheckPhoneNumInfoView.setError(msg);
    }

    private void handlerResponse(ApiResponse<PhoneNumEntity> response) {
        {

            int code = response.getResultcode();
            switch (code) {
                case 200:
                    mCheckPhoneNumInfoView.setPhoneNumInfo(response.getResult());
                    break;
                case 203:
                    mCheckPhoneNumInfoView.setError(mContext.getString(R.string.error_no_result));
                    break;

                default:
                    break;
            }

        }
    }
}
