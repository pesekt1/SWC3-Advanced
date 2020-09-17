package generics;

public class NumberList<T extends Number> {
    //private T[] items = new T[10]; //Java compiler does not know type at this stage
    private T[] items = (T[]) new Object[10]; //workaround for Java compiler
    private int count;

    public void add(T item) {
        items[count++] = item;
    }

    public T get(int index) {
        return items[index];
    }
}
