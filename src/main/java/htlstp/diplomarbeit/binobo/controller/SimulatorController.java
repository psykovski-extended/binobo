package htlstp.diplomarbeit.binobo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SimulatorController {

    @GetMapping(value = "/simulator3D")
    public String toSimulator(){
        return "simulator3D";
    }

}
