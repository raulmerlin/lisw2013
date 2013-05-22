package lisw.audimetrosocial.business;

/**
 * Class that check the places mentionned by the user and match them to 
 * the region in Spain
 * @author lisw
 *
 */
public class Locale {
	
	/**
	 * Return the ISO 3166-1 representation of a location base on regular expressions; 	
	 * 
	 * @param location The location enter by the user 
	 * @return
	 */
	public static String getLocalCode(String location) {
		if(location.matches(".*Álava.*")) return "ES-PV";
		else if(location.matches(".*Albacete.*")) return "ES-CM";
		else if(location.matches(".*Alicante.*")) return "ES-VC";
		else if(location.matches(".*Almería.*")) return "ES-AN";
		else if(location.matches(".*Asturias.*")) return "ES-AS";
		else if(location.matches(".*Ávila.*")) return "ES-CL";
		else if(location.matches(".*Badajoz.*")) return "ES-EX";
		else if(location.matches(".*Balear.*")) return "ES-IB";
		else if(location.matches(".*Barcelona.*")) return "ES-CT";
		else if(location.matches(".*Burgos.*")) return "ES-CL";
		else if(location.matches(".*Cáceres.*")) return "ES-EX";
		else if(location.matches(".*Cádiz.*")) return "ES-AN";
		else if(location.matches(".*Cantabria.*")) return "ES-CB";
		else if(location.matches(".*Castellón.*")) return "ES-VC";
		else if(location.matches(".*Ciudad Real.*")) return "ES-CM";
		else if(location.matches(".*Córdoba.*")) return "ES-AN";
		else if(location.matches(".*Cuenca.*")) return "ES-CM";
		else if(location.matches(".*Girona.*")) return "ES-CT";
		else if(location.matches(".*Granada.*")) return "ES-AN";
		else if(location.matches(".*Guadalajara.*")) return "ES-CM";
		else if(location.matches(".*Gipuzkoa.*")) return "ES-PV";
		else if(location.matches(".*Huelva.*")) return "ES-AN";
		else if(location.matches(".*Huesca.*")) return "ES-AR";
		else if(location.matches(".*Jaén.*")) return "ES-AN";
		else if(location.matches(".*La Rioja.*")) return "ES-RI";
		else if(location.matches(".*Las Palmas.*")) return "ES-CN";
		else if(location.matches(".*León.*")) return "ES-CL";
		else if(location.matches(".*Lleida.*")) return "ES-CT";
		else if(location.matches(".*Lugo.*")) return "ES-GA";
		else if(location.matches(".*Madrid.*")) return "ES-MD";
		else if(location.matches(".*Málaga.*")) return "ES-AN";
		else if(location.matches(".*Murcia.*")) return "ES-MC";
		else if(location.matches(".*Navarra.*")) return "ES-NC";
		else if(location.matches(".*Ourense.*")) return "ES-GA";
		else if(location.matches(".*Palencia.*")) return "ES-CL";
		else if(location.matches(".*Pontevedra.*")) return "ES-GA";
		else if(location.matches(".*Salamanca.*")) return "ES-CL";
		else if(location.matches(".*Tenerife.*")) return "ES-CN";
		else if(location.matches(".*Segovia.*")) return "ES-CL";
		else if(location.matches(".*Sevilla.*")) return "ES-AN";
		else if(location.matches(".*Soria.*")) return "ES-CL";
		else if(location.matches(".*Tarragona.*")) return "ES-CT";
		else if(location.matches(".*Teruel.*")) return "ES-AR";
		else if(location.matches(".*Toledo.*")) return "ES-CM";
		else if(location.matches(".*Valencia.*")) return "ES-VC";
		else if(location.matches(".*Vizcaya.*")) return "ES-PV";
		else if(location.matches(".*Zamora.*")) return "ES-CL";
		else if(location.matches(".*Zaragoza.*")) return "ES-AR";
		else if(location.matches(".*Andalucía.*")) return "ES-AN";
		else if(location.matches(".*Aragón.*")) return "ES-AR";
		else if(location.matches(".*Asturias.*")) return "ES-AS";
		else if(location.matches(".*Canarias.*")) return "ES-CN";
		else if(location.matches(".*Cantabria.*")) return "ES-CB";
		else if(location.matches(".*Mancha.*")) return "ES-CM";
		else if(location.matches(".*Catal.*")) return "ES-CT";
		else if(location.matches(".*Extrem.*")) return "ES-EX";
		else if(location.matches(".*Galicia.*")) return "ES-GA";
		else if(location.matches(".*Balea.*")) return "ES-IB";
		else if(location.matches(".*Rioja.*")) return "ES-RI";
		else if(location.matches(".*Navarra.*")) return "ES-NC";
		else if(location.matches(".*Vasco.*")) return "ES-PV";
		else if(location.matches(".*Euska.*")) return "ES-PV";
		else if(location.matches(".*Ceuta.*")) return "ES-CE";
		else if(location.matches(".*Melilla.*")) return "ES-ML";

		// AÑADIDOS A MANO, QUE SE HAN ENCONTRADO MUCHOS
		else if(location.matches(".*Canar.*")) return "ES-CN";
		else if(location.matches(".*Mallor.*")) return "ES-IB";
		else if(location.matches(".*Menorc.*")) return "ES-IB";
		else return "";
	}

}
