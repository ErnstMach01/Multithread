import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Assignment Name: Multi-Thread Application
 * Author: Ernst Mach
 * Date: April 29, 2018
 * Description: A simple multi-threaded application that employs the MVC design principle.
 * Application creates 3 bouncing balls each controlled by their own thread. Application also
 * creates 2 vertical sliders that can be changed to control the FPS of the screen and
 * movement speed of the ball, both of which are controlled by threads.
 */

public class Model implements Runnable
{
    private Ellipse2D.Double ball;                                                          // Ball objects shape attributes
    private Dimension ballDimension;                                                        // Stores size of a Ball
    private Point2D.Double ballCoords;                                                      // Stores location of Ball
    private Point2D.Double ballVelocity;                                                    // Stores x and y velocity of Ball
    private final Color colourOfBall;                                                       // Stores randomized color of ball
    private static final Point2D.Double xBoundaries = new Point2D.Double(0, 590);           // Horizontal boundaries of a ball
    private static final Point2D.Double yBoundaries = new Point2D.Double(0, 440);           // Vertical boundaries of a ball
    private final Random initialDirection = new Random();                                   // Randomize ball's initial left|right direction
    public static int FPS = 200;                                                            // Time for movement timer

    // Basic constructor
    public Model(Color ballColour, int xPos, int yPos)
    {
        ballCoords = new Point2D.Double(xPos, yPos);                                        // Set position of ball
        ballDimension = new Dimension(50, 50);                                              // Set size of ball

        if( (initialDirection.nextInt(2) % 2 == 0) ) {                                      // Set the initial velocity of the ball
            ballVelocity = new Point2D.Double(-1, 1);
        }
        else {
            ballVelocity = new Point2D.Double(1, 1);
        }

        colourOfBall = ballColour;                                                          // Set the color of the ball
        setBallObject( ballCoords, ballDimension);                                          // Draw the ball
    }

    // Update ball position
    @Override
    public void run()
    {
        while(true)
        {
            updateBallPosition();                                                           // Update ball position

            try
            {
                Thread.sleep(FPS);                                                          // Wait a certain amount of time before moving again
            }
            catch(Exception e){}
        }
    }

    // Detect x|y boundary collisions and update Ball velocity
    public void updateBallPosition()
    {
        // If ball hasn't reached window max width, add 1 to x
        if( ballVelocity.getX() > 0 )
        {
            if( (ballCoords.getX() + 1) < xBoundaries.getY() )
                ballCoords.setLocation( ballCoords.getX() + ballVelocity.getX() , ballCoords.getY());
                // Else ball's x has reached max, reverse direction
            else
            {
                ballVelocity.setLocation( -1, ballVelocity.getY());
                ballCoords.setLocation( ballCoords.getX() + ballVelocity.getX() , ballCoords.getY());
            }
        }
        // If ball hasn't reached window min width, minus 1 from x
        else if( ballVelocity.getX() < 0 )
        {
            if( (ballCoords.getX() - 1) > xBoundaries.getX() )
                ballCoords.setLocation( ballCoords.getX() + ballVelocity.getX() , ballCoords.getY());
                // Else ball's x has reached min, reverse direction
            else
            {
                ballVelocity.setLocation( 1, ballVelocity.getY());
                ballCoords.setLocation( ballCoords.getX() + ballVelocity.getX() , ballCoords.getY());
            }
        }

        // If ball hasn't reached window max height, add 1 to y
        if( ballVelocity.getY() > 0 )
        {
            if( (ballCoords.getY() + 1) < yBoundaries.getY() )
                ballCoords.setLocation( ballCoords.getX(), ballCoords.getY() + ballVelocity.getY());
                // Else ball's y has reached max, reverse direction
            else
            {
                ballVelocity.setLocation( ballVelocity.getX(), -1);
                ballCoords.setLocation( ballCoords.getX(), ballCoords.getY() + ballVelocity.getY());
            }
        }
        // If ball hasn't reached window min height, minus 1 from y
        else if( ballVelocity.getY() < 0 )
        {
            if( (ballCoords.getY() - 1) > yBoundaries.getX() )
                ballCoords.setLocation( ballCoords.getX(), ballCoords.getY() + ballVelocity.getY());
                // Else ball's y has reached min, reverse direction
            else
            {
                ballVelocity.setLocation( ballVelocity.getX(), 1);
                ballCoords.setLocation( ballCoords.getX(), ballCoords.getY() + ballVelocity.getY());
            }
        }
        setBallObject(ballCoords); // Set new coordinates of ball

    }

    // Set initial ball object
    private void setBallObject(Point2D loc, Dimension size)
    {
        ball = new Ellipse2D.Double( loc.getX(), loc.getY(), size.getWidth(), size.getHeight());
    }

    // Set new ball values
    private void setBallObject(Point2D loc)
    {
        ball.setFrame(loc, ballDimension);
    }

    // Return coordinates of ball object
    public Point2D getBallCoords()
    {
        return ballCoords;
    }

    // Return color of ball object
    public Color getBallColor()
    {
        return colourOfBall;
    }

    // Return dimensions of ball
    public Dimension getBallDimension()
    {
        return ballDimension;
    }

}
