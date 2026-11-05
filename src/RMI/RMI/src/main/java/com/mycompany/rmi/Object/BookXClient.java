package com.mycompany.rmi.Object;
import RMI.BookX;
import RMI.ObjectService;
import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class BookXClient {
    public static void main(String[] args) {

        String studentCode = "B23DCCN156";
        String qCode = ""; 
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            ObjectService service = (ObjectService)registry.lookup("RMIObjectService");
            Serializable receivedObject = service.requestObject(studentCode, qCode);
            BookX book = (BookX) receivedObject;
            String authorCode = getAuthorCode(book.getAuthor());
            String yearCode = String.format("%02d",Math.floorMod(book.getYearPublished(), 100));
            int genreLength = countLetters(book.getGenre());
            int titleCode = book.getTitle().length() % 10;
            String code = authorCode+ yearCode+ genreLength+ titleCode;
            book.setCode(code);
            service.submitObject(studentCode, qCode, book);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getAuthorCode(String author) {
        String cleaned = author.trim();
        if (cleaned.isEmpty()) {
            throw new IllegalArgumentException("Author khong hop le!");
        }
        String[] names = cleaned.split("\\s+");
        char first = names[0].charAt(0);
        String lastName = names[names.length - 1];
        char last = lastName.charAt(lastName.length() - 1);
        return ("" + first + last).toUpperCase();
    }
    public static int countLetters(String genre) {
        int count = 0;
        for (int i = 0; i < genre.length(); i++) {
            if (Character.isLetter(genre.charAt(i))) {
                count++;
            }
        }
        return count;
    }
}