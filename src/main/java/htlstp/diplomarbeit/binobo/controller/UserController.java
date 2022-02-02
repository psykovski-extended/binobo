package htlstp.diplomarbeit.binobo.controller;

import htlstp.diplomarbeit.binobo.model.Bookmark;
import htlstp.diplomarbeit.binobo.model.Post;
import htlstp.diplomarbeit.binobo.model.User;
import htlstp.diplomarbeit.binobo.service.BookmarkService;
import htlstp.diplomarbeit.binobo.service.PostService;
import htlstp.diplomarbeit.binobo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final PostService postService;
    private final UserService userService;
    private final BookmarkService bookmarkService;

    @Autowired
    public UserController(PostService postService, UserService userService, BookmarkService bookmarkService){
        this.postService = postService;
        this.userService = userService;
        this.bookmarkService = bookmarkService;
    }

    @GetMapping(value = "/profile")
    public String getProfileInfo(Model model, Principal principal){
        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        model.addAttribute("user", user);
        List<Post> posts = new ArrayList<>();
        List<Bookmark> bookmarks = bookmarkService.findAllByUser(user);
        bookmarks.forEach(element -> {
            posts.add(postService.findById(element.getPost().getId()));
        });

        model.addAttribute("bookmarks", posts);
        model.addAttribute("posts", postService.findByUser(user));

        return "user/profile";
    }

}
