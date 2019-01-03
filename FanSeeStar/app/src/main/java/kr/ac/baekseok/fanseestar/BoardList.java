package kr.ac.baekseok.fanseestar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.baekseok.fanseestar.domain.Artist;

public class BoardList extends FragmentActivity {
    String myJSON;
    String userId;
    String writer;
    String boardTitle;
    String boardContent;
    private static final String TAG_RESULTS="webnautes";
    private static final String TAG_WRITER = "writer";
    private static final String TAG_TITLE = "title";
    private static final String TAG_CONTENT ="content";
    Artist artist = null;

    TextView agencyText;
    ListView boardListView;
    ArrayList<HashMap<String, String>> mArrayList;
    ArrayList<Artist> artists = new ArrayList<Artist>();
    int myPosition;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.board_list);
        mArrayList = new ArrayList<>();
        boardListView = (ListView) findViewById(R.id.boardListView);
        Intent intent = getIntent();
        final ArrayList<Artist> artists = (ArrayList<Artist>) intent.getSerializableExtra("artists");
        userId = intent.getStringExtra("userId");
        myPosition = intent.getIntExtra("position",1);
        artist = new Artist();
        artist = artists.get(myPosition);
        GetData task = new GetData();
        task.execute(artist.getArtistName());

    }
    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(BoardList.this,
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

            String serverURL = "http://junseok.dothome.co.kr/fanSeeStar/boardList.php";
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

                writer = item.getString(TAG_WRITER);
                boardTitle = item.getString(TAG_TITLE);
                boardContent = item.getString(TAG_CONTENT);

                HashMap<String,String> hashMap = new HashMap<String,String>();

                hashMap.put(TAG_WRITER, writer);
                hashMap.put(TAG_TITLE, boardTitle);
                hashMap.put(TAG_CONTENT, boardContent);

                mArrayList.add(hashMap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    BoardList.this, mArrayList, R.layout.list_item_board,
                    new String[]{TAG_WRITER, TAG_TITLE, TAG_CONTENT},
                    new int[]{R.id.writer, R.id.boardTitle, R.id.boardContent}
            );

            boardListView.setAdapter(adapter);


        } catch (JSONException e) {

            //Log.d(TAG, "showResult : ", e);
        }

    }
}
