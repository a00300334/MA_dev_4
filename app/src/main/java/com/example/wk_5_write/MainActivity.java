package com.example.wk_5_write;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.Manifest;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 123;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            String[] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE

            };

            ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    STORAGE_PERMISSION_CODE
            );
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == STORAGE_PERMISSION_CODE){
            // this is handling the Storage request

            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // user grantet permission for WRITE_EXTERNAL_STORAGE
                recreate();
            }else{
                //user denied the permission
                Toast.makeText(this, "This app require Storage, please allow it.", Toast.LENGTH_LONG).show();
                finishAndRemoveTask();
            }
        }
    }


    public void increase(View view) {
        //increase counter
        counter ++;

        // check if exist and open file
        File folder = new File(Environment.getExternalStorageDirectory(),"CounterExample");

        // If folder does not exist
        if(!folder.exists()){
            folder.mkdir();
        }

        // write to the file (timestamp, counter)
        File file = new File(folder, "counter.csv");

        try {
            FileWriter writer = new FileWriter(file, true);
            writer.append(""+System.currentTimeMillis()).append(",").append(""+counter).append("\n");
            writer.flush();  // flash buffer to the file
            writer.close(); // closes the stream
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}

