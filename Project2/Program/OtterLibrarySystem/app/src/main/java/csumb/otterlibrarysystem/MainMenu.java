package csumb.otterlibrarysystem;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainMenu extends ActionBarActivity implements View.OnClickListener {
    Database instance;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        instance = Database.getInstance();

        View createButton = findViewById(R.id.create_account_button);
        createButton.setOnClickListener(this);
        View placeHoldButton = findViewById(R.id.place_hold_button);
        placeHoldButton.setOnClickListener(this);
        View cancelHoldButton = findViewById(R.id.cancel_hold_button);
        cancelHoldButton.setOnClickListener(this);
        View manageSystemButton = findViewById(R.id.admin_login_button);
        manageSystemButton.setOnClickListener(this);
    }

    public void onClick(View v){
        if(v.getId() == R.id.create_account_button){
            Intent i = new Intent(this, CreateAccount.class);
            startActivity(i);
        }
        else if(v.getId() == R.id.place_hold_button){
            Intent i = new Intent(this, PlaceHold.class);
            startActivity(i);
        }
        else if(v.getId() == R.id.cancel_hold_button){
            Intent i = new Intent(this, CancelHold.class);
            startActivity(i);
        }
        else if(v.getId() == R.id.admin_login_button){
            Intent i = new Intent(this, AdminLogin.class);
            startActivity(i);
        }
    }


}
