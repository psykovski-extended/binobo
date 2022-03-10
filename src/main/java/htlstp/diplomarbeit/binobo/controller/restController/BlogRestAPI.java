package htlstp.diplomarbeit.binobo.controller.restController;

import htlstp.diplomarbeit.binobo.controller.util.FlashMessage;
import htlstp.diplomarbeit.binobo.model.*;
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
    private final VoteService voteService;

    @Autowired
    public BlogRestAPI(PostService postService,
                       UserService userService,
                       CommentService commentService,
                       SubCommentService subCommentService,
                       CategoryService categoryService,
                       BookmarkService bookmarkService,
                       VoteService voteService) {
        this.postService = postService;
        this.userService = userService;
        this.commentService = commentService;
        this.subCommentService = subCommentService;
        this.categoryService = categoryService;
        this.bookmarkService = bookmarkService;
        this.voteService = voteService;
    }

    @PatchMapping(value = "/toggle_bookmark")
    public ResponseEntity<FlashMessage> toggleBookmark(@RequestParam("post_id") Long post_id,
                                                       @RequestParam("user_id") Long user_id,
                                                       @RequestParam("api_key") String token){
        User user = userService.findById(user_id);

        if(Objects.equals(user.getApi_key().getToken(), token)) {
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

            return ResponseEntity.ok().body(
                    new FlashMessage("Bookmark toggled!",
                            FlashMessage.Status.SUCCESS));
        }else {
            return ResponseEntity.badRequest().body(
                    new FlashMessage("Invalid API Key!",
                            FlashMessage.Status.FAILURE));
        }
    }

    // upvote post, down-vote post, remove vote from post

    @PatchMapping(value = "/toggle_vote_for_post")
    public ResponseEntity<FlashMessage> toggleVotePost(@RequestParam("post_id") Long post_id,
                                                       @RequestParam("user_id") Long user_id,
                                                       @RequestParam("action") String action,
                                                       @RequestParam("api_key") String token) {
        User user = userService.findById(user_id);
        if(Objects.equals(user.getApi_key().getToken(), token)) {
            Post post = postService.findById(post_id);
            Vote vote = voteService.findByUserAndPost(user, post);

            if (vote == null) {
                Vote newVote = new Vote();
                newVote.setIsUseful(Objects.equals(action, "up"));

                newVote.setUser(user);
                newVote.setPost(post);

                voteService.save(newVote);
            } else {
                switch (action) {
                    case "up": {
                        if (vote.getIsUseful()) {
                            vote.setPost(null);
                            vote.setUser(null);
                            // this is needed to disassociate the vote from the user and post
                        } else {
                            vote.setIsUseful(true);
                        }
                    } break;
                    case "down": {
                        if (!vote.getIsUseful()) {
                            vote.setPost(null);
                            vote.setUser(null);
                        } else {
                            vote.setIsUseful(false);
                        }
                    } break;
                }
                voteService.save(vote);
                if(vote.getUser() == null && vote.getPost() == null) voteService.delete(vote);
            }
            return ResponseEntity.ok().body(
                    new FlashMessage("Set Vote",
                            FlashMessage.Status.SUCCESS));
        }
        return ResponseEntity.badRequest().body(
                new FlashMessage("Invalid API Key!",
                        FlashMessage.Status.FAILURE));
    }

    // upvote comment, down-vote comment, remove vote from comment

    @PatchMapping(value = "/toggle_vote_for_comment")
    public ResponseEntity<FlashMessage> toggleVoteComment(@RequestParam("comment_id") Long comment_id,
                                                          @RequestParam("user_id") Long user_id,
                                                          @RequestParam("action") String action,
                                                          @RequestParam("api_key") String token) {
        User user = userService.findById(user_id);
        if(Objects.equals(user.getApi_key().getToken(), token)) {
            Comment comment = commentService.findById(comment_id);
            Vote vote = voteService.findByUserAndComment(user, comment);

            if (vote == null) {
                Vote newVote = new Vote();
                newVote.setIsUseful(Objects.equals(action, "up"));

                newVote.setUser(user);
                newVote.setComment(comment);

                voteService.save(newVote);
            } else {
                switch (action) {
                    case "up": {
                        if (vote.getIsUseful()) {
                            vote.setComment(null);
                            vote.setUser(null);
                        } else {
                            vote.setIsUseful(true);
                        }
                    } break;
                    case "down": {
                        if (!vote.getIsUseful()) {
                            vote.setComment(null);
                            vote.setUser(null);
                        } else {
                            vote.setIsUseful(false);
                        }
                    } break;
                }
                voteService.save(vote);
                if(vote.getUser() == null && vote.getComment() == null) voteService.delete(vote);
            }
            return ResponseEntity.ok().body(
                    new FlashMessage("Set Vote",
                            FlashMessage.Status.SUCCESS));
        }
        return ResponseEntity.badRequest().body(
                new FlashMessage("Invalid API Key!",
                        FlashMessage.Status.FAILURE));
    }

}
