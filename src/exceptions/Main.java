package exceptions;

import java.io.IOException;

public class Main {

    //Simulating different scenarios - run only 1 at a time:
    public static void main(String[] args) {
        runtimeExceptionDemo();

        //ExceptionsDemo.readFile("file.txt");

        //ExceptionsDemo.read("file.txt");

        //ExceptionsDemo.catchMultipleExceptions("file.txt");

        //ExceptionsDemo.finallyBlock("file.txt");

        //ExceptionsDemo.throwingRuntimeException(-1);

        //ExceptionsDemo.throwingCheckedException(-1);

        //rethrowing(-1);

        //ExceptionsDemo.customException(10);

        //ExceptionsDemo.customExceptionChain(10);


    }

    public static void runtimeExceptionDemo(){
        ExceptionsDemo.sayHello("Tomas");
        ExceptionsDemo.sayHello(null);
    }

    public static void rethrowing(float amount){
        try {
            ExceptionsDemo.rethrowingException(amount);
        } catch (IOException e) {
            System.out.println("An unexpected error occured"); //user friendly message
            //e.printStackTrace();
        }
    }




}
