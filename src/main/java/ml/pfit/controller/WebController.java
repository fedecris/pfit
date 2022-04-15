package ml.pfit.controller;

import ml.pfit.dto.TraceRequest;
import ml.pfit.resolve.RequestResolver;
import ml.pfit.service.StatsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class WebController {

    @Autowired
    RequestResolver requestResolver;

    private final StatsInterface stats;

    @Autowired
    public WebController(StatsInterface stats) {
        this.stats = stats;
    }

    /** Main page */
    @GetMapping("/")
    public String main(Model model, @ModelAttribute TraceRequest traceRequest) {
        model.addAttribute("traceRequest", traceRequest);
        return "main-form";
    }

    /** User query about an IP */
    @PostMapping("/")
    public String traceIP(Model model, @ModelAttribute TraceRequest traceRequest) {
        try {
            traceRequest = requestResolver.resolve(traceRequest);
            model.addAttribute("traceRequest", traceRequest);
            model.addAttribute("stats", stats.toJSON());
            return "main-form";
        } catch (Exception e) {
            // Basic interaction.  Simply propagate the error to the user
            model.addAttribute("detail", e.getMessage());
            return "error";
        }
    }

}
