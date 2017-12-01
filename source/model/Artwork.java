package model;

import javafx.scene.image.Image;

import java.time.LocalDate;

public abstract class Artwork {
	
	protected String title;
	protected StringBuilder description;
	protected LocalDate creationDate; // This should support all possible dates including stuff before 1970
	protected String creatorName;
	protected Image mainImage;
	
	Artwork(String title, StringBuilder description, LocalDate creationDate, String creatorName) {//, Image mainImage) {
		this.title = title;
		this.description = description;
		this.creationDate = creationDate;
		this.creatorName = creatorName;
		//this.mainImage = mainImage;
	}
	
	public abstract ArtworkType getType();
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDescription(StringBuilder description) {
		this.description = description;
	}
	
	public void setCreationDate(LocalDate creationDate) {
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
	
	public LocalDate getCreationDate() {
		return this.creationDate;
	}
	
	public String getCreatorName() {
		return this.creatorName;
	}
	
	public Image getMainImage() {
		return this.mainImage;
	}
	
}


