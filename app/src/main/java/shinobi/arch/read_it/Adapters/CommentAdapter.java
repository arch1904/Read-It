package shinobi.arch.read_it.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import shinobi.arch.read_it.Post.Comment;
import shinobi.arch.read_it.R;

/**
 * Adapter for Comments Recycler View
 * Created by architgupta on 5/2/17.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private ArrayList<Comment> commentArrayList;

    public CommentAdapter(ArrayList<Comment> allComments) {
        this.commentArrayList = allComments;
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
        final View commentListItem = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.comment_item, parent, false);
        return new ViewHolder(commentListItem);
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
        Comment comment = this.commentArrayList.get(position);
        holder.commentTextView.setText(comment.getComment());
    }

    /**
     * RecyclerView wants to know how many list items there are, so it knows when it gets to the
     * end of the list and should stop scrolling.
     *
     * @return the number of NewsArticles in the array.
     */
    @Override
    public int getItemCount() {
        if (commentArrayList == null) {
            return 0;
        }
        return commentArrayList.size();
    }

    /**
     * A ViewHolder class for our adapter that 'caches' the references to the
     * subviews, so we don't have to look them up each time.
     * Also onCLick of a subview it opens a new activity for a detail view
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView commentTextView;

        private ViewHolder(final View itemView) {
            super(itemView);
            commentTextView = (TextView) itemView.findViewById(R.id.commentTextView);
        }
    }
}