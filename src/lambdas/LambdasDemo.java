package lambdas;

import java.util.List;
import java.util.function.*;

public class LambdasDemo {
    public String prefix = "-";

    //constructor
    public LambdasDemo(String message) {

    }

    public static void anonymousInnerClass() {
        //using an implementation
        LambdasDemo.greet(new ConsolePrinter());

        //using anonymous inner class
        LambdasDemo.greet(new Printer() {
            @Override
            public void print(String message) {
                System.out.println(message);
            }
        });
    }

    public static void lambdaSyntax() {
        //using lambda expression
        greet((String message) -> {
            System.out.println(message);
        });

        //using lambda expression - shorter syntax
        greet(message -> System.out.println(message));

        //lambda expression is an object:
        //Printer printer2 = new ConsolePrinter();
        Printer printer = message -> System.out.println(message);
        greet(printer);
    }

    public void variableCapture() {
        //variable capture
        String prefix = "-";
        greet(message -> System.out.println(prefix + message));

        //this references to enclosing object - in this case LambdaDemo
        greet(message -> System.out.println(this.prefix + message));
    }

    public static void methodReferencing() {
        //method reference syntax
        greet(System.out::println);

        greet(LambdasDemo::print);

        var demo = new LambdasDemo("Hello");
        greet(demo::print2); //because print2() is not static

        //reference to constructor:
        //greet(message -> new LambdasDemo(message))
        greet(LambdasDemo::new);
    }

    public static void print(String message) {
        System.out.println(message);
    }

    public void print2(String message) {
        System.out.println(message);
    }

    public static void greet(Printer printer) {
        printer.print("Hello!");
    }

    public static void consumerInterface() {
        List<Integer> list = List.of(1, 2, 3);
        list.forEach(item -> System.out.println(item)); //forEach takes a consumer

        //chaining consumers:
        List<String> stringList = List.of("a", "b", "c");
        Consumer<String> print = item -> System.out.println(item);
        Consumer<String> printUppercase = item -> System.out.println(item.toUpperCase());
        stringList.forEach(print.andThen(printUppercase)); //chaining consumer
    }

    public static void supplierInterface() {
        Supplier<Double> getRandom = () -> Math.random(); //lazy evaluation - its only a reference
        var random = getRandom.get(); // here we call the get() method
        System.out.println(random);
    }

    public static void functionInterface() {
        Function<String, Integer> map = str -> str.length();
        var length = map.apply("Testing string");
        System.out.println(length);

        //composing functions:
        //start: "key:value"
        //first function: "key=value"
        //second function:"{key=value}"
        Function<String, String> replaceColon = str -> str.replace(":", "=");
        Function<String, String> addBraces = str -> "{" + str + "}";
        var result = replaceColon.andThen(addBraces).apply("key:column");
        System.out.println(result);
    }

    public static void predicateInterface() {
        Predicate<String> isLongerThan5 = str -> str.length() > 5;
        var result = isLongerThan5.test("sky");
        System.out.println(result);

        //combining predicates:
        Predicate<String> hasLeftBrace = str -> str.startsWith("{");
        Predicate<String> hasRightBrace = str -> str.endsWith("}");
        result = hasLeftBrace.and(hasRightBrace).test("{testing}");
        System.out.println(result);
    }

    public static void binaryOperatorInterface() {
        BinaryOperator<Integer> add = (a, b) -> a + b;
        var result = add.apply(1, 2);

        //combining with Function operator: (a,b) -> (a + b) -> square
        Function<Integer, Integer> square = a -> a * a;
        result = add.andThen(square).apply(1,2);
        System.out.println(result);
    }

    public static void unaryOperatorInterface() {
        UnaryOperator<Integer> square = n -> n * n;
        UnaryOperator<Integer> increment = n -> n + 1;

        var result = increment.andThen(square).apply(1);
        System.out.println(result);
    }


}
