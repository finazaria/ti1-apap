package tugas1_singidol_2006482773.singidol.service;

import tugas1_singidol_2006482773.singidol.model.KonserModel;
import tugas1_singidol_2006482773.singidol.model.PenampilanKonserModel;
import tugas1_singidol_2006482773.singidol.repository.KonserDb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class KonserServiceImpl implements KonserService{
    @Autowired
    KonserDb konserDb;

    @Override
    public void addKonser(KonserModel konser) {
        konserDb.save(konser);
    }

    @Override
    public void updateKonser(KonserModel konser) {
        konserDb.save(konser);
    }

    @Override
    public List<KonserModel> getListKonser() {
        return konserDb.findAll();
    }

    @Override
    public KonserModel getKonserbyId(Long id) {
        Optional<KonserModel> konser = konserDb.findById(id);
        if (konser.isPresent())
            return konser.get();
        else
            return null;
    }

    @Override
    public List<KonserModel> filterKonser(Float pendapatanMinimal, Long idIdol) {
        List<KonserModel> listFilteredKonser = new ArrayList<>();

        List<KonserModel> listKonser = konserDb.findAll();

        for (KonserModel konser : listKonser) {
            List<PenampilanKonserModel> listPenampilan = konser.getListPenampilan();
            for (PenampilanKonserModel penampilan : listPenampilan) {
                if (penampilan.getIdIdol().getId().equals(idIdol) & penampilan.getIdKonser().getTotalPendapatan().floatValue() >= pendapatanMinimal) {
                    listFilteredKonser.add(penampilan.getIdKonser());
                }
            }
        }
        return listFilteredKonser;
    }

    
    
}
