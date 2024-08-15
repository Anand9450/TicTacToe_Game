package anand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame {
    private JButton[][] buttons;
    private char currentPlayer;
    private int size;
    private int[] player1RowMarkings, player1ColMarkings;
    private int[] player2RowMarkings, player2ColMarkings;
    private int player1MainDiagMarkings, player1AntiDiagMarkings;
    private int player2MainDiagMarkings, player2AntiDiagMarkings;

    public TicTacToeGUI(int size) {
        this.size = size;
        buttons = new JButton[size][size];
        currentPlayer = 'X';
        player1RowMarkings = new int[size];
        player1ColMarkings = new int[size];
        player2RowMarkings = new int[size];
        player2ColMarkings = new int[size];
        player1MainDiagMarkings = player1AntiDiagMarkings = 0;
        player2MainDiagMarkings = player2AntiDiagMarkings = 0;

        setTitle("Tic Tac Toe");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(size, size));

        initializeBoard();
        setVisible(true);
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                add(buttons[i][j]);
            }
        }
    }

    private boolean makeMove(int row, int col) {
        if (currentPlayer == 'X') {
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

    private void resetGame() {
        currentPlayer = 'X';
        player1RowMarkings = new int[size];
        player1ColMarkings = new int[size];
        player2RowMarkings = new int[size];
        player2ColMarkings = new int[size];
        player1MainDiagMarkings = player1AntiDiagMarkings = 0;
        player2MainDiagMarkings = player2AntiDiagMarkings = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            buttons[row][col].setText(String.valueOf(currentPlayer));
            buttons[row][col].setEnabled(false);

            boolean gameWon = makeMove(row, col);
            if (gameWon) {
                JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                resetGame();
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }
    }

    public static void main(String[] args) {
        new TicTacToeGUI(3);
    }
}
