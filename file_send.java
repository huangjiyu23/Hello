
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

import java.io.IOException;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class file_send extends JFrame implements ActionListener{
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

    public static void main(String[] args) {
        new file_send();
    }


    public  file_send()
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
        this.setBounds(400, 200, 800, 300);
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
//                Scanner in = new Scanner(System.in);
//                //从键盘读入文件地址，后续修正为图形界面
//                System.out.println("请输入你的文件地址");
//                String file_path = in.nextLine();
            String fileName = file_path.substring(file_path.lastIndexOf("\\")+1) + "\n";
//            String fileName = file_path.substring(file_path.lastIndexOf("\\") + 1);

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
                    System.out.println(sts);
                    if(sts.equals("hjy"))
                    {
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

//            String fileLengthString = fileLength.toString() + "\n";
//            System.out.println("转换后的长度显示"+fileLengthString);
//            try {
//                outputStream_filename.write(fileLengthString.getBytes());
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//
//            try {
//                outputStream_filename.flush();
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }

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
                    System.out.println(sts);
                    if(sts.equals("hjy"))
                    {
                        break;
                    }
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }


//            try {
//                outputStream_filename.flush();
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }

            System.out.println(fileName);
            System.out.println(file_path);
            //读取文件
            fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file_path);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            byte[] buffer = new byte[1024];
            int len;


            while (true) {
                try {
                    if (!((len = fileInputStream.read(buffer)) != -1)) break;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    outputStream.write(buffer, 0, len);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
//            //防止阻塞,
//            try {
//                socket.shutdownOutput();
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//
//            byteArrayOutputStream = new ByteArrayOutputStream();
//            InputStream inputStream = null;
//            try {
//                inputStream = socket.getInputStream();
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//            int len2;
//            byte[] buffer2 = new byte[1024];
//            while (true) {
//                try {
//                    if (!((len2 = inputStream.read(buffer2)) != -1)) break;
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
//                byteArrayOutputStream.write(buffer2, 0, len2);
//            }
//            System.out.println(byteArrayOutputStream.toString());

//            try {
//                socket = new Socket(address, port);
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//            System.out.println("连接已经建立");

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

                String ipAddress = getAddress(); // 获取IP地址
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

//为注释掉之前的接收代码，改用新的接收代码
//    class Receive extends Thread {
//        public void run(){
//
//            InputStream fileNameInputStream = null;
//            try {
//                fileNameInputStream = socket.getInputStream();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            BufferedReader fileNameReader = new BufferedReader(new InputStreamReader(fileNameInputStream));
//            String fileName = null;
//            try {
//                fileName = fileNameReader.readLine();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("接收到的文件名：" + fileName);
//
//            // 存储文件
//            FileOutputStream fileOutputStream = null;
//            try {
//                fileOutputStream = new FileOutputStream(fileName);
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//            InputStream fileInputStream = null;
//            try {
//                fileInputStream = socket.getInputStream();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while (true) {
//                try {
//                    if (!((bytesRead = fileInputStream.read(buffer)) != -1)) break;
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                try {
//                    fileOutputStream.write(buffer, 0, bytesRead);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//            // 告知客户端文件接收完毕
//            OutputStream outputStream = null;
//            try {
//                outputStream = socket.getOutputStream();
//            } catch (IOException e) {
//                //throw new RuntimeException(e);
//            }
//            try {
//                outputStream.write("File received".getBytes());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//
//        }
//    }
//
//}


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

}
