package generics;

//bad solution of the generic class - using Object class, 2 problems:
//1.) We have to cast into the type we want
//2.) Compiler cannot see the bugs - we can get a casting error if we try to cast wrong data types.

public class ObjectList {
  private Object[] items = new Object[10];
  private int count;

  public void add(Object item) {
    items[count++] = item;
  }

  public Object get(int index) {
    return items[index];
  }
}
