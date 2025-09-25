import java.util.Scanner;

public class Game {
    private static char currentPlayer;
    private static boolean gameOver = false;
    private static boolean draw = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board board = new Board(scanner);
        Bot bot = new Bot();

        Game.setFirstMove(scanner);
        bot.setSearchDepth(9);

        while (true) {
            Game.playMove(board, scanner);
            board.displayBoard();
            checkGameStatus(board);

            if (Game.gameOver) {
                break;
            }

            if (Game.currentPlayer == 'x') {
                Game.currentPlayer = 'o';
            } else if (Game.currentPlayer == 'o') {
                Game.currentPlayer = 'x';
            }

            int[] move = bot.getMove(board);

            board.updateBoard(move[0], move[1], Game.currentPlayer);
            System.out.println("\nBot plays: (" + move[0] + ", " + move[1] + ")");
            board.displayBoard();
            checkGameStatus(board);

            if (Game.gameOver) {
                break;
            }

            if (Game.currentPlayer == 'x') {
                Game.currentPlayer = 'o';
            } else if (Game.currentPlayer == 'o') {
                Game.currentPlayer = 'x';
            }
        }

        if (Game.draw) {
            System.out.println("Draw!");
        } else {
            System.out.println("\nGame over! Player " + currentPlayer + " won.");
        }

        scanner.close();
    }

    private static void setFirstMove(Scanner scanner) {
        System.out.println(
                "\nI did not attend this class because I was competing in Informatrix in Romania.\nFrom what I heard, the task is to create the strongest ox bot.\nThe only constraint is that the player alwasys starts the game.");
        System.out.println("Choose your option.");
        System.out.println("'o' is the first player.");
        System.out.println("'x' is the second player.");
        System.out.print("Select your option (o or x): ");

        if (scanner.hasNext()) {
            Game.currentPlayer = scanner.next().charAt(0);
        } else {
            System.out.println("No input detected. Default = 'o'");

            Game.currentPlayer = 'o';
        }

        if (Game.currentPlayer != 'o' && Game.currentPlayer != 'x') {
            System.out.println("Invalid option. Default = 'o'");

            Game.currentPlayer = 'o';
        }
    }

    private static void playMove(Board board, Scanner scanner) {
        int row, column;

        while (true) {
            System.out.println("\nInput your move");
            System.out.print("Row: ");

            row = scanner.nextInt();

            System.out.print("Column: ");

            column = scanner.nextInt();

            if (row >= 0 && row < board.getBoardSize() && column >= 0 && column < board.getBoardSize()) {
                if (board.getBoard()[row][column] != '-') {
                    System.out.println("Invalid move, please input the position again.");
                } else {
                    break;
                }
            } else {
                System.out.println("Move out of bound, please input the position again.");
            }
        }

        board.updateBoard(row, column, Game.currentPlayer);
    }

    private static void checkGameStatus(Board board) {
        char[][] b = board.getBoard();
        int size = board.getBoardSize();

        // Check rows
        for (int row = 0; row < size; row++) {
            boolean win = true;

            for (int col = 0; col < size; col++) {
                if (b[row][col] != Game.currentPlayer) {
                    win = false;

                    break;
                }
            }
            if (win) {
                Game.gameOver = true;

                return;
            }
        }

        // Check columns
        for (int col = 0; col < size; col++) {
            boolean win = true;

            for (int row = 0; row < size; row++) {
                if (b[row][col] != Game.currentPlayer) {
                    win = false;

                    break;
                }
            }
            if (win) {
                Game.gameOver = true;

                return;
            }
        }

        // Check \
        boolean win = true;
        for (int i = 0; i < size; i++) {
            if (b[i][i] != Game.currentPlayer) {
                win = false;

                break;
            }
        }
        if (win) {
            Game.gameOver = true;

            return;
        }

        // Check /
        win = true;
        for (int i = 0; i < size; i++) {
            if (b[i][size - 1 - i] != Game.currentPlayer) {
                win = false;

                break;
            }
        }
        if (win) {
            Game.gameOver = true;

            return;
        }

        boolean full = true;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (b[row][col] == '-') {
                    full = false;

                    break;
                }
            }
        }
        if (full) {
            Game.gameOver = true;
            Game.draw = true;
        }
    }

    public static char getCurrentPlayer() {
        return Game.currentPlayer;
    }

    public static char getOpponentPlayer() {
        if (Game.currentPlayer == 'x') {
            return 'o';
        } else if (Game.currentPlayer == 'o') {
            return 'x';
        }

        return '-';
    }
}
