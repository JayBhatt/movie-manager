package news.MovieManager.vendor.imdb.service;

import lombok.extern.slf4j.Slf4j;
import news.MovieManager.vendor.imdb.exception.ImdbException;
import news.MovieManager.vendor.imdb.model.GenreList;
import news.MovieManager.vendor.imdb.model.ImdbConfig;
import news.MovieManager.vendor.imdb.model.MovieList;
import news.MovieManager.vendor.imdb.model.RequestType;
import news.MovieManager.vendor.imdb.model.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class ImdbService {

    private static final boolean INCLUDE_ADULT = false;
    private static final boolean INCLUDE_VIDEO = false;

    public MovieList getPopular(Integer page, ImdbConfig config) throws ImdbException {
        Flux<MovieList> movieList;
        try {
            /**
             * @NOTE: Retries n number of times on failure, the value of n comes from db.
             */
            final Integer queryPage = page != null ? page : 1;
            movieList = WebClient.create(config.getBaseUrl()).get().
                    uri(uriBuilder ->
                        uriBuilder.path(RequestType.POPULAR.getType())
                        .queryParam("region", config.getRegion())
                        .queryParam("sort_by", Sort.POPULAR_DESC)
                        .queryParam("include_adult", ImdbService.INCLUDE_ADULT)
                        .queryParam("include_video", ImdbService.INCLUDE_VIDEO)
                        .queryParam("primary_release_year", (LocalDate.now()).getYear())
                        .queryParam("page", queryPage)
                        .queryParam("api_key", config.getKey())
                        .build()
                    )
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new ImdbException("Service Unavailable")))
                    .bodyToFlux(MovieList.class)
                    .retryWhen(Retry.backoff(Long.parseLong(config.getMaxRetryCount()), Duration.ofSeconds(Long.parseLong(config.getMaxRetryDelay())))
                    .filter(throwable -> throwable instanceof ImdbException)
                    .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                        throw new ImdbException("Internal server error");
                    }));
        } catch (Exception ex) {
            log.error("An error occurred while fetching movie list. Error: {}, Trace: {}", ex.getMessage(), ex.getStackTrace());
            throw new ImdbException(ex.getMessage());
        }
        List<MovieList> flattenList = movieList.collectList().block();
        return (flattenList != null && flattenList.size() > 0) ? flattenList.get(0) : null;
    }

    public GenreList getGenre(Long movieId, ImdbConfig config) throws ImdbException {
        Flux<GenreList> genreList;
        try {
            /**
             * @NOTE: Does not retry on failure as this is just partial data.
             */
            genreList = WebClient.create(config.getBaseUrl()).get().
                    uri(uriBuilder ->
                            uriBuilder.path(RequestType.MOVIE_DETAIL.getType())
                                    .queryParam("api_key", config.getKey())
                                    .build(movieId)
                    )
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToFlux(GenreList.class);
        } catch (Exception ex) {
            log.error("An error occurred while fetching details of the movie. Error: {}, Trace: {}", ex.getMessage(), ex.getStackTrace());
            throw new ImdbException(ex.getMessage());
        }
        List<GenreList> flattenList = genreList.collectList().block();
        return (flattenList != null && flattenList.size() > 0) ? flattenList.get(0) : null;
    }

}
