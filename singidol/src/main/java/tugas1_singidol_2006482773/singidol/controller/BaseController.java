package tugas1_singidol_2006482773.singidol.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Component
public class BaseController {
    @GetMapping("/")
    private String Home() {
        return "home";
    }
}
