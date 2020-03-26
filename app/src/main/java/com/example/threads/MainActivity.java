package com.example.threads;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private TextView tv_random_string;
    private Thread thread;
    private boolean keepRunning = true;
    private JokeService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_random_string = findViewById(R.id.tv_random_joke);
        service = new JokeManager().getJokeService();
        showRandomJoke();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        keepRunning = false;
        thread.interrupt();
    }

    private void showRandomJoke() {
        thread = new Thread(() -> {
            while (keepRunning) {
                Call<Joke> joke = service.randomJoke();
                try {
                    String newJoke = joke.execute().body().value.joke;
                    if (newJoke != null) {
                        tv_random_string.post(() -> {
                            tv_random_string.setText(newJoke);
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(8000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

}
