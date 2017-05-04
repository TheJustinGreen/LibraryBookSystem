package com.example.justin.librarybooks;

/**
 *
 * Created by thaso on 4/23/2017.
 * A class of book objects
 */

public class Book
{

    //Instance variables of a book object
    private String isbn;
    private String title;
    private String author;
    private String studentNumber;
    private String description;
    private String edition;
    private int drawable, count;
    private String bookReserveDate,bookReturnDate;
    private int id = 0;

    /**
     * A constructor of a book object
     * @param author Book author
     * @param count The number of books available
     * @param description The book description
     * @param drawable the front cover of the book
     * @param edition Book edition
     * @param isbn The identification number of a book
     * @param studentNumber The student number of the student that borrowed the book
     * @param title The title of the book
     */
    public Book(String author, int count, String description, int drawable, String edition, String isbn, String studentNumber, String title) {
        this.author = author;
        this.count = count;
        this.description = description;
        this.drawable = drawable;
        this.edition = edition;
        this.isbn = isbn;
        this.studentNumber = studentNumber;
        this.title = title;
    }

    /**
     * Default constructor of a book object
     */
    public Book()
    {
        return;
    }

    /**
     * Return the book id
     * @return
     */
    public int getId() {

        return id;
    }

    /**
     * Sets the book's id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return the date on which a borrowed book should returned
     * @return
     */
    public String getBookReturnDate() {
        return bookReturnDate;
    }

    /**
     * Sets the day on which a book is to be returned
     * @param bookReturnDate Book return date
     */
    public void setBookReturnDate(String bookReturnDate) {
        this.bookReturnDate = bookReturnDate;
    }

    /**
     * Returns the date on which a book was reserved
     * @return
     */
    public String getBookReserveDate() {
        return bookReserveDate;
    }

    /**
     * Sets the day on which a book is borrowed
     * @param bookReserveDate
     */
    public void setBookReserveDate(String bookReserveDate) {
        this.bookReserveDate = bookReserveDate;
    }

    /**
     * Returns the number of books available.
     * @return
     */
    public int getCount() {
        return count;
    }

    /**
     * Returns the book edition.
     * @return
     */
    public String getEdition() {
        return edition;
    }

    /**
     * Returns the author of the book.
     * @return
     */
    public String getAuthor() {

        return author;
    }

    /**
     * Sets the author of the book.
     * @param author
     */
    public void setAuthor(String author) {

        this.author = author;
    }

    /**
     * Returns the book discription
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the book description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Rerturns the front cover of the book object
     * @return
     */
    public int getDrawable() {
        return drawable;
    }

    /**
     * Sets the image of the book's front cover
     * @param drawable
     */
    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    /**
     * Returns the ISBN of a book
     * @return
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of a book object
     * @param isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Returns the student number of the student who reserved the book
     * @return
     */
    public String getStudentNumber() {
        return studentNumber;
    }

    /**
     * Sets the student number of the student who borrowed the book
     * @param studentNumber
     */
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    /**
     * Returns the title of the object
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of a book object
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
