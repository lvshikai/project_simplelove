package com.example.easylove.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easylove.R;
import com.example.easylove.base.BaseFragment;

/**
 * 
 * 信箱
 *
 */
public class FragmentMailbox extends BaseFragment {

	private View mView;

	@Override
	protected void initActivity(Bundle savedInstanceState) {
		
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		mView = inflater.inflate(R.layout.fragment_mailbox, null);

		return mView;
	}

}
