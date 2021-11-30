package htlstp.diplomarbeit.binobo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InfoPagesController {

    @RequestMapping({"", "/home"})
    public String toIndex(){
        return "index";
    }

    @RequestMapping(value = "/developer")
    public String devInfo(){
        return "devInformation";
    }

    @RequestMapping(value = "/project")
    public String projInfo(){
        return "projectOverview";
    }

}
