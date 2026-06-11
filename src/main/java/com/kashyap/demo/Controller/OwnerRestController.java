package com.kashyap.demo.Controller;

import com.kashyap.demo.vmm.DBLoader;
import com.kashyap.demo.vmm.RDBMS_TO_JSON;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class OwnerRestController {

    @PostMapping("/OwnerSignup")
    public String OwnerSignup(@RequestParam String name, @RequestParam String email, @RequestParam String pass, @RequestParam String cpass, @RequestParam String city, @RequestParam String contact, @RequestParam String brandname) {
        try {
            ResultSet rs = DBLoader.executeQuery("select * from owner where name = '" + name + "'");

            if (rs.next()) {
                return "0";
            } else {
                rs.moveToInsertRow();
                rs.updateString("name", name);
                rs.updateString("email", email);
                rs.updateString("password", pass);
                rs.updateString("city", city);
                rs.updateString("contact", contact);
                rs.updateString("brandname", brandname);
                rs.updateString("status", "Pending");

                rs.insertRow();

                return "1";

            }
        } catch (SQLException ex) {
            return ex.toString();
        }
    }

    @PostMapping("/CheckOwnerLogin")
    public String CheckOwnerLogin(@RequestParam String email, @RequestParam String pass, HttpSession session) {
        session.setAttribute("owner_email", email);
        try {
            ResultSet rs = DBLoader.executeQuery("select * from owner where email = '" + email + "' and password = '" + pass + "'");

            if (rs.next()) {
                return "0";
            } else {
                return "1";
            }
        } catch (SQLException ex) {
            return ex.toString();
        }
    }

    @GetMapping("PropertyOwnerLogout")
    public void PropertyOwnerLogout(HttpSession session, HttpServletResponse res) throws IOException {
        session.invalidate();

        res.sendRedirect("PropertyOwnerLogin");
    }

    @PostMapping("InsertProperty1")
    public String InsertProperty(HttpSession session, @RequestParam String pt, @RequestParam String city, @RequestParam String price, @RequestParam String op, @RequestParam String desc, @RequestParam String add, @RequestParam String pn, @RequestParam MultipartFile pp, @RequestParam String status, @RequestParam String latitude, @RequestParam String longitude) {
        try {
            String owner_email = (String) session.getAttribute("owner_email");
//            System.out.println(owner_email);

            String pn1 = pn.replace("'", "''");

            ResultSet rs = DBLoader.executeQuery("select * from ownerproperties where property_name = '" + pn1 + "' and owner_email = '" + owner_email + "'");

            String photo_name = pp.getOriginalFilename();
            byte[] b = pp.getBytes();

            String absPath = "src/main/resources/static/myUploads/";

            FileOutputStream fos = new FileOutputStream(absPath + photo_name);
            fos.write(b);

            if (rs.next()) {
                return "0";
            } else {
                rs.moveToInsertRow();

//                System.out.println("Property ID");
//                System.out.println(pt);
                rs.updateString("owner_email", owner_email);
                rs.updateString("property_type", pt);
                rs.updateString("propertyid", pt);
                rs.updateString("cityid", city);
                rs.updateString("price", price);
                rs.updateString("offer_price", op);
                rs.updateString("description", desc);
                rs.updateString("address", add);
                rs.updateString("property_name", pn);
                rs.updateString("photo", "myUploads/" + photo_name);
                rs.updateString("status", status);
                rs.updateString("latitude", latitude);
                rs.updateString("longitude", longitude);

                rs.insertRow();

                return "1";

            }
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
            return "Error: " + ex.toString();
        }
    }

    @GetMapping("ShowProperty1")
    public String ShowProperty(HttpSession session) {
        try {
            String owner_email = (String)session.getAttribute("owner_email");
            String ans = new RDBMS_TO_JSON().generateJSON("Select op.*, c.cityname AS city, c.statename, c.countryname, p.propertyname AS property_type from ownerproperties op JOIN city c ON op.cityid = c.cityid JOIN property p ON op.propertyid = p.propertyid where op.owner_email = '"+ owner_email +"'");
            return ans;
        } catch (Exception ex) {
            return ex.toString();

        }
    }

//    @GetMapping("getPropertyId")
//    public String getPropertyId(@RequestParam int id) {
//        String ans = new RDBMS_TO_JSON().generateJSON("select * from ownerproper");
//    }
    @GetMapping("deleteProperty1")
    public String deleteProperty(@RequestParam int id) {
        try {
            ResultSet rs = DBLoader.executeQuery("select * from ownerproperties where id = '" + id + "' ");

            if (rs.next()) {
                rs.deleteRow();
            }
            return "1";
        } catch (SQLException ex) {
            return ex.toString();
        }
    }

    @PostMapping("editProperty1")
    public String editProperty(HttpSession session, @RequestParam int id, @RequestParam String pt, @RequestParam String city, @RequestParam String price, @RequestParam String op, @RequestParam String desc, @RequestParam String add, @RequestParam String pn, @RequestParam MultipartFile pp, @RequestParam String status, @RequestParam String latitude, @RequestParam String longitude) {
        try {
            String oemail = (String) session.getAttribute("owner_email");

            ResultSet rs = DBLoader.executeQuery("select * from ownerproperties where id = '" + id + "' ");

            if (rs.next()) {
                if (oemail != null && !oemail.isEmpty()) {
                    rs.updateString("owner_email", oemail);
                }

                if (pt != null && !pt.isEmpty()) {
                    rs.updateString("property_type", pt);
                }

                if (city != null && !city.isEmpty()) {
                    rs.updateString("cityid", city);
                }

                if (price != null && !price.isEmpty()) {
                    rs.updateString("price", price);
                }

                if (op != null && !op.isEmpty()) {
                    rs.updateString("offer_price", op);
                }

                if (desc != null && !desc.isEmpty()) {
                    rs.updateString("description", desc);
                }

                if (add != null && !add.isEmpty()) {
                    rs.updateString("address", add);
                }

                if (pn != null && !pn.isEmpty()) {
                    rs.updateString("property_name", pn);
                }

                if (status != null && !status.isEmpty()) {
                    rs.updateString("status", status);
                }

                if (latitude != null && !latitude.isEmpty()) {
                    rs.updateString("latitude", latitude);
                }

                if (longitude != null && !longitude.isEmpty()) {
                    rs.updateString("longitude", longitude);
                }

                rs.updateRow();
                return "1";
            } else {
                return "0";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return ex.toString();
        }
    }

//    Select Cities from dropdown
    @GetMapping("getCities")
    @ResponseBody
    public String getCities() {
        String ans = new RDBMS_TO_JSON().generateJSON("select * from city");
        return ans;
    }

    @GetMapping("getProperties")
    @ResponseBody
    public String getProperties() {
        String ans = new RDBMS_TO_JSON().generateJSON("select * from property");
        return ans;
    }

//    @GetMapping("getOwnerEmail")
//    @ResponseBody
//    public String getOwnerEmail(HttpSession session) {
//        String email = (String) session.getAttribute("email");
//
//        if (email == null) {
//            return "";
//        }
//
//        return email;
//    }
    //OWNER EDIT PROPERTIES REST CONTROLLERS
    @GetMapping("/OwnerEditform")
    public String openEdit(@RequestParam int id, Model model) throws Exception {

        ResultSet rs = DBLoader.executeQuery("select * from ownerproperties where id = " + id);

        if (rs.next()) {
            model.addAttribute("property", rs);
        }

        return "OwnerEditForm";
    }

    @GetMapping("ShowProperty2")
    public String ShowProperty2(HttpSession session) {
        try {
            String owner_email = (String)session.getAttribute("owner_email");
            
            String ans = new RDBMS_TO_JSON().generateJSON("Select op.*, c.cityname, p.propertyname from ownerproperties op JOIN city c ON op.cityid = c.cityid JOIN property p ON op.propertyid = p.propertyid JOIN owner o ON op.owner_email = o.email where o.status = 'Active' AND op.status = 'Active' AND op.owner_email = '"+ owner_email +"'");
//            ans = ans.replace("'", "''");
            return ans;
        } catch (Exception ex) {
            return ex.toString();

        }
    }

    @PostMapping("updateproperty")
    public String updateproperty(@RequestParam String id, @RequestParam String price, @RequestParam String pn, @RequestParam String op, @RequestParam String desc, @RequestParam String add, @RequestParam String latitude, @RequestParam String longitude) {
        try {
            System.out.println("ID-----------");
            System.out.println(id);
            int idx = Integer.parseInt(id);
            ResultSet rs = DBLoader.executeQuery("select * from ownerproperties where id = " + idx);

            if (rs.next()) {
                if (price != null && !price.isEmpty()) {
                    rs.updateString("price", price);
                }

                if (pn != null && !pn.isEmpty()) {
                    rs.updateString("property_name", pn);
                }

                if (op != null && !op.isEmpty()) {
                    rs.updateString("offer_price", op);
                }

                if (desc != null && !desc.isEmpty()) {
                    rs.updateString("description", desc);
                }

                if (add != null && !add.isEmpty()) {
                    rs.updateString("address", add);
                }

//                if (status != null && !status.isEmpty()) {
//                    rs.updateString("status", status);
//                }
                if (latitude != null && !latitude.isEmpty()) {
                    rs.updateString("latitude", latitude);
                }

                if (longitude != null && !longitude.isEmpty()) {
                    rs.updateString("longitude", longitude);
                }

                rs.updateRow();
                return "1";
            } else {
                return "0";
            }
        } catch (SQLException ex) {
            return ex.toString();
        }
    }

    @PostMapping("uploadPhoto")
    public String uploadPhoto(@RequestParam int id, @RequestParam MultipartFile[] photo) {
        try {

            for (MultipartFile pic : photo) {
                if (pic == null || pic.isEmpty()) {
                    continue;
                }

                String photo_name = pic.getOriginalFilename();
                byte[] b = pic.getBytes();

                String absPath = "src/main/resources/static/myUploads/";

                FileOutputStream fos = new FileOutputStream(absPath + photo_name);
                fos.write(b);

                ResultSet rs = DBLoader.executeQuery("select * from propertiesphoto");

                rs.moveToInsertRow();
                rs.updateString("photo", "myUploads/" + photo_name);
                rs.updateInt("id", id);
                rs.insertRow();
            }

            return "1";

        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
            return "Upload Failed!";
        }
    }

    @PostMapping("getPropertyPhotos")
    public String getPropertyPhotos(@RequestParam int id) {
        String ans = new RDBMS_TO_JSON().generateJSON("select * from propertiesphoto where id = '" + id + "'");

        return ans;
    }

    @PostMapping("deletePropertyPhoto")
    public String deletePropertyPhoto(@RequestParam int id) {
        try {

            System.out.println("ID ------> " + id);
            ResultSet rs = DBLoader.executeQuery("select * from propertiesphoto where photoid = '" + id + "'");

            if (rs.next()) {
                rs.deleteRow();
                return "1";
            } else {
                return "0";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "Error: " + ex.toString();
        }
    }

    @GetMapping("getOwnerBookingHistory")
    public String getOwnerBookingHistory(HttpSession session) {
        String owner_email = (String) session.getAttribute("owner_email");
        return new RDBMS_TO_JSON().generateJSON("select * from payment where owner_email = '" + owner_email + "'");
    }
}
