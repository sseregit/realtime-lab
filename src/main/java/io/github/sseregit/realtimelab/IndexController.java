package io.github.sseregit.realtimelab;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    String index(Model model) {
        model.addAttribute("message", "RealTimeLab");
        return "index";
    }

    @GetMapping("/longpolling-client")
    String longpollingClient(Model model) {
        model.addAttribute("message", "RealTimeLab");
        return "longpolling-client";
    }
}
