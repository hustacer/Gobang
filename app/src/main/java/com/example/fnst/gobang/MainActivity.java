package com.example.fnst.gobang;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
    private GobangPanel gobangPanel;
    private ImageView restartImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);

        gobangPanel = (GobangPanel) findViewById(R.id.id_gobang);
        restartImageView = (ImageView)findViewById(R.id.restart_click);

        restartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gobangPanel.reStart();
        }
        });
    }
}
