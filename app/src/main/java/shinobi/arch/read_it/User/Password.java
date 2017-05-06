package shinobi.arch.read_it.User;

/**
 * PassWord Class to be Hashed and sent to Firebase
 * Created by architgupta on 4/30/17.
 */

public class Password {
    private String pass;

    public Password() {

    }

    public Password(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Password password = (Password) o;

        return pass.equals(password.pass);

    }

    @Override
    public int hashCode() {
        return pass.hashCode();
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}