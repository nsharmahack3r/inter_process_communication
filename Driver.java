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

 class Resource {
    int r1;
    int r2;

    public Resource(int r1, int r2) {
        this.r1 = r1;
        this.r2 = r2;
    }
}

class Process {
    Resource requiredResource = new Resource(50, 50);
    Resource availableResource;
    int processId;
    boolean isProcessRunning = false;

    public Process(Resource availableResource, int processId){
        this.availableResource = availableResource;
        this.processId = processId;
    }

     synchronized public void runProcess(){
        if(areResourcesAvailable()){
            startProcess();
            stopProcess();
            return;
        }
        else{
            try{
                System.out.println("Process " + processId + " Waiting.");
                wait();
            }catch (Exception e){
                System.out.println("Failed to execute wait().");
            }
        }
        startProcess();
        stopProcess();
    }

     synchronized private void updateAvailableResources(){
        if(isProcessRunning){
            availableResource.r1 =  availableResource.r1 - requiredResource.r1;
            availableResource.r2 =  availableResource.r2 - requiredResource.r2;
        }else{
            availableResource.r1 =  availableResource.r1 + requiredResource.r1;
            availableResource.r2 =  availableResource.r2 + requiredResource.r2;
        }
    }

     synchronized private void startProcess(){
        isProcessRunning = true;
        updateAvailableResources();
        System.out.println("Process " + processId + " running.");
        showResourceStatus();
    }
     synchronized private void stopProcess(){
        isProcessRunning = false;
        updateAvailableResources();
        System.out.println("Process " + processId + " finished.");
        showResourceStatus();

        notifyAll();
    }

     synchronized private boolean areResourcesAvailable(){
        return (availableResource.r1>= requiredResource.r1) && (availableResource.r2>= requiredResource.r2);
    }

    // A simple function to test Delay.
    private void delay() {
        int ms = 1000;
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

     synchronized private void showResourceStatus(){
        System.out.println("Resource Status");
        System.out.println( "R1 "+ availableResource.r1 + " " + "R2 "+ availableResource.r2);
    }
}
