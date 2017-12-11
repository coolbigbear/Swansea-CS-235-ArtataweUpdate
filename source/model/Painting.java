package model;

/**
 * A Painting is a type of Artwork in the Artatawe system.
 *
 * A Painting has all the attributes and behaviour of an Artwork and adds a width and a height,
 * both represented with Integers.
 *
 * @author Bassam Helal
 * @author ***REMOVED*** ***REMOVED***
 * @version 1.1
 * @see Artwork
 */
public class Painting extends Artwork {
	
	private Integer width;
	private Integer height;
	
	/**
	 * The primary and only constructor of a Painting, this is used to create a new Painting.
	 *
	 * @param title the String representing the title of the Painting
	 * @param description the StringBuilder representing the description of the Painting
	 * @param creationDate the String representing the creation date of the Painting
	 * @param creatorName the String representing the creator name of the Painting
	 * @param mainImagePath the String representing the path of the main image of the Painting
	 * @param width the Integer representing the width of the Painting
	 * @param height the Integer representing the height of the Painting
	 */
	public Painting(String title, StringBuilder description, String creationDate,
	                String creatorName, String mainImagePath, Integer width, Integer height) {
		super(title, description, creationDate, creatorName, mainImagePath);
		this.width = width;
		this.height = height;
		this.type = ArtworkType.Painting;
	}
	
	/**
	 * Gets the ArtworkType of the Painting, will always return <code>ArtworkType.Painting</code>
	 *
	 * @return ArtworkType.Painting
	 *
	 * @see ArtworkType#Painting
	 */
	@Override
	public ArtworkType getType() {
		return ArtworkType.Painting;
	}
	
	/**
	 * Sets the width of the Painting
	 *
	 * @param width the integer representing the width of the Painting
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}
	
	/**
	 * Sets the height of the Painting
	 *
	 * @param height the integer representing the height of the Painting
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}
	
	/**
	 * Gets the width of the Painting
	 *
	 * @return the integer representing the width of the Painting
	 */
	public Integer getWidth() {
		return this.width;
	}
	
	/**
	 * Gets the height of the Painting
	 *
	 * @return the integer representing the height of the Painting
	 */
	public Integer getHeight() {
		return this.height;
	}
	
	/**
	 * Gets the hashcode of the Painting
	 *
	 * @return the integer representing the hashcode of the Painting
	 *
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.title.hashCode() + this.creationDate.hashCode() + this.description.toString().hashCode();
	}
	
	/**
	 * Checks to see if the passed in Object is equals to this instance
	 *
	 * @param obj the Object to compare with
	 *
	 * @return true if equal, false otherwise
	 *
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Painting) && (obj.hashCode() == this.hashCode());
	}
	
	/**
	 * Gets the String representation of the Painting
	 *
	 * @return the String representation of the Painting
	 *
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		return this.type.toString() + ": \n" +
				"\tTitle: " + this.title + "\n" +
				"\tCreator Name: " + this.creatorName + "\n" +
				"\tCreation Date: " + this.creationDate + "\n" +
				"\tWidth: " + this.width + "\n" +
				"\tHeight: " + this.height + "\n";
		
	}
}
