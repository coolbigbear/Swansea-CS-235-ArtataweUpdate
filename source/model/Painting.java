package model;

import javafx.scene.image.Image;

import java.util.Date;

public class Painting extends Artwork {
	
	private Integer width;
	private Integer height;
	
	public Painting(String title, StringBuilder description, Date creationDate, String creatorName, Image mainImage,
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
	
}
