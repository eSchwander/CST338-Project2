package csumb.otterlibrarysystem;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class CancelHold extends ActionBarActivity implements View.OnClickListener {

    User currentUser = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_hold);
        Database instance = Database.getInstance();

        View logButton = findViewById(R.id.user_login_button);
        logButton.setOnClickListener(this);

        View cancelButton = findViewById(R.id.cancel_hold_button);
        cancelButton.setOnClickListener(this);

    }

    public void onClick(View v){
        if(v.getId() == R.id.user_login_button){
            Database instance = Database.getInstance();
            EditText username = (EditText) findViewById(R.id.account_name_field);
            EditText password = (EditText) findViewById(R.id.password_field);
            String name = username.getText().toString();
            String pass = password.getText().toString();

            if(instance.userLogin(name, pass)){
                currentUser = instance.getUser(name);
                ArrayList<Book> heldBooks = currentUser.getHeldBooks();
                if(heldBooks.size() != 0) {
                    ArrayAdapter<Book> bookAdapter = new ArrayAdapter<>(
                            this, android.R.layout.simple_spinner_item, heldBooks);
                    bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Spinner bookSpinner = (Spinner) findViewById(R.id.book_spinner);
                    bookSpinner.setAdapter(bookAdapter);
                }
                else{
                    Toast.makeText(this, "There are no reservations with that username.", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(this, "Invalid username/password", Toast.LENGTH_LONG).show();
            }
        }
        else if(v.getId() == R.id.cancel_hold_button){
            Spinner mySpinner = (Spinner) findViewById(R.id.book_spinner);
            String title = mySpinner.getSelectedItem().toString();

            if(!title.equals("None Available")){
                Database instance = Database.getInstance();
                EditText username = (EditText) findViewById(R.id.account_name_field);
                String name = username.getText().toString();
                User tempU = instance.getUser(name);

                ArrayList<Book> heldBooks = tempU.getHeldBooks();
                //Toast.makeText(this, heldBooks.get(0).toString(), Toast.LENGTH_LONG).show();
                if(heldBooks.size() > 0) {
                    instance.addLog("Transaction type: Cancel hold");
                    instance.addLog("Customer username: " + name);
                    instance.addLog("Book title: " + title);
                    Time end = new Time(tempU.getBook(title).getEnd());
                    Time start = new Time(tempU.getBook(title).getStart());
                    instance.getBook(title).setStartEnd(null, null);
                    instance.addLog("Pickup date/time: " + start.toString());
                    instance.addLog("Return date/time: " + end.toString());
                    Calendar currTime = Calendar.getInstance();
                    String ampm;
                    if(currTime.get(Calendar.AM_PM) == 1){
                        ampm = "AM";
                    }
                    else{
                        ampm = "PM";
                    }
                    instance.addLog("Transaction time: " + currTime.get(Calendar.HOUR_OF_DAY) +
                            ":" + currTime.get(Calendar.MINUTE) + "(" + ampm + ")\n");

                    tempU.cancelHold(title);
                    Toast.makeText(this, "Hold Canceled", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(this, MainMenu.class);
                    startActivity(i);

//                    ArrayAdapter<Book> bookAdapter = new ArrayAdapter<>(
//                            this, android.R.layout.simple_spinner_item, heldBooks);
//                    bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    Spinner bookSpinner = (Spinner) findViewById(R.id.book_spinner);
//                    bookSpinner.setAdapter(bookAdapter);
//                    if(heldBooks.size() == 0){
//                        ArrayList<String> noBooks = new ArrayList<>();
//                        noBooks.add("None Available");
//                        ArrayAdapter<String> emptyBookAdapter = new ArrayAdapter<>(
//                                this, android.R.layout.simple_spinner_item, noBooks);
//                        bookSpinner.setAdapter(emptyBookAdapter);
//                    }
                }
            }
        }
    }
}
