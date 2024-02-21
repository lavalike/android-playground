package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.android.exercise.databinding.ActivityTextFormatBinding;
import com.android.exercise.ui.widget.FeedForegroundColorSpan;
import com.wangzhen.commons.toolbar.impl.Toolbar;


public class TextFormatActivity extends BaseActivity {
    String text = "一．\t积分获取方式\n" + "1.\t一次性积分获取\n" + "-\t注册成功+10分；\n" + "-\t成功被邀请+100分；\n" + "-\t绑定手机+100分。\n" + "2.\t日常积分获取\n" + "-\t读完文章+3积分，每日上限30分；\n" + "-\t分享成功+5分，每日上限25分；\n" + "-\t评论成功+5分，每日上限5分；\n" + "-\t评论被回复+2分，每日上限20分；\n" + "-\t邀请成功+100分，每日上限500分；\n" + "-\t评论被点赞+1分，每日上限10分；\n" + "-\t阅读文章+2分，每日上限20分。\n" + "3.\t每周积分获取\n" + "-\t订阅栏目+20分，每周上限20分。\n" + "4.\t递进式积分获取\n" + "-\t连续登录1天+5分；\n" + "-\t连续登录2天+6分；\n" + "-\t连续登录3天+7分；\n" + "-\t连续登录4天+9分；\n" + "-\t连续登录5天+11分；\n" + "-\t连续登录6天+13分；\n" + "-\t连续登录7天+15分。\n" + "5.\t邀请好友补充说明\n" + "邀请方法：点击”我的”--“邀请好友”，分享好友，对方下载客户端，注册并输入邀请码即可。）\n" + "注意：邀请的必须是真实注册用户，一旦发现邀请的非真实有活跃度的用户，平台有权进行帐号封禁处理，积分兑换礼品作废。\n" + "\n" + "二．\t在哪里可以兑换积分？\u000B\n" + "点击“我的”-“积分兑换”进入积分商城，可以兑换相应的奖品和服务。\n" + "\n" + "三.\t为什么我的积分没有被记录？\u000B\n" + "存在因为手机网络环境不佳，而导致积分未及时增加的情况，属于极小概率事件，带来不便请谅解！\n" + "\n" + "四.\t商品兑换成功后是否可以取消？\u000B\n" + "商品一经兑换成功后不可修改或取消，请在兑换之前仔细阅读兑换使用说明、有效期限及适用范围。\n" + "如果出现成功下单，但是库存不足时，积分将会在7个工作日之内返还。\n" + "\n" + "五.\t哪些积分操作属于违规行为？\u000B\n" + "凡是以不正当手段（包括但不限于作弊，扰乱系统，实施网络攻击）获得积分，并进行兑换，平台有权终止服务，并对账号进行封禁处理。\n" + "部分奖品限制了每人仅兑换一次，若发现多个账号使用相同的手机号或收货信息的进行兑换，平台有权取消兑换订单，被扣除积分不退还。\n" + "\n";
    private ActivityTextFormatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityTextFormatBinding.inflate(getLayoutInflater())).getRoot());
        text = text.replace("\t", "\t\t");
        binding.tvFormat.setText(text);
        binding.tvRich.setText(appendFeedSource("那些回不去的年少时光", "美丽的女子令人喜欢，坚强的女子令人敬重，当一个女子既美丽又坚强时，她将无往不胜。"));
    }

    private Spannable appendFeedSource(String source, String text) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        int start = builder.length();
        builder.append("//");
        builder.append("@");
        builder.append(source);
        builder.append("：");
        int end = builder.length();
        builder.append(text);
        //改变文本颜色
        ForegroundColorSpan foregroundColorSpan = new FeedForegroundColorSpan(R.color.color_00B2EE);
        builder.setSpan(foregroundColorSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return builder;
    }

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_text_format));
    }
}
