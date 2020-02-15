package com.mcc.compare.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.library.BaseAdapter;
import com.mcc.compare.R;
import com.mcc.compare.activity.base.BaseAppCompatActivity;
import com.mcc.compare.bean.RecordMarks;
import com.mcc.compare.itemViewModel.ItemStudentRecordListSample;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class CompareRecordActivity extends BaseAppCompatActivity {

    private Toolbar toolbar;

    private RecyclerView recyclerView;

    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_record);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.record_recyclerView);
        initView();
        loadData();
    }

    private void initView() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new BaseAdapter.Builder().build();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

    }

    //从数据库获取记录信息
    private void loadData() {
        List<RecordMarks> recordMarks = LitePal.findAll(RecordMarks.class);

        List<ItemStudentRecordListSample> recordListSamples = new ArrayList<>();
        if (recordMarks.size() > 0) {
            for (int i = 0; i < recordMarks.size(); i++) {
                recordListSamples.add(
                        new ItemStudentRecordListSample(recordMarks.get(i)));
            }
        }

        adapter.add(recordListSamples);
    }
}
