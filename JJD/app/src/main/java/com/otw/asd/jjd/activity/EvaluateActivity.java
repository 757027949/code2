package com.otw.asd.jjd.activity;

import android.os.Message;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.http.okhttp.OkHttpUtils;
import com.asd.android.util.ActivityUtil;
import com.asd.android.util.app.AppManager;
import com.asd.android.widget.MyDialog;
import com.bumptech.glide.Glide;
import com.hedgehog.ratingbar.RatingBar;
import com.orhanobut.logger.Logger;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.base.BaseIncludeGridViewChooseImageActivity;
import com.otw.asd.jjd.entity.RecordInfo;
import com.otw.asd.jjd.util.UrlUtil;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 评价
 * Created by Administrator on 2016/3/31.
 */
public class EvaluateActivity extends BaseIncludeGridViewChooseImageActivity {

    @Bind(R.id.star_num_quality)
    RatingBar star_num_quality;
    @Bind(R.id.star_num_manner)
    RatingBar star_num_manner;
    @Bind(R.id.star_num_cleaning)
    RatingBar star_num_cleaning;
    @Bind(R.id.mes)
    FormEditText mes;
    @Bind(R.id.max_txt_num)
    TextView max_txt_num;

    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.money)
    TextView money;
    @Bind(R.id.person_num)
    TextView person_num;
    @Bind(R.id.time)
    TextView time;

    @Bind(R.id.gridview)
    GridView gridview;

    int star_quality = 0, star_manner = 0, star_cleaning = 0;

    RecordInfo recordInfo;

    final int ADDCOMMENT = 0;

    @Override
    public boolean handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case ADDCOMMENT:
                OkHttpUtils.post().url(UrlUtil.ADDCOMMENT)
                        .addParams("commentQuality", star_quality + "")//教学质量
                        .addParams("commentManner", star_manner + "")//教学态度
                        .addParams("commentClean", star_cleaning + "")//车内清洁
                        .addParams("commentContent", mes.getText().toString())//评论内容
                        .addParams("userId", recordInfo.getUserTeacher().getUserId())
                        .addParams("courseOrderId", recordInfo.getCourseOrder().getCourseOrderId())
                        .build()
                        .execute(new MyCallBack() {
                            @Override
                            public void setIsSilence() {
                                setNotSilence(true);
                            }

                            @Override
                            public void onResponse(String response) {
                                Logger.e(response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("success")) {
                                        Toast.makeText(EvaluateActivity.this, jsonObject.getString("obj"), Toast.LENGTH_SHORT).show();
                                        AppManager.finishAllActivity();
                                    } else {
                                        MyDialog.getInstance(EvaluateActivity.this).getWaringAlertDialog(jsonObject.getString("obj")).show();
                                    }
                                } catch (Exception e) {
                                    MyDialog.getInstance(EvaluateActivity.this).getWaringAlertDialog(getResString(R.string.json_error)).show();
                                }
                            }
                        });
                break;
        }
        return false;
    }

    @Override
    public int bindLayout() {
//        Logger.e("RecordActivity...");
        return R.layout.activity_evaluate;
    }

    @Override
    public void initView(View view) {
        setTopTitle("评价");
        AppManager.addActivity(this);

        initGridView(gridview, R.layout.adapter_layout_gridview_item);
        initRatingBar();

        setViewValue();
    }

    private void setViewValue() {
        recordInfo = (RecordInfo) getIntent().getSerializableExtra("data");
        try {
            name.setText(recordInfo.getUserTeacher().getUserRealName());
            money.setText("￥" + recordInfo.getCourseOrder().getCourseOrderPrice());
            person_num.setText(recordInfo.getCourseOrder().getPeopleNumber() + "");
            time.setText(recordInfo.getCourseOrder().getCourseOrderTime());
            Glide.with(EvaluateActivity.this).load(recordInfo.getUserTeacher().getUserHeadPath()).error(R.mipmap.ic_person).into(image);
        } catch (Exception e) {
        }
    }

    @OnTextChanged(R.id.mes)
    public void mesTextChange() {
        max_txt_num.setText(mes.length() + "/100");
    }

    private void initRatingBar() {
        star_num_quality.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(int RatingCount) {
                star_quality = RatingCount;
            }
        });
        star_num_manner.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(int RatingCount) {
                star_manner = RatingCount;
            }
        });
        star_num_cleaning.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(int RatingCount) {
                star_cleaning = RatingCount;
            }
        });
    }

    @OnClick({R.id.submit})
    public void viewClick(View view) {
        if (ActivityUtil.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.submit:
                if (0 == star_quality) {
                    Toast.makeText(this, "请为教学质量打星", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (0 == star_manner) {
                    Toast.makeText(this, "请为教学态度打星", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (0 == star_cleaning) {
                    Toast.makeText(this, "请为车内清洁打星", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!mes.testValidity()) {
                    return;
                }
                mHandler.sendEmptyMessage(ADDCOMMENT);
                break;
        }
    }

    @Override
    protected int setAddImageRes() {
        return R.drawable.image_add;
    }

    @Override
    protected int setImageMaxNumber() {
        return 9;
    }

    @Override
    protected void reSetConvert(ViewHolder holder, String s) {

    }

    @Override
    protected void mConvert(final ViewHolder holder, String s) {
        Glide.with(this).load(s).into((ImageView) holder.getView(R.id.imageView));
        holder.setOnClickListener(R.id.imageView, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removedImage(holder);
            }
        });
    }
}
