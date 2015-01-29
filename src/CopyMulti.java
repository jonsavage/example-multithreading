


// -------------------------------------------------------------------------
/**
 *  Tests a traditional 3way copy vs a multithreaded copy.
 *
 *  @author Author: Jonathan Savage (jon5)
 *  @version Jan 28, 2015
 */
public class CopyMulti
{
    static int arraySize = 100000000;

    static int[] a5 = new int[arraySize];
    static int[] a1 = new int[arraySize];
    static int[] b5 = new int[arraySize];
    static int[] b1 = new int[arraySize];
    static int[] c5 = new int[arraySize];
    static int[] c1 = new int[arraySize];
    static int[] d5 = new int[arraySize];
    static int[] d1 = new int[arraySize];

    static long time;

    // ----------------------------------------------------------
    /**
     * Main method. Here we will benchmark 2 3way copying techniques. First a
     * linear method and next multithreaded.
     * @param args no args
     * @throws InterruptedException not relevant
     */
    public static void main(String args[]) throws InterruptedException
    {
        //We need a reference to ourself to add threads to. 'this' will not
        //work in a static method.
        CopyMulti a = new CopyMulti();

        //We are benchmarking a traditional 3way array swaps with and without
        //multithreading. We want to swap the contents of each pair of arrays
        //and then swap them back into their original array.


        //First, Traditional linear 3 way copy
        System.out.println("traditional swapping of 4 pairs of arrays.");

        //Fill up arrays
        initArrays();

        //start the timer
        time = System.currentTimeMillis();

        //Swap all arrays
        swap(a1, a5);
        swap(b1, b5);
        swap(c1, c5);
        swap(d1, d5);

        //Swap back
        swap(a5, a1);
        swap(b5, b1);
        swap(c5, c1);
        swap(d5, d1);

        time = System.currentTimeMillis() - time;

        System.out.println("time to swap: " + time + " ms.");

        //Now lets split each 3 way copy over 4 threads (use 4 cores in my case)
        System.out.println("\nNow split the same operations over 4 threads.");
        System.out.println("Your JVM has access to " +
            Runtime.getRuntime().availableProcessors() + " cores.");

        //reset array contents
        initArrays();

        //Create 4 threads
        ThreeWayCopyThread thread1 = a.new ThreeWayCopyThread("thread1", a1, a5);
        ThreeWayCopyThread thread2 = a.new ThreeWayCopyThread("thread2", b1, b5);
        ThreeWayCopyThread thread3 = a.new ThreeWayCopyThread("thread3", c1, c5);
        ThreeWayCopyThread thread4 = a.new ThreeWayCopyThread("thread4", d1, d5);

        //start the timer
        time = System.currentTimeMillis();

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        //join() will cause this (main) thread to pause until each respective
        //thread finished.
        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();

        //At this point all threads are finished swapping

        //calculate running time
        time = System.currentTimeMillis() - time;


        //print stats
        System.out.println("\nTime to swap: " + time + " ms.");
    }


    // ----------------------------------------------------------
    /**
     * Fills up the 8 arrays with 1's or 5's
     */
    public static void initArrays() {
        for (int i=0; i< a5.length; i++) {
            a5[i] = b5[i] = c5[i] = d5[i] = 5;
            a1[i] = b1[i] = c1[1] = d1[i] = 1;
          }
    }

    // ----------------------------------------------------------
    /**
     * Traditional 3 way swap, swaps the contents of param a and param b
     * @param a first array
     * @param b second array
     */
    public static void swap(int[] a, int[] b) {

        int swap;
        for (int i = 0; i < a.length; i++)
        {
            swap = a[i];
            a[i] = b[i];
            b[i] = swap;
        }
    }

    /*
     * // -------------------------------------------------------------------------
    /**
     *  Our thread object. Here we will define what each thread does. In this
     *  case it will swap the contents of arrays and swap them back again.
     *
     *  @author Author: Jonathan Savage (jon5)
     *  @version Jan 28, 2015
     */
    private class ThreeWayCopyThread extends Thread
    {
        int[] a;
        int[] b;

        /*
         * // ----------------------------------------------------------
        /**
         * Thread Constructor
         * @param name names the thread
         * @param a first array to swap
         * @param b second array to swap
         */
        public ThreeWayCopyThread(String name, int[] a, int[] b)
        {
            this.a = a;
            this.b = b;

            //sets this thread object's name in inherited method setName("name")
            setName(name);
        }

        /*
         * Called when <thread object>.start() is called.
         * This method MUST be overridden in order to subclass Thread
         */
        public void run() {

            //swap the numbers in the arrays
            swap(a, b);

            //sway them back again
            swap(b, a);

            System.out.println(this.getName() + " finished.");
        }

        // ----------------------------------------------------------
        /**
         * Traditional 3 way swap, swaps the contents of param a and param b
         * @param a first array
         * @param b second array
         */
        public void swap(int[] a, int[] b) {

            int swap;
            for (int i = 0; i < a.length; i++)
            {
                swap = a[i];
                a[i] = b[i];
                b[i] = swap;
            }
        }
    }
}
