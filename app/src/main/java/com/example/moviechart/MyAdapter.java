package com.example.moviechart;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.moviechart.MovieActivitySearch.TAG;
import static com.example.moviechart.MovieActivitySearch.chartlist;
import static com.example.moviechart.MovieActivitySearch.opendatelist;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<MovieData> mDataset;
    Context context;
    static String nationadpter = "";
    static int countpos = 1;
    static int loadcheck = 0;
    static int finalloadcheck = 0;
    static int searchcheck = 0;
    static int temp = 0;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView TextView_title;
        public TextView TextView_grade;
        public TextView TextView_content;
        public SimpleDraweeView ImageView_title;
        CardView container;
        public Button share;
        public Button booking;
        public FloatingActionButton plus;
        String path;
        String genre;
        String runtime;
        String rating;
        String director;
        String actor[] = new String[200];
        String opendate = "";
        String opendate_mutation = "";
        String nationsub1[] = new String[20];
        String nationsub2[] = new String[20];
        String nationsub3[] = new String[20];
        String nationsub4[] = new String[20];
        String nationsub5[] = new String[20];
        String nationsub6[] = new String[20];
        String nationsub7[] = new String[20];
        String nationsub8[] = new String[20];
        String nationsub9[] = new String[20];
        long diffDay;
        RequestQueue dmqueue;
        RequestQueue dmqueue2;
        Date startDate;
        Date endDate;
        Date matchDate;

        public MyViewHolder(View v) {
            super(v);

            container = v.findViewById(R.id.card_view);
            TextView_title = v.findViewById(R.id.TextView_title);
            TextView_content = v.findViewById(R.id.TextView_content);
            TextView_grade = v.findViewById(R.id.TextView_grade);
            ImageView_title = v.findViewById(R.id.ImageView_title);
            share = v.findViewById(R.id.share);
            booking = v.findViewById(R.id.booking);
            plus = v.findViewById(R.id.mov_plus);


            v.setOnClickListener(new View.OnClickListener() {


                @Override

                public void onClick(View v) {

                    final Context context = v.getContext();
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        final Intent intent = new Intent(v.getContext(), DetailMovie.class);
                        intent.putExtra("text_title", TextView_title.getText().toString());
                        intent.putExtra("text_content", TextView_content.getText().toString());

                        StringRequest stringRequest;

                        dmqueue = Volley.newRequestQueue(context);
                        dmqueue2 = Volley.newRequestQueue(context);

                        for(int init = 0; init < 20; init++){
                            nationsub1[init] = "nation_init";
                            nationsub2[init] = "nation_init";
                            nationsub3[init] = "nation_init";
                            nationsub4[init] = "nation_init";
                            nationsub5[init] = "nation_init";
                            nationsub6[init] = "nation_init";
                            nationsub7[init] = "nation_init";
                            nationsub8[init] = "nation_init";
                            nationsub9[init] = "nation_init";
                        }


                        String url = "https://api.themoviedb.org/3/search/movie?api_key=9a9d4b54e72cc4da8c34dbd3bc83cff5&language=kr&query=" + TextView_title.getText().toString() + "&page=1&include_adult=false";

                        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {

                                    JSONObject jsonObj = new JSONObject(response);

                                    JSONArray arrayArticles = jsonObj.getJSONArray("results");

                                    for (int i = 0; i < arrayArticles.length(); i++) {


                                        JSONObject obj = arrayArticles.getJSONObject(i);

                                        opendate = obj.getString("release_date");

                                        nationadpter = obj.getString("original_language");


                                        if (nationadpter.equals("ar")) {
                                            nationsub1[i] = "아랍";
                                        }
                                        if (nationadpter.equals("bg")) {
                                            nationsub1[i] = "불가리아";
                                        }
                                        if (nationadpter.equals("ca")) {
                                            nationsub1[i] = "카탈로니아";
                                        }
                                        if (nationadpter.equals("cs")) {
                                            nationsub1[i] = "체코";
                                        }
                                        if (nationadpter.equals("da")) {
                                            nationsub1[i] = "덴마크";
                                        }
                                        if (nationadpter.equals("de")) {
                                            nationsub1[i] = "독일";
                                        }
                                        if (nationadpter.equals("el")) {
                                            nationsub1[i] = "그리스";
                                        }
                                        if (nationadpter.equals("en")) {
                                            nationsub1[i] = "미국";
                                            nationsub2[i] = "호주";
                                            nationsub3[i] = "캐나다";
                                            nationsub4[i] = "벨리즈";
                                            nationsub5[i] = "자메이카";
                                            nationsub6[i] = "뉴질랜드";
                                            nationsub7[i] = "영국";
                                            nationsub8[i] = "아일랜드";
                                            nationsub9[i] = "남아프리카 공화국";
                                        }
                                        if (nationadpter.equals("es")) {
                                            nationsub1[i] = "스페인";
                                        }
                                        if (nationadpter.equals("eu")) {
                                            nationsub1[i] = "바스크";
                                        }
                                        if (nationadpter.equals("fa")) {
                                            nationsub1[i] = "페르시아";
                                        }
                                        if (nationadpter.equals("fi")) {
                                            nationsub1[i] = "핀란드";
                                        }
                                        if (nationadpter.equals("fr")) {
                                            nationsub1[i] = "프랑스";
                                        }
                                        if (nationadpter.equals("hi")) {
                                            nationsub1[i] = "인도";
                                        }
                                        if (nationadpter.equals("hu")) {
                                            nationsub1[i] = "헝가리";
                                        }
                                        if (nationadpter.equals("id")) {
                                            nationsub1[i] = "인도네시아";
                                        }
                                        if (nationadpter.equals("it")) {
                                            nationsub1[i] = "이탈리아";
                                        }
                                        if (nationadpter.equals("ja")) {
                                            nationsub1[i] = "일본";
                                        }
                                        if (nationadpter.equals("ko")) {
                                            nationsub1[i] = "대한민국";
                                        }
                                        if (nationadpter.equals("lt")) {
                                            nationsub1[i] = "리투아니아";
                                        }
                                        if (nationadpter.equals("ml")) {
                                            nationsub1[i] = "인도";
                                        }
                                        if (nationadpter.equals("nb")) {
                                            nationsub1[i] = "노르웨이";
                                        }
                                        if (nationadpter.equals("nl")) {
                                            nationsub1[i] = "네덜란드";
                                        }
                                        if (nationadpter.equals("no")) {
                                            nationsub1[i] = "노르웨이";
                                        }
                                        if (nationadpter.equals("pl")) {
                                            nationsub1[i] = "폴란드";
                                        }
                                        if (nationadpter.equals("pt")) {
                                            nationsub1[i] = "포르투칼";
                                        }
                                        if (nationadpter.equals("ro")) {
                                            nationsub1[i] = "로마";
                                        }
                                        if (nationadpter.equals("ru")) {
                                            nationsub1[i] = "러시아";
                                        }
                                        if (nationadpter.equals("sk")) {
                                            nationsub1[i] = "슬로바키아";
                                        }
                                        if (nationadpter.equals("sl")) {
                                            nationsub1[i] = "슬로베니안";
                                        }
                                        if (nationadpter.equals("sr")) {
                                            nationsub1[i] = "세르비아";
                                        }
                                        if (nationadpter.equals("sv")) {
                                            nationsub1[i] = "스웨덴";
                                        }
                                        if (nationadpter.equals("te")) {
                                            nationsub1[i] = "카탈로니아";
                                        }
                                        if (nationadpter.equals("th")) {
                                            nationsub1[i] = "태국";
                                        }
                                        if (nationadpter.equals("tr")) {
                                            nationsub1[i] = "터키";
                                        }
                                        if (nationadpter.equals("uk")) {
                                            nationsub1[i] = "우크라이나";
                                        }
                                        if (nationadpter.equals("vi")) {
                                            nationsub1[i] = "베트남";
                                        }
                                        if (nationadpter.equals("zh")) {
                                            nationsub1[i] = "중국";
                                            nationsub2[i] = "대만";
                                            nationsub3[i] = "홍콩";
                                        }


                                        opendate_mutation = opendate.replace("-", "");
                                        String strFormat = "yyyyMMdd";

                                        SimpleDateFormat sdf = new SimpleDateFormat(strFormat);

                                        try {

                                            startDate = sdf.parse(opendatelist[getAdapterPosition()]);

                                            endDate = sdf.parse(opendate_mutation);

                                            diffDay = (startDate.getTime() - endDate.getTime()) / (24 * 60 * 60 * 1000);

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }


                                        if (Math.abs(diffDay) < 130) {
                                            path = "https://image.tmdb.org/t/p/w500/" + obj.getString("poster_path");

                                            matchDate = endDate;
                                        }

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                intent.putExtra("path", path);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                        stringRequest.setTag(TAG);
                        dmqueue.add(stringRequest);


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                //여기에 딜레이 후 시작할 작업들을 입력
                                if (matchDate == null) {
                                    Toast.makeText(context, "정보를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
                                }
                                if (matchDate != null) {

                                    String url2 = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json.jsp?collection=kmdb_new&detail=Y&title=" + TextView_title.getText().toString() + "&ServiceKey=153N99IR8CN88ZT6734B";


                                    JsonObjectRequest stringRequest2 = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
                                        @Override


                                        public void onResponse(JSONObject response) {
                                            try {

                                                JSONArray arrayArticlessub = response.getJSONArray("Data");

                                                JSONObject contents = arrayArticlessub.getJSONObject(0);

                                                JSONArray arrayArticlessub2 = contents.getJSONArray("Result");

                                                for (int i = 0; i < arrayArticlessub2.length(); i++) {

                                                    JSONObject contents2 = arrayArticlessub2.getJSONObject(i);

                                                    JSONArray arrayArticlessub3 = contents2.getJSONArray("rating");
                                                    JSONArray arrayArticlessub4 = contents2.getJSONArray("director");
                                                    JSONArray arrayArticlessub5 = contents2.getJSONArray("actor");


                                                    for (int j = 0; j < arrayArticlessub.length(); j++) {

                                                        String strFormat = "yyyyMMdd";

                                                        SimpleDateFormat sdf = new SimpleDateFormat(strFormat);

                                                        try {
                                                            System.out.println("replsdate = " + contents2.getString("repRlsDate"));

                                                            startDate = sdf.parse(contents2.getString("repRlsDate"));

                                                            endDate = matchDate;

                                                            diffDay = (startDate.getTime() - endDate.getTime()) / (24 * 60 * 60 * 1000);
                                                        } catch (ParseException e) {
                                                            e.printStackTrace();
                                                        }

                                                        for (int m = 0; m < 20; m++) {

                                                            if (contents2.getString("nation").contains(nationsub1[m]) ||
                                                                    contents2.getString("nation").contains(nationsub2[m]) ||
                                                                    contents2.getString("nation").contains(nationsub3[m]) ||
                                                                    contents2.getString("nation").contains(nationsub4[m]) ||
                                                                    contents2.getString("nation").contains(nationsub5[m]) ||
                                                                    contents2.getString("nation").contains(nationsub6[m]) ||
                                                                    contents2.getString("nation").contains(nationsub7[m]) ||
                                                                    contents2.getString("nation").contains(nationsub8[m]) ||
                                                                    contents2.getString("nation").contains(nationsub9[m]) ||
                                                                    nationsub1[m].contains(contents2.getString("nation")) ||
                                                                    nationsub2[m].contains(contents2.getString("nation")) ||
                                                                    nationsub3[m].contains(contents2.getString("nation")) ||
                                                                    nationsub4[m].contains(contents2.getString("nation")) ||
                                                                    nationsub5[m].contains(contents2.getString("nation")) ||
                                                                    nationsub6[m].contains(contents2.getString("nation")) ||
                                                                    nationsub7[m].contains(contents2.getString("nation")) ||
                                                                    nationsub8[m].contains(contents2.getString("nation")) ||
                                                                    nationsub9[m].contains(contents2.getString("nation"))) {
                                                                if (Math.abs(diffDay) < 130) {
                                                                    genre = contents2.getString("genre");
                                                                    runtime = contents2.getString("runtime");

                                                                    for (int k = 0; k < arrayArticlessub3.length(); k++) {

                                                                        JSONObject contents3 = arrayArticlessub3.getJSONObject(k);
                                                                        rating = contents3.getString("ratingGrade");
                                                                    }

                                                                    for (int k = 0; k < arrayArticlessub4.length(); k++) {

                                                                        JSONObject contents3 = arrayArticlessub4.getJSONObject(k);
                                                                        director = contents3.getString("directorNm");
                                                                    }

                                                                    for (int k = 0; k < arrayArticlessub5.length(); k++) {

                                                                        JSONObject contents3 = arrayArticlessub5.getJSONObject(k);
                                                                        actor[k] = contents3.getString("actorNm");

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                }

                                                intent.putExtra("genre", genre);
                                                intent.putExtra("runtime", runtime);
                                                intent.putExtra("rating", rating);
                                                intent.putExtra("director", director);
                                                intent.putExtra("actor", actor);
                                                context.startActivity(intent);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                        }
                                    });
                                    dmqueue2.add(stringRequest2);


                                }
                            }
                        }, 450);


                    }
                }
            });

        }

    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<MovieData> myDataset, Context context, String nation) {


        final Toast t = Toast.makeText(context, "데이터 로딩 중입니다.", Toast.LENGTH_SHORT);
        final Toast te = Toast.makeText(context, "데이터 로딩 완료되었습니다.", Toast.LENGTH_SHORT);

        if (searchcheck == 0) {
            if (myDataset.size() + 2 == countpos && loadcheck == 1) {
                t.cancel();
                te.show();
                countpos = -myDataset.size() + 1;
                loadcheck = 0;
                finalloadcheck++;
                searchcheck++;
            }
        }
        if (searchcheck != 0) {
            if (temp != myDataset.size()) {
                countpos = 1;
                temp = myDataset.size();
            }
            if (myDataset.size() + 1 == countpos && loadcheck == 1) {
                t.cancel();
                te.show();
                countpos = -myDataset.size() + 1;
                loadcheck = 0;
            }
        }

        if (countpos == 1 && loadcheck == 0) {
            if (finalloadcheck == 1) {
                finalloadcheck = 0;
            } else {
                t.show();
                loadcheck = 1;
            }
        }

        if (countpos == 0) finalloadcheck = 0;


        countpos++;

        mDataset = myDataset;
        this.context = context;
        Fresco.initialize(context);
        this.nationadpter = nation;


    }


    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view

        LinearLayout v = (LinearLayout) LayoutInflater.from(context)
                .inflate(R.layout.row_movie, parent, false);
        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        MovieData movie = mDataset.get(position);


        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);

                System.out.println(holder.getAdapterPosition());

                intent.putExtra(Intent.EXTRA_TEXT, "https://search.naver.com/search.naver?sm=top_sug.pre&fbm=1&acr=1&acq=rudnfdhkdrn&qdt=0&ie=utf8&query=" + chartlist[holder.getAdapterPosition()]);

                intent.setType("text/plain");

                context.startActivity(Intent.createChooser(intent, "네이버 링크 공유"));

            }
        });

        holder.booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=" + chartlist[holder.getAdapterPosition()] + " 예고편"));

                context.startActivity(intent);
            }
        });


        if (movie.getTitle() != "null") {
            holder.TextView_title.setText(movie.getTitle());
        } else {
            holder.TextView_title.setText("정보를 찾을 수 없습니다.");
        }

        if (movie.getContent() != "null") {
            holder.TextView_content.setText(movie.getContent());
        } else {
            holder.TextView_content.setText("정보를 찾을 수 없습니다.");
        }

        if (movie.getGrade() != "null") {
            holder.TextView_grade.setText(movie.getGrade());
        } else {
            holder.TextView_grade.setText("정보를 찾을 수 없습니다.");
        }


        Uri uri = Uri.parse(movie.getUrlToImage());
        holder.ImageView_title.setImageURI(uri);


        holder.ImageView_title.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));

        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }


}