package com.example.easylove.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

import com.example.easylove.R;
import com.example.easylove.base.BaseActivity;
import com.example.easylove.fragment.FragmentBeckoning;
import com.example.easylove.fragment.FragmentClub;
import com.example.easylove.fragment.FragmentMailbox;
import com.example.easylove.fragment.FragmentMy;
import com.example.easylove.view.ChangeColorIconWithTextView;

/**
 * 主页
 * 
 * 
 */
@SuppressLint("NewApi")
public class MainActivity extends BaseActivity implements OnPageChangeListener {

	private ViewPager mViewPager;

	/** 心动选项卡 **/
	private FragmentBeckoning mFragmentBeckoning;

	/** 俱乐部选项卡 **/
	private FragmentClub mFragmentClub;

	/** 信箱选项卡 **/
	private FragmentMailbox mFragmentMailbox;

	/** 我的选项卡 **/
	private FragmentMy mFragmentMy;

	private List<ChangeColorIconWithTextView> mTabIndicator;

	private ChangeColorIconWithTextView mBeckoning, mClub, mMailbox, mMy;

	private int index;
	
	@Override
	protected void initActivity(Bundle savedInstanceState) {

		setContentView(R.layout.activity_main);
		
		initUI();
		
		initData();
		
		setListener();
	}

	private void initUI() {
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		mBeckoning = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_beckoning);
		mClub = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_club);
		mMailbox = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_mailbox);
		mMy = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_my);
		
	}

	private void initData() {
		mTabIndicator=new ArrayList<ChangeColorIconWithTextView>();
		
		mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		
		mTabIndicator.add(mBeckoning);
		mTabIndicator.add(mClub);
		mTabIndicator.add(mMailbox);
		mTabIndicator.add(mMy);
	}
	
	private void setListener(){
		
		mViewPager.setOnPageChangeListener(this);
		
		mBeckoning.setOnClickListener(this);
		mClub.setOnClickListener(this);
		mMailbox.setOnClickListener(this);
		mMy.setOnClickListener(this);
		
		mBeckoning.setIconAlpha(1.0f);
	}
	
	public void onClick(View v){

		resetOtherTabs();

		switch (v.getId()) {
		case R.id.id_indicator_beckoning:
			
			index=mTabIndicator.indexOf(mBeckoning);
		
			break;
		case R.id.id_indicator_club:
			
			index=mTabIndicator.indexOf(mClub);
			
			break;
		case R.id.id_indicator_mailbox:
			
			index=mTabIndicator.indexOf(mMailbox);
			
			break;
		case R.id.id_indicator_my:
			
			index=mTabIndicator.indexOf(mMy);
			
			break;
		}
		
		mTabIndicator.get(index).setIconAlpha(1.0f);
		mViewPager.setCurrentItem(index, false);
	}

	@Override
	public void onPageScrollStateChanged(int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		if (positionOffset > 0) {
			ChangeColorIconWithTextView left = mTabIndicator.get(position);
			ChangeColorIconWithTextView right = mTabIndicator.get(position + 1);

			left.setIconAlpha(1 - positionOffset);
			right.setIconAlpha(positionOffset);
		}
	}

	@Override
	public void onPageSelected(int state) {
				
	}
	
	/**
	 * 重置其他的Tab
	 */
	private void resetOtherTabs() {
		for (int i = 0; i < mTabIndicator.size(); i++) {
			mTabIndicator.get(i).setIconAlpha(0);
		}
	}
	
	/**
	 * 
	 * 适配器
	 *
	 */
	public class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		private final String[] titles = { "心动", "俱乐部","信箱", "我的"};

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				if (mFragmentBeckoning == null) {
					mFragmentBeckoning = new FragmentBeckoning();
				}
				return mFragmentBeckoning;
			case 1:
				if (mFragmentClub == null) {
					mFragmentClub = new FragmentClub();
				}
				return mFragmentClub;
			case 2:
				if (mFragmentMailbox == null) {
					mFragmentMailbox = new FragmentMailbox();
				}
				return mFragmentMailbox;
			case 3:
				if (mFragmentMy == null) {
					mFragmentMy = new FragmentMy();
				}
				return mFragmentMy;
			default:
				return null;
			}
		}
	}
	
}
