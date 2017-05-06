package shinobi.arch.read_it;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import shinobi.arch.read_it.Post.Post;
import shinobi.arch.read_it.ResourceClasses.Helper;
import shinobi.arch.read_it.User.Password;
import shinobi.arch.read_it.User.User;

public abstract class BaseActivity extends AppCompatActivity {
    protected DatabaseReference ref;
    protected ArrayList<Post> allPosts;
    protected Map<String, User> users;
    protected Map<String, Password> authenticate;
    protected ValueEventListener valueEventListener;
    protected CountDownLatch countDownLatch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        countDownLatch = new CountDownLatch(1);
        /**
         * Base Value Event Listener
         */
        valueEventListener = new ValueEventListener() {
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        if (allPosts == null) {
            allPosts = new ArrayList<>();
        }
        if (users == null) {
            users = new HashMap<>();
        }
        if (authenticate == null) {
            authenticate = new HashMap<>();
        }
        countDownLatch.countDown();
    }
}