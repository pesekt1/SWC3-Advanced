package collections;

import generics.User;
import generics.GenericList;

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
        System.out.println("\ngeneric list with integers:");
        // we can iterate over the items in our GenericList because we implemented Iterable interface
        for (var item:intList)
            System.out.println(item);

        GenericList<User> userList = new GenericList<>();
        userList.add(new User(10));
        userList.add(new User(20));
        userList.add(new User(30));
        System.out.println("\ngeneric list with users:");
        for (var user:userList)
            System.out.println(user);

        // for each loop is just a syntax sugar... it uses iterator.hasNext() and iterator.next() - look at the bytecode

        var list = new GenericList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        var iterator = list.iterator();
        // [a, b, c]
        //  ^
        System.out.println("\ngeneric list with strings:");
        while(iterator.hasNext()){
            var current = iterator.next();
            System.out.println(current);
        }
    }
}
