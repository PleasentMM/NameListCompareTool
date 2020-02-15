package com.mcc.compare.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.library.BaseAdapter;
import com.mcc.compare.R;
import com.mcc.compare.bean.StudentStatic;
import com.mcc.compare.constant.Constant;
import com.mcc.compare.itemViewModel.ItemStudentSample;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class StaticNameListActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private RecyclerView recyclerView;

    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_name_list);
        toolbar = findViewById(R.id.static_nameList_toolBar);
        recyclerView = findViewById(R.id.static_nameList_recyclerView);
        initView();
        loadData();
    }

    private void loadData() {
        Intent intent = getIntent();
        String codes = intent.getStringExtra(Constant.STATIC_STU_CODE);
        MyTask myTask = new MyTask();
        myTask.execute(codes);
    }

    private class MyTask extends AsyncTask<String,Void,List<ItemStudentSample>> {

        @Override
        protected List<ItemStudentSample> doInBackground(String... strings) {
            List<StudentStatic> statics = LitePal
                    .where("code = ?",strings[0])
                    .find(StudentStatic.class);
            List<ItemStudentSample> samples = new ArrayList<>();
            if (statics.size() > 0) {
                for (StudentStatic s:statics) {
                    samples.add(new ItemStudentSample(s));
                }
            }
            return samples;
        }

        @Override
        protected void onPostExecute(List<ItemStudentSample> itemStudentSamples) {
            adapter.add(itemStudentSamples);
        }
    }

    private void initView() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new BaseAdapter.Builder().build();
        recyclerView.setLayoutManager(new GridLayoutManager(StaticNameListActivity.this,
                4, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
    }
}
