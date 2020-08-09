package exceptions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ExceptionsDemo {

    //can throw an exception if name is null
    public static void sayHello(String name) {
        System.out.println("Hello " + name.toUpperCase());
    }

    //can throw an exception if file does not exist
    public static void readFile(String name) {
        try {
            var reader = new FileReader(name);
            //this will not be executed if there is an exception
            System.out.println("File opened.");
        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        System.out.println("ReadFile finished.");
    }

    //IOException covers FileNotFoundException because it is its parent class.
    public static void read(String name) {
        try {
            var reader = new FileReader(name);
            var value = reader.read();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //catching multiple exceptions
    public static void catchMultipleExceptions(String name) {
        try {
            var reader = new FileReader(name);
            var value = reader.read();
            new SimpleDateFormat().parse("");
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
    }

    //using finally block to close resources
    public static void finallyBlock(String name) {
        FileReader reader = null;
        try {
            reader = new FileReader(name);
            var value = reader.read();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close(); //releasing the resource
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //using try block with resources
    public static void tryWithResources(String name) {
        try (var reader = new FileReader(name)) {
            var value = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //throwing runtime exception
    public static void throwingRuntimeException(float amount){
        var account = new Account();
        account.deposit(amount);
    }

    //throwing checked exception - try-catch block
    public static void throwingCheckedException(float amount){
        var account = new Account();
        try {
            account.deposit2(amount); //deposit2() needs to be in try-catch block
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //rethrowing an exception - we want to use it somewhere else in the program
    //the caller has to handle it.
    public static void rethrowingException(float amount) throws IOException {
        var account = new Account();
        try {
            account.deposit2(amount);
        } catch (IOException e) {
            System.out.println("Logging");
            throw e;
        }
    }

    public static void customException(float amount) {
        var account = new Account();
        try {
            account.withdraw(amount);
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void customExceptionChain(float amount) {
        var account = new Account();
        try {
            account.withdraw2(amount);
        } catch (AccountException e) {
            var cause = e.getCause();
            System.out.println(cause.getMessage());
        }
    }

}
