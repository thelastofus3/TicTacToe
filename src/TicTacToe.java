import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    private static int ROW_COUNT = 3;
    private static int COL_COUNT = 3;
    private static String CELL_EMPTY = " ";
    private static String CELL_X = "X";
    private static String CELL_O = "O";
    private static String GAME_STATE_X_WON = "X ПОБЕДИЛИ!";
    private static String GAME_STATE_O_WON = "O ПОБЕДИЛИ!";
    private static String GAME_STATE_DRAW = "НИЧЬЯ";
    private static String GAME_STATE_NOT_FINISHED = "ИГРА НЕ ЗАКОНЧЕНА";
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
//        String[][] board = createBoard();
//        board[0][0] = CELL_X;
//        inputCellCoordinates(board);

        while(true) startGameRound();
    }
    public static void startGameRound(){
        System.out.println();
        System.out.println("Начало раунда:");
        String[][] board = createBoard();
        startGameLoop(board);
    }
    public static String[][] createBoard(){
        String[][] board = new String[ROW_COUNT][COL_COUNT];
        for (int row = 0 ; row < ROW_COUNT; row++ ){
            for (int col = 0; col < COL_COUNT; col++) {
                board[row][col] = CELL_EMPTY;
            }
        }
        return board;
    }

    public static void startGameLoop(String[][] board) {
        boolean playerTurn = true;
        do {
            if(playerTurn){
                makePlayerTurn(board);
                printBoard(board);
            }else{
                makeBotTurn(board);
                printBoard(board);
            }

            playerTurn = !playerTurn;

            System.out.println();

            String gameState = checkGameState(board);

            if (!Objects.equals(gameState, GAME_STATE_NOT_FINISHED)) {
                System.out.println(gameState);
                return;
            }
            gameState = checkGameState(board);
        }while (true);

    }
    public static void makePlayerTurn(String[][] board){

        // get input
        int[] coordinates = inputCellCoordinates(board);

        // place X on board
        board[coordinates[0]][coordinates[1]] = CELL_X;
    }
    public static int[] inputCellCoordinates(String[][] board){
        System.out.println("Введите 2 числа(ряд и колонка) от 0 до 2 через пробел (0-2):");

        do{
            //допущение - не проверяем на наличие пробела, и корректность цифр

            String[] input = scanner.nextLine().split(" ");

            int row = Integer.parseInt(input[0]);
            int col = Integer.parseInt(input[1]);
            if((row < 0) || (row >= ROW_COUNT) || (col < 0) || (col >= COL_COUNT)){
                System.out.println("Некорректное значение! Введите 2 числа от 0 до 2 через пробел:");
            } else if(!Objects.equals(board[row][col], CELL_EMPTY)) {
                System.out.println("Данная ячейка уже занята");
            }else{
                return new int[]{row,col};
            }
        }while (true);
    }

    public static void makeBotTurn(String[][] board){
        System.out.println("Ход бота");
        int[] coordinates = getRandomEmptyCellCoordinates(board);
        board[coordinates[0]][coordinates[1]] = CELL_O;
    }
    public static int[] getRandomEmptyCellCoordinates(String[][] board){
        do {
            int row  = random.nextInt(ROW_COUNT);
            int col = random.nextInt(COL_COUNT);

            if (Objects.equals(board[row][col], CELL_EMPTY)) {
                return new int[]{row,col};
            }
        }while (true);


        // get random empty cell
        // place O on board
    }
    public static String checkGameState(String[][] board){
        ArrayList<Integer> sums = new ArrayList<>();

        //iterate rows
        for (int row = 0; row < ROW_COUNT; row++) {
            int rowSum = 0;
            for (int col = 0; col < COL_COUNT; col++) {
                rowSum += calculateNumValue(board[row][col]);
            }
            sums.add(rowSum);
        }

        //iterate columns
        for (int col = 0; col < COL_COUNT; col++) {
            int colSum = 0;
            for (int row = 0; row < ROW_COUNT; row++) {
                colSum += calculateNumValue(board[row][col]);
            }
            sums.add(colSum);
        }

        //diagonal from top-left to bottom-right
        int leftDiagonalSum = 0;
        for (int i = 0; i < ROW_COUNT; i++) {
            leftDiagonalSum += calculateNumValue(board[i][i]);
        }
        sums.add(leftDiagonalSum);

        //diagonal from top-right to bottom-left
        int rightDiagonalSum = 0;
        for (int i = 0; i < ROW_COUNT; i++) {
            rightDiagonalSum += calculateNumValue(board[i][(ROW_COUNT - 1) - i]);
        }
        sums.add(leftDiagonalSum);

        if (sums.contains(3)){
            return GAME_STATE_X_WON;
        }else if(sums.contains(-3)){
            return GAME_STATE_O_WON;
        }else if(areAllCellTaken(board)){
            return GAME_STATE_DRAW;
        }else{
            return GAME_STATE_NOT_FINISHED;
        }
    }
    //X - 1 , O - (-1),EMPTY - 0
    //используется для проверки победителя

    private static int calculateNumValue(String cellState) {
        if(Objects.equals(cellState, CELL_X)){
            return 1;
        }else if(Objects.equals(cellState, CELL_O)){
            return -1;
        }else{
            return 0;
        }
    }

    public static boolean areAllCellTaken(String[][] board){
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < COL_COUNT; col++) {
                if(Objects.equals(board[row][col], CELL_EMPTY)){
                    return false;
                }
            }
        }
        return true;
    }
    public static void printBoard(String[][] board){
        System.out.println(" -------");
        for (int row = 0; row < ROW_COUNT; row++) {
            String line =  "| ";
            for (int col = 0; col < COL_COUNT; col++) {
                line += board[row][col] + " ";
            }
            line += "|";
            System.out.println(line);
        }
        System.out.println(" -------");
    }
}
