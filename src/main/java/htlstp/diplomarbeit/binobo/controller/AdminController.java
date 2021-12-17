package htlstp.diplomarbeit.binobo.controller;

import htlstp.diplomarbeit.binobo.model.Role;
import htlstp.diplomarbeit.binobo.model.User;
import htlstp.diplomarbeit.binobo.service.CommentService;
import htlstp.diplomarbeit.binobo.service.PostService;
import htlstp.diplomarbeit.binobo.service.RoleService;
import htlstp.diplomarbeit.binobo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {
    // TODO move logic to backend (Exception based --> take a look at the rest controller)

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final RoleService roleService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AdminController(UserService userService, PostService postService,
                           CommentService commentService, RoleService roleService,
                           JdbcTemplate jdbcTemplate){
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
        this.roleService = roleService;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieves all users stored in database
     * @param model Current model
     * @param principal Current logged in user
     * @return Returns a list of users
     */
    @GetMapping(value = "/admin/listAllUsers")
    public String fetchAllUsers(Model model, Principal principal){
        List<User> users = userService.findAll();
        List<Role> roles = roleService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);

        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        logger.info("[ " + user.getUsername() + " ] : Administration-Pages called. All available user got fetched.");

        return "admin/users/listAllUsers";
    }

    /**
     * Deletes one user, matching parsed id
     * @param user_id Id of user to be deleted
     * @param principal current loggen in user
     * @return Redirects back to admin operation page
     */
    @DeleteMapping(value = "/admin/delete/{user_id}")
    public String deleteUser(@PathVariable("user_id") Long user_id, Principal principal){
        User userToDelete = userService.findById(user_id);
        if(userToDelete == null)return "redirect:/admin/listAllUsers";

        commentService.deleteAllByUser(userToDelete);
        postService.deleteAllByUser(userToDelete);
        userService.delete(userToDelete);

        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        logger.warn("[ " + user.getUsername() + " ] : Deleted user: " + "[ " + userToDelete.getUsername() + " ]");

        return "redirect:/admin/listAllUsers";
    }

    /**
     * Sets role of current user
     * @param user_id User to up-/downgrade
     * @param role_id Role to be set
     * @param principal current logged in user
     * @return Redirects back to admin operation page
     */
    @PatchMapping(value = "/admin/setRoleOfUser/{user_id}/{role_id}")
    public String setRoleOfUserToRole(@PathVariable("user_id") Long user_id, @PathVariable("role_id") Long role_id, Principal principal){
        Role role = roleService.findById(role_id); // could be null, so keep attention
        User userToUpgrade = userService.findById(user_id);
        if(role == null || userToUpgrade == null) return "redirect:/admin/listAllUsers";
        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();

        if(user.getRole().getName().equals("ROLE_OPERATOR")){
            userService.setRole(userToUpgrade, role);
        }else if(user.getRole().getName().equals("ROLE_ADMIN")){
            if(userToUpgrade.getRole().getId() < user.getRole().getId() && role_id <= user.getRole().getId()){
                userService.setRole(userToUpgrade, role);
            }
        }

        logger.info("[ " + user.getUsername() + " ] : Modified the role of \" " + userToUpgrade.getUsername() + "\". New role is: [ " + role.getName() + " ]");

        return "redirect:/admin/listAllUsers";
    }
}
