package model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Sculpture extends Artwork {
	
	private Integer width;
	private Integer height;
	private Integer depth;
	private String mainMaterial;
	private List<Image> additionalImages;
	
	public Sculpture(String title, StringBuilder description, Date creationDate, String creatorName, Image mainImage,
	          Integer width, Integer height, Integer depth, String mainMaterial) {
		super(title, description, creationDate, creatorName, mainImage);
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.mainMaterial = mainMaterial;
		this.additionalImages = new ArrayList<>();
	}
	
	public void addAdditionalImages(Image... images) {
		this.additionalImages.addAll(Arrays.asList(images));
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
	
	public void setAdditionalImages(List<Image> additionalImages) {
		this.additionalImages = additionalImages;
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
	
	public List<Image> getAdditionalImages() {
		return this.additionalImages;
	}
	
	@Override
	public ArtworkType getType() {
		return ArtworkType.SCULPTURE;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
