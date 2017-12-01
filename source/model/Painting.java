package model;

import javafx.scene.image.Image;

import java.time.LocalDate;

public class Painting extends Artwork {
	
	private Integer width;
	private Integer height;
	
	public Painting(String title, StringBuilder description, LocalDate creationDate, String creatorName, Image mainImage,
	                Integer width, Integer height) {
		super(title, description, creationDate, creatorName, mainImage);
		this.width = width;
		this.height = height;
	}
	
	@Override
	public ArtworkType getType() {
		return ArtworkType.PAINTING;
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
		return title.hashCode() + creationDate.hashCode() + description.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Painting) && (obj.hashCode() == this.hashCode());
	}
	
	// TODO: 29-Nov-17 Bassam Helal need to do this 
	@Override
	public String toString() {
		return super.toString();
	}
}
