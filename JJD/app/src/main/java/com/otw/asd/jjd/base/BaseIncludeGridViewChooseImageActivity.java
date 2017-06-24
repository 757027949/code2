package com.otw.asd.jjd.base;

import android.content.Intent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.asd.android.adapter.CommonAdapter;
import com.asd.android.adapter.ViewHolder;
import com.bumptech.glide.Glide;
import com.otw.asd.jjd.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * 包含GridView的activity
 * Created by ASD on 2015/12/30.
 */
public abstract class BaseIncludeGridViewChooseImageActivity extends BaseActivity implements IBaseActivity {

    final int REQUEST_IMAGE = 0X23353;


    /**
     * 设置显示添加图片
     *
     * @return
     */
    protected abstract int setAddImageRes();

    /**
     * 设置显示图片最大数
     *
     * @return
     */
    protected abstract int setImageMaxNumber();

    /**
     * 重置数据
     *
     * @param holder
     * @param s
     */
    protected abstract void reSetConvert(ViewHolder holder, String s);

    /**
     * 设置gridview item
     *
     * @param holder
     * @param s
     */
    protected abstract void mConvert(ViewHolder holder, String s);

    /**
     * 图片路径
     */
    List<String> imagePaths = new ArrayList<>();

    /**
     * Gridview 适配器
     */
    protected CommonAdapter<String> gridViewadapter;


    /**
     * 初始化GridView
     * R.layout.adapter_layout_gridview_item
     */
    public void initGridView(GridView gridView, int layoutId) {
        imagePaths.add(null);
//        gridview.setColumnWidth((int) (GestureUtils.getScreenPix(this).widthPixels*0.3));
        gridView.setAdapter(gridViewadapter = new CommonAdapter<String>(this, imagePaths, layoutId) {
            @Override
            public void convert(final ViewHolder holder, String s) {
                reSetConvert(holder, s);
                if (null != s) {
                    mConvert(holder, s);
                } else {
                    Glide.with(mContext).load(setAddImageRes()).into((ImageView) holder.getView(R.id.imageView));
                    holder.setOnClickListener(R.id.imageView, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(BaseIncludeGridViewChooseImageActivity.this, MultiImageSelectorActivity.class);
                            // 是否显示调用相机拍照
                            intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                            // 最大图片选择数量
                            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, setImageMaxNumber());
                            // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
                            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, setImageMaxNumber() > 1 ? MultiImageSelectorActivity.MODE_MULTI : MultiImageSelectorActivity.MODE_SINGLE);
                            startActivityForResult(intent, REQUEST_IMAGE);
                        }
                    });
                }
            }
        });
    }

    /**
     * 移除已添加图片
     *
     * @param holder
     */
    public void removedImage(final ViewHolder holder) {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("提示！")
                .setContentText("确认移除已添加图片吗？")
                .setCancelText("取消")
                .setConfirmText("确定")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (null != imagePaths.get(holder.getPosition())) {
                            imagePaths.remove(holder.getPosition());
                            if (imagePaths.size() < setImageMaxNumber()) {
                                if (null != imagePaths.get(imagePaths.size() - 1)) {
                                    imagePaths.add(null);
                                }
                            }
                            gridViewadapter.notifyDataSetChanged();
                            sweetAlertDialog.cancel();
                        }
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // 处理你自己的逻辑 ....
                imagePaths.clear();
                imagePaths.addAll(path);
                if (imagePaths.size() < setImageMaxNumber()) {
                    imagePaths.add(null);
                }
                gridViewadapter.notifyDataSetChanged();
            }
        }
    }

    public List<String> getImagePaths() {
        if (imagePaths.size() > 0 && null == imagePaths.get(imagePaths.size() - 1)) {
            imagePaths.remove(imagePaths.size() - 1);
        }
        return imagePaths;
    }
}
