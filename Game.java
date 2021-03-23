/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Trent Seidel
 * @version 2021.03.22
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office;
        Room dormA, dormB,dormC,dormD,dormE;
        Room gym, gameRoom, pool, arcade, store;
        Item bat,ball,wire,headset,frog,fish,camal,tire, shovel,shirt,glove,hat, socks, jeans, tshirt;
        //create the items
        bat = new Item("baseball bat", "10 lb");
        ball = new Item("Ball", ".15 lb");
        wire = new Item("wire", "2 lb");
        headset = new Item("headset", "10 oz");
        frog = new Item("toy frog", "2 lb");
        fish = new Item("rubber fish", "12 lb");
        camal = new Item("camal statue", "20 lb");
        tire = new Item("tire", "2 lb");
        shovel = new Item("shovel", "3 lb");
        shirt = new Item("long sleeve shirt", "1 oz");
        glove = new Item("electrical gloves", "1 lb");
        hat = new Item("RVCC hat", "3 oz");
        socks = new Item("RVCC socks", "2 oz"); 
        jeans = new Item("RVCC jeans", "1 lb"); 
        tshirt = new Item("RVCC tshirt", "3 oz");
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        dormA = new Room("in dorm A");
        dormB = new Room("in dorm B");
        dormC = new Room("in dorm C");
        dormD = new Room("in dorm D");
        dormE = new Room("in dorm E");
        gym = new Room("time to workout, your in the gym");
        gameRoom = new Room("your in the videogame hub meet some gamers!");
        pool = new Room("make sure you have a swimsuit, your at the campus pool!");
        arcade = new Room("your in the arcade");
        store = new Room("in the store");
        
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north", gym);
        theater.setExit("west", outside);
        pub.setExit("east", outside);
        pub.setExit("north", dormA);
        lab.setExit("north", outside);
        lab.setExit("east", office);
        office.setExit("west", lab);
        gym.setExit("south", outside);
        gym.setExit("west", dormA);
        gym.setExit("east", dormE);
        dormA.setExit("north", dormB);
        dormA.setExit("east", gym);
        dormB.setExit("north", gameRoom);
        dormB.setExit("east", dormC);
        gameRoom.setExit("south", dormB);
        dormC.setExit("east", dormB);
        dormC.setExit("west", dormD);
        dormD.setExit("east", dormC);
        dormD.setExit("north", pool);
        pool.setExit("south", dormD);
        dormE.setExit("west", store);
        dormC.setExit("east", gym);
        store.setExit("east", dormE);
        office.setExit("west", arcade);
        arcade.setExit("east", office);
      
        
        
        

        currentRoom = outside;  // start game outside
    }
    /**
     *  allows the long description of the room be read when user types look.
     */ 
    public void look() {
    	System.out.println(currentRoom.getLongDescription());
    }
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Print out the opening message for the player.
     */
    public void printEat() {
    	System.out.println("You are full now");
    }
 

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;
            
            case EAT:
            	printEat();
            	break;
            
            case LOOK:
            	look();
            	break;
            	
            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    public static void main(String args[]) {
    	Game game = new Game();
    	game.play();
    }
}

