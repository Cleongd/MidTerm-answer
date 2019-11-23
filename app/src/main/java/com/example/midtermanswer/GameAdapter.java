package com.example.midtermanswer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameAdapter extends ArrayAdapter {

    private ArrayList<GameModel> data;
    private Context mContext;

    public GameAdapter(Context context, ArrayList<GameModel> data){
        super(context, R.layout.single_game_list_item);
        this.data = data;
        this.mContext = context;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v;
        Viewholder holder;
        if(convertView == null){
            holder = new Viewholder();
            v = LayoutInflater.from(mContext).inflate(R.layout.single_game_list_item, null);

            holder.tvTitle = v.findViewById(R.id.tvGame);
            holder.tvRating = v.findViewById(R.id.tvRating);
            holder.tvPrice = v.findViewById(R.id.tvPrice);

            v.setTag(holder);
        } else {
            holder = (Viewholder) convertView.getTag();
            v = convertView;
        }

        GameModel model = (GameModel) getItem(position);

        holder.tvTitle.setText(model.getTitle());
        holder.tvRating.setText(model.getRating());
        holder.tvPrice.setText(model.getPrice());

        return v;
    }

    class Viewholder{
        TextView tvTitle, tvRating, tvPrice;
    }
}
