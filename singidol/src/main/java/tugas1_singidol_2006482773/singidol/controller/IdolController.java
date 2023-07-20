package tugas1_singidol_2006482773.singidol.controller;

import tugas1_singidol_2006482773.singidol.model.IdolModel;
import tugas1_singidol_2006482773.singidol.repository.IdolDb;
import tugas1_singidol_2006482773.singidol.service.IdolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IdolController {
    @Qualifier("idolServiceImpl")
    @Autowired
    private IdolService idolService;

    // Fitur 10 : Menampilkan daftar idol
    @GetMapping("/idol")
    public String listIdol(Model model) {
        List<IdolModel> listIdol = idolService.getListIdol();
        model.addAttribute("listIdol", listIdol);
        return "viewall-idol";
    }

    // Fitur 11 : add Idol
    //Routing URL yang diinginkan
    @RequestMapping("/idol/tambah")
    public String addIdol(Model model) {
        model.addAttribute("idol", new IdolModel());

        return "form-add-idol";
    }

    @PostMapping("/idol/tambah")
    public String addIdolSubmitPage(@ModelAttribute IdolModel idol, Model model) {
        // Tambah idol nnti update idol list juga
        idolService.addIdol(idol);
        model.addAttribute("namaIdol", idol.getNamaIdol());       // Karena nama idol yang mau ditampilin di page nya

        return "add-idol";
    }

    // Fitur 12 : Menampilkan detail idol
    // Ngakses html nya pake => Path Variable
    @GetMapping("/idol/{id}")
    public String viewDetailIdolPage(@PathVariable Long id, Model model) {
        IdolModel idol = idolService.getIdolbyId(id);
        model.addAttribute("idol", idol);

        return "view-idol";
    }
}
