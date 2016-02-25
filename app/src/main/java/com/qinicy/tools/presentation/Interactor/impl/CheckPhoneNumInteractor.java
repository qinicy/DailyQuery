package com.qinicy.tools.presentation.Interactor.impl;

import com.qinicy.tools.data.api.APIs;
import com.qinicy.tools.data.api.IApiCallBack;
import com.qinicy.tools.presentation.Interactor.ICheckPhoneNumInteractor;

/**
 * Created by qinicy on 16/2/25.
 */
public class CheckPhoneNumInteractor implements ICheckPhoneNumInteractor {

    @Override
    public void checkPhoneNum(int number, IApiCallBack listener) {
        APIs.getInstance().checkPhoneNum(number,listener);
    }
}
