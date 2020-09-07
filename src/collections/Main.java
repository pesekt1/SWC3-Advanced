package collections;

import generics.GenericList;
import generics.User;

public class Main {

    public static void main(String[] args) {
        workingWithGenericList();
        CollectionDemo.show();
        ListDemo.show();
        ComparableDemo.show();
        QueueDemo.show();
        SetDemo.show();
        MapDemo.show();
    }

    public static void workingWithGenericList(){
        System.out.println("\n--------- genericList: -----------------");
        GenericList<Integer> intList = new GenericList<>();
        intList.add(1); // Integer.valueOf(1)
        intList.add(2);
        intList.add(3);
        System.out.println("\nprint intList:");
        for (var item:intList)
            System.out.println(item);

        GenericList<User> userList = new GenericList<>();
        userList.add(new User(10));
        userList.add(new User(20));
        userList.add(new User(30));
        System.out.println("\nprint userList:");
        for (var user:userList)
            System.out.println(user);
    }
}
