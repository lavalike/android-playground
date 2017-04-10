package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MinaActivity.java
 * Created by wangzhen on 2017/4/10.
 */
public class MinaActivity extends BaseActivity {

    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mina);
        ButterKnife.bind(this);
        new MinaThread().start();
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_mina), true);
    }

    /**
     * 子线程开启Mina服务
     */
    class MinaThread extends Thread {
        @Override
        public void run() {
            Log.e(TAG, "客户端开始连接");
            IoConnector connector = new NioSocketConnector();
            connector.setConnectTimeoutMillis(1000 * 30);
            connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(
                    new TextLineCodecFactory(
                            Charset.forName("utf-8"),
                            LineDelimiter.WINDOWS.getValue(),
                            LineDelimiter.WINDOWS.getValue()
                    )));
            connector.setHandler(new MinaClientHandler());
            //创建连接
            ConnectFuture future = connector.connect(new InetSocketAddress("10.100.47.155", 8090));
            //等待连接创建完成
            future.awaitUninterruptibly();
            IoSession session = future.getSession();
            session.write("我是客户端.");
        }
    }

    class MinaClientHandler extends IoHandlerAdapter {
        @Override
        public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
            super.exceptionCaught(session, cause);
            String info = "异常：" + cause.getMessage() + "\n";
            Message msg = Message.obtain();
            msg.obj = info;
            handler.sendMessage(msg);
        }

        @Override
        public void messageReceived(IoSession session, Object message) throws Exception {
            super.messageReceived(session, message);
            String info = "远程：" + message.toString() + "\n";
            Message msg = Message.obtain();
            msg.obj = info;
            handler.sendMessage(msg);
        }

        @Override
        public void messageSent(IoSession session, Object message) throws Exception {
            super.messageSent(session, message);
            String info = "自己：" + message + "\n";
            Message msg = Message.obtain();
            msg.obj = info;
            handler.sendMessage(msg);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String text = textView.getText().toString();
            if (TextUtils.isEmpty(text)) {
                text = "";
            }
            textView.setText(text + String.valueOf(msg.obj));
        }
    };
}
