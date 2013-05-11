package lisw.audimetrosocial.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lisw.audimetrosocial.business.TwitterQuery;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Servlet implementation class ObtenerDatos
 */
@WebServlet("/ObtenerDatos")
public class ObtenerDatos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, Integer> datosMapa = new HashMap<String, Integer>();
	private Map<String, Integer> datosHora = new HashMap<String, Integer>();
	private Map<String, Integer> datosProgramas = new HashMap<String, Integer>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObtenerDatos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String hashtag1 = request.getParameter("primerHashtag");
		if(hashtag1 == null) {
			hashtag1 = "#granhotel";
		}
		TwitterQuery twitterQuery = new TwitterQuery();
		String responseText = twitterQuery.query(hashtag1);
		datosMapa = twitterQuery.getDatosMapa();
		datosHora = twitterQuery.getDatosHora();
		datosProgramas = twitterQuery.getDatosProgramas();
		int count = twitterQuery.getCount();
		
		request.setAttribute("responseText", responseText);
		request.setAttribute("count", count);
		request.setAttribute("datosMapa", datosMapa);
		request.setAttribute("datosHora", datosHora);
		request.setAttribute("datosProgramas", datosProgramas);
		
		this.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}