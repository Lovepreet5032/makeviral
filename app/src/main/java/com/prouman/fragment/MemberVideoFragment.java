package com.prouman.fragment;

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
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
import com.prouman.adapter.PrivateVideoAdapter;
import com.prouman.model.PrivateVideoModel;
import com.prouman.view.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.prouman.R.id.back_btn;

/**
 * Created by dsingh on 9/1/2016.
 */
public class MemberVideoFragment extends Fragment {
    private RecyclerView mVideoRecyclerView;
    private List<PrivateVideoModel> mVideos;
    private PrivateVideoAdapter videoViewAdapter;
    private static final String catID = "video";
     ImageView backBtn;
    private ProgressDialog progressDialog;
  String mCatID,uproID,hash;
    private RelativeLayout topLayout;
    private RelativeLayout backToGuest;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mCatID = getArguments().getString(catID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.video_layout, container, false);
        SharedPreferences sharedPreferences =getActivity().getSharedPreferences("MyPref",0);
        uproID = sharedPreferences.getString(PrefrencesConstant.uproid,null);
        hash= sharedPreferences.getString(PrefrencesConstant.hash,null);
        backToGuest = (RelativeLayout)view.findViewById(R.id.layout_back_to_guest);
        topLayout = ((RelativeLayout)view.findViewById(R.id.layout_top));
        backBtn =(ImageView)view.findViewById(back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                        getActivity().finish();


            }
        });
        mVideoRecyclerView = (RecyclerView) view.findViewById(R.id.video_recycler_view);

        mVideoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //handler = new DBHandler(getActivity());


        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mVideoRecyclerView.addItemDecoration(itemDecoration);

        updateUI();
       /* RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mVideoRecyclerView.setItemAnimator(new DefaultItemAnimator());*/
        return view;
    }

    private void updateUI() {
        //mVideos = handler.getAllVideo();

        mVideos = new ArrayList<>();
        videoViewAdapter = new PrivateVideoAdapter(mVideos, getActivity());
        getVideo();
        mVideoRecyclerView.setAdapter(videoViewAdapter);


    }

    private void getVideo() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.Video_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                       /* JSONArray jsonArray = jsonObject.getJSONArray("category");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject innerObj = jsonArray.getJSONObject(i);
                            catID = innerObj.getString("id");
                            //name = innerObj.getString("name");


                        }*/
                        JSONArray videoArray = jsonObject.getJSONArray("videos");
                        for (int j = 0; j < videoArray.length(); j++) {
                            PrivateVideoModel model = new PrivateVideoModel();
                            JSONObject videoObj = videoArray.getJSONObject(j);
                            String vId = videoObj.getString("id");
                            String vTitle = videoObj.getString("title");
                            String vDesc = videoObj.getString("description");
                            String vSource = videoObj.getString("source");
                            String vThumbnail = videoObj.getString("thumbnail");
                                String catVID = videoObj.getString("category");
                                model.setCategory(catVID);
                                model.setId(vId);
                                model.setTitle(vTitle);
                                model.setSource(vSource);
                                model.setDescription(vDesc);
                                model.setThumbnail(vThumbnail);
                                if (mCatID.equals(catVID)) {

                                    mVideos.add(model);
                                    progressDialog.hide();
                                }

                           /* else
                            {
                                progressDialog.hide();
                                Toast.makeText(getContext(),"No video Found",Toast.LENGTH_SHORT).show();
                            }*/
                            //src=\"https://player.vimeo.com/video/165380453?title=0&byline=0&portrait=0\"
                          //  String result = vSource.substring(vSource.indexOf('h'), vSource.indexOf("/"));


                        }
                        videoViewAdapter.notifyDataSetChanged();
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
                Map<String, String> params = new HashMap<>();
                params.put("upro_id", uproID);
                params.put("hash", hash);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    public static MemberVideoFragment newInstance(String catVID) {
        Bundle args= new Bundle() ;
        args.putString(catID,catVID);
        MemberVideoFragment fragment = new MemberVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }
}


