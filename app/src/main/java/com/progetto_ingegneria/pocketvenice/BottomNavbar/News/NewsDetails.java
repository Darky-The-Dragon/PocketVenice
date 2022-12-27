package com.progetto_ingegneria.pocketvenice.BottomNavbar.News;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.News.Models.NewsHeadlines;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.Utility.DateTimeFormatting;
import com.progetto_ingegneria.pocketvenice.R;
import com.squareup.picasso.Picasso;

public class NewsDetails extends Fragment implements AppBarLayout.OnOffsetChangedListener {

    private static final String HEADLINES = "param1";
    protected View view;
    protected ImageView imageView;
    protected TextView appbar_title, appbar_subtitle, date, time, title;
    protected boolean isHideToolbarView = false;
    protected FrameLayout date_behaviour;
    protected LinearLayout titleAppbar;
    protected AppBarLayout appBarLayout;
    protected Toolbar toolbar;
    protected String mUrl, mImg, mTitle, mDate, mSource, mAuthor;
    protected NewsHeadlines mHeadlines;
    protected FirebaseUser user;
    protected boolean isLogged = false;

    public NewsDetails() {
        // Required empty public constructor
    }

    public static NewsDetails newInstance(NewsHeadlines headlines) {
        NewsDetails fragment = new NewsDetails();
        Bundle args = new Bundle(1);
        args.putSerializable(HEADLINES, headlines);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mHeadlines = (NewsHeadlines) getArguments().getSerializable(HEADLINES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news_details, container, false);

        //La chiamata a questo metodo provoca NullPointerException
        //toolbarSetup();

        checkAuth();
        initView();
        //toolbarSetup();
        loadData();
        setData();
        initWebView(mUrl);

        return view;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(String mUrl) {

        WebView webView = view.findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptThirdPartyCookies(webView, true);

        webView.loadUrl(mUrl);
    }

    private void setData() {
        Picasso.get().load(mImg).into(imageView);
        appbar_title.setText(mTitle);
        appbar_subtitle.setText(mUrl);
        title.setText(mTitle);
        time.setText(DateTimeFormatting.DateToTimeFormat(mDate));
    }

    private void loadData() {
        mUrl = mHeadlines.getUrl();
        mImg = mHeadlines.getUlrToImage();
        mTitle = mHeadlines.getTitle();
        mDate = mHeadlines.getPublishedAt();
        mSource = String.valueOf(mHeadlines.getSource());
        mAuthor = mHeadlines.getAuthor();
    }

    private void initView() {
        appBarLayout = view.findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(this);
        titleAppbar = view.findViewById(R.id.title_appbar);
        imageView = view.findViewById(R.id.backdrop);
        appbar_title = view.findViewById(R.id.title_on_appbar);
        appbar_subtitle = view.findViewById(R.id.subtitle_on_appbar);
        time = view.findViewById(R.id.time);
        title = view.findViewById(R.id.title);
    }

    private void checkAuth() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            isLogged = true;
        }
    }

    private void toolbarSetup() {

        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        //problemi da qui
        requireActivity().getActionBar().setTitle("");
        requireActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        final CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;


        if (percentage == 1f && isHideToolbarView) {
            //date_behaviour.setBackgroundColor(10);
            //date_behaviour.setVisibility(View.GONE);
            titleAppbar.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;
        } else if (percentage < 1f && isHideToolbarView) {
            //date_behaviour.setBackgroundColor(10);
            //date_behaviour.setVisibility(View.VISIBLE);
            titleAppbar.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}