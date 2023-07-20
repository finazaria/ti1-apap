package tugas1_singidol_2006482773.singidol.service;
import tugas1_singidol_2006482773.singidol.model.KonserModel;

import java.util.List;

public interface KonserService {
    void addKonser(KonserModel konser);
    void updateKonser(KonserModel konser);
    List<KonserModel> getListKonser();
    KonserModel getKonserbyId(Long id);
    List<KonserModel> filterKonser(Float pendapatanMinimal, Long idIdol);
}
