
public class MultiThreadExampleOne
{

    public static void main(String args[])
    {
        MultiThreadExampleOne  obj = new MultiThreadExampleOne ();

        myThread thread1 = obj.new myThread("thread1", 5);
        myThread thread2 = obj.new myThread("thread2", 10);
        myThread thread3 = obj.new myThread("thread3", 2);

        thread1.start();
        thread2.start();
        thread3.start();

    }


    private class myThread extends Thread
    {
        private int wait;

        //Overriden Thread constructor
        public myThread(String name, int wait)
        {
            //call to super to set the thread's name
            setName(name);

            //Sets the wait attribute to wait * 1000 Milliseconds
            this.wait = wait * 1000;


            System.out.println("Thread: " + getName() + " created with wait time " + wait);
        }

        //Called when we start() the thread.
        public void run() {
            System.out.println("start() called for " + this.getName());

            //ignore try/catch. It is here only because sleep() requires it.
            try
            {
                //Causes this thread to sleep for wait Milliseconds.
                this.sleep(wait);
            }
            //Ingnore
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //After the thread is done waiting, print that it is finished.
            System.out.println(this.getName() + " finished");
        }
    }
}
