package com.copasso.cocobill.presenter;


import com.copasso.cocobill.base.BaseObserver;
import com.copasso.cocobill.base.RxPresenter;
import com.copasso.cocobill.model.bean.local.BBill;
import com.copasso.cocobill.model.repository.LocalRepository;
import com.copasso.cocobill.presenter.contract.MonthListContract;
import com.copasso.cocobill.utils.BillUtils;
import com.copasso.cocobill.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.fuseable.ScalarCallable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhouas666 on 2019-01-08
 */
public class MonthListPresenter extends RxPresenter<MonthListContract.View> implements MonthListContract.Presenter{

    private String TAG="MonthListPresenter";

    @Override
    public void getMonthList(String id, String year, String month) {
        LocalRepository.getInstance().getBBillByUserIdWithYM(id, year, month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<BBill>>() {
                    @Override
                    protected void onSuccees(List<BBill> bBills) throws Exception {
                        mView.loadDataSuccess(BillUtils.packageDetailList(bBills));
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mView.onFailure(e);
                    }
                });
    }

    @Override
    public void getYearList(String id, String year) {

        Disposable disposable = Observable.fromArray(1,2,3,4,5,6,7,8,9,10,11,12)
                .compose(RxUtil.schedulersTransformer())
                .concatMap((Function<Integer, ObservableSource<List<BBill>>>) month ->
                        LocalRepository.getInstance().getBBillByUserIdWithYM(id, year, String.valueOf(month))).collect((Callable<ArrayList<BBill>>) ArrayList::new, ArrayList::addAll)
                .subscribe(bBills -> mView.loadDataSuccess(BillUtils.packageDetailList(bBills)),
                        throwable -> mView.onFailure(throwable));
    }

    @Override
    public void deleteBill(Long id) {
        LocalRepository.getInstance().deleteBBillById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Long>() {
                    @Override
                    protected void onSuccees(Long l) throws Exception {
                        mView.onSuccess();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mView.onFailure(e);
                    }
                });
    }

    @Override
    public void updateBill(BBill bBill) {
        LocalRepository.getInstance()
                .updateBBill(bBill)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BBill>() {
                    @Override
                    protected void onSuccees(BBill bBill) throws Exception {
                        mView.onSuccess();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mView.onFailure(e);
                    }
                });
    }
}
