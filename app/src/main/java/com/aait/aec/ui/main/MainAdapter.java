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

package com.aait.aec.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.aait.aec.R;
import com.aait.aec.data.db.model.Exam;
import com.aait.aec.ui.base.BaseViewHolder;
import com.aait.aec.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Janisharali on 25/05/2017.
 */


public class MainAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {

    List<Exam> albumList;
    List<Exam> filteredAlbumList;
    Callback mCallback;

    public MainAdapter(List<Exam> categories) {
        this.albumList = categories;
        filteredAlbumList = new ArrayList<>();
        filteredAlbumList.addAll(categories);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exam, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    public void addItems(List<Exam> sensors) {
        albumList.clear();
        albumList.addAll(sensors);

        filteredAlbumList.clear();
        filteredAlbumList.addAll(sensors);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return filteredAlbumList.size();
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredAlbumList = albumList;
                } else {
                    List<Exam> filteredList = new ArrayList<>();
                    for (Exam row : albumList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle() != null)
                            if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {// || row.getType().toLowerCase().contains(charSequence)) {
                                filteredList.add(row);
                            }
                    }

                    filteredAlbumList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredAlbumList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredAlbumList = (ArrayList<Exam>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface Callback {
        void onItemClicked(Exam exam);
    }

    public class MyViewHolder extends BaseViewHolder {

        @BindView(R.id.exam_date)
        TextView exDate;

        @BindView(R.id.exam_title)
        TextView exTitle;

        @BindView(R.id.exam_type)
        TextView exType;

        @BindView(R.id.exam_weight)
        TextView exWeight;

        @BindView(R.id.exam_instructor)
        TextView exInst;

        @BindView(R.id.exam_img_thumb)
        ImageView exIcon;

        Exam album;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(int position) {

            album = filteredAlbumList.get(position);
            if (album != null) {

                exDate.setText(String.valueOf(album.getDate()));
                exInst.setText(String.valueOf(album.getInst()));
                exTitle.setText(String.valueOf(album.getTitle()));
                exWeight.setText(String.valueOf(album.getWeight()));
                exType.setText(String.valueOf(album.getType()));

                itemView.setOnClickListener(view -> {
                    CommonUtils.toast(album.getTitle());
                    mCallback.onItemClicked(album);
                });
            }

        }

        @Override
        protected void clear() {
            exDate.setText("");
            exInst.setText("");
            exTitle.setText("");
            exWeight.setText("");
            exType.setText("");
        }
    }
}
