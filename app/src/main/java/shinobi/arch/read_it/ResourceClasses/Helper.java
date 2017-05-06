package shinobi.arch.read_it.ResourceClasses;

import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.Map;

import shinobi.arch.read_it.Post.Post;
import shinobi.arch.read_it.User.Password;
import shinobi.arch.read_it.User.User;

/**
 * HELPER CLASS TO HOLD ALL THE CONSTANT VALUES
 * Created by architgupta on 4/30/17.
 */

public class Helper {
    public static final String POSTS = "Posts";
    public static final String USERS = "Users";
    public static final String AUTHENTICATION = "Auth";
    public static final String CURRENT_USER = "curr_user";
    public static final GenericTypeIndicator<ArrayList<Post>> POST_LIST_TYPE = new GenericTypeIndicator<ArrayList<Post>>() {
    };
    public static final GenericTypeIndicator<Map<String, User>> USERNAME_USER_MAP = new GenericTypeIndicator<Map<String, User>>() {
    };
    public static final GenericTypeIndicator<Map<String, Password>> USER_PASS_MAP = new GenericTypeIndicator<Map<String, Password>>() {
    };

    public static final String INVALID_PASSWORD = "Invalid Password";
    public static final String WELCOME_MSG = "Welcome to Read-It %s";
    public final static String POST = "POST";
    public static final String UP_VOTED = "upvoted";
    public static final String UP_VOTE = "upvote";
    public static final String DOWN_VOTE = "Downvote";
    public static final String DOWN_VOTED = "Downvoted";
    public static final String EMPTY_COMMENT = "Comment Can't Be Empty";
    public static final int REQUEST_IMAGE_CAPTURE = 111;
    public static final String EMPTY_NAME = "Name Can't Be Empty";
    public static final String INVALID_PASSWORD_LENGTH = "Invalid Password, Password has to be from 4 to 10 characters";
    public static final String INVALID_USERNAME_LENGTH = "UserName has to be from  1 to 7 characters";
    public static final String NON_UNIQUE_USERNAME = "username is already under use, please make a more unique one";
    public static final String INVALID_EMAIL = "Invalid Email";
    public static final String INTERRUPTED_EXCEPTION = "Interrupted Exception";
    public static final String FIREBASE = "Firebase";
    public static final String IMAGE = "IMAGE";
    public static final String FAILED = "FAILED";
    public static final String NULL_USER = "NULL USER";
}