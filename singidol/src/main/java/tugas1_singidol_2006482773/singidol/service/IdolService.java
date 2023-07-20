package tugas1_singidol_2006482773.singidol.service;

import tugas1_singidol_2006482773.singidol.model.IdolModel;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface IdolService {
    // Fitur 10 : Get List Idol
    List<IdolModel> getListIdol();

    // Fitur 11 : add Idol
    void addIdol(IdolModel idol);

    // Fitur 12 : find Idol by id
    IdolModel getIdolbyId(Long id);
}
