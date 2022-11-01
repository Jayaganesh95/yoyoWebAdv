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
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements ViewTreeObserver.OnScrollChangedListener,
        SwipeRefreshLayout.OnRefreshListener {

    WebView webView;
    ProgressBar progressBar;
    Toolbar toolbar;
    SwipeRefreshLayout swipeRefreshLayout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setSubtitle("Stream malayalam series");
        setSupportActionBar(toolbar);

        webView = findViewById(R.id.webView);
        webView.loadUrl("https://www.ddmalar.website");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.getViewTreeObserver().addOnScrollChangedListener(this);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);

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

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.nav_layout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_account) {
                    Toast.makeText(MainActivity.this, "accnt", Toast.LENGTH_SHORT).show();
                }else if (itemId == R.id.nav_settings) {
                    Toast.makeText(MainActivity.this, "settng", Toast.LENGTH_SHORT).show();
                }else if (itemId==R.id.nav_logout) {
                    Toast.makeText(MainActivity.this, "logout", Toast.LENGTH_SHORT).show();
                }
                return false;
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
               // Log.d("TAG", "onQueryTextChange: newText");
                return false;
            }
        });

        return true;
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }else {
            if (itemId == R.id.about) {
                Toast.makeText(this, "about", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.search) {
                return true;
            }else if (itemId==R.id.help){
                Toast.makeText(this, "help", Toast.LENGTH_SHORT).show();
            }else if (itemId==R.id.home){
                webView.loadUrl("https://www.ddmalar.website");
            } else{
                super.onBackPressed();
            }
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




    @Override
    public void onRefresh() {
        String webUrl = webView.getUrl();
        webView.loadUrl(webUrl);
        swipeRefreshLayout.setRefreshing(false);
    }
}