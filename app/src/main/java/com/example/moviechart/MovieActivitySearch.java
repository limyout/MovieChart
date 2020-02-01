package com.example.moviechart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MovieActivitySearch extends AppCompatActivity {

    public static final String TAG = "MyTag";
    SearchView searchView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    RequestQueue queue_search;
    StringRequest stringRequest;
    List<MovieData> movie = new ArrayList<>();
    int i = 0;
    public static String opendatelist[] = new String[200];
    public static String contentslist[] = new String[200];
    RequestQueue queue;
    RequestQueue mqueue;
    RequestQueue pqueue;
    public static String chartlist[] = new String[200];
    public static int saleslist[] = new int[10];
    long diffDay;
    TextView nodatames;
    String nation = "";
    String nationsub1[] = new String[20];
    String nationsub2[] = new String[20];
    String nationsub3[] = new String[20];
    String nationsub4[] = new String[20];
    String nationsub5[] = new String[20];
    String nationsub6[] = new String[20];
    String nationsub7[] = new String[20];
    String nationsub8[] = new String[20];
    String nationsub9[] = new String[20];
    Context context;

    FloatingActionButton mov_plus, mov_cgv, mov_mega, mov_lotte;
    Animation MovOpen, MovClose, MovClockwise, MovantiClockwise;
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar bar = getSupportActionBar();
        bar.setTitle("Searching Movie");

        setContentView(R.layout.activity_movie_search);

        nodatames = (TextView) findViewById(R.id.textView3);

        nodatames.setTextColor(Color.parseColor("#080808"));

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_search);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)

        queue_search = Volley.newRequestQueue(this);
        queue_search.cancelAll(TAG);

        mqueue = Volley.newRequestQueue(this);
        mqueue.cancelAll(TAG);

        pqueue = Volley.newRequestQueue(this);
        pqueue.cancelAll(TAG);

        queue = Volley.newRequestQueue(this);
        queue.cancelAll(TAG);

        for (int a = 0; a < 20; a++) nationsub1[a] = "nulll";
        for (int a = 0; a < 20; a++) nationsub2[a] = "nulll";
        for (int a = 0; a < 20; a++) nationsub3[a] = "nulll";
        for (int a = 0; a < 20; a++) nationsub4[a] = "nulll";
        for (int a = 0; a < 20; a++) nationsub5[a] = "nulll";
        for (int a = 0; a < 20; a++) nationsub6[a] = "nulll";
        for (int a = 0; a < 20; a++) nationsub7[a] = "nulll";
        for (int a = 0; a < 20; a++) nationsub8[a] = "nulll";
        for (int a = 0; a < 20; a++) nationsub9[a] = "nulll";

        mov_plus =  (FloatingActionButton)findViewById(R.id.mov_plus);
        mov_cgv =  (FloatingActionButton)findViewById(R.id.mov_cgv);
        mov_mega =  (FloatingActionButton)findViewById(R.id.mov_mb);
        mov_lotte =  (FloatingActionButton)findViewById(R.id.mov_lc);
        MovOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.mov_open);
        MovClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.mov_close);
        MovClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        MovantiClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        mov_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglemov();
            }
        });

        mov_cgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.cgv.co.kr/WebAPP/Member/Login.aspx?RedirectURL=http%3a%2f%2fm.cgv.co.kr%2fquickReservation%2fDefault.aspx%3fMovieIdx%3d83039%26sm_type%3d"));
                startActivity(intent);
            }
        });

        mov_mega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.megabox.co.kr/?menuId=booking&mBookingType=1"));
                startActivity(intent);
            }
        });

        mov_lotte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.lottecinema.co.kr/NLCMW/Ticketing/Cinema"));
                startActivity(intent);
            }
        });

        chart();
    }

    public void togglemov(){
        if(isOpen){
            mov_lotte.startAnimation(MovClose);
            mov_cgv.startAnimation(MovClose);
            mov_mega.startAnimation(MovClose);
            mov_plus.startAnimation(MovantiClockwise);
            mov_mega.setClickable(false);
            mov_cgv.setClickable(false);
            mov_lotte.setClickable(false);
            isOpen = false;
        }
        else{
            mov_lotte.startAnimation(MovOpen);
            mov_cgv.startAnimation(MovOpen);
            mov_mega.startAnimation(MovOpen);
            mov_plus.startAnimation(MovClockwise);
            mov_mega.setClickable(true);
            mov_cgv.setClickable(true);
            mov_lotte.setClickable(true);
            isOpen = true;
        }
    }


    public void chart() {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, -1);

        String date = sdf.format(calendar.getTime());

        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=a135dfed1aabcde35e8a1dcb8aa9d95d&targetDt=" + date;

        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        JSONObject arrayArticles = response.getJSONObject("boxOfficeResult");
                        JSONArray arrayArticlessub = arrayArticles.getJSONArray("dailyBoxOfficeList");

                        for (i = 0; i < arrayArticlessub.length(); i++) {
                            JSONObject title = arrayArticlessub.getJSONObject(i);

                            String movieNm = title.getString("movieNm");

                            String opendate = title.getString("openDt");

                            int sales = title.getInt("audiAcc");

                            String opendate_mutation = opendate.replace("-", "");

                            chartlist[i] = movieNm;

                            opendatelist[i] = opendate_mutation;

                            saleslist[i] = sales;

                            try {
                                chartlist[i] = URLEncoder.encode(chartlist[i], "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                Log.e("Myapp", "UnsupportedEncodingException");
                            }
                            getMovie(i);

                        }

                        mAdapter = new MyAdapter(movie, MovieActivitySearch.this, nation);
                        recyclerView.setAdapter(mAdapter);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "정보를 읽어올 수 없습니다.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            mqueue.add(request);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "네트워크 연결이 확인되지 않습니다.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    public void getMovie(final int num) {
        String url = "https://api.themoviedb.org/3/search/movie?api_key=9a9d4b54e72cc4da8c34dbd3bc83cff5&language=kr&query=" + chartlist[num] + "&page=1&include_adult=false";

// Request a string response from the provided URL.
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);

                            JSONArray arrayArticles = jsonObj.getJSONArray("results");

                            try {
                                chartlist[num] = URLDecoder.decode(chartlist[num], "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                Log.e("Myapp", "UnsupportedEncodingException");
                            }

                            JSONObject obj = arrayArticles.getJSONObject(0);

                            String applysalse = String.valueOf(saleslist[num] / 10000);

                            MovieData movieData = new MovieData();
                            movieData.setTitle(chartlist[num]);
                            movieData.setGrade("누적 관람객 : " + applysalse + "만명\n");
                            movieData.setUrlToImage("https://image.tmdb.org/t/p/w500" + obj.getString("poster_path"));
                            System.out.println(chartlist[num]);

                            plot(movieData, num);
                            movie.add(movieData);


                            mAdapter = new MyAdapter(movie, MovieActivitySearch.this, nation);
                            recyclerView.setAdapter(mAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        stringRequest.setTag(TAG);
        queue.add(stringRequest);
// Add the request to the RequestQueue.
    }

    public void plot(final MovieData movieData, final int num) {

        String url = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json.jsp?collection=kmdb_new&detail=Y&title=" + chartlist[num] + "&ServiceKey=153N99IR8CN88ZT6734B";


        JsonObjectRequest stringRequest2 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override


            public void onResponse(JSONObject response) {
                try {

                    JSONArray arrayArticlessub = response.getJSONArray("Data");

                    JSONObject contents = arrayArticlessub.getJSONObject(0);

                    JSONArray arrayArticlessub2 = contents.getJSONArray("Result");


                    for (i = 0; i < arrayArticlessub2.length(); i++) {

                        JSONObject contents2 = arrayArticlessub2.getJSONObject(i);

                        if (contents2.getString("repRlsDate").equals(opendatelist[num])) {

                            movieData.setContent(contents2.getString("plot"));

                            contentslist[num] = contents2.getString("plot");

                        }
                    }

                    mAdapter = new MyAdapter(movie, MovieActivitySearch.this, nation);
                    recyclerView.setAdapter(mAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        pqueue.add(stringRequest2);

    }


    public void MovieSearch(final String search) {


        String url = "https://api.themoviedb.org/3/search/movie?api_key=9a9d4b54e72cc4da8c34dbd3bc83cff5&language=kr&query=" + search + "&page=1&include_adult=false";


// Request a string response from the provided URL.
        try {
            stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObj = new JSONObject(response);

                                JSONArray arrayArticles = jsonObj.getJSONArray("results");


                                if (arrayArticles.length() == 0) {
                                    nodatames.setTextColor(Color.parseColor("#ffffff"));
                                } else {
                                    nodatames.setTextColor(Color.parseColor("#080808"));
                                }


                                for (i = 0; i < arrayArticles.length(); i++) {


                                    chartlist[i] = search;

                                    JSONObject obj = arrayArticles.getJSONObject(i);

                                    String opendate = obj.getString("release_date");

                                    String opendate_mutation = opendate.replace("-", "");

                                    opendatelist[i] = opendate_mutation;


                                    nation = obj.getString("original_language");


                                    if (nation.equals("ar")) {
                                        nationsub1[i] = "아랍";
                                    }
                                    if (nation.equals("bg")) {
                                        nationsub1[i] = "불가리아";
                                    }
                                    if (nation.equals("ca")) {
                                        nationsub1[i] = "카탈로니아";
                                    }
                                    if (nation.equals("cs")) {
                                        nationsub1[i] = "체코";
                                    }
                                    if (nation.equals("da")) {
                                        nationsub1[i] = "덴마크";
                                    }
                                    if (nation.equals("de")) {
                                        nationsub1[i] = "독일";
                                    }
                                    if (nation.equals("el")) {
                                        nationsub1[i] = "그리스";
                                    }
                                    if (nation.equals("en")) {
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
                                    if (nation.equals("es")) {
                                        nationsub1[i] = "스페인";
                                    }
                                    if (nation.equals("eu")) {
                                        nationsub1[i] = "바스크";
                                    }
                                    if (nation.equals("fa")) {
                                        nationsub1[i] = "페르시아";
                                    }
                                    if (nation.equals("fi")) {
                                        nationsub1[i] = "핀란드";
                                    }
                                    if (nation.equals("fr")) {
                                        nationsub1[i] = "프랑스";
                                    }
                                    if (nation.equals("hi")) {
                                        nationsub1[i] = "인도";
                                    }
                                    if (nation.equals("hu")) {
                                        nationsub1[i] = "헝가리";
                                    }
                                    if (nation.equals("id")) {
                                        nationsub1[i] = "인도네시아";
                                    }
                                    if (nation.equals("it")) {
                                        nationsub1[i] = "이탈리아";
                                    }
                                    if (nation.equals("ja")) {
                                        nationsub1[i] = "일본";
                                    }
                                    if (nation.equals("ko")) {
                                        nationsub1[i] = "대한민국";
                                    }
                                    if (nation.equals("lt")) {
                                        nationsub1[i] = "리투아니아";
                                    }
                                    if (nation.equals("ml")) {
                                        nationsub1[i] = "인도";
                                    }
                                    if (nation.equals("nb")) {
                                        nationsub1[i] = "노르웨이";
                                    }
                                    if (nation.equals("nl")) {
                                        nationsub1[i] = "네덜란드";
                                    }
                                    if (nation.equals("no")) {
                                        nationsub1[i] = "노르웨이";
                                    }
                                    if (nation.equals("pl")) {
                                        nationsub1[i] = "폴란드";
                                    }
                                    if (nation.equals("pt")) {
                                        nationsub1[i] = "포르투칼";
                                    }
                                    if (nation.equals("ro")) {
                                        nationsub1[i] = "로마";
                                    }
                                    if (nation.equals("ru")) {
                                        nationsub1[i] = "러시아";
                                    }
                                    if (nation.equals("sk")) {
                                        nationsub1[i] = "슬로바키아";
                                    }
                                    if (nation.equals("sl")) {
                                        nationsub1[i] = "슬로베니안";
                                    }
                                    if (nation.equals("sr")) {
                                        nationsub1[i] = "세르비아";
                                    }
                                    if (nation.equals("sv")) {
                                        nationsub1[i] = "스웨덴";
                                    }
                                    if (nation.equals("te")) {
                                        nationsub1[i] = "카탈로니아";
                                    }
                                    if (nation.equals("th")) {
                                        nationsub1[i] = "태국";
                                    }
                                    if (nation.equals("tr")) {
                                        nationsub1[i] = "터키";
                                    }
                                    if (nation.equals("uk")) {
                                        nationsub1[i] = "우크라이나";
                                    }
                                    if (nation.equals("vi")) {
                                        nationsub1[i] = "베트남";
                                    }
                                    if (nation.equals("zh")) {
                                        nationsub1[i] = "중국";
                                        nationsub2[i] = "대만";
                                        nationsub3[i] = "홍콩";
                                    }

                                    MovieData movieData = new MovieData();

                                    movieData.setUrlToImage("https://image.tmdb.org/t/p/w500" + obj.getString("poster_path"));

                                    plot_search(movieData, search, i, nationsub1[i], nationsub2[i], nationsub3[i], nationsub4[i], nationsub5[i], nationsub6[i], nationsub7[i], nationsub8[i], nationsub9[i]);

                                    movie.add(movieData);

                                }
                                mAdapter = new MyAdapter(movie, MovieActivitySearch.this, nation);
                                recyclerView.setAdapter(mAdapter);

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "정보를 읽어올 수 없습니다.", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            stringRequest.setTag(TAG);
            queue_search.add(stringRequest);


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "http접근이 불가합니다.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
// Add the request to the RequestQueue.
    }


    public void plot_search(final MovieData movieData, final String search, final int num, final String nationsub1, final String nationsub2, final String nationsub3, final String nationsub4, final String nationsub5, final String nationsub6, final String nationsub7, final String nationsub8, final String nationsub9) {

        String url = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json.jsp?collection=kmdb_new&detail=Y&title=" + search + "&ServiceKey=153N99IR8CN88ZT6734B";


        JsonObjectRequest stringRequest2 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override


            public void onResponse(JSONObject response) {
                try {

                    JSONArray arrayArticlessub = response.getJSONArray("Data");

                    JSONObject contents = arrayArticlessub.getJSONObject(0);

                    JSONArray arrayArticlessub2 = contents.getJSONArray("Result");

//
                    for (int j = 0; j < arrayArticlessub2.length(); j++) {

                        JSONObject contents2 = arrayArticlessub2.getJSONObject(j);

                        String temp = (String) contents2.getString("repRlsDate");

                        if (temp != null && temp.length() != 0) {


                            String strFormat = "yyyyMMdd";

                            SimpleDateFormat sdf = new SimpleDateFormat(strFormat);

                            try {
                                Date startDate = sdf.parse((String) contents2.getString("repRlsDate"));
                                Date endDate = sdf.parse((String) opendatelist[num]);

                                diffDay = (startDate.getTime() - endDate.getTime()) / (24 * 60 * 60 * 1000);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            System.out.println(contents2.getString("nation"));
                            System.out.println("시작 :: ----------------------- " + nationsub1);



                            if (contents2.getString("nation").contains(nationsub1) || contents2.getString("nation").contains(nationsub2) || contents2.getString("nation").contains(nationsub3) || contents2.getString("nation").contains(nationsub4) || contents2.getString("nation").contains(nationsub5) || contents2.getString("nation").contains(nationsub6) || contents2.getString("nation").contains(nationsub7) || contents2.getString("nation").contains(nationsub8) || contents2.getString("nation").contains(nationsub9)
                                    || nationsub1.contains(contents2.getString("nation")) || nationsub2.contains(contents2.getString("nation")) || nationsub3.contains(contents2.getString("nation")) || nationsub4.contains(contents2.getString("nation")) || nationsub5.contains(contents2.getString("nation")) || nationsub6.contains(contents2.getString("nation")) || nationsub7.contains(contents2.getString("nation")) || nationsub8.contains(contents2.getString("nation")) || nationsub9.contains(contents2.getString("nation"))) {
                                if (Math.abs(diffDay) < 130) {

                                    String title_orign = contents2.getString("title");

                                    String excepths = title_orign.replace(" !HS ", "");
                                    String excepthe = excepths.replace("!HE", "");
                                    String exceptsp = excepthe.replace("  ", "");

                                    movieData.setTitle(exceptsp);

                                    contentslist[num] = contents2.getString("plot");

                                }
                            }


                            movieData.setContent(contentslist[num]);


                        }
                    }
                    mAdapter = new MyAdapter(movie, MovieActivitySearch.this, nation);
                    recyclerView.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue_search.add(stringRequest2);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_menu, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        changeSearchViewTextColor(searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                for (int j = 0; j < contentslist.length; j++) {
                    contentslist[j] = "정보를 찾을 수 없습니다.";
                }
                movie.clear();

                MovieSearch(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

// Add the request to the RequestQueue.
                return true;
            }
        });
        return true;
    }

    private void changeSearchViewTextColor(View v) {
        if (v != null) {
            if (v instanceof TextView) {
                ((TextView) v).setTextColor(Color.WHITE);
                return;
            } else if (v instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) v;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    changeSearchViewTextColor(viewGroup.getChildAt(i));
                }
            }
        }
    }
}