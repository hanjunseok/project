package kr.ac.baekseok.fanseestar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class Login extends Activity {
    Button loginBtn,unLoginBtn;
    EditText EditId, EditPass;
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);

        loginBtn = (Button)findViewById(R.id.btnLogin);
        unLoginBtn = (Button)findViewById(R.id.btnLogin2);


        EditId = (EditText) findViewById(R.id.edtId);
        EditPass = (EditText) findViewById(R.id.edtpw);

        EditId.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        EditPass.setImeOptions(EditorInfo.IME_ACTION_DONE);

        EditPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId){
                    case EditorInfo.IME_ACTION_DONE:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Looper.prepare();
                                member();
                                Looper.loop();
                            }
                        }).start();
                        finish();
                        break;
                }
                return true;
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        member();
                        Looper.loop();
                    }
                }).start();
                finish();
            }
        });
        unLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("userId","");
                startActivity(intent);
            }
        });
    }
    public void member(){
        try{
            httpclient= new DefaultHttpClient();
            httppost = new HttpPost("http://junseok.dothome.co.kr/fanSeeStar/user/login.php");
            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("id", EditId.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("pass", EditPass.getText().toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response=httpclient.execute(httppost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);

            if(!response.equalsIgnoreCase("")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), response+" 님 로그인 성공", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra("userId",response);
                        startActivity(intent);
                    }
                });
            }else{

                Toast.makeText(getApplicationContext(), "아이디 비밀번호 확인하세요", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Login.this, Login.class));
            }
        }catch (Exception e){
            e.printStackTrace();

        }

    }
}
