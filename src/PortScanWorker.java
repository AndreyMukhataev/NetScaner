
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;



public class PortScanWorker implements Runnable {

    static int globalId = 1;

    private static int id;
    private List<Integer> ports;
    private List<Integer> openPorts;
    private InetAddress inetAddress;
    private int timeout = 200;

    public PortScanWorker() {
        id = globalId++;
    }

    public static int getId() {
        return id;
    }


    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public List<Integer> getOpenPorts() {
        return openPorts;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public void setPorts(List<Integer> ports) {
        this.ports = ports;
    }

    @Override
    public void run() {
        openPorts = new ArrayList<Integer>();
        for (Integer port : ports) {
            try {
                InetSocketAddress isa = new InetSocketAddress(inetAddress,port);
                Socket socket = new Socket();
                socket.connect(isa,timeout);
                System.out.println("Found opened port: " + inetAddress.getHostName()+ ":" + port);
                openPorts.add(port);
                socket.close();
            } catch (IOException ioe) {}
        }
    }



    public Boolean sendPingRequest(String ipAddress)
            throws UnknownHostException, IOException
    {
        InetAddress geek = InetAddress.getByName(ipAddress);
        System.out.println("Sending Ping Request to " + ipAddress);
        if (geek.isReachable(timeout)) {
            System.out.println("Host is reachable");
            return true;
        }
        else{
            System.out.println("Host is unreachable");
            return false;
        }
    }

}
