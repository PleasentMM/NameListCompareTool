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
import com.mcc.compare.activity.StaticNameListActivity;
import com.mcc.compare.bean.StaticGrade;
import com.mcc.compare.bean.StudentStatic;
import com.mcc.compare.constant.Constant;

import org.litepal.LitePal;

/**
 * @CreateDate: 2020/2/7 15:21
 * @Author MCC
 * 保存的
 */
public class ItemStudentStaticListSample implements IEntity {

    private StaticGrade staticMarks;

    public ItemStudentStaticListSample(StaticGrade staticMarks) {
        this.staticMarks = staticMarks;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_student_static_list_sample;
    }

    @Override
    public void bindView(final BaseAdapter baseAdapter, BaseAdapter.ViewHolder holder, IEntity data, final int position) {
        final Context context = holder.itemView.getContext();
        ((TextView)holder.itemView.findViewById(R.id.item_student_static_sample))
                .append(staticMarks.getGrade());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StaticNameListActivity.class);
                intent.putExtra(Constant.STATIC_STU_CODE,staticMarks.getCode());
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("删除此名单?");
                dialog.setPositiveButton("确定删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        LitePal.deleteAll(StaticGrade.class,"code = ?",staticMarks.getCode());
                        LitePal.deleteAll(StudentStatic.class,"code = ?",staticMarks.getCode());

                        baseAdapter.remove(position);

                        Toast.makeText(context,"已删除",Toast.LENGTH_LONG).show();
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
