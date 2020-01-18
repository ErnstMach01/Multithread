import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Assignment Name: Multi-Thread Application
 * Author: Ernst Mach
 * Date: April 29, 2018
 * Description: A simple multi-threaded application that employs the MVC design principle.
 * Application creates 3 bouncing balls each controlled by their own thread. Application also
 * creates 2 vertical sliders that can be changed to control the FPS of the screen and
 * movement speed of the ball, both of which are controlled by threads.
 */

class View extends JPanel
{
    private ArrayList<Model> balls;                                         // Array to hold list of balls
    private final Random colorGenerator = new Random();                     // Randomize ball's colours
    private ExecutorService threadExecutor;
    private Color ballColour;                                               // Stores colour of ball

    private Image dbImage;                                                  // Variable to print old image
    private Graphics dbg;                                                   // Variable to hold new image

    public static int FrameRate = 60;                                       // Set the initial frame rate of screen

    // Basic constructor
    public View() {

        balls = new ArrayList();                                            // Create array
        threadExecutor = Executors.newCachedThreadPool();
        for (int i = 0; i < 3; i++) {                                       // Creates 3 balls
            createBall(100 * i, 30 * i);
        }

        JFrame frame = new JFrame("Threads");                                           // Create JFrame

        JSlider framesPerSecond = new JSlider(JSlider.VERTICAL, 0, 120, FrameRate);     // Create a vertical slider
        framesPerSecond.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();                                // Change listener that changes the frame rate of screen
                if (!source.getValueIsAdjusting()) {
                    FrameRate  = source.getValue();
                }
            }
        });

        framesPerSecond.setMajorTickSpacing(10);                                        // Set each tick to be worth 10
        framesPerSecond.setPaintTicks(true);
        Hashtable labelTable = new Hashtable();
        labelTable.put( new Integer( 0 ), new JLabel("0 FPS") );                        // Label the max and min frame rate
        labelTable.put( new Integer( 120 ), new JLabel("120 FPS") );
        framesPerSecond.setLabelTable( labelTable );
        framesPerSecond.setPaintLabels(true);

        JSlider timer = new JSlider(JSlider.VERTICAL, 1, 1000, Model.FPS);              // Create a vertical slider
        timer.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();                                // Change listener that changes the rate at which the balls get drawn
                if (!source.getValueIsAdjusting()) {
                    Model.FPS = source.getValue();
                }
            }
        });

        timer.setMajorTickSpacing(10);                                                  // Set each tick to be worth 10
        timer.setPaintTicks(true);
        Hashtable timerLabelTable = new Hashtable();
        timerLabelTable.put( new Integer( 1 ), new JLabel("1 ms") );                    // Label the max and min time
        timerLabelTable.put( new Integer( 1000 ), new JLabel("1 s") );
        timer.setLabelTable( timerLabelTable );
        timer.setPaintLabels(true);

        // Basic window setup
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setResizable(false);

        //Add the panels to the frame
        frame.add(this, BorderLayout.CENTER );
        frame.add(framesPerSecond, BorderLayout.WEST );
        frame.add(timer, BorderLayout.EAST );
        frame.pack();
        frame.setVisible(true);

    }

    // Creates a ball
    public void createBall(int x, int y)
    {
        // Randomly generate a colour
        ballColour = new Color( colorGenerator.nextInt(255),colorGenerator.nextInt(255),colorGenerator.nextInt(255) );
        // Add new ball object to a LinkedList
        balls.add( new Model( ballColour, x, y) );
        // Executes new thread of created ball object
        threadExecutor.execute( balls.get(balls.size() - 1) );
    }

    // Continuously repaint screen
    public void moveBall()
    {
        repaint();
    }

    // Sets the default JPanel size when pack() is called
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(640, 480);
    }

    // Draw the balls
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        for( Model ball : balls)
        {
            g2d.setPaint( ball.getBallColor() );
            g2d.fillOval((int) ball.getBallCoords().getX(), (int) ball.getBallCoords().getY(), (int) ball.getBallDimension().getWidth(), (int) ball.getBallDimension().getHeight() );
        }
    }

    //Image buffering to avoid flickering
    public void paint(Graphics g) {
        dbImage = createImage(getWidth(), getHeight());
        dbg = dbImage.getGraphics();
        paintComponent(dbg);
        g.drawImage(dbImage, 0, 0, this);
    }
}