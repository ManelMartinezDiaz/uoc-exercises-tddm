package edu.uoc.monuments.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import edu.uoc.library.LibraryManager;
import edu.uoc.library.model.Monument;
import edu.uoc.monuments.R;

import edu.uoc.library.calback.GetCallback;



public class AddMonumentActivity extends AppCompatActivity implements View.OnClickListener{

    //    private Button buttonInsert, buttonSelectImage;
    private ImageView mImage;
    private AutoCompleteTextView monument_nameView;
    private AutoCompleteTextView monument_countryView;
    private AutoCompleteTextView monument_descriptionView;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_monument);

        // Set button and image views
        Button buttonInsert = (Button) findViewById(R.id.buttonInsert);
        Button buttonSelectImage = (Button) findViewById(R.id.buttonSelectImage);
        mImage = (ImageView) findViewById(R.id.monument_image);

        // Set listeners als butons
        buttonInsert.setOnClickListener(this);
        buttonSelectImage.setOnClickListener(this);

        //Set textviews
        monument_nameView = (AutoCompleteTextView) findViewById(R.id.monument_name);
        monument_countryView = (AutoCompleteTextView) findViewById(R.id.monument_city);
        monument_descriptionView = (AutoCompleteTextView) findViewById(R.id.monument_description);

    }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonInsert:
                    setResult(RESULT_OK);
                    //Toast.makeText(this,"Select Insert!!! =",Toast.LENGTH_SHORT).show();

                    // Store values in the same type need by saveMonumentInBackground method.
                    String name = monument_nameView.getText().toString();
                    String country = monument_countryView.getText().toString();
                    String description = monument_descriptionView.getText().toString();

                    //The image must be a bitmap type
                    //To copy the image, we get a Drawable from the mImage, create a Bitmap that will contain our image.
                    //Initialise a canvas with the Bitmap created and Use a canvas taking bitmap (the copy of bitmap) as a parameter
                    Drawable mImageDrawable = ((ImageView) mImage).getDrawable();

                    Bitmap bitmap = null;
                    bitmap = Bitmap.createBitmap(mImageDrawable.getIntrinsicWidth(),mImageDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);

                    Canvas canvas = new Canvas(bitmap);
                    mImageDrawable.setBounds(0,0,canvas.getWidth(),canvas.getHeight());
                    mImageDrawable.draw(canvas);

                    LibraryManager.getInstance(getApplicationContext()).saveMonumentInBackground(name, country, description, bitmap, new GetCallback<Monument>() {
                        @Override
                        public void onSuccess(Monument monument) {
                            if (monument != null) {
                                 Toast.makeText(getApplicationContext(),"Monument Insert OK",Toast.LENGTH_SHORT).show();
                              //  monumentAdapter.notifyDataSetChanged();
                            }

                        }
                        @Override
                        public void onFailure(Throwable e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    //Use finish to retorn the previous activity
                    finish();
                    break;
                case R.id.buttonSelectImage:
                   // Toast.makeText(this,"Select Image!!! =",Toast.LENGTH_SHORT).show();
                    //When image captura, refresh image
                    dispatchTakePictureIntent();
                    //mImage.

                    break;
                case View.NO_ID:
                default:
                    // TODO Auto-generated method stub

                    break;
            }
       }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImage.setImageBitmap(imageBitmap);

        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

}
