package com.mycompany.rmi.Object;
import RMI.Book;
import RMI.ObjectService;
import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class BookClient {

    public static void main(String[] args) {

        String studentCode = "B23DCCN156";
        String qCode = ""; 
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            ObjectService service = (ObjectService)registry.lookup("RMIObjectService");
            Serializable receivedObject = service.requestObject(studentCode, qCode);
            Book book = (Book) receivedObject;
            String authorCode = getAuthorCode(book.getAuthor());
            String yearCode = String.format("%02d",Math.floorMod(book.getYearPublished(), 100));
            int titleLength = countLetters(book.getTitle());
            String pageCode = String.format("%03d",book.getPageCount());
            String code = authorCode+ yearCode+ titleLength+ pageCode;
            book.setCode(code);
            service.submitObject(studentCode, qCode, book);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getAuthorCode(String author) {
        String[] names = author.trim().split("\\s+");
        if (names.length == 0) {
            throw new IllegalArgumentException("Author khong hop le!");
        }
        if (names.length == 1) {
            return names[0].substring(0, 1).toUpperCase();
        }
        String firstInitial = names[0].substring(0, 1);
        String lastInitial = names[names.length - 1].substring(0, 1);
        return (firstInitial + lastInitial).toUpperCase();
    }
    public static int countLetters(String title) {
        int count = 0;
        for (int i = 0; i < title.length(); i++) {
            if (Character.isLetter(title.charAt(i))) {
                count++;
            }
        }
        return count;
    }
}