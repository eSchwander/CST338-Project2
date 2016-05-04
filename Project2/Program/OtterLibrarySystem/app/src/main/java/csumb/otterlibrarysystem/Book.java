package csumb.otterlibrarysystem;

/**
 * Created by jazzb_000 on 5/5/2015.
 */
public class Book {
    private String title, author, isbn;
    private Double fee;

    private Time start = new Time(), end = new Time();

    public Book(String title, String author, String isbn, Double fee){
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.fee = fee;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public String getIsbn(){
        return isbn;
    }

    public Double getFee(){
        return fee;
    }


    public void setStartEnd(Time start, Time end){
        this.start = start;
        this.end = end;
    }

    public Integer getStart(){
        return start.getMinutes();
    }

    public Integer getEnd(){
        return end.getMinutes();
    }

    public boolean equals(Object other){
        if(other instanceof Book){
            if(isbn.equals(((Book) other).getIsbn()) &&
               author.equals(((Book) other).getAuthor()) &&
               fee.equals(((Book) other).getFee())  &&
               title.equals(((Book) other).getTitle()) ){
                return true;
            }
        }
            return false;
    }

    public String toString(){
        return title;
    }


}
