package com.foxconn.crd.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

/**
 * Created by qinicy on 16/1/28.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    /**
     * click listener
     */
    protected OnItemClickListener mOnItemClickListener;
    /**
     * long click listener
     */
    protected OnItemLongClickListener mOnItemLongClickListener;
    protected Context mContext;

    /**
     * data
     */
    protected List<T> mList;
    /**
     * @param context the Context of view
     * @param list the datas to attach the adapter
     */
    public BaseRecyclerViewAdapter(Context context,List<T> list) {
        mContext = context;
        mList = list;
    }

    /**
     * set a long click listener
     *
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    /**
     * set a click listener
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    /**
     * get a item by index
     *
     * @param position
     * @return
     */
    protected T getItem(int position) {
        return mList != null?mList.get(position):null;
    }

    @Override
    public int getItemCount() {
        return mList != null?mList.size():0;
    }

    /**
     * inflate a view by viewgroup ,id ,etc
     *
     * @param viewGroup
     * @param layoutId
     * @return
     */
    protected View inflateItemView(ViewGroup viewGroup, int layoutId) {
        return inflateItemView(viewGroup, layoutId, false);
    }

    /**
     * inflate a view by viewgroup ,id ,etc
     *
     * @param viewGroup
     * @param layoutId
     * @param attach
     * @return
     */
    protected View inflateItemView(ViewGroup viewGroup, int layoutId, boolean attach) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, attach);
    }

    /**
     * a final function to avoid you override
     * use template design pattern
     *
     * @param vh
     * @param position
     */
    @Override
    public final void onBindViewHolder(ViewHolder vh, int position) {
        final T item = getItem(position);
        bindDataToItemView(vh, item);
        bindItemViewClickListener(vh, item);
    }

    /**
     * bind data to itemview
     *
     * @param vh   viewholder
     * @param item item
     */
    protected abstract void bindDataToItemView(ViewHolder vh, T item);
    /**
     * bind click listner to itemview
     *
     * @param vh   viewholder
     * @param item item
     */
    protected final void bindItemViewClickListener(ViewHolder vh, final T item) {
        if (mOnItemClickListener != null) {
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(view, item);
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            vh.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemLongClickListener.onLongClick(v, item);
                    return true;
                }
            });
        }
    }


}
