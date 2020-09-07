package generics;

// list for storing integers - just to show the point that this solution is limited for Integer
// we would need another class to make a FloatList etc.
public class IntList {
  private int[] items = new int[10];
  private int count;

  public void add(int item) {
    items[count++] = item;
  }

  public int get(int index) {
    return items[index];
  }
}
