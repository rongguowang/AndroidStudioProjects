package edu.cs.asr;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.EngineInfo;
import android.speech.tts.UtteranceProgressListener;

import java.util.ArrayList;
import java.util.List;

public class ASRActivity extends AppCompatActivity {
    private ImageButton mSpeak = null;
    private TextView mText = null;
    private int RECOGNIZER_INTENT_RESULT_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSpeak = (ImageButton)this.findViewById(R.id.start_button);
        mText = (TextView)this.findViewById(R.id.result_text);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        List<ResolveInfo> infolist = this.getPackageManager().queryIntentActivities(intent, 0);

        int i = 0;
        if (i != 0) {
            mSpeak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "hello, what to do now?");
                    startActivityForResult(intent, RECOGNIZER_INTENT_RESULT_CODE);
                }
            });
        } else {
            mSpeak.setEnabled(false);
            intent = new Intent(RecognitionService.SERVICE_INTERFACE);
            PackageManager pm = this.getPackageManager();
            ResolveInfo info = pm.resolveService(intent, PackageManager.GET_META_DATA);
            ResolveInfo aInfo = pm.resolveActivity(intent, PackageManager.GET_META_DATA);
            boolean sInfoFlag = info != null && info.serviceInfo != null;
            boolean aInfoFlag = aInfo != null && aInfo.activityInfo != null;
            if (sInfoFlag == true && aInfoFlag == false) {
                String text = new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name).flattenToString();
                mText.setText(text);
            } else if (aInfoFlag == true && sInfoFlag == false) {
                String text = new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name).flattenToString();
                mText.setText(text);
            } else if (aInfoFlag && sInfoFlag) {
                String text1 = new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name).flattenToString();
                String text2 = new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name).flattenToString();
                mText.setText(text1 + text2);
            } else {
                mText.setText("No Recognizer Service Found");
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent intent) {
        if(requestcode == RECOGNIZER_INTENT_RESULT_CODE && resultcode == RESULT_OK) {
            ArrayList<String> results = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < results.size(); i++) {
                sb.append(results.get(i).toString() + "\n");
            }
            sb.append("Total " + results.size() + " results found");
            mText.setText(sb.toString());
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_asr, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
