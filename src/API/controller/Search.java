package API.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@WebServlet("/search.do")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
		
        String clientId = "yxO7C6C5LsdJtbHkbfHs";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "TOjCC9nvUj";//애플리케이션 클라이언트 시크릿값";
        try {
        	String bookName = request.getParameter("bookName");
            String text = URLEncoder.encode(bookName, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/movie.json?query="+ text; // json 결과과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer responseStr = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                responseStr.append(inputLine);
            }
            br.close();
            
            System.out.println(responseStr.toString());


            request.setAttribute("responseStr", responseStr.toString());
            
            // 결과 페이지로 이동
            String path = "bookResult.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);     
			
        } catch (Exception e) {
            System.out.println(e);
        }		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
