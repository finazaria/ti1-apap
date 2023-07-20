package tugas1_singidol_2006482773.singidol.controller;

import tugas1_singidol_2006482773.singidol.model.IdolModel;
import tugas1_singidol_2006482773.singidol.model.KonserModel;
import tugas1_singidol_2006482773.singidol.model.PenampilanKonserModel;
import tugas1_singidol_2006482773.singidol.service.IdolService;
import tugas1_singidol_2006482773.singidol.service.KonserService;
import tugas1_singidol_2006482773.singidol.service.PenampilanKonserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Controller
public class KonserController {
    @Qualifier("konserServiceImpl")
    @Autowired
    private KonserService konserService;

    @Qualifier("idolServiceImpl")
    @Autowired
    private IdolService idolService;

    @Qualifier("penampilanKonserServiceImpl")
    @Autowired
    private PenampilanKonserService penampilanKonserService;

    //Routing URL yang diinginkan
    @RequestMapping("/konser/tambah")
    public String addKonser(Model model) {
        KonserModel konser = new KonserModel();

        // Get list idol yang ada
        List<IdolModel> listIdol = idolService.getListIdol();

        // Create new list
        List<PenampilanKonserModel> listPenampilanNew = new ArrayList<>();

        konser.setListPenampilan(listPenampilanNew);
        konser.getListPenampilan().add(new PenampilanKonserModel());
        
        model.addAttribute("listIdol", listIdol);
        model.addAttribute("listPenampilan", listPenampilanNew);
        model.addAttribute("konser", konser);

        return "form-add-konser";
    }

    @PostMapping(value = "/konser/tambah", params = {"addRowIdol"})
    private String addRowIdolMultiple(
            @ModelAttribute KonserModel konser,
            Model model
    ) {
        if (konser.getListPenampilan() == null || konser.getListPenampilan().size() == 0) {
            List<PenampilanKonserModel> listPenampilanNew = new ArrayList<>();
            konser.setListPenampilan(listPenampilanNew);
        }
        // Kalo udah ada
        PenampilanKonserModel newPenampilanKonser = new PenampilanKonserModel();
        konser.getListPenampilan().add(newPenampilanKonser);
        // newPenampilanKonser.setJamMulaiTampil(konser);
        List<IdolModel> listIdol = idolService.getListIdol();

        model.addAttribute("konser", konser);
        model.addAttribute("listIdol", listIdol);
        // model.addAttribute("listPenampilan", konser.getListPenampilan());
        return "form-add-konser";
    }

    @PostMapping(value = "/konser/tambah", params = {"deleteRowIdol"})
    private String deleteRowIdolMultiple(
            @ModelAttribute KonserModel konser,
            @RequestParam("deleteRowIdol") Integer row,
            Model model
    ) {
        final Integer rowId = Integer.valueOf(row);
        konser.getListPenampilan().remove(rowId.intValue());

        List<PenampilanKonserModel> listPenampilan = konser.getListPenampilan();

        // Untuk opsi iteration dropdown
        List<IdolModel> listIdol = idolService.getListIdol();

        model.addAttribute("konser", konser);
        model.addAttribute("listIdol", listIdol);
        model.addAttribute("listPenampilan", listPenampilan);

        return "form-add-konser";
    }

    @PostMapping("/konser/tambah")
    public String addKonserSubmitPage(@ModelAttribute KonserModel konser, Model model) {
        // Before you add the concert. Penampilannya dulu yg di set ke konser itu
        // Karena kalo engga, penampilannya gapunya tujuan (id_konser)
        for (PenampilanKonserModel penampilan : konser.getListPenampilan()) {
            penampilan.setIdKonser(konser);
        }
        konser.setTotalPendapatan(BigInteger.ZERO);     // Nge-set totalPendapatan awal
        konserService.addKonser(konser);
        model.addAttribute("namaKonser", konser.getNamaKonser());
        return "add-konser";
    }

    // Fitur 4 : View Detail Konser
    @GetMapping("/konser/{id}")
    public String viewDetailKonserPage(@PathVariable Long id, Model model) {
        KonserModel konser = konserService.getKonserbyId(id);
        List<PenampilanKonserModel> listPenampilan = konser.getListPenampilan();
        
        model.addAttribute("listPenampilan", listPenampilan);
        model.addAttribute("konser", konser);

        return "view-konser";
    }

    // Fitur 13 : Filter Konser
    @GetMapping("/konser/cari")
    public String filterKonserFormPage(Model model) {
        // We need listIdol untuk ditampilin di dropdown menu nya
        List<IdolModel> listIdol = idolService.getListIdol();
        model.addAttribute("listIdol", listIdol);

        return "form-filter-konser";
    }
    // Nge pass pendapatan, dan idol yg kita dapat
    @GetMapping("/carikonser")
    public String filterKonserSubmit(@RequestParam(value = "pendapatan_minimal", required = true) Float pendapatanMinimal, 
                                    @RequestParam(value = "idol", required = true) Long idIdol, 
                                    Model model) {

        List<KonserModel> listFilteredKonser = konserService.filterKonser(pendapatanMinimal, idIdol);

        // We also have to show selected idol and pendapatanMinimal
        model.addAttribute("selectedIdolId", idIdol);
        model.addAttribute("pendapatanMinimal", BigInteger.valueOf(pendapatanMinimal.longValue()));
        
        List<IdolModel> listIdol = idolService.getListIdol();
        model.addAttribute("listIdol", listIdol);
        model.addAttribute("listFilteredKonser", listFilteredKonser);
        return "view-filter-konser";
    }

    // Fitur 5 : Update Konser
    @GetMapping("/konser/ubah/{id}")
    public String updateKonserFormPage(@PathVariable Long id, Model model) {
        KonserModel konser = konserService.getKonserbyId(id);
        List<PenampilanKonserModel> listPenampil = konser.getListPenampilan();
        List<IdolModel> listIdol = idolService.getListIdol();

        model.addAttribute("konser", konser);
        model.addAttribute("listPenampil", listPenampil);
        model.addAttribute("listIdol", listIdol);

        return "form-update-konser";
    }

    @PostMapping(value = "/konser/ubah/{id}", params = {"addRowIdol"})
    private String addRowIdolMultipleUpdate(
            @ModelAttribute KonserModel konser,
            Model model
    ) {
        if (konser.getListPenampilan() == null || konser.getListPenampilan().size() == 0) {
            List<PenampilanKonserModel> listPenampilanNew = new ArrayList<>();
            konser.setListPenampilan(listPenampilanNew);
        }
        // Kalo udah ada
        PenampilanKonserModel newPenampilanKonser = new PenampilanKonserModel();
        konser.getListPenampilan().add(newPenampilanKonser);
        // newPenampilanKonser.setJamMulaiTampil(konser);
        List<IdolModel> listIdol = idolService.getListIdol();

        model.addAttribute("konser", konser);
        model.addAttribute("listIdol", listIdol);
        model.addAttribute("listPenampilan", konser.getListPenampilan());
        return "form-update-konser";
    }

    @PostMapping(value = "/konser/ubah/{id}", params = {"deleteRowIdol"})
    private String deleteRowIdolMultipleUpdate(
            @ModelAttribute KonserModel konser,
            @RequestParam("deleteRowIdol") Integer row,
            Model model
    ) {
        final Integer rowId = Integer.valueOf(row);
        konser.getListPenampilan().remove(rowId.intValue());

        List<PenampilanKonserModel> listPenampilan = konser.getListPenampilan();

        // Untuk opsi iteration dropdown
        List<IdolModel> listIdol = idolService.getListIdol();

        model.addAttribute("konser", konser);
        model.addAttribute("listIdol", listIdol);
        model.addAttribute("listPenampilan", listPenampilan);

        return "form-update-konser";
    }

    @PostMapping(value = "/konser/ubah/{id}", params = {"save"})
    public String updateKonserSubmitPage(@PathVariable Long id, 
                                        @ModelAttribute KonserModel konser, 
                                        Model model) {
        // Kalo blm punya listPenampilan => make a new one
        if (konser.getListPenampilan() == null) {
            List<PenampilanKonserModel> listPenampilan = new ArrayList<>();
            konser.setListPenampilan(listPenampilan);
        }
        // Kalo udah ada, kosongin dulu
        penampilanKonserService.emptyKonser(konser);

        // Siapa aja yang terpengaruh??
        for (PenampilanKonserModel penampilan : konser.getListPenampilan()) {
            penampilan.setIdKonser(konser);
        }

        KonserModel updatedKonser = konserService.getKonserbyId(id);
        updatedKonser.setNamaKonser(konser.getNamaKonser());
        updatedKonser.setWaktu(konser.getWaktu());
        updatedKonser.setTempat(konser.getTempat());
        // updatedKonser.setTotalPendapatan(konser.getTotalPendapatan());
        updatedKonser.setListPenampilan(konser.getListPenampilan());

        konserService.updateKonser(updatedKonser);
        model.addAttribute("konser", updatedKonser);

        return "update-konser";
    }

    // viewAll Konser
    @GetMapping("/konser")
    public String listKonser(Model model){
        List<KonserModel> listKonser = konserService.getListKonser();
        model.addAttribute("listKonser", listKonser);

        return "viewall-konser";
    }


}
