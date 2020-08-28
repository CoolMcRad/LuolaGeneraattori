
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GUI extends JFrame {
    Tile[][] floor;
    World world;
    Image image;
    Board board = new Board();
    
    public GUI(World w) {
        floor = w.getFloor();
        world = w;
        this.setTitle("LuolastoGeneraattori");
        this.setSize(1700,1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(floor.length,floor.length));
        this.setVisible(true);
        this.setResizable(false);
        this.setContentPane(board);
        addKeyListener(new AL());
//        String p = "p8.png";
//        ImageIcon ima = new ImageIcon(getClass().getResource(p));
//        Image imag = new ImageIcon(this.getClass().getResource(p)).getImage();
//        image = imag;
    }
    
        public class AL extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent event) {
            int keyCode = event.getKeyCode();
            if (keyCode == event.VK_W)
            {
                world.playerTestMove("w");
                setFloor(world.getFloor());
            }
            if (keyCode == event.VK_S)
            {
                world.playerTestMove("s");
                setFloor(world.getFloor());
            }
            if (keyCode == event.VK_A)
            {
                world.playerTestMove("a");
                setFloor(world.getFloor());
            }
            if (keyCode == event.VK_D)
            {
                world.playerTestMove("d");
                setFloor(world.getFloor());
            }
        }

        @Override
        public void keyReleased(KeyEvent event) {
        }
    }
    
    public class Board extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, 1700, 1000);
//            g.setColor(Color.BLACK);
            for (int i = 0; i < floor.length; i++) {
                for (int j = 0; j < floor.length; j++) {
                    g.clearRect(i*19, j*19, 19, 19);
                    image = floor[i][j].getImage();
                    g.drawImage(image, i*19, j*19, 19, 19, rootPane);
                }
            }
        }
    }

    public void setFloor(Tile[][] floor) {
        this.floor = floor;
        this.board.updateUI();
    }
    
}
