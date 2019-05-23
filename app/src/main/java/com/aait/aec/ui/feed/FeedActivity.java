package com.aait.aec.ui.feed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.aait.aec.R;
import com.aait.aec.data.db.model.Category;
import com.aait.aec.ui.base.BaseActivity;
import com.aait.aec.utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aait.aec.utils.ViewUtils.dpToPx;

public class FeedActivity extends BaseActivity implements FeedMvpView {

    @Inject
    FeedMvpPresenter<FeedMvpView> mPresenter;

    @Inject
    FeedPagerAdapter mPagerAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.sensor_recycler)
    RecyclerView mRecyclerView;

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

        prepareCategories();

    }

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
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

    private void prepareCategories() {
        int[] covers = new int[]{
                R.drawable.category_comp_arch,
                R.drawable.category_software,
                R.drawable.category_oop,
                R.drawable.category_distributed_db,
                R.drawable.category_design_pattern,
                R.drawable.category_operating_system};

        Category a1 = new Category("Computer Architecture", "Mr. Abebe", covers[0]);
        Category a2 = new Category("Software Eng.", "Mr. Natnael A.", covers[1]);
        Category a3 = new Category("OOP", "Mr. Yosef A. ", covers[2]);
        Category a4 = new Category("Distributed DB", "Mr. Wondimagegn", covers[3]);
        Category a5 = new Category("Design Pattern", "Dr. Vittapu", covers[4]);
        Category a6 = new Category("Operating System", "Mr. Eyob W.", covers[5]);

        categoryList.add(a1);
        categoryList.add(a2);
        categoryList.add(a3);
        categoryList.add(a4);
        categoryList.add(a5);
        categoryList.add(a6);

        mPagerAdapter.addItems(categoryList);
    }

}
