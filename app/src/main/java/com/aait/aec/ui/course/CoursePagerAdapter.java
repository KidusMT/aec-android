package com.aait.aec.ui.course;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aait.aec.R;
import com.aait.aec.data.db.model.Category;
import com.aait.aec.ui.base.BaseViewHolder;
import com.aait.aec.utils.CommonUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Janisharali on 25/05/2017.
 */

public class CoursePagerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    List<Category> albumList;

    public CoursePagerAdapter(List<Category> categories) {
        this.albumList = categories;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    public void addItems(List<Category> sensors) {
        this.albumList.clear();
        this.albumList.addAll(sensors);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class MyViewHolder extends BaseViewHolder {

        @BindView(R.id.category_title)
        TextView title;

        @BindView(R.id.category_count)
        TextView subtitle;

        @BindView(R.id.thumbnail)
        ImageView icon;

        Category album;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(int position) {

            album = albumList.get(position);
            if (album != null) {

                title.setText(String.valueOf(album.getTitle()));
                subtitle.setText(String.valueOf(album.getSubTitle()));

                // loading album cover using Glide library
                Glide.with(itemView.getContext()).load(album.getThumb()).into(icon);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                showPopupMenu(holder.overflow);
                        CommonUtils.toast(album.getTitle());
                    }
                });
            }

        }

        @Override
        protected void clear() {
            title.setText("");
            subtitle.setText("");
        }
    }
}
