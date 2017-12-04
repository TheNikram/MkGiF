package com.servlet.ConnectionServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Baza {
	private String typyEventKsiazek[] = {"W przyszlosci", "W trakcie", "Skonczone", "Przerwane"};
	private String typyEventFilmow[] = {"W przyszlosci", "W trakcie", "Skonczone", "Przerwane"};
	private String typyEventGier[] = {"W przyszlosci", "W trakcie", "Skonczone", "Przerwane"};
	
	public String getTypeEventKsiazek(int index)
	{
		return this.typyEventKsiazek[index];
	}

	public String getTypeEventFilmow(int index)
	{
		return this.typyEventFilmow[index];
	}
	
	public String getTypeEventGier(int index)
	{
		return this.typyEventGier[index];
	}

	private Connection connect() {
        // SQLite connection string
		Connection conn = null;
		try {
		Class.forName("org.sqlite.JDBC");
		String url = "jdbc:sqlite:" + this.getClass().getResource("//").getPath() + "Baza.db";
        
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		} catch (ClassNotFoundException ex) {
	            ex.printStackTrace();
		}
        return conn;
    }
 
	public int sprawdzenieUzytkownika(String login, String haslo)
	{
		int rezultat = -1;
		String sql = "SELECT ID FROM User WHERE Login = '" + login + "' AND Password = '" + haslo + "';";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
            	rezultat = rs.getInt("id");
                //System.out.println(rs.getInt("id"));
            }
            return rezultat;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return -1;
	}
	
    public ArrayList<String> selectAll(){
    	ArrayList<String> tablica = new ArrayList<String>();
    	String sql = "SELECT tytul FROM Filmy";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
            	tablica.add(rs.getString("tytul"));
                System.out.println(/*rs.getInt("id") +  "\t" +*/ 
                                   rs.getString("tytul"));// + "\t" +
                                   //rs.getDouble("capacity"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return tablica;
    }
    
    public JSONArray zestawienieKsiazek(String typ, int idUzytkownik)
    {
    	String sql = "SELECT Book.ID AS ID, BookEvent.Date AS Data, Book.Title AS Tytul, Book.Creator AS Tworca, "
    			+ "Rating.Rating AS Rating, BookEventType.Name AS Zdarzenie FROM BookEvent INNER JOIN Book "
    			+ "ON BookEvent.ID_Book = Book.ID INNER JOIN BookEventType ON BookEventType.ID = BookEvent.ID_BookEventType "
    			+ "INNER JOIN Rating ON Rating.ID = Book.ID_Rating WHERE BookEvent.ID_User = " + idUzytkownik
    			+ " AND BookEventType.Name = '" + typ + "'";
    	//System.out.println(sql);
    	JSONObject json;
    	JSONArray array = new JSONArray();
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
            	json = new JSONObject();
            	//tablica.add(rs.getString("tytul"));
                //System.out.println(rs.getString("Data") +  "\t" + 
                  //                 rs.getString("Tytul") + "\t" +
                    //               rs.getString("Tworca") + "\t" +
                      //             rs.getString("Rating") + "\t" +
                        //           rs.getString("Zdarzenie") + "\n");
                json.put("ID", rs.getString("ID"));
            	json.put("Data", rs.getString("Data"));
                json.put("Tytul", rs.getString("Tytul"));
                json.put("Tworca", rs.getString("Tworca"));
                json.put("Rating", rs.getString("Rating"));
                json.put("Zdarzenie", rs.getString("Zdarzenie"));
                array.add(json);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return array;
    }
    
    public JSONArray zestawienieFilmow(String typ, int idUzytkownik)
    {
    	String sql = "SELECT MovieEvent.Date AS Data, Movie.Title AS Tytul, Movie.Creator AS Tworca, "
    			+ "Rating.Rating AS Rating, MovieEventType.Name AS Zdarzenie FROM MovieEvent INNER JOIN Movie "
    			+ "ON MovieEvent.ID_Movie = Movie.ID INNER JOIN MovieEventType ON MovieEventType.ID = MovieEvent.ID_MovieEventType "
    			+ "INNER JOIN Rating ON Rating.ID = Movie.ID_Rating WHERE MovieEvent.ID_User = " + idUzytkownik
    			+ " AND MovieEventType.Name = '" + typ + "'";
    	//System.out.println(sql);
    	JSONObject json;
    	JSONArray array = new JSONArray();
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
            	json = new JSONObject();
            	//tablica.add(rs.getString("tytul"));
                System.out.println(rs.getString("Data") +  "\t" + 
                                   rs.getString("Tytul") + "\t" +
                                   rs.getString("Tworca") + "\t" +
                                   rs.getString("Rating") + "\t" +
                                   rs.getString("Zdarzenie") + "\n");
                json.put("Data", rs.getString("Data"));
                json.put("Tytul", rs.getString("Tytul"));
                json.put("Tworca", rs.getString("Tworca"));
                json.put("Rating", rs.getString("Rating"));
                json.put("Zdarzenie", rs.getString("Zdarzenie"));
                array.add(json);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return array;
    }
    
    public JSONArray zestawienieGier(String typ, int idUzytkownik)
    {
    	String sql = "SELECT GameEvent.Date AS Data, Game.Title AS Tytul, Game.Creator AS Tworca, "
    			+ "Rating.Rating AS Rating, GameEventType.Name AS Zdarzenie FROM GameEvent INNER JOIN Game "
    			+ "ON GameEvent.ID_Game = Game.ID INNER JOIN GameEventType ON GameEventType.ID = GameEvent.ID_GameEventType "
    			+ "INNER JOIN Rating ON Rating.ID = Game.ID_Rating WHERE GameEvent.ID_User = " + idUzytkownik
    			+ " AND GameEventType.Name = '" + typ + "'";
    	//System.out.println(sql);
    	JSONObject json;
    	JSONArray array = new JSONArray();
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
            	json = new JSONObject();
            	//tablica.add(rs.getString("tytul"));
                System.out.println(rs.getString("Data") +  "\t" + 
                                   rs.getString("Tytul") + "\t" +
                                   rs.getString("Tworca") + "\t" +
                                   rs.getString("Rating") + "\t" +
                                   rs.getString("Zdarzenie") + "\n");
                json.put("Data", rs.getString("Data"));
                json.put("Tytul", rs.getString("Tytul"));
                json.put("Tworca", rs.getString("Tworca"));
                json.put("Rating", rs.getString("Rating"));
                json.put("Zdarzenie", rs.getString("Zdarzenie"));
                array.add(json);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return array;
    }
    
    public void edytowanieRatinguKsiazki(int idKsiazki, int nowaOcena, int idUzytkownik)
    {
    	String sql = "SELECT * FROM Rating WHERE ID = (SELECT ID_Rating FROM Book WHERE ID = " + idKsiazki + ")";
    	String suma, ilosc, idRating;
    	System.out.println(sql);
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
            	idRating = rs.getString("ID");
            	ilosc = rs.getString("Ilosc");
            	suma = rs.getString("Suma");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }
        sql = "SELECT COUNT (*) AS Rezultat FROM BookEvent INNER JOIN BookEventType ON BookEventType.ID =  BookEvent.ID_BookEventType WHERE ID_User = " + idUzytkownik + " AND BookEventType.Name = 'Rating'";
        //int rezultat = 
        try (Connection conn = this.connect();
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)){
        	   while (rs.next()) {
               	idRating = rs.getString("Rezultat");
               	ilosc = rs.getString("Ilosc");
               	suma = rs.getString("Suma");
        	   }
           
           sql = "UPDATE Rating SET Suma = 12, Ilosc = 3, Rating = 4 WHERE ID = 1";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        Baza app = new Baza();
        //app.selectAll();
        app.sprawdzenieUzytkownika("Nikram", "nikram");
    }
}

