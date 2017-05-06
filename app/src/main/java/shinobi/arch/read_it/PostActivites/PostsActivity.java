package shinobi.arch.read_it.PostActivites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import shinobi.arch.read_it.Adapters.PostAdapter;
import shinobi.arch.read_it.BaseActivity;
import shinobi.arch.read_it.R;
import shinobi.arch.read_it.ResourceClasses.Helper;

public class PostsActivity extends BaseActivity {

    private String userName;
    private PostAdapter mPostAdapter;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        /**
         * Custom Value Event Listener for Post Methods
         */
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allPosts = dataSnapshot.child(Helper.POSTS).getValue(Helper.POST_LIST_TYPE);
                users = dataSnapshot.child(Helper.USERS).getValue(Helper.USERNAME_USER_MAP);
                authenticate = dataSnapshot.child(Helper.AUTHENTICATION).getValue(Helper.USER_PASS_MAP);
                mPostAdapter = new PostAdapter(allPosts);
                mRecyclerView.setAdapter(mPostAdapter);

                if (allPosts == null) {
                    allPosts = new ArrayList<>();
                }
                if (users == null) {
                    users = new HashMap<>();
                }
                if (authenticate == null) {
                    authenticate = new HashMap<>();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Inflate Recycler View
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        userName = getIntent().getStringExtra(Helper.CURRENT_USER);

        ImageButton floatingActionButton = (ImageButton) findViewById(R.id.imageButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newPostIntent = new Intent(v.getContext(), NewPostActivity.class);
                newPostIntent.putExtra(Helper.CURRENT_USER, userName);
                ref.removeEventListener(valueEventListener);
                v.getContext().startActivity(newPostIntent);
            }
        });


    }
}