package shinobi.arch.read_it.Post;

import junit.framework.TestCase;

import org.junit.Test;

import java.io.IOException;

import shinobi.arch.read_it.User.User;

import static org.junit.Assert.*;

/**
 * Test for Post Class
 * Created by architgupta on 5/6/17.
 */
public class PostTest extends TestCase {
    private static final String RANDOM = "RANDOM";
    private static final String IMAGE_PATH = "imagePath";
    private static final String RANDOM_CONTENT = "Random Content";
    private static final User AUTHOR = new User();
    private Post post;

    public void setUp() {
        post = new Post(RANDOM, AUTHOR, IMAGE_PATH, RANDOM_CONTENT);
    }

    /**
     * Test SetVotes
     * Pass in a post with different upvote downvote count to update upVote DownVote count for
     * current post
     */
    public void testSetVotes() {
        Post testPost = new Post(RANDOM, AUTHOR, IMAGE_PATH, RANDOM_CONTENT);
        testPost.upVote();
        testPost.upVote();
        testPost.downVote();

        post.setVotes(testPost);
        assertEquals(post.getUpVotes(), testPost.getUpVotes());
        assertEquals(post.getDownVotes(), testPost.getDownVotes());
    }

    /**
     * Test Removal of Upvote
     */
    public void testRemoveUpVote() {
        post.upVote();
        int expected = post.getUpVotes() - 1;
        post.removeUpVote();
        assertEquals(post.getUpVotes(), expected);
    }

    /**
     * Test Removal of Downvote
     */
    public void testRemoveDownVote() {
        post.downVote();
        int expected = post.getDownVotes() - 1;
        post.removeDownVote();
        assertEquals(post.getDownVotes(), expected);
    }

    /**
     * Test if the String to BitMap Decoder handles edge cases efficiently
     */
    public void testDecode() throws IOException {
        assertNull(Post.decodeFromFirebaseBase64(null));
    }

}