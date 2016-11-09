package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.base.App;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.greendao.DBLoader;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;
import com.android.exercise.db.greendao.UserDao;
import com.android.exercise.domain.greendao.Man;
import com.android.exercise.domain.greendao.User;
import com.android.exercise.util.T;

import org.greenrobot.greendao.query.QueryBuilder;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * GreenDao数据库
 * created by wangzhen on 2016/11/07
 */
public class GreendaoActivity extends BaseActivity {

    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greendao);
        ButterKnife.bind(this);
        mUserDao = DBLoader.getSession().getUserDao();
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_greendao), true);
    }

    @OnClick({R.id.btn_greendao_insert, R.id.btn_greendao_query, R.id.btn_greendao_delete, R.id.btn_greendao_batch_insert, R.id.btn_xutils_batch_insert})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_greendao_insert:
                User user = new User();
                user.setName("王震");
                user.setAge(24);
                mUserDao.insert(user);
                T.get(mContext).toast("新增成功");
                break;
            case R.id.btn_greendao_query:
                List<User> userList = mUserDao.loadAll();
                T.get(mContext).toast("共" + userList.size() + "条数据");
                QueryBuilder<User> builder = mUserDao.queryBuilder();
                builder.where(UserDao.Properties.Age.ge(25));
                break;
            case R.id.btn_greendao_delete:
                mUserDao.deleteAll();
                T.get(mContext).toast("已全部删除");
                break;
            case R.id.btn_greendao_batch_insert:
                greenDaoInsert();
                break;
            case R.id.btn_xutils_batch_insert:
                xutilsInsert();
                break;
        }
    }

    private void xutilsInsert() {
        final List<Man> list = new ArrayList<>();
        Man man;
        for (int i = 0; i < 10000; i++) {
            man = new Man();
            man.setName("王震");
            man.setAge(i);
            list.add(man);
        }
        final DbManager db = x.getDb(App.getDbConfig());
        final long timeStart = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    db.save(list);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            long timeEnd = System.currentTimeMillis();
                            T.get(mContext).toast("xUtils插入10000条数据:" + (timeEnd - timeStart));
                        }
                    });
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void greenDaoInsert() {
        final List<User> list = new ArrayList<>();
        User user;
        for (int i = 0; i < 10000; i++) {
            user = new User();
            user.setName("王震");
            user.setAge(i);
            list.add(user);
        }
        final long timeStart = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mUserDao.insertInTx(list);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        long timeEnd = System.currentTimeMillis();
                        T.get(mContext).toast("GreenDao插入10000条数据:" + (timeEnd - timeStart));
                    }
                });
            }
        }).start();
    }
}
