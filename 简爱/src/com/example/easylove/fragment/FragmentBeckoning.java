package com.example.easylove.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.easylove.R;
import com.example.easylove.base.BaseFragment;

/**
 * 
 * 心动
 *
 */
public class FragmentBeckoning extends BaseFragment {

	private View mView;

	@Override
	protected void initActivity(Bundle savedInstanceState) {
		
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		mView = inflater.inflate(R.layout.fragment_beckoning, null);

		return mView;
	}

	

}
