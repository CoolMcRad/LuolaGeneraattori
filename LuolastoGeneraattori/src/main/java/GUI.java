
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;

public class GUI extends JFrame {

    int[] nls = {48, 72, 120, 168};
    int[] ncs = {2, 3, 4, 6};
    int[] cors = {2, 3, 4, 5};
    int[] nhk = {0, 2, 3};
    int[] ndia = {0, 1};
    boolean pause = false;

    int levelSize = 48;
    int pai = 19;
    Randomizer satun = new Randomizer();
    int cs;
    int cellSize = (levelSize / 4);
    int maxRooms = (levelSize / cellSize) * (levelSize / cellSize) + 1;
    int rooms = satun.randomBetween(2, maxRooms);
    int huoneKoko = cellSize / 2;
    int corridorSize = 2;
    Corridor c;
    boolean dia = false;
    String pmove = "";

    JButton b;
    JButton b2;
    JButton b3;
    Tile[][] floor;
    World world;
    World w2;
    Image image;
    Board board = new Board();

    public GUI(World w) {
        floor = w.getFloor();
        world = w;
        w2 = new World();
        this.setTitle("LuolastoGeneraattori");
        this.setSize(1700, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(floor.length, floor.length));
        this.setVisible(true);
        this.setResizable(false);
        this.setContentPane(board);
        this.addKeyListener(new AL());

        b = new JButton("Peli");
        b.setFocusable(false);
        b.addActionListener(actions);
        this.add(b);

        b2 = new JButton("Luola");
        b2.setFocusable(false);
        b2.addActionListener(actions);
        this.add(b2);

        b3 = new JButton("Ulkotila");
        b3.setFocusable(false);
        b3.addActionListener(actions);
        this.add(b3);

        JRadioButton maxLs = new JRadioButton("MAX kentän koko");
        maxLs.setMnemonic(KeyEvent.VK_B);
        maxLs.setActionCommand("168");
        maxLs.setFocusable(false);

        JRadioButton sLs = new JRadioButton("Suuri kentän koko");
        sLs.setMnemonic(KeyEvent.VK_C);
        sLs.setActionCommand("120");
        sLs.setFocusable(false);

        JRadioButton kLs = new JRadioButton("keski kentän koko");
        kLs.setMnemonic(KeyEvent.VK_D);
        kLs.setActionCommand("72");
        kLs.setFocusable(false);

        JRadioButton minLs = new JRadioButton("MIN kentän koko");
        minLs.setMnemonic(KeyEvent.VK_R);
        minLs.setActionCommand("48");
        minLs.setSelected(true);
        minLs.setFocusable(false);

        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(maxLs);
        group.add(sLs);
        group.add(kLs);
        group.add(minLs);

        maxLs.addActionListener(actions);
        sLs.addActionListener(actions);
        kLs.addActionListener(actions);
        minLs.addActionListener(actions);

        add(group);

        JRadioButton maxCs = new JRadioButton("MAX Solu koko");
        maxCs.setActionCommand("2");
        maxCs.setFocusable(false);

        JRadioButton sCs = new JRadioButton("Suuri solu koko");
        sCs.setActionCommand("3");
        sCs.setFocusable(false);

        JRadioButton kCs = new JRadioButton("keski solu koko");
        kCs.setActionCommand("4");
        kCs.setSelected(true);
        kCs.setFocusable(false);

        JRadioButton minCs = new JRadioButton("MIN solu koko");
        minCs.setFocusable(false);
        minCs.setActionCommand("6");

        //Group the radio buttons.
        ButtonGroup cellSizeg = new ButtonGroup();
        cellSizeg.add(maxCs);
        cellSizeg.add(sCs);
        cellSizeg.add(kCs);
        cellSizeg.add(minCs);

        maxCs.addActionListener(actions);
        sCs.addActionListener(actions);
        kCs.addActionListener(actions);
        minCs.addActionListener(actions);

        add(cellSizeg);

        JRadioButton maxKoko = new JRadioButton("MAX huone koko");
        maxKoko.setActionCommand("maxhk");
        maxKoko.setFocusable(false);

        JRadioButton keskiKoko = new JRadioButton("Keski huone koko");
        keskiKoko.setActionCommand("mhk");
        maxKoko.setSelected(true);
        keskiKoko.setFocusable(false);

        JRadioButton minKoko = new JRadioButton("MIN huone koko");
        minKoko.setActionCommand("minhk");
        minKoko.setFocusable(false);

        //Group the radio buttons.
        ButtonGroup hK = new ButtonGroup();
        hK.add(maxKoko);
        hK.add(keskiKoko);
        hK.add(minKoko);

        maxKoko.addActionListener(actions);
        keskiKoko.addActionListener(actions);
        minKoko.addActionListener(actions);

        add(hK);

        JRadioButton diaOn = new JRadioButton("Diagonaaliset tiet");
        diaOn.setActionCommand("t");
        diaOn.setFocusable(false);

        JRadioButton diaOff = new JRadioButton("Normaalit tiet");
        diaOff.setActionCommand("f");
        diaOff.setSelected(true);
        diaOff.setFocusable(false);

        //Group the radio buttons.
        ButtonGroup diako = new ButtonGroup();
        diako.add(diaOn);
        diako.add(diaOff);

        diaOn.addActionListener(actions);
        diaOff.addActionListener(actions);

        add(diako);

        JRadioButton corS = new JRadioButton("pienet tiet");
        corS.setActionCommand("s");
        corS.setSelected(true);
        corS.setFocusable(false);

        JRadioButton corM = new JRadioButton("Keskikoko tiet");
        corM.setActionCommand("m");
        corM.setFocusable(false);

        JRadioButton corL = new JRadioButton("Suuret tiet");
        corL.setActionCommand("l");
        corL.setFocusable(false);

        //Group the radio buttons.
        ButtonGroup tieKoko = new ButtonGroup();
        tieKoko.add(corS);
        tieKoko.add(corM);
        tieKoko.add(corL);

        corS.addActionListener(actions);
        corM.addActionListener(actions);
        corL.addActionListener(actions);

        add(tieKoko);

        int delay = 200;
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (!pause) {
                    world.enemyBehaviour();
                    setFloor(world.getFloor());
                }
            }
        };
        new Timer(delay, taskPerformer).start();

        int dela = 50;
        ActionListener playerPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (!pause) {
                    world.playerTestMove(pmove);
                    setFloor(world.getFloor());
                    pmove = "";
                }
            }
        };
        new Timer(dela, playerPerformer).start();

    }

    public ActionListener actions = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == b) {
                setFloor(world.getFloor());
                pause = false;
            } else if (e.getSource() == b2) {
                rooms = satun.randomBetween(2, maxRooms);
                setFloor(world.generateNewFloor(0, levelSize, cellSize, rooms, huoneKoko, dia, corridorSize).getStage().getTilesh());
                pause = true;
            } else if (e.getSource() == b3) {
                rooms = satun.randomBetween(2, maxRooms);
                setFloor(world.generateNewFloor(1, levelSize, cellSize, rooms, huoneKoko, dia, corridorSize).getStage().getTilesh());
                pause = true;
            } else if (e.getActionCommand().equals("168")) {
                pai = 5;
                levelSize = 168;
                cellSize = (levelSize / cs);
                maxRooms = (levelSize / cellSize) * (levelSize / cellSize) + 1;
            } else if (e.getActionCommand().equals("120")) {
                pai = 8;
                levelSize = 120;
                cellSize = (levelSize / cs);
                maxRooms = (levelSize / cellSize) * (levelSize / cellSize) + 1;
            } else if (e.getActionCommand().equals("72")) {
                pai = 13;
                levelSize = 72;
                cellSize = (levelSize / cs);
                maxRooms = (levelSize / cellSize) * (levelSize / cellSize) + 1;
            } else if (e.getActionCommand().equals("48")) {
                pai = 19;
                levelSize = 48;
                cellSize = (levelSize / cs);
                maxRooms = (levelSize / cellSize) * (levelSize / cellSize) + 1;
            } else if (e.getActionCommand().equals("2")) {
                cs = 2;
                cellSize = (levelSize / cs);
                maxRooms = (levelSize / cellSize) * (levelSize / cellSize) + 1;
            } else if (e.getActionCommand().equals("4")) {
                cs = 4;
                cellSize = (levelSize / cs);
                maxRooms = (levelSize / cellSize) * (levelSize / cellSize) + 1;
            } else if (e.getActionCommand().equals("3")) {
                cs = 3;
                cellSize = (levelSize / cs);
                maxRooms = (levelSize / cellSize) * (levelSize / cellSize) + 1;
            } else if (e.getActionCommand().equals("6")) {
                cs = 6;
                cellSize = (levelSize / cs);
                maxRooms = (levelSize / cellSize) * (levelSize / cellSize) + 1;
            } else if (e.getActionCommand().equals("minhk")) {
                huoneKoko = 4;
            } else if (e.getActionCommand().equals("maxhk")) {
                huoneKoko = cellSize;
            } else if (e.getActionCommand().equals("mhk")) {
                huoneKoko = cellSize / 2;
            } else if (e.getActionCommand().equals("t")) {
                dia = true;
            } else if (e.getActionCommand().equals("f")) {
                dia = false;
            } else if (e.getActionCommand().equals("s")) {
                corridorSize = 2;
            } else if (e.getActionCommand().equals("m")) {
                corridorSize = 3;
            } else if (e.getActionCommand().equals("l")) {
                corridorSize = 5;
            }
        }
    };

    private void add(ButtonGroup group) {
        for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            add(button);
        }
    }

    public class AL extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent event) {
            int keyCode = event.getKeyCode();
            if (keyCode == event.VK_W) {
                pmove = "w";
            }
            if (keyCode == event.VK_S) {
                pmove = "s";
            }
            if (keyCode == event.VK_A) {
                pmove = "a";
            }
            if (keyCode == event.VK_D) {
                pmove = "d";
            }
            if (keyCode == event.VK_F) {
                world.attack();
            }
            if (keyCode == event.VK_E) {
                world.doorEnter();
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
            g.setColor(Color.GRAY);
            g.fillRect(1100, 500, 500, 400);
            g.setColor(Color.YELLOW);
            g.drawString("Level: " + world.p.level, 1110, 520);
            g.drawString(world.p.exp + " / 100", 1110, 540);
            g.drawString("Health: " + world.p.health, 1110, 560);
            g.drawString("Floor: " + world.currentFloor, 1110, 580);
            if (world.p.dead) {
                g.setColor(Color.RED);
                g.drawString("Status: DEAD", 1110, 600);
            } else {
                g.setColor(Color.GREEN);
                g.drawString("Status: ALIVE", 1110, 600);
            }

//            g.setColor(Color.BLACK);
            for (int i = 0; i < floor.length; i++) {
                for (int j = 0; j < floor.length; j++) {
                    g.clearRect(i * pai, j * pai, pai, pai);
                    if (floor[i][j].hit == false & floor[i][j].toBeHit == false) {
                        image = floor[i][j].getImage();
                    }
                    if (floor[i][j].toBeHit) {
                        image = floor[i][j].target;
                    }
                    if (floor[i][j].hit) {
                        image = floor[i][j].impact;
                        floor[i][j].hit = false;
                        floor[i][j].toBeHit = false;
                    }
                    g.drawImage(image, i * pai, j * pai, pai, pai, rootPane);
                }
            }
        }
    }

    public void setFloor(Tile[][] floor) {
        this.floor = floor;
        this.board.updateUI();
    }

}
