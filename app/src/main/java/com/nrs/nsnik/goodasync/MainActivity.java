package com.nrs.nsnik.goodasync;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.nrs.nsnik.goodasync.fragments.SbUserListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mainToolBar) Toolbar mMainToolbar;
    @BindView(R.id.mainDrawerLayout) DrawerLayout mDrawerLayout;
    @BindView(R.id.mainNavigationView) NavigationView mNavigationView;
    @BindView(R.id.mainContainer) RelativeLayout mMainContainer;
    SbUserListFragment SbUsrListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.transparentStatusBar);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        addOnConnection(savedInstanceState);
    }

    private void addOnConnection(Bundle savedInstanceState) {
        if (checkConnection()) {
            initialize();
            initializeDrawer();
            listeners();
            addFragments(savedInstanceState);
        } else {
            removeOffConnection(savedInstanceState);
        }
    }

    private void addFragments(Bundle savedInstanceState) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (savedInstanceState == null) {
            SbUsrListFragment = new SbUserListFragment();
            ft.add(R.id.mainContainer, SbUsrListFragment);
            ft.show(SbUsrListFragment);
            ft.commit();
        }
    }

    private void removeOffConnection(final Bundle savedInstanceState) {
        Snackbar.make(mMainContainer, getResources().getString(R.string.noInternet), BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setActionTextColor(ContextCompat.getColor(MainActivity.this, R.color.white))
                .setAction(getResources().getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addOnConnection(savedInstanceState);
                    }
                }).show();
    }

    private boolean checkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void initializeDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mainDrawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.mainNavigationView);
        mNavigationView.getMenu().getItem(0).setChecked(true);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mMainToolbar, R.string.drawerOpen, R.string.drawerClose) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navItem1:
                        drawerAction(0);
                        break;
                    case R.id.navItem2:
                        drawerAction(1);
                        break;
                    case R.id.navItem3:
                        drawerAction(2);
                        break;
                }
                return false;
            }
        });
    }


    private void drawerAction(int key) {
        invalidateOptionsMenu();
        MenuItem navItem1 = mNavigationView.getMenu().getItem(0).setChecked(false);
        MenuItem navItem2 = mNavigationView.getMenu().getItem(1).setChecked(false);
        MenuItem navItem3 = mNavigationView.getMenu().getItem(2).setChecked(false);
        mDrawerLayout.closeDrawers();
        switch (key) {
            case 0:
                navItem1.setChecked(true);
                break;
            case 1:
                navItem2.setChecked(true);
                break;
            case 2:
                navItem3.setChecked(true);
                break;
        }
    }

    private void initialize() {
        setSupportActionBar(mMainToolbar);
    }

    private void listeners() {
    }

}
