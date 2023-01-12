package com.progetto_ingegneria.pocketvenice.BottomNavbar.News;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.News.Models.NewsHeadlines;
import com.progetto_ingegneria.pocketvenice.BottomNavbar.Utility.DateTimeFormatting;
import com.progetto_ingegneria.pocketvenice.R;

/**
 * Forniscle la possibilità di visualizzare in modo corretto nel dettaglio una particolare News.
 * La visualizzazione viene effettuata tramite una webView integrata nell'applicazione, ma viene fornita la possiblità di aprire la news selezionata in un browser web esterno come Chrome.
 * Se l'utente ha effettuato il login, è possibile effettaure la condivisione di della news sepcifica con altri utenti.
 * @see com.progetto_ingegneria.pocketvenice.Auth.LoginActivity
 * Login Activity
 * @see News
 * News
 */
public class NewsDetails extends Fragment implements Toolbar.OnMenuItemClickListener {

    private static final String HEADLINES = "param1";
    protected View view;
    protected ImageView container_img;
    protected TextView title, time, title_appbar, subtitle_appbar;
    protected Toolbar toolbar;
    protected WebView webView;

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

        checkAuth();
        initView();
        setupToolbar();
        loadData();
        setData();
        initWebView();

        return view;
    }

    private void setupToolbar() {
        toolbar.inflateMenu(R.menu.menu_news);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {

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
        title = view.findViewById(R.id.title);
        time = view.findViewById(R.id.time);
        toolbar = requireActivity().findViewById(R.id.layoutToolBar);
        webView = view.findViewById(R.id.webView);
        toolbar.setOnMenuItemClickListener(this);

    }

    private void checkAuth() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            isLogged = true;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.view_web) {
            try {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(mUrl));
                requireActivity().startActivity(i);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Something went wrong. Cannot open browser. Try again", Toast.LENGTH_SHORT).show();
            }
        }
        if (item.getItemId() == R.id.share_button) {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plan");
                i.putExtra(Intent.EXTRA_SUBJECT, mTitle);
                String body = "News:\n" + mTitle + "Shared from PocketVenice App" + "\n";
                i.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(i, "Share with: "));
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Something went wrong. Cannot share at this moment. Try again", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}