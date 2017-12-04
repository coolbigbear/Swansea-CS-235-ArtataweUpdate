package model;

public enum ArtworkType {
	
	PAINTING,
	SCULPTURE;
	
	@Override
	public String toString() {
		if (this.equals(SCULPTURE)) {
			return "Sculpture";
		} else if (this.equals(PAINTING)) {
			return "Painting";
		} else throw new IllegalStateException("Artwork Type not defined");
	}
}
