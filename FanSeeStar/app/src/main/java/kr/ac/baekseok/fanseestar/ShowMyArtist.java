package kr.ac.baekseok.fanseestar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
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

public class ShowMyArtist extends FragmentActivity {
    String myJSON;
    String userId;
    String agency;
    String artistName;
    String image;
    private static final String TAG_RESULTS="webnautes";
    private static final String TAG_AGENCY = "agency";
    private static final String TAG_ARTIST = "artistName";
    private static final String TAG_IMAGE ="image";
    Artist artist = null;

    TextView agencyText;
    ListView myArtistListView;
    ArrayList<HashMap<String, String>> mArrayList;
    ArrayList<Artist> artists = new ArrayList<Artist>();
    int myPosition;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.user_artist);

        myArtistListView = (ListView) findViewById(R.id.myArtistListView);
        mArrayList = new ArrayList<>();
        Intent intent = getIntent();
        //final ArrayList<Artist> artists = (ArrayList<Artist>) intent.getSerializableExtra("artists");
        userId = intent.getStringExtra("userId");
        GetData task = new GetData();
        task.execute(userId);


        myArtistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myPosition=position;
                Intent intent = new Intent(getApplicationContext(), BoardList.class);
                intent.putExtra("artists", artists);
                intent.putExtra("userId", userId);
                intent.putExtra("position",myPosition);
                startActivity(intent);
            }
        });

    }
    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ShowMyArtist.this,
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

            String serverURL = "http://junseok.dothome.co.kr/fanSeeStar/myArtist.php";
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

                agency = item.getString(TAG_AGENCY);
                artistName = item.getString(TAG_ARTIST);
                image = item.getString(TAG_IMAGE);

                Artist artist = new Artist();
                artist.setAgency(agency);
                artist.setArtistName(artistName);
                artist.setImage(image);

                artists.add(artist);

                HashMap<String,String> hashMap = new HashMap<String,String>();

                hashMap.put(TAG_AGENCY, agency);
                hashMap.put(TAG_ARTIST, artistName);
                hashMap.put(TAG_IMAGE, image);

                mArrayList.add(hashMap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    ShowMyArtist.this, mArrayList, R.layout.list_item_my_artist,
                    new String[]{TAG_AGENCY, TAG_ARTIST},
                    new int[]{R.id.agency, R.id.artist}
            );

            myArtistListView.setAdapter(adapter);


        } catch (JSONException e) {

            //Log.d(TAG, "showResult : ", e);
        }

    }

}
