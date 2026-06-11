package com.kashyap.demo.Controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @GetMapping({"/", "/index"})
    public String index(Model model, HttpSession session) {
        String user_email = (String) session.getAttribute("user_email");
        model.addAttribute("user_email", user_email);
        model.addAttribute("showLogout", false);
        return "index";
    }

    @GetMapping("UserSignup")
    public String UserSignup() {
        return "UserSignup";
    }

    @GetMapping("UserLogin")
    public String UserLogin(Model model, HttpSession session, HttpServletResponse response) {
        response.setHeader("Cache-Control",
                "no-cache, no-store, must-revalidate");

        response.setHeader("Pragma", "no-cache");

        response.setDateHeader("Expires", 0);

        String user_email = (String) session.getAttribute("user_email");
        model.addAttribute("user_email", user_email);
        model.addAttribute("showLogout", true);

        return "UserLogin";
    }

    @GetMapping("CityProperties")
    public String CitiesProperties(Model model, HttpSession session) {
        String user_email = (String) session.getAttribute("user_email");
        model.addAttribute("user_email", user_email);

        model.addAttribute("showLogout", true);
        return "CityProperties";
    }

    @GetMapping("PropertyDetails")
    public String PropertyDetails(Model model, HttpSession session) {
        String user_email = (String) session.getAttribute("user_email");
        model.addAttribute("user_email", user_email);

        model.addAttribute("showLogout", true);
        return "PropertyDetails";
    }

    @GetMapping("paymentPage")
    public String paymentPage(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String user_email = (String) session.getAttribute("user_email");
        model.addAttribute("user_email", user_email);

        if (session.getAttribute("user_email") == null) {
            redirectAttributes.addFlashAttribute("msg", "Please login first!");
            return "redirect:/UserLogin";
        }

        model.addAttribute("showLogout", true);
        return "/paymentPage";
    }

    @GetMapping("BookingHistory")
    public String BookingHistory(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String user_email = (String) session.getAttribute("user_email");
        model.addAttribute("user_email", user_email);

        if (session.getAttribute("user_email") == null) {
            redirectAttributes.addFlashAttribute("msg", "Please login first!");
            return "redirect:/UserLogin";
        }

        model.addAttribute("showLogout", true);
        return "/BookingHistory";
    }

//    @GetMapping("about")
//    public String about(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
//        String user_email = (String)session.getAttribute("user_email");
//        model.addAttribute("user_email", user_email);
//        
//        if (session.getAttribute("user_email") == null) {
//            redirectAttributes.addFlashAttribute("msg", "Please login first!");
//            return "redirect:/UserLogin";
//        }
//        
//        model.addAttribute("showLogout", true);
//        return "about";
//    }
    @GetMapping("about")
    public String about() {
        return "about";
    }
}
