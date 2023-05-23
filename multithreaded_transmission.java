package com.itheima;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class multithreaded_transmission extends JFrame implements ActionListener{
    private static final long serialVersionUID = 1L;



    public File file;

    JTextField textField = null;
    String filePath = null;
    public JTextField Address = new JTextField(20);
    public JTextField Port = new JTextField(20);

    private static String address = null;
    private static int port = 0;

    public  Socket socket = null;

    public OutputStream outputStream_filename;

    OutputStream outputStream = null;

    public ByteArrayOutputStream byteArrayOutputStream;

    public  FileInputStream fileInputStream = null;

    public  int PortNumber[] = {9990,9991,9992,9993,9994,9995,9996,9997};

    public String ipAddress =null;

    public static void main(String[] args) {
        new multithreaded_transmission();
    }


    public multithreaded_transmission()
    {
        JPanel jp1 = new JPanel();
        JLabel jl1 = new JLabel("IP地址");
        jp1.setLayout(new FlowLayout());
        jp1.add(jl1);
        jp1.add(Address);

        JPanel jp2 = new JPanel();
        JLabel jl2 = new JLabel("端口号");
        jp2.setLayout(new FlowLayout());
        jp2.add(jl2);
        jp2.add(Port);

        JButton ok = new JButton("连接");
        JButton quit = new JButton("取消");
        JPanel jp3 = new JPanel();
        jp3.setLayout(new FlowLayout());
        jp3.add(ok);
        jp3.add(quit);

        ok.addActionListener(this); // 添加事件监听器
        quit.addActionListener(this);
        JLabel label = new JLabel("请选择文件：");
        textField = new JTextField(30);
        JButton browseButton = new JButton("浏览");
        JButton confirmButton = new JButton("发送");

        browseButton.addActionListener(this);
        confirmButton.addActionListener(this);



        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(textField);
        panel.add(browseButton);
        panel.add(confirmButton);

        JPanel jp4 = new JPanel();
        //在此尝试创建进度条
        final JProgressBar jProgressBar = new JProgressBar(0,100);
        JLabel fileSendLable = new JLabel("文件传输进度 :");
        jProgressBar.setStringPainted(true);
        jp4.add(fileSendLable);
        jp4.add(jProgressBar);


        this.add(panel);
        this.setVisible(true);
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.add(jp4,BorderLayout.SOUTH);
        this.setAlwaysOnTop(true);
        this.pack();

        this.setTitle("选择文件窗口");
        this.setLayout(new FlowLayout());
        this.setBounds(0, 0, 800, 300);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("浏览")) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("D:\\web learn"));

            // 创建文件过滤器

            FileNameExtensionFilter video_file = new FileNameExtensionFilter("图片文件", "jpg","jpeg","png");
            chooser.setFileFilter(video_file);

            FileNameExtensionFilter mp3_file = new FileNameExtensionFilter("音视频文件", "mp3","mp4");
            chooser.setFileFilter(mp3_file);

            FileNameExtensionFilter filter = new FileNameExtensionFilter("文本文件", "txt","doc","docx");
            chooser.setFileFilter(filter);

            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            chooser.showDialog(new JLabel(), "选择文件");
            file = chooser.getSelectedFile();
            textField.setText(file.getAbsoluteFile().toString());

        }
        else if (e.getActionCommand().equals("发送")) {

            //为了同步读卡住后面的执行
            byte[] buffer_laji = new byte[1024];

            filePath = file.getAbsolutePath() ;
            System.out.println("文件大小为: "+file.length());
            Long fileLength_1 = file.length();
            System.out.println("Long的文件长度 " +fileLength_1);
            BigInteger fileLengthBigInt = BigInteger.valueOf(file.length());
            System.out.println("BigInteger内容为 ： " + fileLengthBigInt);
            file_path_read();
            System.out.println(filePath);
            try {
                outputStream_filename = socket.getOutputStream();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                outputStream = socket.getOutputStream();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            String file_path = filePath;
            while(file_path == null)
            {
                file_path = file_path_read();
            }
            System.out.println("文件路径" + file_path);
            String fileName = file_path.substring(file_path.lastIndexOf("\\")+1) + "\n";
            try {
                outputStream_filename.write(fileName.getBytes());
                outputStream_filename.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            int len_laji =0;
            try {
                InputStream fileInputStream_laji = socket.getInputStream();
                while ((len_laji = fileInputStream_laji.read(buffer_laji)) != -1)
                {
                    String sts = new String(buffer_laji,0,len_laji);
                    if(sts.equals("hjy"))
                    {
                        System.out.println("收到文件名的确认");
                        break;
                    }
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            System.out.println(outputStream_filename.toString());
            try {
                outputStream_filename.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            String fileLength  =fileLengthBigInt.toString() +"\n";
            System.out.println("转换后: "+fileLength);
            try {
                outputStream_filename.write(fileLength.getBytes());
                socket.setTcpNoDelay(true);
                outputStream_filename.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                InputStream fileInputStream_laji = socket.getInputStream();
                while ((len_laji = fileInputStream_laji.read(buffer_laji)) != -1)
                {
                    String sts = new String(buffer_laji,0,len_laji);
                    if(sts.equals("hjy"))
                    {
                        System.out.println("收到文件长度的确认");
                        break;
                    }
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            System.out.println(fileName);
            System.out.println(file_path);
            //发送了文件名和长度，现在准备进行多线程传输.

            //发送方计算自己的文件长度，判断准备几个线程发送
            BigInteger tenMb = new BigInteger("10485760");
            BigInteger threentyMb = new BigInteger("31457280");
            System.out.println("开始判断线程个数");
            int SocketNumberChoose = 0;

            if(fileLengthBigInt.compareTo(tenMb) <0)
            {
                System.out.println("文件长度小于10Mb，选择两个线程");
                SocketNumberChoose =2;
            }
            else if(fileLengthBigInt.compareTo(threentyMb) >0)
            {
                System.out.println("文件长度大于30MBb,选择8个线程");
                SocketNumberChoose = 8;
            }
            else
            {
                System.out.println("文件长度介于10-30Mb，选择四个线程");
                SocketNumberChoose = 4;
            }

            //发切片给服务器，C++那边不会算要切几片
            try {
                String strNumber = Integer.toString(SocketNumberChoose)+"\n";
                outputStream.write(strNumber.getBytes());
                outputStream.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            //发送方要等待接收方返回信息表示接收方已经创建好了

            try {
                InputStream fileInputStream_laji = socket.getInputStream();
                while ((len_laji = fileInputStream_laji.read(buffer_laji)) != -1)
                {
                    String sts = new String(buffer_laji,0,len_laji);
                    if(sts.equals("hjy"))
                    {
                        System.out.println("收到接收方线程建立成功的确认");
                        break;
                    }
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            //根据切片的数量，计算每个切片的大小

            BigInteger[] dividedResult = fileLengthBigInt.divideAndRemainder(BigInteger.valueOf(SocketNumberChoose));
            BigInteger sendFileLength = dividedResult[0]; // 商部分
            BigInteger remainder = dividedResult[1]; // 余数部分

            // 检查余数部分，如果不为0，则需要进行调整
            if (!remainder.equals(BigInteger.ZERO)) {
                sendFileLength = sendFileLength.add(BigInteger.ONE);
            } else {
                sendFileLength = dividedResult[0];
            }

            System.out.println("切片的大小"+sendFileLength.toString());


            //开始创建线程，数量为SocketNumberChoose

            List<Thread> threads = new ArrayList<Thread>();

            for (int i = 0; i < SocketNumberChoose; i++) {
                final int threadNumber = i + 1; // 线程编号，从1开始

                String finalFile_path = file_path;
                BigInteger finalSendFileLength = sendFileLength;
                Thread thread = new Thread(() -> {
                    try {
                        // 在每个线程中执行相应的操作
                        System.out.println("开始创建第" + threadNumber+ "线程");

                        int port = PortNumber[threadNumber - 1]; // 获取对应的端口号

                        // 创建Socket对象并连接到指定的IP地址和端口号
//                        Socket socket = new Socket(ipAddress, port);



                        Inet6Address address = (Inet6Address) Inet6Address.getByName(ipAddress);
                        InetSocketAddress socketAddress = new InetSocketAddress(address, port);
                        Socket socketChild = new Socket();

                        try {
                            socketChild.connect(socketAddress);
                            System.out.println(threadNumber +"连接创建成功");
                        }catch (IOException ex)
                        {
                            ex.printStackTrace();
                        }




                        // 在这里添加线程的具体逻辑

                        BigInteger sendFileBegin = finalSendFileLength.multiply(new BigInteger(String.valueOf(threadNumber-1)));
                        OutputStream outputStreamChild = null;
                        outputStreamChild = socketChild.getOutputStream();
                        String fileLengthChild  =sendFileBegin.toString() +"\n";
                        outputStreamChild.write(fileLengthChild.getBytes());
                        outputStreamChild.flush();
                        System.out.println(threadNumber+ "线程"+"起始位置"+sendFileBegin.toString());

                        //已经发送了读取下标给接收方，等待接收方回复

                        InputStream fileInputStream_laji = socketChild.getInputStream();

                        int nothing = 0;

                        while ((nothing = fileInputStream_laji.read(buffer_laji)) != -1)
                        {
                            String sts = new String(buffer_laji,0,nothing);
                            if(sts.equals("hjy"))
                            {
                                System.out.println("收到接收方回传，线程"+threadNumber+"开始准备进行文件传输");
                                break;
                            }
                        }


                        fileSendTo(threadNumber, finalFile_path, finalSendFileLength,sendFileBegin,outputStreamChild);

                        socketChild.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                thread.start(); // 启动线程
            }



            // 等待所有线程执行完成
            for (Thread thread : threads) {
                try {
                    thread.join(); // 等待线程执行完成
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }



            System.out.println("文件发送完毕");


        }

        else if (e.getActionCommand().equals("连接")) {
            //注释代码为IPV4创建连接
//            if (Address.getText().isEmpty() || Port.getText().isEmpty()) {
//                System.out.println("IP地址或端口号不能为空");
//            }
//            else {
//                System.out.println("Address: " + Address.getText());
//                System.out.println("Port: " + Port.getText());
//                address =  getAddress();
//                port = Integer.parseInt(getPort()) ;
//
//                try {
//                    socket = new Socket(address, port);
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
////                Receive receive =new Receive();
////                receive.start();
//                System.out.println("连接已经建立");
//            }

            //使用IPV6
            if (Address.getText().isEmpty() || Port.getText().isEmpty()) {
                System.out.println("IP地址或端口号不能为空");
            } else {
                System.out.println("Address: " + Address.getText());
                System.out.println("Port: " + Port.getText());

                ipAddress = getAddress(); // 获取IP地址
                int port = Integer.parseInt(getPort());

                try {
                    Inet6Address address = (Inet6Address) Inet6Address.getByName(ipAddress);
                    InetSocketAddress socketAddress = new InetSocketAddress(address, port);
                    socket = new Socket();
                    socket.connect(socketAddress);

                    System.out.println("连接已经建立");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }


        }
        else  if (e.getActionCommand().equals("取消")){
            this.dispose();
            if(socket != null)
            {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    outputStream_filename.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    fileInputStream.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }


    }
    public String file_path_read() {
        return filePath;
    }

    public  String getAddress()
    {

        return Address.getText();
    }
    public  String getPort()
    {
        return Port.getText();
    }

//新的接收代码

    static class Receive extends Thread {
        public void run() {

            ServerSocket serverSocket = null;
            ExecutorService executorService = Executors.newFixedThreadPool(10); // 创建线程池
            try {
                serverSocket = new ServerSocket(9999);
                System.out.println("服务器已启动，等待客户端连接...");
//            Socket socket = serverSocket.accept(); // 监听客户端连接

                while (true) {
                    Socket socket = serverSocket.accept(); // 监听客户端连接
                    System.out.println("客户端已连接");

                    executorService.submit(() -> {
                        try {
                            // 获取文件名
                            InputStream fileNameInputStream = socket.getInputStream();
                            BufferedReader fileNameReader = new BufferedReader(new InputStreamReader(fileNameInputStream));
                            String fileName = fileNameReader.readLine();
                            System.out.println("接收到的文件名：" + fileName);

                            String fileLengString = fileNameReader.readLine();
                            System.out.println("String 中内容  " + fileLengString);
                            BigInteger fileLength = new BigInteger(fileLengString);
                            System.out.println("接收到的文件长度：" + fileLength);

                            // 存储文件
                            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                            System.out.println("????");
                            InputStream fileInputStream = socket.getInputStream();
                            byte[] buffer = new byte[1024];
                            long totalBytesReceived = 0;
                            BigInteger totoalLength = new BigInteger("0");
                            int len;
                            while (totoalLength.compareTo(fileLength) < 0 && (len = fileInputStream.read(buffer)) != -1) {
                                System.out.println(totoalLength.toString());
                                fileOutputStream.write(buffer, 0, len);
                                totoalLength = BigInteger.valueOf(len).add(totoalLength);
                            }
                            System.out.println("检测是否出循环");
                            System.out.println(totoalLength.toString());
                            // 关闭资源
                            fileOutputStream.close();
                            fileInputStream.close();
                            fileNameReader.close();
                            socket.close();
                            System.out.println("文件已接收并存储");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });


                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public void fileSendTo(int socketNumber, String file_path, BigInteger sendFileLength, BigInteger sendFileBegin, OutputStream outputStreamChild) throws IOException {
        File file = new File(file_path);
        FileInputStream bis = new FileInputStream(file);

        System.out.println("进程" + socketNumber + "发送的长度: " + sendFileLength);

        long longValue = sendFileBegin.longValue();

        bis.skip(longValue); // 文件指针跳到指定的开始位置

        byte[] buffer = new byte[1024];
        int len = 0;
        BigInteger totalLength = new BigInteger("0");
        StringBuilder data = new StringBuilder(); // 用于保存发送的数据
        while (totalLength.compareTo(sendFileLength) < 0 && (len = bis.read(buffer)) != -1) {
            totalLength = BigInteger.valueOf(len).add(totalLength);

            // 判断剩余要发送的数据长度
            if (totalLength.compareTo(sendFileLength) > 0) {
                len -= totalLength.subtract(sendFileLength).intValue(); // 调整 len 的值
            }

            outputStreamChild.write(buffer, 0, len);

            String dataStr = new String(buffer, 0, len);
            data.append(dataStr);
        }

        System.out.println("进程" + socketNumber + "发送的数据: " + data.toString());
    }


}