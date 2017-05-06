package shinobi.arch.read_it.Adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import shinobi.arch.read_it.PostActivites.DetailActivity;
import shinobi.arch.read_it.Post.Post;
import shinobi.arch.read_it.R;
import shinobi.arch.read_it.ResourceClasses.Helper;

import static shinobi.arch.read_it.Post.Post.decodeFromFirebaseBase64;

/**
 * Post Adapter for RecyclerView
 * Created by architgupta on 4/24/17.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private ArrayList<Post> postArrayList;
    private static ArrayList<Post> searchablePosts;


    public PostAdapter(ArrayList<Post> postArrayList) {
        this.postArrayList = postArrayList;
        searchablePosts = postArrayList;
    }

    /**
     * This function is called only enough times to cover the screen with views.  After
     * that point, it recycles the views when scrolling is done.
     *
     * @param parent   the intended parent object (our RecyclerView)
     * @param viewType unused in our function (enables having different kinds of views in the same RecyclerView)
     * @return the new ViewHolder we allocate
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // a LayoutInflater turns a layout XML resource into a View object.
        final View movieListItem = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.post_item, parent, false);
        return new ViewHolder(movieListItem);
    }

    /**
     * This function gets called each time a ViewHolder needs to hold data for a different
     * position in the list.  We don't need to create any views (because we're recycling), but
     * we do need to update the contents in the views.
     *
     * @param holder   the ViewHolder that knows about the Views we need to update
     * @param position the index into the array of NewsArticles
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = this.postArrayList.get(position);
        holder.titleTextView.setText(post.getTitle());
        holder.descriptionTextView.setText(post.getContent());
        if (post.getImgUrl() != null) {

            try {
                Bitmap imageBitmap = decodeFromFirebaseBase64(post.getImgUrl());
                holder.imageView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                Log.d("IMAGE", "FAILED");
                e.printStackTrace();
            }
        }

    }

    /**
     * RecyclerView wants to know how many list items there are, so it knows when it gets to the
     * end of the list and should stop scrolling.
     *
     * @return the number of NewsArticles in the array.
     */
    @Override
    public int getItemCount() {
        if (postArrayList == null) {
            return 0;
        }
        return postArrayList.size();
    }

    /**
     * A ViewHolder class for our adapter that 'caches' the references to the
     * subviews, so we don't have to look them up each time.
     * Also onCLick of a subview it opens a new activity for a detail view
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private ImageView imageView;
        private TextView descriptionTextView;

        private ViewHolder(final View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            imageView = (ImageView) itemView.findViewById(R.id.listImageView);
            descriptionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Post currPost = searchPostByTitle(titleTextView.getText().toString());
                    Gson gson = new Gson();
                    if (currPost != null) {
                        String postObject = gson.toJson(currPost);
                        Intent detail = new Intent(v.getContext(), DetailActivity.class);
                        detail.putExtra(Helper.POST, postObject);
                        v.getContext().startActivity(detail);
                    }
                }
                //}
            });
        }

        private Post searchPostByTitle(String title) {
            for (Post currPost : searchablePosts) {
                if (title.equals(currPost.getTitle())) {
                    return currPost;
                }
            }
            return null;
        }
    }
}