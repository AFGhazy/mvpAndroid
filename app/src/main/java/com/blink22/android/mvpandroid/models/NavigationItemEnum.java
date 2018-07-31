package com.blink22.android.mvpandroid.models;

import com.blink22.android.mvpandroid.R;
import com.blink22.android.mvpandroid.ui.newtodo.NewTodoActivity;
import com.blink22.android.mvpandroid.ui.todos.TodosActivity;

/**
 * Created by ahmedghazy on 7/26/18.
 */

public enum NavigationItemEnum {
    HOME(R.string.list_todos, android.R.drawable.ic_dialog_info, TodosActivity.class, 0),
    ADD_NEW_NOTE(R.string.add_new_todo, android.R.drawable.ic_input_add, NewTodoActivity.class, 1);

    private int labelResourceId;
    private int iconResourceId;
    private Class classToLaunch;
    private int id;

    NavigationItemEnum(int labelResourceId, int iconResourceId, Class classToLaunch, int id) {
        this.labelResourceId = labelResourceId;
        this.iconResourceId = iconResourceId;
        this.classToLaunch = classToLaunch;
        this.id = id;
    }

    public int getLabelResourceId() {
        return labelResourceId;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public Class getClassToLaunch() {
        return classToLaunch;
    }

    public int getId() {
        return id;
    }
}
