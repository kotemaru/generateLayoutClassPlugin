package org.kotemaru.android.myapp;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.kotemaru.android.myapp.layout.ActivityMainViews;
import org.kotemaru.android.myapp.layout.ListItemViews;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainViews mViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViews = new ActivityMainViews(this);
        mViews.mListView.setAdapter(new PkgListAdapter());
    }

    class PkgListAdapter extends BaseAdapter {
        private PackageManager mPackageManager  = getPackageManager();
        private LayoutInflater mInflater = getLayoutInflater();
        private List<ApplicationInfo> mItemInfos;

        PkgListAdapter() {
            mItemInfos = mPackageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        }

        @Override
        public int getCount() {
            return mItemInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return mItemInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            if (view == null) {
                view = mInflater.inflate(R.layout.list_item, null, false);
                view.setTag(new ListItemViews(view));
            }
            ListItemViews views = (ListItemViews) view.getTag();
            ApplicationInfo info = mItemInfos.get(position);
            views.mIcon.setImageDrawable(info.loadIcon(mPackageManager));
            views.mLabel.setText(info.loadLabel(mPackageManager));
            return view;
        }
    }
}
