/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.aait.aec.ui.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.aait.aec.BuildConfig;
import com.aait.aec.R;
import com.aait.aec.data.network.model.exam.Exam;
import com.aait.aec.ui.base.BaseActivity;
import com.aait.aec.ui.create.CreateExamActivity;
import com.aait.aec.ui.custom.RoundedImageView;
import com.aait.aec.ui.feed.FeedActivity;
import com.aait.aec.ui.login.LoginActivity;
import com.aait.aec.ui.result.ResultActivity;
import com.aait.aec.ui.student.StudentActivity;
import com.aait.aec.utils.CommonUtils;
import com.aait.aec.utils.MyDividerItemDecoration;
import com.aait.aec.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by janisharali on 27/01/17.
 */

public class MainActivity extends BaseActivity implements MainMvpView, MainAdapter.Callback {

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @Inject
    MainAdapter mAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_view)
    DrawerLayout mDrawer;

    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    @BindView(R.id.tv_app_version)
    TextView mAppVersionTextView;

    @BindView(R.id.exam_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_no_exam)
    TextView tvNoExams;

    @BindView(R.id.exam_swipe_to_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    List<Exam> exams = new ArrayList<>();
    private TextView mNameTextView;
    private TextView mEmailTextView;
    private RoundedImageView mProfileImageView;
    private ActionBarDrawerToggle mDrawerToggle;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);
        mAdapter.setCallback(this);

        setUp();

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.loadExams();
            mSwipeRefreshLayout.setRefreshing(false);
        });
    }

    @OnClick(R.id.fab_exam)
    void onFabClicked() {
        startActivity(CreateExamActivity.getStartIntent(this));
    }

    @Override
    public void onBackPressed() {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment fragment = fragmentManager.findFragmentByTag(AboutFragment.TAG);
//        if (fragment == null) {
//            super.onBackPressed();
//        } else {
//            onFragmentDetached(AboutFragment.TAG);
//        }
    }

    @Override
    public void updateAppVersion() {
        String version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
        mAppVersionTextView.setText(version);
    }

    @Override
    public void updateUserName(String currentUserName) {
        mNameTextView.setText(currentUserName);
    }

    @Override
    public void updateUserEmail(String currentUserEmail) {
        mEmailTextView.setText(currentUserEmail);
    }

    @Override
    public void updateUserProfilePic(String currentUserProfilePicUrl) {
        //load profile pic url into ANImageView
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDrawer != null)
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onFragmentAttached() {
    }

    @Override
    public void lockDrawer() {
        if (mDrawer != null)
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void unlockDrawer() {
        if (mDrawer != null)
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void showExams(List<Exam> exams) {
        if (exams != null) {
            if (exams.size() > 0) {
                if (tvNoExams != null && tvNoExams.getVisibility() == View.VISIBLE)
                    tvNoExams.setVisibility(View.GONE);
                if (mRecyclerView != null && mRecyclerView.getVisibility() == View.GONE)
                    mRecyclerView.setVisibility(View.VISIBLE);
                mAdapter.addItems(exams);
            } else {
                if (tvNoExams != null && tvNoExams.getVisibility() == View.GONE) {
                    tvNoExams.setVisibility(View.VISIBLE);
                    tvNoExams.setText("No exam list found");
                }
                if (mRecyclerView != null && mRecyclerView.getVisibility() == View.VISIBLE)
                    mRecyclerView.setVisibility(View.GONE);
            }
        }
        hideLoading();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem mSearch = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView mSearchView = (SearchView) mSearch.getActionView();

        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Drawable drawable = item.getIcon();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
        //noinspection SimplifiableIfStatement
        if (item.getItemId() == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                mToolbar,
                R.string.open_drawer,
                R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard(MainActivity.this);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        setupNavMenu();
        mPresenter.onNavMenuCreated();
        setupCardContainerView();
        mPresenter.onViewInitialized();

        setUpRecyclerView();
//        prepareExams();

        mPresenter.loadExams();
    }

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        mRecyclerView.setAdapter(mAdapter);

    }

    private void setupCardContainerView() {

        int screenWidth = ScreenUtils.getScreenWidth(this);
        int screenHeight = ScreenUtils.getScreenHeight(this);

    }

    void setupNavMenu() {
        View headerLayout = mNavigationView.getHeaderView(0);
        mProfileImageView = headerLayout.findViewById(R.id.iv_profile_pic);
        mNameTextView = headerLayout.findViewById(R.id.tv_name);
        mEmailTextView = headerLayout.findViewById(R.id.tv_email);

        mNavigationView.setNavigationItemSelectedListener(
                item -> {
                    mDrawer.closeDrawer(GravityCompat.START);
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            mPresenter.onDrawerOptionAboutClick();
                            return true;
                        case R.id.nav_notification:
                            mPresenter.onDrawerNotificationClick();
                            return true;
                        case R.id.nav_category:
                            mPresenter.onDrawerMyFeedClick();
                            return true;
                        case R.id.nav_settings:
                            return true;
                        case R.id.nav_item_logout:
                            mPresenter.onDrawerOptionLogoutClick();
                            return true;
                        default:
                            return false;
                    }
                });
    }

    @Override
    public void openLoginActivity() {
        startActivity(LoginActivity.getStartIntent(this));
        finish();
    }

    @Override
    public void openNotificationActivity() {
        // todo to be replaced later with Notification Activity
        startActivity(StudentActivity.getStartIntent(this));
    }

    @Override
    public void closeNavigationDrawer() {
        if (mDrawer != null) {
            mDrawer.closeDrawer(Gravity.START);
        }
    }

    @Override
    public void openMyFeedActivity() {
        startActivity(FeedActivity.getStartIntent(this));
    }

    private void prepareExams() {

        // todo handle this later
//        Exam a1 = new Exam(R.drawable.drawer_logo, "Object Oriented Programming", "Quiz: ", "April 12, 2019", "Mr. Fitsum A.", "5%");
//        Exam a2 = new Exam(R.drawable.drawer_logo, "Software Engineering", "Mid Exam 1: ", "April 12, 2019", "Mr. Natnael A.", "20%");
//        Exam a3 = new Exam(R.drawable.drawer_logo, "Operating System", "Mid Exam 1: ", "April 12, 2019", "Mr. Eyob", "20%");
//        Exam a4 = new Exam(R.drawable.drawer_logo, "Int. to Networking", "Quiz: ", "April 12, 2019", "Mr. Tigabu", "20%");
//        Exam a5 = new Exam(R.drawable.drawer_logo, "Web Programming", "Mid Exam: ", "April 12, 2019", "Mr. Eskindir", "20%");
//        Exam a6 = new Exam(R.drawable.drawer_logo, "Advan. Mobile Programming", "Final Exam: ", "April 12, 2019", "Mr. Yosef A.", "20%");
//        Exam a7 = new Exam(R.drawable.drawer_logo, "Software Project Management", "Mid Exam: ", "April 12, 2019", "Mr. Dagnachew", "25%");
//        Exam a8 = new Exam(R.drawable.drawer_logo, "Software Quality Assurance", "Final Exam: ", "April 12, 2019", "Mr. Dagnachew", "20%");
//        Exam a9 = new Exam(R.drawable.drawer_logo, "Distributed System", "Mid Exam: ", "April 12, 2019", "Mr. Dagnachew", "20%");
//        Exam a10 = new Exam(R.drawable.drawer_logo, "FPGA", "Mid Exam: ", "April 12, 2019", "Mr. Dagnachew", "15%");
//
//        exams.add(a1);
//        exams.add(a2);
//        exams.add(a3);
//        exams.add(a4);
//        exams.add(a5);
//        exams.add(a6);
//        exams.add(a7);
//        exams.add(a8);
//        exams.add(a9);
//        exams.add(a10);
//
//        mAdapter.addItems(exams);
    }

    @Override
    public void onItemClicked(Exam exam) {
        startActivity(ResultActivity.getStartIntent(this, exam));
    }
}
