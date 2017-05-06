package shinobi.arch.read_it.User;

/**
 * User Class
 * Representation of a User
 * Created by architgupta on 4/18/17.
 */

public class User {
    private String userName;
    private String emailID;
    private int upVotes;
    private int downVotes;
    private int karma;

    public User() {

    }

    /**
     * As soon as someone upvotes your POST
     * increment general upvote count and karma
     */
    public void upVote() {
        this.karma++;
        this.upVotes++;
    }

    /**
     * As soon as someone downvotes your POST
     * decrement general downvote count and karma
     */
    public void downVote() {
        this.karma--;
        this.downVotes++;
    }

    /**
     * Remove Upvote from User
     */
    public void removeUpVote() {
        if (upVotes > 0) {
            upVotes--;
        }
    }

    /**
     * Remove DownVote from User
     */
    public void removeDownVote() {
        if (downVotes > 0) {
            downVotes--;
        }
    }

    /**
     * Parametrized Constructor
     *
     * @param userName
     * @param emailID
     */
    public User(String userName, String emailID) {
        this.userName = userName;
        this.emailID = emailID;
        this.upVotes = 0;
        this.downVotes = 0;
        this.karma = 0;
    }

    /**
     * Getters, Setters, Equals, HashCode (Machine Generated)
     */

    public String getUserName() {
        return userName;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public int getKarma() {
        return karma;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (upVotes != user.upVotes) return false;
        if (downVotes != user.downVotes) return false;
        if (karma != user.karma) return false;
        if (!userName.equals(user.userName)) return false;
        return emailID.equals(user.emailID);

    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + emailID.hashCode();
        result = 31 * result + upVotes;
        result = 31 * result + downVotes;
        result = 31 * result + karma;
        return result;
    }
}