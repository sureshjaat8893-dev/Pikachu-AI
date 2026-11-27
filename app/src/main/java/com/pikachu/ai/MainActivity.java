
package com.pikachu.ai;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {

    private TextToSpeech voice;
    private TextView status;
    private static final int MIC = 101;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(1);
        layout.setPadding(25,40,25,25);
        layout.setGravity(17);
        layout.setBackgroundColor(0xFFFFF5D6);

        TextView title = new TextView(this);
        title.setText("⚡ PIKACHU AI 2.0");
        title.setTextSize(28);
        title.setGravity(17);
        layout.addView(title);

        status = new TextView(this);
        status.setText("जी मालिक! मैं तैयार हूँ।");
        status.setTextSize(20);
        status.setPadding(10,30,10,30);
        layout.addView(status);

        Button listen = new Button(this);
        listen.setText("🎤 बोलिए मालिक");
        layout.addView(listen);

        setContentView(layout);

        voice = new TextToSpeech(this, result -> {
            if(result == TextToSpeech.SUCCESS) {
                voice.setLanguage(new Locale("hi","IN"));
                voice.setPitch(0.80f);
                voice.setSpeechRate(0.90f);
                speak("जी मालिक! Pikachu तैयार है।");
            }
        });

        listen.setOnClickListener(v -> listenCommand());
    }

    private void speak(String text) {
        status.setText(text);
        if(voice != null) {
            voice.speak(text, TextToSpeech.QUEUE_FLUSH,
                null, "pikachu");
        }
    }

    private void listenCommand() {
        if(checkSelfPermission(Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                new String[]{Manifest.permission.RECORD_AUDIO}, MIC);
            return;
        }

        if(!SpeechRecognizer.isRecognitionAvailable(this)) {
            speak("मालिक, आवाज पहचानने की सुविधा उपलब्ध नहीं है।");
            return;
        }

        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "hi-IN");
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "जी मालिक, बोलिए");

        try {
            startActivityForResult(i, MIC);
        } catch(Exception e) {
            speak("मालिक, आवाज सुनने में परेशानी हुई।");
        }
    }

    @Override
    protected void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);

        if(request == MIC && result == RESULT_OK && data != null) {
            ArrayList<String> words =
                data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);

            if(words != null && !words.isEmpty()) {
                command(words.get(0));
            }
        }
    }

    private void command(String text) {
        String c = text.toLowerCase(Locale.ROOT);

        if(c.contains("कैमरा") || c.contains("camera")) {
            speak("जी मालिक! कैमरा खोल रहा हूँ।");
            open(new Intent("android.media.action.IMAGE_CAPTURE"));
        }
        else if(c.contains("म्यूजिक") || c.contains("youtube music")) {
            speak("जी मालिक! म्यूजिक खोल रहा हूँ।");
            app("com.google.android.apps.youtube.music");
        }
        else if(c.contains("व्हाट्सएप") || c.contains("whatsapp")) {
            speak("जी मालिक! WhatsApp खोल रहा हूँ।");
            app("com.whatsapp");
        }
        else if(c.contains("यूट्यूब") || c.contains("youtube")) {
            speak("जी मालिक! YouTube खोल रहा हूँ।");
            open(new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com")));
        }
        else if(c.contains("क्रोम") || c.contains("chrome")) {
            app("com.android.chrome");
        }
        else if(c.contains("कॉल") || c.contains("call")) {
            String number = text.replaceAll("[^0-9+]","");
            Intent i = number.length() >= 5
                ? new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+number))
                : new Intent(Intent.ACTION_DIAL);
            speak("जी मालिक! डायलर खोल रहा हूँ।");
            open(i);
        }
        else if(c.contains("सेटिंग")) {
            open(new Intent(android.provider.Settings.ACTION_SETTINGS));
        }
        else {
            speak("मालिक, यह कमांड अभी मेरे सिस्टम में नहीं है।");
        }
    }

    private void app(String name) {
        Intent i = getPackageManager().getLaunchIntentForPackage(name);
        if(i != null) open(i);
        else speak("मालिक, यह ऐप फोन में नहीं मिला।");
    }

    private void open(Intent i) {
        try {
            startActivity(i);
        } catch(Exception e) {
            speak("मालिक, यह काम अभी नहीं हो पाया।");
        }
    }

    @Override
    protected void onDestroy() {
        if(voice != null) {
            voice.stop();
            voice.shutdown();
        }
        super.onDestroy();
    }
}
