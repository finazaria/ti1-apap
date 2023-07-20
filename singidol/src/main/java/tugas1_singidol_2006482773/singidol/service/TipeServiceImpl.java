package tugas1_singidol_2006482773.singidol.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tugas1_singidol_2006482773.singidol.model.TipeModel;
import tugas1_singidol_2006482773.singidol.repository.TipeDb;

@Service
@Transactional
public class TipeServiceImpl implements TipeService {
    @Autowired
    TipeDb tipeDb;

    @Override
    public List<TipeModel> getListTipe() {
        return tipeDb.findAll();
    }

    @Override
    public TipeModel getTipeById(Long id) {
        Optional<TipeModel> tipe = tipeDb.findById(id);
        if (tipe.isPresent()) return tipe.get();
        else return null;
    }

    
    
}
