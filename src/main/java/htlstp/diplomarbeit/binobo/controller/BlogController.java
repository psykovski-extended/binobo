package htlstp.diplomarbeit.binobo.controller;

import htlstp.diplomarbeit.binobo.controller.util.FlashMessage;
import htlstp.diplomarbeit.binobo.model.Comment;
import htlstp.diplomarbeit.binobo.model.Post;
import htlstp.diplomarbeit.binobo.model.User;
import htlstp.diplomarbeit.binobo.service.PostService;
import htlstp.diplomarbeit.binobo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

        return "blog/blogForm";
    }

    @RequestMapping(value = "/blog/{blogId}/edit")
    public String formEditBlogEntry(@PathVariable Long blogId, Model model, Principal principal){
        Post p = postService.findById(blogId);
        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();

        if(p.getUsername().equals(user.getUsername())){
            if(!model.containsAttribute("post")){
                model.addAttribute("post", p);
            }
            model.addAttribute("heading", "blogEntry.edit()");
            model.addAttribute("action", String.format("/blog/%s", blogId));
            model.addAttribute("submit", "Update");

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

            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Successfully added new Entry!", FlashMessage.Status.SUCCESS));
        }

        return "redirect:/blog";
    }

    @RequestMapping(value = "/blog", method = RequestMethod.POST)
    public String addBlogEntry(@Valid @ModelAttribute("post") Post post, BindingResult errors, RedirectAttributes redirectAttributes, Principal principal){
        if(errors.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.post",errors);
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Parsed data contains errors, please check the fields below!",
                    FlashMessage.Status.FAILURE));
            redirectAttributes.addFlashAttribute("post",post);

            return "redirect:/blog/new";
        }
        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        post.setUser(user);
        post.setUsername(user.getUsername());

        postService.savePost(post);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Post successfully uploaded!", FlashMessage.Status.SUCCESS));
        return "redirect:/blog";
    }

    @RequestMapping(value = "/blog/user/entries")
    public String getAllPostsFromUser(Model model, Principal principal){
        User user = (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
        model.addAttribute("posts", postService.findByUser(user));
        return "blog/blogOverview";
    }
}
