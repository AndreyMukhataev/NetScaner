

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws InterruptedException, IOException {
        LinkedList<String> hostsAndOpenPorts = new LinkedList<>();
        FileHandler fh = new FileHandler();

        Scanner reader=new Scanner(System.in);

        System.out.println("Enter IP address:");
        String a = reader.next();
        System.out.println("---------------------------------------------------------------------");

//        String a = "192.168.1.1";
        String[] aArray = a.split("\\.");
        String netNumber = aArray[0] + "."+ aArray[1] + "."+ aArray[2] + ".";
        for(int i= 1; i<256; i++){
            // проверка открытый или закрытый хост
            String  host = netNumber + Integer.toString(i);
            if (!new PortScanWorker().sendPingRequest(host)) {
                hostsAndOpenPorts.add(host +" is unreachable") ;
            }
            else {

                ScanPortByHost m = new ScanPortByHost();
                List<Integer> openHosts =  m.scanHost(netNumber + Integer.toString(i));
                String hostAndOpenPorts = host + " is reachable" + fh.DELIMITER + "Open ports:";
                for (int el: openHosts){
                    hostAndOpenPorts = hostAndOpenPorts + Integer.toString(el) + ",";
                }
                //удаление последней запятой
                if (openHosts.size()!= 0) hostAndOpenPorts = hostAndOpenPorts.substring(0, hostAndOpenPorts.length()-1);
                hostsAndOpenPorts.add(hostAndOpenPorts);
            }

        }

        System.out.println("---------------------------------------------------------------------");
        System.out.println("Write data to file? (Y/N)");


        a=reader.next();
        //reader.close();
        if(a.equals("Y")) {
            //запись в файл
            fh.newWriteFile(hostsAndOpenPorts);
        }
    }
}
