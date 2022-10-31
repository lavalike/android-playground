package com.android.exercise.ui.activity.hashmap;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.domain.MapKey;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * HashMapTreeifyActivity HashMap树化过程
 * Created by wangzhen on 2019/2/20.
 */
public class HashMapTreeifyActivity extends BaseActivity {

    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hashmap_treeify);
        ButterKnife.bind(this);

        Map<MapKey, String> map = new HashMap<>();

        for (int i = 0; i < 6; i++) {
            map.put(new MapKey(String.valueOf(i)), "A");
        }
        tvContent.setText("第一次");
        tvContent.append(" --> ");
        tvContent.append(String.valueOf(map.size()));
        tvContent.append("\n");
        tvContent.append("1号桶中bin的数量6，不超过TREEIFY_THRESHOLD(8)，初始容量为16，小于MIN_TREEIFY_THRESHOLD(64)，因此不会树化");
        tvContent.append("\n");
        tvContent.append(map.toString());

        for (int i = 0; i < 10; i++) {
            map.put(new MapKey(String.valueOf(i)), "A");
        }
        tvContent.append("\n\n");
        tvContent.append("第二次");
        tvContent.append(" --> ");
        tvContent.append(String.valueOf(map.size()));
        tvContent.append("\n");
        tvContent.append("1号桶中bin的数量10，超过TREEIFY_THRESHOLD(8)，但上次操作后容量为16，小于MIN_TREEIFY_THRESHOLD(64)，因此不会树化");
        tvContent.append("\n");
        tvContent.append(map.toString());

        for (int i = 0; i < 50; i++) {
            map.put(new MapKey(String.valueOf(i)), "A");
        }
        tvContent.append("\n\n");
        tvContent.append("第三次");
        tvContent.append(" --> ");
        tvContent.append(String.valueOf(map.size()));
        tvContent.append("\n");
        tvContent.append("1号桶中bin的数量50已超过TREEIFY_THRESHOLD(8)，上次操作后容量为64，大于等于MIN_TREEIFY_THRESHOLD(64)，因此会树化");
        tvContent.append("\n");
        tvContent.append(map.toString());

        map.put(new MapKey("X"), "B");
        map.put(new MapKey("Y"), "B");
        map.put(new MapKey("Z"), "B");

        tvContent.append("\n\n");
        tvContent.append("第四次");
        tvContent.append(" --> ");
        tvContent.append(String.valueOf(map.size()));
        tvContent.append("\n");
        tvContent.append("1号桶采用树形存储结构，验证其他桶存储结构");
        tvContent.append("\n");
        tvContent.append(map.toString());
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_hashmap_treeify));
    }
}
