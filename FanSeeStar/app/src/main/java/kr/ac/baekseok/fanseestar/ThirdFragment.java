package kr.ac.baekseok.fanseestar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
public class ThirdFragment extends Fragment
{
    ListView listview , listviewReview;
    SimpleAdapter adapter;
    ImageView imView;
    String myJSON;
    String userName;
    String userAddress;
    String userIntroduce;
    String userImage;
    String reviewCount;
    private static final String TAG_RESULTS="result";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_INTRODUCE ="introduce";
    private static final String TAG_IMAGE ="image";
    String rankCount;
    Artist artist = null;
    JSONArray peoples = null;

    ArrayList<HashMap<String, Object>> personList;
    ArrayList<Artist> artists = new ArrayList<Artist>();
    String userId;
    @SuppressLint("ValidFragment")
    public ThirdFragment(String userId){
        this.userId = userId;
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_third,container,false);
//        listview = (ListView) view.findViewById(R.id.rankListView);
//        imView = (ImageView) view.findViewById(R.id.imageView);
//        personList = new ArrayList<HashMap<String, Object>>();
//        getData("http://junseok.dothome.co.kr/app/appRank.php");
//
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Intent intent = new Intent(getActivity(), RankDetail.class);
//                intent.putExtra("users", users);
//                intent.putExtra("position",position);
//                startActivity(intent);
//
//
//            }
//        });
        return view;
    }

//    protected void showList(){
//        try {
//            JSONObject jsonObj = new JSONObject(myJSON);
//            peoples = jsonObj.getJSONArray(TAG_RESULTS);
//
//            for(int i=0;i<peoples.length();i++){
//                JSONObject c = peoples.getJSONObject(i);
//                userName = c.getString(TAG_NAME);
//                userAddress = c.getString(TAG_ADDRESS);
//                userIntroduce = c.getString(TAG_INTRODUCE);
//                userImage = c.getString(TAG_IMAGE);
//                HashMap<String,Object> persons = new HashMap<String,Object>();
//
//                persons.put(TAG_NAME,userName);
//                persons.put(TAG_ADDRESS,userAddress);
//                persons.put(TAG_INTRODUCE,userIntroduce);
//                persons.put(rankCount,i+1);
//                personList.add(persons);
//                dto = new DTO();
//                dto.setUserName(userName);
//                dto.setUserAddress(userAddress);
//                dto.setUserIntroduce(userIntroduce);
//                dto.setUserImage(userImage);
//                dto.setReviewCount(reviewCount);
//                users.add(dto);
//
//
//            }
//
//
//            adapter = new SimpleAdapter(
//                    getContext(), personList, R.layout.list_third,
//                    new String[]{TAG_NAME,TAG_ADDRESS,TAG_INTRODUCE,rankCount},
//                    new int[]{R.id.name, R.id.address, R.id.introduce, R.id.rank});
//            listview.setAdapter(adapter);
//            setListViewHeightBasedOnChildren(listview);
//
//
//
//
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }
//    public void getData(String url){
//        class GetDataJSON extends AsyncTask<String, Void, String> {
//
//            @Override
//            protected String doInBackground(String... params) {
//
//                String uri = params[0];
//
//                BufferedReader bufferedReader = null;
//                try {
//                    URL url = new URL(uri);
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    StringBuilder sb = new StringBuilder();
//
//                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//
//                    String json;
//                    while((json = bufferedReader.readLine())!= null){
//                        sb.append(json+"\n");
//                    }
//
//                    return sb.toString().trim();
//
//                }catch(Exception e){
//                    return null;
//                }
//
//
//
//            }
//
//            @Override
//            protected void onPostExecute(String result){
//                myJSON=result;
//                showList();
//            }
//        }
//        GetDataJSON g = new GetDataJSON();
//        g.execute(url);
//    }
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


