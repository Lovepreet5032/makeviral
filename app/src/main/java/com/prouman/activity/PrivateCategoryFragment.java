package com.prouman.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prouman.R;
import com.prouman.Util.ConfigURL;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.model.CategoryModel;
import com.prouman.view.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jcs on 12/7/2016.
 */
public class PrivateCategoryFragment extends Fragment {
    TextView tv1, tv2;

    private String catID,uproID;
    private String name;
    public List<CategoryModel> mCatList;

    private RecyclerView mVideoRecyclerView;

    private PrivateCategoryViewAdapter catViewAdapter;

    private ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    private RelativeLayout backToGuest;
    private RelativeLayout bottomLayout;
    private boolean isShowShareLayout;
    private ProgressBar loadProgress;

    private RelativeLayout shareLayout;
    private TextView title;
    private RelativeLayout topLayout;
    String hash;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View catView = inflater.inflate(R.layout.fragment_category, container, false);
        sharedPreferences =getActivity().getSharedPreferences("MyPref",0);
        uproID = sharedPreferences.getString(PrefrencesConstant.uproid,null);
        hash = sharedPreferences.getString(PrefrencesConstant.hash,null);
        backToGuest = (RelativeLayout)catView.findViewById(R.id.layout_back_to_guest);
        topLayout = ((RelativeLayout)catView.findViewById(R.id.layout_top));
        mVideoRecyclerView = (RecyclerView) catView.findViewById(R.id.category_recycler_view);

        mVideoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mVideoRecyclerView.addItemDecoration(itemDecoration);

        upDateUI();


        return catView;

    }

    private void upDateUI() {
        mCatList = new ArrayList<>();
        catViewAdapter =new PrivateCategoryViewAdapter(mCatList,getContext());
        getData();
        mVideoRecyclerView.setAdapter(catViewAdapter);
     /*   RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mVideoRecyclerView.setItemAnimator(new DefaultItemAnimator());*/

    }

    private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.Video_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success)
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("category");
                        for(int i = 0; i<jsonArray.length(); i++)
                        {
                            JSONObject innerObj = jsonArray.getJSONObject(i);
                            catID = innerObj.getString("id");
                            name = innerObj.getString("name");
                            int privateTag= innerObj.getInt("private");
                            CategoryModel catModel = new CategoryModel();
                            catModel.setCategeoryName(name);
                            catModel.setId(catID);
                            if (privateTag==1)
                            {
                                mCatList.add(catModel);
                            }



                        }

                        catViewAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String,String> params= new HashMap<>();
                params.put("upro_id",uproID);
                params.put("hash",hash);
                return  params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
}
