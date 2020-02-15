package com.mcc.compare.itemViewModel;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.library.BaseAdapter;
import com.example.library.IEntity;
import com.mcc.compare.R;
import com.mcc.compare.activity.ItemStudentDetailActivity;
import com.mcc.compare.bean.StudentRecord;
import com.mcc.compare.constant.Constant;

/**
 * @CreateDate: 2020/2/7 22:53
 * @Author MCC
 * 可标注颜色Item
 */
public class ItemStudentSampleCompare implements IEntity {

    private StudentRecord studentRecord;

    public ItemStudentSampleCompare(StudentRecord studentRecord) {
        this.studentRecord = studentRecord;
    }

    public void setState(int state) {
        studentRecord.setState(state);
    }

    public StudentRecord getStudentRecord() {
        return studentRecord;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_student_sample;
    }

    @Override
    public void bindView(BaseAdapter baseAdapter, BaseAdapter.ViewHolder holder, IEntity data, int position) {
        final Context context = holder.itemView.getContext();
        final TextView textView = holder.itemView.findViewById(R.id.student_sample);
        textView.setText(studentRecord.getName());

        if (studentRecord.getState() == Constant.STATE_ONE) {
            textView.setBackgroundColor(context.getResources().getColor(R.color.colorGrey));
        } else if (studentRecord.getState() == Constant.STATE_TWO) {
            textView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        } else if (studentRecord.getState() == Constant.STATE_THREE) {
            textView.setBackgroundColor(context.getResources().getColor(R.color.colorYellow));
        } else if (studentRecord.getState() == Constant.STATE_FOUR) {
            textView.setBackgroundColor(context.getResources().getColor(R.color.colorOrange));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击进行三种颜色的循环
                if (studentRecord.getState() == Constant.STATE_ONE) {
                    studentRecord.setState(Constant.STATE_TWO);
                    textView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                } else if (studentRecord.getState() == Constant.STATE_TWO) {
                    studentRecord.setState(Constant.STATE_THREE);
                    textView.setBackgroundColor(context.getResources().getColor(R.color.colorYellow));
                } else if (studentRecord.getState() == Constant.STATE_THREE) {
                    studentRecord.setState(Constant.STATE_FOUR);
                    textView.setBackgroundColor(context.getResources().getColor(R.color.colorOrange));
                } else if (studentRecord.getState() == Constant.STATE_FOUR){
                    studentRecord.setState(Constant.STATE_ONE);
                    textView.setBackgroundColor(context.getResources().getColor(R.color.colorGrey));
                }
                studentRecord.save();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context, ItemStudentDetailActivity.class);
                intent.putExtra(Constant.STU_STATIC_CODE,studentRecord.getCodeInStatic());
                intent.putExtra(Constant.STU_NAME,studentRecord.getName());
                context.startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public String toString() {
        return "ItemStudentSampleCompare{" +
                "studentRecord=" + studentRecord.toString() +
                '}';
    }
}
