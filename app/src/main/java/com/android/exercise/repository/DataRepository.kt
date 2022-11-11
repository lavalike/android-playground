package com.android.exercise.repository

import com.android.exercise.R
import com.android.exercise.domain.BaseBean
import com.android.exercise.domain.ItemBean
import com.android.exercise.domain.TitleBean
import com.android.exercise.ui.activity.*
import com.android.exercise.ui.activity.adapter.LoadMoreActivity
import com.android.exercise.ui.activity.android11.ScopedStorageActivity
import com.android.exercise.ui.activity.android11.TelephonyCompatActivity
import com.android.exercise.ui.activity.android11.ToastCompatActivity
import com.android.exercise.ui.activity.android13.ClipboardSensitiveActivity
import com.android.exercise.ui.activity.android13.IntentMatchActivity
import com.android.exercise.ui.activity.android13.NewPermissionsActivity
import com.android.exercise.ui.activity.android13.PostNotificationActivity
import com.android.exercise.ui.activity.biometric.BiometricActivity
import com.android.exercise.ui.activity.bitmap.BitmapOptimizeActivity
import com.android.exercise.ui.activity.bitmap.LargeImageActivity
import com.android.exercise.ui.activity.bluetooth.BluetoothActivity
import com.android.exercise.ui.activity.calendar.CalendarActivity
import com.android.exercise.ui.activity.circle.CircleImageActivity
import com.android.exercise.ui.activity.database.LitePalActivity
import com.android.exercise.ui.activity.database.ObjectBoxActivity
import com.android.exercise.ui.activity.database.RealmActivity
import com.android.exercise.ui.activity.di.dagger2.Dagger2Activity
import com.android.exercise.ui.activity.di.hilt.HiltActivity
import com.android.exercise.ui.activity.di.kodein.KodeinActivity
import com.android.exercise.ui.activity.di.koin.KoinActivity
import com.android.exercise.ui.activity.download.DownloadActivity
import com.android.exercise.ui.activity.hashmap.HashMapTreeifyActivity
import com.android.exercise.ui.activity.hook.HookActivity
import com.android.exercise.ui.activity.ipc.messenger.MessengerActivity
import com.android.exercise.ui.activity.jetpack.databinding.DataBindingActivity
import com.android.exercise.ui.activity.jetpack.datastore.DataStoreActivity
import com.android.exercise.ui.activity.jetpack.draganddrop.DragAndDropActivity
import com.android.exercise.ui.activity.jetpack.lifecycle.LifecycleActivity
import com.android.exercise.ui.activity.jetpack.navigation.NavigationActivity
import com.android.exercise.ui.activity.jetpack.room.RoomActivity
import com.android.exercise.ui.activity.jetpack.viewmodel.ViewModelActivity
import com.android.exercise.ui.activity.jetpack.work.WorkManagerActivity
import com.android.exercise.ui.activity.kotlin.CoroutinesActivity
import com.android.exercise.ui.activity.kotlin.SerializationActivity
import com.android.exercise.ui.activity.layer.MultiLayerActivity
import com.android.exercise.ui.activity.md5.MD5Activity
import com.android.exercise.ui.activity.mmkv.MMKVActivity
import com.android.exercise.ui.activity.motion.MotionLayoutActivity
import com.android.exercise.ui.activity.player.ExoPlayerActivity
import com.android.exercise.ui.activity.player.IjkPlayerActivity
import com.android.exercise.ui.activity.queue.PriorityQueueActivity
import com.android.exercise.ui.activity.queue.TaskSequenceActivity
import com.android.exercise.ui.activity.shared_elements.SharedElementsActivity
import com.android.exercise.ui.activity.stream.FileStreamActivity
import com.android.exercise.ui.activity.touch.MultiTouchActivity
import com.android.exercise.ui.activity.websocket.WebSocketActivity
import com.android.exercise.ui.activity.wifi.WifiActivity
import com.android.exercise.util.AppUtil

/**
 * DataRepository
 * @author: wangzhen
 * @date: 2022/11/10/010
 */
object DataRepository {
    @JvmStatic
    fun home() = mutableListOf<BaseBean>().apply {
        add(TitleBean("Dependency Injection"))
        add(ItemBean(getString(R.string.item_kodein), KodeinActivity::class.java))
        add(ItemBean(getString(R.string.item_koin), KoinActivity::class.java))
        add(ItemBean(getString(R.string.item_hilt), HiltActivity::class.java))
        add(ItemBean(getString(R.string.item_dagger), Dagger2Activity::class.java))

        add(TitleBean("Android 13"))
        add(
            ItemBean(
                getString(R.string.item_android13_post_notification),
                PostNotificationActivity::class.java
            )
        )
        add(
            ItemBean(
                getString(R.string.item_android13_new_permissions),
                NewPermissionsActivity::class.java
            )
        )
        add(
            ItemBean(
                getString(R.string.item_clipboard_sensitive), ClipboardSensitiveActivity::class.java
            )
        )
        add(ItemBean(getString(R.string.item_intent_match), IntentMatchActivity::class.java))

        add(TitleBean("Android 11"))
        add(
            ItemBean(
                getString(R.string.item_scoped_storage), ScopedStorageActivity::class.java
            )
        )
        add(
            ItemBean(
                getString(R.string.item_telephony_compat), TelephonyCompatActivity::class.java
            )
        )
        add(ItemBean(getString(R.string.item_toast_compat), ToastCompatActivity::class.java))

        add(TitleBean("Jetpack"))
        add(ItemBean(getString(R.string.item_drag_and_drop), DragAndDropActivity::class.java))
        add(ItemBean(getString(R.string.item_motion_layout), MotionLayoutActivity::class.java))
        add(ItemBean(getString(R.string.item_data_store), DataStoreActivity::class.java))
        add(ItemBean(getString(R.string.item_view_model), ViewModelActivity::class.java))
        add(ItemBean(getString(R.string.item_lifecycle), LifecycleActivity::class.java))
        add(ItemBean(getString(R.string.item_data_binding), DataBindingActivity::class.java))
        add(ItemBean(getString(R.string.item_navigation), NavigationActivity::class.java))
        add(ItemBean(getString(R.string.item_room), RoomActivity::class.java))
        add(ItemBean(getString(R.string.item_work_manager), WorkManagerActivity::class.java))
        add(
            ItemBean(
                getString(R.string.item_constraintlayout), ConstraintLayoutActivity::class.java
            )
        )

        add(TitleBean("kotlin"))
        add(
            ItemBean(
                getString(R.string.item_kotlin_coroutines), CoroutinesActivity::class.java
            )
        )
        add(
            ItemBean(
                getString(R.string.item_kotlin_serialization), SerializationActivity::class.java
            )
        )

        add(TitleBean("IPC"))
        add(ItemBean(getString(R.string.item_messenger), MessengerActivity::class.java))

        add(TitleBean("硬件相关"))
        add(ItemBean(getString(R.string.item_biometric), BiometricActivity::class.java))
        add(ItemBean(getString(R.string.item_wifi), WifiActivity::class.java))
        add(ItemBean(getString(R.string.item_bluetooth), BluetoothActivity::class.java))

        add(TitleBean("数据存储"))
        add(ItemBean(getString(R.string.item_object_box), ObjectBoxActivity::class.java))
        add(ItemBean(getString(R.string.item_realm), RealmActivity::class.java))
        add(ItemBean(getString(R.string.item_litepal), LitePalActivity::class.java))
        add(ItemBean(getString(R.string.item_mmkv), MMKVActivity::class.java))

        add(TitleBean("图片"))
        add(ItemBean(getString(R.string.item_large_bitmap), LargeImageActivity::class.java))
        add(
            ItemBean(
                getString(R.string.item_bitmap_optimize), BitmapOptimizeActivity::class.java
            )
        )
        add(ItemBean(getString(R.string.item_image_html), ImageHtmlActivity::class.java))
        add(
            ItemBean(
                getString(R.string.item_image_recycler), ImageRecyclerActivity::class.java
            )
        )

        add(TitleBean("个人开源"))
        add(ItemBean(getString(R.string.item_download), DownloadActivity::class.java))
        add(
            ItemBean(
                getString(R.string.item_refresh_layout), RefreshLayoutActivity::class.java
            )
        )

        add(TitleBean("三方开源"))
        add(ItemBean(getString(R.string.item_rxjava), RxJavaActivity::class.java))
        add(ItemBean(getString(R.string.item_retrofit), RetrofitActivity::class.java))
        add(ItemBean(getString(R.string.item_okhttp), OKHttpActivity::class.java))
        add(ItemBean(getString(R.string.item_okhttp_websocket), WebSocketActivity::class.java))
        add(ItemBean(getString(R.string.item_andServer), AndServerActivity::class.java))
        add(ItemBean(getString(R.string.item_mina), MinaActivity::class.java))

        add(TitleBean("视频"))
        add(ItemBean(getString(R.string.item_camera_video), VideoRecordActivity::class.java))
        add(ItemBean(getString(R.string.item_exoplayer), ExoPlayerActivity::class.java))
        add(ItemBean(getString(R.string.item_ijkplayer), IjkPlayerActivity::class.java))

        add(TitleBean("视图相关"))
        add(ItemBean(getString(R.string.item_path_search), PathSearchActivity::class.java))
        add(ItemBean(getString(R.string.item_path_measure), PathMeasureActivity::class.java))
        add(ItemBean(getString(R.string.item_path), PathActivity::class.java))
        add(ItemBean(getString(R.string.item_swipe_close), SwipeCloseActivity::class.java))
        add(ItemBean(getString(R.string.item_pull_layout), ElasticActivity::class.java))
        add(
            ItemBean(
                getString(R.string.item_shared_elements), SharedElementsActivity::class.java
            )
        )
        add(ItemBean(getString(R.string.item_circle_image), CircleImageActivity::class.java))
        add(ItemBean(getString(R.string.item_adapter), LoadMoreActivity::class.java))
        add(ItemBean(getString(R.string.item_hero), MultiLayerActivity::class.java))
        add(ItemBean(getString(R.string.item_wave_view), WaveActivity::class.java))
        add(ItemBean(getString(R.string.item_calendar), CalendarActivity::class.java))
        add(ItemBean(getString(R.string.item_multi_touch), MultiTouchActivity::class.java))
        add(ItemBean(getString(R.string.item_view_switcher), ViewSwitchActivity::class.java))
        add(ItemBean(getString(R.string.item_invoke_app), InvokeAppActivity::class.java))
        add(ItemBean(getString(R.string.item_notification), NotificationActivity::class.java))
        add(ItemBean(getString(R.string.item_text_format), TextFormatActivity::class.java))
        add(ItemBean(getString(R.string.item_text_link), TextLinkActivity::class.java))
        add(ItemBean(getString(R.string.item_state_button), StateButtonActivity::class.java))
        add(ItemBean(getString(R.string.item_recycler_tik_tok), TikTokActivity::class.java))
        add(
            ItemBean(
                getString(R.string.item_recycler_hover), HoverRecyclerViewActivity::class.java
            )
        )
        add(
            ItemBean(
                getString(R.string.item_recycler_touch), RecyclerTouchActivity::class.java
            )
        )
        add(ItemBean(getString(R.string.item_window), WindowActivity::class.java))
        add(ItemBean(getString(R.string.item_slidingmenu), SlidingMenuActivity::class.java))
        add(ItemBean(getString(R.string.item_loadmore), RecyclerActivity::class.java))
        add(ItemBean(getString(R.string.item_flowlayout), FlowLayoutActivity::class.java))
        add(ItemBean(getString(R.string.item_24hanim), Anim24hActivity::class.java))
        add(ItemBean(getString(R.string.item_drawerslide), DrawerSlideActivity::class.java))
        add(ItemBean(getString(R.string.item_ad_window), ADWindowActivity::class.java))
        add(ItemBean(getString(R.string.item_ice_switch), PageSwitchActivity::class.java))
        add(ItemBean(getString(R.string.item_porter_duff), PorterDuffActivity::class.java))
        add(ItemBean(getString(R.string.item_scrollview), ScrollActivity::class.java))
        add(ItemBean(getString(R.string.item_anim_view), AnimViewActivity::class.java))
        add(ItemBean(getString(R.string.item_edit_view), EditViewActivity::class.java))
        add(ItemBean(getString(R.string.item_floor), FloorActivity::class.java))
        add(ItemBean(getString(R.string.item_recorder), RecorderActivity::class.java))
        add(ItemBean(getString(R.string.item_ripple), RippleActivity::class.java))
        add(ItemBean(getString(R.string.item_view), ViewActivity::class.java))
        add(
            ItemBean(
                getString(R.string.item_html_rich_text), HtmlRichTextActivity::class.java
            )
        )
        add(
            ItemBean(
                getString(R.string.item_view_animation), ViewAnimationActivity::class.java
            )
        )
        add(
            ItemBean(
                getString(R.string.item_frame_animation), FrameAnimationActivity::class.java
            )
        )

        add(TitleBean("自动化"))
        add(ItemBean(getString(R.string.item_qq_auto_msg), AppOptActivity::class.java))

        add(TitleBean("基础"))
        add(ItemBean(getString(R.string.item_hook), HookActivity::class.java))
        add(ItemBean(getString(R.string.item_bit_opt), BitOptActivity::class.java))
        add(ItemBean(getString(R.string.item_md5), MD5Activity::class.java))
        add(ItemBean(getString(R.string.item_accessibility), AutoServiceActivity::class.java))
        add(ItemBean(getString(R.string.item_broadcast), BroadcastActivity::class.java))
        add(ItemBean(getString(R.string.item_threadpool), ThreadPoolActivity::class.java))
        add(ItemBean(getString(R.string.item_dispatch), DispatchActivity::class.java))
        add(ItemBean(getString(R.string.item_stream), FileStreamActivity::class.java))
        add(
            ItemBean(
                getString(R.string.item_hashmap_treeify), HashMapTreeifyActivity::class.java
            )
        )
        add(
            ItemBean(
                getString(R.string.item_priority_queue), PriorityQueueActivity::class.java
            )
        )
        add(ItemBean(getString(R.string.item_task_sequence), TaskSequenceActivity::class.java))
        add(
            ItemBean(
                getString(R.string.item_annotation_runtime), RuntimeAnnotationActivity::class.java
            )
        )
    }

    private fun getString(resId: Int): String {
        return AppUtil.getContext().getString(resId)
    }
}