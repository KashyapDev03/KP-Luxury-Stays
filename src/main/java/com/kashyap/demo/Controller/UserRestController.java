package com.kashyap.demo.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.sql.*;
import com.kashyap.demo.vmm.DBLoader;
import com.kashyap.demo.vmm.RDBMS_TO_JSON;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class UserRestController {

    @PostMapping("Signup")
    public String Signup(@RequestParam String name, @RequestParam String email, @RequestParam String pass, @RequestParam String contact, @RequestParam String add) {
        try {
            ResultSet rs = DBLoader.executeQuery("select * from user where uemail = '" + email + "' ");

            if (rs.next()) {
                return "0";
            } else {

//                String photo_name = photo.getOriginalFilename();
//                byte[] b = photo.getBytes();
//
//                String absPath = "src/main/resources/static/myUploads/";
//
//                FileOutputStream fos = new FileOutputStream(absPath + photo_name);
//                fos.write(b);

                rs.moveToInsertRow();

                rs.updateString("uname", name);

                rs.updateString("uemail", email);

                rs.updateString("upassword", pass);

//                rs.updateString("uphoto", "myUploads/" + photo_name);

                rs.updateString("ucontact", contact);

                rs.updateString("uaddress", add);

                rs.insertRow();

                return "1";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return ex.toString();
        }
    }

    @PostMapping("Login")
    public String Login(HttpSession session, @RequestParam String email, @RequestParam String pass) {
        try {
            session.setAttribute("user_email", email);
            ResultSet rs = DBLoader.executeQuery("select * from user where uemail = '" + email + "' AND upassword = '" + pass + "' ");

            if (rs.next()) {
                return "0";
            } else {
                return "1";
            }
        } catch (SQLException ex) {
            return ex.toString();
        }
    }

    @GetMapping("UserLogout")
    public void UserLogout(HttpSession session, HttpServletResponse res) throws IOException {
        session.invalidate();

        res.sendRedirect("/");
    }

    @GetMapping("showCities")
    public String showCities() {
        String ans = new RDBMS_TO_JSON().generateJSON("select * from city");

        return ans;
    }

    @GetMapping("getCityProperties")
    public String getCityProperties(@RequestParam int cityid, HttpSession session) {
        try {
            String owner_email = (String)session.getAttribute("owner_email");
//            System.out.println("Owner_email: ===============> " + owner_email);

            String query = "select op.*, c.cityname from ownerproperties op JOIN city c ON op.cityid = c.cityid JOIN owner o ON op.owner_email = o.email where op.cityid = '" + cityid + "' AND op.status = 'Active' AND o.status = 'Active' ";

            String ans = new RDBMS_TO_JSON().generateJSON(query);

            return ans;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return "Error: " + ex.toString();
        }
    }

    @GetMapping("showlocation")
    public String showlocation(@RequestParam String cityid) {
        int id = Integer.parseInt(cityid);
        String query = "select cityname, statename, countryname from city where cityid = '" + id + "'";

        return new RDBMS_TO_JSON().generateJSON(query);
    }

    @GetMapping("showproperty")
    public String showproperty(@RequestParam int cityid) {
        return new RDBMS_TO_JSON().generateJSON("select op.* from ownerproperties op JOIN owner o ON op.owner_email = o.email where op.cityid = '" + cityid + "' AND o.status = 'Active' AND op.status = 'Active'");
    }

    @GetMapping("showtext")
    public String showtext(@RequestParam int pid) {
//        String query = "select p.*, op.property_name from property p JOIN ownerproperties op ON p.propertyid = op.propertyid where p.propertyid = '" + pid + "'";
        String query = "select * from ownerproperties where id = '"+ pid +"' AND status = 'Active'";
        return new RDBMS_TO_JSON().generateJSON(query);
    }

    @GetMapping("getPropertyPrice")
    public String getPropertyPrice(@RequestParam int pid) {
        String query = "select * from ownerproperties where id = '"+ pid +"' AND status = 'Active'";
//        return new RDBMS_TO_JSON().generateJSON("select op.* from ownerproperties op JOIN owner o ON op.owner_email = o.email where propertyid = '" + pid + "' AND o.status = 'Active'");
        return new RDBMS_TO_JSON().generateJSON(query);
    }

    @GetMapping("showMap")
    public String showMap(@RequestParam int propertyId) {
        String query = "select * from ownerproperties where id = '"+ propertyId +"'";
//        return new RDBMS_TO_JSON().generateJSON("select op.* from ownerproperties op JOIN owner o ON op.owner_email = o.email where propertyid = '" + propertyId + "' AND o.status = 'Active' ");
        return new RDBMS_TO_JSON().generateJSON(query);
    }

    @PostMapping("showPropertyPhotos")
    public String showPropertyPhotos(@RequestParam int propertyId) {
        String query = "select * from propertiesphoto where id = '" + propertyId + "'";
        return new RDBMS_TO_JSON().generateJSON(query);
    }

    @PostMapping("payment")
    @ResponseBody
    public String payment(HttpSession session, int detail_id, int total_price, String startDate, String endDate) {
        try {
//            String user_email = (String) session.getAttribute("user_email");
//
//            if (user_email == null) {
//                return "0";
//            }
//            String query = "select * from ownerproperties where propertyid = '" + detail_id + "'";
//            ResultSet rs = DBLoader.executeQuery(query);

//            String owner_email = "";
//            if (rs.next()) {
//                owner_email = rs.getString("owner_email");
//            }
//            ResultSet checkRS = DBLoader.executeQuery(
//                    "select * from payment "
//                    + "where owner_property_id = '" + detail_id + "' "
//                    + "AND STR_TO_DATE(start_date,'%m/%d/%Y') <= STR_TO_DATE('" + endDate + "','%m/%d/%Y') "
//                    + "AND STR_TO_DATE(end_date,'%m/%d/%Y') >= STR_TO_DATE('" + startDate + "','%m/%d/%Y')"
//            );
            ResultSet checkRS = DBLoader.executeQuery("select * from payment where owner_property_id = '" + detail_id + "' AND start_date <= '" + endDate + "' AND end_date >= '" + startDate + "'");

            if (checkRS.next()) {

                System.out.println("BOOKED RECORD FOUND");

                System.out.println("FrontEnd START DATE ---> " + startDate);
                System.out.println("FrontEnd END DATE ---> " + endDate);

                System.out.println("DB START DATE ---> " + checkRS.getString("start_date"));
                System.out.println("DB END DATE ---> " + checkRS.getString("end_date"));
                return "BOOKED";
            } else {
//                ResultSet paymentRs = DBLoader.executeQuery("select * from payment");
//                paymentRs.moveToInsertRow();
//
//                paymentRs.updateString("owner_email", owner_email);
//                paymentRs.updateString("user_email", user_email);
//                paymentRs.updateInt("owner_property_id", detail_id);
//                paymentRs.updateInt("total_price", total_price);
//                paymentRs.updateString("payment_type", "Cash");
//                paymentRs.updateString("address", "Temporary Address");
//                paymentRs.updateString("start_date", startDate);
//                paymentRs.updateString("end_date", endDate);
//
//                paymentRs.insertRow();

                return "1";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "Error: " + ex.toString();
        }
    }

    @GetMapping("/cash")
    public String payment(@RequestParam int detail_id, @RequestParam int grand, @RequestParam String start,
            @RequestParam String end, HttpSession session, @RequestParam String type) {
        System.out.println("Detail Id --------------------> " + detail_id);
        String ans = "";
        int payment_id = 0;
        String owner_email = "";
        String address = "";
        String user_email = (String) session.getAttribute("user_email");
        try {
            ResultSet rs1 = DBLoader.executeQuery("select * from ownerproperties where id=" + detail_id);
            if (rs1.next()) {
                owner_email = rs1.getString("owner_email");
                address = rs1.getString("address");
            }
            ResultSet rs2 = DBLoader.executeQuery("select * from payment");
            rs2.moveToInsertRow();
            rs2.updateString("owner_email", owner_email);
            rs2.updateString("user_email", user_email);
            rs2.updateInt("owner_property_id", detail_id);
            rs2.updateInt("total_price", grand);
            rs2.updateString("payment_type", type);
            rs2.updateString("address", address);
            rs2.updateString("start_date", start);
            rs2.updateString("end_date", end);
            rs2.insertRow();
            ResultSet rs3 = DBLoader.executeQuery("select max(id) as id from payment");
            if (rs3.next()) {
                payment_id = rs3.getInt("id");
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate ldStartDate = LocalDate.parse(start, formatter);
            LocalDate ldEndDate = LocalDate.parse(end, formatter);
            for (LocalDate date = ldStartDate; date.isBefore(ldEndDate); date = date.plusDays(1)) {
                ResultSet rs4 = DBLoader.executeQuery("select * from payment_detail");
                rs4.moveToInsertRow();
                rs4.updateString("start_date", date.format(formatter));
                rs4.updateString("end_date", date.plusDays(1).format(formatter));
                rs4.updateInt("payment_id", payment_id);
                rs4.insertRow();

            }

//            sendemail obj = new sendemail(user_email, 
//    "Booking Confirmation - Bon Voyage", 
//    "Dear " + user_email + ",\n\n" +
//    "We are delighted to confirm your booking! Below are the details of your reservation:\n\n" +
//    "Booking Details:\n" +
//    "Property Address: " + address + "\n" +
//    "Check-in Date: " + start + "\n" +
//    "Check-out Date: " + end + "\n" +
//    "Total Price: RS. " + grand + "\n" +
//    "Payment Method: " + type + "\n\n" +
//    "Additional Information:\n" +
//    "Please ensure you arrive at the property on the check-in date specified above. If you have any special requests or need assistance during your stay, feel free to contact the property owner at: " + owner_email + ".\n" +
//    "If you need to modify or cancel your booking, kindly notify us at least 48 hours in advance.\n\n" +
//    "Thank you for choosing our platform for your stay. We hope you have a wonderful experience!\n\n" +
//    "Best regards,\n" +
//    "The Airbnb Team");
            ans = ans + "success";
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.toString();
        }

        return ans;
    }

    @GetMapping("getUserBookingHistory")
    public String getUserBookingHistory(HttpSession session) {
        String user_email = (String) session.getAttribute("user_email");
        System.out.println("User Email ==============> " + user_email);
        return new RDBMS_TO_JSON().generateJSON("select * from payment where user_email = '" + user_email + "'");
    }

    @PostMapping("checkAvailability")
    @ResponseBody
    public String checkAvailability(HttpSession session, int detail_id, String startDate, String endDate) {
        try {

            String user_email = (String) session.getAttribute("user_email");

            if (user_email == null) {
                return "0";
            }

            String query = "select pd.* from payment_detail pd JOIN payment p ON pd.payment_id = p.id where p.owner_property_id = '"+ detail_id +"' AND STR_TO_DATE(pd.start_date,'%m/%d/%Y') < STR_TO_DATE('" + endDate + "','%m/%d/%Y') AND STR_TO_DATE(pd.end_date,'%m/%d/%Y') > STR_TO_DATE('" + startDate + "','%m/%d/%Y')";
//            ResultSet checkRS = DBLoader.executeQuery(
//                    "SELECT pd.* "
//                    + "FROM payment_detail pd "
//                    + "INNER JOIN payment p ON pd.payment_id = p.id "
//                    + "WHERE p.owner_property_id = '" + detail_id + "' "
//                    + "AND STR_TO_DATE(pd.start_date,'%m/%d/%Y') < STR_TO_DATE('" + endDate + "','%m/%d/%Y') "
//                    + "AND STR_TO_DATE(pd.end_date,'%m/%d/%Y') > STR_TO_DATE('" + startDate + "','%m/%d/%Y')"
//            );
            
            ResultSet checkRS = DBLoader.executeQuery(query);

            if (checkRS.next()) {

                return "BOOKED";

            } else {

                return "AVAILABLE";
            }

        } catch (Exception ex) {

            ex.printStackTrace();

            return ex.toString();
        }
    }
    
    @GetMapping("showOwnerName") 
    public String showOwnerName(@RequestParam int id) {
        String query = "select op.owner_email, o.name from ownerproperties op JOIN owner o ON op.owner_email = o.email where op.id = '"+ id +"'";
        return new RDBMS_TO_JSON().generateJSON(query);
    }

}
