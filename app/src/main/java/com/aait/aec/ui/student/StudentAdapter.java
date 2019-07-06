package com.aait.aec.ui.student;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aait.aec.R;
import com.aait.aec.data.db.model.Student;
import com.aait.aec.ui.base.BaseViewHolder;
import com.aait.aec.utils.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    List<Student> albumList;
    private Callback mCallback;

    private boolean hide;

    public StudentAdapter(List<Student> categories) {
        this.albumList = categories;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    public void addItems(List<Student> students, boolean hide) {
        //only when first time should hide edit btn
        this.hide = hide;
        this.albumList.clear();
        this.albumList.addAll(students);

        notifyDataSetChanged();
    }

    public void setOnLongClick(Callback callback) {
        mCallback = callback;
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public interface Callback {
        void onItemClicked(Student student);
    }

    public class MyViewHolder extends BaseViewHolder {

        @BindView(R.id.std_row)
        TextView stdRow;

        @BindView(R.id.std_name)
        TextView stdName;

        @BindView(R.id.std_id)
        TextView stdId;

        @BindView(R.id.std_edit)
        ImageView btnEdit;

        Student album;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(int position) {

            if (hide)
                btnEdit.setVisibility(View.GONE);
            else
                btnEdit.setVisibility(View.VISIBLE);

            album = albumList.get(position);
            if (album != null) {

                stdRow.setText(String.valueOf(position + 1));
                stdName.setText(String.valueOf(album.getName()));
                stdId.setText(String.valueOf(album.getStdId()));

                btnEdit.setOnClickListener(v -> mCallback.onItemClicked(album));
                stdName.setOnClickListener(v -> CommonUtils.toast(album.getName()));
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
