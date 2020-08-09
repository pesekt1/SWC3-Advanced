package exceptions;

import java.io.IOException;

public class Account {
  private float balance;
  private final float LIMIT = 1000;

  // we dont want to use try-catch block because this is an unchecked (runtime) exception
  //defensive programming - checking input and throwing an exception if needed
  public void deposit(float value)  {
    if (value <= 0)
      throw new IllegalArgumentException();
  }

  //checked exception - caller has to use try-catch block
  public void deposit2(float value) throws IOException {
    if (value <= 0)
      throw new IOException();
  }

  //custom exception
  public void withdraw(float value) throws InsufficientFundsException {
    if (value > balance)
      throw new InsufficientFundsException();
  }

  //chain of exceptions: General exception -> more specific exceptions
  public void withdraw2(float value) throws AccountException {
    if (value > balance)
      throw new AccountException(new InsufficientFundsException());
    if (value > LIMIT)
      throw new AccountException(new LimitOverdraft());
  }
}
















