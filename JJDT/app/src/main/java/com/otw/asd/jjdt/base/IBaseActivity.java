package com.otw.asd.jjdt.base;

import android.view.View;

/**
 * Created by ASD on 2015/12/30.
 */
public interface IBaseActivity {

    /**
     * 绑定渲染视图的布局文件
     * @return 布局文件资源id
     */
    public int bindLayout();

    /**
     * 初始化控件
     */
    public void initView(final View view);

}
