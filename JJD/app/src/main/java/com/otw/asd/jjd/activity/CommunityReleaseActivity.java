package com.otw.asd.jjd.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.andreabaccega.widget.FormEditText;
import com.asd.android.adapter.CommonAdapter;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.http.okhttp.MyCallBack;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.BitmapUtil;
import com.asd.android.util.ViewCompoundDrawableUtil;
import com.asd.android.util.gesture.GestureUtils;
import com.asd.android.widget.MyDialog;
import com.asd.util.JsonMapper;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseActivity;
import com.otw.asd.jjd.base.BaseIncludeGridViewChooseImageActivity;
import com.otw.asd.jjd.util.ImageFactory;
import com.otw.asd.jjd.util.UrlUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.qqtheme.framework.picker.OptionPicker;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by Administrator on 2016/9/28.
 */

public class CommunityReleaseActivity extends BaseIncludeGridViewChooseImageActivity {

    @Bind(R.id.title)
    FormEditText title;
    @Bind(R.id.mes)
    FormEditText mes;
    @Bind(R.id.college)
    TextView college;

    @Bind(R.id.image_cover)
    ImageView image_cover;

    @Bind(R.id.gridview)
    GridView gridview;

    OptionPicker collegeOptionPicker;

    final int ADDCOMMON = 0;
    String commonType = "";
    String picturePath = "";
    boolean isCommonCoverPath = false;//是否有封面
    ImageFactory imageFactory;

    String commonImagePath = "", commonImagePath1 = "", commonImagePath2 = "";

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case ADDCOMMON:
                if (null == imageFactory) {
                    imageFactory = new ImageFactory();
                }
                getCommonImagePathsParams();
                OkHttpUtils.post().url(UrlUtil.ADDCOMMON)
                        .addParams("commonType", commonType)//类型，默认为ALL，/*** ALL:全部* A:吐槽* B:女神* C:男神* D:屌丝**/
                        .addParams("commonTitle", title.getText().toString())//标题
                        .addParams("commonText", mes.getText().toString())//文本
                        .addParams("commonCoverPath", (isCommonCoverPath) ? BitmapUtil.getInstance().convertIconToString(imageFactory.ratio(picturePath, 500, 500)) : "")//封面
//                        .addParams("commonCoverPath", (isCommonCoverPath) ? BitmapUtil.getInstance().convertIconToString(imageFactory.getBitmap(picturePath)) : "")//封面
                        .addParams("commonImagePath", commonImagePath)//图片，base64字符串json数组格式
                        .addParams("commonImagePath1", commonImagePath1)//图片，base64字符串json数组格式
                        .addParams("commonImagePath2", commonImagePath2)//图片，base64字符串json数组格式
                        .build()
                        .execute(new com.asd.android.http.okhttp.MyCallBack() {
                            @Override
                            public boolean setIsSilence() {
                                return false;
                            }

                            @Override
                            public Context getContext() {
                                return CommunityReleaseActivity.this;
                            }

                            @Override
                            public SweetAlertDialog getLoadDialog() {
                                return getLodingAlertDialog();
                            }

                            @Override
                            public void onResponse(String response) {
                                Logger.e(response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("success")) {
                                        Toast.makeText(CommunityReleaseActivity.this, "发布成功...", Toast.LENGTH_SHORT).show();
                                        finishSelf();
                                    } else {
                                        MyDialog.getInstance(CommunityReleaseActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(CommunityReleaseActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_community_release;
    }

    @Override
    public void initView(View view) {
        setTopTitle("");

        ViewCompoundDrawableUtil drawableUtil = new ViewCompoundDrawableUtil(this);
        drawableUtil.initTextViewCompoundDrawable(title, 0, 0.03f, 0, 1);
        drawableUtil.initTextViewCompoundDrawable(college, 2, (int) (GestureUtils.getScreenPix(this).heightPixels * 0.015));

        initGridView(gridview, R.layout.adapter_layout_community_gridview_item);
    }

    @OnClick({R.id.release, R.id.image_add_cover, R.id.college})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.release:
                if (!title.testValidity()) {
                    return;
                }
                if (!mes.testValidity()) {
                    return;
                }
                if ("".equals(college.getText().toString())) {
                    Toast.makeText(this, "请选择社区类别...", Toast.LENGTH_SHORT).show();
                    return;
                }
                mHandler.sendEmptyMessage(ADDCOMMON);
                break;
            case R.id.image_add_cover:
                getPicture(0);
                break;
            case R.id.college:
                if (null == collegeOptionPicker) {
                    final String[] results = getResources().getStringArray(R.array.community_college);
                    collegeOptionPicker = new OptionPicker(this, results);
                    collegeOptionPicker.setCancelTextColor(0xFF33B5E5);
                    collegeOptionPicker.setSubmitTextColor(0xFF33B5E5);
                    collegeOptionPicker.setSelectedItem(college.getText().toString());
                    collegeOptionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(String option) {
                            college.setText(option);
                            if (option.equals(results[0])) {
                                commonType = "A";
                            } else if (option.equals(results[1])) {
                                commonType = "B";
                            } else if (option.equals(results[2])) {
                                commonType = "C";
                            } else if (option.equals(results[3])) {
                                commonType = "D";
                            }
                        }
                    });
                }
                collegeOptionPicker.show();
                break;
        }
    }

    /**
     * 获取图片
     *
     * @param requestCode
     */
    private void getPicture(int requestCode) {
        Intent intent = new Intent(this, MultiImageSelectorActivity.class);
        // 是否显示调用相机拍照
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大图片选择数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
        // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected int setAddImageRes() {
        return R.mipmap.ic_community_release_add;
    }

    @Override
    protected int setImageMaxNumber() {
        return 3;
    }

    @Override
    protected void reSetConvert(ViewHolder holder, String s) {
        holder.setVisible(R.id.remove, false);
        holder.setOnClickListener(R.id.imageView, null);
    }

    @Override
    protected void mConvert(final ViewHolder holder, String s) {
        Glide.with(this).load(s).into((ImageView) holder.getView(R.id.imageView));
        holder.setVisible(R.id.remove, true);
        holder.setOnClickListener(R.id.remove, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removedImage(holder);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data && resultCode == RESULT_OK) {
            // 获取返回的图片列表
            List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            picturePath = path.get(0);
            if (requestCode == 0) {
                // 处理你自己的逻辑 ....
                Glide.with(this).load(picturePath).error(R.drawable.ic_error).into(image_cover);
                isCommonCoverPath = true;
            }
        }
    }

    /**
     * 获取图片字符串
     *
     * @return
     */
    public void getCommonImagePathsParams() {
        if (getImagePaths().size() > 0) {
            ImageFactory imageFactory = new ImageFactory();
            for (int i = 0; i < getImagePaths().size(); i++) {
                String[] strings = new String[1];
//                View view = gridViewadapter.getView(i, null, gridview);
//                strings[i] = BitmapUtil.getInstance().convertIconToString(BitmapUtil.convertViewToBitmap(view));

                strings[0] = BitmapUtil.getInstance().convertIconToString(imageFactory.ratio(getImagePaths().get(i), 500, 500));
//                strings[i] = BitmapUtil.getInstance().convertIconToString(imageFactory.getBitmap(getImagePaths().get(i)));
                if (0 == i) {
                    commonImagePath = JsonMapper.toNormalJson(strings);
                } else if (1 == i) {
                    commonImagePath1 = JsonMapper.toNormalJson(strings);
                } else if (2 == i) {
                    commonImagePath2 = JsonMapper.toNormalJson(strings);
                }
            }
        }
    }
}
