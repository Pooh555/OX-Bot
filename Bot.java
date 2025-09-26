public class Bot {
    private int response[];
    private int searchDepth;

    public Bot() {
        this.response = new int[2];
    }

    private int minimax(Board board, int depth, boolean isMaximizing) {
        if (checkGameStatus(board, Game.getCurrentPlayer())) return 10 - depth;
        if (checkGameStatus(board, Game.getOpponentPlayer())) return depth - 10;
        if (isFull(board) || depth == 0) return 0;

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int row = 0; row < board.getBoardSize(); row++) {
                for (int column = 0; column < board.getBoardSize(); column++) {
                    if (board.getBoard()[row][column] == '-') {
                        board.updateBoard(row, column, Game.getCurrentPlayer());
                        
                        best = Math.max(best, minimax(board, depth - 1, false));
                        
                        board.updateBoard(row, column, '-');
                    }
                }
            }

            return best;
        } else {
            int best = Integer.MAX_VALUE;
            
            for (int row = 0; row < board.getBoardSize(); row++) {
                for (int column = 0; column < board.getBoardSize(); column++) {
                    if (board.getBoard()[row][column] == '-') {
                        board.updateBoard(row, column, Game.getOpponentPlayer());
                        
                        best = Math.min(best, minimax(board, depth - 1, true));
                        
                        board.updateBoard(row, column, '-');
                    }
                }
            }

            return best;
        }
    }

    private void calculate(int iteration, Board board) {
        int bestVal = Integer.MIN_VALUE;

        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int column = 0; column < board.getBoardSize(); column++) {
                if (board.getBoard()[row][column] == '-') {
                    board.updateBoard(row, column, Game.getCurrentPlayer());

                    // ✅ Check for immediate win
                    if (checkGameStatus(board, Game.getCurrentPlayer())) {
                        this.response[0] = row;
                        this.response[1] = column;
                        board.updateBoard(row, column, '-');
                        return; // play winning move immediately
                    }

                    // Otherwise, use minimax
                    int moveVal = minimax(board, iteration - 1, false);
                    board.updateBoard(row, column, '-');

                    if (moveVal > bestVal) {
                        bestVal = moveVal;
                        this.response[0] = row;
                        this.response[1] = column;
                    }
                }
            }
        }
    }

    public int[] getMove(Board board) {
        calculate(this.searchDepth, board);
        
        return this.response;
    }

    private boolean isFull(Board board) {
        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int column = 0; column < board.getBoardSize(); column++) {
                if (board.getBoard()[row][column] == '-') return false;
            }
        }

        return true;
    }

    private boolean checkGameStatus(Board board, char player) {
        for (int row = 0; row < board.getBoardSize(); row++) {
            boolean win = true;
            for (int column = 0; column < board.getBoardSize(); column++) {
                if (board.getBoard()[row][column] != player) {
                    win = false;

                    break;
                }
            }
            if (win) return true;
        }
        for (int column = 0; column < board.getBoardSize(); column++) {
            boolean win = true;

            for (int row = 0; row < board.getBoardSize(); row++) {
                if (board.getBoard()[row][column] != player) {
                    win = false;

                    break;
                }
            }

            if (win) return true;
        }
        boolean win = true;
        for (int i = 0; i < board.getBoardSize(); i++) {
            if (board.getBoard()[i][i] != player) {
                win = false;
                break;
            }
        }
        if (win) return true;
        win = true;
        for (int i = 0; i < board.getBoardSize(); i++) {
            if (board.getBoard()[i][board.getBoardSize() - i - 1] != player) {
                win = false;
                break;
            }
        }
        return win;
    }

    public void setSearchDepth(int depth) {
        this.searchDepth = depth;
    }
}
