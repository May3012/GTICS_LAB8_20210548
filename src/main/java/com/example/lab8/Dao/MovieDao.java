package com.example.lab8.Dao;

import com.example.lab8.Entity.Movie;
import com.example.lab8.Entity.User;
import com.example.lab8.Repository.MovieRepository;
import com.example.lab8.Repository.UserRepository;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class MovieDao {

    @Value("${e88e146c70d821fcc6370975b5fe34bc}")
    private String apiKey;

    public MovieDao(MovieRepository movieRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public List<Map<String, Object>> listarPeliculasEnCines() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + apiKey;
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            return (List<Map<String, Object>>) response.getBody().get("results");
        } catch (HttpClientErrorException e) {
            return Collections.emptyList();
        }
    }

    public boolean agregarPeliculaFavorita(String nombrePelicula, String nombreUsuario) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&query=" + nombrePelicula;

        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            List<Map<String, Object>> results = (List<Map<String, Object>>) response.getBody().get("results");

            if (results == null || results.isEmpty()) {
                return false;
            }

            Map<String, Object> movieData = results.get(0);

            Optional<User> optUser = userRepository.findByNombre(nombreUsuario);
            User user;

            if (optUser.isEmpty()) {
                user = new User();
                user.setNombre(nombreUsuario);
                userRepository.save(user);
            } else {
                user = optUser.get();
            }

            Movie movie = new Movie();
            movie.setId((Integer) movieData.get("id"));
            movie.setNombre((String) movieData.get("title"));
            movie.setOverview((String) movieData.get("overview"));
            movie.setPopularidad((Double) movieData.get("popularity"));
            movie.setFechaLanzamiento((String) movieData.get("release_date"));
            movie.setIdUser(user);

            movieRepository.save(movie);

            return true;

        } catch (HttpClientErrorException e) {
            return false;
        }
    }
}
