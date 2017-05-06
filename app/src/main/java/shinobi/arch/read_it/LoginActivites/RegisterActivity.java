package shinobi.arch.read_it.LoginActivites;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import shinobi.arch.read_it.BaseActivity;
import shinobi.arch.read_it.ResourceClasses.Helper;
import shinobi.arch.read_it.R;
import shinobi.arch.read_it.User.Password;
import shinobi.arch.read_it.User.User;

public class RegisterActivity extends BaseActivity {
    private TextView name;
    private TextView email;
    private TextView userName;
    private TextView password;
    private Button signUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        setContentView(R.layout.activity_register);
        init();
        ref.addValueEventListener(valueEventListener);
        /**
         * On Click Listener for SignUp Button,
         * Checks for Validity of Inputs
         * Checks for Existing UserName
         * and then creates a new account
         */
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidInput()) {
                    User currentUser = new User(userName.getText().toString(), email.getText().toString());
                    Password currentPassword = new Password(password.getText().toString());
                    if (users.get(currentUser.getUserName()) != null) {
                        Toast.makeText(v.getContext(), Helper.NON_UNIQUE_USERNAME, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    users.put(userName.getText().toString(), currentUser);
                    authenticate.put(currentUser.getUserName(), currentPassword);
                    ref.child(Helper.USERS).setValue(users);
                    ref.child(Helper.AUTHENTICATION).setValue(authenticate);
                    try {
                        countDownLatch.await(10, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        Log.d(Helper.FIREBASE, Helper.INTERRUPTED_EXCEPTION);
                    }
                }
                Intent loginIntent = new Intent(v.getContext(), LoginActivity.class);
                ref.removeEventListener(valueEventListener);
                v.getContext().startActivity(loginIntent);

            }
        });

    }

    /**
     * Initializes View Attributes
     */
    private void init() {
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.emailID);
        userName = (TextView) findViewById(R.id.user_name);
        password = (TextView) findViewById(R.id.newPassword);
        signUpButton = (Button) findViewById(R.id.sign_up);
        name.setText("");
        email.setText("");
        userName.setText("");
        password.setText("");
    }

    /**
     * Checks if the User Input Fields Contain Valid Values
     *
     * @return True if Valid, False otherwise
     */
    private boolean isValidInput() {
        if ("".equals(name.getText().toString())) {
            Toast.makeText(this, Helper.EMPTY_NAME, Toast.LENGTH_SHORT).show();
            return false;
        } else if ("".equals(password.getText().toString()) ||
                password.getText().toString().length() < 4 ||
                password.getText().toString().length() > 10) {
            Toast.makeText(this, Helper.INVALID_PASSWORD_LENGTH, Toast.LENGTH_SHORT).show();
            return false;
        } else if ("".equals(userName.getText().toString()) || userName.getText().toString().length() > 7) {
            Toast.makeText(this, Helper.INVALID_USERNAME_LENGTH, Toast.LENGTH_SHORT).show();
            return false;
        } else if ("".equals(email.getText().toString()) || !isValidEmail(email.getText())) {
            Toast.makeText(this, Helper.INVALID_EMAIL, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Checks if a given email is valid
     *
     * @param target Char[], given email
     * @return True if Valid, False Otherwise
     */
    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}