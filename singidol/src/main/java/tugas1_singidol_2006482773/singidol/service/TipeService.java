package tugas1_singidol_2006482773.singidol.service;
import java.util.List;

import tugas1_singidol_2006482773.singidol.model.TipeModel;

public interface TipeService {
    List<TipeModel> getListTipe();
    TipeModel getTipeById(Long id);
}
