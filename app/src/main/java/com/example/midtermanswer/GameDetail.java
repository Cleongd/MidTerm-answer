package com.example.midtermanswer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class GameDetail extends AppCompatActivity {
    private TextView tvTitle, tvRating, tvPrice, tvDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        findViews();
    }

    private void findViews(){
        tvTitle = findViewById(R.id.tvTitle);
        tvRating = findViewById(R.id.tvRating);
        tvPrice = findViewById(R.id.tvPrice);
        tvDescription = findViewById(R.id.tvDescription);

    }

    private void initializeData(){
        Bundle bundle = getIntent().getExtras();

        tvTitle.setText(bundle.getString("title"));
        tvRating.setText(bundle.getString("rating"));
        tvPrice.setText(bundle.getString("price"));
        tvDescription.setText(bundle.getString("description"));


    }
}
