

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class file_name_read extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;



    public File file;

    JTextField textField = null;
    String filePath = null;

    public file_name_read() {
        this.setTitle("选择文件窗口");
        this.setLayout(new FlowLayout());
        this.setBounds(400, 200, 600, 200);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("请选择文件：");
        textField = new JTextField(30);
        JButton browseButton = new JButton("浏览");
        JButton confirmButton = new JButton("确定");

        browseButton.addActionListener(this);
        confirmButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(textField);
        panel.add(browseButton);
        panel.add(confirmButton);

        this.add(panel);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new file_name_read();
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

        } else if (e.getActionCommand().equals("确定")) {
            filePath = file.getAbsolutePath();
            file_path_read();
            System.out.println(filePath);
            this.dispose();
        }
    }

    public String file_path_read() {
        return filePath;
    }
}
