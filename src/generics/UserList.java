package generics;

//list for storing users
public class UserList {
  private User[] users = new User[10];
  private int count;

  public void add(User user) {
    users[count++] = user;
  }

}
