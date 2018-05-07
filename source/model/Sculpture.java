package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A Sculpture is a type of Artwork in the Artatawe system.
 * <p>
 * A Sculpture has all the attributes and behaviour of an Artwork and adds a width, height, depth and a main
 * material, as well as a number of additional images
 *
 * @author Bassam Helal
 * @author ***REMOVED*** ***REMOVED***
 * @version 1.1
 * @see Artwork
 */
public class Sculpture extends Artwork {

    private Integer width;
    private Integer height;
    private Integer depth;
    private String mainMaterial;
    private List<String> additionalImagesPaths;

    /**
     * Constructs a new Sculpture without any additional images.
     *
     * @param title         the String representing the title of the Sculpture
     * @param description   the StringBuilder representing the description of the Sculpture
     * @param creationDate  the LocalDateTime representing the creation date of the Sculpture
     * @param creatorName   the String representing the creator name of the Sculpture
     * @param mainImagePath the String representing the path of the main image of the Sculpture
     * @param width         the Integer representing the width of the Sculpture
     * @param height        the Integer representing the height of the Sculpture
     * @param depth         the Integer representing the depth of the Sculpture
     * @param mainMaterial  the String representing the main material of the Sculpture
     */
    public Sculpture(String title, StringBuilder description, String creationDate, String creatorName,
                     String mainImagePath, Integer width, Integer height, Integer depth, String mainMaterial) {
        super(title, description, creationDate, creatorName, mainImagePath);
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.mainMaterial = mainMaterial;
        this.type = ArtworkType.Sculpture;
        this.additionalImagesPaths = new ArrayList<>();
    }

    /**
     * Constructs a new Sculpture with the additional images, this should be used only by the Database.
     *
     * @param title                 the String representing the title of the Sculpture
     * @param description           the StringBuilder representing the description of the Sculpture
     * @param creationDate          the LocalDateTime representing the creation date of the Sculpture
     * @param creatorName           the String representing the creator name of the Sculpture
     * @param mainImagePath         the String representing the path of the main image of the Sculpture
     * @param width                 the Integer representing the width of the Sculpture
     * @param height                the Integer representing the height of the Sculpture
     * @param depth                 the Integer representing the depth of the Sculpture
     * @param mainMaterial          the String representing the main material of the Sculpture
     * @param additionalImagesPaths the List of Strings representing the additional image's paths
     */
    public Sculpture(String title, StringBuilder description, String creationDate, String creatorName,
                     String mainImagePath, Integer width, Integer height, Integer depth, String mainMaterial,
                     ArrayList<String> additionalImagesPaths) {
        super(title, description, creationDate, creatorName, mainImagePath);
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.mainMaterial = mainMaterial;
        this.type = ArtworkType.Sculpture;
        this.additionalImagesPaths = additionalImagesPaths;
    }

    /**
     * Adds additional images to the Sculpture.
     *
     * @param images the varargs or array representing the various Strings of the paths of the additional images to
     *               be added
     */
    public void addAdditionalImages(String... images) {
        this.additionalImagesPaths.addAll(Arrays.asList(images));
    }

    /**
     * Sets the width of the Sculpture.
     *
     * @param width the integer representing the width of the Sculpture
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * Sets the height of the Sculpture.
     *
     * @param height the integer representing the width of the Sculpture
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * Sets the depth of the Sculpture.
     *
     * @param depth the integer representing the depth of the Sculpture
     */
    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    /**
     * Sets the main material of the Sculpture
     *
     * @param mainMaterial the String representing the main material of the Sculpture
     */
    public void setMainMaterial(String mainMaterial) {
        this.mainMaterial = mainMaterial;
    }

    /**
     * Sets the additional images of the Sculpture
     *
     * @param additionalImagesPaths the List of Strings representing the paths of the additional images of the Sculpture
     */
    public void setAdditionalImagesPaths(List<String> additionalImagesPaths) {
        this.additionalImagesPaths = additionalImagesPaths;
    }

    /**
     * Gets the width of the Sculpture
     *
     * @return the integer representing the width of the Sculpture
     */
    public Integer getWidth() {
        return this.width;
    }

    /**
     * Gets the height of the Sculpture
     *
     * @return the integer representing the height of the Sculpture
     */
    public Integer getHeight() {
        return this.height;
    }

    /**
     * Gets the depth of the Sculpture
     *
     * @return the integer representing the depth of the Sculpture
     */
    public Integer getDepth() {
        return this.depth;
    }

    /**
     * Gets the main material of the Sculpture
     *
     * @return the String representing the main material of the Sculpture
     */
    public String getMainMaterial() {
        return this.mainMaterial;
    }

    /**
     * Gets the paths of the additional images of the Sculpture
     *
     * @return the List of Strings representing the the paths of the additional images of the Sculpture
     */
    public List<String> getAdditionalImagesPaths() {
        return this.additionalImagesPaths;
    }

    /**
     * Gets the ArtworkType of the Sculpture, will always return <code>ArtworkType.Sculpture</code>
     *
     * @return ArtworkType.Sculpture
     * @see ArtworkType#Sculpture
     */
    @Override
    public ArtworkType getType() {
        return ArtworkType.Sculpture;
    }

    /**
     * Gets the hashcode of the Sculpture
     *
     * @return the integer representing the hashcode of the Sculpture
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return this.title.hashCode() + this.creationDate.hashCode() + this.description.toString().hashCode();
    }

    /**
     * Checks to see if the passed in Object is equals to this instance
     *
     * @param obj the Object to compare with
     * @return true if equal, false otherwise
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Sculpture) && (obj.hashCode() == this.hashCode());
    }

    /**
     * Gets the String representation of the Sculpture
     *
     * @return the String representation of the Sculpture
     * @see Object#toString()
     */
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
