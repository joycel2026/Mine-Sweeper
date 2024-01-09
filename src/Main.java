import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main{
    public static void main(String[] args) {
        System.out.println("Welcome to MineSweeper! Hope you enjoy it :D");
        System.out.println("This game is made up of a 9*9 grid with a total number of 81 spots.");
        System.out.println("Two coordinates are assigned to each spot in the form of (x,y). The x value will be the column number of the spot with the y value representing its row number.");
        System.out.println("You can click a spot by responding in the format of 'n,x value,y value'.");
        System.out.println("By inserting “n” for the first index, it indicates “no” and your inference that the spot does not contain a bomb, which would then reveal the authentic identity of that spot.");
        System.out.println("The second and third index will locate the specific spot you are targeting.");
        System.out.println("If the targeting spot is indeed empty, the block will be replaced by a number manifesting the number of neighbour spots that do contain mines.");
        System.out.println("You can mark a mine by responding in the format of 'y,x value,y value'.");
        System.out.println("By inserting “y” for the first coordinate, it indicates “yes” and the user’s inference that the spot contains a bomb, which would then mark the spot with a pinpoint📍.");
        System.out.println("Kind reminder to strictly follow the response format of '_,_,_' with no space in between :>");
        System.out.println("After your first click, 9 bombs will be planted randomly within the gird, Good luck!");
        System.out.println("Sorry for the long instruction...but now hope you understand the game rules ^^");
        String[][] grid = new String[9][9];
        for (int col = 0; col < 9; col++) {
            for (int row = 0; row < 9; row++) {
                grid[col][row] = "⬜";
            }
        }
        for (int col = 0; col < 9; col++) {
            for (int row = 0; row < 9; row++) {
                System.out.print(grid[col][row] + " ");
            }
            System.out.println();
        }
        int spot = 72;
        int pinpoint = 9;
        System.out.println("Remaining spots:"+spot+"\nRemaining Pinpoints for Marking Mines:"+pinpoint);
        System.out.println("Please start your game by making your first click at a random spot in the format of 'n,x,y':");
        Scanner firstClick = new Scanner(System.in);
        String FirstClick = firstClick.next();
        while (lengthCheck(FirstClick)){
            System.out.println("Please input a valid response in the correct format of 'n,x,y'");
            FirstClick = firstClick.next();
        }while(FirstClick(FirstClick)) {
            System.out.println("Please input a valid response in the format of 'n,x,y', "+firstClick(FirstClick));
            FirstClick = firstClick.next();
        }
        int userRow = Integer.parseInt(FirstClick.substring(2,3))-1;
        int userCol = Integer.parseInt(FirstClick.substring(4))-1;
        int[] bombLocations = new int[9];
        Random random = new Random();
        int bombsPlaced = 0;
        while (bombsPlaced < 9) {
            int location = random.nextInt(81);
            int col = location / 9;
            int row = location % 9;
            if (col != userCol || row != userRow) {
                if (!contains(bombLocations, location)) {
                    bombLocations[bombsPlaced] = location;
                    bombsPlaced++;
                }
            }
        }
        Map<String, String> dictionary = new HashMap<>();
        dictionary.put("0", "0️⃣");
        dictionary.put("1", "1️⃣");
        dictionary.put("2", "2️⃣");
        dictionary.put("3", "3️⃣");
        dictionary.put("4", "4️⃣");
        dictionary.put("5", "5️⃣");
        dictionary.put("6", "6️⃣");
        dictionary.put("7", "7️⃣");
        dictionary.put("8", "8️⃣");
        dictionary.put("9", "9️⃣");
        int bombCount = countAdjacentBombs(bombLocations, userCol, userRow);
        grid[userCol][userRow] = dictionary.get(Integer.toString(bombCount));
        spot -= 1;
        if (bombCount == 0) {
            updateAdjacentSpots(bombLocations, grid, userCol, userRow);
            spot = spotLeft(grid);
        }
        for (int col = 0; col < 9; col++) {
            for (int row = 0; row < 9; row++) {
                System.out.print(grid[col][row] + " ");
            }
            System.out.println();
        }
        System.out.println("Remaining spots:"+spot+"\nRemaining Pinpoints for Marking Mines:"+pinpoint);
        System.out.println("Nice! You've successfully made your first click! 9 bombs have already been placed randomly within the gird :>\nnow you can continue clicking or marking a spot by entering 'n/y,x,y':");
        while(spot>0){
            Scanner userClick = new Scanner(System.in);
            String UserClick = userClick.next();
            while (lengthCheck(UserClick)){
                System.out.println("Please input a valid response in the correct format of 'y/n,x,y'");
                UserClick = userClick.next();
            }while(clickCheck(UserClick)) {
                System.out.println("Please input a valid response in the format of 'y/n,x,y', "+ClickCheck(UserClick));
                UserClick = userClick.next();
            }
            userRow = Integer.parseInt(UserClick.substring(2,3))-1;
            userCol = Integer.parseInt(UserClick.substring(4))-1;
            String userChoice = UserClick.substring(0,1);
            if (userChoice.equals("n")){
                int location = userCol * 9 + userRow;
                if (contains(bombLocations, location)){
                    for (int col = 0; col < 9; col++) {
                        for (int row = 0; row < 9; row++) {
                            location = col * 9 + row;
                            if (contains(bombLocations, location)){
                                grid[col][row] = "💣";
                            }
                            System.out.print(grid[col][row] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println("OOPS! THAT SPOT IS A MINE T^T NICE GAME THO, HOPE YOU ENJOYED IT :D ");
                    System.exit(0);
                }
                if (grid[userCol][userRow] != "⬜"&&grid[userCol][userRow] != "📍"){
                    System.out.println("Sorry but you cannot click an already revealed spot, please choose another spot by entering 'n/y,x,y':");
                }
                if (grid[userCol][userRow] == "📍"){
                    System.out.println("Are you sure that you want to click on a previously marked spot? please enter 'y' for yes and 'n' for no:");
                    Scanner yesOrNo = new Scanner(System.in);
                    String YesOrNo = yesOrNo.next();
                    while ((!YesOrNo.equals("y"))&&(!YesOrNo.equals("n"))){
                        System.out.println("Please input a valid response of either 'y' or 'n'");
                        YesOrNo = yesOrNo.next();
                    }
                    if (YesOrNo.equals("y")){
                        bombCount = countAdjacentBombs(bombLocations, userCol, userRow);
                        grid[userCol][userRow] = dictionary.get(Integer.toString(bombCount));
                        spot -= 1;
                        pinpoint += 1;
                        if (bombCount == 0) {
                            updateAdjacentSpots(bombLocations, grid, userCol, userRow);
                            spot = spotLeft(grid);
                        }
                        for (int col = 0; col < 9; col++) {
                            for (int row = 0; row < 9; row++) {
                                System.out.print(grid[col][row] + " ");
                            }
                            System.out.println();
                        }
                        System.out.println("Remaining spots:"+spot+"\nRemaining Pinpoints for Marking Mines:"+pinpoint);
                        System.out.println("Please continue playing by clicking or marking a spot in the form of 'n/y,x,y':");
                    }
                    if (YesOrNo.equals("n")){
                        System.out.println("Okie, please continue playing by clicking or marking a spot in the form of 'n/y,x,y':");
                    }
                }
                if (grid[userCol][userRow] == "⬜") {
                    bombCount = countAdjacentBombs(bombLocations, userCol, userRow);
                    grid[userCol][userRow] = dictionary.get(Integer.toString(bombCount));
                    spot -= 1;
                    if (bombCount == 0) {
                        updateAdjacentSpots(bombLocations, grid, userCol, userRow);
                        spot = spotLeft(grid);
                    }
                    for (int col = 0; col < 9; col++) {
                        for (int row = 0; row < 9; row++) {
                            System.out.print(grid[col][row] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println("Remaining spots:"+spot+"\nRemaining Pinpoints for Marking Mines:"+pinpoint);
                    System.out.println("Please continue playing by clicking or marking a spot in the form of 'n/y,x,y':");
                }
            }if (userChoice.equals("y")){
                if (grid[userCol][userRow] != "⬜") {
                    System.out.println("Sorry but you cannot mark an already clicked spot, please choose another spot by entering 'n/y,x,y':");
                }if (grid[userCol][userRow] == "⬜") {
                    grid[userCol][userRow] = "📍";
                    pinpoint-=1;
                    for (int col = 0; col < 9; col++) {
                        for (int row = 0; row < 9; row++) {
                            System.out.print(grid[col][row] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println("Remaining spots:"+spot+"\nRemaining Pinpoints for Marking Mines:"+pinpoint);
                    System.out.println("Please continue playing by clicking or marking a spot in the form of 'n/y,x,y':");
                }
            }
        }
        if (spot==0){
            for (int col = 0; col < 9; col++) {
                for (int row = 0; row < 9; row++) {
                    if (grid[col][row] == "⬜"){
                        grid[col][row] = "💣";
                    }
                }
            }
            System.out.println("NICE GAME! YOU HAVE SWEPT OUT ALL THE MINES!! CONGRATULATIONS YAY!!! :D");
        }






    }
    public static boolean isNumeric(String val){
        int i;
        try {
            i = Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return false;
        }
        return i > 0 && i < 10;
    }
    public static boolean lengthCheck(String response){
        return response.length()<5;
    }
    public static String firstClick(String response){
        String zero = "";
        String one = "";
        String two = "";
        String three = "";
        String four = "";
        if (response.charAt(0)!='n'){
            zero = "first coordinate(it should be 'n' for the first click), ";
        }if (response.charAt(1)!=','){
            one = "comma between the first and second indexes, ";
        }if (!isNumeric(response.substring(2, 3))){
            two = "x value(should be an integer between 1 to 9), ";
        }if (response.charAt(3)!=','){
            three = "comma between the second and third indexes, ";
        }if (!isNumeric(response.substring(4))){
            four = "y value(should be an integer between 1 to 9), ";
        }
        return "check your "+zero+one+two+three+four;
    }
    public static String ClickCheck(String response){
        String zero = "";
        String one = "";
        String two = "";
        String three = "";
        String four = "";
        if ((response.charAt(0)!='n')&&(response.charAt(0)!='y')){
            zero = "first coordinate(should be either 'x' or 'y'), ";
        }if (response.charAt(1)!=','){
            one = "comma between the first and second indexes, ";
        }if (!isNumeric(response.substring(2, 3))){
            two = "x value(should be an integer between 1 to 9), ";
        }if (response.charAt(3)!=','){
            three = "comma between the second and third indexes, ";
        }if (!isNumeric(response.substring(4))){
            four = "y value(should be an integer between 1 to 9), ";
        }
        return "check your "+zero+one+two+three+four;
    }
    public static boolean FirstClick(String response){
        if((response.charAt(0)!='n')||(response.charAt(1)!=',')||(response.charAt(3)!=',')||(!isNumeric(response.substring(2, 3)))||(!isNumeric(response.substring(4)))){
            return true;
        }return false;
    }
    public static boolean clickCheck(String response){
        if(((response.charAt(0)!='n')&&(response.charAt(0)!='y'))||(response.charAt(1)!=',')||(response.charAt(3)!=',')||(!isNumeric(response.substring(2, 3)))||(!isNumeric(response.substring(4)))){
            return true;
        }return false;
    }

    private static boolean contains(int[] array, int value) {
        for (int i : array) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }
    private static int countAdjacentBombs(int[] bombLocations, int col, int row) {
        int bombCount = 0;
        for (int i = col - 1; i <= col + 1; i++) {
            for (int j = row - 1; j <= row + 1; j++) {
                int location = i * 9 + j;
                if (isValidIndex(i, j) && contains(bombLocations, location)) {
                    bombCount++;
                }
            }
        }
        return bombCount;
    }

    private static boolean isValidIndex(int col, int row) {
        return row >= 0 && row < 9 && col >= 0 && col < 9;
    }
    private static void updateAdjacentSpots(int[] bombLocations, String[][] grid, int col, int row) {
        Map<String, String> dictionary = new HashMap<>();
        dictionary.put("0", "0️⃣");
        dictionary.put("1", "1️⃣");
        dictionary.put("2", "2️⃣");
        dictionary.put("3", "3️⃣");
        dictionary.put("4", "4️⃣");
        dictionary.put("5", "5️⃣");
        dictionary.put("6", "6️⃣");
        dictionary.put("7", "7️⃣");
        dictionary.put("8", "8️⃣");
        dictionary.put("9", "9️⃣");
        for (int i = col - 1; i <= col + 1; i++) {
            for (int j = row - 1; j <= row + 1; j++) {
                if (isValidIndex(i, j) && grid[i][j] == "⬜") {
                    int bombCount = countAdjacentBombs(bombLocations, i, j);
                    grid[i][j] = dictionary.get(Integer.toString(bombCount));
                    if (bombCount == 0) {
                        updateAdjacentSpots(bombLocations, grid, i, j);
                    }
                }
            }
        }
    }
    private static int spotLeft(String[][] grid){
        int result = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j].equals("⬜")){
                    result++;
                }
            }
        }
        return result;
    }

}