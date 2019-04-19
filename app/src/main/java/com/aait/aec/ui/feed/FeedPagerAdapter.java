/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.aait.aec.ui.feed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aait.aec.R;
import com.aait.aec.data.db.model.Category;
import com.aait.aec.ui.base.BaseViewHolder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Janisharali on 25/05/2017.
 */

public class FeedPagerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    List<Category> albumList;

    public FeedPagerAdapter(List<Category> categories) {
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
        Category album = albumList.get(position);

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
            title = (TextView) view.findViewById(R.id.title);


        }

        public void onBind(int position) {

            album = albumList.get(position);

            title.setText(String.valueOf(album.title));
            subtitle.setText(String.valueOf(album.subTitle));

            // loading album cover using Glide library
            Glide.with(itemView.getContext()).load(album.thumb).into(icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                showPopupMenu(holder.overflow);
                }
            });
        }

        @Override
        protected void clear() {

        }
    }
}
