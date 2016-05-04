package csumb.otterlibrarysystem;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class AddBook extends ActionBarActivity implements View.OnClickListener {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        View addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(this);
    }


    public void onClick(View v){
        if(v.getId() == R.id.add_button){
            Database instance = Database.getInstance();
            EditText title = (EditText) findViewById(R.id.enter_title);
            EditText author = (EditText) findViewById(R.id.enter_author);
            EditText isbn = (EditText) findViewById(R.id.enter_isbn);
            EditText fee = (EditText) findViewById(R.id.enter_fee);
            String tempTitle = title.getText().toString();
            String tempAuth = author.getText().toString();
            String tempIsbn = isbn.getText().toString();
            String tempFee = fee.getText().toString();

            Double dFee = Double.parseDouble(tempFee);
            Book toAdd = new Book(tempTitle, tempAuth, tempIsbn, dFee);
            boolean flag = true;
            for(int i =0; i < instance.getBookAmount(); i++){
                if(toAdd.equals(instance.getBook(i))){
                    flag = false;
                    Toast.makeText(this, "Book already exists in the system", Toast.LENGTH_LONG).show();
                    break;
                }
            }
            if(flag){
                instance.addBook(toAdd);
            }

        }
    }

}
