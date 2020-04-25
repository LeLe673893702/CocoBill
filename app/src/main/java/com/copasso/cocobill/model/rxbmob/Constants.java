package com.copasso.cocobill.model.rxbmob;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * Created by Administrator on 2017/2/17.
 */

public final class Constants {
    public static final  int SINGLE_SAVE = 0;
    public static final int SINGLE_DELETE = 1;
    public static final int SINGLE_UPDATE = 2;
    public static final int LIST_SAVE = 3;
    public static final int LIST_DELETE = 4;
    public static final int LIST_UPDATE = 5;

    @IntDef({SINGLE_SAVE, SINGLE_DELETE, SINGLE_UPDATE, LIST_SAVE, LIST_DELETE, LIST_UPDATE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OperationStatus {

    }

}
