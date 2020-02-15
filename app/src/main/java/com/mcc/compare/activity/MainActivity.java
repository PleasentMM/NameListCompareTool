package com.mcc.compare.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.library.BaseAdapter;
import com.mcc.compare.R;
import com.mcc.compare.StringHelper.StudentPattern;
import com.mcc.compare.activity.base.BaseAppCompatActivity;
import com.mcc.compare.bean.RecordMarks;
import com.mcc.compare.bean.StaticGrade;
import com.mcc.compare.bean.StudentRecord;
import com.mcc.compare.bean.StudentStatic;
import com.mcc.compare.constant.Constant;
import com.mcc.compare.itemViewModel.ItemStudentSampleCompare;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends BaseAppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private BaseAdapter adapter;

    //点击选择名单
    private Button buttonChoose;
    //输入名单
    private EditText editTextStudents;
    //开始对比
    private Button buttonPattern;
    //记录此次比对结果
    private Button buttonRecord;

    private List<ItemStudentSampleCompare> compares;

    private String currentGrade;

    private String currentCode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.main_recyclerView);
        toolbar = findViewById(R.id.main_toolbar);
        editTextStudents = findViewById(R.id.main_editText);
        buttonPattern = findViewById(R.id.main_pattern);
        buttonChoose = findViewById(R.id.main_choose);
        buttonRecord = findViewById(R.id.main_record);

        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<StaticGrade> grades = LitePal.findAll(StaticGrade.class);
        if (grades.size() == 0) {
            adapter.clearData();
        }
    }

    private void beginPattern(final String string) {

        final List<String> list = StudentPattern.toStudentList(string);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("分析出的名单:");
        builder.setMessage(list.toString()+"\n 共"+list.size()+"人");
        builder.setPositiveButton("开始比对", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //更新RecyclerView Item的背景色
                //初始化对比结果
                adapter.clearData();
                for (int j = 0; j < compares.size(); j++) {
                    if (list.contains(compares.get(j).getStudentRecord().getName())) {
                        compares.get(j).setState(Constant.STATE_TWO);
                    } else {
                        compares.get(j).setState(Constant.STATE_ONE);
                    }
                }
                //在加载名单
                adapter.add(compares);
                Toast.makeText(MainActivity.this,"列表已标色!",Toast.LENGTH_LONG).show();

            }
        });
        builder.setNegativeButton("取消修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    //在对话框加载已有的名单code,grade
    private List<StaticGrade> loadStaticList() {
        //找到所有名单记录信息
        List<StaticGrade> staticGrades = LitePal.findAll(StaticGrade.class);
        Log.i("Main", "loadStaticList: "+staticGrades.toString());
        return staticGrades;
    }

    //加载已选择的静态名单
    private void loadGradeList(String code,String grade) {
        //从数据库取名单根据code
        List<StudentStatic> studentStaticList = LitePal
                .where("code = ?",code)
                .find(StudentStatic.class);
        Log.i("where", "loadGradeList: "+studentStaticList.toString());
        //初始化要对比的名单
        compares = new ArrayList<>();
        //设置好现在比对的学生班级信息
        currentGrade = grade;
        currentCode = code;
        //填写要对对比的名单的基本信息
        for (StudentStatic s:studentStaticList) {
            compares.add(new ItemStudentSampleCompare(
                    new StudentRecord(currentGrade, Constant.STATE_ONE,currentCode,s.getName())));
        }

        Log.i("StudentRecord", "loadGradeList: "+compares.toString());

        adapter.add(compares);

        Toast.makeText(getApplicationContext(),
                "已载入并初始化，共"+ compares.size()+"人",
                Toast.LENGTH_LONG).show();

    }

    private void initView() {
        toolbar.inflateMenu(R.menu.main_toolbar_menu);
        //顶部菜单栏
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.main_recorded:
                        startActivity(new Intent(getApplicationContext(),
                                CompareRecordActivity.class));
                        return true;
                    case R.id.main_upgrade:
                        startActivity(new Intent(getApplicationContext(),
                                UpgradeActivity.class));
                        return true;
                }
                return false;
            }
        });
        //配置RecyclerView
        adapter = new BaseAdapter.Builder().build();
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),
                4,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        //点击开始对比
        buttonPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((editTextStudents.getText()+"").equals("")
                        || compares == null) {
                    Toast.makeText(getApplicationContext(),
                            "请输入内容或者载入名单",Toast.LENGTH_LONG).show();
                } else {
                    //如果内容不空进行对比
                    beginPattern(editTextStudents.getText()+"");
                }
            }
        });

        //点击选择列表名单
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadStaticList().size() > 0) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("请选择名单:");
                    //载入code
                    final String[] chooseArray = new String[loadStaticList().size()];
                    for (int i = 0; i < loadStaticList().size(); i++) {
                        chooseArray[i] = loadStaticList().get(i).getGrade();
                    }

                    dialog.setItems(chooseArray, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapter.clearData();
                            loadGradeList(loadStaticList().get(which).getCode(),
                                    loadStaticList().get(which).getGrade());
                            Log.i("load", "onClick: "+loadStaticList().get(which).getCode()+
                                    loadStaticList().get(which).getGrade());
                        }
                    });
                    dialog.show();
                } else {
                    Toast.makeText(MainActivity.this,"请先输入名单!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        //点击输入对比项目名进行保存
        buttonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果以经载入
                if (compares != null) {
                    //记录此刻的时间
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("输入对比项目名进行保存");

                    final LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                    final View inputView = inflater.inflate(R.layout.item_dialog_edit_text, null);

                    dialog.setView(inputView);

                    dialog.setPositiveButton("确定保存", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //记录此刻的时间
                            Date nowDate = new Date();
                            //生成的编码
                            String code = System.currentTimeMillis()+"";
                            EditText marks = inputView.findViewById(R.id.dialog_exitText);

                            RecordMarks recordMarks = new RecordMarks(code,marks.getText() + "", nowDate);

                            List<StudentRecord> records = new ArrayList<>();
                            //取出对比的结果化为RecordMarks
                            for (int i = 0; i < compares.size(); i++) {
                                StudentRecord studentRecord = new StudentRecord();
                                studentRecord.setCode(code);
                                studentRecord.setMarks(marks.getText()+"");
                                studentRecord.setCodeInStatic(compares.get(i).getStudentRecord().getCodeInStatic());
                                studentRecord.setName(compares.get(i).getStudentRecord().getName());
                                studentRecord.setGrade(compares.get(i).getStudentRecord().getGrade());
                                studentRecord.setState(compares.get(i).getStudentRecord().getState());
                                studentRecord.setDate(nowDate);
                                records.add(studentRecord);
                            }

                            Log.i("final", "onClick: "+records.toString());
                            //保存到数据库
                            recordMarks.save();
                            LitePal.saveAll(records);

                            Toast.makeText(MainActivity.this,
                                    "完成记录保存\n记录项目名称:" + marks.getText(),
                                    Toast.LENGTH_LONG).show();

                        }
                    });
                    dialog.setNegativeButton("取消保存", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                } else {
                    Toast.makeText(MainActivity.this,"请先载入比对",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
