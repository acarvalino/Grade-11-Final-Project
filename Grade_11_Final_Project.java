

/*
 * Andrew Carvalino
 * 
 * This game will be in the form of a role playing game (RPG) where you allocate your stats into
 * Health (health points), Dexterity, Strength (Str and Dex affect damage of different weapons), and Luck (chance of critical hit).
 * After creating a character (only stats and name), the player will then be able to journey around a map that
 * includes enemies, weapons, and more health.
 * Once all the enemies in the map are defeated, the player will have beaten the game and the game will end;
 * however, if the player dies in-game, the game will be over.
 * 
 * 
 * In order to assign a value to each stat, there will be an integer controlling the maximum points that are available to be allocated, 
 * (and will also include loops) and will also ask the user how many points he/she would like to place in each stat (user input), 
 * with the loop not stoping until there are no more points left to allocate.
 * The stats will be stored in an integer array. The map will be generated as a two dimensional array that will be made through a nested loop.
 * The character will take up one spot on the map at a given time and will have the option to go upward, downward, left, or right, 
 * unless something is blocking them (the edge of the map). There will be a method dedicated to the combat that will use random number generator 
 * to determine how much critical damage the enemy does. If the player dies, it will trigger the game to stop/end (System.exit(0)). 
 * An inventory will be available to select and equip certain items or heal, which will be made using a 2D array.
 * The player will encounter an enemy when the two over lap in the arena, thus triggering the combat method in order to initiate the turn-based combat. 
 * The combat will have the player attack first, then the enemy, and it will continue on this cycle until one of the two reaches a 
 * point where their health is equal to or bellow zero. Throughout the game, after each move (be it moving in the map or making a move in combat) 
 * the user will have a set of options, for example, one option allowing them being to access their inventory and the other will let them move.
 * When the player overlaps with an item (can't tell the difference as they are both represented by twos on the shown arena) the player will
 * collect that item and it will expand their inventory. The player will also be able to equip new weapons picked up, to increase damage.
 */

import java.util.Scanner;
import java.util.Random;
public class Grade_11_Final_Project {
  
  
  public static void main(String[] args) { 
    //Starting screen
    Scanner input = new Scanner(System.in);
    
    String startGame;
    System.out.println("Insert Game Name");
    System.out.println("Would you like to begin");
    System.out.println("Yes or No");
    
    while(true){
      while(true){
        try{
          startGame = input.nextLine();
          break;
        }
        catch(Exception ex){
          ClearLine();
          System.out.println("Please enter either 'Yes' or 'No'");
        }
      }
      startGame = startGame.trim();
      startGame = startGame.toLowerCase();
      
      if (startGame.equals("yes")){
        break;
      }
      else if (startGame.equals("no")){
        System.exit(0);
      }
      else{
        ClearLine();
        System.out.println("Please enter a valid input");
      }
    }
    ClearLine();
    
    //Character Name (String manipulation)
    String firstName = "";
    String lastName = "";
    String firstInitial = "";
    String lastInitial = "";
    while(true){
      System.out.println("Please enter your first name");
      firstName = input.nextLine();
      firstName = firstName.trim();
      firstName = firstName.toLowerCase();
      firstInitial = firstName.substring(0,1);
      firstInitial = firstInitial.toUpperCase();
      if(firstName.isEmpty()){
      }
      else{
        break;
      }
    }
    while(true){
      System.out.println("Please enter your last name");
      lastName = input.nextLine();
      lastName = lastName.trim();
      lastName = lastName.toLowerCase();
      lastInitial = lastName.substring(0,1);
      lastInitial = lastInitial.toUpperCase();
      if(lastName.isEmpty()){
      }
      else{
        break;
      }
    }
    ClearLine();
    
    /*Stat Allocation: Health, Dexterity, Strength, Luck (max is 5 points per stat and min is 1)(all points must be used)
     * Health: each point will add 10 more health
     * Dex: each point will allow the user to weild weapons that requier skill/finesse
     * Str: each point will allow the user to weild heavier weapons
     * Luck: increases the chance of a critical hit (multiplier)
    */
    
    int availableStats = 10;
    int statsLeft = 0; //used to make sure there is at least one point per stat
    String stat = "   ";
    int inputStats;
    int[] characterStats = {0,0,0,0}; //holds the user's stats
    System.out.println("Please allocate all available stat points.");
    System.out.println("Your stat categories are: Health, Dexterity, Strength, and Luck");
    System.out.println("You must have at least one point per stat, so the max for a single stat is 7 (with all others being 1)");
    for(int i = 1; i <= characterStats.length; i++){
      System.out.println("You have: " + availableStats + " points available");
      stat = " ";
      inputStats = 0;
      System.out.println("Your current stats are: ");
      System.out.println("Health:" + characterStats[0] + " Dex: " + characterStats[1] + " Str: " + characterStats[2] + " Luck: " + characterStats[3]);
      if(i == 1){
        stat = "Health";
      }
      else if(i == 2){
        stat = "Dex";
      }
      else if(i == 3){
        stat = "Str";
      }
      else if(i == 4){
        stat = "Luck";
      }
      else{
        System.out.println("Fix character for-loop");
      }
      System.out.println("How many points would you like in " + stat + "?");
      while(true){
        inputStats = InputInt();
        statsLeft = availableStats - inputStats;
        if(inputStats <= 7 && inputStats <= availableStats && statsLeft >= characterStats.length - i && inputStats > 0){
          availableStats = availableStats - inputStats;
          characterStats[i - 1] = inputStats;
          ClearLine();
          break;
        }
        else{
          System.out.println("Please enter a value so you have at least one point per stat");
          System.out.print(" and less than or equal to the current available stats: " + availableStats);
          ClearLine();
        }
      }
      if(characterStats[3] != 0 && availableStats != 0){
        System.out.println("Please use up all your available stat points");
        i = i - 1;
      }
      else{
      }
    }
    ClearLine();
    
    characterStats[0] = 175 + characterStats[0] * 10;
    characterStats[1] = (characterStats[1] * 10) / 2;
    characterStats[2] = (characterStats[2] * 10) / 2;
    characterStats[3] = characterStats[3] * 10;
    
    final int maxHealth = characterStats[0];
    int health = characterStats[0];
    /*
     * if a weapon has an A scaling in Dex or Str, the value of the buff for either of these (no weapon will have A scaling in both) will be characterStats[1 or 2] * 2
     * if it's a B scaling, the value of characterStats[1 or 2] will be added (no multiplier)
     * if it's a C scaling (likely for both Dex and Str) then the buff will be characterStats[1 or 2]/2
    */
    
    System.out.println("You have " + characterStats[0] + " health points; a " + characterStats[1] + " buff on dexterity weapons;");
    System.out.println("a " + characterStats[2] + " buff on strength weapons; and a " + characterStats[3] + "% chance of a critical hit.");
    System.out.println("Dear " + firstName + " " + lastName + ",");
    System.out.println("You have been hired by the local village to clear out all the vermin that reside in the nearby forest who have been causing trouble for the traveling merchants.");
    System.out.println("While you already have your own sword, you will find better weapons scattered throughout the forest that you may use to your leisure.");
    System.out.println("There will be healing items scattered throughout the environment, perhaps even held by the creatures. Good luck and try not to die.");
    ClearLine();
    
    //1 = healing   50 = basic sword  75 = stronger sword (C scale)  75 = claymore (A scale str)  75 = scimitar (A scale dex)
    //A scaling = 4   B = 2   C = 1
    
    String[] inventoryName = {"Healing Item", "Basic Sword"};
    int[][] inventory = new int[2][4];
    
    // 1 = healing  2 = basic sword  3 = stronger sword  4 = claymore  5 = scimitar
    inventory[0][0] = 1; // indicator of item
    inventory[0][1] = 100; // how much it heals
    inventory[0][2] = 1; // # of healing items remaining
    inventory[0][3] = 0; // left over space
    inventory[1][0] = 2; // item ID
    inventory[1][1] = 1; // dex
    inventory[1][2] = 1; // str
    inventory[1][3] = 50; // damage
    
    int[] damage = {50, characterStats[1], characterStats[2]}; //default damage, (C) dex scaling, (C) str scaling of the basic sword
    
    int[][] arena = new int[12][12];
    for(int i = 0; i <= arena.length - 1; i++){
      for(int j = 0; j <= arena[i].length - 1; j++){
        arena[i][j] = 0;
      }
    }
    
    //character (1), enemy (2), and loot (3) positions
    arena[0][0] = 1;
    
    arena[2][2] = 2;
    arena[3][2] = 2;
    arena[11][4] = 2;
    arena[10][11] = 2;
    arena[3][10] = 2;
    
    arena[11][3] = 3;
    arena[4][2] = 3;
    arena[6][7] = 3;
    arena[0][3] = 3;
    arena[8][3] = 3;
    PrintArena(arena);
    System.out.println("To move: input 'w' to move up, 'a' to move left, 's' to move down, and 'd' to move right");
    System.out.println("To access your inventory press 'i'");
    
    String playerMove = "";
    int position = 0;
    int inventoryChoice = 0;
    int stop = 0;
    while(true){
      while(true){
        for(int i = 0; i <= arena.length - 1; i++){
          for(int j = 0; j <= arena[i].length - 1; j++){
            if(arena[i][j] != 2){
              stop = 1;
            }
            else if(arena[i][j] == 2){
              stop = 2;
              break;
            }
          }
          if(stop == 2){
            break;
          }
        }
        if(stop == 1){
          System.out.println("Congratulations! You have won! The game will end, thank you for playing!");
          Pause(4000);
          System.exit(0);
        }
        else if(stop == 2){
          System.out.println("What would you like to do?");
          playerMove = input.nextLine();
          break;
        }
      }
      
      if(playerMove.equals("i")){
        inventoryChoice = PrintInventory(inventory);
        if(inventoryChoice == 0){
            // do nothing
        }
        else if(inventoryChoice == 1){
            if(inventory[0][2] > 0){
                health = health + 100;
                inventory[0][2] = inventory[0][2] - 1;
            }
            ClearLine();
        }
        else if(inventoryChoice == 2){
            damage[0] = inventory[1][3];
            damage[1] = characterStats[1] * inventory[1][1];
            damage[2] = characterStats[2] * inventory[1][2];
            ClearLine();
        }
        else if(inventoryChoice == 3){
            damage[0] = 50;
            damage[1] = characterStats[1] * 2;
            damage[2] = characterStats[2] * 2;
            ClearLine();
        }
        else if(inventoryChoice == 4){
            damage[0] = 50;
            damage[1] = characterStats[1] * 0;
            damage[2] = characterStats[2] * 4;
            ClearLine();
        }
        else if(inventoryChoice == 5){
            damage[0] = 50;
            damage[1] = characterStats[1] * 4;
            damage[2] = characterStats[2] * 0;
            ClearLine();
        }
      }
      else if(playerMove.equals("w")){
        position = MoveCharacter(1, arena);
        PrintArena(arena);
        if(position == 3){
          health = Combat(health, damage, characterStats, inventory);
        }
        else if(position == 4){
          inventory = IncreaseArray(inventory);
          System.out.println("You found something!");
        }
      }
      else if(playerMove.equals("a")){
        position = MoveCharacter(2, arena);
        PrintArena(arena);
        if(position == 3){
          health = Combat(health, damage, characterStats, inventory);
        }
        else if(position == 4){
          inventory = IncreaseArray(inventory);
          System.out.println("You found something!");
        }
      }
      else if(playerMove.equals("s")){
        position = MoveCharacter(3, arena);
        PrintArena(arena);
        if(position == 3){
          health = Combat(health, damage, characterStats, inventory);
        }
        else if(position == 4){
          inventory = IncreaseArray(inventory);
          System.out.println("You found something!");
        }
      }
      else if(playerMove.equals("d")){
        position = MoveCharacter(4, arena);
        PrintArena(arena);
        if(position == 3){
          health = Combat(health, damage, characterStats, inventory);
        }
        else if(position == 4){
          inventory = IncreaseArray(inventory);
          System.out.println("You found something!");
        }
      }
      else{
        System.out.println("Please enter a valid input");
      }
      ClearLine();
    }
  }
  
  public static void ClearLine(){
    final int LINES = 3;
    for(int i = 0; i < LINES; i++){
      System.out.println(" ");
    }
  }
  public static void Pause(int sleepTime){
    try{
      Thread.sleep(sleepTime);
    }
    catch(Exception ex){
        System.out.println("sleepTime error");
    }
  }
  
  public static int InputInt(){
    Scanner input = new Scanner(System.in);
    int userInput = 0;
    while(true){
      try{
        userInput = input.nextInt();
        break;
      }
      catch(Exception ex){
        System.out.println("Please enter a valid integer value");
      }
      finally{
        input.nextLine();
      }
    }
    return userInput;
  }
  
  public static void PrintArena(int[][] arena){
    for(int i = 0; i <= arena.length - 1; i++)
    {
      for(int j = 0; j <= arena[i].length - 1; j++)
      {
        if(j == arena[i].length - 1){
          if(arena[i][j] != 0 && arena[i][j] != 1){
            System.out.println(2);
          }
          else{
            System.out.println(arena[i][j]);
          }
        }
        else{
          if(arena[i][j] != 0 && arena[i][j] != 1){
            System.out.print(2 + " ");
          }
          else{
            System.out.print(arena[i][j] + " ");
          }
        }
      }
    }
  }
  
  public static int PrintInventory(int[][] inventory){
      int[] damage = {0,0,0};
      int choice = 0;
      System.out.println("You may either equip/use an item by entering its corresponding number or input 0 to back out of the inventory.");
      System.out.println("In your inventory you have:");
      for(int i = 0; i <= inventory.length - 1; i++){
          if(inventory[i][0] == 1){
              System.out.println("1. Healing Items (" + inventory[i][2] + " remaining)");
          }
          else if(inventory[i][0] == 2){
              System.out.println("2. Basic Sword, Base damage: " + inventory[i][3] + "  Dex scaling: " + inventory[i][1] + "  Str scaling: " + inventory[i][2]);
          }
          else if(inventory[i][0] == 3){
              System.out.println("3. Stronger Sword, Base damage: " + inventory[i][3] + "  Dex scaling: " + inventory[i][1] + "  Str scaling: " + inventory[i][2]);
          }
          else if(inventory[i][0] == 4){
              System.out.println("4. Claymore, Base damage: " + inventory[i][3] + "  Dex scaling: " + inventory[i][1] + "  Str scaling: " + inventory[i][2]);
          }
          else if(inventory[i][0] == 5){
              System.out.println("5. Scimitar, Base damage: " + inventory[i][3] + "  Dex scaling: " + inventory[i][1] + "  Str scaling: " + inventory[i][2]);
          }
          else{
          }
      }
      int stop = 0;
      while(true){
          choice = InputInt();
          if(choice == 0){
              break;
          }
          else if(choice == 1 && inventory[0][2] == 0){
              System.out.println("You are out of healing items!");
          }
          for(int i = 0; i<= inventory.length - 1; i++){
              if(choice == inventory[i][0]){
                  stop = 1;
              }
          }
          if(stop == 0){
              System.out.println("Please select an item that you have");
          }
          else{
              break;
          }
      }
      return choice;
  }
  
  public static int MoveCharacter(int input, int[][] arena){
    int playerPosition = 0;
    int j = 0;
    while(true){
      if(input == 1){
        for(int a = 0; a <= arena.length - 1; a++){
          for(int b = 0; b <= arena[a].length - 1; b++){
            if(arena[a][b] == 1){
              if(a != 0){
                arena[a-1][b] = arena[a][b] + arena[a-1][b];
                playerPosition = arena[a-1][b];
                arena[a][b] = 0;
                arena[a-1][b] = 1;
              }
              else{
                System.out.println("Please enter a valid input");
              }
            }
          }
        }
      }
      if(input == 2){
        for(int c = 0; c <= arena.length - 1; c++){
          for(int d = 0; d <= arena[c].length - 1; d++){
            if(arena[c][d] == 1){
              if(d != 0){
                arena[c][d-1] = arena[c][d] + arena[c][d-1];
                playerPosition = arena[c][d-1];
                arena[c][d-1] = 1;
                arena[c][d] = 0;
              }
              else{
                System.out.println("Please enter a valid input");
              }
            }
          }
        }
      }
      if(input == 3){
        for(int e = 0; e <= arena.length - 1; e++){
          for(int f = 0; f <= arena[e].length - 1; f++){
            if(arena[e][f] == 1){
              if(e != 11){
                arena[e+1][f] = arena[e+1][f] + arena[e][f];
                playerPosition = arena[e+1][f];
                arena[e+1][f] = 1;
                arena[e][f] = 0;
                e = e + 1;
                j = f;
                break;
              }
              else{
                System.out.println("Please enter a valid input");
                break;
              }
            }
          }
          if(e != 11){
            if(arena[e][j] == 1){
              break;
            }
          }
        }
      }
      if(input == 4){
        for(int g = 0; g <= arena.length - 1; g++){
          for(int h = 0; h <= arena[g].length - 1; h++){
            if(arena[g][h] == 1){
              if(h != 11){
                arena[g][h+1] = arena[g][h+1] + arena[g][h];
                playerPosition = arena[g][h+1];
                arena[g][h+1] = 1;
                arena[g][h] = 0;
                break;
              }
              else{
                System.out.println("Please enter a valid input");
                break;
              }
            }
          }
        }
      }
      break;
    }
    return playerPosition;
  }
  public static int[][] IncreaseArray(int[][] inventory){
      Random myRandom = new Random();
      int newItem;
      int sizeIncrease = 0;
      int stop = 0;
      while(true){
          newItem = myRandom.nextInt(5) + 1;
          for(int i = 0; i <= inventory.length - 1; i++){
              if(inventory[i][0] == newItem && newItem != 1){
                  stop = 0;
                  break;
              }
              else if(newItem == 1){
                  inventory[0][2] = inventory[0][2] + 1;
                  System.out.println("You found a Healing Item!");
                  stop = 1;
                  break;
              }
              else{
                  stop = 1;
              }
          }
          if(stop == 1){
              break;
          }
      }
      int[][] newArray = new int[inventory.length + 1][4];
      for(int i = 0; i <= inventory.length - 1; i++){
          for(int j = 0; j <= inventory[i].length - 1; j++){
              newArray[i][j] = inventory[i][j];
          }
      }
      for(int i = 0; i <= newArray.length - 1; i++){
          if(newItem != 1){
              if(newArray[i][0] == 0){
                  newArray[i][0] = newItem;
                  if(newItem == 3){
                      newArray[i][1] = 2;
                      newArray[i][2] = 2;
                      newArray[i][3] = 50;
                      break;
                  }
                  else if(newItem == 4){
                      newArray[i][1] = 0;
                      newArray[i][2] = 4;
                      newArray[i][3] = 50;
                      break;
                  }
                  else if(newItem == 5){
                      newArray[i][1] = 4;
                      newArray[i][2] = 0;
                      newArray[i][3] = 50;
                      break;
                  }
                  else{
                      System.out.println("fix stuff");
                  }
              }
          }
      }
      return newArray;
  }
  
  public static int Combat(int playerHealth, int[] playerDamage, int[] characterStats, int[][] inventory){
      Random myRandom = new Random();
      Scanner input = new Scanner(System.in);
      int criticalHit;
      int enemyDamage = 50;
      int enemyCritical = 0;
      int enemyHealth = 200;
      //int healingItems = inventory[0];
      int healing = 75;
      int damage = playerDamage[0] + playerDamage[1] + playerDamage[2];
      int attackDamage = 0;
      String userInput = "";
      System.out.println("You entered combat with an enemy!");
      while(playerHealth > 0 && enemyHealth > 0){
          System.out.println("The enemy has " + enemyHealth + " and you have " + playerHealth + " left and " + inventory[0][2] + " healing items left");
          System.out.println("Your current weapon deals " + damage + " damage");
          System.out.println("Will you:");
          System.out.println("(a) attack (h) heal");
          while(true){
          userInput = input.nextLine();
              if(userInput.equals("a")){
                  criticalHit = (myRandom.nextInt(100) + 1);
                  if(criticalHit <= characterStats[3]){
                      criticalHit = 50;
                      System.out.println("You landed a critical hit!");
                  }
                  else{
                      criticalHit = 0;
                  }
                  attackDamage = damage + criticalHit;
                  enemyHealth = enemyHealth - attackDamage;
                  break;
              }
              else if(userInput.equals("h")){
                  if(inventory[0][2] > 0){
                      inventory[0][2] = inventory[0][2] - 1;
                      playerHealth = playerHealth + 100;
                  }
                  else{
                      System.out.println("You are out of healing items!");
                  }
                  break;
              }
              else{
                  System.out.println("Please enter either 'a' or 'h'");
              }
          }
          if(enemyHealth > 0){
              enemyCritical = (myRandom.nextInt(3)+1) * 10;
              playerHealth = playerHealth - enemyDamage - enemyCritical;
          }
          if(enemyHealth < 0){
              enemyHealth = 0;
              System.out.println("Victory!");
          }
          if(playerHealth <= 0){
              System.out.println("You have died");
              System.exit(0);
          }
      }
      return playerHealth;
  }
}