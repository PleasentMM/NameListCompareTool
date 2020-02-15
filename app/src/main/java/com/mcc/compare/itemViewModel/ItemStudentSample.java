package com.mcc.compare.itemViewModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.library.BaseAdapter;
import com.example.library.IEntity;
import com.mcc.compare.R;
import com.mcc.compare.activity.ItemStudentDetailActivity;
import com.mcc.compare.bean.StudentStatic;
import com.mcc.compare.constant.Constant;

import org.litepal.LitePal;

/**
 * @CreateDate: 2020/2/6 11:42
 * @Author MCC
 */
public class ItemStudentSample implements IEntity {

    private StudentStatic student;

    public ItemStudentSample(StudentStatic student) {
        this.student = student;
    }

    public StudentStatic getStudent() {
        return student;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_student_sample;
    }

    @Override
    public void bindView(final BaseAdapter baseAdapter, final BaseAdapter.ViewHolder holder, final IEntity data, final int position) {
        final Context context = holder.itemView.getContext();
        TextView textView = holder.itemView.findViewById(R.id.student_sample);
        textView.setText(student.getName());
        textView.setBackgroundColor(context.getResources().getColor(R.color.colorGrey));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemStudentDetailActivity.class);
                intent.putExtra(Constant.STU_STATIC_CODE,student.getCode());
                intent.putExtra(Constant.STU_NAME,student.getName());
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("删除此学生信息？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LitePal.deleteAll(StudentStatic.class,
                                "code = ? and name = ?",student.getCode(),student.getName());
                        baseAdapter.remove(position);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
                return false;
            }
        });

    }
}
