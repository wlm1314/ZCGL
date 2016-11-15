package com.bjprd.zcgl.widget.recycleview;

/**
 * Created by 王少岩 on 2016/11/15.
 */

public class BindingTool {
    private int layoutId;
    private int variableId;

    public BindingTool(int layoutId, int variableId) {
        this.layoutId = layoutId;
        this.variableId = variableId;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public int getVariableId() {
        return variableId;
    }
}
