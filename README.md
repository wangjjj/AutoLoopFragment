# AutoLoopFragment
自动循环滚动的Fragment,重写viewpager禁止触摸时的滚动，简单好用

/////////////////////////////////////////////////////////
public class LoopFragmentAdapter extends FragmentPagerAdapter
{
    private ArrayList<Fragment> fragmentList;
    public LoopFragmentAdapter(FragmentManager fm)
    {
        super(fm);
    }
    public LoopFragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList)
    {
        super(fm);
        this.fragmentList=fragmentList;
    }
    @Override
    public Fragment getItem(int position)
    {
        //在这里不处理position的原因是因为getItem方法在
        //instantiateItem方法中调用。只要在调用前处理
        //position即可，以免重复处理
        return fragmentList.get(position);
    }

    @Override
    public int getCount()
    {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getItemPosition(Object object)
    {
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        //处理position。让数组下标落在[0,fragmentList.size)中，防止越界
        position = position % fragmentList.size();
        return super.instantiateItem(container, position);
    }
}
/////////////////////////////////////////////////////////
public class LoopViewPager extends ViewPager {

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public LoopViewPager(Context context) {
        super(context);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.i(tag, ev.getAction() + "--" + isAutoPlay);
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
                || action == MotionEvent.ACTION_OUTSIDE) {
            if(mListener != null) mListener.next();
        } else if (action == MotionEvent.ACTION_DOWN) {
            if(mListener != null) mListener.stop();
        }
        return super.dispatchTouchEvent(ev);
    }
    private OnTouchViewListener mListener;
    public void setOnTouchViewListener(OnTouchViewListener mListener){
        this.mListener = mListener;
    }
    /**
     *响应事件
     */
    public interface OnTouchViewListener{
        void next();
        void stop();
    }
}
/////////////////////////////////////////////////////////

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



//开计时器
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
//关计时器
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

