package netty.ch2;

import java.io.IOException;
import java.net.Socket;

/**
 * @author 闪电侠
 *
 * 当Client程序需要从Server端获取信息及其他服务时，应创建一个Socket对象：
 * Socket MySocket=new Socket(“ServerComputerName”，600)；
 * Socket类的构造函数有两个参数，第一个参数是欲连接到的Server计算机的主机地址，第二个参数是该Server机上提供服务的端口号。
 * Socket对象建立成功之后，就可以在Client和Server之间建立一个连接，并通过这个连接在两个端点之间传递数据。利用Socket类的方法getOutputStream()和getInputStream()分别获得向Socket读写数据的输入／输出流，最后将从Server端读取的数据重新返还到Server端。
 * 当Server和Client端的通信结束时，可以调用Socket类的close()方法关闭Socket，拆除连接。
 * ServerSocket 一般仅用于设置端口号和监听，真正进行通信的是服务器端的Socket与客户端的Socket，在ServerSocket 进行accept之后，就将主动权转让了。
 */
public class Client {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;
    private static final int SLEEP_TIME = 5000;

    public static void main(String[] args) throws IOException {
        // 当Client程序需要从Server端获取信息及其他服务时，应创建一个Socket对象：
        final Socket socket = new Socket(HOST, PORT);

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("客户端启动成功!");
                while (true) {
                    try {
                        String message = "hello worlderer";
                        System.out.println("客户端发送数据: " + message);
                        socket.getOutputStream().write(message.getBytes());
                    } catch (Exception e) {
                        System.out.println("写数据出错!");
                    }
                    sleep();
                }


            }
        }).start();

    }

    private static void sleep() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
