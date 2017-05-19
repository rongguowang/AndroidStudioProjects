package com.linli.mobile.acitivty;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.linli.mobile.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AddAddressActivity extends AppCompatActivity {
    private ImageView mBack = null;
    private TextView mSave = null;
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowTitleEnabled(false);

        mBack = (ImageView) this.findViewById(R.id.add_address_header_back);
        mSave = (TextView) this.findViewById(R.id.add_address_header_save);
        qrScan = new IntentIntegrator(this);

        mBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                qrScan.setPrompt(getResources().getString(R.string.app_name));
                qrScan.setCaptureActivity(ScannerActivity.class).initiateScan();
            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                try {
                    String s = result.getContents();
                    Toast.makeText(this,"sacn result: " + s, Toast.LENGTH_LONG).show();
                } catch(Exception jse) {
                    jse.printStackTrace();
                }
            } else {
                Toast.makeText(this, "result null", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
