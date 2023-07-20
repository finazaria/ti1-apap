package tugas1_singidol_2006482773.singidol.repository;

import tugas1_singidol_2006482773.singidol.model.TipeModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipeDb extends JpaRepository<TipeModel, String> {
    Optional<TipeModel> findById(Long id);
}
