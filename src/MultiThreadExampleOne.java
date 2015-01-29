
// -------------------------------------------------------------------------
/**
 *  A simple multi thread example to show that threads can be run concurrently
 *  and finish nonlinearly. Creates 3 threads which simulate a total running
 *  time of 17 seconds. We show that with multithreading we can complete all
 *  3 tasks in the time of the longest task.
 *
 *  @author Author: Jonathan Savage (jon5)
 *  @version Jan 28, 2015
 */
public class MultiThreadExampleOne
{
    //Each task time simulates a how long each thread will take in seconds.
    //Change these to experiment with the order of thread completion.
    static int taskOneTime = 5;
    static int taskTwoTime = 10;
    static int taskThreeTime = 2;


    // ----------------------------------------------------------
    /**
     * Where the magic happens.
     * @param args none
     * @throws InterruptedException ignore
     */
    public static void main(String args[]) throws InterruptedException {
        //We need a reference to ourself to add threads to. 'this' will not
        //work in a static method.
        MultiThreadExampleOne  obj = new MultiThreadExampleOne ();

        //Create and initialize 3 new threads with names and wait times.
        myThread thread1 = obj.new myThread("thread1", taskOneTime);
        myThread thread2 = obj.new myThread("thread2", taskTwoTime);
        myThread thread3 = obj.new myThread("thread3", taskThreeTime);

        //start the timer and each thread, note the order and total timer and
        //compare it with the console output

        long time = System.currentTimeMillis();
        thread1.start();
        thread2.start();
        thread3.start();

        //join() will halt this main thread until each respective thread
        //finishes. This will let us see how long all 3 threads combined
        //took to finish.
        thread1.join();
        thread2.join();
        thread3.join();

        //Here we know all threads are finished.

        //Calculate total running time in seconds.
        time = (System.currentTimeMillis() - time) / 1000;

        System.out.println("\nAll threads finshed in " + time + " seconds.");

        //Print a note
        System.out.println("\nSo we have completed " + (taskOneTime +
            taskTwoTime + taskThreeTime) + " seconds of work in " +
            time + " seconds total.");

    }

    /*
     * // -------------------------------------------------------------------------
    /**
     *  This is our overridden Thread class. Here we tell the Thread what to
     *  do in run(). In this case it will sleep() for a specified amount of
     *  time.
     *
     *  @author Author: Jonathan Savage
     *  @version Jan 28, 2015
     */
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
        public myThread(String name, int wait) {
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
            try {
                //Causes this thread to sleep for wait # of Milliseconds.
                Thread.sleep(wait);
            }
            //Ignore
            catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //After the thread is done waiting, print that it is finished.
            System.out.println(this.getName() + " finished.");
        }
    }
}
