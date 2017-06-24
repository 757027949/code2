package com.otw.asd.jjd.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.asd.android.adapter.MultiItemCommonAdapter;
import com.asd.android.adapter.MultiItemTypeSupport;
import com.asd.android.adapter.ViewHolder;
import com.asd.android.widget.PinnedSectionListView.PinnedSectionListAdapter;
import com.otw.asd.jjd.R;
import com.otw.asd.jjd.activity.AccountDetailInfoActivity;
import com.otw.asd.jjd.activity.RecordInfoActivity;
import com.otw.asd.jjd.entity.AccountDetail;
import com.otw.asd.jjd.entity.AccountDetailInfo;
import com.otw.asd.jjd.entity.PinnedSectionItem;

import java.util.List;

/**
 * 账户明细适配器
 * Created by Administrator on 2016/4/26.
 */
public class AccountDetailAdapter extends MultiItemCommonAdapter<PinnedSectionItem> implements PinnedSectionListAdapter {
    public AccountDetailAdapter(Context context, List<PinnedSectionItem> datas) {
        super(context, datas, new MultiItemTypeSupport<PinnedSectionItem>() {
            @Override
            public int getLayoutId(int position, PinnedSectionItem item) {
                if (item.getItemType() == PinnedSectionItem.SECTION) {
                    return R.layout.adapter_layout_section_item;
                } else {
                    return R.layout.adapter_layout_account_detail_item;
                }
            }

            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public int getItemViewType(int postion, PinnedSectionItem item) {
                return item.getItemType();
            }
        });
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return PinnedSectionItem.SECTION == viewType;
    }

    @Override
    public void convert(ViewHolder holder, PinnedSectionItem item) {
        switch (holder.getLayoutId()) {
            case R.layout.adapter_layout_section_item:
                holder.setText(R.id.section_item, item.getSectionString());
                break;

            case R.layout.adapter_layout_account_detail_item:
                final AccountDetailInfo accountOrdersBean = (AccountDetailInfo) item;
                holder.setText(R.id.status, accountOrdersBean.getAccountOrderExplain());
                holder.setText(R.id.time, accountOrdersBean.getAccountOrderTime());
                holder.setText(R.id.money, "￥" + accountOrdersBean.getAccountOrderAmount());


                holder.setOnClickListener(R.id.rel_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, AccountDetailInfoActivity.class);
                        intent.putExtra("flow", "accountDetail");
                        intent.putExtra("data", accountOrdersBean);
                        mContext.startActivity(intent);
                    }
                });
                break;
        }
    }
}
