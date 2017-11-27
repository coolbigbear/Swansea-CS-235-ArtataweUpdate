package model;

import javafx.scene.image.Image;

import java.util.Date;

public abstract class Artwork {
	
	protected String title;
	protected StringBuilder description;
	protected Date creationDate;
	protected String creatorName;
	protected Image mainImage;
	
	Artwork(String title, StringBuilder description, Date creationDate, String creatorName, Image mainImage) {
		this.title = title;
		this.description = description;
		this.creationDate = creationDate;
		this.creatorName = creatorName;
		this.mainImage = mainImage;
	}
	
	public abstract ArtworkType getType();
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDescription(StringBuilder description) {
		this.description = description;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	
	public void setMainImage(Image mainImage) {
		this.mainImage = mainImage;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public StringBuilder getDescription() {
		return this.description;
	}
	
	public Date getCreationDate() {
		return this.creationDate;
	}
	
	public String getCreatorName() {
		return this.creatorName;
	}
	
	public Image getMainImage() {
		return this.mainImage;
	}
	
}


