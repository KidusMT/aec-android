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

package com.aait.aec.ui.student;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aait.aec.R;
import com.aait.aec.data.db.model.Exam;
import com.aait.aec.ui.base.BaseViewHolder;
import com.aait.aec.utils.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Janisharali on 25/05/2017.
 */

public class StudentAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    List<Exam> albumList;

    public StudentAdapter(List<Exam> categories) {
        this.albumList = categories;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    public void addItems(List<Exam> sensors) {
        this.albumList.clear();
        this.albumList.addAll(sensors);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class MyViewHolder extends BaseViewHolder {

        @BindView(R.id.std_row)
        TextView stdRow;

        @BindView(R.id.std_name)
        TextView stdName;

        @BindView(R.id.std_id)
        TextView stdId;

        Exam album;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(int position) {

            album = albumList.get(position);
            if (album != null) {

                exDate.setText(String.valueOf(album.getDate()));
                exInst.setText(String.valueOf(album.getInst()));
                exTitle.setText(String.valueOf(album.getTitle()));
                exWeight.setText(String.valueOf(album.getWeight()));
                exType.setText(String.valueOf(album.getType()));

                // loading album cover using Glide library
//                Glide.with(itemView.getContext()).load(album.getThumb()).into(album.getThumb());

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
            exDate.setText("");
            exInst.setText("");
            exTitle.setText("");
            exWeight.setText("");
            exType.setText("");
        }
    }
}
