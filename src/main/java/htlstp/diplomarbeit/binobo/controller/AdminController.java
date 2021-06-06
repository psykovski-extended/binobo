package htlstp.diplomarbeit.binobo.controller;

import htlstp.diplomarbeit.binobo.model.Role;
import htlstp.diplomarbeit.binobo.model.User;
import htlstp.diplomarbeit.binobo.service.CommentService;
import htlstp.diplomarbeit.binobo.service.PostService;
import htlstp.diplomarbeit.binobo.service.RoleService;
import htlstp.diplomarbeit.binobo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;
    @Autowired
    RoleService roleService;

    // TODO establish log-system to log all admin-modification
    @RequestMapping(value = "/admin/listAllUsers")
    String fetchAllUsers(Model model){
        List<User> users = userService.findAll();
        List<Role> roles = roleService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        return "admin/users/listAllUsers";
    }

    @RequestMapping(value = "/admin/delete/{user_id}", method = RequestMethod.DELETE)
    String deleteUser(@PathVariable("user_id") Long user_id, Principal principal){
        User userToDelete = userService.findById(user_id);
        if(userToDelete == null)return "redirect:/admin/listAllUsers";

        commentService.deleteAllByUser(userToDelete);
        postService.deleteAllByUser(userToDelete);
        userService.delete(userToDelete);

        return "redirect:/admin/listAllUsers";
    }

    @RequestMapping(value = "/admin/setRoleOfUser/{user_id}/{role_id}", method = RequestMethod.PATCH)
    String setRoleOfUserToRole(@PathVariable("user_id") Long user_id, @PathVariable("role_id") Long role_id, Principal principal){
        Role role = roleService.findById(role_id); // could be null, so keep attention
        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        User userToUpgrade = userService.findById(user_id);

        if(user.getRole().getName().equals("ROLE_OPERATOR")){
            userService.setRole(userToUpgrade, role);
        }else if(user.getRole().getName().equals("ROLE_ADMIN")){
            if(userToUpgrade.getRole().getId() < user.getRole().getId() && role_id <= user.getRole().getId()){
                userService.setRole(userToUpgrade, role);
            }
        }

        return "redirect:/admin/listAllUsers";
    }
}
