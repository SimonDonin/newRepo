package library;

public class Author {

	private String name;
	private String biography;
	
	
	
	public Author(String name, String biography) {
		this(name);
		this.biography = biography;
	}
	
	public Author(String name) {
		if (name.isBlank()) {
			System.out.println("A name of the author can't be blank "
					+ "and it is being assigned to the default value");
			this.name = "DEFAULT NAME";
		} else {
		this.name = name.toUpperCase();
		}
	}
	
	public String getName() {
		return name;
	}
	public String getBiography() {
		return biography;
	}
	
	
}
