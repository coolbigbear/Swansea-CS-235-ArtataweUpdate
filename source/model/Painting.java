package model;

import java.time.LocalDate;

// TODO: 04-Dec-17 Documentation!
public class Painting extends Artwork {
	
	private Integer width;
	private Integer height;
	
	public Painting(String title, StringBuilder description, LocalDate creationDate,
	                String creatorName, String mainImagePath, Integer width, Integer height) {
		super(title, description, creationDate, creatorName, mainImagePath);
		this.width = width;
		this.height = height;
		this.type = ArtworkType.Painting;
	}

	@Override
	public ArtworkType getType() {
		return ArtworkType.Painting;
	}
	
	public void setWidth(Integer width) {
		this.width = width;
	}
	
	public void setHeight(Integer height) {
		this.height = height;
	}
	
	public Integer getWidth() {
		return this.width;
	}
	
	public Integer getHeight() {
		return this.height;
	}
	
	@Override
	public int hashCode() {
		return this.title.hashCode() + this.creationDate.hashCode() + this.description.toString().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Painting) && (obj.hashCode() == this.hashCode());
	}
	
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
