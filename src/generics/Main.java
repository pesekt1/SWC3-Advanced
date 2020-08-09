package generics;

import jdk.jshell.execution.Util;

public class Main {

    //working with generics, run one method at a time:
    public static void main(String[] args) {
        //intList();
        //objectList();
        //genericList();
        //compareUsers();
        //utils();
        //multipleParameters();
        //wildCards();
    }

    //simple integer list
    public static void intList(){
        IntList list = new IntList();
        list.add(1);
        System.out.println(list.get(0));
    }

    //bad solution:
    public static void objectList(){
        //allows us to store different types...
        ObjectList list = new ObjectList();
        list.add(1);
        list.add("String");
        list.add(new User(10));
        int number = (int)list.get(0);
        int number2 = (int)list.get(2); // casting error
    }

    //using a generic list
    public static void genericList(){
       GenericList<Integer> list = new GenericList<>();
       list.add(1);
       GenericList<User> userList = new GenericList<>();
       userList.add(new User(10));
    }

    //we can compare users because we implemented Comparable interface
    public static void compareUsers(){
        var user1 = new User(20);
        var user2 = new User(10);
        if(user1.compareTo(user2) > 0)
            System.out.println("user1 > user2");

    }

    //generic methods
    public static void utils(){
        var max = Utils.max(1,2); // bigger integer
        System.out.println(max);

        var max2 = Utils.max(new User(10), new User(15)); //user with more points
        System.out.println(max2); //we overridden toString method for User class
    }

    //multiple generics
    public static void multipleParameters(){
        Utils.print(1,"Tomas");
    }

    //wildcards
    public static void wildCards(){
        var instructorsList = new GenericList<Instructor>();
        instructorsList.add(new Instructor(10));
        instructorsList.add(new Instructor(15));
        Utils.printUsers(instructorsList);
    }
}
