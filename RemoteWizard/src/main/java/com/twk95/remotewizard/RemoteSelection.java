package com.twk95.remotewizard;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class RemoteSelection extends AppCompatActivity {

    ViewPager mViewPager;
    int[] mRemotes = {R.drawable.remote_0,
            R.drawable.remote_1,
            R.drawable.remote_2};
    private Integer page = 0;

    public RemoteSelection() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remotes);
        final Button start = (Button) findViewById(R.id.start_button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RemoteSelection.this, ProgramActivity.class);
                intent.putExtra("remote", page);
                startActivity(intent);
            }
        });
        assert getSupportActionBar() != null;
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Select A Remote");
        CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                page = i;
                if (i == 3) {
                    start.setEnabled(false);
                    start.setTextColor(getResources().getColor(R.color.primary_muted));
                } else {
                    start.setEnabled(true);
                    start.setTextColor(getResources().getColor(R.color.primary));
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mRemotes.length + 1;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view;
            switch (position) {
                default:
                    view = mLayoutInflater.inflate(R.layout.pager_item, container, false);
                    ImageView imageView = (ImageView) view.findViewById(R.id.remoteView);
                    imageView.setImageResource(mRemotes[position]);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(RemoteSelection.this, ProgramActivity.class);
                            intent.putExtra("remote", page);
                            startActivity(intent);
                        }
                    });
                    break;
                case 3:
                    view = mLayoutInflater.inflate(R.layout.no_remote, container, false);
                    break;
            }
            container.addView(view);
            return view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Phazr-5";
                case 1:
                    return "DTA Remote";
                case 2:
                    return "New DTA Remote";
                case 3:
                    return "404";
            }
            return null;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();
            if (position < -1) {
                view.setAlpha(0);
            } else if (position <= 1) {
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else {
                view.setAlpha(0);
            }
        }
    }

}
