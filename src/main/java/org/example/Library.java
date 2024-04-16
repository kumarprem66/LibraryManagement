package org.example;

import org.example.excep.BookNotAvailableException;
import org.example.model.Book;
import org.example.model.LibraryMember;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Library {

    private ArrayList<Book> books = new ArrayList<>();
    private LinkedList<LibraryMember> waitingList = new LinkedList<>();
    private Vector<LibraryMember> members = new Vector<>();
    private Stack<Book> returnedBooks = new Stack<>();
    private HashSet<LibraryMember> libraryMembers = new HashSet<>();
    private LinkedHashSet<Book> borrowedBooks = new LinkedHashSet<>();
    private TreeSet<Book> sortedBookTitles = new TreeSet<>(Comparator.comparing(Book::getTitle));
    private HashMap<Integer, Book> bookMap = new HashMap<>();
    private LinkedHashMap<Integer, Book> checkoutOrderMap = new LinkedHashMap<>();
    private TreeMap<String, Book> sortedBookMap = new TreeMap<>();




    public void addBook(Book book) {


        books.add(book);
        bookMap.put(book.getId(), book);
        sortedBookTitles.add(book);
        sortedBookMap.put(book.getTitle(), book);
    }

    // Method to add a library member
    public void addMember(LibraryMember member) {
        members.add(member);
        libraryMembers.add(member);
    }

    public void borrowBook(LibraryMember member, int bookId) throws BookNotAvailableException {
        Book book = bookMap.get(bookId);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            book.setBorrowerId(member.getMemberId());
            borrowedBooks.add(book);
            checkoutOrderMap.put(bookId, book);
        } else {
            waitingList.add(member);
            throw new BookNotAvailableException("Book is not available for checkout.");
        }
    }

    public void returnBook(int bookId) {
        Book book = bookMap.get(bookId);
        if (book != null) {
            book.setAvailable(true);
            book.setBorrowerId(0);
            returnedBooks.push(book);
        }
    }

    public void displayAvailableBooks() {
        System.out.println("Available Books:");
        books.stream()
                .filter(Book::isAvailable)
                .forEach(b-> System.out.println(b.getTitle() + " by " + b.getAuthor()));
    }

    // Method to display the list of borrowed books by a specific library member
    public void displayBorrowedBooks(LibraryMember member) {
        System.out.println("Borrowed Books by " + member.getName() + ":");
        borrowedBooks.stream()
                .filter(book -> member.getMemberId()==book.getBorrowerId() &&  !book.isAvailable())
                .forEach(book -> System.out.println(book.getTitle() + " by " + book.getAuthor()));
    }

    // Method to search for books by title
    public void searchBooksByTitle(String title) {
        System.out.println("Books with title \"" + title + "\":");
        sortedBookTitles.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .forEach(book -> System.out.println(book.getTitle() + " by " + book.getAuthor()));
    }

    // Method to search for books by author
    public void searchBooksByAuthor(String author) {
        System.out.println("Books by author \"" + author + "\":");
        sortedBookTitles.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(author))
                .forEach(book -> System.out.println(book.getTitle() + " by " + book.getAuthor()));


    }

    public boolean checkMemberExist(int member_id){
        return libraryMembers.stream().anyMatch(new Predicate<LibraryMember>() {
            @Override
            public boolean test(LibraryMember libraryMember) {
                return libraryMember.getMemberId()==member_id;
            }
        });
    }


}
