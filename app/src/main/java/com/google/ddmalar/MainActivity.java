package com.google.ddmalar;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setSubtitle("Stream malayalam series");

        webView = findViewById(R.id.webView);
        webView.loadUrl("https://www.ddmalar.website");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

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

         MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
             @Override
             public boolean onMenuItemActionExpand(MenuItem menuItem) {
                 webView.loadUrl("https://www.google.com");
                 return true;
             }

             @Override
             public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                 Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                 return true;
             }
         };
         menu.findItem(R.id.search).setOnActionExpandListener(onActionExpandListener);
         SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
         searchView.setQueryHint("Search for a Website");
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
}