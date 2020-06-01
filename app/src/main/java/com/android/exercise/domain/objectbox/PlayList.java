package com.android.exercise.domain.objectbox;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * PlayList
 * Created by wangzhen on 2020/6/1.
 */
@Entity
public class PlayList {
    @Id
    public long id;
    public String name;
    public long time;
}
