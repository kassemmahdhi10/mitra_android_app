package com.example.testing_gps_services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.Result;
import com.mapbox.mapboxsdk.maps.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import testing.gps_service.R;
import ua.naiksoftware.stomp.client.StompClient;

import static android.Manifest.permission.CAMERA;





//import okhttp3.Request;
//import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private Button btn_start, btn_stop;
    private TextView textView;
    private BroadcastReceiver broadcastReceiver;
    public MapView mapView;
    String error;
    private static final String GEOJSON_SOURCE_ID = "GEOJSON_SOURCE_ID";
    private static final String MARKER_IMAGE_ID = "MARKER_IMAGE_ID";
    private static final String MARKER_LAYER_ID = "MARKER_LAYER_ID";
    private static final String CALLOUT_LAYER_ID = "CALLOUT_LAYER_ID";
    private static final String PROPERTY_SELECTED = "selected";
    public static List<DataInfoCommande> commandesList = new ArrayList<>();
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    androidx.appcompat.widget.Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
  public  int currentapiVersion = android.os.Build.VERSION.SDK_INT;
    StompClient mStompClient;
    String TAG="LongOperation";

    @Override
    protected void onResume() {
        super.onResume();








        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, final Intent intent) {


                    final Double value1 = (Double) intent.getExtras().get("longtitude");
                    final Double value2 = (Double) intent.getExtras().get("Latitude");

                    // textView.append("\n" +intent.getExtras().get("longtitude"));
                    // textView.append("\n" +intent.getExtras().get("Latitude"));
                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                    String URL = "http://192.168.4.99:8988/positionsgps";
                    final StringRequest objectRequest = new StringRequest(
                            Request.Method.POST,
                            URL,

                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }

                    ){
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String,String> params=new HashMap<String,String>();

                            params.put("id",getIntent().getStringExtra("id"));

                            params.put("longtitude", value1.toString());
                            params.put("Latitude", value2.toString());

                            return params;
                        }
                        @Override
                        public Map<String,String> getHeaders()throws AuthFailureError{
                            Map<String,String> params= new HashMap<String, String>();
                            params.put("content-type","application/x-www-form-urlencoded;charset=utf-8");
                            return params;
                        }
                    };


                    requestQueue.add(objectRequest);

                }

            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }






    private boolean checkPermission() {
        return ( ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA ) == PackageManager.PERMISSION_GRANTED);
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }





    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 //GetAllCommande();
      /*  stompClient = new StompClient("http://localhost:8988/gs-guide-websocket") { //example "ws://localhost:8080/message-server"
            @Override
            protected void onStompError(String errorMessage) {
               // Log.d(TAG, "error : " + errorMessage);
            }

            @Override
            protected void onConnection(boolean connected) {
               // Log.d(TAG, "connected : " + String.valueOf(connected));
            }

            @Override
            protected void onDisconnection(String reason) {
               // Log.d(TAG, "disconnected : " + reason);
            }

            @Override
            protected void onStompMessage(final Frame frame) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), frame.getBody(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        stompClient.subscribe("topic/notifications.2");
        stompClient.sendMessage("/app/2","hellooooo"); */

      //  mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "http://192.168.4.109:8988/gs-guide-websocket");
      //  mStompClient.connect();

      //  mStompClient.topic("/topic/notifications.2").subscribe(topicMessage -> {
      //      Log.d(TAG, topicMessage.getPayload());
      //  });

       // mStompClient.send("/app/2", "My first STOMP message!").subscribe();

        // ...

     //   mStompClient.disconnect();

        //  connectStomp();
        //kacem bar

        //Mapbox declaration
        if(!runtime_permissions()) {
            Intent i =new Intent(getApplicationContext(),GPS_Service.class);
            startService(i);


            setContentView(R.layout.activity_main);
            drawerLayout = findViewById(R.id.drawer);


            //kacem bar
            toolbar= (Toolbar) findViewById(R.id.toolbar);
            actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
      if(drawerLayout.isDrawerOpen(Gravity.END)){

          drawerLayout.closeDrawer(Gravity.START);
      }

          else
      {
          drawerLayout.openDrawer(Gravity.LEFT);

      }
                }
            });





            navigationView = findViewById(R.id.navigationView);
            navigationView.setNavigationItemSelectedListener(this);



            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
             getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            actionBarDrawerToggle.syncState();

            //load default fragment
            /*
           fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString("id", getIntent().getStringExtra("id"));
            bundle.putString("Token",getIntent().getStringExtra("Token"));
            MapsFragment mainFragment = new MapsFragment();


            mainFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.container_fragment,mainFragment);
            fragmentTransaction.commit();
*/





        }else{

            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }


    }






    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
            }else {
                runtime_permissions();
            }
        }else if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }






    }

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // do something on back.o
          //  Toast.makeText(getApplicationContext(),"Back",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }*/

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    Toast.makeText(getApplicationContext(),"Répéter le scan",Toast.LENGTH_LONG).show();

                    break;
            }
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(menuItem.getItemId() == R.id.map)
        {
            getSupportActionBar().setTitle("Voir sur Map");
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString("id", getIntent().getStringExtra("id"));
            bundle.putString("Token",getIntent().getStringExtra("Token"));
            MapsFragment mapsFragment = new MapsFragment();


            mapsFragment.setArguments(bundle);
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, mapsFragment);
            fragmentTransaction.commit();
        }


            if(menuItem.getItemId() == R.id.commandes)

            {
                //GetAllCommande();
                getSupportActionBar().setTitle("Mes commandes");
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("id", getIntent().getStringExtra("id"));
                bundle.putString("Token",getIntent().getStringExtra("Token"));
                FragmentCommandes fragmentCommandes = new FragmentCommandes();


                fragmentCommandes.setArguments(bundle);
                fragmentManager=getSupportFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment, fragmentCommandes);
                fragmentTransaction.commit();
           }
        if(menuItem.getItemId() == R.id.scan)

        {
//Toast.makeText(getApplicationContext(),"Access Scanner",Toast.LENGTH_LONG).show();



            if (checkPermission()) {
                if(mScannerView == null) {
                    mScannerView = new ZXingScannerView(this);
                    setContentView(mScannerView);
                }
                mScannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
                    @Override
                    public void handleResult(Result rawResult) {
                        final String result = rawResult.getText();
                        Log.d("QRCodeScanner", rawResult.getText());
                        Log.d("QRCodeScanner", rawResult.getBarcodeFormat().toString());

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Confirmer code : "+result).setPositiveButton("Oui", dialogClickListener)
                                .setNegativeButton("Non", dialogClickListener).show();



                    }
                });
                mScannerView.startCamera();
            } else {
                requestPermission();
            }








            /*fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new FragmentOne());

            fragmentTransaction.commit();*/
        }
        if(menuItem.getItemId() == R.id.logout)

        {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        return true;
    }
    private void GetAllCommande() {

        Thread th=new Thread(){
            @Override
            public void run() {
                super.run();

                Log.e("data","access to Get all commande");
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                String URL = "http://192.168.4.99:8988/livreurs/commandes/" +getIntent().getStringExtra("id");
                CustomStringRequest stringRequest = new CustomStringRequest(
                        Request.Method.GET,
                        URL,

                        new Response.Listener<CustomStringRequest.ResponseM>() {

                            @Override
                            public void onResponse(CustomStringRequest.ResponseM response) {

                                //From here you will get headers
                                ArrayList<DataInfoCommande> valuesCommande=new ArrayList<>();
                                String responseString = response.response;
                                JSONArray jArray = null;
                                try {
                                    jArray = new JSONArray(responseString);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                for (int i = 0; i < jArray.length(); i++) {

                                    JSONObject json_data = null;
                                    JSONObject json_client=null;
                                    JSONObject json_societe=null;
                                    // int idCommande;
                                    // String type;
                                    try {
                                        json_data = jArray.getJSONObject(i);
                                        json_client=json_data.getJSONObject("client");
                                        json_societe=json_client.getJSONObject("societe");
                                        json_data = jArray.getJSONObject(i);
                                        // json_client = json_data.getJSONObject("client");

                                        // idCommande = json_data.getInt("id");
                                        //  type = json_data.getString("type");
                                        // Log.i("Data",json_societe.getString("latitude")+"  "+json_societe.getString("longtitude"));
                                        // valuesCommandes.add(new DataInfoCommande(json_client.getString("nom"), json_client.getString("prenom"), idCommande, type));
                                        //  commandesList.add(new DataInfoCommande(  "kasem", "mahdhi",1,"jou")) ;
                                        //  valuesCommande.add(new DataInfo(json_societe.getDouble("latitude"),json_societe.getDouble("longtitude"),json_client.getString("nom")+" "+ json_client.getString("prenom"),json_client.getString("telephone"),json_client.getInt("id")));
                                        commandesList.add(new DataInfoCommande(json_societe.getString("latitude"),json_societe.getString("longtitude"),   json_client.getInt("id"),json_client.getString("telephone")));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }


                                for(int j=0;j<commandesList.size();j++){

                                    Log.d("Aff",commandesList.get(j).toString());


                                }
                                Log.d("Aff",commandesList.size()+"    ----    "+commandesList.size());




                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Check your Information", Toast.LENGTH_LONG).show();

                            }
                        }

                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();


                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("content-type", "application/x-www-form-urlencoded;charset=utf-8");

                        Log.e("token",getIntent().getStringExtra("Token") );
                        params.put("Authorization", "Bearer " + getIntent().getStringExtra("Token"));


                        return params;
                    }
                };


                requestQueue.add(stringRequest);

                //commandesList.add(new DataInfoCommande(  "kasem", "mahdhi",1,"jou")) ;
                //DataInfoCommandeAdapter adapter = new DataInfoCommandeAdapter(getActivity(), commandesList);
                // recyclerView.setAdapter(adapter);
                // recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }


        };
        th.start();
    }
    private void connectStomp() {
        // replace your websocket url
       // mStompClient = Stomp.over(WebSocket.class,"http://192.168.4.109:8988/gs-guide-websocket");
                //Stomp.over(Stomp.ConnectionProvider.OKHTTP, "http://192.168.4.109:8988/gs-guide-websocket");
        mStompClient.connect();

        mStompClient.topic("/topic/notifications.2").subscribe(topicMessage -> {
            Log.d(TAG, topicMessage.getPayload());
        });

        mStompClient.send("/app/2", "My first STOMP message!").subscribe();


        mStompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {

                case OPENED:
                    Log.d(TAG, "Stomp connection opened");
                    break;

                case ERROR:
                    Log.e(TAG, "Error", lifecycleEvent.getException());
                    break;

                case CLOSED:
                    Log.d(TAG, "Stomp connection closed");
                    break;
            }
        });





    }
}