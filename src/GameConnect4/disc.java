package GameConnect4;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class disc extends Circle{
    private int col;
    private int row;


    @Override
    public String toString() {
        return "Disk: \n" +
                "Col: "+ col + "\n"+
                "Row: "+ row + "\n";
    }

    public disc(int row, int col) {
        this.col = col;
        this.row = row;
        this.setRadius( 30 );
        this.setFill( Color.BLACK );
    }
    public int getCol() {
        return col;
    }
    public int getRow() {
        return row;
    }

    public void setCol(int col) {
        this.col = col;
    }
    public void setRow(int row) {
        this.row = row;
    }
}
