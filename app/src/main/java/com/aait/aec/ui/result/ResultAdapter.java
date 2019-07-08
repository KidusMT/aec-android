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

public class ResultAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    List<Student> albumList;
    List<com.aait.aec.data.network.model.Student> students;
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

    public void updateItems(List<com.aait.aec.data.network.model.Student> students) {
        this.students.clear();
        this.albumList.clear();
        this.students.addAll(students);
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

        com.aait.aec.data.network.model.Student std;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(int position) {


            if (albumList != null && albumList.size() > 0) {
                album = albumList.get(position);
                if (album != null) {

                    stdRow.setText(String.format(" %s", position + 1));
                    stdName.setText(String.valueOf(album.getName()));
                    stdId.setText(String.valueOf(album.getStdId()));
                    strScore.setText(String.valueOf(album.getScore()));

                    itemView.setOnClickListener(view -> mCallback.onItemClicked(album));
                }
            }


            if (students != null && students.size() > 0) {
                std = students.get(position);
//                if (std != null) {

                    stdRow.setText(String.format(" %s", position + 1));
                    stdName.setText(String.valueOf(std.getName()));
                    stdId.setText(String.valueOf(std.getId()));
                    strScore.setText(String.valueOf(std.getResult()));

//                    itemView.setOnClickListener(view -> mCallback.onItemClicked(std));
//                }
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
