package com.shenkangyun.recoveryproject.HomePage.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenkangyun.recoveryproject.BeanFolder.PreventionBean;
import com.shenkangyun.recoveryproject.R;

/**
 * Created by Administrator on 2018/4/1.
 */

public class PreventionAdapter extends BaseQuickAdapter<PreventionBean, BaseViewHolder> {

    public PreventionAdapter() {
        super(R.layout.item_prevention, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, PreventionBean item) {
        helper.setText(R.id.tv_unitName, item.getUnitName());
    }
}
