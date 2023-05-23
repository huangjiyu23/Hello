package com.itheima;

import java.io.*;

public class test {

        public static void main(String[] args) {


                try{

                    File file = new File("D:\\web learn\\file_transfer\\hello.txt");

                    FileInputStream bis=new FileInputStream(file);



                    bis.skip(1);//文件指向前一字节


//指定文件位置读取的文件流
                    InputStream sbs = new BufferedInputStream(bis);

//存入文件，以便检测

                    File file1=new File("D:\\web learn\\file_transfer\\ss1.txt");

                    OutputStream os=null;

                    try

                    {

                        os=new FileOutputStream(file1);

                        byte buffer[]=new byte[4*1024];

                        int len = 0;

                        while((len = sbs.read(buffer)) != -1)//

                        {

                            os.write(buffer,0,len);

                        }

                        os.flush();

                    }catch(Exception e){

                        e.printStackTrace();

                    }finally{

                        try {

                            os.close();

                        } catch (IOException e) {



                            e.printStackTrace();

                        }

                    }

                }catch(FileNotFoundException e){

                    e.printStackTrace();

                }catch(IOException e){

                    e.printStackTrace();

                } finally{

                }


        }


}
