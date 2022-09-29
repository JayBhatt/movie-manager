package news.MovieManager.persistence.repository;

import news.MovieManager.persistence.entity.Config;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IConfigDao extends IRepositoryAbstract<Config, Long> {
    @Query("SELECT DISTINCT c FROM Config c WHERE c.key = :key AND c.active = :active")
    Optional<Config> findDistinctByKeyAndActive(@Param("key") String key, @Param("active") Boolean active);

}
