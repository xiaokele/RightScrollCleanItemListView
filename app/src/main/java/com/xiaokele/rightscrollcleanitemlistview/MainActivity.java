package com.xiaokele.rightscrollcleanitemlistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> contentList = new ArrayList<>();
    private RightScrollCleanSelectedItemListView listView;
    private ListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //准备数据
        initData();

        listView = (RightScrollCleanSelectedItemListView) findViewById(R.id.listView);
        listView.setOnDeleteClickListener(new RightScrollCleanSelectedItemListView.OnDeleteClickListener() {
            @Override
            public void onClickDelete(int index) {
                contentList.remove(index);
                listViewAdapter.notifyDataSetChanged();
            }
        });

        listViewAdapter = new ListViewAdapter(this, 0, contentList);
        listView.setAdapter(listViewAdapter);
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            contentList.add("xiaokele" + i);
        }
    }

    @Override
    public void onBackPressed() {
        if (listView.isDeleteShown()) {
            listView.hideDeleteBtn();
            return;
        }
        super.onBackPressed();
    }
}
