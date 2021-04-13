package com.example.kvizandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.skydoves.medal.MedalAnimation;
import com.skydoves.medal.MedalDirection;

public class Done extends AppCompatActivity {
    Button btnTryAgain;
    TextView txtResultScore, getTextResultQuestion,wrong;
    ImageView image;

    Button surprise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        image=findViewById(R.id.badge);



        txtResultScore = (TextView)findViewById(R.id.txtTotalScore);
        wrong=(TextView)findViewById(R.id.wrong);
        getTextResultQuestion = (TextView)findViewById(R.id.txtTotalQuestion);
        surprise=(Button)findViewById(R.id.surprise);



        btnTryAgain = (Button)findViewById(R.id.btnTryAgain);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Done.this, Playing.class);
                startActivity(intent);
                finish();
            }
        });

        // get data from bundle and set to view
        Bundle extra = getIntent().getExtras();
        if (extra != null)
        {
            int score = extra.getInt("SCORE");
            if(score>=2)
            {
                surprise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        image.setVisibility(View.VISIBLE);
                        MedalAnimation medalAnimation = new MedalAnimation.Builder()
                                .setDirection(MedalDirection.RIGHT)
                                .setDegreeX(360)
                                .setDegreeZ(360)
                                .setSpeed(3200)
                                .setTurn(4)
                                .setLoop(10)
                                .build();
                        ImageView imageView = findViewById(R.id.badge);
                        imageView.startAnimation(medalAnimation);
                    }
                });

            }

            int totalQuestion = extra.getInt("SCORE");
            int correctAnswer = extra.getInt("CORRECT");
            int wrongAnswer=extra.getInt("WRONG");

            txtResultScore.setText(String.format("SCORE : %d",score));
            getTextResultQuestion.setText(String.format("CORRECT : %d", correctAnswer));
            wrong.setText(String.format("WRONG: %d", wrongAnswer));








        }




    }
}