package com.example.testing_gps_services;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import testing.gps_service.R;

public class FragmentCommandes extends Fragment {
    public List<DataInfoCommande> commandesList = new ArrayList<>();
    public RecyclerView recyclerView;
    public View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //GetAllCommande();
      //  commandesList=MainActivity.commandesList;
    }




    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.fragment_commandes, container, false);



        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);


















        //commandesList.add(new DataInfoCommande(  "kasem", "mahdhi",1,"jou")) ;
        //DataInfoCommandeAdapter adapter = new DataInfoCommandeAdapter(getActivity(), commandesList);
        // recyclerView.setAdapter(adapter);
        // recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));





//for(int i=0;i<=10;i++)
   //     commandesList.add(new DataInfoCommande(  "kasem", "mahdhi",1,"jou")) ;


      /* commandesList.add(
                new DataInfoCommande(
                        "1",
                        "Microsoft Surface Pro 4 Core m3 6th Gen - (4 GB/128 GB SSD/Windows 10)",
                        13  ,
                        "4.3"

                        ));

*/ GetAllCommande();
        Thread thr=new Thread(){
            @Override
            public void run() {
                try {
                                     sleep(2000);



                    getActivity().runOnUiThread(new Runnable(){
                        public void run(){
                            Toast.makeText(getActivity().getApplicationContext(),"Sman"+ commandesList.size(),Toast.LENGTH_LONG).show();
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            DataInfoCommandeAdapter adapter = new DataInfoCommandeAdapter(getActivity(), commandesList);

                            //setting adapter to recyclerview

                            recyclerView.setAdapter(adapter);


                        }

                    });




                    // manage other components that need to respond
                    // to the activity lifecycle

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        };
thr.start();



        //creating recyclerview adapter
        return view;
    }


    private void GetAllCommande() {

        Thread th=new Thread(){
            @Override
            public void run() {
                super.run();

                Log.e("data","access to Get all commande");
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                String URL = "http://192.168.4.99:8988/livreurs/commandes/" + getArguments().getString("id");
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
                                Toast.makeText(getActivity(), "Check your Information", Toast.LENGTH_LONG).show();

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

                        Log.e("token", getArguments().getString("Token"));
                        params.put("Authorization", "Bearer " + getArguments().getString("Token"));


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




}