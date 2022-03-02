package htlstp.diplomarbeit.binobo.controller;

import htlstp.diplomarbeit.binobo.controller.util.FlashMessage;
import htlstp.diplomarbeit.binobo.dto.RegisterRequest;
import htlstp.diplomarbeit.binobo.event.OnRegistrationCompleteEvent;
import htlstp.diplomarbeit.binobo.model.API_Key;
import htlstp.diplomarbeit.binobo.model.ConfirmationToken;
import htlstp.diplomarbeit.binobo.model.DataAccessToken;
import htlstp.diplomarbeit.binobo.model.User;
import htlstp.diplomarbeit.binobo.service.RobotDataService;
import htlstp.diplomarbeit.binobo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Calendar;

@Controller
public class AuthController {

    private final UserService userService;
    private final RobotDataService robotDataService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public AuthController(UserService userService, RobotDataService robotDataService, ApplicationEventPublisher eventPublisher){
        this.userService = userService;
        this.robotDataService = robotDataService;
        this.eventPublisher = eventPublisher;
    }

    @RequestMapping("/login")
    public String getLoginForm(Model model){
        if(!model.containsAttribute("userDTO"))
            model.addAttribute("userDTO", new RegisterRequest());
        model.addAttribute("user", new User());

        return "login_ud";
    }

    @PostMapping(value = "/login/register")
    public String registerUser(@Valid @ModelAttribute("userDTO") RegisterRequest userDto, BindingResult errors,
                               RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            if(errors.hasErrors())throw new ValidationException("Your credentials do not seem to be correct, please try again!");
            userService.register(userDto);

            User user = userService.findByUsername(userDto.getUsername());

            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user,
                    request.getLocale(), appUrl));
            redirectAttributes.addFlashAttribute("flash_info",
                    new FlashMessage("Please verify your email before you next sign in!", FlashMessage.Status.INFO));
        }catch (Exception exception){
            redirectAttributes.addFlashAttribute("flash_err", new FlashMessage(exception.getMessage(), FlashMessage.Status.FAILURE));
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userDTO",errors);
            redirectAttributes.addFlashAttribute("userDTO", userDto);
        }
        return "redirect:/login";
    }

    @RequestMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        ConfirmationToken confirmationToken = userService.getVerificationToken(token);

        if (confirmationToken == null) {
            redirectAttributes.addFlashAttribute("flash_err", new FlashMessage("Invalid Confirmation-Token!", FlashMessage.Status.FAILURE));
            return "redirect:/login";
        }

        User user = confirmationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((confirmationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            // TODO: send Email again
            userService.deleteByUsername(user.getUsername());
            redirectAttributes.addFlashAttribute("flash_err", new FlashMessage("Your confirmation-Token has expired! Please enter your credentials again:", FlashMessage.Status.FAILURE));
            return "redirect:/login";
        }

        user.setActivated(true);

        DataAccessToken dat = new DataAccessToken();
        user.setDataAccessToken(robotDataService.saveDataAccessToken(dat));

        API_Key key = new API_Key();
        userService.saveAPIKey(key);
        user.setApi_key(key);

        userService.save(user);
        userService.deleteToken(confirmationToken);

        redirectAttributes.addFlashAttribute("flash_succ", new FlashMessage("Your account has successfully been activated!", FlashMessage.Status.SUCCESS));

        return "redirect:/login";
    }

}
