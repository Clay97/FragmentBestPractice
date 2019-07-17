package com.example.fragmentbestpractice.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragmentbestpractice.News;
import com.example.fragmentbestpractice.NewsContentActivity;
import com.example.fragmentbestpractice.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsTitleFragment extends Fragment {
    private boolean isTowPane;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.news_title_frag,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.news_title_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        Adapter newsAdapter = new Adapter(getNews());
        recyclerView.setAdapter(newsAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_layout)!=null){
            isTowPane = true;
        }else {
            isTowPane = false;
        }
    }

    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
        private List<News> mNewsList;

        public Adapter(List<News> news) {
            mNewsList = news;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
            final Adapter.ViewHolder viewHolder = new Adapter.ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    News news = mNewsList.get(viewHolder.getAdapterPosition());
                    if (isTowPane){
                        NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_content_fragment);
                        newsContentFragment.refresh(news.getTitle(),news.getContent());
                    }else {
                        NewsContentActivity.actionStart(getActivity(),news.getTitle(),news.getContent());
                    }
                }
            });
            return viewHolder;

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            News news = mNewsList.get(position);
            holder.newsTitleText.setText(news.getTitle());
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView newsTitleText;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                newsTitleText = itemView.findViewById(R.id.news_title);
            }
        }
    }



    private List<News> getNews(){
        List<News> newsList = new ArrayList<>();
        for (int i = 1; i <= 50; i++){
            News news = new News();
            news.setTitle("This is new Title"+ i);
            news.setContent(getRandomLengthContent("This is new Title"+ i+"."));
            newsList.add(news);
        }

        return newsList;
    }

    private String getRandomLengthContent(String content){
        Random random = new Random();
        int lengrh = random.nextInt(20)+1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lengrh; i++){
            builder.append(content);
        }
        return builder.toString();
    }
}
