package kr.ac.baekseok.fanseestar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.baekseok.fanseestar.domain.Artist;


@SuppressLint("ValidFragment")
public class FirstFragment extends Fragment
{
    ListView listview;
    SimpleAdapter adapter;
    ImageView imView;
    String myJSON;
    String userArtist;
    String userAgency;
    String userImage;
    private static final String TAG_RESULTS="result";
    private static final String TAG_ARTIST = "artist";
    private static final String TAG_AGENCY = "agency";
    private static final String TAG_IMAGE ="image";
    Artist artist = null;
    JSONArray jsonData = null;
    private Context context;
    ArrayList<HashMap<String, Object>> jsonList;
    ArrayList<Artist> artists = new ArrayList<Artist>();
    int myPosition;
    String userId;
    @SuppressLint("ValidFragment")
    public FirstFragment(String userId) {
        this.userId = userId;
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        listview = (ListView) view.findViewById(R.id.smListView);
        imView = (ImageView) view.findViewById(R.id.img);
        jsonList = new ArrayList<HashMap<String, Object>>();
        getData("http://junseok.dothome.co.kr/fanSeeStar/sm/smMain.php");
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                myPosition=position;
                Intent intent = new Intent(getActivity(), ShowSchedule.class);
                intent.putExtra("artists", artists);
                intent.putExtra("userId", userId);
                intent.putExtra("position",position);
                startActivity(intent);


            }
        });



        return view;
    }

    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            jsonData = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<jsonData.length();i++){
                JSONObject c = jsonData.getJSONObject(i);
                userAgency = c.getString(TAG_AGENCY);
                userArtist = c.getString(TAG_ARTIST);
                userImage = c.getString(TAG_IMAGE);
                ;
                HashMap<String,Object> jsonMap = new HashMap<String,Object>();

                jsonMap.put(TAG_AGENCY,userAgency);
                jsonMap.put(TAG_ARTIST,userArtist);
                jsonMap.put(TAG_IMAGE,R.drawable.sm1+i);
                jsonList.add(jsonMap);

                artist = new Artist();
                artist.setAgency(userAgency);
                artist.setArtistName(userArtist);
                artist.setImage(userImage);
                artists.add(artist);


            }


            adapter = new SimpleAdapter(
                    getContext(), jsonList, R.layout.list_item,
                    new String[]{TAG_AGENCY,TAG_ARTIST, TAG_IMAGE},
                    new int[]{R.id.agency, R.id.artist, R.id.img});
            listview.setAdapter(adapter);
            //setListViewHeightBasedOnChildren(listview);






        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }



            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
//    public static void setListViewHeightBasedOnChildren(ListView listView) {
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {
//            // pre-condition
//            return;
//        }
//
//        int totalHeight = 0;
//        int count=0;
//        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
//
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
//            totalHeight += listItem.getMeasuredHeight();
//            count = i;
//        }
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight;
//        listView.setLayoutParams(params);
//        listView.requestLayout();
//
//    }

}


