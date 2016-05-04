package csumb.otterlibrarysystem;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jazzb_000 on 5/8/2015.
 */
public class User {
    private String password;
    private ArrayList<Book> heldBooks = new ArrayList<>();

    public User(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public ArrayList<Book> getHeldBooks(){
        return heldBooks;
    }


    public Book getBook(int i){
        return heldBooks.get(i);
    }

    public Book getBook(String title){
        for(int i = 0; i < heldBooks.size(); i++){
            if(heldBooks.get(i).toString().equals(title)){
                return getBook(i);
            }
        }
        return null;
    }

    public void setHold(Book book){
        heldBooks.add(book);
    }

    public void cancelHold(String title){
        heldBooks.remove(getBook(title));
    }

    public int getBookIndex(String title){
        for(int i = 0; i < heldBooks.size(); i++){
            if(heldBooks.get(i).toString().equals(title)){
                return i;
            }
        }
        return -1;
    }

}
