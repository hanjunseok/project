package kr.ac.baekseok.fanseestar;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ViewPager vp;
    String userId,reviewCount;
    private BackPressCloseHandler backPressCloseHandler;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        vp = (ViewPager) findViewById(R.id.vp);
        final Button btn_first = (Button) findViewById(R.id.sm);
        final Button btn_second = (Button) findViewById(R.id.jyp);
        final Button btn_third = (Button) findViewById(R.id.yg);
        TextView goHome = (TextView) findViewById(R.id.homeGo);
        TextView menu = (TextView) findViewById(R.id.menu);

        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(0);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        reviewCount = intent.getStringExtra("reviewCount");
        btn_first.setOnClickListener(movePageListener);
        btn_first.setTag(0);
        btn_second.setOnClickListener(movePageListener);
        btn_second.setTag(1);
        btn_third.setOnClickListener(movePageListener);
        btn_third.setTag(2);
        backPressCloseHandler = new BackPressCloseHandler(this);



        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, kr.ac.baekseok.fanseestar.Menu.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });
        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(0);
            }
        });

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    btn_first.setSelected(true);
                    btn_second.setSelected(false);
                    btn_third.setSelected(false);
                }else if (position == 1){
                    btn_first.setSelected(false);
                    btn_second.setSelected(true);
                    btn_third.setSelected(false);
                }else if (position == 2){
                    btn_first.setSelected(false);
                    btn_second.setSelected(false);
                    btn_third.setSelected(true);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    View.OnClickListener movePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = (int) v.getTag();
            vp.setCurrentItem(tag);
        }
    };




    private class pagerAdapter extends FragmentStatePagerAdapter {
        public pagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FirstFragment(userId);
                case 1:
                    return new SecondFragment(userId);
                case 2:
                    return new ThirdFragment(userId);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
        }



    }
}
