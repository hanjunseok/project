package kr.ac.baekseok.fanseestar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Join extends Activity {

    EditText editName, editGender, editAddress, editId, editPass, editPhone;
    ImageButton btnJoin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.join);
        editName=(EditText)findViewById(R.id.name);
        editGender=(EditText)findViewById(R.id.gender);
        editId=(EditText)findViewById(R.id.id);
        editPass=(EditText)findViewById(R.id.pass);
        editAddress=(EditText)findViewById(R.id.address);
        editPhone=(EditText)findViewById(R.id.phone);
        btnJoin=(ImageButton) findViewById(R.id.btnJoin);

        editName.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editGender.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editAddress.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editPhone.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editId.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editPass.setImeOptions(EditorInfo.IME_ACTION_DONE);

        editPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId){
                    case EditorInfo.IME_ACTION_DONE:
                        String name = editName.getText().toString();
                        String gender = editGender.getText().toString();
                        String id = editId.getText().toString();
                        String pass = editPass.getText().toString();
                        String address = editAddress.getText().toString();
                        String phone = editPhone.getText().toString();

                        if(name.equals("")){
                            Toast.makeText(getApplicationContext(), "이름을 입력하세요.", Toast.LENGTH_LONG).show();
                        }else if(gender.equals("")){
                            Toast.makeText(getApplicationContext(), "성명을 입력하세요.", Toast.LENGTH_LONG).show();
                        }else if(address.equals("")){
                            Toast.makeText(getApplicationContext(), "주소를 입력하세요.", Toast.LENGTH_LONG).show();
                        }else if(phone.equals("")){
                            Toast.makeText(getApplicationContext(), "핸드폰번호를 입력하세요.", Toast.LENGTH_LONG).show();
                        }else if(id.equals("")){
                            Toast.makeText(getApplicationContext(), "아이디를 입력하세요.", Toast.LENGTH_LONG).show();
                        }else if(pass.equals("")){
                            Toast.makeText(getApplicationContext(), "패스워드를 입력하세요.", Toast.LENGTH_LONG).show();
                        }else
                            joinToDatabase(name, gender, address, id, pass, phone);
                        break;
                }
                return true;
            }
        });
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String gender = editGender.getText().toString();
                String id = editId.getText().toString();
                String pass = editPass.getText().toString();
                String address = editAddress.getText().toString();
                String phone = editPhone.getText().toString();

                if(name.equals("")){
                    Toast.makeText(getApplicationContext(), "이름을 입력하세요.", Toast.LENGTH_LONG).show();
                }else if(gender.equals("")){
                    Toast.makeText(getApplicationContext(), "성명을 입력하세요.", Toast.LENGTH_LONG).show();
                }else if(id.equals("")){
                    Toast.makeText(getApplicationContext(), "아이디를 입력하세요.", Toast.LENGTH_LONG).show();
                }else if(pass.equals("")){
                    Toast.makeText(getApplicationContext(), "패스워드를 입력하세요.", Toast.LENGTH_LONG).show();
                }else if(address.equals("")){
                    Toast.makeText(getApplicationContext(), "주소를 입력하세요.", Toast.LENGTH_LONG).show();
                }else if(phone.equals("")){
                    Toast.makeText(getApplicationContext(), "핸드폰 번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }else
                    joinToDatabase(name, gender, address, id, pass, phone);
            }
        });
    }
    private void joinToDatabase(String name, String gender, String address, String id, String pass, String phone) {
        class JoinData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Join.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();

                Toast.makeText(getApplicationContext(), "회원가입 성공!", Toast.LENGTH_LONG).show();
                finish();

            }

            @Override
            protected String doInBackground(String... params) {
                try {

                    String name = (String) params[0];
                    String gender = (String) params[1];
                    String address = (String)params[2];
                    String id = (String) params[3];
                    String pass = (String) params[4];
                    String phone = (String) params[5];

                    String link = "http://junseok.dothome.co.kr/app/AppJoin.php";
                    String data =  URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                    data += "&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8");
                    data += "&" + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8");
                    data += "&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                    data += "&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
                    data += "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();


                } catch (Exception e) {
                    return new String("Exception : " + e.getMessage());
                }
            }
        }

        JoinData task = new JoinData();
        task.execute(name, gender, address, id, pass, phone);
    }
}
