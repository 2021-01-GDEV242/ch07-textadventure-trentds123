/**
 * Class Item - an item that will be found in room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Item" represents one item in a location of the game. Their are separate
 *  Items spread through all the rooms. Item it will 
 * store a reference to the room its stored in, the weight of the object and the description.
 * 
 * @author  Trent Seidel
 * @version 2021.03.22
 */

public class Item {
	// fields
	private String weight;
	private String discription;
	//contructor
	public Item(String description, String weight) {
		
		this.discription = description;
		this.weight = weight;
		
	}
	
	/**
	 * @return item description from constructor 
	 */
	public String getItemInfo() {
		String val;
		val = discription + " " + weight;
		return val;
	}
	
	

}
