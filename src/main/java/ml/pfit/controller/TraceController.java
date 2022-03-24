package ml.pfit.controller;

import ml.pfit.model.Country;
import ml.pfit.resolve.CountryResolver;
import ml.pfit.resolve.CurrencyResolver;
import ml.pfit.resolve.IPResolver;
import ml.pfit.resolve.RequestResolver;
import ml.pfit.service.StatsFactory;
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
    RequestResolver requestResolver;

    @Autowired
    StatsFactory statsFactory;

    /** End-point for retrieving info about a specific IP */
    @GetMapping("/api/traceip/{ip}")
    public ResponseEntity<Object> traceIP(Model model, @PathVariable("ip") String ip) {
        try {
            return new ResponseEntity<>(requestResolver.resolve(ip).toJSON(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** End-point for retrieving statistical distances from Buenos Aires City */
    @GetMapping("/api/stats")
    public ResponseEntity<Object> stats(Model model) {
        try {
            return new ResponseEntity<>(statsFactory.getInstance().toJSON(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
