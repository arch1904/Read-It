package shinobi.arch.read_it.PostActivites;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import shinobi.arch.read_it.BaseActivity;
import shinobi.arch.read_it.Post.Comment;
import shinobi.arch.read_it.Post.Post;
import shinobi.arch.read_it.Adapters.CommentAdapter;
import shinobi.arch.read_it.R;
import shinobi.arch.read_it.ResourceClasses.Helper;
import shinobi.arch.read_it.User.User;

import static shinobi.arch.read_it.Post.Post.decodeFromFirebaseBase64;

public class DetailActivity extends BaseActivity {
    private CommentAdapter mCommentAdapter;
    private TextView titleTextView;
    private TextView authorTextView;
    private ImageView imageView;
    private TextView descriptionTextView;
    private Button upVoteButton;
    private Button downVoteButton;
    private RecyclerView mRecyclerView;
    private Button commentButton;
    private EditText commentText;
    private TextView scoreView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        String json = getIntent().getStringExtra(Helper.POST);
        String userJson = getIntent().getStringExtra(Helper.CURRENT_USER);
        Gson gson = new Gson();

        final Post post = gson.fromJson(json, Post.class);
        final User currentUser = gson.fromJson(userJson, User.class);

        /**
         * Custom Value Event Listener for Detail Activity
         */
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allPosts = dataSnapshot.child(Helper.POSTS).getValue(Helper.POST_LIST_TYPE);
                users = dataSnapshot.child(Helper.USERS).getValue(Helper.USERNAME_USER_MAP);
                authenticate = dataSnapshot.child(Helper.AUTHENTICATION).getValue(Helper.USER_PASS_MAP);
                if (allPosts == null) {
                    allPosts = new ArrayList<>();
                }
                if (users == null) {
                    users = new HashMap<>();
                }
                if (authenticate == null) {
                    authenticate = new HashMap<>();
                }
                //Inflate View with Required Components
                mCommentAdapter = new CommentAdapter(post.getComments());
                mRecyclerView.setAdapter(mCommentAdapter);

                if (post != null) {
                    titleTextView.setText(post.getTitle());
                    descriptionTextView.setText(post.getContent());
                    authorTextView.setText(post.getAuthor().getUserName());
                    if (post.getImgUrl() != null) {

                        try {
                            Bitmap imageBitmap = decodeFromFirebaseBase64(post.getImgUrl());
                            imageView.setImageBitmap(imageBitmap);
                        } catch (IOException e) {
                            Log.d(Helper.IMAGE, Helper.FAILED);
                            e.printStackTrace();
                        }
                    }
                    int score = post.getUpVotes() - post.getDownVotes();
                    String karma = score + "";
                    scoreView.setText(karma);
                }

                /**
                 * On Click Listener for Delete Button
                 * Deletes Current Post and redirects to Posts Page
                 */
                upVoteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (upVoteButton.getText().toString().equalsIgnoreCase(Helper.UP_VOTE)) {
                            upVoteButton.setText(Helper.UP_VOTED);
                            post.upVote();
                        } else {
                            upVoteButton.setText(Helper.UP_VOTE);
                            post.removeUpVote();
                        }
                        if (downVoteButton.getText().toString().equalsIgnoreCase(Helper.DOWN_VOTED)) {
                            downVoteButton.setText(Helper.DOWN_VOTE);
                            post.removeDownVote();
                        }

                        for (Post currPost : allPosts) {
                            if (currPost.getTitle().equals(post.getTitle())) {
                                currPost.setVotes(post);
                            }
                        }
                        ref.child(Helper.POSTS).setValue(allPosts);

                    }
                });

                /**
                 * On Click Listener for DownVoteButton
                 */
                downVoteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (downVoteButton.getText().toString().equalsIgnoreCase(Helper.DOWN_VOTE)) {
                            downVoteButton.setText(Helper.DOWN_VOTED);
                            post.downVote();
                        } else {
                            downVoteButton.setText(Helper.DOWN_VOTE);
                            post.removeDownVote();
                        }
                        if (upVoteButton.getText().toString().equalsIgnoreCase(Helper.UP_VOTED)) {
                            upVoteButton.setText(Helper.UP_VOTE);
                            post.removeUpVote();
                        }
                        for (Post currPost : allPosts) {
                            if (currPost.getTitle().equals(post.getTitle())) {
                                currPost.setVotes(post);
                            }
                        }
                        ref.child(Helper.POSTS).setValue(allPosts);
                    }
                });

                /**
                 * On Click Listener for Comment Button
                 */
                commentButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (commentText.getText().toString().length() <= 0) {
                            Toast.makeText(v.getContext(), Helper.EMPTY_COMMENT, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Comment newComment = new Comment(currentUser, commentText.getText().toString());
                        ArrayList<Comment> comments = post.getComments();
                        if (comments == null) {
                            comments = new ArrayList<>();
                        }
                        commentText.setText("");
                        comments.add(newComment);
                        post.setComments(comments);
                        for (Post currPost : allPosts) {
                            if (currPost.getTitle().equals(post.getTitle())) {
                                currPost.setComments(post.getComments());
                            }
                        }
                        ref.child(Helper.POSTS).setValue(allPosts);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Initialize All View Attributes
     */

    public void init() {
        titleTextView = (TextView) findViewById(R.id.titleDetailTextView);
        authorTextView = (TextView) findViewById(R.id.postAuthor);
        imageView = (ImageView) findViewById(R.id.imageDetailView);
        descriptionTextView = (TextView) findViewById(R.id.contentTextView);
        upVoteButton = (Button) findViewById(R.id.upVoteButton);
        downVoteButton = (Button) findViewById(R.id.downVoteButton);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        commentButton = (Button) findViewById(R.id.commentButton);
        commentText = (EditText) findViewById(R.id.commentDetailText);
        scoreView = (TextView) findViewById(R.id.scoreTextView);
    }

    /**
     * Runs When The Activity is Destroyed
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}