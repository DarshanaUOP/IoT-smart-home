package com.example.acer.hackethon2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends ActionBarActivity {
    EditText userName, password;
    TextView logging;
    String Name, Password;
    String value, mac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText) findViewById(R.id.et_UserName);
        password = (EditText) findViewById(R.id.etPassword);
        logging = (TextView) findViewById(R.id.txtLogin);
        nextWindow();
    }

    public void nextWindow() {
        logging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = userName.getText().toString(); //change the input into string and assign to variable
                Password = password.getText().toString();
                BackGround b = new BackGround();
                b.execute(Name, Password);
            }
        });
    }
    //class to do background task and

    class BackGround extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String name = (String) params[0];
            String password = (String) params[1];
            String data = "";
            int tmp;
            //...............
            // Creating JSON Parser object
            //JSONParser jParser = new JSONParser();
            try {
                URL url = new URL("http://10.30.4.2/tutorial/hello.php");
                //URL url = new URL("http://192.168.253.1/useuDb/hello.php");
                String urlParams = "name=" + name + "&" + "password=" + password;

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
     //@Override
                @Override
                        public void onPostExecute(String s) {

                            String err = null;
                            try {
                                JSONObject root = new JSONObject(s);
                                JSONObject data = root.getJSONObject("data");
                                value = data.getString("value");
                                mac = data.getString("mac");
                                Toast.makeText(MainActivity.this,"Recieved : "+value+" mac : "+mac,Toast.LENGTH_SHORT).show();

                    }catch (JSONException e) {
                        e.printStackTrace();
                        err = "Exception:3 " + e.getMessage();
                        Toast.makeText(MainActivity.this,err,Toast.LENGTH_SHORT).show();
                    }
                        //if(err.equals("null")) {

                       //Implement loging activity here;

                //if (value.equals("true")){
                if (true){
                        try{
                            //Intent showDetails = new Intent(MainActivity.this,ShowDetails.class);
                            //startActivity(showDetails);
                        }catch (Exception e1){
                            Toast.makeText(MainActivity.this,e1.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                }else{
                    Intent showDetails = new Intent(MainActivity.this,ShowDetails.class);
                    showDetails.putExtra("mac", mac);
                    startActivity(showDetails);

                    Toast.makeText(MainActivity.this,"Incorrect Username Or Password",Toast.LENGTH_SHORT).show();
                }

                }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

