package com.document.enviadatos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import static java.net.Proxy.Type.HTTP;

public class MainActivity extends AppCompatActivity {

    EditText e1,e2;
    TextView t1,t2;
    ImageView img1;

    public static  String nombre;
    Button button1,button2,button3;
    private int CAMERA_PIC_REQUEST=1;
    public  static  String lat;
    public  static  String lon;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cargar();


        SharedPreferences sharedPreferences = getSharedPreferences("datos", MODE_PRIVATE);
        if (sharedPreferences!=null)
        {
            String correo=sharedPreferences.getString("name","");
            String pass=sharedPreferences.getString("ape","");
            e1.setText(correo);
            e2.setText(pass);
            t1.setText(lat);
            t2.setText(lon);

        }
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAMERA_PIC_REQUEST);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubicacion();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String nombre=e1.getText().toString();
               String apellido=e2.getText().toString();
               String latiud=t1.getText().toString();
               String longitud=t2.getText().toString();
                enviaardatos(nombre,apellido,latiud,longitud);
            }
        });


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putString("var", e1.getText().toString());

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        e1.setText(savedInstanceState.getString("var"));
    }
    private void ubicacion() {
        gurdardatos();
        startActivity(new Intent(MainActivity.this,Mapa.class));

    }

    public void  gurdardatos(){
        SharedPreferences preferences=getSharedPreferences("datos", Context.MODE_PRIVATE);
        String name=e1.getText().toString();
        String apellido=e2.getText().toString();
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("name",name);
        editor.putString("ape",apellido);
        editor.commit();
    }
    private void enviaardatos(String nombre, String apellido, String latiud, String longitud) {


        if (TextUtils.isEmpty(nombre)){
            Toast.makeText(this, "Falta nombre", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(apellido)){
            Toast.makeText(this, "falta apellido we", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(latiud)){
            Toast.makeText(this, "falta direccion we", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(longitud)){
            Toast.makeText(this, "falta direccion we", Toast.LENGTH_SHORT).show();
        }
        else if (   bitmap==null){
            Toast.makeText(this, "falta imagen we", Toast.LENGTH_SHORT).show();
        }
        else{


        String datos =nombre+","+apellido+","+latiud+","+longitud;

      //  Drawable mDrawable = mImageView.getDrawable();
        //Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Image Description", null);
        Uri uri = Uri.parse(path);

        //Intent intent = new Intent(Intent.ACTION_SEND);
        //intent.setType("image/jpeg");
        //intent.putExtra(Intent.EXTRA_STREAM, uri);
        //startActivity(Intent.createChooser(intent, "Share Image"));


        //bitmapDrawable = (BitmapDrawable) mImageView.getDrawable();// get the from imageview or use your drawable from drawable folder
      //  bitmap1 = bitmapDrawable.getBitmap();
        String imgBitmapPath= MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"title",null);
        Uri imgBitmapUri=Uri.parse(imgBitmapPath);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, datos);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imgBitmapUri);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share images"));
        }

      //  Intent emailIntent = new Intent(Intent.ACTION_SEND);
      //  emailIntent.setType("image/jpeg");
      //  emailIntent.putExtra(Intent.EXTRA_TEXT,nombre);
      //  emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
      //  if(null!=emailIntent.resolveActivity(getPackageManager())){
      //           startActivity(Intent.createChooser(emailIntent,getResources().getText(R.string.send_to)));
      //      }


        // Intent sendIntent = new Intent();
      // sendIntent.setAction(Intent.ACTION_SEND);
      // sendIntent.putExtra(Intent.EXTRA_TEXT,nombre);

      // ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
      // sendIntent.setType("image/jpeg");


      //// sendIntent.setType("text/plain");
      // if(null!=sendIntent.resolveActivity(getPackageManager())){
      //     startActivity(Intent.createChooser(sendIntent,getResources().getText(R.string.send_to)));
        //}
    }

    public static boolean checkPermission(final Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED; }


    private void cargar() {

        e1=(EditText)findViewById(R.id.idnombre);
        e2=(EditText)findViewById(R.id.idapellido);
        t1=(TextView)findViewById(R.id.idlatitud);
        t2=(TextView)findViewById(R.id.idlongitud);
        img1=(ImageView)findViewById(R.id.idimgfoto);
        button1=(Button)findViewById(R.id.idbtngaleria);
        button2=(Button)findViewById(R.id.idbtunicacion);
        button3=(Button)findViewById(R.id.idbenviar);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA_PIC_REQUEST){
            if(resultCode==RESULT_OK){
          bitmap    = (Bitmap)data.getExtras().get("data");

                img1.setImageBitmap(bitmap);
            }
        }
    }
}
