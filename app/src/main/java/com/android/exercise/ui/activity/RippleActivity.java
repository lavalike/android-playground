package com.android.exercise.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.util.AppUtil;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.net.URL;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RippleActivity.java
 * Created by wangzhen on 2017/4/10.
 */
public class RippleActivity extends BaseActivity {

    @BindView(R.id.tv_html)
    TextView tvHtml;
    @BindView(R.id.tv_unique_id)
    TextView tvUniqueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);
        ButterKnife.bind(this);

        tvUniqueId.setText("UniqueID:" + getUniquePsuedoID());
        tvHtml.setText("Html内容加载中...");
        readAssetHtml();
    }

    private void readAssetHtml() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String html = AppUtil.getAssetsText("html/html_template.html");
                final Spanned spanned = Html.fromHtml(html, imgGetter, null);
                tvHtml.post(new Runnable() {
                    @Override
                    public void run() {
                        tvHtml.setText(spanned);
                    }
                });
            }
        }).start();
    }

    Html.ImageGetter imgGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            try {
                URL url = new URL(source);
                drawable = Drawable.createFromStream(url.openStream(), "");
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
                        .getIntrinsicHeight());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return drawable;
        }
    };

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_ripple));
    }

    //获得独一无二的Psuedo ID
    public static String getUniquePsuedoID() {
        String serial = null;
        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 位
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }
}
