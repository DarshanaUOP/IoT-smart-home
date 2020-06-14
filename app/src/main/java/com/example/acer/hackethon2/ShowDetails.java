package com.example.acer.hackethon2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import adapters.PluggingsAdapter;
import entities.Pluggings;


public class ShowDetails extends ActionBarActivity {
    private ListView listOfDetails;
    private List<Pluggings> detail_view;
    public int k, h;
    String mac, Mac, device, status, on_off;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        detail_view = new ArrayList<Pluggings>();


        //Here get details from db and set "Product name " and other details
        /**
         *this.detail_view.add(new Pluggings("Product Name", R.mipmap.ic_turn_on, true));
         */
        //while()
        //..............
        Mac = getIntent().getStringExtra(mac);
        BackGround b = new BackGround();
        b.execute(Mac);
        this.listOfDetails = (ListView) findViewById(R.id.listView_details);
        this.listOfDetails.setAdapter(new PluggingsAdapter(this, detail_view));

        //Toast.makeText(ShowDetails.this,"AAAAAAAA",Toast.LENGTH_SHORT).show();
        showMassages("Loading");

        this.listOfDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //make request for on or off
                showMassages(detail_view.get(position).getProductName() + " clicked ");

            }
        });
    }


//class to do background task and

    class BackGround extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            String mac = (String) params[0];
            String data = "";
            int tmp;
            //...............
            // Creating JSON Parser object
            //JSONParser jParser = new JSONParser();
            try {
                URL url = new URL("http://10.30.4.2/tutorial/devices.php");
                //URL url = new URL("http://192.168.253.1/useuDb/hello.php");
                String urlParams = "mac=" + mac;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                StringBuilder sb = new StringBuilder();
                while ((tmp = is.read()) != -1) {                    //.............

                    sb.append((char) tmp);
                    data = sb.toString();
                }

                is.close();
                httpURLConnection.disconnect();

                return data;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception1: " + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception2: " + e.getMessage();
            }
        }


        @Override
        public void onPostExecute(String s) {

            String err = null;
            try {
                JSONObject root = null;
                try {
                    root = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int h = 1;
                while (h < 3) {
                    String data1 = String.valueOf(h);
                    JSONObject data = root.getJSONObject(data1);
                    String device = data.getString("device");
                    String status = data.getString("status");
                    String on_off = data.getString("on_off");

                    showMassages("Recieved : " + device + " status : " + status);

                    detail_view.add(new Pluggings(device, getImage(status), on_off_state(on_off)));
                }

                ShowDetails.this.listOfDetails.setAdapter(new PluggingsAdapter(ShowDetails.this, detail_view));

                //catch(JSONException e){
                //  e.printStackTrace();
                // err = "Exception:3 " + e.getMessage();
                // Toast.makeText(MainActivity.this, err, Toast.LENGTH_SHORT).show();
                // }
                //this.detail_view.add(new Pluggings("Product 2",getImage(true),false));
                //this.detail_view.add(new Pluggings("Product 3",getImage(false),true));
                //this.detail_view.add(new Pluggings("Product 4",getImage(true),true));
                //this.detail_view.add(new Pluggings("Product 5",getImage(true),true));
                //this.detail_view.add(new Pluggings("LED",getImage(false),true));

            } catch (JSONException e) {
                showMassages(e.getMessage());
                e.printStackTrace();
            }
        }

        }

        public void showMassages(String massage) {
            Toast.makeText(ShowDetails.this, massage, Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_show_details, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            if (id == R.id.action_settings) {
                //noinspection SimplifiableIfStatement
                LayoutInflater layoutInflater = LayoutInflater.from(this);
                View mView = layoutInflater.inflate(R.layout.add_new_device, null);
                //..............................
                final EditText txtDevicePinMunmer = (EditText) mView.findViewById(R.id.et_device_pin_number);
                final EditText txtDeviceName = (EditText) mView.findViewById(R.id.et_device_name);

                AlertDialog.Builder AlertDialogbuilderAddNewDevice =
                        new AlertDialog.Builder(this);

                AlertDialogbuilderAddNewDevice.setView(mView);
                AlertDialogbuilderAddNewDevice.setCancelable(false)
                        .setIcon(R.mipmap.ic_addnew)
                        .setTitle("New Device")
                        .setPositiveButton("Verify", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //code for register
                                showMassages("Verifying");
                                try {
                                    Thread.sleep(500);
                                } catch (Exception e) {
                                }
                                showMassages("Item " + txtDevicePinMunmer.getText() + " added to your list.");

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }

            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        public int getImage(String is_on_off) {
            int imageInt;
            if (is_on_off.equals("true")) {
                imageInt = R.mipmap.ic_turn_on;
            } else {
                imageInt = R.mipmap.ic_turn_off;
            }
            return imageInt;
        }

        public boolean on_off_state(String io_state){
            boolean is_on_or_off;
            if (io_state.equals("true")){
                is_on_or_off=true;
            }else {
                is_on_or_off=false;
            }
            return is_on_or_off;
        }

}
