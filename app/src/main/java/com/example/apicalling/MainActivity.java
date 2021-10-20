package com.example.apicalling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.apicalling.adapter.UserAdapter;
import com.example.apicalling.databinding.ActivityMainBinding;
import com.example.apicalling.model.RetrofitApiClient;
import com.example.apicalling.pojo.Data;
import com.example.apicalling.pojo.Root;
import com.example.apicalling.utils.PaginationScrollListener;
import com.example.apicalling.viewmodel.UserDataViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding activityMainBinding;
    private RecyclerView userRecycleView;
    private UserAdapter userAdapter;
    private ArrayList<Root> listOfResults = new ArrayList<>();
    private UserDataViewModel userDataViewModel;
    private int TOTAL_PAGES = 5;
    private int currentPage = 1;
    LinearLayoutManager linearLayoutManager;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        userRecycleView = activityMainBinding.userList;
        progressBar = activityMainBinding.mainProgress;
        setContentView(activityMainBinding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        userDataViewModel = ViewModelProviders.of(this).get(UserDataViewModel.class);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userAdapter = new UserAdapter(listOfResults,getBaseContext());
        getUserList();


        userRecycleView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return false;
            }
        });


    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);
        Call<Root> call = calluserList();
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                userAdapter.removeLoadingFooter();
                isLoading = false;

                for (int i = 0; i< response.body().getData().size(); i++) {
                    listOfResults.add(response.body());
                    // userDataViewModel.addUserData( i, response);
                }
                //userAdapter.addAll(listOfResults);

                if (currentPage != TOTAL_PAGES) userAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                t.printStackTrace();
                // TODO: 08/11/16 handle failure
            }
        });
    }
    private void getUserList() {
        Call<Root> call = calluserList();

        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i< response.body().getData().size(); i++) {
                        listOfResults.add(response.body());
                       // userDataViewModel.addUserData( i, response);
                    }
                    if (currentPage <= TOTAL_PAGES) userAdapter.addLoadingFooter();
                    else isLastPage = true;
                    progressBar.setVisibility(View.GONE);
                    userRecycleView.setAdapter(userAdapter);
                    userRecycleView.setLayoutManager(linearLayoutManager);
                }
            }


            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Log.d("userData",t.getCause().getLocalizedMessage());
            }

        });
    }

    /**
     * Performs a Retrofit call to the user data.
     * Same API call for Pagination.
     * As {@link #currentPage} will be incremented automatically
     * by @{@link PaginationScrollListener} to load next page.
     */
    private Call<Root> calluserList(){
        return RetrofitApiClient.getInstance().getMyApi().getUserList(currentPage,TOTAL_PAGES);
    }
}
