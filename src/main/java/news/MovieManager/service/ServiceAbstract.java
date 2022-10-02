package news.MovieManager.service;

import java.util.List;

public interface ServiceAbstract<T> {

    T findByIdAndActive(long id, Boolean active);

    List<T> findAllByActive(Boolean active);

    T save(T entity);
}
