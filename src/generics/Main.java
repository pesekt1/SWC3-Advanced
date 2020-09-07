package generics;


public class Main {

    //working with generics, run one method at a time:
    public static void main(String[] args) {
        intList();
        userList();
        //objectList(); // will throw an exception
        genericList();
        compareUsers();
        utils();
        multipleParameters();
        printUser();
        wildCards();
    }

    //simple integer list
    public static void intList(){
        System.out.println("\n--------- intList: --------------------");
        IntList list = new IntList();
        list.add(1);
        //list.add(1.2); //not possible to add a double
        //list.add("abc"); //not possible to add a string
        System.out.println(list.get(0));
    }

    public static void userList(){
        System.out.println("\n--------- userList: ------------------");
        var userList = new UserList();
        userList.add(new User(10));
        userList.add(new User(20));
        userList.add(new User(30));
        // We cannot iterate over the items because our userList is not implementing Iterable interface
//        for (var user:userList) {
//            System.out.println(user);
//        }
    }

    //bad solution of the generic class - using Object type, 2 problems:
    //1.) We have to cast into the type we want
    //2.) Compiler cannot see the bugs - we can get a casting error if we try to cast wrong data types.
    public static void objectList(){
        System.out.println("\n--------- objectList - bad solution: ----------");
        //allows us to store different types...
        ObjectList list = new ObjectList();
        list.add(1); // Integer.valueOf(1)
        list.add("String");
        list.add(new User(10));
        //int num = list.get(0); // error: expects int but gets Object
        int number = (int)list.get(0); // we have to cast the type
        int number2 = (int)list.get(2); // casting error - but compiler does not see it
    }

    //using a generic list
    public static void genericList(){
        System.out.println("\n--------- genericList: -----------------");
        GenericList<Integer> intList = new GenericList<>();
        intList.add(1); // Integer.valueOf(1)
        intList.add(2);
        intList.add(3);
        System.out.println("intList first item:" + intList.get(0));

        GenericList<User> userList = new GenericList<>();
        userList.add(new User(10));
        userList.add(new User(20));
        userList.add(new User(30));
        System.out.println("userList first item:" + userList.get(0));
    }

    //we can compare users because we implemented Comparable interface
    public static void compareUsers(){
        System.out.println("\n--------- comparable User: ------------------");
        var user1 = new User(20);
        var user2 = new User(30);

        if(user1.compareTo(user2) > 0)
            System.out.println("user1 > user2");
        else if(user1.compareTo(user2) == 0)
            System.out.println("user1 = user2");
        else
            System.out.println("user1 < user2");

        var instructor1 = new Instructor(50);
        User instructor2 = new Instructor(10);
    }

    //generic methods
    public static void utils(){
        System.out.println("\n--------- utils: generic methods --------------------");
        var max = Utils.max(1,2); // bigger integer
        System.out.println(max);

        var max2 = Utils.max(new User(10), new User(15)); //user with more points
        System.out.println(max2); //we overridden toString method for User class
    }

    //multiple generics
    public static void multipleParameters(){
        System.out.println("\n--------- utils: generics with multiple parameters:");
        Utils.print(1,"Tomas");
        Utils.print(1, new User(50));
        Utils.print("Tomas", new User(100));
    }

    public static void printUser(){
        System.out.println("\n--------- utils: printUser: --------------------");
        Utils.printUser(new User(50));
        Utils.printUser(new Instructor(60)); // Instructor is a User
    }

    //wildcards
    public static void wildCards(){
        System.out.println("\n--------- utils: wildcards: ---------------------------");

        var usersList = new GenericList<User>();
        usersList.add(new User(10));
        usersList.add(new Instructor(20));
        System.out.println("\nprint list of users:");
        Utils.printUsersFixed(usersList);

        var instructorsList = new GenericList<Instructor>();
        instructorsList.add(new Instructor(10));
        instructorsList.add(new Instructor(15));
        System.out.println("\nprint list of instructors:");
        Utils.printUsers(instructorsList);

        //not possible: GenericList<Instructor> is not a subtype of GenericList<User>
        //Utils.printUsersFixed(instructorsList);

        Utils.add(usersList);
    }
}
