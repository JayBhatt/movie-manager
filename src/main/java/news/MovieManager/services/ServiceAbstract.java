package news.MovieManager.services;

import java.util.List;
import java.util.Optional;

public interface ServiceAbstract<T> {

    Optional<T> findByIdAndActive(long id, Boolean active);

    List<T> findAllByActive(Boolean active);

    T save(T entity);
}
