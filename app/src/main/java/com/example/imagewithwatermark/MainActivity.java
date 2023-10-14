package com.example.imagewithwatermark;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextWatermark;
    private ImageView imageView;
    private Button buttonApplyWatermark;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextWatermark = findViewById(R.id.editTextWatermark);
        imageView = findViewById(R.id.imageView);
        buttonApplyWatermark = findViewById(R.id.buttonApplyWatermark);

        editTextWatermark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                applyWatermark();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void applyWatermark() {
        String watermarkText = editTextWatermark.getText().toString();

        // Load your original image here (you can load it from resources, file, URL, etc.)
        Bitmap originalBitmap = loadOriginalImage();

        if (originalBitmap != null) {
            // Create a new Bitmap for the watermarked image
            Bitmap watermarkedBitmap = Bitmap.createBitmap(
                    originalBitmap.getWidth(),
                    originalBitmap.getHeight(),
                    originalBitmap.getConfig()
            );

            // Create a Canvas to draw the watermarked image
            Canvas canvas = new Canvas(watermarkedBitmap);

            // Draw the original image
            canvas.drawBitmap(originalBitmap, 0, 0, null);

            // Create a Paint object for the watermark text
            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#CCCCCC")); // Set text color (light gray)
            paint.setTextSize(20); // Set text size
            paint.setAntiAlias(true); // Enable anti-aliasing
            float textWidth = paint.measureText(watermarkText);

            // Calculate the position to start drawing watermarks
            float watermarkPadding = textWidth + 10; // Adjust the padding as needed
            float watermarkSpacing = textWidth + 10; // Adjust the spacing between watermarks as needed
             int rotationDegree = -30;
            Log.d("dbharry", "textWidth = " + textWidth);

            float x = watermarkPadding;
            float y = watermarkPadding;

            // canvas.rotate(rotationDegree, 130, 200);

            // Loop to draw watermarks across the entire image
            while (y < watermarkedBitmap.getHeight()) {
                canvas.save();

                canvas.rotate(rotationDegree, x, y);

                canvas.drawText(watermarkText, x, y, paint);

                canvas.restore();

                x += watermarkSpacing;

                if (x + paint.measureText(watermarkText) > watermarkedBitmap.getWidth()) {
                    x = watermarkPadding;
                    y += watermarkSpacing;
                }
            }

            // Set the watermarked image to the ImageView
            imageView.setImageBitmap(watermarkedBitmap);
        }
    }

    // Replace this with code to load your original image from your desired source
    private Bitmap loadOriginalImage() {
        // For example, you can load an image from resources:
        return BitmapFactory.decodeResource(getResources(), R.drawable.image);
    }
}