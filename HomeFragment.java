package com.example.myapplication;
import static java.lang.Double.parseDouble;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class HomeFragment extends Fragment implements View.OnClickListener {
    Context context;
    Admission admission;
    TextView textADM;
    EditText textGre, textToefl,textRating,textCgpa;
    public HomeFragment() {}
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("ADMIT ACHIEVE");
        admission = new Admission();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        this.context = container.getContext();
        textGre = view.findViewById(R.id.textGre);
        textToefl = view.findViewById(R.id.textToefl);
        textRating = view.findViewById(R.id.textRating);
        textCgpa = view.findViewById(R.id.textCgpa);
        textADM = view.findViewById(R.id.textADM);

        Button btnSubmit = view.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        return view;
    }

    private void GetPredictProbability() {

        // Build WebAPI URL
        String url = "http://3.208.153.195/predictProbability?";
        url += "GRE=" + admission.GRE + "&";
        url += "TOEFL=" + admission.TOEFL + "&";
        url += "Rating=" + admission.Rating + "&";
        url += "CGPA=" + admission.CGPA;
// creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(context);
// make json array request and then extracting data from each json object.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject responseObj = response.getJSONObject(0);
                            String returnValue = responseObj.getString("prediction");
                            Double val = parseDouble((returnValue));
                            Double percent = (100 * val);
                            textADM.setText("prediction:You have " + String.format("%.1f",(percent))+"% chance of Admission");
                            Toast.makeText(context, returnValue, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonArrayRequest);
    }
        @Override
        public void onClick(View view){
            switch (view.getId()) {
                case R.id.btnSubmit:
                    admission.GRE= textGre.getText().toString();
                    admission.TOEFL = textToefl.getText().toString();
                    admission.Rating = textRating.getText().toString();
                    admission.CGPA=textCgpa.getText().toString();

                    GetPredictProbability();
                    textADM.setText("wait..");
                    break;
            }

    }
}