package shinobi.arch.read_it.Post;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.IOException;
import java.util.ArrayList;

import shinobi.arch.read_it.User.User;

/**
 * Post Class
 * Representation of a Post
 * Created by architgupta on 4/18/17.
 */

public class Post {
    private String title;
    private User author;
    private String content;
    private ArrayList<Comment> comments;
    private String imgUrl;
    private int upVotes;
    private int downVotes;

    /**
     * Default Constructor
     */
    public Post() {
    }

    /**
     * Parameterized Constructor
     *
     * @param title   String, Title of the Movie
     * @param author  User, Author of the Post
     * @param imgPath String, Image Path
     */
    public Post(String title, User author, String imgPath, String content) {
        this.title = title;
        this.author = author;
        this.upVotes = 0;
        this.downVotes = 0;
        if (imgPath == null) {
            this.imgUrl = "";
        } else {
            this.imgUrl = imgPath;
        }
        this.comments = new ArrayList<>();
        this.content = content;
    }

    /**
     * Update Upvote Count for Post and User
     */
    public void upVote() {
        upVotes++;
        author.upVote();
    }

    /**
     * Update Downvote count for Post and User
     */
    public void downVote() {
        downVotes++;
        author.downVote();
    }

    /**
     * Set the upVote,DownVote values to the changed values
     *
     * @param post Post, post object with updated upVoteDownVote count
     */
    public void setVotes(Post post) {
        this.downVotes = post.downVotes;
        this.upVotes = post.upVotes;
    }

    /**
     * Remove a previous up-vote
     */
    public void removeUpVote() {
        if (upVotes > 0) {
            upVotes--;
            author.removeUpVote();
        }
    }

    /**
     * Remove a previous down-vote
     */
    public void removeDownVote() {
        if (downVotes > 0) {
            downVotes--;
            author.removeDownVote();
        }
    }

    /**
     * Decode Byte Array from Firebase and convert to Bitmap
     *
     * @param image String, encoded image
     * @return Bitmap, Image
     */
    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        if (image == null) {
            return null;
        }
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    /**
     * Getters, Setters, Equals and Hashcode
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        Post oldPost = this;
        this.title = title;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;

    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;

    }

    public void setContent(String newContent) {
        this.content = newContent;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImg_url(String img_url) {
        this.imgUrl = img_url;
    }

    public String getContent() {
        return this.content;
    }

    public int getUpVotes() {

        return upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (upVotes != post.upVotes) return false;
        if (downVotes != post.downVotes) return false;
        if (!title.equals(post.title)) return false;
        if (!author.equals(post.author)) return false;
        return imgUrl.equals(post.imgUrl);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + comments.hashCode();
        result = 31 * result + imgUrl.hashCode();
        result = 31 * result + upVotes;
        result = 31 * result + downVotes;
        return result;
    }
}
