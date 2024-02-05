package com.example.easydrug.Utils;

import static android.Manifest.permission.INTERNET;
import static com.example.easydrug.Configs.serviceRegion;
import static com.example.easydrug.Configs.speechSubscriptionKey;

import android.app.Activity;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisCancellationDetails;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;

public class SpeechUtil {
    private static String TAG = "SpeechUtil";
    private static SpeechConfig speechConfig;
    private static SpeechSynthesizer synthesizer;
    private static Thread thread;
    private static String curText;

    public static void speechText(Activity activity, String text) {
        // Note: we need to request the permissions
        int requestCode = 5; // unique code for the permission request
        ActivityCompat.requestPermissions(activity, new String[]{INTERNET}, requestCode);

        // Initialize speech synthesizer and its dependencies
        if (speechConfig == null) {
            speechConfig = SpeechConfig.fromSubscription(speechSubscriptionKey, serviceRegion);
        }

        if (synthesizer == null) {
            synthesizer = new SpeechSynthesizer(speechConfig);
        } else {
            synthesizer.StopSpeakingAsync();
        }


        thread = new Thread(() -> {
            try {
                Thread.sleep(100);

                if (curText != null && curText.equals(text)) {
                    curText = null;
                    return;
                }
                curText = text;
                // Note: this will block the UI thread, so eventually, you want to register for the event
                SpeechSynthesisResult result = synthesizer.SpeakText(text);
                assert(result != null);

                if (result.getReason() == ResultReason.SynthesizingAudioCompleted) {
                    Log.i(TAG, "Speech synthesis succeeded.");
                }
                else if (result.getReason() == ResultReason.Canceled) {
                    String cancellationDetails =
                            SpeechSynthesisCancellationDetails.fromResult(result).toString();
                    Log.i(TAG, "Error synthesizing. Error detail: " +
                            System.lineSeparator() + cancellationDetails +
                            System.lineSeparator() + "Did you update the subscription info?");
                }

                result.close();
            } catch (Exception ex) {
                Log.e("SpeechSDKDemo", "unexpected " + ex.getMessage());
                assert(false);
            }
        });
        thread.start();

    }

    // when exit activity
    public static void destroy() {
        curText = null;
        if (synthesizer != null) {
            synthesizer.StopSpeakingAsync();
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synthesizer.close();
                    synthesizer = null;

                }
            }).start();
        }
        if (speechConfig != null) {
            speechConfig.close();
            speechConfig = null;
        }
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }
}
