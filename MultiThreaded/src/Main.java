/**
 * Assignment Name: Multi-Thread Application
 * Author: Ernst Mach
 * Date: April 29, 2018
 * Description: A simple multi-threaded application that employs the MVC design principle.
 * Application creates 3 bouncing balls each controlled by their own thread. Application also
 * creates 2 vertical sliders that can be changed to control the FPS of the screen and
 * movement speed of the ball, both of which are controlled by threads.
 */

public class Main {
    public static void main(String[] args)
    {
        Controller Controller = new Controller(new View());
    }
}
