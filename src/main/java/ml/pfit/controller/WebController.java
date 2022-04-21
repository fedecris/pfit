package ml.pfit.controller;

import lombok.RequiredArgsConstructor;
import ml.pfit.dto.TraceRequestDTO;
import ml.pfit.resolve.RequestResolver;
import ml.pfit.service.StatsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class WebController {

    private final RequestResolver requestResolver;

    private final StatsInterface stats;

    /** Main page */
    @GetMapping("/")
    public String main(Model model, @ModelAttribute TraceRequestDTO traceRequestDTO) {
        model.addAttribute("traceRequestDTO", traceRequestDTO);
        return "main-form";
    }

    /** User query about an IP */
    @PostMapping("/")
    public String traceIP(Model model, @ModelAttribute TraceRequestDTO traceRequestDTO) {
        try {
            traceRequestDTO = requestResolver.resolve(traceRequestDTO);
            model.addAttribute("traceRequestDTO", traceRequestDTO);
            model.addAttribute("stats", stats.toJSON());
            return "main-form";
        } catch (Exception e) {
            // Basic interaction.  Simply propagate the error to the user
            model.addAttribute("detail", e.getMessage());
            return "error";
        }
    }

}
