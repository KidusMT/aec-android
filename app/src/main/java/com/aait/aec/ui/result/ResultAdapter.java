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

package com.aait.aec.ui.result;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aait.aec.R;
import com.aait.aec.data.db.model.Student;
import com.aait.aec.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Janisharali on 25/05/2017.
 */

public class ResultAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    List<Student> albumList;
    private Callback mCallback;

    public ResultAdapter(List<Student> categories) {
        this.albumList = categories;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_result, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    public void addItems(List<Student> sensors) {
        this.albumList.clear();
        this.albumList.addAll(sensors);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {
        void onItemClicked(Student student);
    }

    public class MyViewHolder extends BaseViewHolder {

        @BindView(R.id.result_std_row)
        TextView stdRow;

        @BindView(R.id.result_std_result)
        TextView strScore;

        @BindView(R.id.result_std_name)
        TextView stdName;

        @BindView(R.id.result_std_id)
        TextView stdId;

        Student album;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(int position) {

            album = albumList.get(position);
            if (album != null) {

                stdRow.setText(String.format(" %s", position));
                stdName.setText(String.valueOf(album.getName()));
                stdId.setText(String.valueOf(album.getStdId()));
                strScore.setText(String.valueOf(album.getScore()));

                itemView.setOnClickListener(view -> mCallback.onItemClicked(album));
            }

        }

        @Override
        protected void clear() {
            stdRow.setText("");
            stdName.setText("");
            stdId.setText("");
        }
    }
}
