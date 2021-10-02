package htlstp.diplomarbeit.binobo.controller;

import htlstp.diplomarbeit.binobo.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class EmulatorController {

    @GetMapping(value = "/emulator3D")
    public String toSimulator(Model model, Principal principal){
        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("token", user.getDataAccessToken().getToken());
        return "emulator3D";
    }

}
