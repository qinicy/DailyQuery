package com.foxconn.crd.common.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by qinicy on 16/1/28.
 */
public abstract class BaseActivity extends AppCompatActivity{
    public <T extends View> T findView(int id){
        return (T)this.findViewById(id);
    }
}
