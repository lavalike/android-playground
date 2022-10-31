package com.android.exercise.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.bumptech.glide.Glide;
import com.wangzhen.commons.toolbar.impl.Toolbar;
import com.wangzhen.linktextview.LinkTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * TextView超链接
 * Created by wangzhen on 2017/6/2.
 */
public class TextLinkActivity extends BaseActivity {

    @BindView(R.id.tv_link_html)
    LinkTextView tvLinkHtml;
    @BindView(R.id.iv_gif)
    ImageView ivGif;

    private String HTML_CODE = "Android是一种基于<a href=\"/item/Linux\">Linux</a>的自由及开放源代码的<a href=\"/item/%E6%93%8D%E4%BD%9C%E7%B3%BB%E7%BB%9F\">操作系统</a>，主要使用于<a href=\"/item/%E7%A7%BB%E5%8A%A8%E8%AE%BE%E5%A4%87\">移动设备</a>，如<a href=\"/item/%E6%99%BA%E8%83%BD%E6%89%8B%E6%9C%BA\">智能手机</a>和<a href=\"/item/%E5%B9%B3%E6%9D%BF%E7%94%B5%E8%84%91\">平板电脑</a>，由<a href=\"/item/Google\">Google</a>公司和<a href=\"/item/%E5%BC%80%E6%94%BE%E6%89%8B%E6%9C%BA%E8%81%94%E7%9B%9F\">开放手机联盟</a>领导及开发。尚未有统一中文名称，中国大陆地区较多人使用“<a href=\"/item/%E5%AE%89%E5%8D%93\">安卓</a>”或“<a href=\"/item/%E5%AE%89%E8%87%B4\">安致</a>”。Android操作系统最初由<a href=\"/item/Andy%20Rubin\">Andy Rubin</a>开发，主要支持<a href=\"/item/%E6%89%8B%E6%9C%BA/6342\" data-lemmaid=\"6342\">手机</a>。2005年8月由Google收购注资。2007年11月，Google与84家硬件制造商、软件开发商及电信营运商组建开放手机联盟共同研发改良Android系统。随后Google以Apache开源许可证的授权方式，发布了Android的源代码。第一部Android智能手机发布于2008年10月。Android逐渐扩展到<a href=\"/item/%E5%B9%B3%E6%9D%BF%E7%94%B5%E8%84%91\">平板电脑</a>及其他领域上，如<a href=\"/item/%E7%94%B5%E8%A7%86\">电视</a>、<a href=\"/item/%E6%95%B0%E7%A0%81%E7%9B%B8%E6%9C%BA\">数码相机</a>、<a href=\"/item/%E6%B8%B8%E6%88%8F%E6%9C%BA\">游戏机</a>等。2011年第一季度，Android在全球的市场份额首次超过<a href=\"/item/%E5%A1%9E%E7%8F%AD%E7%B3%BB%E7%BB%9F\">塞班系统</a>，跃居全球第一。 2013年的第四季度，Android平台手机的全球市场份额已经达到78.1%。<sup>[1]</sup><a class=\"sup-anchor\" name=\"ref_[1]_9322617\">&nbsp;</a>\n" +
            "2013年09月24日谷歌开发的操作系统Android在迎来了5岁生日，全世界采用这款系统的设备数量已经达到10亿台。";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_link);
        ButterKnife.bind(this);
        tvLinkHtml.setText(HTML_CODE);
        tvLinkHtml.setOnLinkClickListener(new LinkTextView.OnLinkClickListener() {
            @Override
            public void onClick(String url) {
                showToast(url);
            }
        });
        Glide.with(this)
                .load("https://testimg.thehour.cn/focusimage/service/2018/08/13/1534124035394.gif")
                .into(ivGif);
    }

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_text_link));
    }

    @OnClick(R.id.iv_gif)
    public void onViewClicked() {
        Drawable drawable = ivGif.getDrawable();
        Log.e(tag, "drawable --> " + drawable);
    }
}
