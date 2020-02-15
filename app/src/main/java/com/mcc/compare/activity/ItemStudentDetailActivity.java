package com.mcc.compare.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mcc.compare.R;
import com.mcc.compare.activity.base.BaseAppCompatActivity;
import com.mcc.compare.bean.StudentStatic;
import com.mcc.compare.constant.Constant;

import org.litepal.LitePal;

import java.util.List;

public class ItemStudentDetailActivity extends BaseAppCompatActivity {

    private Toolbar toolbar;

    private StudentStatic student;

    private Intent intent;

    private TextView textViewName;
    private TextView textViewGrade;
    private TextView textViewTel;
    private TextView textViewAddress;
    private TextView textViewInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_student_detail);
        toolbar = findViewById(R.id.toolbar);
        textViewName = findViewById(R.id.item_student_name);
        textViewGrade = findViewById(R.id.item_student_grade);
        textViewTel = findViewById(R.id.item_student_tel);
        textViewAddress = findViewById(R.id.item_student_address);
        textViewInfo = findViewById(R.id.item_student_info);
        initView();
    }

    private void initView() {

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        intent = getIntent();

        String code = intent.getStringExtra(Constant.STU_STATIC_CODE);
        String name = intent.getStringExtra(Constant.STU_NAME);

        Log.i("ExtraGet", "initView:code:"+code+"name:"+name);

        final List<StudentStatic> staticList = LitePal
                .where("code = ? and name = ?",code,name)
                .find(StudentStatic.class);



        Log.i("ExtraFind", "initView: "+staticList.toString());

        if (staticList.size() > 0) {

            student = staticList.get(0);

            toolbar.setTitle(student.getName()+"的详细信息");

            textViewName.append(student.getName()+" ");

            if (student.getGrade() == null){
                textViewGrade.append("未填写");
            } else {
                textViewGrade.append(student.getGrade());
            }


            if (student.getTel() == null){
                textViewTel.append("未填写");
            } else {
                textViewTel.append(student.getTel());
            }

            if (student.getAddress() == null) {
                textViewAddress.append("未填写");
            } else {
                textViewAddress.append(student.getAddress()+"");
            }

            if (student.getInfo() == null) {
                textViewInfo.append("无备注信息");
            } else {
                textViewInfo.append(student.getInfo());
            }
        }

        textViewTel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ItemStudentDetailActivity.this);
                dialog.setTitle("请输入要变更的电话号码");

                final LayoutInflater inflater = LayoutInflater.from(ItemStudentDetailActivity.this);
                final View inputView = inflater.inflate(R.layout.item_dialog_edit_text, null);
                dialog.setView(inputView);

                dialog.setPositiveButton("完成保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText tel = inputView.findViewById(R.id.dialog_exitText);
                        String telString = tel.getText()+"";

                        textViewTel.setText("电话号码："+telString);

                        student.setTel(telString);

                        student.save();
                    }
                });

                dialog.setNegativeButton("取消更改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();

                return false;
            }
        });


        textViewAddress.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ItemStudentDetailActivity.this);
                dialog.setTitle("请输入要变更的家庭住址");

                final LayoutInflater inflater = LayoutInflater.from(ItemStudentDetailActivity.this);
                final View inputView = inflater.inflate(R.layout.item_dialog_edit_text, null);
                dialog.setView(inputView);

                dialog.setPositiveButton("完成保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText address = inputView.findViewById(R.id.dialog_exitText);
                        String addressString = address.getText()+"";

                        textViewAddress.setText("家庭住址："+addressString);

                        student.setAddress(addressString);

                        student.save();
                    }
                });

                dialog.setNegativeButton("取消更改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
                return false;
            }
        });

        textViewInfo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ItemStudentDetailActivity.this);
                dialog.setTitle("请输入要变更的备注信息");

                final LayoutInflater inflater = LayoutInflater.from(ItemStudentDetailActivity.this);
                final View inputView = inflater.inflate(R.layout.item_dialog_edit_text, null);
                dialog.setView(inputView);

                dialog.setPositiveButton("完成保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText info = inputView.findViewById(R.id.dialog_exitText);
                        String infoString = info.getText()+"";

                        textViewInfo.setText("备注信息："+infoString);

                        student.setInfo(infoString);

                        student.save();
                    }
                });

                dialog.setNegativeButton("取消更改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
                return false;
            }
        });

        textViewTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (student.getTel() != null) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:"+student.getTel());
                    callIntent.setData(data);
                    startActivity(callIntent);
                } else {
                    Toast.makeText(ItemStudentDetailActivity.this,"电话为空",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}
