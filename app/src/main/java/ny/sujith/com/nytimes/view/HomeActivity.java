package ny.sujith.com.nytimes.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import ny.sujith.com.nytimes.R;
import ny.sujith.com.nytimes.view.adapter.ArticleListAdapter;
import ny.sujith.com.nytimes.model.Result;
import ny.sujith.com.nytimes.presenter.HomePresenter;
import ny.sujith.com.nytimes.presenter.HomePresenterImpl;
import ny.sujith.com.nytimes.presenter.HomeArticleIntractorImpl;
import ny.sujith.com.nytimes.utils.CustomProgressDialog;

/**
 * Created by Sujith Chandranon 09-11-2018.
 */
public class HomeActivity extends AppCompatActivity implements HomePresenter.MainView,
        NavigationView.OnNavigationItemSelectedListener {


    private CustomProgressDialog mCustomProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        setupToolbar();
        setupNavigationView();
        setPresenter();


    }

    private void setPresenter() {
        HomePresenter.presenter presenter = new HomePresenterImpl(this, new
                HomeArticleIntractorImpl());
        presenter.requestDataFromServer();
    }

    private void setupNavigationView() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string
                .navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, List<Result> resultList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArticleListAdapter articleListAdapter = new ArticleListAdapter(resultList, this,
                getFragmentManager());
        recyclerView.setAdapter(articleListAdapter);
    }

    @Override
    public void showProgress() {
        createProgressDialog();

        if (!mCustomProgressDialog.isShowing())
            mCustomProgressDialog.showDialog();
    }

    private void createProgressDialog() {
        try{
        if (null == mCustomProgressDialog)
            mCustomProgressDialog = new CustomProgressDialog(this);
        }catch (Exception er){

        }
    }

    @Override
    public void hideProgress() {
        try{
        if (null != mCustomProgressDialog)
            mCustomProgressDialog.dismissDialog();
        }catch (Exception er){

        }
    }

    @Override
    public void setDataToRecyclerView(List<Result> resultList) {
        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView, resultList);
    }


    @Override
    public void onResponseFailure(Throwable throwable) {

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

}
