package com.ktweb.haru5chinese.ui.activity;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.kakao.kakaolink.v2.KakaoLinkCallback;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.network.ErrorResult;
import com.ktweb.haru5chinese.R;
import com.ktweb.haru5chinese.adapter.CommentAdapter;
import com.ktweb.haru5chinese.api.news.apiNews;
import com.ktweb.haru5chinese.api.news.newsClient;
import com.ktweb.haru5chinese.entity.news.ApiResponse;
import com.ktweb.haru5chinese.entity.news.Article;
import com.ktweb.haru5chinese.entity.news.Comment;
import com.ktweb.haru5chinese.manager.PrefManager;
import com.ktweb.haru5chinese.manager.StorageFavorites;
import com.ktweb.haru5chinese.services.BitLay;
import com.ktweb.haru5chinese.services.StoryLink;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.squareup.picasso.Picasso;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ArticleActivity extends AppCompatActivity {

	... 중략 ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        initView();
        initAction();
        Intent intent = getIntent();
        processIntent(intent);
        setFabClickListener();
    }

    void setFabClickListener() {

	... 중략 ...

        findViewById(R.id.fab_share_kakao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareArticleToKakao();
            }
        });

        findViewById(R.id.fab_share_band).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String linkUrl = getString(R.string.app_base_url)+id_article;
                final List<String> dataMap = new ArrayList<String>();
                dataMap.add(0, "" + getString(R.string.app_name) + "");

                class BandBitLayAsync extends BitLay{
                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);

                        // API 5천번 이상이거나 오류로 생성 못했을때는 긴 링크를 전달.
                        if(this.shortUrl == null || "".equals(this.shortUrl)){
                            this.shortUrl = getString(R.string.app_base_url) + id_article;
                        }
                        dataMap.add(1, "" + this.shortUrl + "");
                        dataMap.add(2, "\n");
                        dataMap.add(3, "" + title_article + "");
                        dataMap.add(4, "" + android.text.Html.fromHtml(content_article).toString().replace("img{max-width:100% !important}", "") + "");

                        shareArticleToBand(dataMap);
                    }
                    public BandBitLayAsync(String longUrl, String key, String lgn) {
                        super(longUrl, key, lgn);
                    }
                }
                new BandBitLayAsync(linkUrl, getString(R.string.api_bitly_key), getString(R.string.api_bitly_login)).execute();
            }
        });
        // 2018-12-16
        findViewById(R.id.fab_share_kakaoStory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<String> dataMap = new ArrayList<String>();
                dataMap.add(0, "" + getString(R.string.app_name) + "");

                final Map<String, Object> urlInfoAndroid = new Hashtable<String, Object>(1);
                urlInfoAndroid.put("title", "" + getString(R.string.app_name) + "\n\n" + title_article);
                urlInfoAndroid.put("desc", short_article);
                urlInfoAndroid.put("imageurl", new String[] {image_article});
                urlInfoAndroid.put("type", "article");
                String linkUrl = getString(R.string.app_base_url) + id_article;

                class BandBitLayAsync extends BitLay{
                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        if(this.shortUrl == null || "".equals(this.shortUrl)){
                            this.shortUrl = getString(R.string.app_base_url) + id_article;
                        }
                        dataMap.add(1, "원문 바로가기 : " + this.shortUrl + "");
                        dataMap.add(2, "\n");
                        dataMap.add(3, "" + title_article + "");
                        dataMap.add(4, "" + android.text.Html.fromHtml(content_article).toString().replace("img{max-width:100% !important}", "") + "");

                        urlInfoAndroid.put("linkUrl", this.shortUrl);

                        shareArticleToKakaoStory(dataMap, urlInfoAndroid);
                    }
                    public BandBitLayAsync(String longUrl, String key, String lgn) {
                        super(longUrl, key, lgn);
                    }
                }
                new BandBitLayAsync(linkUrl, getString(R.string.api_bitly_key), getString(R.string.api_bitly_login)).execute();
            }
        });

        findViewById(R.id.fab_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.toggle(true);
                shareArticle();
            }
        });

	... 중략 ...

    }
	
	... 중략 ...

    private void shareArticle() {
        String shareBody = title_article + " \n\n " + android.text.Html.fromHtml(content_article).toString().replace("img{max-width:100% !important}", "") + " \n\n ● 하루 5분 매일매일!\n" + getString(R.string.app_name) + " 앱 다운로드 ▶   " + getString(R.string.url_app_google_play);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.app_name)));
    }

    private void shareArticleToBand(final List<String> dataMap) {
        try {
            PackageManager manager = this.getPackageManager();
            Intent i = manager.getLaunchIntentForPackage("com.nhn.android.band");

            String serviceDomain = "www.bloter.net"; //  연동 서비스 도메인
            String encodedText;
            StringBuilder stb = new StringBuilder();
            for (String item : dataMap){
                stb.append(item + "\n");
            }
            encodedText = URLEncoder.encode(stb.toString());
            Uri uri = Uri.parse("bandapp://create/post?text=" + encodedText + "&route=" + serviceDomain);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (Exception e) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_market_url) + "com.nhn.android.band"));
            startActivity(intent);
        }
    }
    // 2018-12-16
    private void shareArticleToKakaoStory(final List<String> dataMap, Map<String, Object> urlInfoAndroid){
        StoryLink storyLink = StoryLink.getLink(getApplicationContext());

        if (!storyLink.isAvailableIntent()) {
            // 카카오스토리 설치 안된 경우.
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_market_url) + "com.kakao.story"));
            startActivity(intent);
            return;
        }
        try {
            StringBuilder stb = new StringBuilder();
            for (String item : dataMap){
                stb.append(item + "\n");
            }
            storyLink.openKakaoLink(this,
                    stb.toString(),
                    getPackageName(),
                    ""+this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName,
                    getString(R.string.app_name),
                    "UTF-8",
                    urlInfoAndroid);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void shareArticleToKakao() {
        FeedTemplate params = FeedTemplate
                .newBuilder(ContentObject.newBuilder("[  " + getString(R.string.app_name) + "  ]",
                        image_article,
                        LinkObject.newBuilder()
                                .setWebUrl("https://play.google.com/store/apps/details?id=" + getPackageName())
                                .setMobileWebUrl("https://play.google.com/store/apps/details?id=" + getPackageName())
                                .setAndroidExecutionParams("id_article=" + id_article).build())
                        .setDescrption(title_article)
                        .build())
                .addButton(new ButtonObject("자세히 보기", LinkObject.newBuilder()
                        .setWebUrl(getString(R.string.app_base_url) + id_article)
                        .setMobileWebUrl(getString(R.string.app_base_url) + id_article)
                        .setAndroidExecutionParams("id_article=" + id_article)
                        .build()))
                .build();
        KakaoLinkService.getInstance().sendDefault(ArticleActivity.this, params, new KakaoLinkCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                super.onFailure(errorResult);
                Log.e("asdf error", "error uu");
            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {
                super.onSuccess(result);
                Log.e("asdf", "success " + result.getWarningMsg().toString());
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        PrefManager prf = new PrefManager(getApplicationContext());
        prf.setString("APP_RUN", "true");
    }

    @Override
    public void onStart() {
        super.onStart();
        if(returlUrl != null){
            web_view_article_content.loadUrl(returlUrl);
            web_view_article_content.reload();
            web_view_article_content.onResume();
        }
        PrefManager prf = new PrefManager(getApplicationContext());
        prf.setString("APP_RUN", "true");
    }

    @Override
    protected void onStop() {
        super.onStop();
        // web view block
        returlUrl = web_view_article_content.getUrl();
        web_view_article_content.loadUrl(""); // url 날려버림.
        web_view_article_content.reload(); // 없는 url 로 호출
        web_view_article_content.onPause(); // 그래도 모르니 pause
    }

    private String returlUrl;
    @Override
    public void onPause() {
        // web view block
        returlUrl = web_view_article_content.getUrl();
        web_view_article_content.loadUrl(""); // url 날려버림.
        web_view_article_content.reload(); // 없는 url 로 호출
        web_view_article_content.onPause(); // 그래도 모르니 pause
        super.onPause();

        PrefManager prf = new PrefManager(getApplicationContext());
        prf.setString("APP_RUN", "false");
    }

    @Override
    public void onDestroy() {
        // web view block
        returlUrl = web_view_article_content.getUrl();
        web_view_article_content.loadUrl(""); // url 날려버림.
        web_view_article_content.reload(); // 없는 url 로 호출
        web_view_article_content.destroy(); // 그래도 모르니 destroy
        super.onDestroy();
        PrefManager prf = new PrefManager(getApplicationContext());
        prf.setString("APP_RUN", "false");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        if(returlUrl != null){
            web_view_article_content.loadUrl(returlUrl);
            web_view_article_content.reload();
            web_view_article_content.onResume();
        }
        PrefManager prf = new PrefManager(getApplicationContext());
        prf.setString("APP_RUN", "true");
    }
	
	... 중략 ...
}
