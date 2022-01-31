package htlstp.diplomarbeit.binobo.controller;

import htlstp.diplomarbeit.binobo.controller.util.FlashMessage;
import htlstp.diplomarbeit.binobo.model.Bookmark;
import htlstp.diplomarbeit.binobo.model.Comment;
import htlstp.diplomarbeit.binobo.model.Post;
import htlstp.diplomarbeit.binobo.model.User;
import htlstp.diplomarbeit.binobo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
public class BlogController {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;
    private final SubCommentService subCommentService;
    private final CategoryService categoryService;
    private final BookmarkService bookmarkService;

    @Autowired
    public BlogController(PostService postService, UserService userService, CommentService commentService,
                          SubCommentService subCommentService, CategoryService categoryService,
                          BookmarkService bookmarkService) {
        this.postService = postService;
        this.userService = userService;
        this.commentService = commentService;
        this.subCommentService = subCommentService;
        this.categoryService = categoryService;
        this.bookmarkService = bookmarkService;
    }

    @GetMapping(value = "/blog")
    public String listAllBlogs(Model model){
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);

        return "blogOverview";
    }

    @GetMapping(value = "/blog/{postId}")
    public String postX(@PathVariable Long postId, Model model, Principal principal){
        Post post = postService.findById(postId);
        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        Bookmark bookmark = bookmarkService.findByPostAndUser(post, user).orElse(null);

        model.addAttribute("post", post);
        model.addAttribute("user", user);
        model.addAttribute("user_id", user.getId());
        model.addAttribute("post_id", post.getId());
        model.addAttribute("bookmark", bookmark);

        if(!model.containsAttribute("comment_action"))
            model.addAttribute("comment_action", String.format("/blog/post/%s/addComment", postId));
        model.addAttribute("comments", commentService.findAllByPost(post));
        if(!model.containsAttribute("comment"))
            model.addAttribute("comment", new Comment());

        return "blogEntryPreview";
    }

    @GetMapping(value = "/blog/new")
    public String newBlogPost(Model model){
        if(!model.containsAttribute("post")){
            model.addAttribute("post", new Post());
        }
        model.addAttribute("heading", "new BlogPost()");
        model.addAttribute("submit", "Upload");
        model.addAttribute("action", "/blog");
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("method", "post");

        return "blogForm";
    }

    @GetMapping(value = "/blog/{blogId}/edit")
    public String formEditBlogEntry(@PathVariable Long blogId, Model model, Principal principal){
        Post p = postService.findById(blogId);
        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();

        if(p.getUsername().equals(user.getUsername()) || user.getRole().getName().equals("ROLE_ADMIN") ||
                user.getRole().getName().equals("ROLE_OPERATOR")){
            if(!model.containsAttribute("post")){
                model.addAttribute("post", p);
            }
            model.addAttribute("heading", "blogEntry.edit()");
            model.addAttribute("action", String.format("/blog/%s", blogId));
            model.addAttribute("submit", "Update");
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("method", "patch");

            return "blogForm";
        }

        return "redirect:/blog";
    }

    @PostMapping(value = "/blog/{blogId}")
    public String updateBlogEntry(@Valid @ModelAttribute("post") Post post, BindingResult errors,
                                  RedirectAttributes redirectAttributes, Principal principal){// BindingResults
        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();

        if(post.getUsername().equals(user.getUsername())){
            if(errors.hasErrors()){
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.post",errors);
                redirectAttributes.addFlashAttribute("post",post);
                return String.format("redirect:/blog/%s/edit",post.getId());
            }

            post.setUpdatedOn(LocalDateTime.now());
            post.setUser(userService.findByUsername(post.getUsername()));

            postService.savePost(post);

            redirectAttributes.addFlashAttribute("flash_succ",
                    new FlashMessage("Successfully added new Entry!", FlashMessage.Status.SUCCESS));
        }

        return "redirect:/blog";
    }

    @PostMapping(value = "/blog")
    public String addBlogEntry(@Valid @ModelAttribute("post") Post post, BindingResult errors, RedirectAttributes redirectAttributes, Principal principal){
        if(errors.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.post",errors);
            redirectAttributes.addFlashAttribute("flash_err",
                    new FlashMessage("Parsed data contains errors, please check the fields below!",
                            FlashMessage.Status.FAILURE));
            redirectAttributes.addFlashAttribute("post",post);

            List<FieldError> errorz = errors.getFieldErrors();

            for(FieldError oe : errorz){
                System.out.println(oe.getField());
            }

            return "redirect:/blog/new";
        }
        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        post.setUser(user);
        post.setUsername(user.getUsername());

        postService.savePost(post);
        redirectAttributes.addFlashAttribute("flash_succ",
                new FlashMessage("Post successfully uploaded!",
                        FlashMessage.Status.SUCCESS));
        return "redirect:/blog";
    }

    @GetMapping(value = "/blog/user/entries")
    public String getAllPostsFromUser(Model model, Principal principal){
        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        model.addAttribute("posts", postService.findByUser(user));
        return "blogOverview";
    }

    @PostMapping(value = "/blog/post/{postId}/addComment")
    public String addComment(@PathVariable Long postId, @Valid @ModelAttribute("comment") Comment comment,
                             BindingResult errors, RedirectAttributes redirectAttributes, Principal principal){
        if(errors.hasErrors()){
             redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.comment",errors);
             redirectAttributes.addFlashAttribute("flash_err",
                     new FlashMessage("Comment contains errors, please make sure the input is valid and not empty!",
                             FlashMessage.Status.FAILURE));
             redirectAttributes.addFlashAttribute("comment", comment);

             return String.format("redirect:/blog/%s", postId);
         }

        Post post = postService.findById(postId);
        comment.setPost(post);
        comment.setUser((User)((UsernamePasswordAuthenticationToken)principal).getPrincipal());
        commentService.saveComment(comment);

        redirectAttributes.addFlashAttribute("flash_succ", new FlashMessage("Succesfully added comment!", FlashMessage.Status.SUCCESS));
        return String.format("redirect:/blog/%s", postId);
    }

    @GetMapping(value = "/blog/{postId}/delete")
    public String deletePost(@PathVariable Long postId, Principal principal, RedirectAttributes redirectAttributes) {
        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        Post post = postService.findById(postId);

        if(post.getUsername().equals(user.getUsername()) || user.getRole().getId() >= 3){
            List<Comment> comments = commentService.findAllByPost(post);
            for(Comment c : comments){
                subCommentService.deleteAllByComment(c);
            }
            commentService.deleteAllByPost(post);
            postService.deletePost(post);

            redirectAttributes.addFlashAttribute("flash_info", new FlashMessage(
                    "Your post has been deleted!", FlashMessage.Status.INFO));
        } else {
            redirectAttributes.addFlashAttribute("flash_warn", new FlashMessage(
                    "You do not have permissions to delete this blog entry!", FlashMessage.Status.WARN));
        }

        return "redirect:/blog";
    }

    // patch?

    @PostMapping(value = "/blog/{postId}/incrementMark")
    public String incrementMark(@PathVariable Long postId){
        try {
            postService.incrementMarks(postService.findById(postId));
        }catch (NullPointerException npe){
            return "redirect:/blog";
        }
        return String.format("redirect:/blog/%s", postId);
    }

    @PostMapping(value = "/blog/{postId}/decrementMark")
    public String decrementMark(@PathVariable Long postId){
        try {
            postService.decrementMarks(postService.findById(postId));
        }catch (NullPointerException npe){
            return "redirect:/blog";
        }
        return String.format("redirect:/blog/%s", postId);
    }

    @PostMapping(value = "/blog/{commentId}/incrementMarkComment")
    public String incrementMarkComment(@PathVariable Long commentId){
        Comment comment = commentService.findById(commentId);
        try {
            commentService.incrementMarks(comment);
        }catch (NullPointerException npe){
            return "redirect:/blog";
        }
        return String.format("redirect:/blog/%s", comment.getPost().getId());
    }

    @PostMapping(value = "/blog/{commentId}/decrementMarkComment")
    public String decrementMarkComment(@PathVariable Long commentId){
        Comment comment = commentService.findById(commentId);
        try {
            commentService.decrementMarks(comment);
        }catch (NullPointerException npe){
            return "redirect:/blog";
        }
        return String.format("redirect:/blog/%s", comment.getPost().getId());
    }

    @GetMapping(value = "/blog/{comment_id}/comment/delete")
    public String deleteComment(@PathVariable Long comment_id, @RequestParam("post_id") Long post_id,
                                Principal principal, RedirectAttributes redirectAttributes){
        Comment comment = commentService.findById(comment_id);
        if(comment == null) return "redirect:/blog";

        User cur_user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        User comment_user = comment.getUser();

        if(cur_user == comment_user || cur_user.getRole().getId() >= 2){
            commentService.deleteComment(comment);

            redirectAttributes.addFlashAttribute("flash_succ",
                    new FlashMessage("Comment got successfully deleted!",
                            FlashMessage.Status.SUCCESS));
        } else {
            redirectAttributes.addFlashAttribute("flash_err",
                    new FlashMessage("You are not allowed to delete this comment!",
                            FlashMessage.Status.FAILURE));
        }
        return String.format("redirect:/blog/%s", post_id);
    }

    @GetMapping(value = "/blog/{comment_id}/comment/edit")
    public String editComment(@PathVariable Long comment_id, @RequestParam("post_id") Long post_id,
                              Principal principal, RedirectAttributes redirectAttributes) {

        Comment comment = commentService.findById(comment_id);
        if(comment == null) return "redirect:/blog";

        User cur_user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        User comment_user = comment.getUser();

        if(cur_user == comment_user || cur_user.getRole().getId() >= 2){
            redirectAttributes.addFlashAttribute("comment", comment);
            redirectAttributes.addFlashAttribute("comment_action",
                    String.format("/blog/confirmCommentChange?post_id=%s&comment_user_id=%s", post_id, comment.getUser().getId()));
        }
        return String.format("redirect:/blog/%s", post_id);
    }

    @PostMapping(value = "/blog/confirmCommentChange")
    public String editCommentConfirm(@Valid @ModelAttribute("comment") Comment comment, @RequestParam("post_id") Long post_id,
                                     @RequestParam("comment_user_id") Long comment_user_id,
                                     BindingResult errors, RedirectAttributes redirectAttributes, Principal principal){

        User cur_user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();

        if(errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("flash_warn",
                    new FlashMessage("Comment contains errors, please check if the input field is not empty!",
                            FlashMessage.Status.WARN));
            return String.format("/blog/%s/comment/edit?post_id=%s", comment.getId(), post_id);
        }

        if(comment.getUser().getUsername().equals(cur_user.getUsername()) || cur_user.getRole().getId() >= 2) {
            comment.setUser(userService.findById(comment_user_id));
            commentService.saveComment(comment);
            redirectAttributes.addFlashAttribute("flash_info",
                    new FlashMessage("Comment successfully edited!",
                            FlashMessage.Status.INFO));
        } else {
            redirectAttributes.addFlashAttribute("flash_err",
                    new FlashMessage("You are not allowed to perform such a task!",
                            FlashMessage.Status.FAILURE));
        }

        return String.format("redirect:/blog/%s", post_id);
    }
}
