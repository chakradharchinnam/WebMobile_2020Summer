// Importing all the neccesary packages
package com.example.login;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    // Initializing variables
    Button Login;
    EditText Username;
    EditText Password;
    TextView Status;
    String usrname;
    String pswrd;
    boolean flag = false;
    // assigning them
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Username = findViewById(R.id.usernametxt);
        Password = findViewById(R.id.pwdtxt);
        Status = findViewById(R.id.statustxt);
        usrname = Username.getText().toString();
        pswrd = Password.getText().toString();
        Login = findViewById(R.id.button);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Username.getText().toString().isEmpty() && !Password.getText().toString().isEmpty()) {
                    if (Username.getText().toString().equals("vicky") && Password.getText().toString().equals("vickyntr"))
                    { flag = true; }
                }
                if (!flag)
                { Status.setText("Something went wrong with credentials"); }
                else
                { reDirectToMainScreen(); }
            }
        });
    }
    public void reDirectToMainScreen () {
        Intent intent = new Intent(MainActivity.this, MainScreen.class);
        startActivity(intent);
    }
}