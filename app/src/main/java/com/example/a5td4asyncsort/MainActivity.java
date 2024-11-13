package com.example.a5td4asyncsort;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    ExecutorService executorService;
    Handler handler;
    ProgressBar progressBar;
    int[] array;
    SortView sortView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        sortView = findViewById(R.id.sortView2);

        EditText editTextNumber = findViewById(R.id.editTextNumber);
        Button startBtn = findViewById(R.id.btnStart);

        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        startBtn.setOnClickListener(v -> {
            String sizeStr = editTextNumber.getText().toString();
                if (sizeStr.isEmpty()){
                    Toast.makeText(this, "Enter number of elements", Toast.LENGTH_SHORT).show();
                    return;
                }
            int size = Integer.parseInt(sizeStr);
            array = generateArray(size);
            progressBar.setProgress(0);

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    bubbleSort();
                }
            });




        });




    }

    private int[] generateArray(int size){
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100) + 1;
        }
        return array;
    }

    private void bubbleSort(){
        if(array.length<=1){
            handler.post(()->{
                sortView.setArray(array);
                progressBar.setProgress(100);
                Toast.makeText(this,"Sorting completed", Toast.LENGTH_SHORT).show();
            });
            return;
        }

        int n = array.length;
        int temp = 0;
        for(int i=0; i < (n - 1); i++){
            for(int j=0; j < ( n- i - 1); j++){
                if(array[j] > array[j + 1]){
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    // i * n => full cycles + j => partial cycle
                    int progress = (int) (((float) (i * n) / (n*n))*100);
                    handler.post(()->{
                        sortView.setArray(array.clone());
                        progressBar.setProgress(progress);
                    });

                    try {
                        Thread.sleep(6);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        handler.post(()->{
            sortView.setArray(array);
            progressBar.setProgress(100);
            Toast.makeText(this,"Sorting completed", Toast.LENGTH_SHORT).show();
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}