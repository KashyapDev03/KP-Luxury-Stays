package com.kashyap.demo.Controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    @GetMapping("/adminLogin")
    public String adminLogin(Model model, HttpSession session, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        response.setHeader("Cache-Control",
                "no-cache, no-store, must-revalidate");

        response.setHeader("Pragma", "no-cache");

        response.setDateHeader("Expires", 0);
        
        String admin_email = (String) session.getAttribute("admin_email");
        model.addAttribute("admin_email", admin_email);

        model.addAttribute("showLogout", true);
//        }        
        return "AdminLogin";
    }

    @GetMapping("adminHome")
    public String adminHome(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String admin_email = (String) session.getAttribute("admin_email");
        model.addAttribute("admin_email", admin_email);

        if (session.getAttribute("admin_email") == null) {
            redirectAttributes.addFlashAttribute("msg", "Please login first!");
            return "redirect:/adminLogin";
        }

        model.addAttribute("showLogout", true);
        return "AdminHome";
    }

    @GetMapping("AdminManageCities")
    public String AdminManageCities(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String admin_email = (String) session.getAttribute("admin_email");
        model.addAttribute("admin_email", admin_email);

        if (session.getAttribute("admin_email") == null) {
            redirectAttributes.addFlashAttribute("msg", "Please login first!");
            return "redirect:/adminLogin";
        }

        model.addAttribute("showLogout", true);
        return "AdminManageCities";
    }

    @GetMapping("AdminManageProperties")
    public String AdminManageProperties(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String admin_email = (String) session.getAttribute("admin_email");
        model.addAttribute("admin_email", admin_email);

        if (session.getAttribute("admin_email") == null) {
            redirectAttributes.addFlashAttribute("msg", "Please login first!");
            return "redirect:/adminLogin";
        }

        model.addAttribute("showLogout", true);
        return "AdminManageProperties";
    }

    @GetMapping("AdminManageOwner")
    public String AdminManageOwner(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String admin_email = (String) session.getAttribute("admin_email");
        model.addAttribute("admin_email", admin_email);

        if (session.getAttribute("admin_email") == null) {
            redirectAttributes.addFlashAttribute("msg", "Please login first!");
            return "redirect:/adminLogin";
        }

        model.addAttribute("showLogout", true);
        return "AdminManageOwner";
    }
}
