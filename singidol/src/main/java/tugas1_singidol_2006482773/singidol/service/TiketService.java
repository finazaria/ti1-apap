package tugas1_singidol_2006482773.singidol.service;
import java.util.List;

import tugas1_singidol_2006482773.singidol.model.TiketModel;


public interface TiketService {
    void addTiket(TiketModel tiket);
    void deleteTiket(TiketModel tiket);
    List<TiketModel> getListTiket();
    TiketModel getTiketbyId(Long id);
}
