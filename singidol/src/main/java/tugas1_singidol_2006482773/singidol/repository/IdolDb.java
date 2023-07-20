package tugas1_singidol_2006482773.singidol.repository;

import tugas1_singidol_2006482773.singidol.model.IdolModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdolDb extends JpaRepository<IdolModel, String> {
    // JPA
    Optional<IdolModel> findById(Long id);
}
