package GameConnect4;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class mainMenu extends BorderPane {

    public mainMenu(){
        this.getStylesheets().add( "styles/modeStyle.css" );
        this.getStyleClass().add( "mainBack" );
        Stage stage = new Stage();
        //HEADER
        Text mode = new Text( "" );
        mode.getStyleClass().add( "title" );

        //BUTTONS
        Button singlePlayer = new Button("Single Player");
        singlePlayer.getStyleClass().add( "buttons" );
            singlePlayer.setOnMouseClicked( event -> {
                connectBoard game = new connectBoard( "1player" );
                stage.close();
            });

        Button twoPlayer = new Button("Two Players");
        twoPlayer.getStyleClass().add( "buttons" );
            twoPlayer.setOnMouseClicked( event -> {
                connectBoard game = new connectBoard("2player");
                stage.close();
            });

        Button exit = new Button("Exit");
        exit.setAlignment( Pos.CENTER );
        exit.getStyleClass().add( "exitButton" );
            exit.setOnMouseClicked( event -> {
                System.exit( 0 );
            });

        //BUTTON LAYOUT
        HBox buttonLayout = new HBox( 10 );
            buttonLayout.setAlignment( Pos.CENTER );
            buttonLayout.getChildren().addAll( singlePlayer,twoPlayer );

        HBox topPage = new HBox( 10 );
            topPage.getChildren().add( mode );
            topPage.getStyleClass().add( "background" );

        HBox bottomPage = new HBox( 10 );
            bottomPage.setAlignment( Pos.TOP_CENTER );
            bottomPage.getChildren().add( exit );

        this.setTop(  topPage   );
        this.setBottom( bottomPage );
        this.setCenter( buttonLayout );

        Scene scene = new Scene(this,450,350);

        //STAGE
        stage.setScene( scene );
        stage.show();

    }
}
