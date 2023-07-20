package tugas1_singidol_2006482773.singidol.repository;
import tugas1_singidol_2006482773.singidol.model.PenampilanKonserModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PenampilanKonserDb extends JpaRepository<PenampilanKonserModel, String>{
    Optional<PenampilanKonserModel> findById(Long id);
}
