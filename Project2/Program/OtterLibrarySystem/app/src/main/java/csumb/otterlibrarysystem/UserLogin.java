package csumb.otterlibrarysystem;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.NumberFormat;


public class UserLogin extends ActionBarActivity implements View.OnClickListener {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        View loginButton = findViewById(R.id.user_login_button);
        loginButton.setOnClickListener(this);

    }


    public void onClick(View v){

        Bundle extras = getIntent().getExtras();
        Integer start = extras.getInt("startMinute");
        Integer end = extras.getInt("endMinute");
        String title = extras.getString("title");
        Integer totalMin = end - start;

        if(v.getId() == R.id.user_login_button){
            Database instance = Database.getInstance();
            EditText username = (EditText) findViewById(R.id.user_name_field);
            EditText password = (EditText) findViewById(R.id.password_field);
            String name = username.getText().toString();
            String pass = password.getText().toString();

            if(instance.userLogin(name, pass)){
                instance.addLog("Transaction Type: Place Hold");
                instance.addLog("Customer username: " + name);
                instance.addLog("Pickup date/time: " + new Time(start).toString());
                instance.addLog("Return date/time: " + new Time(end).toString());
                instance.addLog("Book Title: " + title);

                Book tempBook = instance.getBook(title);
                Double tempFee = tempBook.getFee() * (totalMin/60);
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                String fee = formatter.format(tempFee);
                instance.addLog("Total amount: " + fee  + "\n");
                String toasty = "Hold Placed!\n";
                toasty += "Pick up date: " + new Time(start).toString() + "\n";
                toasty += "Return date: " + new Time(end).toString() + "\n";
                toasty += "Total amount: " + fee;

                Toast.makeText(this, toasty, Toast.LENGTH_LONG).show();
                User tempUser = instance.getUser(name);
                tempUser.setHold(tempBook);
                tempBook.setStartEnd(new Time(start), new Time(end));
//                Intent i = new Intent(this, MainMenu.class);
//                startActivity(i);
            }
            else{
                Toast.makeText(this, "Invalid username/password", Toast.LENGTH_LONG).show();
            }
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent i = new Intent(this, MainMenu.class);
            startActivity(i);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
