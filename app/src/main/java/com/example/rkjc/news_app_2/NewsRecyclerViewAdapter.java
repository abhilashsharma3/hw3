package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NewsRecyclerViewAdapter  extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsItemViewHolder> {

    Context newsContext;
    ArrayList<NewsItem> newsItems;

    public NewsRecyclerViewAdapter(Context context,ArrayList<NewsItem> newsItem){
       this.newsContext=context;
        this.newsItems=newsItem;
    }

    @NonNull
    @Override
    public NewsRecyclerViewAdapter.NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        boolean atp=false;
        View view=layoutInflater.inflate(R.layout.news_item,parent,atp);
        NewsItemViewHolder newsItemViewHolder=new NewsItemViewHolder(view);
        return newsItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerViewAdapter.NewsItemViewHolder holder, final int position) {
       final NewsItem fresh=newsItems.get(position);
        holder.Title.setText(fresh.getTitle());
        holder.desc.setText(fresh.getDescription());
        holder.date.setText(fresh.getDate());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url=""+fresh.getUrlimg();
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                newsContext.startActivity(i);
            }
        });
        Picasso.with(newsContext).load(fresh.getUrlimg())
                .resize(360,210)
                .into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    public class NewsItemViewHolder extends RecyclerView.ViewHolder {
        TextView Title;
        TextView desc;
        TextView date;
        TextView url;
        RelativeLayout relativeLayout;
        ImageView imageView;

        public NewsItemViewHolder(View itemView){
            super(itemView);
            Title= (TextView) itemView.findViewById(R.id.Title);
            desc=(TextView) itemView.findViewById(R.id.desc);
            date= (TextView) itemView.findViewById(R.id.date);
            relativeLayout=itemView.findViewById(R.id.relativelayout);
            imageView=itemView.findViewById(R.id.image);
        }
        public void bind(final int index){
            Title.setText(newsItems.get(index).getTitle());
            desc.setText(newsItems.get(index).getDescription());
            date.setText(newsItems.get(index).getDate());
        }
    }

    public void setNews(ArrayList<NewsItem> newsItems){
        this.newsItems=newsItems;
       notifyDataSetChanged();

    }

}
