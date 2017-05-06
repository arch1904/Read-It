package shinobi.arch.read_it.PostActivites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import java.io.ByteArrayOutputStream;

import shinobi.arch.read_it.BaseActivity;
import shinobi.arch.read_it.Post.Post;
import shinobi.arch.read_it.R;
import shinobi.arch.read_it.ResourceClasses.Helper;
import shinobi.arch.read_it.User.User;

import static shinobi.arch.read_it.ResourceClasses.Helper.REQUEST_IMAGE_CAPTURE;

public class NewPostActivity extends BaseActivity {
    private EditText title;
    private EditText content;
    private ImageView imageView;
    private Button publishButton;
    private Button cancelButton;
    private User currentUser;
    private String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        init();
        ref.addValueEventListener(valueEventListener);

        /**
         * On Click Listener for The Publish Button
         * Checks Validity of the User's Post and then Publishes it
         */
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidPost()) {
                    return;
                }
                String currentUserName = getIntent().getStringExtra(Helper.CURRENT_USER);
                currentUser = users.get(currentUserName);
                if (currentUser == null) {
                    Log.d(Helper.CURRENT_USER, Helper.NULL_USER);

                } else {
                    Post newPost = new Post(title.getText().toString(), currentUser, imgPath, content.getText().toString());
                    allPosts.add(newPost);
                    ref.child(Helper.POSTS).setValue(allPosts);
                    Intent postIntent = new Intent(v.getContext(), PostsActivity.class);
                    ref.removeEventListener(valueEventListener);
                    v.getContext().startActivity(postIntent);
                }
            }
        });

        /**
         * Checks for Tap on the Image View by the user to alter the Image
         */
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        /**
         * On Click Listener for Cancel Button that goes back to the Posts Page
         */
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postIntent = new Intent(v.getContext(), PostsActivity.class);
                v.getContext().startActivity(postIntent);
            }
        });


    }

    /**
     * Initialize all View Attributes
     */
    public void init() {
        setWindowParams();
        title = (EditText) findViewById(R.id.newTitleTextView);
        content = (EditText) findViewById(R.id.newContentTextView);
        imageView = (ImageView) findViewById(R.id.newImageView);
        publishButton = (Button) findViewById(R.id.publishButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        imgPath = "";
    }

    /**
     * Get Result for Image Intent and get the resultant URI
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            imgPath = encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

    /**
     * Encode Image Bitmap to String and send value to result
     *
     * @param bitmap Image Bitmap
     * @return String, encoded Image
     */
    public String encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        return imageEncoded;
    }


    /**
     * Check if the Post Data Entered by the User is valid
     *
     * @return True, if Valid post, False Otherwise
     */
    public boolean isValidPost() {
        if (title.getText().toString().length() == 0) {
            return false;
        }
        if (content.getText().toString().length() == 0) {
            return false;
        }
        return true;
    }

    /**
     * Set Window Parameters for Floating Window
     */
    public void setWindowParams() {
        WindowManager.LayoutParams wlp = getWindow().getAttributes();
        wlp.dimAmount = 0;
        wlp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        getWindow().setAttributes(wlp);
    }
}