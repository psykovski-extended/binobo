package htlstp.diplomarbeit.binobo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebsiteFetcher {

    @RequestMapping(value = "/{file}")
    public String toPage(@PathVariable String file){
        return file;
    }
}
