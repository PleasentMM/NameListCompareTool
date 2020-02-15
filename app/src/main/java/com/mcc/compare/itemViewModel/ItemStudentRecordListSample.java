package com.mcc.compare.itemViewModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library.BaseAdapter;
import com.example.library.IEntity;
import com.mcc.compare.R;
import com.mcc.compare.activity.CompareRecordDetailActivity;
import com.mcc.compare.bean.RecordMarks;
import com.mcc.compare.bean.StudentRecord;
import com.mcc.compare.constant.Constant;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;

/**
 * @CreateDate: 2020/2/6 19:13
 * @Author MCC
 * 比对信息记录
 */
public class ItemStudentRecordListSample implements IEntity {

    private RecordMarks recordMarks;

    public ItemStudentRecordListSample(RecordMarks recordMarks) {
        this.recordMarks = recordMarks;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_student_record_list_sample;
    }

    @Override
    public void bindView(final BaseAdapter baseAdapter, BaseAdapter.ViewHolder holder, final IEntity data, final int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:SS");
        final Context context = holder.itemView.getContext();
        String timeString = sdf.format(recordMarks.getDate());
        //备注信息
        ((TextView)holder.itemView.findViewById(R.id.item_record_info)).append(recordMarks.getMarks());
        //记录时间
        ((TextView)holder.itemView.findViewById(R.id.item_record_textView)).append(timeString);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CompareRecordDetailActivity.class);
                intent.putExtra(Constant.STU_RECORD,recordMarks);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("删除此条记录?");
                dialog.setPositiveButton("确定删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        LitePal.deleteAll(RecordMarks.class,"code = ?",recordMarks.getCode());
                        LitePal.deleteAll(StudentRecord.class,"code = ?",recordMarks.getCode());
                        Toast.makeText(context,"完成删除",Toast.LENGTH_LONG).show();

                        baseAdapter.remove(position);
                    }
                });
                dialog.setNegativeButton("取消删除", new DialogInterface.OnClickListener() {
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
