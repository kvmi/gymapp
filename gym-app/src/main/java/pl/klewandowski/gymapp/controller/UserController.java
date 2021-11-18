package pl.klewandowski.gymapp.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.klewandowski.gymapp.model.User;
import pl.klewandowski.gymapp.service.UserService;

import java.sql.SQLIntegrityConstraintViolationException;

import static pl.klewandowski.gymapp.Constants.*;


@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String main() {
        return "main2";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("usersList", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/addUser")
    public ModelAndView addUserByAdmin() {
        return new ModelAndView("add_user", "user", new User());
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView saveUser(User user, Model model) {
        String message = userService.addUser(user);
        if (message.equals(ADD_USER_WRONG_EMAIL_MESSAGE)) {
            model.addAttribute("message", ADD_USER_WRONG_EMAIL_MESSAGE);
            return new ModelAndView("register", "user", new User());
        } else if (message.equals(ADD_USER_DUPLICATE_RECORD_IN_DB)) {
            model.addAttribute("message", ADD_USER_DUPLICATE_RECORD_IN_DB);
            return new ModelAndView("register", "user", new User());
        } else {
            model.addAttribute("message", ADD_USER_SUCCESS_MESSAGE);
            return new ModelAndView("redirect:/login");
        }
    }

    @RequestMapping(value = "/update-user", method = RequestMethod.POST)
    public ModelAndView updateUser(User user, Model model) {
        String message = userService.addUser(user);
        if (message.equals(ADD_USER_WRONG_EMAIL_MESSAGE)) {
            model.addAttribute("message", ADD_USER_WRONG_EMAIL_MESSAGE);
            return new ModelAndView("redirect:/user/edit/" + user.getId(), "user", new User());
        } else if (message.equals(ADD_USER_DUPLICATE_RECORD_IN_DB)) {
            model.addAttribute("message", ADD_USER_DUPLICATE_RECORD_IN_DB);
            return new ModelAndView("redirect:/user/edit/" + user.getId(), "user", new User());
        } else {
            model.addAttribute("message", ADD_USER_SUCCESS_MESSAGE);
            return new ModelAndView("redirect:/users");
        }
    }

    @RequestMapping(value = "/save-user", method = RequestMethod.POST)
    public ModelAndView saveUserByAdmin(User user, Model model) {
        String message = userService.addUser(user);
        if (message.equals(ADD_USER_WRONG_EMAIL_MESSAGE)) {
            model.addAttribute("message", ADD_USER_WRONG_EMAIL_MESSAGE);
            return new ModelAndView("add_user", "user", new User());
        } else if (message.equals(ADD_USER_DUPLICATE_RECORD_IN_DB)) {
            model.addAttribute("message", ADD_USER_DUPLICATE_RECORD_IN_DB);
            return new ModelAndView("add_user", "user", new User());
        } else {
            model.addAttribute("message", ADD_USER_SUCCESS_MESSAGE);
            return new ModelAndView("redirect:/users");
        }
    }

    @GetMapping("/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof AnonymousAuthenticationToken)
            return "login";
        else return "redirect:/";
    }

    @RequestMapping("/signup")
    public ModelAndView addUser() {
        return new ModelAndView("register", "user", new User());
    }

    @GetMapping("/user/edit/{id}")
    public String editUser(@PathVariable(value = "id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "update_user";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

}
