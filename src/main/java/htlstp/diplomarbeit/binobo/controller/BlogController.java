package htlstp.diplomarbeit.binobo.controller;

import htlstp.diplomarbeit.binobo.controller.util.FlashMessage;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class BlogController {
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    SubCommentService subCommentService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/blog")
    public String listAllBlogs(Model model){
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);

        return "blog/blogOverview";
    }

    @RequestMapping(value = "/blog/{postId}")
    public String postX(@PathVariable Long postId, Model model, Principal principal){
        Post post = postService.findById(postId);
        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();

        model.addAttribute("post", post);
        model.addAttribute("user", user);
        model.addAttribute("comments", commentService.findAllByPost(post));
        if(!model.containsAttribute("comment"))
            model.addAttribute("comment", new Comment());

        return "blog/blog_PostX";
    }

    @RequestMapping(value = "/blog/new")
    public String newBlogPost(Model model){
        if(!model.containsAttribute("post")){
            model.addAttribute("post", new Post());
        }
        model.addAttribute("heading", "new BlogPost()");
        model.addAttribute("submit", "Upload");
        model.addAttribute("action", "/blog");
        model.addAttribute("categories", categoryService.findAll());

        return "blog/blogForm";
    }

    @RequestMapping(value = "/blog/{blogId}/edit")
    public String formEditBlogEntry(@PathVariable Long blogId, Model model, Principal principal){
        Post p = postService.findById(blogId);
        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();

        if(p.getUsername().equals(user.getUsername()) || user.getRole().getName().equals("ROLE_ADMIN") || user.getRole().getName().equals("ROLE_OPERATOR")){
            if(!model.containsAttribute("post")){
                model.addAttribute("post", p);
            }
            model.addAttribute("heading", "blogEntry.edit()");
            model.addAttribute("action", String.format("/blog/%s", blogId));
            model.addAttribute("submit", "Update");
            model.addAttribute("categories", categoryService.findAll());

            return "blog/blogForm";
        }

        return "redirect:/blog";
    }

    @RequestMapping(value = "/blog/{blogId}", method = RequestMethod.POST)
    public String updateBlogEntry(@Valid @ModelAttribute("post") Post post, BindingResult errors, RedirectAttributes redirectAttributes, Principal principal){// BindingResults
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

            redirectAttributes.addFlashAttribute("flash_succ", new FlashMessage("Successfully added new Entry!", FlashMessage.Status.SUCCESS));
        }

        return "redirect:/blog";
    }

    @RequestMapping(value = "/blog", method = RequestMethod.POST)
    public String addBlogEntry(@Valid @ModelAttribute("post") Post post, BindingResult errors, RedirectAttributes redirectAttributes, Principal principal){
        if(errors.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.post",errors);
            redirectAttributes.addFlashAttribute("flash_err", new FlashMessage("Parsed data contains errors, please check the fields below!",
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
        redirectAttributes.addFlashAttribute("flash_succ", new FlashMessage("Post successfully uploaded!", FlashMessage.Status.SUCCESS));
        return "redirect:/blog";
    }

    @RequestMapping(value = "/blog/user/entries")
    public String getAllPostsFromUser(Model model, Principal principal){
        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        model.addAttribute("posts", postService.findByUser(user));
        return "blog/blogOverview";
    }

    @RequestMapping(value = "/blog/post/{postId}/addComment", method = RequestMethod.POST)
    public String addComment(@PathVariable Long postId, @Valid @ModelAttribute("comment") Comment comment, BindingResult errors, RedirectAttributes redirectAttributes, Principal principal){
        if(errors.hasErrors()){
             redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.comment",errors);
             redirectAttributes.addFlashAttribute("flash_err", new FlashMessage("Comment contains errors, please make sure the input is valid and not empty!", FlashMessage.Status.FAILURE));
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

    @RequestMapping(value = "/blog/{postId}/delete")
    public String deletePost(@PathVariable Long postId, Principal principal, RedirectAttributes redirectAttributes) {
        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        Post post = postService.findById(postId);

        if(post.getUsername().equals(user.getUsername())){
            List<Comment> comments = commentService.findAllByPost(post);
            for(Comment c : comments){
                subCommentService.deleteAllByComment(c);
            }
            commentService.deleteAllByPost(post);
            postService.deletePost(post);

            redirectAttributes.addFlashAttribute("flash_info", new FlashMessage("Your post has been deleted!", FlashMessage.Status.INFO));
        }

        return "redirect:/blog";
    }

    @RequestMapping(value = "/blog/{postId}/incrementMark", method = RequestMethod.POST)
    public String incrementMark(@PathVariable Long postId){
        try {
            postService.incrementMarks(postService.findById(postId));
        }catch (NullPointerException npe){
            return "redirect:/blog";
        }
        return String.format("redirect:/blog/%s", postId);
    }

    @RequestMapping(value = "/blog/{postId}/decrementMark", method = RequestMethod.POST)
    public String decrementMark(@PathVariable Long postId){
        try {
            postService.decrementMarks(postService.findById(postId));
        }catch (NullPointerException npe){
            return "redirect:/blog";
        }
        return String.format("redirect:/blog/%s", postId);
    }

    @RequestMapping(value = "/blog/{commentId}/incrementMarkComment", method = RequestMethod.POST)
    public String incrementMarkComment(@PathVariable Long commentId){
        Comment comment = commentService.findById(commentId);
        try {
            commentService.incrementMarks(comment);
        }catch (NullPointerException npe){
            return "redirect:/blog";
        }
        return String.format("redirect:/blog/%s", comment.getPost().getId());
    }

    @RequestMapping(value = "/blog/{commentId}/decrementMarkComment", method = RequestMethod.POST)
    public String decrementMarkComment(@PathVariable Long commentId){
        Comment comment = commentService.findById(commentId);
        try {
            commentService.decrementMarks(comment);
        }catch (NullPointerException npe){
            return "redirect:/blog";
        }
        return String.format("redirect:/blog/%s", comment.getPost().getId());
    }

}
