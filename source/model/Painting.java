package model;

public class Painting extends Artwork{
	
	@Override
	public ArtworkType getType() {
		return ArtworkType.PAINTING;
	}
}
