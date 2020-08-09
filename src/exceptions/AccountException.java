package exceptions;

//custom exception
public class AccountException extends Exception {
  public AccountException(Exception cause) {
    super(cause); //we pass the exception in the constructor
    //it can be retreived using getCause() method
  }
}
