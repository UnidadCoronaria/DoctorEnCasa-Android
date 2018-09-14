package com.unidadcoronaria.doctorencasa.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;

import com.unidadcoronaria.doctorencasa.App;
import com.unidadcoronaria.doctorencasa.R;
import com.unidadcoronaria.doctorencasa.di.component.DaggerNewsComponent;
import com.unidadcoronaria.doctorencasa.presenter.BasePresenter;
import com.unidadcoronaria.doctorencasa.presenter.NewsPresenter;

import butterknife.BindView;

/**
 * Created by AGUSTIN.BALA on 5/21/2017.
 */

public class NewsFragment extends BaseFragment<NewsPresenter> {

    public static final String TAG = "NewsFragment";

    @BindView(R.id.webview)
    WebView webView;

    @BindView(R.id.rl_progress)
    View progress;

    @Override
    protected int makeContentViewResourceId() {
        return R.layout.fragment_news;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    protected void inject() {
        DaggerNewsComponent.builder().applicationComponent(App.getInstance().getApplicationComponent()).build().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progress.setVisibility(View.VISIBLE);
        webView.loadUrl("http://www.ayudamedica.net/category/noticias/");
        webView.getSettings().setJavaScriptEnabled(true);
        new Handler().postDelayed(() -> {
            if(progress != null && webView != null){
                progress.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        },5000);
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

}
