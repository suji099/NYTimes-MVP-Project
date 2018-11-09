package ny.sujith.com.nytimes.view.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import ny.sujith.com.nytimes.R;
import ny.sujith.com.nytimes.model.Result;
import ny.sujith.com.nytimes.utils.GlideApp;
import ny.sujith.com.nytimes.view.NYArticleDetailActivity;
import ny.sujith.com.nytimes.view.NYArticleDetailFragment;
import ny.sujith.com.nytimes.view.HomeActivity;

/**
 * Created by Sujith Chandranon 09-11-2018.
 */

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArtcleViewHolder> {

    private List<Result> resultList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ArtcleViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView txtHeader;
        private TextView date;
        private ImageView imgArticleIcon;
        private View layout;
        private TextView source;
        private TextView byLine;


        public ArtcleViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.title);
            imgArticleIcon = (ImageView) v.findViewById(R.id.img_article_icon);
            date = (TextView) v.findViewById(R.id.date);
            source = (TextView) v.findViewById(R.id.source);
            byLine = (TextView) v.findViewById(R.id.byLine);


        }
    }

    public FragmentManager fragmentManager;
    public HomeActivity mainActivity;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ArticleListAdapter(List<Result> resultList, HomeActivity mainActivity,
                              FragmentManager fragmentManager) {
        this.resultList = resultList;
        this.fragmentManager = fragmentManager;
        this.mainActivity = mainActivity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ArtcleViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.layout_fragment_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ArtcleViewHolder vh = new ArtcleViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ArtcleViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Context context = v.getContext();
                    Intent intent = new Intent(context, NYArticleDetailActivity.class);
                    intent.putExtra(NYArticleDetailFragment.ARG_ITEM_URL, resultList.get(position)
                            .getUrl());
                    intent.putExtra(NYArticleDetailFragment.ARG_ITEM_TITLE, resultList.get(position)
                            .getTitle());

                    context.startActivity(intent);


            }
        });

        final String name = resultList.get(position).getTitle();
        holder.txtHeader.setText(name);
        holder.date.setText(resultList.get(position).getPublishedDate());

        holder.source.setText(resultList.get(position).getSource());
        holder.byLine.setText(resultList.get(position).getByline());


        GlideApp.with(mainActivity).load(resultList.get(position).getMedia().get(0).getMediaMetadata
                ().get(0).getUrl()).centerCrop().listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable
                    > target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable>
                    target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).apply(RequestOptions.circleCropTransform()).into(holder
                .imgArticleIcon);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return resultList.size();
    }

}
