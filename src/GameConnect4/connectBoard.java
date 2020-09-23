package GameConnect4;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

public class connectBoard extends BorderPane {


    public boolean win;
    public int turns;
    public int horizontalCounter = 0;
    public int verticalCounter = 0;
    public int diagonalCounter = 0;
    public int diagonalCounter2 = 0;
    private static final int col = 7;
    private static final int row = 6;


    public connectBoard(String mode) {
        this.getStylesheets().add( "styles/modeStyle.css" );
        this.getStyleClass().add( "mainBack" );

        Text header = new Text( "" );
        header.getStyleClass().add( "title" );


        HBox title = new HBox( 10 );
        title.getChildren().add( header );
        title.getStyleClass().add( "background" );

        Button exit = new Button( "exit" );
        exit.getStyleClass().add( "exitButton" );
            exit.setOnMouseClicked( event -> {
                System.exit( 0 );
            });

        this.setTop( title );
        this.setBottom( exit );
        if (mode.equalsIgnoreCase( "1player" )) {
            this.setCenter( grid( mode ) );
        }
        else if (mode.equalsIgnoreCase( "2player" )){
            this.setCenter( grid( mode ) );
        }

        Scene scene = new Scene(this, 550,650);

        Stage stage = new Stage();
        stage.setScene( scene );
        stage.show();

    }

    public GridPane grid(String mode){
        GridPane c4board = new GridPane();
        if(mode.equalsIgnoreCase( "1player" )){
            playerVsComputer( c4board );
        }
        else if (mode.equalsIgnoreCase( "2player" )){
            playerVsPlayer( c4board );
        }
        c4board.setAlignment( Pos.CENTER );
        c4board.setHgap( 2 );
        c4board.setVgap( 2 );

        return c4board;
    }

    public disc[][] playerVsPlayer(GridPane grid){
     disc[][] array = new disc[row][col];
     int[][] boardLayout = new int[row][col];

        for(int y = 0; y < row; y++){
            for(int x = 0; x < col; x++){
               //ARRAY OF NUMBERS
                int empty = 0;
                boardLayout[y][x] = empty;

               //ARRAY OF CIRCLES
                disc disc_Chip = new disc(y+1,x+1);
                disc_Chip.setStroke( Color.BLACK );
                disc_Chip.setStrokeWidth( 10 );

                grid.add( disc_Chip,x,y );
                array[y][x] = disc_Chip;

                //CIRCLES BEING CLICKED
                disc_Chip.setOnMouseClicked(event ->{
                   int discCol = disc_Chip.getCol() - 1;
                   int rowValue = checkCol(discCol,boardLayout);
                   turns++;

                   if((turns%2)==0 && boardLayout[rowValue][discCol] ==  0){
                       boardLayout[rowValue][discCol] = 2;
                       if(horizontalWin( rowValue,col,boardLayout, 2 ) == true){
                           winnerScreen( 2 , "2player");                       }
                       if(verticalWin( row,discCol,boardLayout,2 ) == true){
                           winnerScreen( 2 , "2player");                       }
                       if(diagonalWin( row,col,boardLayout,2 ) == true){
                           winnerScreen( 2 , "2player");
                       }
                       array[rowValue][discCol].setFill(  Color.rgb( 5,34,181 )  );
                   }

                   else if((turns%2 != 0 && boardLayout[rowValue][discCol] == 0)){
                       boardLayout[rowValue][discCol] = 1;
                       if(horizontalWin( rowValue,col,boardLayout, 1 ) == true){
                           winnerScreen( 1 , "2player");
                       }
                       if(verticalWin( row,discCol,boardLayout,1 ) == true){
                           winnerScreen( 1 , "2player");
                       }
                       if(diagonalWin( row,col,boardLayout,1 ) == true){
                           winnerScreen( 1 , "2player");
                       }
                       array[rowValue][discCol].setFill( Color.rgb( 181,5,5 ) );
                   }
                });
            }
        }

        return array;
    }

    public disc[][] playerVsComputer(GridPane grid){
        disc[][] array = new disc[row][col];
        int[][] boardLayout = new int[row][col];

        for(int y = 0; y < row; y++){
            for(int x = 0; x < col; x++){
                //ARRAY OF NUMBERS
                int empty = 0;
                boardLayout[y][x] = empty;

                //ARRAY OF CIRCLES
                disc disc_Chip = new disc(y+1,x+1);
                disc_Chip.setStroke( Color.BLACK );
                disc_Chip.setStrokeWidth( 10 );

                grid.add( disc_Chip,x,y );
                array[y][x] = disc_Chip;

                //CIRCLES BEING CLICKED
                disc_Chip.setOnMouseClicked(event ->{
                    int computerMove = (int)(Math.random() * 6 + 0);
                    int discCol = disc_Chip.getCol() - 1;
                    int rowValue = checkCol(discCol,boardLayout);
                    int computerRowValue = checkCol(computerMove,boardLayout);

                    //Player Move Red Disc
                    if(boardLayout[rowValue][discCol] == 0){
                        boardLayout[rowValue][discCol] = 1;
                        if(horizontalWin( rowValue,col,boardLayout, 1 ) == true){
                            winnerScreen( 1 , "1player");
                        }
                        if(verticalWin( row,discCol,boardLayout,1 ) == true){
                            winnerScreen( 1 , "1player");
                        }
                        if(diagonalWin( row,col,boardLayout,1 ) == true){
                            winnerScreen( 1, "1player" );
                        }
                        array[rowValue][discCol].setFill( Color.rgb( 181,5,5 ) );
                    }

                    //Computer Move Blue disc
                    if(rowValue == computerRowValue && computerMove == discCol){
                       int computerMove2 = (int)(Math.random() * 6 + 0);
                       boardLayout[computerRowValue][computerMove2] = 2;
                       array[computerRowValue][computerMove2].setFill( Color.rgb( 5,34,181 ) );

                       if(horizontalWin( computerRowValue,col,boardLayout, 2 ) == true){
                          winnerScreen( 2, "1player" );
                       }
                       if(verticalWin( row,computerMove2,boardLayout,2 ) == true){
                          winnerScreen( 2, "1player" );
                       }
                       if(diagonalWin( row,col,boardLayout,2 ) == true){
                          winnerScreen( 2, "1player" );
                       }
                    }
                    else {
                        boardLayout[computerRowValue][computerMove] = 2;
                        array[computerRowValue][computerMove].setFill( Color.rgb( 5, 34, 181 ) );

                        if(horizontalWin( computerRowValue,col,boardLayout, 2 ) == true){
                           winnerScreen( 2, "1player" );
                        }
                        if(verticalWin( row,computerMove,boardLayout,2 ) == true){
                           winnerScreen( 2, "1player" );
                        }
                        if(diagonalWin( row,col,boardLayout,2 ) == true){
                           winnerScreen( 2, "1player" );
                        }
                    }

                });
            }
        }

        return array;
    }
    public int checkCol(int col, int[][] numArray){
        int checked = 0;

        for(int i =0; i < numArray.length; i++){

                if (i < numArray.length - 1) {
                    if (numArray[i + 1][col] == 1 || numArray[i + 1][col] == 2) {
                        checked = i;
                        break;
                    } else if (numArray[i][col] == 0) {
                        checked = i;
                        checked = checked + 1;
                    }
                }

        }
        return checked;
    }

    public boolean horizontalWin(int row, int col,int[][] array, int user){
        for(int i = 0; i < col; i++){
            if(array[row][i] == user){
                horizontalCounter++;
            }
            else {
                horizontalCounter = 0;
            }
            if(horizontalCounter >=4){
                return true;
            }

        }
        return false;
    }

    public boolean verticalWin(int row, int col, int[][] array, int user){
        for(int i = 0; i < row; i++){
            if(array[i][col] == user){
                verticalCounter++;
            }
            else {
                verticalCounter = 0;
            }
            if (verticalCounter >=4){
                return true;
            }
        }
        return false;
    }

    public boolean diagonalWin(int row,int col, int[][] array, int user) {
        for (int i=3; i<row; i++){
            for (int j=0; j<col-3; j++){
                if (array[i][j] == user && array[i-1][j+1] == user && array[i-2][j+2] == user && array[i-3][j+3] == user)
                    return true;
            }
        }
        for (int i=3; i<row; i++){
            for (int j=3; j<col; j++){
                if (array[i][j] == user && array[i-1][j-1] == user && array[i-2][j-2] == user && array[i-3][j-3] == user)
                    return true;
            }
        }
        return false;
    }

    public void winnerScreen(int user, String mode){
        Stage stage = new Stage();
        BorderPane winScene = new BorderPane();
        winScene.getStylesheets().add( "styles/modeStyle.css" );
        winScene.getStyleClass().add( "mainBack" );

        Text header = new Text( "" );
        header.getStyleClass().add( "title" );


        HBox title = new HBox( 10 );
        title.getChildren().add( header );
        title.getStyleClass().add( "background" );

        winScene.setTop( title );

        DropShadow shadow = new DropShadow();
        shadow.setOffsetY( 5 );
        shadow.setColor( Color.BLACK );

        if(user == 1){
            Text winner = new Text( "RED WINS" );
            winner.getStyleClass().add( "playerOneWin" );
            winner.setEffect( shadow );

            Button exitGame = new Button( "EXIT" );
            exitGame.getStyleClass().add( "buttons" );
            //ACTION FOR EXIT
            exitGame.setOnAction( event -> {
                System.exit( 0 );
            });

            Button playAgain = new Button( "PLAY AGAIN" );
            playAgain.getStyleClass().add( "buttons" );

            //ACTION FOR CLOSEWINDOW
            playAgain.setOnAction( event -> {
                if(mode.equalsIgnoreCase( "2player" )){
                    connectBoard reset = new connectBoard( "2player" );
                    stage.close();
                }
                else{
                    connectBoard reset = new connectBoard( "1player" );
                    stage.close();
                }
            });

            HBox buttons = new HBox( 10 );
            buttons.setAlignment( Pos.CENTER );
            buttons.getChildren().addAll( exitGame,playAgain );

            VBox vbox = new VBox( 25 );
            vbox.getStyleClass().add( "mainBack" );
            vbox.setAlignment( Pos.CENTER );
            vbox.getChildren().addAll( winner,buttons );

            winScene.setCenter( vbox );

            Scene winnerScreen = new Scene( winScene,350,350 );
            stage.setScene( winnerScreen );
            stage.show();
            stage.show();
        }
        else{
            Text winner = new Text( "BLUE WINS" );
            winner.getStyleClass().add( "playerTwoWin" );
            winner.setEffect( shadow );

            Button exitGame = new Button( "EXIT" );
            exitGame.getStyleClass().add( "buttons" );
                //ACTION FOR EXIT
                exitGame.setOnAction( event -> {
                    System.exit( 0 );
                });

            Button playAgain = new Button( "PLAY AGAIN" );
                playAgain.getStyleClass().add( "buttons" );
                //ACTION FOR CLOSEWINDOW
                playAgain.setOnAction( event -> {
                    if(mode.equalsIgnoreCase( "2player" )){
                        connectBoard reset = new connectBoard( "2player" );
                        stage.close();
                    }
                    else{
                        connectBoard reset = new connectBoard( "1player" );
                        stage.close();
                    }
                });

            HBox buttons = new HBox( 10 );
            buttons.setAlignment( Pos.CENTER );
            buttons.getChildren().addAll( exitGame,playAgain );

            VBox vbox = new VBox( 25 );
            vbox.getStyleClass().add( "mainBack" );
            vbox.setAlignment( Pos.CENTER );
            vbox.getChildren().addAll( winner,buttons );

            winScene.setCenter( vbox );
            Scene winnerScreen = new Scene( winScene,350,350 );
            stage.setScene( winnerScreen );
            stage.show();
        }
    }
}
