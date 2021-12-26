package natalieoriya.example.exercise4;

import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Client implements Runnable{
    private int port;
    private String ip;
    private Socket socket;
    private PrintStream ps;
    private boolean isConnected;
    private static Lock mutex;
    public static Client instance = null;

    private Client() {
        this.port = 5402;
        this.ip = "10.0.2.2";
        this.isConnected=false;
        mutex=new ReentrantLock(true);
    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setIp(String ip){
        this.ip=ip;
    }



    public static Client getInstance() {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }


    public void send(final String value) {

        try {
            Thread t=new Thread(new Runnable() {
                @Override
                public void run() {
                    if(isConnected) {
                        mutex.lock();
                        ps.println(value);
                        ps.flush();
                        mutex.unlock();
                    }
                }
            });
            t.start();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void run() {
        mutex.lock();
        try {
            socket = new Socket(this.ip, this.port);
            ps = new PrintStream(socket.getOutputStream());
            isConnected=true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        mutex.unlock();
    }

    public void closeSocket(){
        try {
            socket.close();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

}
