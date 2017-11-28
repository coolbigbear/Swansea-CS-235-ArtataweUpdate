package test;

import model.Artwork;
import model.ArtworkType;
import model.Painting;
import model.Sculpture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This Test class will test the base code also called the Model
 * this means no UI related things will be tested
 * Uses JUnit5
 *
 * @author Bassam Helal
 */
@DisplayName("Model")
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
	
	//region Test Setup
	
	
		/*
		
			@BeforeAll
	void setUpBeforeAll() {
	
	}
	
	@BeforeEach
	void setUp() {
		StringBuilder monaLisaDescription = new StringBuilder();
		monaLisaDescription.append("The Mona Lisa (/ˌmoʊnə ˈliːsə/; Italian: Monna Lisa " +
				"[ˈmɔnna ˈliːza] or La Gioconda [la dʒoˈkonda], French: La Joconde [la ʒɔkɔ̃d])");
		monaLisaDescription.append(" is a half-length portrait painting by the Italian Renaissance artist " +
				"Leonardo da Vinci that has been described as \"the best known, the most visited," +
				" the most written about, the most sung about, the most parodied work of art in the world\".");
		Date monaLisaDate = new Date();
		Image monaLisaImage = new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg/800px-Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg");
		Artwork monaLisa = new Painting("Mona Lisa", monaLisaDescription, monaLisaDate,
				"Leonardo da Vinci", monaLisaImage, 25, 50);
		
		StringBuilder davidDescription = new StringBuilder();
		davidDescription.append("David (Italian pronunciation: [ˈdaːvid]) is a masterpiece of Renaissance " +
				"sculpture created between 1501 and 1504 by Michelangelo.");
		Date davidDate = new Date();
		Image davidImage = new Image("https://upload.wikimedia.org/wikipedia/commons/8/84/Michelangelo%27s_David_2015.jpg");
		Artwork david = new Sculpture("David", davidDescription, davidDate,
				"Michelangelo", davidImage, 500, 500, 1000, "Marble");
		
		
		//Auction auction = new Auction();
	}
	
	@AfterEach
	void tearDown() {
	
	}
	*/
	
	//endregion
	
	//region Profile Tests
	
	/**
	 * Will test the Profile constructor and getters at the same time
	 */
	/*
	@Test
	
	void testProfileConstructorAndGetters(){
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
	@DisplayName("Painting Type")
	@Test
	void testPaintingGetType() {
		Artwork localPainting = new Painting("", new StringBuilder(""), LocalDate.now(), "", null, 5, 5);
		assertEquals(ArtworkType.PAINTING, localPainting.getType());
	}
	
	//endregion
	
	//region Sculpture Tests
	
	/**
	 * Tests the type of a Sculpture which is an Artwork
	 */
	@DisplayName("Sculpture Type")
	@Test
	void testSculptureGetType() {
		Artwork localSculpture = new Sculpture("", new StringBuilder(""), LocalDate.now(), "",
				null, 5, 5, 5, "");
		assertEquals(ArtworkType.SCULPTURE, localSculpture.getType());
	}
	
	//endregion
	
	//endregion
	
	//region Auction Tests
	
	//region Bid Validation
	
	//endregion
	
	//endregion
	
	//region Bid Tests
	
	// We may need some Bid time tests as well
	
	/**
	 * Will test the Bid's getters
	 */
	@DisplayName("Bid Getters")
	@Test
	void testAllBidGetters() {
	
	}
	
	/**
	 * Will test two unequal Bids to see if they are equal
	 */
	@DisplayName("Bid Equals false")
	@Test
	void testBidEqualsFalseMethod() {
	
	}
	
	/**
	 * Will test two equal Bids to see if they are equal
	 */
	@DisplayName("Bid Equals true")
	@Test
	void testBidEqualsTrueMethod() {
	
	}
	
	/**
	 * Will test the Bid's toString method
	 */
	@DisplayName("Bid toString")
	@Test
	void testBidToString() {
	
	}
	
	//endregion
	
	//region Feed Tests
	
	
	//endregion
	
	//region Util Tests
	
	
	//endregion
	
}
