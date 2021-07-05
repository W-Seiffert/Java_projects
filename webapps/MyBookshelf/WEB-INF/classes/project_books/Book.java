package project_books;

// eine JavaBean für die Webanwendung
public class Book {

    // Variablen-Setting, alle Variablen "private"
	private int id;
	private String nameFam;
	private String nameFirst;
	private String title;
	private String placePubl;
	private int yearPubl;
	
	// Konstruktoren
	public Book() {
	   this(0, "", "", "", "", 0);
	}
	
	public Book(int id, String nameFam, String nameFirst, String title, String placePubl, int yearPubl) {
	    this.id = id;
		this.nameFam = nameFam;
		this.nameFirst = nameFirst;
		this.title = title;
		this.placePubl = placePubl;
		this.yearPubl = yearPubl;
	}
	
	// Getter- und Settermethoden, "public"
	
	public int getId() {
		return id;
	}
	public void setId(int value) {
		id = value;
	}

	public String getNameFam() {
		return nameFam;
	}
	public void setNameFam(String value) {
		nameFam = value;
	}
	
	public String getNameFirst() {
		return nameFirst;
	}
	public void setNameFirst(String value) {
	  nameFirst = value;	
	}
	
	public String getTitle() {
	    return title;	
	}
	public void setTitle(String value) {
		title = value;
	}
	
	public String getPlacePubl() {
	    return placePubl;
	}
	public void setPlacePubl(String value) {
	    placePubl = value;	
	}
	
	public int getYearPubl() {
		return yearPubl;
	}    
	public void setYearPubl(int value){	
	    yearPubl = value;
	}
	
}