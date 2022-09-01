package com.tornado.a2048.Game2048;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tornado.a2048.R;

import java.util.List;

public class MenuImageAdapter extends RecyclerView.Adapter<MenuImageAdapter.ViewHolder> {


    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    List<Integer> id;
    List<String> text;

    // data is passed into the constructor
    MenuImageAdapter(Context context,List<Integer> id,List<String> text) {
        this.mInflater = LayoutInflater.from(context);
        this.id = id;
        this.text = text;

    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.myView.setBackgroundResource(id.get(position));
        holder.mytext.setText(text.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return 3;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView myView;
        TextView mytext;
        ViewHolder(View itemView) {
            super(itemView);
            myView = itemView.findViewById(R.id.imagelist);
            mytext = itemView.findViewById(R.id.textview);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return "";
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
