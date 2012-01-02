package net.granoeste.creador.resouceprovider;

import java.io.FileNotFoundException;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class ResouceProvider extends ContentProvider {
	final static String TAG = ResouceProvider.class.getSimpleName();

	@Override
	public String getType(Uri uri) {
		return "image/*";
	}

	@Override
	public AssetFileDescriptor openAssetFile(Uri uri, String mode)
			throws FileNotFoundException {
		Log.d(TAG, uri.toString());

		AssetFileDescriptor desc = null;
		if (uri.getPathSegments().size() > 0) {
			// パスからリソースIDを取得
			int resourceId = Integer.valueOf((String) uri.getPathSegments().get(0)).intValue();
			// リソースIDのAssetFileDescriptorを取得
			desc = getContext().getResources().openRawResourceFd(resourceId);
		}
		return desc;
	}

	// not operation ------------------------------------------------
	@Override
	public boolean onCreate() {
		return false;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}
	// --------------------------------------------------------------
}
