package model;

import java.time.LocalDate;

// TODO: 04-Dec-17 Documentation!
public abstract class Artwork {

	protected String title;
	protected StringBuilder description;
	protected String creationDate;
	protected String creatorName;
	protected String mainImagePath;
	protected ArtworkType type;

	public Artwork(String title, StringBuilder description, String creationDate, String creatorName, String mainImagePath) {
		this.title = title;
		this.description = description;
		this.creationDate = creationDate;
		this.creatorName = creatorName;
		this.mainImagePath = mainImagePath;
	}

	public abstract ArtworkType getType();

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(StringBuilder description) {
		this.description = description;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public void setMainImagePath(String mainImagePath) {
		this.mainImagePath = mainImagePath;
	}

	public String getTitle() {
		return this.title;
	}

	public StringBuilder getDescription() {
		return this.description;
	}

	public String getCreationDate() {
		return this.creationDate;
	}
	
	public String getCreatorName() {
		return this.creatorName;
	}
	
	public String getMainImagePath() {
		return this.mainImagePath;
	}
	
}


