
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


public class ScanPortByHost {

    private static final int MAX_THREADS = 256;

    private static InetAddress inetAddress;
    private static List<Integer> allPorts;


    public  List<Integer> scanHost(String host) throws InterruptedException, IOException {



        List<Integer> allOpenPorts = new ArrayList<Integer>();

        List<PortScanWorker> workers = new ArrayList<PortScanWorker>(MAX_THREADS);

        Thread[] threads = new Thread[MAX_THREADS+1];


        processArgs(host);

        final int PORTS_PER_THREAD = allPorts.size()/MAX_THREADS;

        List<Integer> threadPorts = new ArrayList<Integer>();
        for (int i=0,counter=0; i<allPorts.size();i++,counter++) {
            if (counter<PORTS_PER_THREAD) {
                threadPorts.add(allPorts.get(i));
            } else {
                PortScanWorker psw = new PortScanWorker();
                psw.setInetAddress(inetAddress);
                psw.setPorts(new ArrayList<Integer>(threadPorts));
                workers.add(psw);
                threadPorts.clear();
                counter=0;
            }
        }
        PortScanWorker psw = new PortScanWorker();
        psw.setInetAddress(inetAddress);
        psw.setPorts(new ArrayList<Integer>(threadPorts));
        workers.add(psw);

        System.out.println("Ports to scan: "+allPorts.size());
        System.out.println("Threads to work: "+workers.size());

        System.out.println("Start scanning...");

        for (int i=0 ;i< workers.size();i++ ) {
            PortScanWorker psw1 = workers.get(i);
            Thread thread= new Thread(psw1);
            threads[i] = thread;
            thread.start();

        }

        //ожидаем выполнение всех потоков
        for ( Thread t : threads ) {
            if (t!=null) t.join();
        }

        for (PortScanWorker psw2 : workers) {
            List<Integer> openPorts = psw2.getOpenPorts();
            allOpenPorts.addAll(openPorts);
        }
        return allOpenPorts;
    }

    static void processArgs(String host) {

        try {
            inetAddress = InetAddress.getByName(host);
        } catch (IOException ioe) {
            System.out.println("Error when resolving host!");
            System.exit(2);
        }

        System.out.println("Scanning host "+host);

        int minPort = 0;
        int maxPort = 0x10000-1;

        allPorts = new ArrayList<Integer>(maxPort-minPort+1);

        for (int i=minPort; i<=maxPort; i++) {
            allPorts.add(i);
        }
    }

}