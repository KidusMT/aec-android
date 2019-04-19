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

package com.aait.aec.ui.feed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.aait.aec.R;
import com.aait.aec.data.db.model.Category;
import com.aait.aec.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by janisharali on 25/05/17.
 */

public class FeedActivity extends BaseActivity implements FeedMvpView {

    @Inject
    FeedMvpPresenter<FeedMvpView> mPresenter;

    @Inject
    FeedPagerAdapter mPagerAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.sensor_recycler)
    RecyclerView mRecyclerView;

    @Inject
    LinearLayoutManager mLayoutManager;

    List<Category> categoryList = new ArrayList<>();

    public static Intent getStartIntent(Context context) {
        return new Intent(context, FeedActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setUpRecyclerView();

        prepareCategorys();

    }

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mPagerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    private void prepareCategorys() {
        int[] covers = new int[]{
                R.drawable.category_comp_arch,
                R.drawable.category_software,
                R.drawable.category_oop,
                R.drawable.category_distributed_db,
                R.drawable.category_design_pattern,
                R.drawable.category_operating_system};

        Category a = new Category("Computer Architecture", "Mr. Abebe", covers[0]);
        categoryList.add(a);

        a = new Category("Software Eng.", "Mr. Natnael A.", covers[1]);
        categoryList.add(a);

        a = new Category("OOP", "Mr. Yosef A. ", covers[2]);
        categoryList.add(a);

        a = new Category("Distributed DB", "Mr. Wondimagegn", covers[3]);
        categoryList.add(a);

        a = new Category("Design Pattern", "Dr. Vittapu", covers[4]);
        categoryList.add(a);

        a = new Category("Operating System", "Mr. Eyob W.", covers[5]);
        categoryList.add(a);


        mPagerAdapter.addItems(categoryList);
    }

}
