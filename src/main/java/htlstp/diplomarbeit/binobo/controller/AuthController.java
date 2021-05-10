package htlstp.diplomarbeit.binobo.controller;

import htlstp.diplomarbeit.binobo.controller.util.FlashMessage;
import htlstp.diplomarbeit.binobo.dto.RegisterRequest;
import htlstp.diplomarbeit.binobo.model.User;
import htlstp.diplomarbeit.binobo.service.UserService;
import htlstp.diplomarbeit.binobo.service.validation.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;

@Controller
public class AuthController {
    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String getLoginForm(Model model, HttpServletRequest httpServletRequest){
        if(!model.containsAttribute("userDTO"))
            model.addAttribute("userDTO", new RegisterRequest());
        model.addAttribute("user", new User());
        try {
            Object flash = httpServletRequest.getSession().getAttribute("flash");
            model.addAttribute("flash", flash);

            httpServletRequest.getSession().removeAttribute("flash");
        } catch (Exception ex) {
            // "flash" session attribute must not exist - do nothing and proceed normally
        }
        return "login_ud";
    }

    @RequestMapping(value = "/login/register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("userDTO") @Valid RegisterRequest userDto, Errors errors,
                               RedirectAttributes redirectAttributes) {
        System.out.println("Just something");
        try {
            if(errors.hasErrors())throw new ValidationException("Some fields contain errors, please check them again!");
            userService.register(userDto);
        }catch (Exception exception){
            redirectAttributes.addAttribute("flash", new FlashMessage(exception.getMessage(), FlashMessage.Status.FAILURE));
            redirectAttributes.addAttribute("userDTO", userDto);
            System.out.println("ERROR occurred");
            return "redirect:/login";
        }

        System.out.println("SUCCESS!!");

        return "redirect:/blog";
    }

}
