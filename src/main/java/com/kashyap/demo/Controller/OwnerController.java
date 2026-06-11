package com.kashyap.demo.Controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OwnerController {

    @GetMapping("/PropertyOwnerSignup")
    public String PropertyOwnerSignup(Model model, HttpSession session) {
        String owner_email
                = (String) session.getAttribute("owner_email");

        model.addAttribute("owner_email", owner_email);
        return "/PropertyOwnerSignup";
    }

    @GetMapping("/PropertyOwnerLogin")
    public String PropertyOwnerLogin(Model model, HttpSession session, HttpServletResponse response) {
        response.setHeader("Cache-Control",
                "no-cache, no-store, must-revalidate");

        response.setHeader("Pragma", "no-cache");

        response.setDateHeader("Expires", 0);

        String owner_email = (String) session.getAttribute("owner_email");

        model.addAttribute("owner_email", owner_email);

        if (owner_email != null) {
            model.addAttribute("showLogout", true);
        }

        return "/PropertyOwnerLogin";
    }

    @GetMapping("/PropertyOwnerHome")
    public String PropertyOwnerHome(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String owner_email
                = (String) session.getAttribute("owner_email");

        model.addAttribute("owner_email", owner_email);

        if (session.getAttribute("owner_email") == null) {
            redirectAttributes.addFlashAttribute("msg", "Please login first!");
            return "redirect:/PropertyOwnerLogin";
        }

        model.addAttribute("showLogout", true);
        return "/PropertyOwnerHome";
    }

    @GetMapping("/OwnerManageProperties")
    public String OwnerManageProperties(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String owner_email
                = (String) session.getAttribute("owner_email");

        model.addAttribute("owner_email", owner_email);

        if (session.getAttribute("owner_email") == null) {
            redirectAttributes.addFlashAttribute("msg", "Please login first!");
            return "redirect:/PropertyOwnerLogin";
        }

        model.addAttribute("showLogout", true);
        return "/OwnerManageProperties";
    }

    @GetMapping("/OwnerEditProperties")
    public String OwnerEditProperties(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String owner_email
                = (String) session.getAttribute("owner_email");

        model.addAttribute("owner_email", owner_email);

        if (session.getAttribute("owner_email") == null) {
            redirectAttributes.addFlashAttribute("msg", "Please login first!");
            return "redirect:/PropertyOwnerLogin";
        }

        model.addAttribute("showLogout", true);
        return "/OwnerEditProperties";
    }

    @GetMapping("/AddPhotosforProperties")
    public String AddPhotosforProperties(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String owner_email
                = (String) session.getAttribute("owner_email");

        model.addAttribute("owner_email", owner_email);

        if (session.getAttribute("owner_email") == null) {
            redirectAttributes.addFlashAttribute("msg", "Please login first!");
            return "redirect:/PropertyOwnerLogin";
        }

        model.addAttribute("showLogout", true);
        return "/AddPhotosforProperties";
    }

    @GetMapping("/OwnerManageBookings")
    public String OwnerManageBookings(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String owner_email
                = (String) session.getAttribute("owner_email");

        model.addAttribute("owner_email", owner_email);

        if (session.getAttribute("owner_email") == null) {
            redirectAttributes.addFlashAttribute("msg", "Please login first!");
            return "redirect:/PropertyOwnerLogin";
        }

        model.addAttribute("showLogout", true);
        return "/OwnerManageBookings";
    }
}
