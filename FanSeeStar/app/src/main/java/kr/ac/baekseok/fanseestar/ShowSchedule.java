package kr.ac.baekseok.fanseestar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.baekseok.fanseestar.domain.Artist;

public class ShowSchedule extends FragmentActivity{
    String myJSON;
    String userId;
    int scheduleId;
    String dateTime;
    String content;
    private static final String TAG_RESULTS="webnautes";
    private static final String TAG_SCHEDULEID = "scheduleId";
    private static final String TAG_DATE = "dateTime";
    private static final String TAG_CONTENT ="content";
    Artist artist = null;

    TextView agencyText;
    ListView scheduleListView;
    Button addUpBtn;
    ArrayList<HashMap<String, String>> mArrayList;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.schedule);
        agencyText = (TextView)findViewById(R.id.agencyText);
        scheduleListView = (ListView) findViewById(R.id.scheduleListView);
        addUpBtn = (Button)findViewById(R.id.addUp);



        Intent intent = getIntent();
        ArrayList<Artist> artists = (ArrayList<Artist>) intent.getSerializableExtra("artists");
        userId = intent.getStringExtra("userId");
        int position = intent.getIntExtra("position",1);
        artist = new Artist();
        artist = artists.get(position);

        agencyText.setText(artist.getArtistName() + " Schedules");
        GetData task = new GetData();
        task.execute(artist.getArtistName());
        mArrayList = new ArrayList<>();

            addUpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(userId.equals("")){
                        Intent intent = new Intent(getApplicationContext(),Login.class);
                        startActivity(intent);
                    }else {
                        addUpArtist(userId, artist.getArtistName());
                    }
                }
            });

    }
    private class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ShowSchedule.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            if (result == null){

                agencyText.setText(errorString);
            }
            else {

                myJSON = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String searchKeyword = params[0];

            String serverURL = "http://junseok.dothome.co.kr/fanSeeStar/schedule.php";
            String postParameters = "name=" + searchKeyword;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();


                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {


                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(myJSON);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_RESULTS);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                scheduleId = Integer.parseInt(item.getString(TAG_SCHEDULEID));
                dateTime = item.getString(TAG_DATE);
                content = item.getString(TAG_CONTENT);

                HashMap<String,String> hashMap = new HashMap<String,String>();

                hashMap.put(TAG_SCHEDULEID, String.valueOf(scheduleId));
                hashMap.put(TAG_DATE, dateTime);
                hashMap.put(TAG_CONTENT, content);

                mArrayList.add(hashMap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    ShowSchedule.this, mArrayList, R.layout.list_item_schdule,
                    new String[]{TAG_DATE, TAG_CONTENT},
                    new int[]{R.id.dateTime, R.id.content}
            );

            scheduleListView.setAdapter(adapter);

        } catch (JSONException e) {

            //Log.d(TAG, "showResult : ", e);
        }

    }

    private void addUpArtist(String userId, String artistName) {
        class JoinData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ShowSchedule.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();

                Toast.makeText(getApplicationContext(), "즐겨찾기 성공!", Toast.LENGTH_LONG).show();
                finish();

            }

            @Override
            protected String doInBackground(String... params) {
                try {

                    String userId = (String) params[0];
                    String artistName = (String) params[1];

                    String link = "http://junseok.dothome.co.kr/fanSeeStar/addUpArtist.php";
                    String data =  URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8");
                    data += "&" + URLEncoder.encode("artistName", "UTF-8") + "=" + URLEncoder.encode(artistName, "UTF-8");

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
        task.execute(userId,artistName);
    }
}
