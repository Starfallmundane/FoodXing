package com.lx.foodxing.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    private ProgressDialog progress;
    protected View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutResId(), container, false);
        initView();
        initData();
        initListener();
        return view;
    }


    protected abstract int getLayoutResId();

    protected abstract void initView();

    protected void initData(){};
    
    protected void initListener(){};

    void showProgress() {
        progress = new ProgressDialog(getActivity());
        progress.show();

    }

    void dissProgress() {
        if (progress != null) {
            progress.dismiss();
        }
    }
}