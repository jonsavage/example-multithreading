
// -------------------------------------------------------------------------
/**
 *  A simple multi thread example to show that threads can be run concurrently
 *  and finish nonlinearly.
 *
 *  @author Author: Jonathan Savage (jon5)
 *  @version Jan 28, 2015
 */
public class MultiThreadExampleOne
{

    // ----------------------------------------------------------
    /**
     * Where the magic happens.
     * @param args none
     */
    public static void main(String args[])
    {
        //We need a reference to ourself to add threads to. 'this' will not
        //work in a static method.
        MultiThreadExampleOne  obj = new MultiThreadExampleOne ();

        //Create and initialize 3 new threads with names and wait times.
        myThread thread1 = obj.new myThread("thread1", 5);
        myThread thread2 = obj.new myThread("thread2", 10);
        myThread thread3 = obj.new myThread("thread3", 2);

        //start each thread, note the order and compare it with the console
        //output
        thread1.start();
        thread2.start();
        thread3.start();
    }


    private class myThread extends Thread {

        //wait time in milliseconds
        private int wait;

        /*
         * // ----------------------------------------------------------
        /**
         * Create a new myThread object.
         * @param name name of thread
         * @param wait wait time in seconds
         */
        public myThread(String name, int wait)
        {
            //call to super to set the thread's name
            setName(name);

            //Sets the wait attribute to (wait * 1000) Milliseconds
            this.wait = wait * 1000;


            System.out.println("Thread: " + getName() +
                " created with wait time: " + wait + " seconds.");
        }

        //Called when we start() the thread.
        public void run() {
            System.out.println("start() called for " + this.getName() + ".");

            //ignore try/catch. It is here only because sleep() requires it.
            try
            {
                //Causes this thread to sleep for wait # of Milliseconds.
                Thread.sleep(wait);
            }
            //Ignore
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //After the thread is done waiting, print that it is finished.
            System.out.println(this.getName() + " finished.");
        }
    }
}
