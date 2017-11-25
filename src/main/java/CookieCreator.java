

import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;

public class CookieCreator extends HttpServlet{
     public void createContext(String cookie_name,String id,HttpServletRequest req,HttpServletResponse res){
        String random           =   ""+(int)(Math.random()*500000+100000);
        System.out.println(random);
        Cookie cookie           =   new Cookie(cookie_name,random);
        ServletContext context  =   getServletContext(); 
       // ServletContext context  =   req.getServletContext();
        HashMap<String,String> map;
        if(context.getAttribute("cookie")== null){
            map =  new HashMap<String,String>();
        }else{
            map         =   (HashMap<String,String>) context.getAttribute("cookie");
            while(map.containsKey(random)){
                random  =   ""+(int)(Math.random()*500000+100000);
            }
            
        }
        map.put(random, id);
        context.setAttribute("cookie",map);
        res.addCookie(cookie);
        System.out.println("ko");
        
    }
}