package org.zomatoapibuild.avijayvargiy.zomatoapibuild;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button b;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidNetworking.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        b = (Button) findViewById(R.id.button);
        tv = (TextView) findViewById(R.id.textView2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.get("https://developers.zomato.com/api/v2.1/categories")
                        .addHeaders("user-key", "de69b5e2ce1bb35eeda019f001bc378e")
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    tv.setText("Loading");
                                    String jsonarrayraw = response.getString("categories");
                                    JSONArray jsonarray = new JSONArray(jsonarrayraw);
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                                        String id = jsonobject.getJSONObject("categories").getString("id");
                                        String name = jsonobject.getJSONObject("categories").getString("name");
                                        Log.i("RESULT", id + " " + name);
                                        tv.setText(id + " " + name);
                                        Toast.makeText(MainActivity.this, id + " " + name, Toast.LENGTH_LONG ).show();
                                    }

                                } catch (JSONException e) {
                                    tv.setText("error in json");
                                }
                            }
                            @Override
                            public void onError(ANError anError) {
                               tv.setText(anError.getErrorBody());
                            }
                        });

            }
        });
    }
}
