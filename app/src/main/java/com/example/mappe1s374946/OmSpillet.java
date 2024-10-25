package com.example.mappe1s374946;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class OmSpillet extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.omspillet);



        ImageButton tilbakeKnapp = findViewById(R.id.tilbakeKnapp);

        tilbakeKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
