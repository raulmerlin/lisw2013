package lisw.audimetrosocial.business;

public enum ShowList {

	/**
	 * Hashtag for The Walking Dead
	 */
	WALKING_DEAD("#thewalkingdead"),
	
	/**
	 * Hashtag for The Simpsons
	 */
	SIMPSON("#thesimpsons"),
	
	/**
	 * Hashtag for Spain
	 */
	SPAIN("#spain"),
	
	/**
	 * Hashtag for TVE
	 */
	TVE("#tve"),
	
	/**
	 * Hashtag for Tele Cinco
	 */
	TELECINCO("#telecinco"),
	
	/**
	 * Hashtag for La Sexta
	 */
	LASEXTA("#lasexta");
	
	/**
	 * The hashtag associated to the constant
	 */
	private String hashtag;
	
	/**
	 * Default constructor
	 * @param value of the hashtag
	 */
	private ShowList(String value){
		this.hashtag = value;
	}
	
	/**
	 * Get the hashtag of the enumeration field.
	 * 
	 * @return The {@link #hashtag hashtag} associated to the enumeration field.
	 */
	public String getHashtag(){
		return this.hashtag;
	}
	
	/**
	 * Check if a hashtag is associated to one of the value of the enumeration
	 * 
	 * @param hashtag that we are looking for.
	 * @return The hashtag associated if it exists, null otherwise.
	 */
	public static String isHashtag(String hashtag){
		if (WALKING_DEAD.getHashtag().equalsIgnoreCase(hashtag)){
			return WALKING_DEAD.getHashtag();
		}
		if (SIMPSON.getHashtag().equalsIgnoreCase(hashtag)){
			return SIMPSON.getHashtag();
		}
		if (SPAIN.getHashtag().equalsIgnoreCase(hashtag)){
			return SPAIN.getHashtag();
		}
		if (TVE.getHashtag().equalsIgnoreCase(hashtag)){
			return TVE.getHashtag();
		}
		if (TELECINCO.getHashtag().equalsIgnoreCase(hashtag)){
			return TELECINCO.getHashtag();
		}
		if (LASEXTA.getHashtag().equalsIgnoreCase(hashtag)){
			return LASEXTA.getHashtag();
		}
		return null;
	}
}
