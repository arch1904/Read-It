package shinobi.arch.read_it;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import junit.framework.TestCase;


import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import shinobi.arch.read_it.Post.Post;
import shinobi.arch.read_it.User.User;

/**
 * Test Read and Write to Firebase
 * Created by architgupta on 4/18/17.
 */

public class DBTest extends TestCase {
    public static final String ARCHIT = "ARCHIT";
    public static final String AUTHOR = "author";
    private static User change;
    private User changedValue;
    private DatabaseReference ref;


    /**
     * Set Up the Database Access
     */
    public void setUp() {
        change = new User("DONALD","donnydumbass@tt.org");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("posts");
    }

    /**
     * Test Read and Write to Firebase Real Time Database by creating an interim list and
     * changing some values in it to see if the change is reflected
     */
    public void testReadWriteToDB() throws InterruptedException {

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Post post = new Post();

        ref.child(ARCHIT).setValue(post)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        countDownLatch.countDown();
                    }
                });
        ValueEventListener valueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Post currentPost = dataSnapshot.getValue(Post.class);
                changedValue = currentPost.getAuthor();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.child(ARCHIT).addValueEventListener(valueListener);
        ref.child(ARCHIT).child(AUTHOR).setValue(change);
        countDownLatch.await(10, TimeUnit.SECONDS);
        assertEquals(change, changedValue);
    }

    public void testChangeAgain() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        ValueEventListener valueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Post currentPost = dataSnapshot.getValue(Post.class);
                changedValue = currentPost.getAuthor();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        change = new User("barack", "bbbbb@wh.org");
        ref.child(ARCHIT).addValueEventListener(valueListener);
        ref.child(ARCHIT).child(AUTHOR).setValue(change);
        countDownLatch.await(10, TimeUnit.SECONDS);
        assertEquals(change, changedValue);


    }

}