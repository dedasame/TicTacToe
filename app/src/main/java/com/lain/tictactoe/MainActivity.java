package com.lain.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView playerOneScore, playerTwoScore,playerStatus;
    Button [] buttons = new Button[9];
    Button resetGame;
    int playerOneScoreCount, playerTwoScoreCount,rountCount;
    boolean activePlayer;
    int [] gameState = {2,2,2,2,2,2,2,2,2};
    int [][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = findViewById(R.id.playerOneScore);
        playerTwoScore = findViewById(R.id.playerTwoScore);
        playerStatus = findViewById(R.id.playerStatus);
        resetGame = findViewById(R.id.btn_restart);

        for(int i = 0; i<buttons.length;i++){
            String buttonID = "btn_"+i;
            int resourceID = getResources().getIdentifier(buttonID,"id",getPackageName());
            buttons[i] = findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }
        rountCount=0;
        playerOneScoreCount=0;
        playerTwoScoreCount=0;
        activePlayer=true;


    }


    @Override
    public void onClick(View v) {

        if(!((Button)v).getText().toString().equals("")) return;
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length()));


        if(activePlayer){
            ((Button)v).setText("X");
            ((Button)v).setTextColor(Color.parseColor("#f2ff00"));
            gameState[gameStatePointer] = 0;
        }
        else{
            ((Button)v).setText("O");
            ((Button)v).setTextColor(Color.parseColor("#00ff5e"));
            gameState[gameStatePointer] = 1;
        }
        rountCount++;

        if(checkWinner()==true){

            //Kazanan varsa
            if(activePlayer==true){
                playerOneScoreCount++;
                updatePlayerScore();
                playerStatus.setText("1. OYUNCU KAZANDI");
                playAgain();
            }
            else{
                playerTwoScoreCount++;
                updatePlayerScore();
                playerStatus.setText("2. OYUNCU KAZANDI");
                playAgain();
            }

        }
        //butonlardaki her yer dolduğu için beraberlik
        else if(rountCount==9){
            playAgain();
            playerStatus.setText("BERABERLİK");
        }

        else{
            //kazanma durumu yoksa devam etmesi için aktif oyuncuyu değiştiriyoruz.
            activePlayer = !activePlayer;
        }

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                playerOneScoreCount=0;
                playerTwoScoreCount=0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });


    }

    public boolean checkWinner() {
        boolean winnerResult = false;

        for (int[] winningPosion : winningPositions) {
            if (gameState[winningPosion[0]] == gameState[winningPosion[1]] &&
                    gameState[winningPosion[1]] == gameState[winningPosion[2]] &&
                    gameState[winningPosion[0]] != 2) {
                winnerResult = true;
            }
        }
        return winnerResult;
    }

    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    public void playAgain(){
        rountCount=0;
        activePlayer=true;

        for(int i = 0; i< buttons.length;i++){
            gameState[i] = 2;
            buttons[i].setText("");
        }

    }







}