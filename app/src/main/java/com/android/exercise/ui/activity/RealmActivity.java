package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.common.toolbar.ToolBarCommonHolder;
import com.android.exercise.domain.realm.User;
import com.android.exercise.util.T;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
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

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_realm), true);
    }

    @OnClick({R.id.btn_realm_insert, R.id.btn_realm_query, R.id.btn_realm_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_realm_insert:
//                //新建一个对象，并进行存储
//                mRealm.beginTransaction();
//                User user = mRealm.createObject(User.class);
//                user.setName("张三");
//                user.setAge(24);
//                mRealm.commitTransaction();
//                //复制一个对象到Realm数据库
//                User user = new User();
//                user.setName("张三");
//                user.setAge(24);
//                mRealm.beginTransaction();
//                mRealm.copyToRealm(user);
//                mRealm.commitTransaction();
                //使用事务块
                final User user = new User();
                user.setName("张三");
                user.setAge(24);
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(user);
                        T.get(mContext).toast("新增一条");
                    }
                });
                break;
            case R.id.btn_realm_query:
                RealmResults<User> usersQuery = mRealm.where(User.class).findAll();
                List<User> list = mRealm.copyFromRealm(usersQuery);
                if (list != null) {
                    T.get(mContext).toast("共" + list.size() + "条");
                }
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
        }
    }
}
