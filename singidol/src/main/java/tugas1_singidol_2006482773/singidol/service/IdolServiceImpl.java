package tugas1_singidol_2006482773.singidol.service;

import tugas1_singidol_2006482773.singidol.model.IdolModel;
import tugas1_singidol_2006482773.singidol.repository.IdolDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IdolServiceImpl implements IdolService {
    @Autowired
    IdolDb idolDb;

    // Fitur 10 : Nampilin daftar idol
    @Override
    public List<IdolModel> getListIdol() {
        return idolDb.findAll();
    }

    // Fitur 11 : add Idol
    @Override
    public void addIdol(IdolModel idol) {
        idolDb.save(idol);
    }

    @Override
    public IdolModel getIdolbyId(Long id) {
        Optional<IdolModel> idol = idolDb.findById(id);
        return idol.get();
    }
    
}
