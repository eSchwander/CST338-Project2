package csumb.otterlibrarysystem;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;


public class ManageSystem extends ActionBarActivity implements View.OnClickListener {
    Database instance;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_system);
        instance = Database.getInstance();
        TextView log = (TextView)findViewById(R.id.log_field);
        ArrayList<String> logArray = instance.getLog();
        if(logArray.size() > 0){
            String logString = "";
            for (int i = 0; i < logArray.size(); i++) {
                logString += (i + 1) + ". " + logArray.get(i) + "\n";
            }
            log.setText(logString);
        }

        View addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(this);

    }

    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent i = new Intent(this, MainMenu.class);
            startActivity(i);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void onClick(View v){
        if(v.getId() == R.id.add_button){
            Intent i = new Intent(this, AddBook.class);
            startActivity(i);
        }
    }
}
