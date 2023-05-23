


import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {


    public static OutputStream outputStream = null;

    public static int[] PortNumber = {9990,9991,9992,9993,9994,9995,9996,9997};//端口限定

    public static String ipAddress = "2408:8445:b5f0:7ce5:165a:3caa:289:fc6f";


    public static void main(String[] args) throws IOException{
        Receive receive = new Receive();
        receive.start();
//        file_send fileSend =new file_send();
        multithreaded_transmission multithreadedTransmission =new multithreaded_transmission();

    }
//使用IPV4
//    static class Receive extends Thread {
//        public void run() {
//
//            ServerSocket serverSocket = null;
//            ExecutorService executorService = Executors.newFixedThreadPool(10); // 创建线程池
//            try {
//                serverSocket = new ServerSocket(9999);
//                System.out.println("服务器已启动，等待客户端连接...");
////            Socket socket = serverSocket.accept(); // 监听客户端连接
//
//                while (true) {
//                    Socket socket = serverSocket.accept(); // 监听客户端连接
//                    System.out.println("客户端已连接");
//
//                    executorService.submit(() -> {
//                        try {
//                            // 获取文件名
//                            InputStream fileNameInputStream = socket.getInputStream();
//                            BufferedReader fileNameReader = new BufferedReader(new InputStreamReader(fileNameInputStream));
//                            String fileName = fileNameReader.readLine();
//                            System.out.println("接收到的文件名：" + fileName);
//
//                            String fileLengString = fileNameReader.readLine();
//                            System.out.println("String 中内容  " + fileLengString);
//                            BigInteger fileLength = new BigInteger(fileLengString);
//                            System.out.println("接收到的文件长度：" + fileLength);
//
//                            // 存储文件
//                            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
//                            System.out.println("????");
//                            InputStream fileInputStream = socket.getInputStream();
//                            byte[] buffer = new byte[1024];
//                            long totalBytesReceived = 0;
//                            BigInteger totoalLength = new BigInteger("0");
//                            int len;
//                            while (totoalLength.compareTo(fileLength) < 0 && (len = fileInputStream.read(buffer)) != -1) {
//                                System.out.println(totoalLength.toString());
//                                fileOutputStream.write(buffer, 0, len);
//                                totoalLength = BigInteger.valueOf(len).add(totoalLength);
//                            }
//                            System.out.println("检测是否出循环");
//                            System.out.println(totoalLength.toString());
//                            // 关闭资源
//                            fileOutputStream.close();
//                            fileInputStream.close();
//                            fileNameReader.close();
//                            socket.close();
//                            System.out.println("文件已接收并存储");
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    });
//
//
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (serverSocket != null) {
//                    try {
//                        serverSocket.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
    //IPV6地址2408:8444:b580:4722:d72f:3eb9:e304:d567
    //下面代码采用IPV6传输
//static class Receive extends Thread {
//    public void run() {
//
//        ExecutorService executorService = Executors.newFixedThreadPool(10); // 创建线程池
//        InetSocketAddress serverAddress = null;
//        try {
//            serverAddress = new InetSocketAddress(Inet6Address.getByName("fe80::70df:da97:616b:bb67%4"), 9999);
//        } catch (UnknownHostException e) {
//            throw new RuntimeException(e);
//        }
//        ServerSocket serverSocket = null;
//        try {
//            serverSocket = new ServerSocket();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            serverSocket.bind(serverAddress);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            System.out.println("服务器已启动，等待客户端连接...");
//
//            while (true) {
//                Socket socket = serverSocket.accept(); // 监听客户端连接
//                System.out.println("客户端已连接");
//
//                executorService.submit(() -> {
//                    try {
//                        // 获取文件名
//                        InputStream fileNameInputStream = socket.getInputStream();
//                        BufferedReader fileNameReader = new BufferedReader(new InputStreamReader(fileNameInputStream));
//                        String fileName = fileNameReader.readLine();
//                        System.out.println("接收到的文件名：" + fileName);
//
//                        String fileLengString = fileNameReader.readLine();
//                        System.out.println("String 中内容  " + fileLengString);
//                        BigInteger fileLength = new BigInteger(fileLengString);
//                        System.out.println("接收到的文件长度：" + fileLength);
//
//                        // 存储文件
//                        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
//                        System.out.println("????");
//                        InputStream fileInputStream = socket.getInputStream();
//                        byte[] buffer = new byte[1024];
//                        long totalBytesReceived = 0;
//                        BigInteger totoalLength = new BigInteger("0");
//                        int len;
//                        while (totoalLength.compareTo(fileLength) < 0 && (len = fileInputStream.read(buffer)) != -1) {
//                            System.out.println(totoalLength.toString());
//                            fileOutputStream.write(buffer, 0, len);
//                            totoalLength = BigInteger.valueOf(len).add(totoalLength);
//                        }
//                        System.out.println("检测是否出循环");
//                        System.out.println(totoalLength.toString());
//                        // 关闭资源
//                        fileOutputStream.close();
//                        fileInputStream.close();
//                        fileNameReader.close();
//                        socket.close();
//                        System.out.println("文件已接收并存储");
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
//
//
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (serverSocket != null) {
//                try {
//                    serverSocket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//}

    //替换为使用多线程IPV6
static class Receive extends Thread {
    public void run() {

        ExecutorService executorService = Executors.newFixedThreadPool(10); // 创建线程池
//        InetSocketAddress serverAddress = new InetSocketAddress(Inet6Address.getByName("2408:8444:b580:4722:d72f:3eb9:e304:d567"), 9999);
//        ServerSocket serverSocket = new ServerSocket();
//        serverSocket.bind(serverAddress);
        InetSocketAddress serverAddress = null;

        try {
            serverAddress = new InetSocketAddress(Inet6Address.getByName("2408:8445:b5f0:7ce5:165a:3caa:289:fc6f"), 6667);

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            serverSocket.bind(serverAddress);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
//            serverSocket = new ServerSocket(9998);

            System.out.println("服务器已启动，等待客户端连接...");
//            Socket socket = serverSocket.accept(); // 监听客户端连接

            while (true) {
                Socket socket = serverSocket.accept(); // 监听客户端连接
                System.out.println("客户端已连接");
                outputStream = socket.getOutputStream();
                executorService.submit(()->{
                    try {
                        // 获取文件名
                        InputStream fileNameInputStream = socket.getInputStream();
                        BufferedReader fileNameReader = new BufferedReader(new InputStreamReader(fileNameInputStream));
                        String fileName = fileNameReader.readLine();
                        System.out.println("接收到的文件名：" + fileName);
                        outputStream.write("hjy".getBytes());
                        outputStream.flush();
                        //获取文件长度
                        String fileLengString = fileNameReader.readLine();
                        System.out.println("String 中内容  " +fileLengString);
                        BigInteger fileLength = new BigInteger(fileLengString);
                        System.out.println("接收到的文件长度：" + fileLength);
                        outputStream.write("hjy".getBytes());
                        outputStream.flush();

                        //根据获取到的信息，建立空文件
                        File file = new File(fileName);
                        file.createNewFile();

                        //设置空文件的长度
                        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                        randomAccessFile.setLength(fileLength.longValue());


                        fileLengString = fileNameReader.readLine();
//                        System.out.println("String 中线程数量  " +fileLengString);
//                        fileLength = new BigInteger(fileLengString);
//                        System.out.println("接收到的线程数量：" + fileLength);




                        //处理，需要的线程个数

                        BigInteger tenMb = new BigInteger("10485760");
                        BigInteger threentyMb = new BigInteger("31457280");
                        System.out.println("开始判断线程个数");
                        int SocketNumberChoose = 0;
                        //原先是计算，为了配合传输，进行接收需要线程数

                        if(fileLength.compareTo(tenMb) <0)
                        {
                            System.out.println("文件长度小于10Mb，选择两个线程");
                            SocketNumberChoose =2;
                        }
                        else if(fileLength.compareTo(threentyMb) >0)
                        {
                            System.out.println("文件长度大于30MBb,选择8个线程");
                            SocketNumberChoose = 8;
                        }
                        else
                        {
                            System.out.println("文件长度介于10-30Mb，选择四个线程");
                            SocketNumberChoose = 4;
                        }
//                        SocketNumberChoose =fileLength.intValue();
//                        System.out.println("需要的线程数为: "+SocketNumberChoose);
                        //开始创建多线程


                        List<Thread> threads = new ArrayList<>();

                        for (int i = 0; i < SocketNumberChoose; i++) {
                            final int threadNumber = i + 1; // 线程编号，从1开始

                            Thread thread = new Thread(() -> {
                                try {
                                    // 在每个线程中执行相应的操作
                                    System.out.println("开始创建第" + threadNumber + "线程");

                                    // 获取对应的端口号
                                    int port = PortNumber[threadNumber - 1];

                                    // 创建Socket对象并连接到指定的IP地址和端口号
//                                    Socket nestedThread = new Socket(ipAddress, port);


                                    InetSocketAddress serverChildAddress = null;
                                    serverChildAddress = new InetSocketAddress(Inet6Address.getByName("2408:8445:b5f0:7ce5:165a:3caa:289:fc6f"), port);

                                    ServerSocket serverChileSocket = null;
                                    serverChileSocket = new ServerSocket();
                                    serverChileSocket.bind(serverChildAddress);
                                    Socket socketChild = serverChileSocket.accept(); // 监听客户端连接
                                    System.out.println("线程"+threadNumber+"连接成功");

                                    InputStream fileChildInputStream = socketChild.getInputStream();
                                    BufferedReader fileChindReader = new BufferedReader(new InputStreamReader(fileChildInputStream));
                                    //获取文件长度
                                    String fileLengStringChind = fileChindReader.readLine();
                                    System.out.println("String 中内容  " +fileLengStringChind);
                                    BigInteger fileBegin = new BigInteger(fileLengStringChind);
                                    System.out.println("进程"+threadNumber+"接收到的文件长度：" + fileBegin);

                                    OutputStream outputStreamChild = socketChild.getOutputStream();

                                    outputStreamChild.write("hjy".getBytes());
                                    outputStreamChild.flush();




                                    fileSave(fileName, fileChildInputStream,fileBegin,threadNumber);

                                    serverChileSocket.close();

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            });

                            thread.start(); // 启动线程
                            threads.add(thread); // 将线程添加到线程列表中
                        }
                        //所有线程已经创建
                        //发送确认
                        outputStream.write("hjy".getBytes());
                        outputStream.flush();

                        // 等待所有线程执行完成
                        for (Thread thread : threads) {
                            try {
                                thread.join(); // 等待线程执行完成
                                randomAccessFile.close();

                                //
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }

                        System.out.println("文件接收完毕");


                        // 存储文件
                        // 关闭资源
                        fileNameReader.close();
                        socket.close();
                        System.out.println("文件已接收并存储");

                    }catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                });


            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

        //文件存储
        public static synchronized void fileSave(String fileName, InputStream fileInputStream, BigInteger fileBegin, int i) throws IOException {
            RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw");
            randomAccessFile.seek(fileBegin.longValue());
            System.out.println("进程"+i+"文件指针位置"+fileBegin.longValue());
            byte[] buffer = new byte[1024];
            int len;
            StringBuilder receivedData = new StringBuilder(); // 用于保存接收到的数据

            while ((len = fileInputStream.read(buffer)) != -1) {
                randomAccessFile.write(buffer, 0, len);
                String dataStr = new String(buffer, 0, len);
                receivedData.append(dataStr);
            }

            System.out.println("进程" + i + "接收到的数据: " + receivedData.toString());
            randomAccessFile.close();
            fileInputStream.close();
        }
    }

}
