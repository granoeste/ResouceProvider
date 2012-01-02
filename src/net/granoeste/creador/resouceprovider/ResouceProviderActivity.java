package net.granoeste.creador.resouceprovider;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ResouceProviderActivity extends Activity implements OnItemClickListener {
	final static String TAG = ResouceProviderActivity.class.getSimpleName();

    // コンテントプロバイダーのURI
	final public Uri CONTENT_URI = Uri.parse("content://" + ResouceProvider.class.getCanonicalName());

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// GritViewの設定
		setContentView(R.layout.main);
		GridView gridView = (GridView)findViewById(R.id.grid_view);
		int i = getResources().getDimensionPixelSize(R.dimen.icon_size);
		gridView.setNumColumns(-1);
	    gridView.setColumnWidth(i);
	    gridView.setVerticalSpacing(i / 3);
	    gridView.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);
	    gridView.setOnItemClickListener(this);
	    gridView.setAdapter(new IconsAdapter(this));

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// 選択されたアイテムのリソースIDを取得。
		String resourceId = parent.getItemAtPosition(position).toString();
		// ResultのIntentに、コンテントプロバイダーのURIのPathに選択されたアイテムのリソースIDを設定する。
		setResult(RESULT_OK, new Intent(null, Uri.withAppendedPath(CONTENT_URI, resourceId)));

		Log.d(TAG, Uri.withAppendedPath(CONTENT_URI, resourceId).toString());

		finish();
	}

	private class IconsAdapter extends BaseAdapter {
		private Context mContext;
		private ArrayList<Integer> mThumbs = new ArrayList<Integer>();;

		public IconsAdapter(Context context) {
			mContext = context;
			loadIcons();
	    }

		private void loadIcons() {
			// R.array.icon_packに登録されているdrawableのリソースIDをリストに追加する。
			for (String str : getResources().getStringArray(R.array.icon_pack)) {
				int resourceId = getResources().getIdentifier(str, "drawable", getApplication().getPackageName());
				if (resourceId == 0) {
					continue;
				}
				mThumbs.add(Integer.valueOf(resourceId));
			}
		}

		@Override
		public int getCount() {
			return mThumbs.size();
		}

		@Override
		public Object getItem(int position) {
		    return mThumbs.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView != null) {
				imageView = (ImageView)convertView;
			} else {
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT));
			}
			// リストに登録されているidのリソースイメージをセットする。
			imageView.setImageResource(((Integer)mThumbs.get(position)).intValue());
			return imageView;
		}
	}
}