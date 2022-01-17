package inter_process_communication;

public class Driver {
    public static void main(String[] args) {
        Resource availableResources = new Resource(80,80);

        Process p1 = new Process(availableResources,1);
        Process p2 = new Process(availableResources,2);
        Process p3 = new Process(availableResources,3);


        // Start Process 1
        new Thread(){
            public void run(){
                p1.runProcess();
            }
        }.start();

        delay();

        // Start Process 1
        new Thread(){
            public void run(){
                p2.runProcess();
            }
        }.start();

        delay();

        // Start Process 1
        new Thread(){
            public void run(){
                p3.runProcess();
            }
        }.start();

    }

    public static void delay() {
        int ms = 10;
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
}
