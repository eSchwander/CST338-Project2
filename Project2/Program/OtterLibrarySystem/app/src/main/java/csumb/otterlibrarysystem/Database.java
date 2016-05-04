package csumb.otterlibrarysystem;


import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by jazzb_000 on 5/5/2015.
 */
public class Database {
    private static Database instance = null;
    private HashMap<String, User> users = new HashMap<>();
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<String> log = new ArrayList<>();

    private Database(){
        users.put("a@lice5", new User("@csit100"));
        users.put("$brian7", new User("123abc##"));
        users.put("!chris12!", new User("CHRIS12!!"));
        users.put("!admin2", new User ("!admin2"));

        books.add(new Book("Hot Java", "S. Narayanan", "123-ABC-101", .05));
        books.add(new Book("Fun Java", "Y. Byun", "ABCDEF-09", 1.0));
        books.add(new Book("Algorithm for Java", "K. Alice", "CDE-777-123", .25));
    }

    public static Database getInstance(){
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }

    public boolean validUser(String username, String password){

        if(users.containsKey(username)){
            return false;
        }

        boolean special = false, num = false, user = false, pass = false;
        int alpha = 0;
        char temp;

        //check for valid username
        for(int i = 0; i < username.length();i++){
            temp = username.charAt(i);
            if(temp == '@' || temp == '!' || temp == '#' || temp == '$'){
                special = true;
            }
            else if(temp >= 48 && temp <= 57){
                num = true;
            }
            else if((temp >= 65 && temp <= 90) || (temp >= 97 && temp <= 122)){
                alpha++;
            }
        }
        if(special && num && alpha >= 3){
            user = true;
        }

        special = false;
        num = false;
        alpha = 0;

        //check for valid password
        for(int i = 0; i < password.length();i++){
            temp = password.charAt(i);
            if(temp == '@' || temp == '!' || temp == '#' || temp == '$'){
                special = true;
            }
            else if(temp >= 48 && temp <= 57){
                num = true;
            }
            else if((temp >= 65 && temp <= 90) || (temp >= 97 && temp <= 122)){
                alpha++;
            }
        }
        if(special && num && alpha >= 3){
            pass = true;
        }

        return(user && pass);
    }

    public boolean checkDuplicate(String username){
        return !users.containsKey(username);
    }

    public int addUser(String username, String password){
        //  What the returned int means.
        // -1 = duplicate account
        //  0 = invalid format
        //  1 = success
        if(validUser(username, password)){
            users.put(username, new User(password));
            log.add("Transaction Type: New Account  ");
            log.add("Customer's username: " + username);
            Calendar currTime = Calendar.getInstance();
            log.add("Transaction date: " + (currTime.get(Calendar.MONTH)+1) + "/"
                    + currTime.get(Calendar.DAY_OF_MONTH) + "/"
                    + currTime.get(Calendar.YEAR));
            Integer hour = currTime.get(Calendar.HOUR_OF_DAY);
            String ampm = " (AM)";
            if(hour > 12){
                hour -= 12;
                ampm = " (PM)";
            }
            log.add("Transaction time: " + hour + ":" + currTime.get(Calendar.MINUTE) + ampm + "\n");

            return 1;
        }
        else if(checkDuplicate(username)){
            return 0;
        }
        else{
            return -1;
        }
    }

    public ArrayList<String> getLog(){
        return log;
    }

    public Integer getBookAmount(){
        return books.size();
    }

    public Book getBook(int i){
        return books.get(i);
    }

    public Book getBook(String title){
        for(int i = 0; i < books.size(); i++){
            if(books.get(i).toString().equals(title)){
                return getBook(i);
            }
        }
        return null;
    }

    public boolean bookAvailability(Book book, Time start, Time end){
        Integer bs = book.getStart(), be = book.getEnd(),
                s = start.getMinutes(), e = end.getMinutes();

        // if the book is not available at the moment, return false
        if((bs >= s && bs <= e) || (be >= s && be <= e)){
            return false;
        }
        else if((e >= bs && e <= be) || (s >= be && s <= bs)){
            return false;
        }

        return true;
    }

    public boolean userLogin(String username, String password){
        try {
            User temp = users.get(username);
            String pass = temp.getPassword();
            if (pass.equals(password)) {
                return true;
            }
            return false;
        }
        catch(Exception e){
            return false;
        }
    }

    public void addLog(String log){
        this.log.add(log);
    }

    public void addBook(Book book){
        books.add(book);
    }

    public User getUser(String name){
        return users.get(name);
    }
}
