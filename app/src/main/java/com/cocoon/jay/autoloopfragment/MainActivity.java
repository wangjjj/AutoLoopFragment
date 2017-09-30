package com.cocoon.jay.autoloopfragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Fragment> list;
    private LoopViewPager viewPager;

    private Timer mTimer;
    private TimerTask mTimerTask;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (LoopViewPager) findViewById(R.id.viewpager);

        Fragment a=new TestFragmentA();
        Fragment b=new TestFragmentB();
        Fragment c=new TestFragmentC();
        Fragment d=new TestFragmentD();

        list =new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);

        initViewPager();

    }






    private void initViewPager(){

        viewPager.setAdapter(new LoopFragmentAdapter(getSupportFragmentManager(),list));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {}

            @Override
            public void onPageSelected(int position)
            {

                //处理position。让position落在[0,fragmentList.size)中，防止数组越界
                position = position % list.size();
                Fragment fragment= list.get(position); //获得此时选中的fragment

            }

            @Override
            public void onPageScrollStateChanged(int state)
            {}
        });

        viewPager.setCurrentItem(list.size() * 100);//一开始可以往左滑

        viewPager.setOnTouchViewListener(new LoopViewPager.OnTouchViewListener() {
            @Override
            public void next() {
                handler.removeCallbacks(mTimerTask);
                stopTimer();
                startTimer();
            }

            @Override
            public void stop() {
                handler.removeCallbacks(mTimerTask);
                stopTimer();
            }
        });

        startTimer();

    }




    private void startTimer(){
        if (mTimer == null) {
            mTimer = new Timer();
        }

        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(0);

                        }
                    });
                }
            };
        }

        if(mTimer != null && mTimerTask != null )
            mTimer.schedule(mTimerTask , 3000, 3000);

    }

    private void stopTimer(){

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }





    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);

            switch (msg.what) {
                case 0:
                    int index = viewPager.getCurrentItem();
                    viewPager.setCurrentItem(index + 1);
                    break;
            }

        }
    };

}
