package com.gdzc.zcdj.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdzc.R;
import com.gdzc.zcdj.model.ZcxgBean;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Created by 王少岩 on 2017/1/17.
 */

public class ZcxgAdapter extends SwipeMenuAdapter<ZcxgAdapter.DefaultViewHolder> {
    private OnItemClickListener mOnItemClickListener;
    private List<ZcxgBean.Zcxg> datas;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public ZcxgAdapter(List<ZcxgBean.Zcxg> datas) {
        this.datas = datas;
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_zcxg_item, parent, false);
    }

    @Override
    public ZcxgAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(ZcxgAdapter.DefaultViewHolder holder, int position) {
        holder.setData(datas.get(position));
        holder.setOnItemClickListener(mOnItemClickListener);
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvZcmc, tvFlmc, tvDj, tvSl, tvPl, tvJe, tvXh, tvGg, tvCj, tvCch;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvZcmc = (TextView) itemView.findViewById(R.id.tv_zcmc);
            tvFlmc = (TextView) itemView.findViewById(R.id.tv_flmc);
            tvDj = (TextView) itemView.findViewById(R.id.tv_dj);
            tvSl = (TextView) itemView.findViewById(R.id.tv_sl);
            tvPl = (TextView) itemView.findViewById(R.id.tv_pl);
            tvJe = (TextView) itemView.findViewById(R.id.tv_je);
            tvXh = (TextView) itemView.findViewById(R.id.tv_xh);
            tvGg = (TextView) itemView.findViewById(R.id.tv_gg);
            tvCj = (TextView) itemView.findViewById(R.id.tv_cj);
            tvCch = (TextView) itemView.findViewById(R.id.tv_cch);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(ZcxgBean.Zcxg zcxg) {
            tvZcmc.setText(zcxg.资产名称);
            tvFlmc.setText(zcxg.字符字段7);
            tvDj.setText(zcxg.单价);
            tvSl.setText(zcxg.数量);
            tvPl.setText(zcxg.批量);
            tvJe.setText(zcxg.金额);
            tvXh.setText(zcxg.型号);
            tvGg.setText(zcxg.规格);
            tvCj.setText(zcxg.厂家);
            tvCch.setText(zcxg.出厂号);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
