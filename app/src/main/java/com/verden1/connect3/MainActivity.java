package com.verden1.connect3;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView winnerMessage;
    boolean gameIsActive = true;

    // 0 = blue, 1 = red
    int activePlayer = 0;

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2}; //2 - unplayed

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},    //rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},    // columns
            {0, 4, 8}, {2, 4, 6}};              // diagonals

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameIsActive) {
            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1000f);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.blue);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1000f).setDuration(300);

            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2) {

                    String winner = "Red";

                    winnerMessage = (TextView) findViewById(R.id.tvWinnerMessage);
                    winnerMessage.setTextColor(Color.RED);
                    if (gameState[winningPosition[0]] == 0) {
                        winner = "Blue";
                        winnerMessage.setTextColor(Color.BLUE);
                    }

                    winnerMessage.setText(winner + " has Won!");

                    // someone has won
                    gameIsActive = false;
                    LinearLayout layout = (LinearLayout) findViewById(R.id.llPlayAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                    break;
                } else {
                    boolean gameIsOver = true;
                    for (int counterState : gameState) {
                        if (counterState == 2) {
                            gameIsOver = false;
                        }
                    }
                    if (gameIsOver) {
                        TextView winnerMessage = (TextView) findViewById(R.id.tvWinnerMessage);
                        winnerMessage.setText("It's a draw!");
                        gameIsActive = false;
                        LinearLayout layout = (LinearLayout) findViewById(R.id.llPlayAgainLayout);
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.llPlayAgainLayout);
        layout.setVisibility(View.INVISIBLE);
        winnerMessage.setTextColor(Color.parseColor("#FF00FF"));
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        activePlayer = 0;
        gameIsActive = true;

        GridLayout gridLayout = (GridLayout) findViewById(R.id.glGameBoard);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
