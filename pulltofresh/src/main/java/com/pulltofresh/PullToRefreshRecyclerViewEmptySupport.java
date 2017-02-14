package com.pulltofresh;
//┏┓　　　┏┓ 
//┏┛┻━━━┛┻┓ 
//┃　　　　　　　┃ 　 
//┃　　　━　　　┃ 
//┃　┳┛　┗┳　┃ 
//┃　　　　　　　┃ 
//┃　　　┻　　　┃ 
//┃　　　　　　　┃ 
//┗━┓　　　┏━┛ 
//┃　　　┃  神兽保佑　　　　　　　　 
//┃　　　┃  代码无BUG！ 
//┃　　　┗━━━┓ 
//┃　　　　　　　┣┓ 
//┃　　　　　　　┏┛ 
//┗┓┓┏━┳┓┏┛ 
// ┃┫┫　┃┫┫ 
// ┗┻┛　┗┻┛

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.pulltofresh.extras.RecyclerViewEmptySupport;

/**
 * Created by lk on 16/6/23.
 */
public class PullToRefreshRecyclerViewEmptySupport extends PullToRefreshBase<RecyclerViewEmptySupport> {

    public PullToRefreshRecyclerViewEmptySupport(Context context) {
        super(context);
    }

    public PullToRefreshRecyclerViewEmptySupport(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshRecyclerViewEmptySupport(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshRecyclerViewEmptySupport(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected RecyclerViewEmptySupport createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerViewEmptySupport recyclerView;
        recyclerView = new RecyclerViewEmptySupport(context, attrs);
        recyclerView.setId(R.id.recyclerview);
        return recyclerView;
    }

    @Override
    protected boolean isReadyForPullStart() {
        if (mRefreshableView.getChildCount() <= 0)
            return true;
        int firstVisiblePosition = mRefreshableView.getChildPosition(mRefreshableView.getChildAt(0));
        if (firstVisiblePosition == 0)
            return mRefreshableView.getChildAt(0).getTop() == mRefreshableView.getPaddingTop();
        else
            return false;

    }

    @Override
    protected boolean isReadyForPullEnd() {
        View lastChild = mRefreshableView.getChildAt(mRefreshableView.getChildCount() - 1);
        int lastVisiblePosition = mRefreshableView.getChildPosition(lastChild);
        if (mRefreshableView.getAdapter() != null && lastChild != null) {
            if (lastVisiblePosition >= mRefreshableView.getAdapter().getItemCount()-1) {
                return lastChild.getBottom() <= mRefreshableView.getBottom();
            }
        }
        return false;
    }

}
