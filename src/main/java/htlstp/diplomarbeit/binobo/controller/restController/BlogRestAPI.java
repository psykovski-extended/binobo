package htlstp.diplomarbeit.binobo.controller.restController;

import htlstp.diplomarbeit.binobo.controller.util.FlashMessage;
import htlstp.diplomarbeit.binobo.model.Bookmark;
import htlstp.diplomarbeit.binobo.model.User;
import htlstp.diplomarbeit.binobo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping(value = "/blog/restAPI")
public class BlogRestAPI {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;
    private final SubCommentService subCommentService;
    private final CategoryService categoryService;
    private final BookmarkService bookmarkService;

    @Autowired
    public BlogRestAPI(PostService postService, UserService userService, CommentService commentService,
                          SubCommentService subCommentService, CategoryService categoryService,
                          BookmarkService bookmarkService) {
        this.postService = postService;
        this.userService = userService;
        this.commentService = commentService;
        this.subCommentService = subCommentService;
        this.categoryService = categoryService;
        this.bookmarkService = bookmarkService;
    }

    // PatchMapping for add/ del bookmark

    @PatchMapping(value = "/toggle_bookmark")
    public ResponseEntity<FlashMessage> toggleBookmark(@RequestParam("post_id") Long post_id, Principal principal){
        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();

        Iterable<Bookmark> bookmarks = bookmarkService.fetchAllBookmarksFromUser(user);
        AtomicBoolean bookmark_exists = new AtomicBoolean(false);
        bookmarks.forEach(bookmark -> {
            if (Objects.equals(bookmark.getPost().getId(), post_id)){
                bookmarkService.deleteBookmark(bookmark);
                bookmark_exists.set(true);
            }
        });
        if (!bookmark_exists.get()) {
            Bookmark bookmark = new Bookmark();
            bookmark.setPost(postService.findById(post_id));
            bookmark.setUser(user);
            bookmarkService.saveBookmark(bookmark);
        }

        return null;
    }

}
