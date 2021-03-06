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
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.nrs.nsnik.goodasync.fragments.Dagger2Fragment;
import com.nrs.nsnik.goodasync.fragments.RoomFragment;
import com.nrs.nsnik.goodasync.fragments.SbUsLsRxJvFragment;
import com.nrs.nsnik.goodasync.fragments.SbUserListFragment;
import com.nrs.nsnik.goodasync.fragments.ViewModelFragment;
import com.squareup.leakcanary.LeakCanary;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String[] mFragmentTags = {"retrofit", "rxjava","dagger2","viewmodel","room"};
    @BindView(R.id.mainToolBar)
    Toolbar mMainToolbar;
    @BindView(R.id.mainDrawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.mainNavigationView)
    NavigationView mNavigationView;
    @BindView(R.id.mainContainer)
    FrameLayout mMainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(getApplication());
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
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new SbUserListFragment(), mFragmentTags[0]).commit();
        } else {
            removeOffConnection(savedInstanceState);
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
                mDrawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.navItem1:
                        if (getSupportFragmentManager().findFragmentByTag(mFragmentTags[0]) == null) {
                            replaceFragment(new SbUserListFragment(), mFragmentTags[0]);
                            drawerAction(0);
                        }
                        break;
                    case R.id.navItem2:
                        if (getSupportFragmentManager().findFragmentByTag(mFragmentTags[1]) == null) {
                            replaceFragment(new SbUsLsRxJvFragment(), mFragmentTags[1]);
                            drawerAction(1);
                        }
                        break;
                    case R.id.navItem3:
                        if (getSupportFragmentManager().findFragmentByTag(mFragmentTags[2]) == null) {
                            replaceFragment(new Dagger2Fragment(), mFragmentTags[2]);
                            drawerAction(2);
                        }
                        break;
                    case R.id.navItem4:
                        if (getSupportFragmentManager().findFragmentByTag(mFragmentTags[3]) == null) {
                            replaceFragment(new ViewModelFragment(), mFragmentTags[3]);
                            drawerAction(3);
                        }
                        break;
                    case R.id.navItem5:
                        if (getSupportFragmentManager().findFragmentByTag(mFragmentTags[4]) == null) {
                            replaceFragment(new RoomFragment(), mFragmentTags[4]);
                            drawerAction(4);
                        }
                        break;
                }
                return false;
            }
        });
    }

    public void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainContainer, fragment, tag);
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.commit();
    }

    private void drawerAction(int key) {
        invalidateOptionsMenu();
        MenuItem navItem1 = mNavigationView.getMenu().getItem(0).setChecked(false);
        MenuItem navItem2 = mNavigationView.getMenu().getItem(1).setChecked(false);
        MenuItem navItem3 = mNavigationView.getMenu().getItem(2).setChecked(false);
        MenuItem navItem4 = mNavigationView.getMenu().getItem(3).setChecked(false);
        MenuItem navItem5 = mNavigationView.getMenu().getItem(4).setChecked(false);
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
            case 3:
                navItem4.setChecked(true);
                break;
            case 4:
                navItem5.setChecked(true);
                break;
        }
    }

    private void initialize() {
        setSupportActionBar(mMainToolbar);
    }

    private void listeners() {
    }

}
