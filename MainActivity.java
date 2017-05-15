package com.avik1q.scehack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import net.atomation.atomationsdk.api.IMultiSenseAtom;
import net.atomation.atomationsdk.api.IOnButtonPressedListener;
import net.atomation.atomationsdk.api.ISensorsReadListener;
import net.atomation.atomationsdk.api.SensorsData;
import net.atomation.atomationsdk.ble.MultiSenseAtomManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SCE";
    public static final String MAC="48:1A:84:00:14:E1";
    public IMultiSenseAtom Atom;
    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w(TAG, "onCreate: ok go!!");
        Atom= MultiSenseAtomManager.getInstance(this.getApplicationContext())
                .getMultiSenseAtom(MAC);
        if(Atom==null)
        {
            Atom= MultiSenseAtomManager.getInstance(this.getApplicationContext())
                    .createMultiSenseDevice(MAC);
        }

        Atom.activate();
        Atom.setSensorsReadListener(new ISensorsReadListener() {
            @Override
            public void onRead(SensorsData sensorsData) {
                Log.w(TAG, "onRead() called with: sensorsData = [" + sensorsData + "]");
            }

            @Override
            public void onError(int errorCode) {
                //Log.d(TAG, "onError() called with: errorCode = [" + errorCode + "]");
            }
        });
        Atom.setButtonPressedListener(new IOnButtonPressedListener() {
            @Override
            public void onPress(int reason) {
                Log.w(TAG, "onPress() called with: reason = [" + reason + "]");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Atom.deactivate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Atom.activate();
    }
}
