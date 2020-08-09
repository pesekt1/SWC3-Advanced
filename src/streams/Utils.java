package streams;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<Movie> createMovies(){

        var list = new ArrayList<Movie>();
        list.add(new Movie("a", 10, Genre.COMEDY));
        list.add(new Movie("b", 15, Genre.COMEDY));
        list.add(new Movie("c", 20, Genre.COMEDY));

        return list;
    }
}
