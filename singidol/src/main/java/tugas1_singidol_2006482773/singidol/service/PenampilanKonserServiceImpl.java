package tugas1_singidol_2006482773.singidol.service;

import tugas1_singidol_2006482773.singidol.model.KonserModel;
import tugas1_singidol_2006482773.singidol.model.PenampilanKonserModel;
import tugas1_singidol_2006482773.singidol.repository.PenampilanKonserDb;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PenampilanKonserServiceImpl implements PenampilanKonserService{
    @Autowired
    PenampilanKonserDb penampilanKonserDb;

    @Override
    public void emptyKonser(KonserModel konser) {
        List<PenampilanKonserModel> listAllPenampilanKonserModel = penampilanKonserDb.findAll();

        for (PenampilanKonserModel penampilan : listAllPenampilanKonserModel) {
            if (penampilan.getIdKonser().getId().equals(konser.getId())) {
                penampilanKonserDb.delete(penampilan);
            }
        }
        
    }
    
}
