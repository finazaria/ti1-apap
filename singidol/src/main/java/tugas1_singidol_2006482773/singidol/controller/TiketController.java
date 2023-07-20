package tugas1_singidol_2006482773.singidol.controller;

import tugas1_singidol_2006482773.singidol.model.KonserModel;
import tugas1_singidol_2006482773.singidol.model.TiketModel;
import tugas1_singidol_2006482773.singidol.model.TipeModel;
import tugas1_singidol_2006482773.singidol.repository.TiketDb;
import tugas1_singidol_2006482773.singidol.service.KonserService;
import tugas1_singidol_2006482773.singidol.service.TiketService;
import tugas1_singidol_2006482773.singidol.service.TipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TiketController {
    @Qualifier("tiketServiceImpl")
    @Autowired
    private TiketService tiketService;

    @Qualifier("tipeServiceImpl")
    @Autowired
    private TipeService tipeService;

    @Qualifier("konserServiceImpl")
    @Autowired
    private KonserService konserService;


    // Fitur 6 : view all tiket
    @GetMapping("/tiket")
    public String listTiket(Model model){
        List<TiketModel> listTiket = tiketService.getListTiket();
        model.addAttribute("listTiket", listTiket);

        return "viewall-tiket";
    }

    // Fitur 8 : Add Tiket
    @GetMapping("/tiket/pesan")
    public String addTiketFormPage(Model model){
        TiketModel tiket = new TiketModel();
        List<TipeModel> listTipe = tipeService.getListTipe();
        List<KonserModel> listKonser = konserService.getListKonser();
        
        model.addAttribute("listTipe", listTipe);
        model.addAttribute("listKonser", listKonser);
        model.addAttribute("tiket", tiket);
        return "form-add-tiket";
    }


    @PostMapping(value = "/tiket/pesan")
    public String addTiketSubmit(@ModelAttribute TiketModel tiket, Model model) {
        // Set Konser
        tiket.setIdKonser(konserService.getKonserbyId(tiket.getIdKonser().getId()));
        
        // Set Tipe
        tiket.setIdTipe(tipeService.getTipeById(tiket.getIdTipe().getId()));
        // Di set gini biar connected
        
        tiketService.addTiket(tiket);
        model.addAttribute("nomorTiket", tiket.getNomorTiket());
        model.addAttribute("namaKonser", tiket.getIdKonser().getNamaKonser());
        return "add-tiket";
    }

    // Fitur 7 : Menampilkan detail tiket
    // Ngakses html nya pake => Path Variable
    @GetMapping("/tiket/{id}")
    public String viewDetailTiketPage(@PathVariable Long id, Model model) {
        TiketModel tiket = tiketService.getTiketbyId(id);
        model.addAttribute("tiket", tiket);

        return "view-tiket";
    }

    // Fitur 9 : Delete Tiket
    @PostMapping("/tiket/hapus/{id}")
    public String deleteTiketSubmit(@PathVariable Long id, Model model){
        TiketModel tiket = tiketService.getTiketbyId(id);
        model.addAttribute("tiket", tiket);
        // Di oper dulu var nya baru di delete
        
        tiketService.deleteTiket(tiket);

        return "delete-tiket";
    }

}
