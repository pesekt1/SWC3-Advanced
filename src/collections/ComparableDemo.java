package collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComparableDemo {
    public static void show() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("c", "email1"));
        customers.add(new Customer("b", "email2"));
        customers.add(new Customer("a", "email3"));
        System.out.println(customers);
        Collections.sort(customers);
        System.out.println("sorted by name: " + customers);
        Collections.sort(customers, new EmailComparator()); //we use Comparator object
        System.out.println("sorted by email: " + customers);
    }
}
