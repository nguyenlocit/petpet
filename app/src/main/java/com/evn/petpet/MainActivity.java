package com.evn.petpet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Map<String, String> pets = new HashMap<>();
    ProgressBar imageLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        imageLoading = findViewById(R.id.imageLoading);

        displayPetImage("dog");

        findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String petName = ((EditText) findViewById(R.id.petName)).getEditableText().toString();
                if (petName.isEmpty()) {
                    return;
                }

                displayPetImage(petName);
            }
        });
    }

    void initData() {
        pets.put("dog", "https://images.squarespace-cdn.com/content/v1/5a7f26bf268b96210912ae2d/1522770168413-11SGHGU3890Z0Q2PW22P/ke17ZwdGBToddI8pDm48kLxnK526YWAH1qleWz-y7AFZw-zPPgdn4jUwVcJE1ZvWEtT5uBSRWt4vQZAgTJucoTqqXjS3CfNDSuuf31e0tVH33scGBZjC30S7EYewNF5iKKwhonf2ThqWWOBkLKnojuqYeU1KwPvsAK7Tx5ND4WE/cody_golden.jpg");
        pets.put("cat", "https://www.smithsstationah.com/imagebank/eVetSites/Feline/01.jpg");
        pets.put("rabbit", "https://assets.change.org/photos/4/nh/bb/HfNHbbpayJVOjKD-400x400-noPad.jpg?1530422195");
    }

    void displayPetImage(String name) {
        String petImage = getPetImageByName(name);
        if (petImage.isEmpty()) {
            return;
        }

        imageLoading.setVisibility(View.VISIBLE);
        Picasso.get()
                .load(petImage)
                .transform(new CircleTransform())
                .memoryPolicy(MemoryPolicy.NO_CACHE,
                        MemoryPolicy.NO_STORE)
                .into(((ImageView) findViewById(R.id.petImage)),
                        new Callback() {
                            @Override
                            public void onSuccess() {
                                imageLoading.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Exception e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
    }

    String getPetImageByName(String name) {
        return pets.get(name);
    }
}
