package np.edu.sg.week12;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Main2Activity.java";
    SharedPreferences sharedPreferences;
    public static final String MY_GLOBAL_PREFS = "MyPrefs";
    public static final String MY_USERNAME = "MyUsername";
    public static final String MY_PASSWORD = "MyPassword";

    MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);



    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void onCreateClick(View v)
    {
        final EditText etPassword = (EditText) findViewById(R.id.editText_PasswordCreate);
        final EditText etUsername = (EditText) findViewById(R.id.editText_UsernameCreate);

        if(isValidUsername(etUsername.toString()) && isValidPassword(etPassword.toString()))
        {

            //sharedPreferences = getSharedPreferences(MY_GLOBAL_PREFS, MODE_PRIVATE);
            //sharedPreferences.Editor editor = sharedPreferences.edit();
            //editor.putString(MY_USERNAME, etUsername.getText().toString());
            //editor.putString(MY_PASSWORD, etPassword.getText().toString());
            //editor.commit();
            UserData dbData = dbHandler.findUser(etUsername.getText().toString());
            if(dbData == null)
            {
                String dbUserName = etUsername.getText().toString();
                String dbPassword = etPassword.getText().toString();
                UserData dbUserData = new UserData();
                dbUserData.setMyUserName(dbUserName);
                dbUserData.setMyPassword(dbPassword);
                dbHandler.addUser(dbUserData);
                Toast.makeText(Main2Activity.this, "UserCreated Successfully." , Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(Main2Activity.this, "User Already Exist \n Please Try Again.", Toast.LENGTH_LONG).show();
            }


            //Toast.makeText(Main2Activity.this, "Valid User Created", Toast.LENGTH_LONG).show();
            finish();

        }
        else
        {
            Toast.makeText(Main2Activity.this, "Invalid User Created", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isValidPassword(String password)
    {
        Pattern passwordPattern;
        Matcher passwordMatcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])([a-zA-Z]*).{6,12}$";

        passwordPattern = Pattern.compile(PASSWORD_PATTERN);
        passwordMatcher = passwordPattern.matcher(password);

        Log.v(TAG, "create Password " + passwordMatcher.matches());

        return passwordMatcher.matches();

    }

    public boolean isValidUsername(String userName)
    {
        Pattern userNamePattern;
        Matcher userNameMatcher;

        final String USERNAME_PATTERN = "^(?=.*[0-9])([a-zA-Z]*).{6,12}$";
        userNamePattern = Pattern.compile(USERNAME_PATTERN);
        userNameMatcher = userNamePattern.matcher(userName);

        Log.v(TAG, "create Username " + userNameMatcher.matches());

        return userNameMatcher.matches();
    }
}
