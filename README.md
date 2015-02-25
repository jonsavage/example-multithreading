# example-multithreading
Simple Multi Threading example

This is just a simple example of a multiThreaded Application. The first example is a proof of concept to show that we can fire off 2 threads and have them 
finish in not-the-same order. The seconf example shows that we can speed up a batch of stupid-long 3 way array copies by splitting 
logical tasks over seperate threads. 




This is my first multi-threaded application and I thought I'd post what I found. I generally like to learn by getting right into messing around with the code and I wasn't able to find a tutorial that met my needs. Hopefully someone can get some use out of this. <br />
<br />
So, what is multithreading?<br />
Multithreading, when implemented efficiently, is the splitting of logical tasks over separate threads and be run concurrently over separate cores (more on cores in a minute). Think of multithreading as a group of workers building a house. It would make sense for one guy to pave the driveway while another goes and gets lunch but it would not make sense for one to install the roof while another is still working on pouring the foundation. In a program it would make sense to assign a file download to a separate thread while continuing to render a GUI on another, so that your user interface does not completely freeze up while waiting for the file to download. Pretty cool stuff right?<br />
<br />
A note on cores:<br />
Each thread can be assigned to a different core by the System. The System makes this decision based on each core's CPU load. On my machine I have a quad core i7, which has 4 physical cores but due to hyperthreading 8 logical cores. This means that the JVM can potentially run * threads concurrently.<br />
<br />
<!-- HTML generated using hilite.me --><br />
<div style="background: #ffffff; border-width: .1em .1em .1em .8em; border: solid gray; overflow: auto; padding: .2em .6em; width: auto;">
<pre style="line-height: 125%; margin: 0;">Runtime<span style="color: #333333;">.</span><span style="color: #0000cc;">getRuntime</span><span style="color: #333333;">().</span><span style="color: #0000cc;">availableProcessors</span><span style="color: #333333;">()</span>
</pre>
</div>
<br />
will return the number of logical cores avaliable to the JVM. <br />
<br />
Here is an example which fires off 3 threads that finish in a different order than started. Each thread sleeps for a specified amount of time and then finishes. Picture the sleep time as a task that takes sleep time seconds to complete. As given we simulate 3 tasks that would take a total of 17 seconds to complete, but since we split each task into a separate thread all 3 are completed in the amount of time that it takes the longest one to finish, 10 seconds. Pretty cool right?<br />
<br />
<!-- HTML generated using hilite.me --><br />
<div style="background: #ffffff; border-width: .1em .1em .1em .8em; border: solid gray; overflow: auto; padding: .2em .6em; width: auto;">
<pre style="line-height: 125%; margin: 0;"><span style="color: #888888;">// -------------------------------------------------------------------------</span>
<span style="color: #888888;">/**</span>
<span style="color: #888888;"> *  A simple multi thread example to show that threads can be run concurrently</span>
<span style="color: #888888;"> *  and finish nonlinearly. Creates 3 threads which simulate a total running</span>
<span style="color: #888888;"> *  time of 17 seconds. We show that with multithreading we can complete all</span>
<span style="color: #888888;"> *  3 tasks in the time of the longest task.</span>
<span style="color: #888888;"> *</span>
<span style="color: #888888;"> *  @author Author: Jonathan Savage (jon5)</span>
<span style="color: #888888;"> *  @version Jan 28, 2015</span>
<span style="color: #888888;"> */</span>
<span style="color: #008800; font-weight: bold;">public</span> <span style="color: #008800; font-weight: bold;">class</span> <span style="color: #bb0066; font-weight: bold;">MultiThreadExampleOne</span>
<span style="color: #333333;">{</span>
    <span style="color: #888888;">//Each task time simulates a how long each thread will take in seconds.</span>
    <span style="color: #888888;">//Change these to experiment with the order of thread completion.</span>
    <span style="color: #008800; font-weight: bold;">static</span> <span style="color: #333399; font-weight: bold;">int</span> taskOneTime <span style="color: #333333;">=</span> <span style="color: #0000dd; font-weight: bold;">5</span><span style="color: #333333;">;</span>
    <span style="color: #008800; font-weight: bold;">static</span> <span style="color: #333399; font-weight: bold;">int</span> taskTwoTime <span style="color: #333333;">=</span> <span style="color: #0000dd; font-weight: bold;">10</span><span style="color: #333333;">;</span>
    <span style="color: #008800; font-weight: bold;">static</span> <span style="color: #333399; font-weight: bold;">int</span> taskThreeTime <span style="color: #333333;">=</span> <span style="color: #0000dd; font-weight: bold;">2</span><span style="color: #333333;">;</span>


    <span style="color: #888888;">// ----------------------------------------------------------</span>
    <span style="color: #888888;">/**</span>
<span style="color: #888888;">     * Where the magic happens.</span>
<span style="color: #888888;">     * @param args none</span>
<span style="color: #888888;">     * @throws InterruptedException ignore</span>
<span style="color: #888888;">     */</span>
    <span style="color: #008800; font-weight: bold;">public</span> <span style="color: #008800; font-weight: bold;">static</span> <span style="color: #333399; font-weight: bold;">void</span> <span style="color: #0066bb; font-weight: bold;">main</span><span style="color: #333333;">(</span>String args<span style="color: #333333;">[])</span> <span style="color: #008800; font-weight: bold;">throws</span> InterruptedException <span style="color: #333333;">{</span>
        <span style="color: #888888;">//We need a reference to ourself to add threads to. 'this' will not</span>
        <span style="color: #888888;">//work in a static method.</span>
        MultiThreadExampleOne  obj <span style="color: #333333;">=</span> <span style="color: #008800; font-weight: bold;">new</span> MultiThreadExampleOne <span style="color: #333333;">();</span>

        <span style="color: #888888;">//Create and initialize 3 new threads with names and wait times.</span>
        myThread thread1 <span style="color: #333333;">=</span> obj<span style="color: #333333;">.</span><span style="color: #0000cc;">new</span> myThread<span style="color: #333333;">(</span><span style="background-color: #fff0f0;">"thread1"</span><span style="color: #333333;">,</span> taskOneTime<span style="color: #333333;">);</span>
        myThread thread2 <span style="color: #333333;">=</span> obj<span style="color: #333333;">.</span><span style="color: #0000cc;">new</span> myThread<span style="color: #333333;">(</span><span style="background-color: #fff0f0;">"thread2"</span><span style="color: #333333;">,</span> taskTwoTime<span style="color: #333333;">);</span>
        myThread thread3 <span style="color: #333333;">=</span> obj<span style="color: #333333;">.</span><span style="color: #0000cc;">new</span> myThread<span style="color: #333333;">(</span><span style="background-color: #fff0f0;">"thread3"</span><span style="color: #333333;">,</span> taskThreeTime<span style="color: #333333;">);</span>

        <span style="color: #888888;">//start the timer and each thread, note the order and total timer and</span>
        <span style="color: #888888;">//compare it with the console output</span>

        <span style="color: #333399; font-weight: bold;">long</span> time <span style="color: #333333;">=</span> System<span style="color: #333333;">.</span><span style="color: #0000cc;">currentTimeMillis</span><span style="color: #333333;">();</span>
        thread1<span style="color: #333333;">.</span><span style="color: #0000cc;">start</span><span style="color: #333333;">();</span>
        thread2<span style="color: #333333;">.</span><span style="color: #0000cc;">start</span><span style="color: #333333;">();</span>
        thread3<span style="color: #333333;">.</span><span style="color: #0000cc;">start</span><span style="color: #333333;">();</span>

        <span style="color: #888888;">//join() will halt this main thread until each respective thread</span>
        <span style="color: #888888;">//finishes. This will let us see how long all 3 threads combined</span>
        <span style="color: #888888;">//took to finish.</span>
        thread1<span style="color: #333333;">.</span><span style="color: #0000cc;">join</span><span style="color: #333333;">();</span>
        thread2<span style="color: #333333;">.</span><span style="color: #0000cc;">join</span><span style="color: #333333;">();</span>
        thread3<span style="color: #333333;">.</span><span style="color: #0000cc;">join</span><span style="color: #333333;">();</span>

        <span style="color: #888888;">//Here we know all threads are finished.</span>

        <span style="color: #888888;">//Calculate total running time in seconds.</span>
        time <span style="color: #333333;">=</span> <span style="color: #333333;">(</span>System<span style="color: #333333;">.</span><span style="color: #0000cc;">currentTimeMillis</span><span style="color: #333333;">()</span> <span style="color: #333333;">-</span> time<span style="color: #333333;">)</span> <span style="color: #333333;">/</span> <span style="color: #0000dd; font-weight: bold;">1000</span><span style="color: #333333;">;</span>

        System<span style="color: #333333;">.</span><span style="color: #0000cc;">out</span><span style="color: #333333;">.</span><span style="color: #0000cc;">println</span><span style="color: #333333;">(</span><span style="background-color: #fff0f0;">"\nAll threads finshed in "</span> <span style="color: #333333;">+</span> time <span style="color: #333333;">+</span> <span style="background-color: #fff0f0;">" seconds."</span><span style="color: #333333;">);</span>

        <span style="color: #888888;">//Print a note</span>
        System<span style="color: #333333;">.</span><span style="color: #0000cc;">out</span><span style="color: #333333;">.</span><span style="color: #0000cc;">println</span><span style="color: #333333;">(</span><span style="background-color: #fff0f0;">"\nSo we have completed "</span> <span style="color: #333333;">+</span> <span style="color: #333333;">(</span>taskOneTime <span style="color: #333333;">+</span>
            taskTwoTime <span style="color: #333333;">+</span> taskThreeTime<span style="color: #333333;">)</span> <span style="color: #333333;">+</span> <span style="background-color: #fff0f0;">" seconds of work in "</span> <span style="color: #333333;">+</span>
            time <span style="color: #333333;">+</span> <span style="background-color: #fff0f0;">" seconds total."</span><span style="color: #333333;">);</span>

    <span style="color: #333333;">}</span>

    <span style="color: #888888;">/*</span>
<span style="color: #888888;">     * // -------------------------------------------------------------------------</span>
<span style="color: #888888;">    /**</span>
<span style="color: #888888;">     *  This is our overridden Thread class. Here we tell the Thread what to</span>
<span style="color: #888888;">     *  do in run(). In this case it will sleep() for a specified amount of</span>
<span style="color: #888888;">     *  time.</span>
<span style="color: #888888;">     *</span>
<span style="color: #888888;">     *  @author Author: Jonathan Savage</span>
<span style="color: #888888;">     *  @version Jan 28, 2015</span>
<span style="color: #888888;">     */</span>
    <span style="color: #008800; font-weight: bold;">private</span> <span style="color: #008800; font-weight: bold;">class</span> <span style="color: #bb0066; font-weight: bold;">myThread</span> <span style="color: #008800; font-weight: bold;">extends</span> Thread <span style="color: #333333;">{</span>

        <span style="color: #888888;">//wait time in milliseconds</span>
        <span style="color: #008800; font-weight: bold;">private</span> <span style="color: #333399; font-weight: bold;">int</span> wait<span style="color: #333333;">;</span>

        <span style="color: #888888;">/*</span>
<span style="color: #888888;">         * // ----------------------------------------------------------</span>
<span style="color: #888888;">        /**</span>
<span style="color: #888888;">         * Create a new myThread object.</span>
<span style="color: #888888;">         * @param name name of thread</span>
<span style="color: #888888;">         * @param wait wait time in seconds</span>
<span style="color: #888888;">         */</span>
        <span style="color: #008800; font-weight: bold;">public</span> <span style="color: #0066bb; font-weight: bold;">myThread</span><span style="color: #333333;">(</span>String name<span style="color: #333333;">,</span> <span style="color: #333399; font-weight: bold;">int</span> wait<span style="color: #333333;">)</span> <span style="color: #333333;">{</span>
            <span style="color: #888888;">//call to super to set the thread's name</span>
            setName<span style="color: #333333;">(</span>name<span style="color: #333333;">);</span>

            <span style="color: #888888;">//Sets the wait attribute to (wait * 1000) Milliseconds</span>
            <span style="color: #008800; font-weight: bold;">this</span><span style="color: #333333;">.</span><span style="color: #0000cc;">wait</span> <span style="color: #333333;">=</span> wait <span style="color: #333333;">*</span> <span style="color: #0000dd; font-weight: bold;">1000</span><span style="color: #333333;">;</span>


            System<span style="color: #333333;">.</span><span style="color: #0000cc;">out</span><span style="color: #333333;">.</span><span style="color: #0000cc;">println</span><span style="color: #333333;">(</span><span style="background-color: #fff0f0;">"Thread: "</span> <span style="color: #333333;">+</span> getName<span style="color: #333333;">()</span> <span style="color: #333333;">+</span>
                <span style="background-color: #fff0f0;">" created with wait time: "</span> <span style="color: #333333;">+</span> wait <span style="color: #333333;">+</span> <span style="background-color: #fff0f0;">" seconds."</span><span style="color: #333333;">);</span>
        <span style="color: #333333;">}</span>

        <span style="color: #888888;">//Called when we start() the thread.</span>
        <span style="color: #008800; font-weight: bold;">public</span> <span style="color: #333399; font-weight: bold;">void</span> <span style="color: #0066bb; font-weight: bold;">run</span><span style="color: #333333;">()</span> <span style="color: #333333;">{</span>
            System<span style="color: #333333;">.</span><span style="color: #0000cc;">out</span><span style="color: #333333;">.</span><span style="color: #0000cc;">println</span><span style="color: #333333;">(</span><span style="background-color: #fff0f0;">"start() called for "</span> <span style="color: #333333;">+</span> <span style="color: #008800; font-weight: bold;">this</span><span style="color: #333333;">.</span><span style="color: #0000cc;">getName</span><span style="color: #333333;">()</span> <span style="color: #333333;">+</span> <span style="background-color: #fff0f0;">"."</span><span style="color: #333333;">);</span>

            <span style="color: #888888;">//ignore try/catch. It is here only because sleep() requires it.</span>
            <span style="color: #008800; font-weight: bold;">try</span> <span style="color: #333333;">{</span>
                <span style="color: #888888;">//Causes this thread to sleep for wait # of Milliseconds.</span>
                Thread<span style="color: #333333;">.</span><span style="color: #0000cc;">sleep</span><span style="color: #333333;">(</span>wait<span style="color: #333333;">);</span>
            <span style="color: #333333;">}</span>
            <span style="color: #888888;">//Ignore</span>
            <span style="color: #008800; font-weight: bold;">catch</span> <span style="color: #333333;">(</span>InterruptedException e<span style="color: #333333;">)</span> <span style="color: #333333;">{</span>
                <span style="color: #888888;">// TODO Auto-generated catch block</span>
                e<span style="color: #333333;">.</span><span style="color: #0000cc;">printStackTrace</span><span style="color: #333333;">();</span>
            <span style="color: #333333;">}</span>

            <span style="color: #888888;">//After the thread is done waiting, print that it is finished.</span>
            System<span style="color: #333333;">.</span><span style="color: #0000cc;">out</span><span style="color: #333333;">.</span><span style="color: #0000cc;">println</span><span style="color: #333333;">(</span><span style="color: #008800; font-weight: bold;">this</span><span style="color: #333333;">.</span><span style="color: #0000cc;">getName</span><span style="color: #333333;">()</span> <span style="color: #333333;">+</span> <span style="background-color: #fff0f0;">" finished."</span><span style="color: #333333;">);</span>
        <span style="color: #333333;">}</span>
    <span style="color: #333333;">}</span>
<span style="color: #333333;">}</span>
</pre>
</div>
<br />
<br />
Here is an example of how to use multithreading (Concurrent Programming) to speed up a traditional 3 way array copy. The task is to swap the contents of 4 arrays with 4 other arrays and then back again. <b>Before you run</b> this make sure to bump up your JVM's allotment of memory since we are using some pretty large arrays. To do this add something like -Xmx8g to Run Configurations =&gt; CopyMulti =&gt; Arguments =&gt; VM Arguements. The 8 represents 8gb worth of memory. You could also adjust the arraySize attribute at the top of the CopyMulti class.<br />
<br />
<br />
<!-- HTML generated using hilite.me --><br />
<div style="background: #ffffff; border-width: .1em .1em .1em .8em; border: solid gray; overflow: auto; padding: .2em .6em; width: auto;">
<pre style="line-height: 125%; margin: 0;"><span style="color: #888888;">// -------------------------------------------------------------------------</span>
<span style="color: #888888;">/**</span>
<span style="color: #888888;"> *  Tests a traditional 3way copy vs a multithreaded copy.</span>
<span style="color: #888888;"> *</span>
<span style="color: #888888;"> *  @author Author: Jonathan Savage (jon5)</span>
<span style="color: #888888;"> *  @version Jan 28, 2015</span>
<span style="color: #888888;"> */</span>
<span style="color: #008800; font-weight: bold;">public</span> <span style="color: #008800; font-weight: bold;">class</span> <span style="color: #bb0066; font-weight: bold;">CopyMulti</span>
<span style="color: #333333;">{</span>
    <span style="color: #008800; font-weight: bold;">static</span> <span style="color: #333399; font-weight: bold;">int</span> arraySize <span style="color: #333333;">=</span> <span style="color: #0000dd; font-weight: bold;">100000000</span><span style="color: #333333;">;</span>

    <span style="color: #008800; font-weight: bold;">static</span> <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[]</span> a5 <span style="color: #333333;">=</span> <span style="color: #008800; font-weight: bold;">new</span> <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[</span>arraySize<span style="color: #333333;">];</span>
    <span style="color: #008800; font-weight: bold;">static</span> <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[]</span> a1 <span style="color: #333333;">=</span> <span style="color: #008800; font-weight: bold;">new</span> <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[</span>arraySize<span style="color: #333333;">];</span>
    <span style="color: #008800; font-weight: bold;">static</span> <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[]</span> b5 <span style="color: #333333;">=</span> <span style="color: #008800; font-weight: bold;">new</span> <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[</span>arraySize<span style="color: #333333;">];</span>
    <span style="color: #008800; font-weight: bold;">static</span> <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[]</span> b1 <span style="color: #333333;">=</span> <span style="color: #008800; font-weight: bold;">new</span> <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[</span>arraySize<span style="color: #333333;">];</span>
    <span style="color: #008800; font-weight: bold;">static</span> <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[]</span> c5 <span style="color: #333333;">=</span> <span style="color: #008800; font-weight: bold;">new</span> <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[</span>arraySize<span style="color: #333333;">];</span>
    <span style="color: #008800; font-weight: bold;">static</span> <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[]</span> c1 <span style="color: #333333;">=</span> <span style="color: #008800; font-weight: bold;">new</span> <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[</span>arraySize<span style="color: #333333;">];</span>
    <span style="color: #008800; font-weight: bold;">static</span> <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[]</span> d5 <span style="color: #333333;">=</span> <span style="color: #008800; font-weight: bold;">new</span> <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[</span>arraySize<span style="color: #333333;">];</span>
    <span style="color: #008800; font-weight: bold;">static</span> <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[]</span> d1 <span style="color: #333333;">=</span> <span style="color: #008800; font-weight: bold;">new</span> <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[</span>arraySize<span style="color: #333333;">];</span>

    <span style="color: #008800; font-weight: bold;">static</span> <span style="color: #333399; font-weight: bold;">long</span> time<span style="color: #333333;">;</span>

    <span style="color: #888888;">// ----------------------------------------------------------</span>
    <span style="color: #888888;">/**</span>
<span style="color: #888888;">     * Main method. Here we will benchmark 2 3way copying techniques. First a</span>
<span style="color: #888888;">     * linear method and next multithreaded.</span>
<span style="color: #888888;">     * @param args no args</span>
<span style="color: #888888;">     * @throws InterruptedException not relevant</span>
<span style="color: #888888;">     */</span>
    <span style="color: #008800; font-weight: bold;">public</span> <span style="color: #008800; font-weight: bold;">static</span> <span style="color: #333399; font-weight: bold;">void</span> <span style="color: #0066bb; font-weight: bold;">main</span><span style="color: #333333;">(</span>String args<span style="color: #333333;">[])</span> <span style="color: #008800; font-weight: bold;">throws</span> InterruptedException
    <span style="color: #333333;">{</span>
        <span style="color: #888888;">//We need a reference to ourself to add threads to. 'this' will not</span>
        <span style="color: #888888;">//work in a static method.</span>
        CopyMulti a <span style="color: #333333;">=</span> <span style="color: #008800; font-weight: bold;">new</span> CopyMulti<span style="color: #333333;">();</span>

        <span style="color: #888888;">//We are benchmarking a traditional 3way array swaps with and without</span>
        <span style="color: #888888;">//multithreading. We want to swap the contents of each pair of arrays</span>
        <span style="color: #888888;">//and then swap them back into their original array.</span>


        <span style="color: #888888;">//First, Traditional linear 3 way copy</span>
        System<span style="color: #333333;">.</span><span style="color: #0000cc;">out</span><span style="color: #333333;">.</span><span style="color: #0000cc;">println</span><span style="color: #333333;">(</span><span style="background-color: #fff0f0;">"traditional swapping of 4 pairs of arrays."</span><span style="color: #333333;">);</span>

        <span style="color: #888888;">//Fill up arrays</span>
        initArrays<span style="color: #333333;">();</span>

        <span style="color: #888888;">//start the timer</span>
        time <span style="color: #333333;">=</span> System<span style="color: #333333;">.</span><span style="color: #0000cc;">currentTimeMillis</span><span style="color: #333333;">();</span>

        <span style="color: #888888;">//Swap all arrays</span>
        swap<span style="color: #333333;">(</span>a1<span style="color: #333333;">,</span> a5<span style="color: #333333;">);</span>
        swap<span style="color: #333333;">(</span>b1<span style="color: #333333;">,</span> b5<span style="color: #333333;">);</span>
        swap<span style="color: #333333;">(</span>c1<span style="color: #333333;">,</span> c5<span style="color: #333333;">);</span>
        swap<span style="color: #333333;">(</span>d1<span style="color: #333333;">,</span> d5<span style="color: #333333;">);</span>

        <span style="color: #888888;">//Swap back</span>
        swap<span style="color: #333333;">(</span>a5<span style="color: #333333;">,</span> a1<span style="color: #333333;">);</span>
        swap<span style="color: #333333;">(</span>b5<span style="color: #333333;">,</span> b1<span style="color: #333333;">);</span>
        swap<span style="color: #333333;">(</span>c5<span style="color: #333333;">,</span> c1<span style="color: #333333;">);</span>
        swap<span style="color: #333333;">(</span>d5<span style="color: #333333;">,</span> d1<span style="color: #333333;">);</span>

        time <span style="color: #333333;">=</span> System<span style="color: #333333;">.</span><span style="color: #0000cc;">currentTimeMillis</span><span style="color: #333333;">()</span> <span style="color: #333333;">-</span> time<span style="color: #333333;">;</span>

        System<span style="color: #333333;">.</span><span style="color: #0000cc;">out</span><span style="color: #333333;">.</span><span style="color: #0000cc;">println</span><span style="color: #333333;">(</span><span style="background-color: #fff0f0;">"time to swap: "</span> <span style="color: #333333;">+</span> time <span style="color: #333333;">+</span> <span style="background-color: #fff0f0;">" ms."</span><span style="color: #333333;">);</span>

        <span style="color: #888888;">//Now lets split each 3 way copy over 4 threads (use 4 cores in my case)</span>
        System<span style="color: #333333;">.</span><span style="color: #0000cc;">out</span><span style="color: #333333;">.</span><span style="color: #0000cc;">println</span><span style="color: #333333;">(</span><span style="background-color: #fff0f0;">"\nNow split the same operations over 4 threads."</span><span style="color: #333333;">);</span>
        System<span style="color: #333333;">.</span><span style="color: #0000cc;">out</span><span style="color: #333333;">.</span><span style="color: #0000cc;">println</span><span style="color: #333333;">(</span><span style="background-color: #fff0f0;">"Your JVM has access to "</span> <span style="color: #333333;">+</span>
            Runtime<span style="color: #333333;">.</span><span style="color: #0000cc;">getRuntime</span><span style="color: #333333;">().</span><span style="color: #0000cc;">availableProcessors</span><span style="color: #333333;">()</span> <span style="color: #333333;">+</span> <span style="background-color: #fff0f0;">" cores."</span><span style="color: #333333;">);</span>

        <span style="color: #888888;">//reset array contents</span>
        initArrays<span style="color: #333333;">();</span>

        <span style="color: #888888;">//Create 4 threads</span>
        ThreeWayCopyThread thread1 <span style="color: #333333;">=</span> a<span style="color: #333333;">.</span><span style="color: #0000cc;">new</span> ThreeWayCopyThread<span style="color: #333333;">(</span><span style="background-color: #fff0f0;">"thread1"</span><span style="color: #333333;">,</span> a1<span style="color: #333333;">,</span> a5<span style="color: #333333;">);</span>
        ThreeWayCopyThread thread2 <span style="color: #333333;">=</span> a<span style="color: #333333;">.</span><span style="color: #0000cc;">new</span> ThreeWayCopyThread<span style="color: #333333;">(</span><span style="background-color: #fff0f0;">"thread2"</span><span style="color: #333333;">,</span> b1<span style="color: #333333;">,</span> b5<span style="color: #333333;">);</span>
        ThreeWayCopyThread thread3 <span style="color: #333333;">=</span> a<span style="color: #333333;">.</span><span style="color: #0000cc;">new</span> ThreeWayCopyThread<span style="color: #333333;">(</span><span style="background-color: #fff0f0;">"thread3"</span><span style="color: #333333;">,</span> c1<span style="color: #333333;">,</span> c5<span style="color: #333333;">);</span>
        ThreeWayCopyThread thread4 <span style="color: #333333;">=</span> a<span style="color: #333333;">.</span><span style="color: #0000cc;">new</span> ThreeWayCopyThread<span style="color: #333333;">(</span><span style="background-color: #fff0f0;">"thread4"</span><span style="color: #333333;">,</span> d1<span style="color: #333333;">,</span> d5<span style="color: #333333;">);</span>

        <span style="color: #888888;">//start the timer</span>
        time <span style="color: #333333;">=</span> System<span style="color: #333333;">.</span><span style="color: #0000cc;">currentTimeMillis</span><span style="color: #333333;">();</span>

        thread1<span style="color: #333333;">.</span><span style="color: #0000cc;">start</span><span style="color: #333333;">();</span>
        thread2<span style="color: #333333;">.</span><span style="color: #0000cc;">start</span><span style="color: #333333;">();</span>
        thread3<span style="color: #333333;">.</span><span style="color: #0000cc;">start</span><span style="color: #333333;">();</span>
        thread4<span style="color: #333333;">.</span><span style="color: #0000cc;">start</span><span style="color: #333333;">();</span>

        <span style="color: #888888;">//join() will cause this (main) thread to pause until each respective</span>
        <span style="color: #888888;">//thread finished.</span>
        thread1<span style="color: #333333;">.</span><span style="color: #0000cc;">join</span><span style="color: #333333;">();</span>
        thread2<span style="color: #333333;">.</span><span style="color: #0000cc;">join</span><span style="color: #333333;">();</span>
        thread3<span style="color: #333333;">.</span><span style="color: #0000cc;">join</span><span style="color: #333333;">();</span>
        thread4<span style="color: #333333;">.</span><span style="color: #0000cc;">join</span><span style="color: #333333;">();</span>

        <span style="color: #888888;">//At this point all threads are finished swapping</span>

        <span style="color: #888888;">//calculate running time</span>
        time <span style="color: #333333;">=</span> System<span style="color: #333333;">.</span><span style="color: #0000cc;">currentTimeMillis</span><span style="color: #333333;">()</span> <span style="color: #333333;">-</span> time<span style="color: #333333;">;</span>


        <span style="color: #888888;">//print stats</span>
        System<span style="color: #333333;">.</span><span style="color: #0000cc;">out</span><span style="color: #333333;">.</span><span style="color: #0000cc;">println</span><span style="color: #333333;">(</span><span style="background-color: #fff0f0;">"\nTime to swap: "</span> <span style="color: #333333;">+</span> time <span style="color: #333333;">+</span> <span style="background-color: #fff0f0;">" ms."</span><span style="color: #333333;">);</span>
    <span style="color: #333333;">}</span>


    <span style="color: #888888;">// ----------------------------------------------------------</span>
    <span style="color: #888888;">/**</span>
<span style="color: #888888;">     * Fills up the 8 arrays with 1's or 5's</span>
<span style="color: #888888;">     */</span>
    <span style="color: #008800; font-weight: bold;">public</span> <span style="color: #008800; font-weight: bold;">static</span> <span style="color: #333399; font-weight: bold;">void</span> <span style="color: #0066bb; font-weight: bold;">initArrays</span><span style="color: #333333;">()</span> <span style="color: #333333;">{</span>
        <span style="color: #008800; font-weight: bold;">for</span> <span style="color: #333333;">(</span><span style="color: #333399; font-weight: bold;">int</span> i<span style="color: #333333;">=</span><span style="color: #0000dd; font-weight: bold;">0</span><span style="color: #333333;">;</span> i<span style="color: #333333;">&lt;</span> a5<span style="color: #333333;">.</span><span style="color: #0000cc;">length</span><span style="color: #333333;">;</span> i<span style="color: #333333;">++)</span> <span style="color: #333333;">{</span>
            a5<span style="color: #333333;">[</span>i<span style="color: #333333;">]</span> <span style="color: #333333;">=</span> b5<span style="color: #333333;">[</span>i<span style="color: #333333;">]</span> <span style="color: #333333;">=</span> c5<span style="color: #333333;">[</span>i<span style="color: #333333;">]</span> <span style="color: #333333;">=</span> d5<span style="color: #333333;">[</span>i<span style="color: #333333;">]</span> <span style="color: #333333;">=</span> <span style="color: #0000dd; font-weight: bold;">5</span><span style="color: #333333;">;</span>
            a1<span style="color: #333333;">[</span>i<span style="color: #333333;">]</span> <span style="color: #333333;">=</span> b1<span style="color: #333333;">[</span>i<span style="color: #333333;">]</span> <span style="color: #333333;">=</span> c1<span style="color: #333333;">[</span><span style="color: #0000dd; font-weight: bold;">1</span><span style="color: #333333;">]</span> <span style="color: #333333;">=</span> d1<span style="color: #333333;">[</span>i<span style="color: #333333;">]</span> <span style="color: #333333;">=</span> <span style="color: #0000dd; font-weight: bold;">1</span><span style="color: #333333;">;</span>
          <span style="color: #333333;">}</span>
    <span style="color: #333333;">}</span>

    <span style="color: #888888;">// ----------------------------------------------------------</span>
    <span style="color: #888888;">/**</span>
<span style="color: #888888;">     * Traditional 3 way swap, swaps the contents of param a and param b</span>
<span style="color: #888888;">     * @param a first array</span>
<span style="color: #888888;">     * @param b second array</span>
<span style="color: #888888;">     */</span>
    <span style="color: #008800; font-weight: bold;">public</span> <span style="color: #008800; font-weight: bold;">static</span> <span style="color: #333399; font-weight: bold;">void</span> <span style="color: #0066bb; font-weight: bold;">swap</span><span style="color: #333333;">(</span><span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[]</span> a<span style="color: #333333;">,</span> <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[]</span> b<span style="color: #333333;">)</span> <span style="color: #333333;">{</span>

        <span style="color: #333399; font-weight: bold;">int</span> swap<span style="color: #333333;">;</span>
        <span style="color: #008800; font-weight: bold;">for</span> <span style="color: #333333;">(</span><span style="color: #333399; font-weight: bold;">int</span> i <span style="color: #333333;">=</span> <span style="color: #0000dd; font-weight: bold;">0</span><span style="color: #333333;">;</span> i <span style="color: #333333;">&lt;</span> a<span style="color: #333333;">.</span><span style="color: #0000cc;">length</span><span style="color: #333333;">;</span> i<span style="color: #333333;">++)</span>
        <span style="color: #333333;">{</span>
            swap <span style="color: #333333;">=</span> a<span style="color: #333333;">[</span>i<span style="color: #333333;">];</span>
            a<span style="color: #333333;">[</span>i<span style="color: #333333;">]</span> <span style="color: #333333;">=</span> b<span style="color: #333333;">[</span>i<span style="color: #333333;">];</span>
            b<span style="color: #333333;">[</span>i<span style="color: #333333;">]</span> <span style="color: #333333;">=</span> swap<span style="color: #333333;">;</span>
        <span style="color: #333333;">}</span>
    <span style="color: #333333;">}</span>

    <span style="color: #888888;">/*</span>
<span style="color: #888888;">     * // -------------------------------------------------------------------------</span>
<span style="color: #888888;">    /**</span>
<span style="color: #888888;">     *  Our thread object. Here we will define what each thread does. In this</span>
<span style="color: #888888;">     *  case it will swap the contents of arrays and swap them back again.</span>
<span style="color: #888888;">     *</span>
<span style="color: #888888;">     *  @author Author: Jonathan Savage (jon5)</span>
<span style="color: #888888;">     *  @version Jan 28, 2015</span>
<span style="color: #888888;">     */</span>
    <span style="color: #008800; font-weight: bold;">private</span> <span style="color: #008800; font-weight: bold;">class</span> <span style="color: #bb0066; font-weight: bold;">ThreeWayCopyThread</span> <span style="color: #008800; font-weight: bold;">extends</span> Thread
    <span style="color: #333333;">{</span>
        <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[]</span> a<span style="color: #333333;">;</span>
        <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[]</span> b<span style="color: #333333;">;</span>

        <span style="color: #888888;">/*</span>
<span style="color: #888888;">         * // ----------------------------------------------------------</span>
<span style="color: #888888;">        /**</span>
<span style="color: #888888;">         * Thread Constructor</span>
<span style="color: #888888;">         * @param name names the thread</span>
<span style="color: #888888;">         * @param a first array to swap</span>
<span style="color: #888888;">         * @param b second array to swap</span>
<span style="color: #888888;">         */</span>
        <span style="color: #008800; font-weight: bold;">public</span> <span style="color: #0066bb; font-weight: bold;">ThreeWayCopyThread</span><span style="color: #333333;">(</span>String name<span style="color: #333333;">,</span> <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[]</span> a<span style="color: #333333;">,</span> <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[]</span> b<span style="color: #333333;">)</span>
        <span style="color: #333333;">{</span>
            <span style="color: #008800; font-weight: bold;">this</span><span style="color: #333333;">.</span><span style="color: #0000cc;">a</span> <span style="color: #333333;">=</span> a<span style="color: #333333;">;</span>
            <span style="color: #008800; font-weight: bold;">this</span><span style="color: #333333;">.</span><span style="color: #0000cc;">b</span> <span style="color: #333333;">=</span> b<span style="color: #333333;">;</span>

            <span style="color: #888888;">//sets this thread object's name in inherited method setName("name")</span>
            setName<span style="color: #333333;">(</span>name<span style="color: #333333;">);</span>
        <span style="color: #333333;">}</span>

        <span style="color: #888888;">/*</span>
<span style="color: #888888;">         * Called when &lt;thread object&gt;.start() is called.</span>
<span style="color: #888888;">         * This method MUST be overridden in order to subclass Thread</span>
<span style="color: #888888;">         */</span>
        <span style="color: #008800; font-weight: bold;">public</span> <span style="color: #333399; font-weight: bold;">void</span> <span style="color: #0066bb; font-weight: bold;">run</span><span style="color: #333333;">()</span> <span style="color: #333333;">{</span>

            <span style="color: #888888;">//swap the numbers in the arrays</span>
            swap<span style="color: #333333;">(</span>a<span style="color: #333333;">,</span> b<span style="color: #333333;">);</span>

            <span style="color: #888888;">//sway them back again</span>
            swap<span style="color: #333333;">(</span>b<span style="color: #333333;">,</span> a<span style="color: #333333;">);</span>

            System<span style="color: #333333;">.</span><span style="color: #0000cc;">out</span><span style="color: #333333;">.</span><span style="color: #0000cc;">println</span><span style="color: #333333;">(</span><span style="color: #008800; font-weight: bold;">this</span><span style="color: #333333;">.</span><span style="color: #0000cc;">getName</span><span style="color: #333333;">()</span> <span style="color: #333333;">+</span> <span style="background-color: #fff0f0;">" finished."</span><span style="color: #333333;">);</span>
        <span style="color: #333333;">}</span>

        <span style="color: #888888;">// ----------------------------------------------------------</span>
        <span style="color: #888888;">/**</span>
<span style="color: #888888;">         * Traditional 3 way swap, swaps the contents of param a and param b</span>
<span style="color: #888888;">         * @param a first array</span>
<span style="color: #888888;">         * @param b second array</span>
<span style="color: #888888;">         */</span>
        <span style="color: #008800; font-weight: bold;">public</span> <span style="color: #333399; font-weight: bold;">void</span> <span style="color: #0066bb; font-weight: bold;">swap</span><span style="color: #333333;">(</span><span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[]</span> a<span style="color: #333333;">,</span> <span style="color: #333399; font-weight: bold;">int</span><span style="color: #333333;">[]</span> b<span style="color: #333333;">)</span> <span style="color: #333333;">{</span>

            <span style="color: #333399; font-weight: bold;">int</span> swap<span style="color: #333333;">;</span>
            <span style="color: #008800; font-weight: bold;">for</span> <span style="color: #333333;">(</span><span style="color: #333399; font-weight: bold;">int</span> i <span style="color: #333333;">=</span> <span style="color: #0000dd; font-weight: bold;">0</span><span style="color: #333333;">;</span> i <span style="color: #333333;">&lt;</span> a<span style="color: #333333;">.</span><span style="color: #0000cc;">length</span><span style="color: #333333;">;</span> i<span style="color: #333333;">++)</span>
            <span style="color: #333333;">{</span>
                swap <span style="color: #333333;">=</span> a<span style="color: #333333;">[</span>i<span style="color: #333333;">];</span>
                a<span style="color: #333333;">[</span>i<span style="color: #333333;">]</span> <span style="color: #333333;">=</span> b<span style="color: #333333;">[</span>i<span style="color: #333333;">];</span>
                b<span style="color: #333333;">[</span>i<span style="color: #333333;">]</span> <span style="color: #333333;">=</span> swap<span style="color: #333333;">;</span>
            <span style="color: #333333;">}</span>
        <span style="color: #333333;">}</span>
    <span style="color: #333333;">}</span>
<span style="color: #333333;">}</span>&nbsp;</pre>
<pre style="line-height: 125%; margin: 0;">&nbsp;</pre>
<pre style="line-height: 125%; margin: 0;">There you have it. Two pretty simple multithreading examples to get your break the ice.</pre>
<pre style="line-height: 125%; margin: 0;">&nbsp;</pre>
<pre style="line-height: 125%; margin: 0;">Link to GitHub repo. https://github.com/jsavage06/example-multithreading </pre>
<pre style="line-height: 125%; margin: 0;">&nbsp;</pre>
</div>
<br />
<br />
