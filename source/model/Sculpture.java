package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: 04-Dec-17 Documentation!
public class Sculpture extends Artwork {
	
	private Integer width;
	private Integer height;
	private Integer depth;
	private String mainMaterial;
	private List<String> additionalImagesPaths;
	
	public Sculpture(String title, StringBuilder description, LocalDate creationDate, String creatorName,
	                 String mainImagePath, Integer width, Integer height, Integer depth, String mainMaterial) {
		super(title, description, creationDate, creatorName, mainImagePath);
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.mainMaterial = mainMaterial;
		this.type = ArtworkType.Sculpture;
		this.additionalImagesPaths = new ArrayList<>();
	}
	
	public Sculpture(String title, StringBuilder description, LocalDate creationDate, String creatorName,
	                 String mainImagePath, Integer width, Integer height, Integer depth, String mainMaterial,
	                 String... additionalImagesPaths) {
		super(title, description, creationDate, creatorName, mainImagePath);
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.mainMaterial = mainMaterial;
		this.type = ArtworkType.Sculpture;
		this.additionalImagesPaths = Arrays.asList(additionalImagesPaths);
	}
	
	public void addAdditionalImages(String... images) {
		this.additionalImagesPaths.addAll(Arrays.asList(images));
	}
	
	public void setWidth(Integer width) {
		this.width = width;
	}
	
	public void setHeight(Integer height) {
		this.height = height;
	}
	
	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	
	public void setMainMaterial(String mainMaterial) {
		this.mainMaterial = mainMaterial;
	}
	
	public void setAdditionalImagesPaths(List<String> additionalImagesPaths) {
		this.additionalImagesPaths = additionalImagesPaths;
	}
	
	public Integer getWidth() {
		return this.width;
	}
	
	public Integer getHeight() {
		return this.height;
	}
	
	public Integer getDepth() {
		return this.depth;
	}
	
	public String getMainMaterial() {
		return this.mainMaterial;
	}
	
	public List<String> getAdditionalImagesPaths() {
		return this.additionalImagesPaths;
	}
	
	@Override
	public ArtworkType getType() {
		return ArtworkType.Sculpture;
	}
	
	@Override
	public int hashCode() {
		return this.title.hashCode() + this.creationDate.hashCode() + this.description.toString().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Sculpture) && (obj.hashCode() == this.hashCode());
	}
	
	@Override
	public String toString() {
		return this.type.toString() + ": \n" +
				"\tTitle: " + this.title + "\n" +
				"\tCreator Name: " + this.creatorName + "\n" +
				"\tCreation Date: " + this.creationDate + "\n" +
				"\tWidth: " + this.width + "\n" +
				"\tHeight: " + this.height + "\n" +
				"\tDepth: " + this.depth + "\n" +
				"\tMain Material: " + this.mainMaterial + "\n";
	}
}
