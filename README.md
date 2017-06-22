Android新技术实践

添加gradle依赖：
>  compile 'com.wangzhen:LinkTextView:1.0.0'

xml使用：
``` xml
<com.wangzhen.linktextview.LinkTextView
    android:id="@+id/tv_link_html"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:linkColor="@android:color/holo_red_dark" />
```

Activity使用：
``` java
tvLinkHtml.setText(HTML_CODE);
tvLinkHtml.setLinkColor(ContextCompat.getColor(this, R.color.colorPrimary));
tvLinkHtml.setOnLinkClickListener(new LinkTextView.OnLinkClickListener() {
    @Override
    public void onClick(String url) {
        showToast(url);
    }
});
```