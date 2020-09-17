package generics;

public class Utils {

    // non-generic method
    public static int max(int a, int b){
        return (a > b) ? a:b; //Math.max(a,b)
    }

    //generic method
    public static <T extends Comparable<T>> T max(T a, T b) {
        return (a.compareTo(b) < 0) ? a : b;
    }

    //generic method with 2 parameters
    public static <K, V> void print(K key, V value) {
        System.out.println(key + ":" + value);
    }

    //method expecting User object
    public static void printUser(User user) {
        System.out.println(user);
    }

    //method without a wildcard - we can only pass GenericList<User> object
    public static void printUsersFixed(GenericList<User> users) {
        for (var user : users) {
            System.out.println(user);
        }
    }

    //method with a wildcard - we can pass a GenericList of any User derivative type
    // <?> means we can pass any object type
    public static void printUsers(GenericList<? extends User> users) {
        for (var user : users) {
            //Object x = users.get(0);
            System.out.println(user);
        }
        //users.add(new User(10)); //not possible ... we need to use "super" keyword
    }

    // ? super User: Java compiler will treat the unknown type as a parent of the User which is Object class
    public static void add(GenericList<? super User> users){
        users.add(new User(10));
        users.add(new Instructor(20));
        System.out.println(users.get(0)); //this is possible because its like Object x = users.get(0)
        //User user = users.get(0); //not possible - compiler does not know if we can cast to User type
    }

}
