package com.aait.aec.ui.addAnswerDialog;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aait.aec.R;
import com.aait.aec.ui.base.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnswerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    int numOfQues;

    private Callback mCallback;

    public AnswerAdapter(int numOfQues) {
        this.numOfQues = numOfQues;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_answer, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    public void addItems(int numOfQues) {
        this.numOfQues = numOfQues;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return numOfQues;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {
        void onItemClicked(int position, int answer);
    }

    public class MyViewHolder extends BaseViewHolder {

        @BindView(R.id.choice_answer)
        RadioGroup mRadioGroup;

        @BindView(R.id.roll_no)
        TextView rollNo;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("DefaultLocale")
        public void onBind(int position) {

            rollNo.setText(String.format("%d.  ", position + 1));

            mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {

                int selectedId = mRadioGroup.getCheckedRadioButtonId();
                int answer = 0;
                switch (selectedId) {
                    case R.id.ans_a:
                        answer = 0;
                        break;
                    case R.id.ans_b:
                        answer = 1;
                        break;
                    case R.id.ans_c:
                        answer = 2;
                        break;
                    case R.id.ans_d:
                        answer = 3;
                        break;
                    case R.id.ans_e:
                        answer = 4;
                        break;
                }
                mCallback.onItemClicked(position, answer);

            });
        }

        @Override
        protected void clear() {
        }
    }
}
