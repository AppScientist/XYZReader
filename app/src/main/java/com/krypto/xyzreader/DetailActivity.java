package com.krypto.xyzreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Displays the full article of the selected item
 */
public class DetailActivity extends AppCompatActivity {

    @Bind(R.id.app_bar)
    Toolbar mToolbar;
    @Bind(R.id.backdrop)
    ImageView mPhoto;
    @Bind(R.id.subtitle)
    TextView mSubTitle;
    @Bind(R.id.desc)
    TextView mDesc;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout layout;
    @BindString(R.string.roboto_regular)
    String roboto_regular;
    @BindString(R.string.share_option)
    String share;

    private String mShareTitle, mShareDesc, mShareAuthor;
    private static final String TITLE="title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Pojo result = getIntent().getParcelableExtra(TITLE);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        displayItems(result);
    }

    public void displayItems(Pojo result) {


        String url = result.getPhoto();
        if (url != null)
            Glide.with(this).load(url).into(mPhoto);

        mShareTitle = result.getTitle();
        layout.setTitle(mShareTitle);


        String subtitle = result.getAuthor();
        String fulldate = result.getPublishedDate();
        String date = fulldate.substring(0, 10);
        mSubTitle.setText("By " + subtitle + ", " + date);
        mSubTitle.setTypeface(Utility.getFont(this, roboto_regular));
        mShareAuthor = "By " + subtitle + ", " + date;

        mShareDesc = result.getBody();
        mDesc.setText(mShareDesc);
        mDesc.setTypeface(Utility.getFont(this, roboto_regular));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void click() {

        startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(mShareTitle + "\n\n" + mShareAuthor + "\n\n" + mShareDesc)
                .getIntent(), share));
    }
}
