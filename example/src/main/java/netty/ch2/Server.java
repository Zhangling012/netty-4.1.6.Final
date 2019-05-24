package netty.ch2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 创建一个ServerSocket类，同时在运行该语句的计算机的指定端口处建立一个监听服务，如：
 * ServerSocket MyListener=new ServerSocket(600)；
 * 这里指定提供监听服务的端口是600，一台计算机可以同时提供多个服务，这些不同的服务之间通过端口号来区别，不同的端口号上提供不同的服务。为了随时监听可能的Client请求，执行如下的语句：
 * Socket LinkSocket=MyListener．accept()；
 * 该语句调用了ServerSocket对象的accept()方法，这个方法的执行将使Server端的程序处于等待状态，程序将一直阻塞直到捕捉到一个来自Client端的请求，并返回一个用于与该Client通信的Socket对象Link-Socket。此后Server程序只要向这个Socket对象读写数据，就可以实现向远端的Client读写数据。结束监听时，关闭ServerSocket对象：
 * Mylistener．close()；
 */
public class Server {

    private ServerSocket serverSocket;

    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("服务端启动成功，端口:" + port);
        } catch (IOException exception) {
            System.out.println("服务端启动失败");
        }
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                doStart();
            }
        }).start();
    }

    private void doStart() {
        System.out.println("000");
        while (true) {
            try {
                System.out.println("111");
                Socket client = serverSocket.accept();
                System.out.println("222");
               new ClientHandler(client).start();
            } catch (IOException e) {
                System.out.println("服务端异常");
            }
        }
    }
}
