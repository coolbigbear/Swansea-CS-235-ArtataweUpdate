package model;

/**
 * An ArtworkType represents the type of Artwork,
 * currently this is either a Painting or a Sculpture.
 *
 * @author Bassam Helal
 * @version 1.0
 * @see Artwork
 */
public enum ArtworkType {
	
	PAINTING,
	SCULPTURE;
	
	/**
	 * Returns the String representation of an ArtworkType.
	 * Returns "Sculpture" if the type is a Sculpture.
	 * Returns "Painting" if the type is a Painting.
	 *
	 * @return the String representation of an ArtworkType
	 */
	@Override
	public String toString() {
		if (this.equals(SCULPTURE)) {
			return "Sculpture";
		} else if (this.equals(PAINTING)) {
			return "Painting";
		} else throw new IllegalStateException("Artwork Type not defined");
	}
}
