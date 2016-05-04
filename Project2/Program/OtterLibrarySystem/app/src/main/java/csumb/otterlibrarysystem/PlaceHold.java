package csumb.otterlibrarysystem;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class PlaceHold extends ActionBarActivity implements View.OnClickListener {

    public ArrayList<String> populateSpinner(String title, Integer start, Integer end){
        ArrayList<String> toReturn = new ArrayList<>();
        String zero = "";
        toReturn.add(title);
        for(Integer i = start; i <= end; i++){
            if(i < 10){
                zero = "0";
            }
            else{
                zero = "";
            }
            toReturn.add(zero + i.toString());
        }
        return toReturn;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_hold);

        // Populate day spinners
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, populateSpinner("Day", 1, 30));
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner startDay = (Spinner) findViewById(R.id.start_days);
        startDay.setAdapter(dayAdapter);
        Spinner endDay = (Spinner) findViewById(R.id.end_days);
        endDay.setAdapter(dayAdapter);


        //Populate hour spinners
        ArrayAdapter<String> hourAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, populateSpinner("Hour", 1, 12));
        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner startHour = (Spinner) findViewById(R.id.start_hours);
        startHour.setAdapter(hourAdapter);
        Spinner endHour = (Spinner) findViewById(R.id.end_hours);
        endHour.setAdapter(hourAdapter);


        //Populate minute spinners
        ArrayAdapter<String> minuteAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, populateSpinner("Minute", 0, 59));
        minuteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner startMinute = (Spinner) findViewById(R.id.start_minutes);
        startMinute.setAdapter(minuteAdapter);
        Spinner endMinute = (Spinner) findViewById(R.id.end_minutes);
        endMinute.setAdapter(minuteAdapter);


        //find books button
        View findBooksButton = findViewById(R.id.search_for_books_button);
        findBooksButton.setOnClickListener(this);

        //checkout button
        View checkoutButton = findViewById(R.id.checkout_button);
        checkoutButton.setOnClickListener(this);
    }

    public void onClick(View v){

        Time start = new Time();
        Time end = new Time();
        Integer[] spinnerArray = new Integer[4];
        String tempampm;

        // If the user clicks find books
        if(v.getId() == R.id.search_for_books_button){


            // Some error checking.
            if(end.getMinutes() - start.getMinutes() > 10080){
                Toast.makeText(this, "A book cannot be reserved longer than 7 days.", Toast.LENGTH_LONG).show();
            }
            else if(end.getMinutes() - start.getMinutes() < 0){
                Toast.makeText(this, "Error: Ending date is before starting date.", Toast.LENGTH_LONG).show();
            }

            else {
                //Collect start data from spinners
                Spinner mySpinner = (Spinner) findViewById(R.id.start_months);
                spinnerArray[0] = Integer.parseInt(mySpinner.getSelectedItem().toString()) - 1;
                mySpinner = (Spinner) findViewById(R.id.start_days);
                spinnerArray[1] = Integer.parseInt(mySpinner.getSelectedItem().toString());
                mySpinner = (Spinner) findViewById(R.id.start_hours);
                spinnerArray[2] = Integer.parseInt(mySpinner.getSelectedItem().toString());
                mySpinner = (Spinner) findViewById(R.id.start_minutes);
                spinnerArray[3] = Integer.parseInt(mySpinner.getSelectedItem().toString());
                mySpinner = (Spinner) findViewById(R.id.start_ampm);
                tempampm = mySpinner.getSelectedItem().toString();
                start = new Time(spinnerArray[0], spinnerArray[1], spinnerArray[2], spinnerArray[3], tempampm);

                //Collect end data from spinners
                mySpinner = (Spinner) findViewById(R.id.end_months);
                spinnerArray[0] = Integer.parseInt(mySpinner.getSelectedItem().toString()) - 1;
                mySpinner = (Spinner) findViewById(R.id.end_days);
                spinnerArray[1] = Integer.parseInt(mySpinner.getSelectedItem().toString());
                mySpinner = (Spinner) findViewById(R.id.end_hours);
                spinnerArray[2] = Integer.parseInt(mySpinner.getSelectedItem().toString());
                mySpinner = (Spinner) findViewById(R.id.end_minutes);
                spinnerArray[3] = Integer.parseInt(mySpinner.getSelectedItem().toString());
                mySpinner = (Spinner) findViewById(R.id.end_ampm);
                tempampm = mySpinner.getSelectedItem().toString();
                end = new Time(spinnerArray[0], spinnerArray[1], spinnerArray[2], spinnerArray[3], tempampm);
            }
            //Toast.makeText(this, end.toString(), Toast.LENGTH_LONG).show();


            ArrayList<Book> availableBooks = new ArrayList<>();
            Database instance = Database.getInstance();
            // Find currently available books
            for(int i = 0; i < instance.getBookAmount(); i++){
                if(instance.bookAvailability(instance.getBook(i), start, end)){
                    availableBooks.add(instance.getBook(i));
                }
            }
            if(!availableBooks.isEmpty()){
                ArrayAdapter<Book> bookAdapter = new ArrayAdapter<>(
                        this, android.R.layout.simple_spinner_item, availableBooks);
                bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner startMinute = (Spinner) findViewById(R.id.book_spinner);
                startMinute.setAdapter(bookAdapter);
            }
        }

        else if(v.getId() == R.id.checkout_button){

            //Collect start data from spinners
            Spinner mySpinner = (Spinner) findViewById(R.id.start_months);
            spinnerArray[0] = Integer.parseInt(mySpinner.getSelectedItem().toString()) - 1;
            mySpinner = (Spinner) findViewById(R.id.start_days);
            spinnerArray[1] = Integer.parseInt(mySpinner.getSelectedItem().toString());
            mySpinner = (Spinner) findViewById(R.id.start_hours);
            spinnerArray[2] = Integer.parseInt(mySpinner.getSelectedItem().toString());
            mySpinner = (Spinner) findViewById(R.id.start_minutes);
            spinnerArray[3] = Integer.parseInt(mySpinner.getSelectedItem().toString());
            mySpinner = (Spinner) findViewById(R.id.start_ampm);
            tempampm = mySpinner.getSelectedItem().toString();
            start = new Time(spinnerArray[0], spinnerArray[1], spinnerArray[2], spinnerArray[3], tempampm);

            //Collect end data from spinners
            mySpinner = (Spinner) findViewById(R.id.end_months);
            spinnerArray[0] = Integer.parseInt(mySpinner.getSelectedItem().toString()) - 1;
            mySpinner = (Spinner) findViewById(R.id.end_days);
            spinnerArray[1] = Integer.parseInt(mySpinner.getSelectedItem().toString());
            mySpinner = (Spinner) findViewById(R.id.end_hours);
            spinnerArray[2] = Integer.parseInt(mySpinner.getSelectedItem().toString());
            mySpinner = (Spinner) findViewById(R.id.end_minutes);
            spinnerArray[3] = Integer.parseInt(mySpinner.getSelectedItem().toString());
            mySpinner = (Spinner) findViewById(R.id.end_ampm);
            tempampm = mySpinner.getSelectedItem().toString();
            end = new Time(spinnerArray[0], spinnerArray[1], spinnerArray[2], spinnerArray[3], tempampm);
            //Integer totalMinutes = end.getMinutes() - start.getMinutes();
            mySpinner = (Spinner) findViewById(R.id.book_spinner);
            String bookTitle = mySpinner.getSelectedItem().toString();

            if(mySpinner.getSelectedItem().toString().equals("None Available")){
                //does nothing
            }
            else{
                Intent intent = new Intent(this,UserLogin.class);

                intent.putExtra("startMinute", start.getMinutes());
                intent.putExtra("endMinute", end.getMinutes());
                intent.putExtra("title", bookTitle);
                startActivity(intent);
            }
        }
    }
}
