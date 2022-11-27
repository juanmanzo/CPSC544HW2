package com.example.cpsc544hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    EditText triangleInput;
    Button enterButton;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enterButton = findViewById(R.id.button);
        output = findViewById(R.id.textView);
        triangleInput = findViewById(R.id.triangleInputs);
        triangleInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    validateInput(v);

                }
                return handled;
            }
        });

    }

    public void validateInput(View view){
        String triangleSizes = String.valueOf(triangleInput.getText());
        String[] sideLengths = triangleSizes.split(",");
        if (checkValues(sideLengths)){
            outputTriangle(sideLengths);
        }

    }

    private boolean checkValues(String[] sideLengths){

        if(sideLengths.length < 3){
            if(sideLengths.length == 1 && sideLengths[0].equals("0")){
                output.setText("Bye");

                try{
                    Thread.sleep(5000);
                }
                catch(Exception e){}
                finish();
                System.exit(0);
                return false;
            }
            output.setText("invalid input (need more input)");
            return false;
        }
        else if (sideLengths.length > 3){
            output.setText("invalid input (too many numbers)");
            return false;
        }
        else{
            return checkIndividualValues(sideLengths);
            /*
            for (String i : sideLengths){
                try{
                    Float.parseFloat(i);
                }
                catch(NumberFormatException ex){
                    output.setText("invalid input (need more input, at least one value is not a number)");
                    return false;
                }
            }
            output.setText("Looks Good");
            return true;
            */

        }

    }

    private boolean checkIndividualValues(String[] sideLengths){
        HashMap<Integer,String> map = new HashMap<>();
        map.putIfAbsent(0,"st");
        map.putIfAbsent(1,"nd");
        map.putIfAbsent(2,"rd");
        String outputString = "";
        boolean valid = true;
        for(int i = 0; i < sideLengths.length; i++){
            try{
                Float.parseFloat(sideLengths[i]);
            }
            catch(NumberFormatException ex){
                outputString += Integer.toString(i+1) + map.get(i) + " item is not a number.\n";
                valid = false;
            }
        }
        if(!valid){
            output.setText("Invalid input: " + outputString);
            return false;
        }

        for(int i = 0; i < sideLengths.length; i++){
            if (Float.parseFloat(sideLengths[i]) <= 0){
                outputString += Integer.toString(i+1) + map.get(i) + " number is too small.\n";
                valid = false;
            }
            else if (Float.parseFloat(sideLengths[i]) > 100) {
                outputString += Integer.toString(i + 1) + map.get(i) + " number is too big.\n";
                valid = false;
            }
        }
        if(!valid){
            output.setText("Invalid input: " + outputString);
            return false;
        }

        output.setText("Looks Good");
        return true;
    }

    private void outputTriangle(String[] sideLengths){
        String outputString = "";

        float[] intSideLengths = new float[sideLengths.length];
        for(int i = 0; i < sideLengths.length; i++){
            intSideLengths[i] = Float.parseFloat(sideLengths[i]);
        }
        //Check if valid triangle
        for(int i = 0; i < intSideLengths.length; i++){
            if((intSideLengths[i] + intSideLengths[(i+1) % 3]) < intSideLengths[(i+2) % 3]){
                output.setText("invalid input (input does not form a triangle)");
                return;
            }
        }



        if(Float.compare(intSideLengths [0],intSideLengths [1]) == 0) {
            if (Float.compare(intSideLengths[1],intSideLengths[2]) == 0)
                outputString = "Equilateral";
            else
                outputString = " Isosceles";
        } else if(Float.compare(intSideLengths[1],intSideLengths[2]) == 0) {
            outputString =  "Isosceles";
        }
        else if(Float.compare(intSideLengths[0],intSideLengths[2]) == 0) {
            outputString =  "Isosceles";
        }
        else {
            outputString =  "Scalene";
        }

        output.setText(outputString);




    }
}