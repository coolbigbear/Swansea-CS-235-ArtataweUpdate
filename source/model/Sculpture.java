package model;

public class Sculpture extends Artwork{
	
	@Override
	public ArtworkType getType() {
		return ArtworkType.SCULPTURE;
	}
}
