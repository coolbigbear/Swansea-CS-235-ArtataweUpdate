package model;

/**
 * An Artwork is currently the only product being sold in the Artatawe system.
 * <p>
 * An Artwork cannot directly be sold, an Artwork is a generalisation, only types of
 * Artworks can be sold such as Paintings and Sculptures.
 *
 * @author Bassam Helal
 * @author ***REMOVED*** ***REMOVED***
 * @version 1.2
 * @see Painting
 * @see Sculpture
 * @see ArtworkType
 */
public abstract class Artwork {

    protected String title;
    protected StringBuilder description;
    protected String creationDate;
    protected String creatorName;
    protected String mainImagePath;
    protected ArtworkType type;

    /**
     * The primary and only constructor of Artwork, used by all its subclasses.
     *
     * @param title         the String representing the title of the Artwork
     * @param description   the StringBuilder representing the description of the Artwork
     * @param creationDate  the String representing the creation date, this is used to allow representation of
     *                      eras or periods in time, for example "15th Century"
     * @param creatorName   the String representing the creator name, note this is not the seller of the Auction,
     *                      rather the name of the Artwork creator
     * @param mainImagePath the String representing the path to the Artwork's main image
     */
    public Artwork(String title, StringBuilder description, String creationDate, String creatorName, String mainImagePath) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.creatorName = creatorName;
        this.mainImagePath = mainImagePath;
    }

    /**
     * Used to get the type of Artwork represented by an ArtworkType.
     *
     * @return the ArtworkType representing the type of Artwork
     */
    public abstract ArtworkType getType();

    /**
     * Sets the title of the Artwork.
     *
     * @param title the String the title will be set to
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the description of the Artwork.
     *
     * @param description the StringBuilder the description will be set to, a StringBuilder is used in order to allow
     *                    appending and modifying more easily.
     */
    public void setDescription(StringBuilder description) {
        this.description = description;
    }

    /**
     * Sets the creation date of the Artwork.
     *
     * @param creationDate the String representing the creation date of the Artwork, a String is used in order to
     *                     allow periods or eras of time, such as "15th Century" or "Prehistoric"
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Sets the creator name of the Artwork, note this is not the seller of the Auction.
     *
     * @param creatorName the String representing the name of the creator of the Artwork
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /**
     * Sets the image path of the Artwork.
     *
     * @param mainImagePath the String representing the image path of the Artwork
     */
    public void setMainImagePath(String mainImagePath) {
        this.mainImagePath = mainImagePath;
    }

    /**
     * Gets the title of the Artwork
     *
     * @return the String representing the title of the Artwork
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the description of the Artwork
     *
     * @return the StringBuilder representing the description of the Artwork
     */
    public StringBuilder getDescription() {
        return this.description;
    }

    /**
     * Gets the creation date of the Artwork
     *
     * @return the String representing the creation date of the Artwork
     */
    public String getCreationDate() {
        return this.creationDate;
    }

    /**
     * Gets the name of the Artwork's creator, note that this is not the name of the seller of the Auction
     *
     * @return the String representing the name of the Artwork creator
     */
    public String getCreatorName() {
        return this.creatorName;
    }

    /**
     * Gets the path of the image of the Artwork
     *
     * @return the String representing the path of the Artwork's image
     */
    public String getMainImagePath() {
        return this.mainImagePath;
    }

}


