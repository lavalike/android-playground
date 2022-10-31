package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.util.AppUtil;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ImageHtmlActivity
 * Created by wangzhen on 2018/12/27.
 */
public class ImageHtmlActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_image);
        ButterKnife.bind(this);
        String htmlCode = AppUtil.getAssetsText("html/html_image_list.html");
        htmlCode = String.format(htmlCode, createImagesTags(initList()));
        webview.loadDataWithBaseURL(null, htmlCode, "text/html", "utf-8", null);
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_image_html));
    }

    /**
     * 创建Img标签
     *
     * @param urls urls
     * @return tags
     */
    private String createImagesTags(List<String> urls) {
        StringBuilder builder = new StringBuilder();
        for (String url : urls) {
            builder.append("<img");
            builder.append(" src=\"");
            builder.append(url);
            builder.append("\"");
            builder.append(" />");
        }
        return builder.toString();
    }

    private List<String> initList() {
        List<String> list = new ArrayList<>();
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/5ba76a38270965a9f9bbd1b481c00755.jpg");
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/36296d5db70c13c63c5a77fc9ede77fc.jpg");
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/c717bbcad96f3a847f2a60cb28c69ed4.jpg");
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/da4e230c01b3aa531752bed05725febb.png");
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/a17181742da0821b09a548335e28ab69.png");
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/6734c014e7d9421783825b860d34a31f.png");
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/c5157a1a44ef22495ad80b593e69911e.png");
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/f347a04389831405252c610a4845e475.png");
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/3c5f2ed5d6fc4d35f73972e691dc1049.png");
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/e4fe294838a856dde90ff7439ec9abf8.png");
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/7b3c36421123d24eb7be62dbac5b0688.png");
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/bbccac514b0d9e5c5fff5d2835d0d94d.png");
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/b9cc314082b9b68e572d2c53900a199c.png");
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/0ed0dbe8f77dc0c8bc416cb005108a56.png");
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/e5ea7d81981b6c50ad4f9979b0b1403d.png");
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/7841a414498715050690d7751e5a67db.png");
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/8010780bdb498d956ebfff16f932e12e.png");
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/c3e778ba82ef41151669972a691bb550.png");
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/b4459e6d99d276417aa616845d1fe6a6.png");
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/7ff34a78578e056b85760def99c1b309.png");
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/f89c9a6b351a3fbe78bfd872303ade93.png");
        list.add("http://rili.php.jxcraft.net/public/uploads/20181121/f4cd72082ac7929578c7acb37b3c40e3.png");
        return list;
    }
}
