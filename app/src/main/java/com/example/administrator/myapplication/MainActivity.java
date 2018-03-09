package com.example.administrator.myapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt_login;
    private Button bt_register;
    private EditText et_usrName;
    private EditText et_passWord;
    private String usrName;
    private String passWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        setContentView(R.layout.activity_main);
        bindView();
    }

    public void bindView(){
        bt_login    = findViewById(R.id.bt_login);
        bt_register = findViewById(R.id.bt_register);
        et_usrName  = findViewById(R.id.et_un);
        et_passWord = findViewById(R.id.et_Pw);
        usrName     = "";
        passWord    = "";
        bt_register.setOnClickListener(this);
        bt_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                usrName = et_usrName.getText().toString();
                passWord = et_passWord.getText().toString();
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            boolean result = login(usrName,passWord);
                            //登录成功，进入主界面
                            if(result == true){
                                //ObjectOutputStream oos = new ObjectOutputStream (
                                        //ManageClientConServer.getClientConServerThread(Integer.parseInt(usrName)).getS().getOutputStream());
                                //JiChatMsg m=new JiChatMsg();
                                //m.setType(JiChatMsgType.GET_ONLINE_FRIENDS);
                               // m.setSender(Integer.parseInt(usrName));
                                //oos.writeObject(m);

                                Intent intent = new Intent(MainActivity.this,JichatMainActivity.class);
                                MainActivity.this.startActivity(intent);
                            }
                            //登录失败，弹出提示
                            else{
                                Log.e("Jichat","fail");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("Jichat",e.toString());
                        }
                    }
                }.start();
                break;
            case R.id.bt_register:
                break;
            default:
                break;

        }

    }

    private boolean login(String usrName,String passWord) throws IOException {
        boolean result = false;

        //1.创建客户端Socket，指定服务器地址和端口
        Socket socket = new Socket("192.168.3.19", 12345);
        //2.获取输出流，向服务器端发送信息
        OutputStream os = socket.getOutputStream();//字节输出流
        InputStream is  = socket.getInputStream();
        PrintWriter pw = new PrintWriter(os);//将输出流包装为打印流
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        //获取客户端的IP地址
        InetAddress address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();
        pw.write("login " + ip + " " + usrName + " " + passWord);
        pw.flush();

        socket.shutdownOutput();//关闭输出流
        //socket.close();
        String temp = br.readLine().toString();
        Log.e("Jichat","response from server :"+temp);
        if(temp.equals("0")){
            result = true;
            //开启一条线程与服务器保持长连接
            //创建一个该账号和服务器保持连接的线程
            //ClientConServerThread ccst=new ClientConServerThread(MainActivity.this,socket);
            //启动该通信线程
            //ccst.start();
            //加入到管理类中
            //ManageClientConServer.addClientConServerThread(Integer.parseInt(usrName), ccst);
        }
        if(temp.equals("1")){
            result = false;
        }

        return result;
    }
}
