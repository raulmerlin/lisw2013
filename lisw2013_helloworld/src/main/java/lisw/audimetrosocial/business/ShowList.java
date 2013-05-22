package lisw.audimetrosocial.business;

public enum ShowList {

	WALKING_DEAD("#thewalkingdead"),
	SIMPSON("#thesimpsons"),
	SPAIN("#spain"),
	TVE("#tve"),
	TELECINCO("#telecinco"),
	LASEXTA("#lasexta");
	
	private String hashtag;
	
	private ShowList(String value){
		this.hashtag = value;
	}
	
	public String getHashtag(){
		return this.hashtag;
	}
	
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
