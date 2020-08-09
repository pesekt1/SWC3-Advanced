package streams;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReducersDemo {

    private static List<Movie> createMovies() {
        return Utils.createMovies();
    }

    public static void reducers() {
        var movies = createMovies();


        var result = movies.stream() //TRUE if at least one movie matches the condition
                .anyMatch(m -> m.getLikes() > 20);
        System.out.println(result);

        result = movies.stream() //TRUE if all movies match the condition
                .allMatch(m -> m.getLikes() > 20);
        System.out.println(result);

        result = movies.stream() //TRUE if no movie matches the condition
                .noneMatch(m -> m.getLikes() > 20);
        System.out.println(result);

        var movie = movies.stream()
                .findFirst() //returns Optional<Movie> - it is preventing NullPointerException
                .get();
        System.out.println(movie);

        movie = movies.stream()
                .max((a, b) -> Math.max(a.getLikes(), b.getLikes()))
                .get();
        System.out.println(movie);

        movie = movies.stream()
                .max(Comparator.comparing(Movie::getLikes))
                .get();
        System.out.println(movie);
    }

    public static void reducingStreams() {
        var movies = createMovies();
        var sum = movies.stream()
                .map(m -> m.getLikes())
                .reduce((a, b) -> a + b); // returns Optional<Integer>
        System.out.println(sum.orElse(0));

        var sum2 = movies.stream()
                .map(Movie::getLikes)
                .reduce(0, Integer::sum);
        System.out.println(sum2);
    }

    public static void collectors() {
        var movies = createMovies();

        var moviesList = movies.stream()
                .filter(m -> m.getLikes() > 10)
                .collect(Collectors.toList()); //also toSet
        System.out.println(moviesList);

        var moviesMap = movies.stream()
                .filter(m -> m.getLikes() > 10)
                .collect(Collectors.toMap(m -> m.getTitle(), m -> m.getLikes()));
        System.out.println(moviesMap);

        //storing the whole object in the value
        var moviesMap2 = movies.stream()
                .filter(m -> m.getLikes() > 10)
                .collect(Collectors.toMap(m -> m.getTitle(), m -> m));
        System.out.println(moviesMap2);

        //using Function.identity() to reference the object
        moviesMap2 = movies.stream()
                .filter(m -> m.getLikes() > 10)
                .collect(Collectors.toMap(m -> m.getTitle(), Function.identity()));
        System.out.println(moviesMap2);

        //summingInt - sum of all likes, can be done using reduce() as well
        var sumFiltered = movies.stream()
                .filter(m -> m.getLikes() > 10)
                .collect(Collectors.summingInt(m -> m.getLikes()));

        var concatTitles = movies.stream()
                .filter(m -> m.getLikes() > 10)
                .map(m -> m.getTitle())
                .collect(Collectors.joining(", "));
        System.out.println(concatTitles);
    }

    public static void groupingElements() {
        var movies = createMovies();
        movies.add(new Movie("d", 20, Genre.THRILLER));
        movies.add(new Movie("e", 20, Genre.THRILLER));
        movies.add(new Movie("f", 20, Genre.ACTION));

        var result = movies.stream()
                .collect(Collectors.groupingBy(m -> m.getGenre()));
        System.out.println(result);

        var moviesCount = movies.stream()
                .collect(Collectors.groupingBy(
                        m -> m.getGenre(), Collectors.counting()));
        System.out.println(moviesCount);

        var result2 = movies.stream()
                .collect(Collectors.groupingBy(
                        Movie::getGenre,
                        Collectors.mapping(
                                Movie::getTitle, Collectors.joining(","))));
        System.out.println(result2);
    }


    public static void partitioningElements() {
        // mapping into 2 groups according to the predicate:
        var movies = createMovies();
        var result = movies.stream()
                .collect(Collectors.partitioningBy(m -> m.getLikes() > 15));
        System.out.println(result);

        // mapping into 2 groups according to the predicate, showing only movies count
        var result2 = movies.stream()
                .collect(Collectors.partitioningBy(m -> m.getLikes() > 15,
                        Collectors.counting()));
        System.out.println(result2);
    }

    public static void primitiveTypeStreams() {
        IntStream.rangeClosed(1,5)
                .forEach(System.out::println);

    }
}
