package news.MovieManager.vendor.imdb.controller;

import news.MovieManager.vendor.imdb.helper.ConfigHelper;
import news.MovieManager.vendor.imdb.model.GenreList;
import news.MovieManager.vendor.imdb.model.MovieList;
import news.MovieManager.vendor.imdb.service.ImdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private final ImdbService imdbService;
    private final ConfigHelper configHelper;

    @Autowired
    public MovieController(ImdbService imdbService, ConfigHelper configHelper) {
        this.imdbService = imdbService;
        this.configHelper = configHelper;
    }

    @RequestMapping(value = "/popular/{page}")
    @ResponseBody
    public ResponseEntity<MovieList> getPopularMovies(@PathVariable("page") Integer page) {
        return ResponseEntity.ok(this.imdbService.getPopular(page, this.configHelper.getConfig()));
    }

    @RequestMapping(value = "/genre/{movieId}")
    @ResponseBody
    public ResponseEntity<GenreList> getGenre(@PathVariable("movieId") Long movieId) {
        return ResponseEntity.ok(this.imdbService.getGenre(movieId, this.configHelper.getConfig()));
    }

}
