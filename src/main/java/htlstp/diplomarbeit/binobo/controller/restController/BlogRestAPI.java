package htlstp.diplomarbeit.binobo.controller.restController;

import htlstp.diplomarbeit.binobo.controller.util.FlashMessage;
import htlstp.diplomarbeit.binobo.model.Bookmark;
import htlstp.diplomarbeit.binobo.model.User;
import htlstp.diplomarbeit.binobo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@ResponseBody
@RequestMapping(value = "/blog_rest_api")
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

    @PatchMapping(value = "/toggle_bookmark")
    public ResponseEntity<FlashMessage> toggleBookmark(@RequestParam("post_id") Long post_id, @RequestParam("user_id") Long user_id){
        User user = userService.findById(user_id);

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

        return ResponseEntity.ok().body(new FlashMessage("Bookmark toggled!", FlashMessage.Status.SUCCESS));
    }

}
