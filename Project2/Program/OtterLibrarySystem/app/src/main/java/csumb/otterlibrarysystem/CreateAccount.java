package csumb.otterlibrarysystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class CreateAccount extends ActionBarActivity implements View.OnClickListener{
    Database instance;
    boolean attempt = false;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        instance = Database.getInstance();

        View createButton = findViewById(R.id.create_account_button);
        createButton.setOnClickListener(this);

    }

    public void onClick(View v){
        String text = null;
        if(v.getId() == R.id.create_account_button){
            EditText username = (EditText) findViewById(R.id.account_name_field);
            EditText password = (EditText) findViewById(R.id.password_field);
            Integer accountCreation = instance.addUser(username.getText().toString(), password.getText().toString());
            if(accountCreation == 1) {
                text = "Your account has been successfully created!";
                Toast.makeText(this, text, Toast.LENGTH_LONG).show();
//                Intent i = new Intent(this, MainMenu.class);
//                startActivity(i);
            }
            else{
                if(accountCreation == 0){
                    text = "Incorrect username/password format.";
                }
                else if(accountCreation == -1){
                    text = "This account already exists.";
                }
                if(!attempt){
                    text += "\nYou have one more attempt.";
                    attempt = true;
                }
                else{
                    attempt = false;
                    text += "\nReturning to main menu.";
                    Intent i = new Intent(this, MainMenu.class);
                    startActivity(i);
                }
                Toast.makeText(this, text, Toast.LENGTH_LONG).show();
            }

        }
    }

}
