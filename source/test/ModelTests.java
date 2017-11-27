package test;

import model.Artwork;
import model.ArtworkType;
import model.Painting;
import model.Sculpture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This Test class will test the base code also called the Model
 * this means no UI related things will be tested
 * Uses JUnit5
 *
 * @author Bassam Helal
 */
class ModelTests {
	
	/*
	 * Bassam Helal 26-Nov-17
	 *
	 * Don't touch this class without telling me but I'm assuming
	 * none of you like this shit anyway.
	 *
	 *
	 * This is like a template of the tests for the model we would need
	 * in order to satisfy the requirements, they aren't fully done but will give us
	 * an idea on what needs to work how.
	 *
	 */
	
	//region Profile Tests
	
	/**
	 * Will test the Profile constructor and getters at the same time
	 */
	/*
	@Test
	void profileConstructorAndGetters(){
		model.Profile profile = new model.Profile(
				 args );
		// many asserts here to do the getters all together
	    assertEquals( hardcoded expected, profile.get... ));
	}
	*/
	
	//endregion
	
	//region Database Tests
	
	
	//endregion
	
	//region Artwork Tests
	
	//region Painting Tests
	
	/**
	 * Tests the type of a Painting which is an Artwork
	 */
	@Test
	void paintingGetType() {
		Artwork painting = new Painting();
		assertEquals(ArtworkType.PAINTING, painting.getType());
	}
	
	//endregion
	
	//region Sculpture Tests
	
	/**
	 * Tests the type of a Sculpture which is an Artwork
	 */
	@Test
	void sculptureGetType() {
		Artwork sculpture = new Sculpture();
		assertEquals(ArtworkType.SCULPTURE, sculpture.getType());
	}
	
	//endregion
	
	//endregion
	
	//region Auction Tests
	
	//endregion
	
	//region Bidding Tests
	
	
	//endregion
	
	//region Feed Tests
	
	
	//endregion
	
	//region ProfileImage Tests
	
	
	//endregion
	
	//region Avatar Tests
	
	
	//endregion
	
	//region CustomDrawing Tests
	
	
	//endregion
	
	//region Util Tests
	
	
	//endregion
	
}
