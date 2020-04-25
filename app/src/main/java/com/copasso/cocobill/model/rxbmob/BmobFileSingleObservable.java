package com.copasso.cocobill.model.rxbmob;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author 刺雒
 * @what
 * @date 2018/2/9
 */

public class BmobFileSingleObservable extends Observable<String> {
    private BmobFile mBmobFile;

    public BmobFileSingleObservable(BmobFile bmobFile) {
        mBmobFile = bmobFile;
    }

    @Override
    protected void subscribeActual(Observer<? super String> observer) {
        Callback callback = new Callback(observer, mBmobFile);
        observer.onSubscribe(callback);
        mBmobFile.uploadblock(callback);
    }

    private final static class Callback extends UploadFileListener implements Disposable {
        private Observer<? super String> mObservable;
        private BmobFile mBmobFile;

        public Callback(Observer<? super String> observable, BmobFile bmobFile) {
            mObservable = observable;
            this.mBmobFile = bmobFile;
        }

        @Override
        public void done(BmobException e) {
            if (e == null) {
                mObservable.onNext(mBmobFile.getFileUrl());
            } else {
                mObservable.onError(e);
            }

            mObservable.onComplete();
        }

        @Override
        public void dispose() {

        }

        @Override
        public boolean isDisposed() {
            return false;
        }
    }
}
