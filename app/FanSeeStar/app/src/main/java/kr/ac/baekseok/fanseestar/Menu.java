package kr.ac.baekseok.fanseestar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Menu extends AppCompatActivity {
    String userId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu);

        Button myArtist = (Button) findViewById(R.id.myArtist);
        Button userUpdateBtn = (Button) findViewById(R.id.btn4);
        Button userDeleteBtn = (Button) findViewById(R.id.btn5);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

            myArtist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(userId.equals("")){
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(getApplicationContext(), ShowMyArtist.class);
                        intent.putExtra("userId",userId);
                        startActivity(intent);
                    }

                }
            });
            userUpdateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(userId==null){
                        Intent intent1 = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent1);
                    }else {
                        Intent intent = new Intent(getApplicationContext(), UserUpdate.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    }
                }
            });
            userDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(userId==null){
                        Intent intent1 = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent1);
                    }else {
                        DialogSimple();
                    }
                }
            });

    }
    private void DialogSimple(){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setMessage("정말로 삭제 하실건가요?").setCancelable(
                false).setPositiveButton("네",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteToDatabase(userId);
                    }
                }).setNegativeButton("아니요",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alt_bld.create();
        // Title for AlertDialog
        alert.setTitle("delete");
        // Icon for AlertDialog
        alert.setIcon(R.drawable.loding);
        alert.show();
    }
    private void deleteToDatabase(String id) {
        class UpdateData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Menu.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();

                Toast.makeText(getApplicationContext(), "회원탈퇴 완료!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);

            }

            @Override
            protected String doInBackground(String... params) {
                try {


                    String id = (String) params[0];

                    String link = "http://junseok.dothome.co.kr/fanSeeStar/user/delete.php";
                    String data =  URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");


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

        UpdateData task = new UpdateData();
        task.execute(id);
    }

}
