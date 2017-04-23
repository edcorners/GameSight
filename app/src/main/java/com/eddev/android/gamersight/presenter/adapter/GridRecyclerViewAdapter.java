package com.eddev.android.gamersight.presenter.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eddev.android.gamersight.GiantBombUtility;
import com.eddev.android.gamersight.R;
import com.eddev.android.gamersight.model.Game;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

/**
 * Created by Edison on 10/10/2016.
 */

public class GridRecyclerViewAdapter extends RecyclerView.Adapter<GridRecyclerViewAdapter.ViewHolder>{
    private List<Game> dataset;
    private Context mContext;

    public interface OnItemClickListener {
        void onItemClick(Game game);
    }

    private final OnItemClickListener mListener;

    public GridRecyclerViewAdapter(List<Game> gameList, Context context, OnItemClickListener listener) {
        dataset = gameList;
        mContext = context;
        mListener = listener;
    }

    public void setDataset(List<Game> dataset) {
        this.dataset = dataset;
    }

    public List<Game> getDataset() {
        return dataset;
    }

    @Override
    public GridRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grid_image, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(dataset.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return dataset != null ? dataset.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView mGameCoverImageView;
        public TextView mTextScrim;
        public ViewHolder(View v) {
            super(v);
            mGameCoverImageView = (ImageView) v.findViewById(R.id.grid_item_image);
            mTextScrim = (TextView) v.findViewById(R.id.grid_item_scrim_text);
        }

        public void bind(final Game game, final OnItemClickListener listener) {
            Picasso.with(itemView.getContext())
                    .load(game.getThumbnailUrl())
                    .placeholder(R.color.mainBackground)
                    .error(R.color.mainBackground)
                    .into(mGameCoverImageView,new Callback() {
                        @Override public void onSuccess() {
                            Bitmap bitmap = ((BitmapDrawable) mGameCoverImageView.getDrawable()).getBitmap();
                            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                                public void onGenerated(Palette palette) {
                                    applyPalette(palette);
                                }
                            });
                            mGameCoverImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        }

                        @Override public void onError() { }
                    });

            String contentDescription = itemView.getContext().getString(R.string.game_thumb_content_description);
            String collectionName = itemView.getContext().getString(GiantBombUtility.getTitle(game.getCollection()));
            mGameCoverImageView.setContentDescription(String.format(contentDescription, game.getName(), collectionName));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(game);
                }
            });

            if(game.isScrimVisible()) {
                mTextScrim.setText(game.getScrimText());
                mTextScrim.setVisibility(View.VISIBLE);
                setScrimContentDescription(game, contentDescription, collectionName);
            }else{
                mTextScrim.setVisibility(View.GONE);
            }
        }

        private void setScrimContentDescription(Game game, String contentDescription, String collectionName) {
            if(game.isInCollecion(Game.TRACKING)){
                Date expectedReleaseDate = game.getExpectedReleaseDate();
                String completionText = game != null ? String.format(itemView.getContext().getString(R.string.coming_out)," " + GiantBombUtility.shortDateFormat.format(expectedReleaseDate)):
                        itemView.getContext().getString(R.string.release_date_unknown);
                mTextScrim.setContentDescription(completionText);
            }else if (game.isInCollecion(Game.OWNED)){
                String completionText = String.format(itemView.getContext().getString(R.string.completion_units_legend), (int)game.getCompletion());
                mTextScrim.setContentDescription(completionText);
            }
        }

        private void applyPalette(Palette palette) {
            int primary = ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary);
            mGameCoverImageView.setBackgroundColor(palette.getDominantColor(primary));
        }
    }


}
