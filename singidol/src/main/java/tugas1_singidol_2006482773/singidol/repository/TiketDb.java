package tugas1_singidol_2006482773.singidol.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tugas1_singidol_2006482773.singidol.model.TiketModel;

@Repository
public interface TiketDb extends JpaRepository<TiketModel, String> {
    Optional<TiketModel> findById(Long id);
}
