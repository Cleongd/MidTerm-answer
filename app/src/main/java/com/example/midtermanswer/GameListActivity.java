package com.example.midtermanswer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GameListActivity extends AppCompatActivity {
    private ListView listview;
    private ArrayList<GameModel> data = new ArrayList<>();
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        GameModel game1 = new GameModel();
        game1.setTitle("Half Life");
        game1.setRating("9/10");
        game1.setPrice("$20");

        data.add(game1);

        getDataFromAPI();
        findViews();
        setListeners();

    }

    private void findViews(){
        listview.findViewById(R.id.listview);

        GameAdapter adapter = new GameAdapter(this,data);
        listview.setAdapter(adapter);
    }

    private void setListeners(){
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GameModel selectedItem = (GameModel) listview.getAdapter().getItem(position);

                Intent i = new Intent(GameListActivity.this, GameListActivity.class);
                i.putExtra("title",selectedItem.getTitle());
                i.putExtra("rating",selectedItem.getRating());
                i.putExtra("price",selectedItem.getRating());
                i.putExtra("description",selectedItem.getRating());
                startActivity(i);
            }
        });
    }

    private void initialize(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViews();
                setListeners();
            }
        });
    }

    private void getDataFromAPI(){
        Request request = new Request.Builder().url("https://api.myjson.com/bins/11792a").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try{
                    JSONObject dataObject = new JSONObject(response.body().string());

                    JSONArray dataArray = new JSONArray("data");

                    for (int i = 0; i < dataArray.length(); i++){

                        JSONObject singleObject = dataArray.getJSONObject(i);

                        GameModel model = new GameModel();
                        model.setTitle(singleObject.getString("title"));
                        model.setRating(singleObject.getString("rating"));
                        model.setPrice(singleObject.getString("price"));
                        model.setDescription(singleObject.getString("description"));

                        data.add(model);

                    }
                    initialize();

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}


