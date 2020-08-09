package streams;

import org.w3c.dom.ls.LSOutput;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class StreamsDemo {

    private static List<Movie> createMovies() {
        return Utils.createMovies();
    }

    public static void programmingTypes() {
        var movies = createMovies();

        //imperative programming - statements about how things should be done
        int count = 0;
        for (var movie : movies) {
            if (movie.getLikes() > 10)
                count++;
        }

        //functional programming (special type of declarative programming):
        var count2 = movies.stream()
                .filter(movie -> movie.getLikes() > 10)
                .count();
        System.out.println(count);
    }

    public static void mappingElements() {
        var movies = createMovies();

        movies.stream()
                .map(movie -> movie.getTitle())
                .forEach(name -> System.out.println(name));

        movies.stream()
                .mapToInt(movie -> movie.getLikes())
                .forEach(like -> System.out.println(like));

        //stream of List<Integer>
        var stream = Stream.of(List.of(1, 2, 3), List.of(4, 5, 6));
        stream.forEach(list -> System.out.println(list));

        //flatMap:
        stream = Stream.of(List.of(1, 2, 3), List.of(4, 5, 6));
        stream
                .flatMap(list -> list.stream())
                .forEach(item -> System.out.println(item));

    }

    public static void slicingStream() {
        var movies = createMovies();

        movies.stream()
                .limit(2)
                .forEach(movie -> System.out.println(movie.getTitle()));

        movies.stream()
                .skip(1)
                .forEach(movie -> System.out.println(movie.getTitle()));

        //pagination:
        var page = 2;
        var pageSize = 2;
        movies.stream()
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .forEach(movie -> System.out.println(movie.getTitle()));

        movies.stream()
                .takeWhile(movie -> movie.getLikes() > 15)
                .forEach(movie -> System.out.println(movie.getTitle()));

        movies.stream()
                .dropWhile(movie -> movie.getLikes() < 15)
                .forEach(movie -> System.out.println(movie.getTitle()));
    }

    public static void sortingStreams() {
        var movies = createMovies();

        movies.stream()
                .sorted((a, b) -> a.getTitle().compareTo(b.getTitle()))
                .forEach(m -> System.out.println(m.getTitle()));

        movies.stream()
                .sorted(Comparator.comparing(m -> m.getTitle()))
                .forEach(m -> System.out.println(m.getTitle()));

        movies.stream()
                .sorted(Comparator.comparing(Movie::getTitle))
                .forEach(m -> System.out.println(m.getTitle()));

        //reversed sorting:
        movies.stream()
                .sorted(Comparator.comparing(Movie::getTitle).reversed())
                .forEach(m -> System.out.println(m.getTitle()));
    }

    public static void gettingUnique() {
        var movies = createMovies();
        movies.add(new Movie("a", 10, Genre.COMEDY)); //add repetitive values
        movies.add(new Movie("a", 10, Genre.COMEDY)); //add repetitive values

        movies.stream()
                .map(m -> m.getLikes())
                .distinct() // only unique likes
                .forEach(like -> System.out.println(like));
    }

    public static void peekingElements() {
        var movies = createMovies();

        movies.stream()
                .filter(m -> m.getLikes() > 10)
                .peek(m -> System.out.println("filtered: " + m))
                .map(m -> m.getTitle())
                .peek(title -> System.out.println("mapped: " + title))
                .forEach(title -> System.out.println(title));
    }
}


