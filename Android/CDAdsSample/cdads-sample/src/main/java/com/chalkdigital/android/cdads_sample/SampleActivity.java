package com.chalkdigital.android.cdads_sample;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.chalkdigital.ads.CDAdErrorCode;
import com.chalkdigital.common.CDAdRequest;
import com.chalkdigital.interstitial.ads.CDAdInterstitial;
import com.chalkdigital.ads.CDAdView;
import com.chalkdigital.interstitial.ads.CDAdVideoInterstitial;
import com.chalkdigital.nativeads.CDAdNative;
import com.chalkdigital.nativeads.CDAdVideoNativeAdRenderer;
import com.chalkdigital.nativeads.BaseNativeAd;
import com.chalkdigital.nativeads.MediaViewBinder;
import com.chalkdigital.nativeads.NativeAd;
import com.chalkdigital.nativeads.NativeErrorCode;
import com.chalkdigital.nativeads.VideoConfiguration;

import java.util.HashMap;


public class SampleActivity extends AppCompatActivity implements CDAdView.CDAdViewListener, CDAdInterstitial.InterstitialAdListener, CDAdVideoInterstitial.InterstitialVideoAdListener, CDAdNative.CDAdNativeAdListener {
    private CDAdView mSmallBannerView;
    private CDAdView mLargeBannerView;
    private CDAdInterstitial mCDAdInterstitial;
    private ProgressBar mProgressBar;
    private SharedPreferences sharedPreferences;
    private Menu mMenu;
    private CDAdVideoInterstitial mCDAdVideoInterstitial;
    private AlertDialog alert11;
    private MediaViewBinder mediaViewBinder;
    private CDAdNative mCDAdNative;
    private NativeAd mNativeAd;
    private ConstraintLayout mConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCDAdVideoInterstitial = new CDAdVideoInterstitial(this);
        mCDAdVideoInterstitial.setLocationAutoUpdateEnabled(true);
        mCDAdVideoInterstitial.setInterstitialVideoAdListener(this);


        mCDAdVideoInterstitial.setTesting(true);



//        mLargeBannerView = findViewById(R.id.largeBannerView);
        mLargeBannerView.setCDAdViewListener(this);
        mLargeBannerView.setLocationAutoUpdateEnabled(true);
        mLargeBannerView.setAdAutoRefreshEnabled(true);     //Default value is false
        mLargeBannerView.setTesting(true);
        mLargeBannerView.setBundleIdentifier("mytestapp.mydomain.com");
        mLargeBannerView.setCategory("IAB1-5,IAB1-6");
        mLargeBannerView.setTesting(true);


        mConstraintLayout = findViewById(R.id.native_video_ad_space);

        mediaViewBinder = new MediaViewBinder.Builder(R.layout.native_video_ad_layout)
                .mediaLayoutId(R.id.native_ad_video_view)
                .build();



    }

    public void loadInterstitialVideo(View v){
        clearAllViews();
        if (mCDAdVideoInterstitial == null){
            mCDAdVideoInterstitial = new CDAdVideoInterstitial(this);
            mCDAdVideoInterstitial.setLocationAutoUpdateEnabled(true);
            mCDAdVideoInterstitial.setInterstitialVideoAdListener(this);
            mCDAdVideoInterstitial.setTesting(true);
            mCDAdVideoInterstitial.setBundleIdentifier("mytestapp.mydomain.com");
            mCDAdVideoInterstitial.setCategory("IAB1-5,IAB1-6");
        }
        HashMap<String, String > map = new HashMap<>();
        map.put("gender", "male");
        map.put("age", "25");
        map.put("income", "100000");
        map.put("language", "en");
        VideoConfiguration videoConfiguration = new VideoConfiguration.Builder().build(this);
        mCDAdVideoInterstitial.requestInterstitialVideo(this,videoConfiguration, map, "partnerId", "placementId");
    }

    public void loadNativeVideo(View v){
        mProgressBar.setVisibility(View.VISIBLE);
        dismissIfAlertDialogVisible();
        clearAllViews();
        CDAdVideoNativeAdRenderer cdAdVideoNativeAdRenderer = new CDAdVideoNativeAdRenderer(mediaViewBinder);
        mCDAdNative = new CDAdNative(this, this);
        mCDAdNative.registerAdRenderer(cdAdVideoNativeAdRenderer);
        CDAdRequest cdAdRequest = (new CDAdRequest.Builder()).build(this);
        cdAdRequest.bundleId = "mytestapp.mydomain.com";
        cdAdRequest.partnerId = "partnerId";
        cdAdRequest.placementId = "placementId";
        cdAdRequest.cat = "IAB1-5,IAB1-6";
        cdAdRequest.testing = true;
        mCDAdNative.makeRequest(cdAdRequest);
    }

    public void loadInterstitial(View v){
        clearAllViews();
        if (mCDAdInterstitial == null){
            mCDAdInterstitial = new CDAdInterstitial(this);
            mCDAdInterstitial.setLocationAutoUpdateEnabled(true);
            mCDAdInterstitial.setInterstitialAdListener(this);
            mCDAdInterstitial.setBundleIdentifier(sharedPreferences.getString("bundleId", ""));
            mCDAdInterstitial.setCategory(sharedPreferences.getString("cat", ""));
            mCDAdInterstitial.setTesting(true);
        }
        HashMap<String, String > map = new HashMap<>();
        map.put("gender", "male");
        map.put("age", "25");
        map.put("income", "100000");
        map.put("language", "en");
        mCDAdInterstitial.requestNewAd(this,map, "partnerId", "placementId");
    }

    private void clearAllViews(){
        if (mNativeAd!=null){
            mNativeAd.clear(mConstraintLayout);
            mConstraintLayout.setVisibility(View.INVISIBLE);
        }
        mLargeBannerView.setVisibility(View.INVISIBLE);
        mLargeBannerView.setAdAutoRefreshEnabled(false);
        mSmallBannerView.setVisibility(View.INVISIBLE);
        mSmallBannerView.setAdAutoRefreshEnabled(false);

        if (mCDAdInterstitial!=null){
            mCDAdInterstitial.destroy();
            mCDAdInterstitial = null;
        }
    }

    public void loadSmallBanner(View v){
        clearAllViews();
        HashMap<String, String > map = new HashMap<>();
        map.put("gender", "male");
        map.put("age", "25");
        map.put("income", "100000");
        map.put("language", "en");
        mSmallBannerView.setAdAutoRefreshEnabled(true);
        mSmallBannerView.requestNewAd(map, "partnerId", "placementId");

    }

    public void loadLargeBanner(View v){
        clearAllViews();
        HashMap<String, String > map = new HashMap<>();
        map.put("gender", "male");
        map.put("age", "25");
        map.put("income", "100000");
        map.put("language", "en");
        mLargeBannerView.setAdAutoRefreshEnabled(true);
        mLargeBannerView.requestNewAd(map, "partnerId", "placementId");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.settings).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.settings:
                startActivity(new Intent(SampleActivity.this, SettingsActivity.class));
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInterstitialAdRequest(CDAdInterstitial cdAdInterstitial) {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onInterstitialLoaded(CDAdInterstitial cdAdInterstitial) {
        mProgressBar.setVisibility(View.INVISIBLE);
        if (cdAdInterstitial.isReady()) {
            cdAdInterstitial.show();
            dismissIfAlertDialogVisible();
        }


    }

    @Override
    public void onInterstitialFailed(CDAdInterstitial cdAdInterstitial, CDAdErrorCode cdAdErrorCode) {
        mProgressBar.setVisibility(View.INVISIBLE);
        showAlertDialogWithMessage("Failed to load Interstitial Ad");
    }

    @Override
    public void onInterstitialShown(CDAdInterstitial cdAdInterstitial) {

    }

    @Override
    public void onInterstitialClicked(CDAdInterstitial cdAdInterstitial) {

    }

    @Override
    public void onInterstitialDismissed(CDAdInterstitial cdAdInterstitial) {

    }


    @Override
    public void onInterstitialVideoAdRequest(final CDAdVideoInterstitial videoInterstitial) {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onInterstitialVideoLoaded(final CDAdVideoInterstitial videoInterstitial) {
        mProgressBar.setVisibility(View.INVISIBLE);
        if (videoInterstitial.isReady()) {
            videoInterstitial.show();
            dismissIfAlertDialogVisible();
        }
    }

    @Override
    public void onInterstitialVideoFailed(final CDAdVideoInterstitial videoInterstitial, final CDAdErrorCode errorCode) {
        mProgressBar.setVisibility(View.INVISIBLE);
        showAlertDialogWithMessage("Failed to load Interstitial Video Ad");
    }


    @Override
    public void onInterstitialVideoShown(final CDAdVideoInterstitial videoInterstitial) {

    }

    @Override
    public void onInterstitialVideoClicked(final CDAdVideoInterstitial videoInterstitial) {

    }

    @Override
    public void onInterstitialVideoDismissed(final CDAdVideoInterstitial videoInterstitial) {

    }

    @Override
    public void onInterstitialVideoEnded(final CDAdVideoInterstitial videoInterstitial) {

    }

    @Override
    public void onBannerAdRequest(CDAdView cdAdView) {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBannerLoaded(CDAdView cdAdView) {
        mProgressBar.setVisibility(View.INVISIBLE);
        cdAdView.setVisibility(View.VISIBLE);
        dismissIfAlertDialogVisible();
    }

    @Override
    public void onBannerFailed(CDAdView cdAdView, CDAdErrorCode cdAdErrorCode) {
        mProgressBar.setVisibility(View.INVISIBLE);
        showAlertDialogWithMessage("Failed to receive "+(cdAdView==mLargeBannerView?"300x250":"320x50")+" Ad");
    }

    @Override
    public void onBannerClicked(CDAdView cdAdView) {

    }

    @Override
    public void onBannerExpanded(CDAdView cdAdView) {

    }

    @Override
    public void onBannerCollapsed(CDAdView cdAdView) {

    }

    @Override
    protected void onStop() {
        if (mCDAdInterstitial!=null){
            mCDAdInterstitial.destroy();
            mCDAdInterstitial = null;
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mSmallBannerView!=null){
            mSmallBannerView.destroy();
            mSmallBannerView = null;
        }
        if (mLargeBannerView!=null){
            mLargeBannerView.destroy();
            mLargeBannerView = null;
        }
        super.onDestroy();
    }

    private void dismissIfAlertDialogVisible(){
        if (alert11 != null && alert11.isShowing()){
            alert11.cancel();
        }
    }

    private void showAlertDialogWithMessage(String message){
        dismissIfAlertDialogVisible();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setNegativeButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void onNativeLoad(final NativeAd nativeAd) {
        mProgressBar.setVisibility(View.INVISIBLE);
        dismissIfAlertDialogVisible();
        View adView = nativeAd.createAdView(SampleActivity.this, mConstraintLayout);
        mNativeAd = nativeAd;
        nativeAd.prepare(adView);
        nativeAd.renderAdView(adView);
        mConstraintLayout.addView(adView);
        mConstraintLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNativeFail(final NativeErrorCode errorCode) {
        mProgressBar.setVisibility(View.INVISIBLE);
        showAlertDialogWithMessage("Failed to load Native Video Ad");
    }

    @Override
    public void onNativeVideoEnded(final BaseNativeAd nativeAd) {

    }

    @Override
    public void onNativeClicked(final BaseNativeAd nativeAd) {

    }
}
