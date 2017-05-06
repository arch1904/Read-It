package shinobi.arch.read_it.Post;

import shinobi.arch.read_it.User.User;

/**
 * Representation of a Comment
 * Created by architgupta on 4/30/17.
 */

public class Comment {
    private User author;
    private String comment;

    /**
     * Default Constructor
     */
    public Comment() {

    }

    /**
     * Parameterized Constructor
     *
     * @param author  User, Author
     * @param comment String, Comment
     */
    public Comment(User author, String comment) {
        this.author = author;
        this.comment = comment;
    }

    /**
     * Getters, Setters, equals and hashcode
     * Machine Generated
     */
    public User getAuthor() {

        return author;
    }

    public String getComment() {
        return comment;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment1 = (Comment) o;

        if (!author.equals(comment1.author)) return false;
        return comment.equals(comment1.comment);

    }

    @Override
    public int hashCode() {
        int result = author.hashCode();
        result = 31 * result + comment.hashCode();
        return result;
    }
}