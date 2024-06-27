package hey.io.hey.domain.performance.repository;

import hey.io.hey.domain.performance.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, String> {

    Optional<Place> findById(String id);

}
