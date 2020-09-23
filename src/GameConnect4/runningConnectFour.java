package GameConnect4;

import javafx.application.Application;
import javafx.stage.Stage;

public class runningConnectFour extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        mainMenu game = new mainMenu();
    }

    public static void main(String[] args){
        launch( args );
    }
}
