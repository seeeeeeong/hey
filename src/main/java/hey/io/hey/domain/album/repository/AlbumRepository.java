package hey.io.hey.domain.album.repository;

import hey.io.hey.domain.album.domain.AlbumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, String>, AlbumQueryRepository {
}
