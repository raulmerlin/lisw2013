package lisw.audimetrosocial.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lisw.audimetrosocial.business.TwitterQuery;

/**
 * Servlet implementation class ObtenerDatos
 */
@WebServlet("/ObtenerDatos")
public class ObtenerDatos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, Integer> datosMapa;
	private Map<String, Integer> datosHora;
	private Map<String, Integer> datosProgramas;
       
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
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("HH");
		String currentHour = dateFormatter.format(new Date());
		int startingHour = Integer.parseInt(currentHour) + 1;
		dateFormatter.applyPattern("mm");
		String currentMinute = dateFormatter.format(new Date());
		int startingMinute = Integer.parseInt(currentMinute) - 15;
		int startingHourForMinuteView = Integer.parseInt(currentHour);
		
		if (startingMinute < 0){
			startingMinute = 60 + startingMinute;
			startingHourForMinuteView--;
		}
		
		request.setAttribute("startingHour", startingHour);
		request.setAttribute("hashtag", hashtag1);
		request.setAttribute("responseText", responseText);
		request.setAttribute("count", count);
		request.setAttribute("datosMapa", datosMapa);
		request.setAttribute("datosHora", datosHora);
		request.setAttribute("datosProgramas", datosProgramas);
		request.setAttribute("quinzeMinutos", twitterQuery.getQuinzeMinutos());
		request.setAttribute("startingMinute", startingMinute);
		request.setAttribute("startingHourForMinuteView", startingHourForMinuteView);
		
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