/**
 * Assignment Name: Multi-Thread Application
 * Author: Ernst Mach
 * Date: April 29, 2018
 * Description: A simple multi-threaded application that employs the MVC design principle.
 * Application creates 3 bouncing balls each controlled by their own thread. Application also
 * creates 2 vertical sliders that can be changed to control the FPS of the screen and
 * movement speed of the ball, both of which are controlled by threads.
 */

public class Controller
{
    private View View1;
    public Controller (View View_) {
        this.View1 = View_;
        while(true)
        {
            //frame.repaint();                                   // Repaint background
            View_.moveBall();                                    // Move each Ball object
            try
            {
                Thread.sleep(1000/View.FrameRate);               //Calculate the frame rate per second and wait that amount of time
            }
            catch(Exception event){}
        }
    }
}