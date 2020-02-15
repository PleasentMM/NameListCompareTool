package com.mcc.compare.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.library.BaseAdapter;
import com.mcc.compare.R;
import com.mcc.compare.activity.base.BaseAppCompatActivity;
import com.mcc.compare.bean.RecordMarks;
import com.mcc.compare.bean.StudentRecord;
import com.mcc.compare.constant.Constant;
import com.mcc.compare.itemViewModel.ItemStudentSampleCompare;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CompareRecordDetailActivity extends BaseAppCompatActivity {

    private Toolbar toolbar;

    private BaseAdapter adapter;

    private RecyclerView recyclerView;

    private StudentRecord studentRecord;

    private RecordMarks recordMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list_show);
        toolbar = findViewById(R.id.record_show_toolbar);
        recyclerView = findViewById(R.id.record_show_recyclerView);
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
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),
                4, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

    }

    //展示详细的记录信息
    private void loadData() {
        Intent intent = getIntent();
        if (intent.getSerializableExtra(Constant.STU_RECORD)!=null) {
            recordMarks = (RecordMarks) intent.getSerializableExtra(Constant.STU_RECORD);
        }

        SimpleDateFormat format = new SimpleDateFormat(" MM月dd日 HH:mm");
        String infoTitle =recordMarks.getMarks()+" "+format.format(recordMarks.getDate());

        toolbar.setTitle(infoTitle);

        MyAsyncTask task = new MyAsyncTask();

        task.execute(recordMarks.getCode());

    }

    private class MyAsyncTask extends AsyncTask<String,Integer,List<ItemStudentSampleCompare>> {

        @Override
        protected List<ItemStudentSampleCompare> doInBackground(String... strings) {
            List<StudentRecord> records = LitePal
                    .where("code = ? ",strings[0])
                    .find(StudentRecord.class);

            List<ItemStudentSampleCompare> compareList = new ArrayList<>();

            for (StudentRecord record:records) {
                compareList.add(new ItemStudentSampleCompare(record));
            }
            return compareList;
        }

        @Override
        protected void onPostExecute(List<ItemStudentSampleCompare> itemStudentSampleCompares) {
            adapter.add(itemStudentSampleCompares);
        }
    }
}
