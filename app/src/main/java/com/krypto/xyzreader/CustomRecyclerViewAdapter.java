package com.krypto.xyzreader;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Displays articles with the help of recyclerview
 */
public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomRecyclerViewViewHolder> {

    Context mContext;
    List<Pojo> mList = new ArrayList<>();
    private static final String TITLE="title";

    public CustomRecyclerViewAdapter(Context context) {

        mContext = context;
    }


    @Override
    public CustomRecyclerViewViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_list_items, viewGroup, false);
        CustomRecyclerViewViewHolder holder = new CustomRecyclerViewViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CustomRecyclerViewViewHolder customRecyclerViewViewHolder, final int i) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            customRecyclerViewViewHolder.card.setForeground(mContext.getResources().getDrawable(R.drawable.ripple, null));

        String url = mList.get(i).getPhoto();
        if (url != null)
            Glide.with(mContext).load(url).into(customRecyclerViewViewHolder.image);

        String title = mList.get(i).getTitle();
        customRecyclerViewViewHolder.title.setText(title);
        customRecyclerViewViewHolder.title.setTypeface(Utility.getFont(mContext, mContext.getString(R.string.roboto_regular)));

        String subTitle = mList.get(i).getAuthor();
        customRecyclerViewViewHolder.subTitle.setText("Written By " + subTitle);
        customRecyclerViewViewHolder.subTitle.setTypeface(Utility.getFont(mContext, mContext.getString(R.string.roboto_regular)));

        customRecyclerViewViewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent(mContext, DetailActivity.class);
                activityIntent.putExtra(TITLE, mList.get(i));
                mContext.startActivity(activityIntent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return mList != null ? mList.size() : 0;
    }

    public void setData(List<Pojo> list) {
        this.mList = list;
        Handler handler = new Handler(mContext.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });

    }

    public List<Pojo> getData() {
        return mList;
    }

    static class CustomRecyclerViewViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.article_title)
        TextView title;
        @Bind(R.id.article_subtitle)
        TextView subTitle;
        @Bind(R.id.thumbnail)
        ImageView image;
        @Bind(R.id.card)
        CardView card;

        public CustomRecyclerViewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
