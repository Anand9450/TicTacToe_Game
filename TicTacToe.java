package anand;

import java.util.Scanner;

public class TicTacToe {
    private int[] player1RowMarkings, player1ColMarkings;
    private int[] player2RowMarkings, player2ColMarkings;
    private int player1MainDiagMarkings, player1AntiDiagMarkings;
    private int player2MainDiagMarkings, player2AntiDiagMarkings;
    private int size;
    private char[][] board;

    public TicTacToe(int size) {
        player1RowMarkings = new int[size];
        player1ColMarkings = new int[size];
        player2RowMarkings = new int[size];
        player2ColMarkings = new int[size];
        player1MainDiagMarkings = player1AntiDiagMarkings = 0;
        player2MainDiagMarkings = player2AntiDiagMarkings = 0;
        this.size = size;
        board = new char[size][size];
    }

    public boolean makeMove(char ch, int row, int col) {
        if (row < 0 || col < 0 || row >= size || col >= size) {
            throw new RuntimeException("Move out of bounds of board");
        }

        if (ch != 'X' && ch != 'O') {
            throw new RuntimeException("Invalid character entered");
        }

        if (board[row][col] != 0) {
            throw new RuntimeException("Invalid Move");
        }

        board[row][col] = ch;

        if (ch == 'X') {
            return markPlayer(player1RowMarkings, player1ColMarkings, row, col, true);
        } else {
            return markPlayer(player2RowMarkings, player2ColMarkings, row, col, false);
        }
    }

    private boolean markPlayer(int[] row, int[] col, int rowPlacement, int colPlacement, boolean isPlayer1) {
        if (++row[rowPlacement] == size || ++col[colPlacement] == size) {
            return true;
        }

        if (rowPlacement == colPlacement) {
            if (isPlayer1) {
                if (++player1MainDiagMarkings == size) return true;
            } else {
                if (++player2MainDiagMarkings == size) return true;
            }
        }

        if (rowPlacement + colPlacement == size - 1) {
            if (isPlayer1) {
                if (++player1AntiDiagMarkings == size) return true;
            } else {
                if (++player2AntiDiagMarkings == size) return true;
            }
        }

        return false;
    }

    private void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] == 0 ? '-' : board[i][j]);
                if (j < size - 1) System.out.print(" | ");
            }
            System.out.println();
            if (i < size - 1) {
                System.out.println("---------");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TicTacToe ticTacToe = new TicTacToe(3);
        boolean gameWon = false;
        char currentPlayer = 'X';

        while (!gameWon) {
            ticTacToe.printBoard();
            System.out.println("Player " + currentPlayer + "'s turn. Enter row and column (0, 1, or 2):");

            int row = scanner.nextInt();
            int col = scanner.nextInt();

            try {
                gameWon = ticTacToe.makeMove(currentPlayer, row, col);
                if (gameWon) {
                    ticTacToe.printBoard();
                    System.out.println("Player " + currentPlayer + " wins!");
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }

        scanner.close();
    }
}
