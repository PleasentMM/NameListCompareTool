package com.mcc.compare.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.library.BaseAdapter;
import com.mcc.compare.R;
import com.mcc.compare.StringHelper.StudentPattern;
import com.mcc.compare.activity.base.BaseAppCompatActivity;
import com.mcc.compare.bean.StaticGrade;
import com.mcc.compare.bean.StudentStatic;
import com.mcc.compare.itemViewModel.ItemStudentStaticListSample;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpgradeActivity extends BaseAppCompatActivity {

    private final String TAG = "Upgrade";

    private Toolbar toolbar;
    //显示已有的学生名单
    private RecyclerView recyclerView;
    private BaseAdapter adapter;

    //输入名单备注信息
    private EditText editTextInfo;
    //输入学生名单
    private EditText editTextNameList;
    //进行保存
    private Button buttonUpgrade;
    //删除所有名单
    private Button buttonDeleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);
        toolbar = findViewById(R.id.upgrade_toolbar);

        recyclerView = findViewById(R.id.upgrade_saved_studentList);

        editTextInfo = findViewById(R.id.upgrade_info);
        editTextNameList = findViewById(R.id.upgrade_editText);

        buttonUpgrade = findViewById(R.id.upgrade_upgrade);

        buttonDeleteAll = findViewById(R.id.upgrade_deleteAll);

        initView();
        //加载已保存的静态名单
        loadGradeList();
    }


    //添加新的名单信息过程
    private void addNewStaticStudentList(final String grade, final String string) {
        //学生名单
        final List<String> nameList = StudentPattern.toStudentList(string);
        //确认对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("分析出的名单信息:");
        builder.setMessage("备注信息:"+grade+"\n名单:"+nameList.toString()+" 共"
                +nameList.size()+"人");
        builder.setPositiveButton("确定保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //确定后组装数据并保存到数据库

                String code = System.currentTimeMillis()+"";

                List<StudentStatic> studentList = new ArrayList<>();
                //记录分组信息
                StaticGrade staticMarks = new StaticGrade(code,grade);

                staticMarks.save();

                for (int j = 0; j < nameList.size(); j++) {
                    StudentStatic studentStatic = new StudentStatic(code,grade,nameList.get(j));
                    studentList.add(studentStatic);
                }

                Log.i(TAG, "save: "+studentList.toString());

                //保存此次分组名单表
                LitePal.saveAll(studentList);

                Toast.makeText(getApplicationContext(),
                        "完成存储",Toast.LENGTH_SHORT).show();

                adapter.add(new ItemStudentStaticListSample(staticMarks));

                Toast.makeText(getApplicationContext(),
                        "列表已更新",Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("取消进行修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //取消重新生成
            }
        });
        builder.show();
    }

    private void loadGradeList() {
        List<StaticGrade> grades = LitePal.findAll(StaticGrade.class);
        List<ItemStudentStaticListSample> samples = new ArrayList<>();
        if (grades.size() > 0) {
            for (int i = 0; i < grades.size(); i++) {
                samples.add(new ItemStudentStaticListSample(grades.get(i)));
            }
        }
        adapter.add(samples);
    }

    private void initView() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //初始化recycleView
        adapter = new BaseAdapter.Builder().build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        buttonUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((editTextNameList.getText()+"").equals("")
                        ||(editTextInfo.getText()+"").equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "请完善内容",Toast.LENGTH_LONG).show();
                } else {
                    addNewStaticStudentList(
                            editTextInfo.getText()+"",
                            editTextNameList.getText()+"");
                }
            }
        });

        buttonDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(UpgradeActivity.this);
                dialog.setTitle("确定删除吗,不可恢复!");
                dialog.setMessage("删除现在所记录的所有名单信息?");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LitePal.deleteAll(StaticGrade.class);
                        LitePal.deleteAll(StudentStatic.class);
                        adapter.clearData();
                        Toast.makeText(UpgradeActivity.this,"已清除所有数据",
                                Toast.LENGTH_LONG).show();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });

    }
}
