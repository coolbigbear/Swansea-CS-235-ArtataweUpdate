package test;

import model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;


/**
 * This Test class will test the base code also called the Model
 * this means no UI related things will be tested here
 * Uses JUnit5
 *
 * @author Bassam Helal
 */
@DisplayName("Model Tests")
class ModelTests {
	
	@BeforeAll
	static void beforeAll() {
		Profile currentUser = Profile.createNewProfile("BassHelal", "Bassam", "Helal",
				"00974666999123", "University", "Lane", "Swansea", "United Kingdom",
				"SA12PP", "path");
		Util.setCurrentUser(currentUser);
	}
	
	@Nested
	class ArtworkTests {
		
		@DisplayName("ArtworkType toString")
		@Test
		void testArtworkTypeToString() {
			ArtworkType sculpture = ArtworkType.Sculpture;
			ArtworkType painting = ArtworkType.Painting;
			assertEquals("Sculpture", sculpture.toString());
			assertEquals("Painting", painting.toString());
		}
		
		@DisplayName("Artwork Title")
		@Test
		void testArtworkTitle() {
			Artwork artwork = new Painting("Mona Lisa", new StringBuilder("Beautiful"), LocalDate.of(1507, 1, 1),
					"Leonardo DaVinci", "path", 5, 5);
			
			artwork.setTitle("The other Lisa");
			
			assertEquals("The other Lisa", artwork.getTitle());
		}
		
		@DisplayName("Artwork Description")
		@Test
		void testArtworkDescription() {
			Artwork artwork = new Painting("Mona Lisa", new StringBuilder("Beautiful"), LocalDate.of(1507, 1, 1),
					"Leonardo DaVinci", "path", 5, 5);
			
			StringBuilder description = new StringBuilder("The Mona Lisa (/ˌmoʊnə ˈliːsə/; Italian: Monna Lisa [ˈmɔnna ˈliːza] " +
					"or La Gioconda [la dʒoˈkonda], French: La Joconde [la ʒɔkɔ̃d]) is a half-length portrait painting by the" +
					" Italian Renaissance artist Leonardo da Vinci that has been described as \"the best known," +
					" the most visited, the most written about, the most sung about, the most parodied work" +
					" of art in the world\".");
			
			artwork.setDescription(description);
			assertEquals("The Mona Lisa (/ˌmoʊnə ˈliːsə/; Italian: Monna Lisa [ˈmɔnna ˈliːza] " +
					"or La Gioconda [la dʒoˈkonda], French: La Joconde [la ʒɔkɔ̃d]) is a half-length portrait painting by the" +
					" Italian Renaissance artist Leonardo da Vinci that has been described as \"the best known," +
					" the most visited, the most written about, the most sung about, the most parodied work" +
					" of art in the world\".", artwork.getDescription().toString());
		}
		
		@DisplayName("Artwork CreationDate")
		@Test
		void testArtworkCreationDate() {
			Artwork artwork = new Painting("Mona Lisa", new StringBuilder("Beautiful"), LocalDate.of(1507, 1, 1),
					"Leonardo DaVinci", "path", 5, 5);
			
			artwork.setCreationDate(LocalDate.of(1970, Month.JANUARY, 1));
			assertEquals(LocalDate.of(1970, 1, 1), artwork.getCreationDate());
		}
		
		@DisplayName("Artwork CreatorName")
		@Test
		void testArtworkCreatorName() {
			Artwork artwork = new Painting("Mona Lisa", new StringBuilder("Beautiful"), LocalDate.of(1507, 1, 1),
					"Leonardo DaVinci", "path", 5, 5);
			
			artwork.setCreatorName("Chuck Norris");
			assertEquals("Chuck Norris", artwork.getCreatorName());
			
		}
		
		@DisplayName("Artwork MainImagePath")
		@Test
		void testArtworkMainImagePath() {
			Artwork artwork = new Painting("Mona Lisa", new StringBuilder("Beautiful"), LocalDate.of(1507, 1, 1),
					"Leonardo DaVinci", "path", 5, 5);
			
			artwork.setMainImagePath("Finland");
			assertEquals("Finland", artwork.getMainImagePath());
			
		}
		
		@DisplayName("Painting Width & Height")
		@Test
		void testPaintingWidthAndHeight() {
			Painting painting = new Painting("Mona Lisa", new StringBuilder("Beautiful"), LocalDate.of(1507, 1, 1),
					"Leonardo DaVinci", "path", 5, 5);
			
			painting.setHeight(50);
			painting.setWidth(100);
			assertEquals(Integer.valueOf(50), painting.getHeight());
			assertEquals(Integer.valueOf(100), painting.getWidth());
		}
		
		@DisplayName("Painting Equals True")
		@Test
		void testPaintingEqualsTrue() {
			Painting painting = new Painting("Mona Lisa", new StringBuilder("Beautiful"), LocalDate.of(1507, 1, 1),
					"Leonardo DaVinci", "path", 5, 5);
			
			Painting anotherPainting = new Painting("Mona Lisa", new StringBuilder("Beautiful"), LocalDate.of(1507,
					1, 1),
					"Leonardo DaVinci", "path", 5, 5);
			
			assertTrue(painting.hashCode() == anotherPainting.hashCode());
			assertTrue(painting.equals(anotherPainting));
		}
		
		@DisplayName("Painting Equals False")
		@Test
		void testPaintingEqualsFalse() {
			Painting painting = new Painting("Mona Lisa", new StringBuilder("Beautiful"), LocalDate.of(1507, 1, 1),
					"Leonardo DaVinci", "path", 5, 5);
			
			Painting anotherPainting = new Painting("Other Lisa", new StringBuilder("Ugly"), LocalDate.of(1507,
					1, 1),
					"Leonardo DaVinci", "path", 5, 5);
			
			assertFalse(painting.hashCode() == anotherPainting.hashCode());
			assertFalse(painting.equals(anotherPainting));
		}
		
		@DisplayName("Painting toString")
		@Test
		void testPaintingToString() {
			Painting painting = new Painting("Mona Lisa", new StringBuilder("Beautiful"), LocalDate.of(1507, 1, 1),
					"Leonardo DaVinci", "path", 5, 5);
			
			assertEquals("Painting: \n\tTitle: Mona Lisa\n\tCreator Name: Leonardo DaVinci\n\tCreation Date: " +
					painting.getCreationDate().toString() + "\n\tWidth: 5\n\tHeight: 5\n", painting.toString());
		}
		
		@DisplayName("Painting Type")
		@Test
		void testPaintingGetType() {
			Artwork localPainting = new Painting("", new StringBuilder(""), LocalDate.now(), "", "path", 5, 5);
			assertEquals(ArtworkType.Painting, localPainting.getType());
		}
		
		@DisplayName("Sculpture Add Additional Images")
		@Test
		void testSculptureAddAdditionalImages() {
			Sculpture sculpture = new Sculpture("David", new StringBuilder("Majestic"), LocalDate.of(1500, 5, 5),
					"Michelangelo", "path", 5, 5, 10, "Marble");
			
			sculpture.addAdditionalImages("path1", "path2", "path3");
			
			List<String> list = new ArrayList<>(Arrays.asList("path1", "path2", "path3"));
			
			assertEquals(list, sculpture.getAdditionalImagesPaths());
		}
		
		@DisplayName("Sculpture Set Additional Images")
		@Test
		void testSculptureSetAdditionalImages() {
			Sculpture sculpture = new Sculpture("David", new StringBuilder("Majestic"), LocalDate.of(1500, 5, 5),
					"Michelangelo", "path", 5, 5, 10, "Marble");
			
			List<String> list = new ArrayList<>(Arrays.asList("path1", "path2", "path3"));
			
			sculpture.setAdditionalImagesPaths(list);
			
			assertEquals(list, sculpture.getAdditionalImagesPaths());
		}
		
		@DisplayName("Sculpture Width, Height & Depth")
		@Test
		void testSculptureWidthHeightAndDepth() {
			Sculpture sculpture = new Sculpture("David", new StringBuilder("Majestic"), LocalDate.of(1500, 5, 5),
					"Michelangelo", "path", 5, 5, 10, "Marble");
			
			sculpture.setHeight(50);
			sculpture.setWidth(100);
			sculpture.setDepth(200);
			assertEquals(Integer.valueOf(50), sculpture.getHeight());
			assertEquals(Integer.valueOf(100), sculpture.getWidth());
			assertEquals(Integer.valueOf(200), sculpture.getDepth());
		}
		
		@DisplayName("Sculpture Main Material")
		@Test
		void testSculptureMainMaterial() {
			Sculpture sculpture = new Sculpture("David", new StringBuilder("Majestic"), LocalDate.of(1500, 5, 5),
					"Michelangelo", "path", 5, 5, 10, "Marble");
			
			sculpture.setMainMaterial("Granite");
			assertEquals("Granite", sculpture.getMainMaterial());
		}
		
		@DisplayName("Sculpture Type")
		@Test
		void testSculptureGetType() {
			Artwork localSculpture = new Sculpture("", new StringBuilder(""), LocalDate.now(), "",
					null, 5, 5, 5, "");
			assertEquals(ArtworkType.Sculpture, localSculpture.getType());
		}
		
		@DisplayName("Sculpture Equals True")
		@Test
		void testSculptureEqualsTrue() {
			Sculpture sculpture = new Sculpture("David", new StringBuilder("Majestic"), LocalDate.of(1500, 5, 5),
					"Michelangelo", "path", 5, 5, 10, "Marble");
			
			Sculpture anotherSculpture = new Sculpture("David", new StringBuilder("Majestic"), LocalDate.of(1500, 5,
					5),
					"Michelangelo", "path", 5, 5, 10, "Marble");
			
			assertTrue(sculpture.hashCode() == anotherSculpture.hashCode());
			assertTrue(sculpture.equals(anotherSculpture));
		}
		
		@DisplayName("Sculpture Equals False")
		@Test
		void testSculptureEqualsFalse() {
			Sculpture sculpture = new Sculpture("David", new StringBuilder("Majestic"), LocalDate.of(1500, 5, 5),
					"Michelangelo", "path", 5, 5, 10, "Marble");
			
			Sculpture anotherSculpture = new Sculpture("Other David", new StringBuilder("Not so Majestic"),
					LocalDate.of(1500, 5, 5), "Michelangelo",
					"path", 5, 5, 10, "Marble");
			
			assertFalse(sculpture.hashCode() == anotherSculpture.hashCode());
			assertFalse(sculpture.equals(anotherSculpture));
		}
		
		@DisplayName("Sculpture toString")
		@Test
		void testSculptureToString() {
			Sculpture sculpture = new Sculpture("David", new StringBuilder("Majestic"), LocalDate.of(1500, 5, 5),
					"Michelangelo", "path", 5, 5, 10, "Marble");
			
			assertEquals("Sculpture: \n\tTitle: David\n\tCreator Name: Michelangelo\n\tCreation Date: " +
							sculpture.getCreationDate().toString() + "\n\tWidth: 5\n\tHeight: 5\n\tDepth: 10\n\tMain " +
							"Material: Marble\n",
					sculpture.toString());
		}
		
	}
	
	@Nested
	class BidTests {
		
		@DisplayName("Bid Main")
		@Test
		void testBid() throws InterruptedException {
			Bid bid = new Bid(1, 50.00);
			assertEquals(Integer.valueOf(1), bid.getAuctionID());
			assertEquals(Double.valueOf(50.00), bid.getBidAmount());
			assertEquals(Util.getCurrentUser().getUsername(), bid.getBidderUsername());
			assertTrue(bid.getDateTimePlaced().isEqual(LocalDateTime.now()));
			Thread.sleep(1);
			assertTrue(bid.getDateTimePlaced().isBefore(LocalDateTime.now()));
		}
		
		@DisplayName("Bid Equals True")
		@Test
		void testBidEqualsTrue() {
			Bid bid = new Bid(1, 50.00);
			Bid anotherBid = new Bid(1, 50.00);
			assertTrue(bid.equals(anotherBid));
		}
		
		@DisplayName("Bid Equals False")
		@Test
		void testBidEqualsFalse() {
			Bid bid = new Bid(1, 50.00);
			Bid anotherBid = new Bid(3, 55.00);
			assertFalse(bid.equals(anotherBid));
		}
		
		@DisplayName("Bid toString")
		@Test
		void testBidToString() {
			Bid bid = new Bid(1, 50.00);
			assertEquals("Bid: " + bid.hashCode() + "\n\tAuction: 1\n\tAmount: 50.0\n\t" +
							"Bidder: BassHelal\n\tTime: " + bid.getDateTimePlaced().toString() + "\n",
					bid.toString());
		}
		
	}
	
	@Nested
	class AuctionTests {
	
	}
	
	@Nested
	class ProfileTests {
	
	}
	
	@Nested
	class FeedTests {
	
	}
	
	@Nested
	class UtilTests {
		
		@Nested
		class DataBaseTests {
		
		}
		
	}
	
	@Nested
	class FeedStringTests {
		@DisplayName("BHFeedString isEmpty")
		@Test
		void testBHFeedStringIsEmpty() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			assertTrue(stringFeed.isEmpty());
		}
		
		@DisplayName("BHFeedString size empty")
		@Test
		void testBHFeedStringSizeEmpty() {
			BHFeedString stringFeed = BHFeedString.getNewInstanceWithCapacity(5);
			
			assertTrue(stringFeed.isEmpty());
			assertEquals(0, stringFeed.size());
		}
		
		@DisplayName("BHFeedString size nonempty")
		@Test
		void testBHFeedStringSizeNonEmpty() {
			BHFeedString stringFeed = BHFeedString.getNewInstanceWithCapacity(5);
			stringFeed.add("Hello");
			assertFalse(stringFeed.isEmpty());
			assertEquals(1, stringFeed.size());
		}
		
		@DisplayName("BHFeedString check instances equal")
		@Test
		void testBHFeedStringInstancesEqual() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			BHFeedString stringFeed2 = BHFeedString.getInstance();
			assertEquals(stringFeed, stringFeed2);
			assertEquals(BHFeedString.getInstance(), BHFeedString.getInstance());
		}
		
		@DisplayName("BHFeedString check instances not equal")
		@Test
		void testBHFeedStringInstancesNotEqual() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			BHFeedString stringFeed2 = BHFeedString.getNewInstance();
			assertNotEquals(stringFeed, stringFeed2);
		}
		
		@DisplayName("BHFeedString check instances not equal with new capacity")
		@Test
		void testBHFeedStringInstancesNotEqualNewCapacity() {
			BHFeedString stringFeed = BHFeedString.getNewInstanceWithCapacity(5);
			BHFeedString stringFeed2 = BHFeedString.getNewInstanceWithCapacity(50);
			assertNotEquals(stringFeed, stringFeed2);
			assertEquals(stringFeed.size(), stringFeed2.size());
		}
		
		@DisplayName("BHFeedString check instances not equal with new capacity smaller")
		@Test
		void testBHFeedStringInstancesNotEqualNewCapacitySmaller() {
			BHFeedString stringFeed = BHFeedString.getNewInstanceWithCapacity(50);
			BHFeedString stringFeed2 = BHFeedString.getNewInstanceWithCapacity(5);
			assertNotEquals(stringFeed, stringFeed2);
			assertEquals(stringFeed.size(), stringFeed2.size());
		}
		
		@DisplayName("BHFeedString add one by one")
		@Test
		void testBHFeedStringAddOne() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			stringFeed.add("Hello");
			stringFeed.add("Hi");
			stringFeed.add("Welcome");
			stringFeed.add("Greetings");
			assertEquals(4, stringFeed.size());
		}
		
		@DisplayName("BHFeedString addAll varargs")
		@Test
		void testBHFeedStringAddAllVarargs() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			stringFeed.addAll("Hello", "Hi", "Welcome", "Greetings");
			assertEquals(4, stringFeed.size());
		}
		
		@DisplayName("BHFeedString addAll collection")
		@Test
		void testBHFeedStringAddAllCollection() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			ArrayList<String> arrayList = new ArrayList<>();
			arrayList.add("Batman");
			arrayList.add("Superman");
			arrayList.add("Wonder woman");
			arrayList.add("Cyborg");
			
			stringFeed.addAll(arrayList);
			assertEquals(4, stringFeed.size());
		}
		
		@DisplayName("BHFeedString Check current instance")
		@Test
		void testBHFeedStringCheckInstances() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			stringFeed.addAll("Hello", "Hi", "Greetings", "Welcome");
			
			stringFeed = BHFeedString.getInstance();
			
			assertEquals(4, stringFeed.size());
		}
		
		@DisplayName("BHFeedString Check current instance 2")
		@Test
		void testBHFeedStringCheckInstances2() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			stringFeed.addAll("Hello", "Hi", "Greetings", "Welcome");
			
			stringFeed = BHFeedString.getInstance();
			BHFeedString anotherStringFeed = BHFeedString.getInstance();
			
			assertEquals(4, anotherStringFeed.size());
			assertEquals(stringFeed, anotherStringFeed);
			assertEquals(BHFeedString.getInstance(), BHFeedString.getInstance());
			
		}
		
		@DisplayName("BHFeedString clear current instance")
		@Test
		void testBHFeedStringClearCurrentInstance() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			ArrayList<String> arrayList = new ArrayList<>();
			arrayList.add("Batman");
			arrayList.add("Superman");
			arrayList.add("Wonder woman");
			arrayList.add("Cyborg");
			
			stringFeed.addAll(arrayList);
			stringFeed.clear();
			
			assertEquals(0, stringFeed.size());
			assertTrue(stringFeed.isEmpty());
		}
		
		@DisplayName("BHFeedString Get all as ArrayList")
		@Test
		void testBHFeedStringGetAllAsArrayList() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			stringFeed.addAll("Hello", "Hi", "Greetings", "Welcome");
			ArrayList<String> arrayList = stringFeed.getAllAsArrayList();
			
			assertEquals("Welcome", arrayList.get(3));
		}
		
		@DisplayName("BHFeedString Get all as ArrayList Empty")
		@Test
		void testBHFeedStringGetAllAsArrayListEmpty() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			ArrayList<String> arrayList = stringFeed.getAllAsArrayList();
			
			assertNotNull(arrayList);
		}
		
		@DisplayName("BHFeedString Get all as Array")
		@Test
		void testBHFeedStringGetAllAsArray() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			stringFeed.addAll("Hello", "Hi", "Greetings", "Welcome");
			String[] array = stringFeed.getAllAsArray();
			
			assertEquals("Welcome", array[3]);
		}
		
		@DisplayName("BHFeedString copy to new instance")
		@Test
		void testBHFeedStringCopyToNewInstance() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			stringFeed.addAll("Hello", "Hi", "Greetings", "Welcome");
			ArrayList<String> stringFeedArrayList = stringFeed.getAllAsArrayList();
			
			BHFeedString newStringFeed = stringFeed.copyToNewInstance();
			ArrayList<String> newStringFeedArrayList = newStringFeed.getAllAsArrayList();
			
			assertEquals(stringFeedArrayList, newStringFeedArrayList);
		}
		
		@DisplayName("BHFeedString copy to new instance with capacity")
		@Test
		void testBHFeedStringCopyToNewInstanceWithCapacity() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			stringFeed.addAll("Hello", "Hi", "Greetings", "Welcome");
			ArrayList<String> stringFeedArrayList = stringFeed.getAllAsArrayList();
			
			//it doesn't matter because it will grow anyway
			BHFeedString newStringFeed = stringFeed.copyToNewInstanceWithCapacity(2);
			ArrayList<String> newStringFeedArrayList = newStringFeed.getAllAsArrayList();
			
			assertEquals(stringFeedArrayList, newStringFeedArrayList);
		}
		
		@DisplayName("BHFeedString update with collection")
		@Test
		void testBHFeedStringUpdateWithCollection() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			stringFeed.addAll("Hello", "Hi", "Greetings", "Welcome");
			
			ArrayList<String> arrayList = new ArrayList<>();
			arrayList.add("Batman");
			arrayList.add("Superman");
			arrayList.add("Wonder woman");
			arrayList.add("Cyborg");
			
			stringFeed = stringFeed.updateWith(arrayList);
			
			assertEquals(arrayList, stringFeed.getAllAsArrayList());
		}
		
		@DisplayName("BHFeedString update with array")
		@Test
		void testBHFeedStringUpdateWithArray() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			stringFeed.addAll("Hello", "Hi", "Greetings", "Welcome");
			
			String[] array = {"Batman", "Superman", "Wonder Woman", "Cyborg"};
			
			stringFeed = stringFeed.updateWith(array);
			
			assertEquals(array[0], stringFeed.getAllAsArray()[0]);
			assertEquals(array[1], stringFeed.getAllAsArray()[1]);
			assertEquals(array[2], stringFeed.getAllAsArray()[2]);
			assertEquals(array[3], stringFeed.getAllAsArray()[3]);
		}
		
		@DisplayName("BHFeedString update with capacity collection")
		@Test
		void testBHFeedStringUpdateWithCapacityCollection() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			stringFeed.addAll("Hello", "Hi", "Greetings", "Welcome");
			
			ArrayList<String> arrayList = new ArrayList<>();
			arrayList.add("Batman");
			arrayList.add("Superman");
			arrayList.add("Wonder woman");
			arrayList.add("Cyborg");
			
			stringFeed = stringFeed.updateWithCapacity(10, arrayList);
			
			assertEquals("Cyborg", stringFeed.getAllAsArrayList().get(3));
		}
		
		@DisplayName("BHFeedString update with capacity array")
		@Test
		void testBHFeedStringUpdateWithCapacityArray() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			stringFeed.addAll("Hello", "Hi", "Greetings", "Welcome");
			
			String[] array = {"Batman", "Superman", "Wonder woman", "Cyborg"};
			
			stringFeed = stringFeed.updateWithCapacity(10, array);
			
			assertEquals("Cyborg", stringFeed.getAllAsArray()[3]);
		}
		
		@DisplayName("BHFeedString iterator with for each")
		@Test
		void testBHFeedStringIteratorForEach() {
			BHFeedString stringFeed = BHFeedString.getNewInstanceWithCapacity(10);
			stringFeed.addAll("Hello", "Hi", "Greetings", "Welcome");
			ArrayList<String> arrayList = new ArrayList<>();
			
			for (String string : stringFeed) {
				arrayList.add(string);
			}
			
			
			assertEquals("Greetings", arrayList.get(2));
		}
		
		@DisplayName("BHFeedString for each")
		@Test
		void testBHFeedStringForEach() {
			BHFeedString stringFeed = BHFeedString.getNewInstanceWithCapacity(10);
			stringFeed.addAll("Hello", "Hi", "Greetings", "Welcome");
			ArrayList<String> arrayList = new ArrayList<>();
			
			stringFeed.forEach((Consumer<String>) s -> arrayList.add(s));
			
			
			assertEquals("Greetings", arrayList.get(2));
		}
		
		@DisplayName("BHFeedString non equal")
		@Test
		void testBHFeedStringNonEqual() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			stringFeed.addAll("Hello", "Hi", "Greetings", "Welcome");
			
			BHFeedString anotherStringFeed = BHFeedString.getNewInstance();
			anotherStringFeed.addAll("Hello", "Hi", "Greetings", "Welcome");
			
			assertNotEquals(stringFeed, anotherStringFeed);
		}
		
		@DisplayName("BHFeedString equal")
		@Test
		void testBHFeedStringEqual() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			stringFeed.addAll("Hello", "Hi", "Greetings", "Welcome");
			
			BHFeedString anotherStringFeed = BHFeedString.getNewInstance();
			anotherStringFeed.addAll("Hello", "Hi", "Greetings", "Welcome");
			
			assertEquals(stringFeed.getAllAsArrayList(), anotherStringFeed.getAllAsArrayList());
		}
		
		@DisplayName("BHFeedString Destroy Instance Not Null")
		@Test
		void testBHFeedStringDestroyInstanceNotNull() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			stringFeed.addAll("Hello", "Hi", "Greetings", "Welcome");
			stringFeed.destroyInstance();
			
			assertNotNull(BHFeedString.getInstance());
		}
		
		@DisplayName("BHFeedString Sort")
		@Test
		void testBHFeedStringSort() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			stringFeed.addAll("Felicia", "Eve", "Daniel", "Bob", "Alice", "Charlie");
			stringFeed.sort();
			
			assertEquals("Alice", stringFeed.getAllAsArray()[0]);
			assertEquals("Bob", stringFeed.getAllAsArray()[1]);
			assertEquals("Charlie", stringFeed.getAllAsArray()[2]);
			assertEquals("Daniel", stringFeed.getAllAsArray()[3]);
			assertEquals("Eve", stringFeed.getAllAsArray()[4]);
			assertEquals("Felicia", stringFeed.getAllAsArray()[5]);
		}
		
		@DisplayName("BHFeedString toString")
		@Test
		void testBHFeedStringToString() {
			BHFeedString stringFeed = BHFeedString.getNewInstance();
			stringFeed.addAll("Felicia", "Eve", "Daniel", "Bob", "Alice", "Charlie");
			stringFeed.sort();
			
			StringBuilder strings = new StringBuilder();
			for (String string : stringFeed) {
				strings.append(string);
				strings.append("\n\t\t");
			}
			
			assertEquals("Feed: \n\tSize: 6\n\tContents: " + strings + "\n", stringFeed.toString());
		}
	}
	
	@Nested
	class OtherTests {
		
		@DisplayName("LocalDateTime now +1 millisecond hashcode")
		@Test
		void testLocalDateTimeNowHashCode() throws InterruptedException {
			LocalDateTime now = LocalDateTime.now();
			int nowHashCode = now.hashCode();
			Thread.sleep(1);
			now = LocalDateTime.now();
			int laterHashcode = now.hashCode();
			assertTrue(nowHashCode != laterHashcode);
		}
		
		@DisplayName("LocalDate equal and hashcode")
		@Test
		void testLocalDatesEqual() {
			LocalDate july271950 = LocalDate.of(1950, Month.JULY, 27);
			LocalDate j7271950 = LocalDate.of(1950, 7, 27);
			assertEquals(july271950, j7271950);
			assertTrue(july271950.hashCode() == j7271950.hashCode());
		}
		
	}
	
	
}
