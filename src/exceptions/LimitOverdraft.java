package exceptions;

public class LimitOverdraft extends Exception{
    public LimitOverdraft() {
        super("Account limit overdraft.");
    }
}
