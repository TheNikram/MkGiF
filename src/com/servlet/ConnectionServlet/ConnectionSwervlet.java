package com.servlet.ConnectionServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class ConnectionSwervlet
 */
@WebServlet("/ConnectionSwervlet")
public class ConnectionSwervlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int  licznik = 0;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnectionSwervlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		licznik++;
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String login, password;
		
		if ((request.getParameter("Login") == null) || (request.getParameter("Password") == null))
		{
			login = password = "";
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			
		}
		else
		{
			login = request.getParameter("Login");
			password = request.getParameter("Password");
		}
		JSONObject json = new JSONObject();
		JSONObject jsonGlowny = new JSONObject();
		Baza b = new Baza();
//ArrayList<String> filmy= b.selectAll();
		if (b.sprawdzenieUzytkownika(login, password) == -1)
		{
			login = password = "";
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
        json.put(b.getTypeEventKsiazek(0), b.zestawienieKsiazek(b.getTypeEventKsiazek(0), b.sprawdzenieUzytkownika(login, password)));
        json.put(b.getTypeEventKsiazek(1), b.zestawienieKsiazek(b.getTypeEventKsiazek(1), b.sprawdzenieUzytkownika(login, password)));
        json.put(b.getTypeEventKsiazek(2), b.zestawienieKsiazek(b.getTypeEventKsiazek(2), b.sprawdzenieUzytkownika(login, password)));
        json.put(b.getTypeEventKsiazek(3), b.zestawienieKsiazek(b.getTypeEventKsiazek(3), b.sprawdzenieUzytkownika(login, password)));
        jsonGlowny.put("Ksiazka", json);
        json = new JSONObject();
        json.put(b.getTypeEventFilmow(0), b.zestawienieFilmow(b.getTypeEventFilmow(0), b.sprawdzenieUzytkownika(login, password)));
        json.put(b.getTypeEventFilmow(1), b.zestawienieFilmow(b.getTypeEventFilmow(1), b.sprawdzenieUzytkownika(login, password)));
        json.put(b.getTypeEventFilmow(2), b.zestawienieFilmow(b.getTypeEventFilmow(2), b.sprawdzenieUzytkownika(login, password)));
        json.put(b.getTypeEventFilmow(3), b.zestawienieFilmow(b.getTypeEventFilmow(3), b.sprawdzenieUzytkownika(login, password)));
        jsonGlowny.put("Film", json);
        json = new JSONObject();
        json.put(b.getTypeEventGier(0), b.zestawienieGier(b.getTypeEventGier(0), b.sprawdzenieUzytkownika(login, password)));
        json.put(b.getTypeEventGier(1), b.zestawienieGier(b.getTypeEventGier(1), b.sprawdzenieUzytkownika(login, password)));
        json.put(b.getTypeEventGier(2), b.zestawienieGier(b.getTypeEventGier(2), b.sprawdzenieUzytkownika(login, password)));
        json.put(b.getTypeEventGier(3), b.zestawienieGier(b.getTypeEventGier(3), b.sprawdzenieUzytkownika(login, password)));
        jsonGlowny.put("Gra", json);
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
//		String message;
//		JSONObject json = new JSONObject();
//		//json.put("name", "student");
//		JSONArray array = new JSONArray();
//		//JSONObject item = new JSONObject();
//		//	item.put("information", "test");
//		//	item.put("id", 3);
//		//	item.put("name", "course1");
//		//	json.put("course", array);
//		//array.add(item);
//		//JSONArray tablica = new JSONArray();
//		//JSONObject item = new JSONObject();
//		 /* */
//		for (int i = 0 ; i < filmy.size() ; i++)
//		{
//			array.add(i, filmy.get(i));
//		}
//		json.put("Filmy", array);
//		message = json.toString();
//
		if (b.sprawdzenieUzytkownika(request.getParameter("Login") ,request.getParameter("Password")) == -1)
		{
			jsonGlowny = new JSONObject();
		}
		out.print(jsonGlowny);
		out.flush();
		System.out.println("Licznik:" + licznik + "\t" );
		for(String string : request.getParameterMap().keySet()) {
			System.out.println(string + "  " + request.getParameter(string));

		}
		
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
