package com.qinicy.tools.presentation.Interactor;

import com.qinicy.tools.data.api.IApiCallBack;

/**
 * Created by qinicy on 16/2/25.
 */
public interface ICheckPhoneNumInteractor {
    void checkPhoneNum(Long number,IApiCallBack listener);
}
