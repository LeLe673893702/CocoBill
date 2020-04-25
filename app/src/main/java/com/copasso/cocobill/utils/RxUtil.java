package com.copasso.cocobill.utils;

import android.app.ProgressDialog;
import android.content.Context;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 *
 * @author
 * @date 2018/11/22
 *
 */
public class RxUtil {
    public static <T> ObservableTransformer<T, T> schedulersTransformer() {
        return upstream -> upstream.unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static <T> ObservableTransformer<T,T> schedulersNextTransformer() {
        return upstream -> upstream.unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }



    public static <T> ObservableTransformer<T, T> loading(Context context, final String msg) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        return observable-> observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> progressDialog.show())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(()-> progressDialog.dismiss());
    }
}
