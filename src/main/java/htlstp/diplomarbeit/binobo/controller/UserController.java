package htlstp.diplomarbeit.binobo.controller;

import htlstp.diplomarbeit.binobo.service.BookmarkService;
import htlstp.diplomarbeit.binobo.service.PostService;
import htlstp.diplomarbeit.binobo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
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

}
