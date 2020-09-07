package generics;

public class Instructor extends User {

  //there is no default constructor in User class
  //so we need to create a constructor and call the Users constructor - "super" keyword
  public Instructor(int points) {
    super(points);
  }
}
