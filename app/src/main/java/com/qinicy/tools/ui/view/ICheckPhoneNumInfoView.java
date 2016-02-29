package com.qinicy.tools.ui.view;

import com.qinicy.tools.data.entities.PhoneNumEntity;

/**
 * Created by qinicy on 16/2/25.
 */
public interface ICheckPhoneNumInfoView extends IView{
    void setPhoneNumInfo(PhoneNumEntity phoneNumEntity);

    void setError(String msg);
    void loading(boolean loading);

}
