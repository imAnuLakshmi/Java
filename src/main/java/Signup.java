
import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.servlet.*;
import java.sql.*;

@MultipartConfig(maxFileSize = 16177215)

public class Signup extends HttpServlet {
    	private static final int BUFFER_SIZE = 4096;
	@Override

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {

			System.out.println("Class Not found");
		}
		System.out.println("class loaded");
		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/testdb", "postgres", "");

		} catch (Exception e) {

			System.out.println("conection failed");

		}
		if (connection != null) {
			System.out.println("Opened database successfully");
			try {
				Statement stmt = connection.createStatement();
                String image=uploadImage(request);
				String sql = "INSERT INTO  player_info (player_name,player_mail,player_password,player_profile) VALUES ('"
						+ request.getParameter("name")
						+ "','"
						+ request.getParameter("mail")
						+ "','"
						+ request.getParameter("password")+ "','"
						+image + "');";

				stmt.executeUpdate(sql);
		    	CookieCreator one = new CookieCreator();
                one.createContext("gc_account", request.getParameter("name"),request,response);
				writer.write(image);
				//response.sendRedirect("http://anulakshmim-5073.zcodeusers.com/home");
				

			} catch (SQLException e) {

				if ((e + "").contains("player_info_player_name_key")) {
					writer.write("Name already Exist");
				}
				if ((e + "").contains("player_info_player_mail_key")) {
					writer.write("Mail already Exist");
				}
			}

		} else {
			System.out.println("something iswrong");
		      }

	}
	public String uploadImage(HttpServletRequest request)throws ServletException, IOException{
	    String filePath ="/home/workspace/JavaWeb/webapps/JavaWeb/images"; 
        InputStream inputStream = null; // input stream of the upload file
         
        // obtains the upload file part in this multipart request
        Part filePart   =   request.getPart("photo");
        String fileType = null; 
        if (filePart != null) {
            fileType    =   filePart.getContentType().substring(6);
            // prints out some information for debugging
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());
             
            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        }
        System.out.print(fileType);
        OutputStream outputStream = null;
        
                String randomFileName= "/"+(int)(Math.random()*500000+1);
                File file = new File(filePath+randomFileName+"."+fileType);
                System.out.println(filePath+randomFileName+"."+fileType);
                while(!file.createNewFile()){
                    randomFileName= "/"+(int)(Math.random()*500000+1);
                    file = new File(filePath+randomFileName+"."+fileType);
                }
                
                outputStream = new FileOutputStream(file);
                int bytesRead = -1;
                byte[] buffer = new byte[BUFFER_SIZE];
                System.out.println(" passed byte "+inputStream.toString()+" ");
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
 
                inputStream.close();
                outputStream.close();
                System.out.println("File saved"); 
                return randomFileName+"."+fileType;
	}
	
}