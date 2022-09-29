package news.MovieManager.persistence.repository;

import news.MovieManager.persistence.entity.EntityAbstract;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface IRepositoryAbstract<E extends EntityAbstract, T extends Serializable> extends CrudRepository<E, T> {

    Optional<E> findByIdAndActive(Long id, Boolean active);

    List<E> findAllByActive(Boolean active);

}
