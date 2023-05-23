package com.itheima;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class IP_address extends JFrame implements ActionListener {

    public JTextField Address = new JTextField(20);
    public JTextField port = new JTextField(20);



//    public static void main(String[] args) {
//        new IP_address("23");
//    }

    public IP_address() {
        JPanel jp1 = new JPanel();
        JLabel jl1 = new JLabel("IP地址");
        jp1.setLayout(new FlowLayout());
        jp1.add(jl1);
        jp1.add(Address);

        JPanel jp2 = new JPanel();
        JLabel jl2 = new JLabel("端口号");
        jp2.setLayout(new FlowLayout());
        jp2.add(jl2);
        jp2.add(port);

        JButton ok = new JButton("确认");
        JButton quit = new JButton("取消");
        JPanel jp3 = new JPanel();
        jp3.setLayout(new FlowLayout());
        jp3.add(ok);
        jp3.add(quit);

        ok.addActionListener(this); // 添加事件监听器

        this.setLayout(new GridLayout(3, 1));
        this.setBounds(400, 200, 600, 200);
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.setAlwaysOnTop(true);
        this.setSize(500, 300);
        this.setVisible(true);
        this.setResizable(false);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         if (e.getActionCommand().equals("确认")) {
            if (Address.getText().isEmpty() || port.getText().isEmpty()) {
                System.out.println("IP地址或端口号不能为空");
            } else  {
                System.out.println("Address: " + Address.getText());
                System.out.println("Port: " + port.getText());
                getAddress();
                getPort();

            }
            this.dispose();
        }
    }

    public  String getAddress()
    {

        return Address.getText();
    }
    public  String getPort()
    {
        return port.getText();
    }
}
