package com.example.bookreviews.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreviews.R;
import com.example.bookreviews.database.entity.BookEntity;
import com.example.bookreviews.database.entity.ReviewEntity;
import com.example.bookreviews.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<T> mData;
    private RecyclerViewItemClickListener mListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;
        ViewHolder(TextView textView){
            super(textView);
            mTextView = textView;
        }
    }

    public RecyclerAdapter(RecyclerViewItemClickListener listener){
        mListener = listener;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> mListener.onItemClick(view, viewHolder.getAdapterPosition()));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        T item = mData.get(position);
        if (item.getClass().equals(BookEntity.class))
            holder.mTextView.setText(((BookEntity) item).getTitle());
        if (item.getClass().equals(ReviewEntity.class))
            holder.mTextView.setText(((ReviewEntity) item).getAuthor());

    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }



    public void setData(final List<T> data) {
        if (mData == null) {
            mData = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mData.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof BookEntity) {
                        return ((BookEntity) mData.get(oldItemPosition)).getId().equals(((BookEntity) data.get(newItemPosition)).getId());
                    }
                    if (mData instanceof ReviewEntity) {
                        return ((ReviewEntity) mData.get(oldItemPosition)).getId().equals(
                                ((ReviewEntity) data.get(newItemPosition)).getId());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof BookEntity) {
                        BookEntity newAccount = (BookEntity) data.get(newItemPosition);
                        BookEntity oldAccount = (BookEntity) mData.get(newItemPosition);
                        return newAccount.getId().equals(oldAccount.getId())
                                && Objects.equals(newAccount.getTitle(), oldAccount.getTitle())
                                && Objects.equals(newAccount.getAuthor(), oldAccount.getAuthor())
                                && Objects.equals(newAccount.getEdition(), oldAccount.getEdition())
                                && Objects.equals(newAccount.getCategory(), oldAccount.getCategory())
                                && Objects.equals(newAccount.getYearPublished(), oldAccount.getYearPublished())
                                && Objects.equals(newAccount.getPlotSummary(), oldAccount.getPlotSummary());
                    }
                    if (mData instanceof ReviewEntity) {
                        ReviewEntity newClient = (ReviewEntity) data.get(newItemPosition);
                        ReviewEntity oldClient = (ReviewEntity) mData.get(newItemPosition);
                        return Objects.equals(newClient.getAuthor(), oldClient.getAuthor())
                                && Objects.equals(newClient.getDate(), oldClient.getDate())
                                && Objects.equals(newClient.getGrade(), oldClient.getGrade())
                                && newClient.getId_book().equals(oldClient.getId_book());
                    }
                    return false;
                }
            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }
}
