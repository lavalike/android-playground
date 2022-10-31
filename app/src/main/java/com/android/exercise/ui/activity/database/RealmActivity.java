package com.android.exercise.ui.activity.database;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.domain.realm.User;
import com.android.exercise.util.T;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Realm数据库
 * created by wangzhen on 2016/11/07
 */
public class RealmActivity extends BaseActivity {

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm);
        ButterKnife.bind(this);
        mRealm = Realm.getDefaultInstance();
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_realm));
    }

    @OnClick({R.id.btn_realm_insert, R.id.btn_realm_delete, R.id.btn_realm_query_async, R.id.btn_realm_batch_insert})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_realm_insert:
                //使用事务块
                final User user = new User();
                user.setName("张三");
                user.setAge(24);
//                mRealm.copyToRealm(user);
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(user);
                        T.get(mContext).toast("新增一条");
                    }
                });
                break;
            case R.id.btn_realm_delete:
                final RealmResults<User> usersDelete = mRealm.where(User.class).findAll();
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        usersDelete.deleteAllFromRealm();
                        T.get(mContext).toast("已全部删除");
                    }
                });
                break;
            case R.id.btn_realm_query_async:
                final RealmResults<User> allAsync = mRealm.where(User.class).findAllAsync();
                allAsync.addChangeListener(new RealmChangeListener<RealmResults<User>>() {
                    @Override
                    public void onChange(RealmResults<User> element) {
                        List<User> list = mRealm.copyFromRealm(element);
                        T.get(mContext).toast("异步查询共" + list.size() + "条");
                        allAsync.removeAllChangeListeners();
                    }
                });
                break;
            case R.id.btn_realm_batch_insert:
                final List<User> userList = new ArrayList<>();
                User item;
                for (int i = 0; i < 10000; i++) {
                    item = new User();
                    item.setName(UUID.randomUUID().toString());
                    item.setAge(24);
                    userList.add(item);
                }
                final long timeStart = System.currentTimeMillis();
                mRealm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(userList);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        long timeEnd = System.currentTimeMillis();
                        T.get(mContext).toast("Realm插入10000条数据:" + (timeEnd - timeStart));
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {

                    }
                });
                break;
        }
    }
}
