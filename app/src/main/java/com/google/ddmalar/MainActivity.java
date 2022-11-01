package com.google.ddmalar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity implements ViewTreeObserver.OnScrollChangedListener {




    WebView webView;
    ProgressBar progressBar;
    Toolbar toolbar;
    SwipeRefreshLayout swipeRefreshLayout;
    Button searchBtn ;
    String searchedText="";
    Button button;
    TextView text ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        searchBtn=findViewById(R.id.button);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!searchedText.isEmpty()){
                    if (searchedText.endsWith(".com")||searchedText.endsWith(".in")){
                        webView.loadUrl("https://"+searchedText);
                    }else {
                        webView.loadUrl("https://www.google.com/search?q="+searchedText);
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Please enter something", Toast.LENGTH_SHORT).show();
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String webUrl = webView.getUrl();
                webView.loadUrl(webUrl);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setSubtitle("Stream malayalam series");


        webView = findViewById(R.id.webView);
        webView.loadUrl("https://www.ddmalar.website");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.getViewTreeObserver().addOnScrollChangedListener(this);


        progressBar = findViewById(R.id.progressBar);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Search for a Website");



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (query.endsWith(".com")||query.endsWith(".in")){
                    webView.loadUrl("https://"+query);
                }else {
                    webView.loadUrl("https://www.google.com/search?q="+query);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
             //   Toast.makeText(MainActivity.this, newText, Toast.LENGTH_SHORT).show();
               // Log.d("TAG", "onQueryTextChange: jhdljshd");
                searchedText=newText;
                return false;
            }
        });

        return true;
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.about) {
            Toast.makeText(this, "about", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.search) {
            return true;
        }else if (itemId==R.id.help){
            Toast.makeText(this, "help", Toast.LENGTH_SHORT).show();
        }else if (itemId==R.id.home){
            webView.loadUrl("https://www.ddmalar.website");
        }else{
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScrollChanged() {
        if (webView.getScrollY() == 0) {
            swipeRefreshLayout.setEnabled(true);
        } else {
            swipeRefreshLayout.setEnabled(false);
        }
    }
}