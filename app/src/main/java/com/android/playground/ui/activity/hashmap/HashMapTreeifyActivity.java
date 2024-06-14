package com.android.playground.ui.activity.hashmap;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.android.playground.databinding.ActivityHashmapTreeifyBinding;
import com.android.playground.domain.MapKey;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMapTreeifyActivity HashMap树化过程
 * Created by wangzhen on 2019/2/20.
 */
public class HashMapTreeifyActivity extends BaseActivity {
    private ActivityHashmapTreeifyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityHashmapTreeifyBinding.inflate(getLayoutInflater())).getRoot());

        Map<MapKey, String> map = new HashMap<>();

        for (int i = 0; i < 6; i++) {
            map.put(new MapKey(String.valueOf(i)), "A");
        }
        binding.tvContent.setText("第一次");
        binding.tvContent.append(" --> ");
        binding.tvContent.append(String.valueOf(map.size()));
        binding.tvContent.append("\n");
        binding.tvContent.append("1号桶中bin的数量6，不超过TREEIFY_THRESHOLD(8)，初始容量为16，小于MIN_TREEIFY_THRESHOLD(64)，因此不会树化");
        binding.tvContent.append("\n");
        binding.tvContent.append(map.toString());

        for (int i = 0; i < 10; i++) {
            map.put(new MapKey(String.valueOf(i)), "A");
        }
        binding.tvContent.append("\n\n");
        binding.tvContent.append("第二次");
        binding.tvContent.append(" --> ");
        binding.tvContent.append(String.valueOf(map.size()));
        binding.tvContent.append("\n");
        binding.tvContent.append("1号桶中bin的数量10，超过TREEIFY_THRESHOLD(8)，但上次操作后容量为16，小于MIN_TREEIFY_THRESHOLD(64)，因此不会树化");
        binding.tvContent.append("\n");
        binding.tvContent.append(map.toString());

        for (int i = 0; i < 50; i++) {
            map.put(new MapKey(String.valueOf(i)), "A");
        }
        binding.tvContent.append("\n\n");
        binding.tvContent.append("第三次");
        binding.tvContent.append(" --> ");
        binding.tvContent.append(String.valueOf(map.size()));
        binding.tvContent.append("\n");
        binding.tvContent.append("1号桶中bin的数量50已超过TREEIFY_THRESHOLD(8)，上次操作后容量为64，大于等于MIN_TREEIFY_THRESHOLD(64)，因此会树化");
        binding.tvContent.append("\n");
        binding.tvContent.append(map.toString());

        map.put(new MapKey("X"), "B");
        map.put(new MapKey("Y"), "B");
        map.put(new MapKey("Z"), "B");

        binding.tvContent.append("\n\n");
        binding.tvContent.append("第四次");
        binding.tvContent.append(" --> ");
        binding.tvContent.append(String.valueOf(map.size()));
        binding.tvContent.append("\n");
        binding.tvContent.append("1号桶采用树形存储结构，验证其他桶存储结构");
        binding.tvContent.append("\n");
        binding.tvContent.append(map.toString());
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_hashmap_treeify));
    }
}
