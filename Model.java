import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Model {

    Cell[][] universe;
    private final int row = 10;
    private final int col = 10;
    private int refresh_speed = 1;
    private Boolean debug = false;

    public Model() {
        universe = new Cell[col][row];
        Random rand = new Random();

        for (int i = 0; i < universe.length; i++) {
            for (int j = 0; j < universe.length; j++) {
                universe[i][j] = new Cell(rand.nextBoolean());
            }
        }

        if (debug) {
            showUniverse(universe);
            System.out.println("");
            System.out.println("Number of neighbours:");
            System.out.println("");
            int k = 0;
            for (int i = 0; i < universe.length; i++) {
                for (int j = 0; j < universe.length; j++) {
                    k++;
                    System.out.print("[" + countNeighbours(i, j) + "]");
                }
                System.out.println("");
            }
   System.out.println("----------------------------");
        }

        //universe = updateUniverse();

        if (debug) {
            showUniverse(universe);

            System.out.println("");
            System.out.println("Number of neighbours:");
            System.out.println("");
            int k = 0;
            for (int i = 0; i < universe.length; i++) {
                for (int j = 0; j < universe.length; j++) {
                    k++;
                    System.out.print("[" + countNeighbours(i, j) + "]");
                }
                System.out.println("");
            }
   System.out.println("----------------------------");
        }
        int i = 0;
        while(i < 50){
            universe = updateUniverse();
          //  showUniverse(universe);
            System.out.println("Stage: " + i ) ;
            System.out.println("\033[H\033[2J");
            i++;
        }

    }

    // Update universe
    public Cell[][] updateUniverse() {
        Cell[][] nextDay = new Cell[universe.length][universe.length];

        for (int i = 0; i < universe.length; i++) {
            for (int j = 0; j < universe.length; j++) {
                nextDay[i][j] = new Cell();
            }
        }


        try {
            TimeUnit.SECONDS.sleep(refresh_speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //TODO: This isn't done yet!
        for (int i = 0; i < universe.length; i++) {
            for (int j = 0; j < universe.length; j++) {
                if (countNeighbours(i, j) < 2) nextDay[i][j].kill(); // kill the cell.
                else if (countNeighbours(i, j) == 2 || countNeighbours(i, j) == 3) ; // the cell should stay alive
                else if (countNeighbours(i, j) > 3) nextDay[i][j].kill(); // die due to overpopulation.
                else if (countNeighbours(i, j) == 3 && !universe[i][j].isAlive)
                    nextDay[i][j].resurect(); // if the dead cell has 3 live neighbours, then resurect it.
            }
        }


        return nextDay;
    }

    public int countNeighbours(int x, int y) {
        int sum = 0;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) {
                    //do nothing, since it will be the current cell
                } else {
                    if ((x + i >= 0 && x + i < col) && (y + j >= 0 && y + j < row)) {
                        if (universe[x + i][y + j].isAlive()) {
                            sum++;
                        }
                    }
                }
            }
        }
        return sum;
    }

    // Prints the Universe to the terminal
    public void showUniverse(Cell[][] cells) {

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                if (cells[i][j].isAlive) {
                    System.out.print("[x]");
                } else {
                    System.out.print("[ ]");
                }
            }
            System.out.println("");
        }
    }

    public void setRefresh_speed(int refresh_speed) {
        this.refresh_speed = refresh_speed;
    }


    private class Cell {

        Boolean isAlive;

        public Cell(Boolean isAlive) {
            this.isAlive = isAlive;
        }

        public Cell() {
            this.isAlive = true;
        }

        public Boolean isAlive() {
            return isAlive;
        }

        public void kill() {
            isAlive = false;
        }

        public void resurect() {
            isAlive = true;
        }
    }
}
