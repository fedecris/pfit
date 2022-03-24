package ml.pfit.controller;

import ml.pfit.model.Country;
import ml.pfit.resolve.CountryResolver;
import ml.pfit.resolve.IPResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TraceController {

    @Autowired
    IPResolver ipResolver;

    @Autowired
    CountryResolver countryResolver;

    /** End-point for retrieving info about a specific IP */
    @GetMapping("/traceip/{ip}")
    public ResponseEntity<Object> traceIP(Model model, @PathVariable("ip") String ip) {
        try {
            Country country = ipResolver.resolve(ip);
            countryResolver.resolve(country);
            return new ResponseEntity<>(country.toJSON(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}
