package io.writter.ninaadpai.writter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import static io.writter.ninaadpai.writter.SetUpNameActivity.topicEditText;

/**
 * Created by ninaadpai on 3/24/17.
 */

class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.RadioViewHolder> {
        private Context context;
        private List<String> favoriteList;
        private Typeface domineBold;

    public FavoriteAdapter(Context context, List<String> favoriteList, Typeface domineBold) {
            this.context = context;
            this.favoriteList = favoriteList;
            this.domineBold = domineBold;
        }

        @Override
        public FavoriteAdapter.RadioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflatedView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.setup_recycler_row, null);
            FavoriteAdapter.RadioViewHolder appViewHolder = new FavoriteAdapter.RadioViewHolder(inflatedView);
            return  appViewHolder;
        }

        @Override
        public void onBindViewHolder(final FavoriteAdapter.RadioViewHolder holder, int position) {

            final String f = favoriteList.get(position);
            holder.textView.setText(f);
            holder.textView.setTypeface(domineBold);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemRemoved(favoriteList.indexOf(f));
                    holder.textView.setVisibility(View.GONE);
                    holder.imageView.setVisibility(View.GONE);
                    favoriteList.remove(f);
                    if(favoriteList.size() == 0) {
                        SetUpNameActivity.goBtn.setText("SKIP FOR NOW");
                        SetUpNameActivity.recyclerTitle.setText("Nothing Selected");
                    }
                    else if(favoriteList.size() > 0) {
                        SetUpNameActivity.goBtn.setText("PROCEED");
                        SetUpNameActivity.recyclerTitle.setText("My Favorites");
                    }

                    if(favoriteList.size() >= 5){
                        topicEditText.setText("You can choose more topics or proceed ahead.");
                    }
                    else if(favoriteList.size() <5){
                        topicEditText.setText("Add at least "+(5-favoriteList.size())+" topics to personalize your home.");
                    }

                }
            });
           }

        @Override
        public int getItemCount() {
            return (null != favoriteList ? favoriteList.size() : 0);
        }

        class RadioViewHolder extends RecyclerView.ViewHolder {
            protected ImageView imageView;
            protected TextView textView;

            public RadioViewHolder(View view) {
                super(view);
                this.imageView = (ImageView) view.findViewById(R.id.delete);
                this.textView = (TextView) view.findViewById(R.id.favorite);
            }
        }
    }

