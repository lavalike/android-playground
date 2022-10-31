package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.service.PollService;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava
 * Created by wangzhen on 2017/4/23.
 */
public class RxJavaActivity extends BaseActivity {

    @BindView(R.id.tv_rxjava)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        ButterKnife.bind(this);
        PollService.setServiceAlarm(this, true);
    }

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_rxjava));
    }

    @OnClick({R.id.btn_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                launch();
                break;
        }
    }

    private void launch() {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER).map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                return s + "--第一次转换";
            }
        }).map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                return s + "--第二次转换";
            }
        }).flatMap(new Function<String, Publisher<String>>() {
            @Override
            public Publisher<String> apply(String s) throws Exception {
                return Flowable.just(s + "--第三次flatMap转换");
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            StringBuilder builder = new StringBuilder();

            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                builder.append(s).append("\n");
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                mTextView.setText(builder.toString());
            }
        });
    }
}
