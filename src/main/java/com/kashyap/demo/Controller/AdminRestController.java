package com.kashyap.demo.Controller;

import org.springframework.web.bind.annotation.*;
import java.sql.*;
import com.kashyap.demo.vmm.DBLoader;
import com.kashyap.demo.vmm.RDBMS_TO_JSON;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AdminRestController {

    @PostMapping("CheckAdminLogin")
    public String checkAdminLogin(HttpSession session, @RequestParam String email, @RequestParam String pass) {
        try {
            ResultSet rs = DBLoader.executeQuery("select * from admin where admin_email = '" + email + "' and password = '" + pass + "'");
            
            if (rs.next()) {
                session.setAttribute("admin_email", email);
                return "1";
            } else {
                return "0";
            }
        } catch (SQLException ex) {
            return ex.toString();
        }
    }
    
    @GetMapping("AdminLogout")
    public void AdminLogout(HttpSession session, HttpServletResponse res) throws IOException{
        session.invalidate();
        
        res.sendRedirect("adminLogin");
    }

    @PostMapping("InsertCity")
    public String InsertCity(@RequestParam String cn, @RequestParam String sn, @RequestParam String countryn, @RequestParam String cd, @RequestParam MultipartFile cp) {
        try {
            ResultSet rs = DBLoader.executeQuery("select * from city where cityname = '" + cn + "'");

            String photo_name = cp.getOriginalFilename();
            byte[] b = cp.getBytes();

            String absPath = "src/main/resources/static/myUploads/";

            FileOutputStream fos = new FileOutputStream(absPath + photo_name);
            fos.write(b);

            if (rs.next()) {
                return "0";
            } else {
                rs.moveToInsertRow();
                rs.updateString("cityname", cn);
                rs.updateString("statename", sn);
                rs.updateString("countryname", countryn);
                rs.updateString("citydesc", cd);
                rs.updateString("cityphoto", "myUploads/" + photo_name);
                
                rs.insertRow();

                return "1";

            }
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
            return "Error: " + ex.toString();
        }
    }

    @GetMapping("ShowData")
    public String ShowData() {
        try {
            String ans = new RDBMS_TO_JSON().generateJSON("select * from city");
            return ans;
        } catch (Exception ex) {
            return ex.toString();
            
        }
    }

    @GetMapping("deleteCity")
    public String deleteCity(@RequestParam int id) {
        try {
            ResultSet rs = DBLoader.executeQuery("select * from city where cityid = '" + id + "' ");

            if (rs.next()) {
                rs.deleteRow();
            }
            return ShowData();
        } catch (SQLException ex) {
            return ex.toString();
        }
    }

    @PostMapping("editCity")
    public String editCity(@RequestParam int id, @RequestParam String cn, @RequestParam String sn, @RequestParam String countryn, @RequestParam String cd, @RequestParam MultipartFile cp) {
        try {
            ResultSet rs = DBLoader.executeQuery("select * from city where cityid = '" + id + "' ");

            if (rs.next()) {
                if (cn != null && !cn.isEmpty()) {
                    rs.updateString("cityName", cn);
                }

                if (sn != null && !sn.isEmpty()) {
                    rs.updateString("statename", sn);
                }
                
                if (countryn != null && !countryn.isEmpty()) {
                    rs.updateString("countryname", countryn);
                }
                
                if (cd != null && !cd.isEmpty()) {
                    rs.updateString("citydesc", cd);
                }

                rs.updateRow();
                return ShowData();
            } else {
                return "Row does not exist!";
            }
        } catch (SQLException ex) {
            return ex.toString();
        }
    }
    
    @PostMapping("InsertProperty")
    public String InsertProperty(@RequestParam String pn, @RequestParam String pd, @RequestParam MultipartFile pp) {
        try {
            String pn1 = pn.replace("'", "''");
            ResultSet rs = DBLoader.executeQuery("select * from property where propertyname = '" + pn1 + "'");

            String photo_name = pp.getOriginalFilename();
            byte[] b = pp.getBytes();

            String absPath = "src/main/resources/static/myUploads/";

            FileOutputStream fos = new FileOutputStream(absPath + photo_name);
            fos.write(b);

            if (rs.next()) {
                return "0";
            } else {
                rs.moveToInsertRow();
                rs.updateString("propertyname", pn);
                rs.updateString("propertydesc", pd);
                rs.updateString("propertyphoto", "myUploads/" + photo_name);
                
                rs.insertRow();

                return "1";

            }
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
            return "Error: " + ex.toString();
        }
    }
    
    @GetMapping("ShowProperty")
    public String ShowProperty() {
        try {
            String ans = new RDBMS_TO_JSON().generateJSON("select * from property");
            return ans;
        } catch (Exception ex) {
            return ex.toString();
            
        }
    }
    
    @GetMapping("deleteProperty")
    public String deleteProperty(@RequestParam int id) {
        try {
            ResultSet rs = DBLoader.executeQuery("select * from property where propertyid = '" + id + "' ");

            if (rs.next()) {
                rs.deleteRow();
            }
            return ShowProperty();
        } catch (SQLException ex) {
            return ex.toString();
        }
    }
    
     @PostMapping("editProperty")
    public String editProperty(@RequestParam int id, @RequestParam String pn, @RequestParam String pd, @RequestParam MultipartFile pp) {
        try {
            ResultSet rs = DBLoader.executeQuery("select * from property where propertyid = '" + id + "' ");

            if (rs.next()) {
                if (pn != null && !pn.isEmpty()) {
                    rs.updateString("propertyname", pn);
                }

                if (pd != null && !pd.isEmpty()) {
                    rs.updateString("propertydesc", pd);
                }

                rs.updateRow();
            }
            return "1";
        } catch (SQLException ex) {
            return ex.toString();
        }
    }
    
    @GetMapping("showOwners")
    public String showOwners() {
//        String query = "select owner.*, op.property_name from owner owner JOIN ownerproperties op ON owner.email = op.owner_email";

        String query = "select * from owner";
        
        return new RDBMS_TO_JSON().generateJSON(query);
    }
    
    @GetMapping("changeStatus")
    public String changeStatus(@RequestParam String email, @RequestParam String status) {
        try {
//            ResultSet rs = DBLoader.executeQuery("select o.*, op.* from owner o JOIN ownerproperties op ON o.email = op.owner_email where email = '"+ email +"' ");
            ResultSet rs = DBLoader.executeQuery("select * from owner where email = '"+ email +"' ");
            
            if (rs.next()) {
                rs.updateString("status", status);
                
                rs.updateRow();
                
                return "Success";
            }else {
                return "Fail";
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
            return "Error: " + ex.toString();
        }
    }
}
