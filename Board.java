import java.util.Scanner;

public class Board {
    private int size;
    private char board[][];

    public Board(Scanner scanner) {
        this.setBoardSize(scanner);
        this.initializeBoard();
    }

    private void setBoardSize(Scanner scanner) {
        System.out.print("Enter board size (integer): ");

        this.size = scanner.nextInt();    
    }
    
    private void initializeBoard() {
        this.board = new char[this.size][this.size];

        for (int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
                this.board[i][j] = '-';
            }
        }
    }

    public void updateBoard(int row, int column, char currentPlayer) {
        this.board[row][column] = currentPlayer;
    }

    public void displayBoard() {
        System.out.println();

        for (int i = 0; i < this.size; i++) {
            System.out.print('|');

            for(int j = 0; j < this.size; j++) {
                if (this.board[i][j] == '-') {
                    System.out.print(" |");
                } else {
                    System.out.print(this.board[i][j] + "|");
                }
            }

            System.out.println();
        }
    }

    public char[][] getBoard() {
        return this.board;
    }

    public int getBoardSize() {
        return this.size;
    }
}
