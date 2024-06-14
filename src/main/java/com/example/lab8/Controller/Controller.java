package com.example.lab8.Controller;

        import com.example.lab8.Dao.MovieDao;
        import com.example.lab8.Entity.Movie;
        import com.example.lab8.Entity.User;
        import com.example.lab8.Repository.MovieRepository;
        import com.example.lab8.Repository.UserRepository;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.http.*;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.client.RestTemplate;
        import org.springframework.web.util.UriComponentsBuilder;

        import java.util.*;

@RestController
@RequestMapping("/api")
public class Controller {

   /* private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    //private final RestTemplate restTemplate;

    @Value("${e88e146c70d821fcc6370975b5fe34bc}")
    private String apiKey;

    public Controller(MovieRepository movieRepository, UserRepository userRepository //, RestTemplate restTemplate
    ) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        //this.restTemplate = restTemplate;
    }*/

   /* public String callApiWithBarerTokenAndParam(String baseUrl,String paramName, String paramValue, String token){

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer" +token);
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam(paramName,paramValue)
                .toUriString();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
*/

    private final MovieDao movieDao;

    public Controller(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @GetMapping("/listarPeliculasEnCines")
    public ResponseEntity<HashMap<String, Object>> listarPeliculasEnCines() {
        HashMap<String, Object> response = new HashMap<>();
        List<Map<String, Object>> listaPeliculas = movieDao.listarPeliculasEnCines();
        response.put("content", listaPeliculas);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/agregarPeliculaFavorita")
    public ResponseEntity<HashMap<String, Object>> agregarPeliculaFavorita(
            @RequestParam(value = "nombrePelicula", required = false) String nombrePelicula,
            @RequestParam(value = "nombreUsuario", required = false) String nombreUsuario) {
        HashMap<String, Object> response = new HashMap<>();
        HashMap<String, Object> errors = new HashMap<>();
        boolean validacion = true;

        if (nombrePelicula == null || nombrePelicula.isEmpty()) {
            response.put("status", "error");
            errors.put("nombrePelicula", "Ingrese el nombre de la película");
            validacion = false;
        }
        if (nombreUsuario == null || nombreUsuario.isEmpty()) {
            response.put("status", "error");
            errors.put("nombreUsuario", "Ingrese el nombre del usuario");
            validacion = false;
        }
        if (validacion) {
            boolean success = movieDao.agregarPeliculaFavorita(nombrePelicula, nombreUsuario);

            if (success) {
                response.put("status", "success");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                response.put("status", "error");
                errors.put("pelicula", "La película ingresada no existe");
                response.put("errors", errors);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } else {
            response.put("errors", errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}

