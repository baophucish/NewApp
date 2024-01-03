package com.nguyenhoangbaophuc.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;


import com.nguyenhoangbaophuc.newsapp.Adapter.CategoryRVAdapter;
import com.nguyenhoangbaophuc.newsapp.Adapter.NewsRVAdapter;
import com.nguyenhoangbaophuc.newsapp.Model.CategoryRVModel;
import com.nguyenhoangbaophuc.newsapp.Model.Item;
import com.nguyenhoangbaophuc.newsapp.RSS.MySaxParser;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {
    private RecyclerView newsRV, categoryRV;
    private ArrayList<CategoryRVModel> categoryRVModelArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private ProgressBar loadingPB;
    private NewsRVAdapter newsRVAdapter;
    private ArrayList<Item> list;
    String TAG="tin-moi-nhat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getValue();
        categoryRV.setAdapter(categoryRVAdapter);
        getCategories();
//        getNews(TAG);
        new MyAsyncTask(this, "https://vnexpress.net/rss/tin-moi-nhat.rss", newsRV).execute();
        newsRVAdapter.notifyDataSetChanged();
    }

    private void getValue() {
        categoryRV = findViewById(R.id.rvCategories);
        categoryRVModelArrayList = new ArrayList<>();
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModelArrayList, this, this);

        newsRV = findViewById(R.id.rvNews);
        loadingPB = findViewById(R.id.pbLoading);
        list = new ArrayList<>();
        newsRVAdapter = new NewsRVAdapter(list, this);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
    }

    private void getCategories() {
        categoryRVModelArrayList.add(new CategoryRVModel("thoi-su", "thoisu","Thời Sự"));
        categoryRVModelArrayList.add(new CategoryRVModel("kinh-doanh", "kinhte","Kinh Doanh"));
        categoryRVModelArrayList.add(new CategoryRVModel("phap-luat", "phapluat","Pháp Luật"));
        categoryRVModelArrayList.add(new CategoryRVModel("the-thao", "thethao", "Thể Thao"));
        categoryRVModelArrayList.add(new CategoryRVModel("the-gioi", "thegioi","Thế Giới"));
        categoryRVModelArrayList.add(new CategoryRVModel("giai-tri", "giaitri","Giải Trí"));
        categoryRVModelArrayList.add(new CategoryRVModel("giao-duc", "giaoduc","Giáo Dục"));
        categoryRVModelArrayList.add(new CategoryRVModel("cong-nghe", "technology","Công Nghệ"));
        categoryRVModelArrayList.add(new CategoryRVModel("suc-khoe", "bsi","Sức Khỏe"));
        categoryRVAdapter.notifyDataSetChanged();
    }

    private void getNews(String category) {
        loadingPB.setVisibility(View.VISIBLE);
        list.clear();

        String RssCategoryURL = "https://vnexpress.net/rss/" + category + ".rss";
        new MyAsyncTask(this, RssCategoryURL, newsRV).execute();

        Log.d("getNews", "Fetching news for category: " + category);
        Log.d("getNews", "Number of items in the list: " + list.size());
    }

    public class MyAsyncTask extends AsyncTask<Void, Void, List<Item>> {
        private Context context;
        private String link;
        private RecyclerView recyclerView;
        private NewsRVAdapter adapter;

        public MyAsyncTask(Context context, String link, RecyclerView recyclerView) {
            this.context = context;
            this.link = link;
            this.recyclerView = recyclerView;
        }

        @Override
        protected List<Item> doInBackground(Void... voids) {
            try {
                URL url = new URL(link);
                URLConnection connection = url.openConnection();

                InputStream is = connection.getInputStream();
                return MySaxParser.xmlParser(is);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Item> items) {
            super.onPostExecute(items);
            if (items != null) {
                list.addAll(items);
                if(newsRVAdapter != null){
                    newsRVAdapter.notifyDataSetChanged();
                }
                Log.d("MyAsyncTask", "Received " + items.size() + " news items.");
            }else {
                Log.e("MyAsyncTask", "Failed to get news items.");
            }
            loadingPB.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModelArrayList.get(position).getCategory();
        getNews(category);
    }
}
