package com.android.playground.ui.activity;

import android.os.Bundle;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.android.playground.databinding.ActivityRxJavaBinding;
import com.android.playground.service.PollService;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

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
    private ActivityRxJavaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityRxJavaBinding.inflate(getLayoutInflater())).getRoot());
        setEvents();
        PollService.setServiceAlarm(this, true);
    }

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_rxjava));
    }

    public void setEvents() {
        binding.btnSend.setOnClickListener(v -> launch());
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
                binding.tvRxjava.setText(builder.toString());
            }
        });
    }
}
