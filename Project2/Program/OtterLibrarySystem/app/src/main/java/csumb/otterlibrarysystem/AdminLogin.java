package csumb.otterlibrarysystem;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class AdminLogin extends ActionBarActivity implements View.OnClickListener {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        View loginButton = findViewById(R.id.admin_login_button);
        loginButton.setOnClickListener(this);
    }

    public void onClick(View v){
        if(v.getId() == R.id.admin_login_button){
            EditText username = (EditText) findViewById(R.id.account_name_field);
            EditText password = (EditText) findViewById(R.id.password_field);
            if(username.getText().toString().equals("!admin2") &&
               password.getText().toString().equals("!admin2")){
                Intent i = new Intent(this, ManageSystem.class);
                startActivity(i);
            }
            else{
                String text = "Invalid username/password";
                Toast.makeText(this, text, Toast.LENGTH_LONG).show();
            }
        }
    }

}
