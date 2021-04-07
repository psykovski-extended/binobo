package htlstp.diplomarbeit.binobo.controller;

import htlstp.diplomarbeit.binobo.model.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebsiteFetcher {

    /*================================================= GET Requests ================================================*/

    @RequestMapping(value = "/blog")
    public String toBlogIndex(){
        return "blog/blogOverview";
    }

    @RequestMapping(value = "/blog_Create")
    public String toBlogCreate(Model model){
        model.addAttribute("post", new Post());
        return "blog/blogForm";
    }

    @RequestMapping(value = "/blog_Edit")
    public String toBlogEdit(){
        return "blog/blogForm";
    }

    @RequestMapping(value = "/devInformation")
    public String toDevInf(){
        return "devInformation";
    }

    @RequestMapping(value = "/index")
    public String toHome(){
        return "index";
    }

    @RequestMapping(value = "/login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping(value = "/projectOverview")
    public String toProjOV(){
        return "projectOverview";
    }

    @RequestMapping(value = "/simulator3D")
    public String toSimulator(){
        return "simulator3D";
    }

    @RequestMapping(value = "/sponsoren")
    public String toSponsoren(){
        return "sponsoren";
    }

    @RequestMapping(value = "")
    public String toIndex(){
        return "index";
    }

    /*================================================= POST Requests ================================================*/

    // TODO Model-Control realisieren
    @RequestMapping(value = "/blog_Created", method = RequestMethod.POST)
    public String postBlogEntry(){
        return "blog/blogOverview";
    }
}
