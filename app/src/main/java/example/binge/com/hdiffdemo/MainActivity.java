package example.binge.com.hdiffdemo;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    static {
        System.loadLibrary("HDiffPatch");
    }

    private final int REQUEST_CODE_SELECT_OLD = 100;
    private final int REQUEST_CODE_SELECT_NEW = 101;

    private TextView oldInfo, newInfo, genInfo;
    private String oldFilePath, newFilePath, patchFilePath, genFilePath;
    private ContentResolver mContentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oldInfo = (TextView) findViewById(R.id.old_info);
        newInfo = (TextView) findViewById(R.id.new_info);
        genInfo = (TextView) findViewById(R.id.gen_info);

        patchFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + System.currentTimeMillis();
        genFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + System.currentTimeMillis() + ".apk";

        mContentResolver = getContentResolver();
    }

    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        switch (view.getId()) {
            case R.id.btn_select_old:
                startActivityForResult(intent, REQUEST_CODE_SELECT_OLD);
                break;
            case R.id.btn_select_new:
                startActivityForResult(intent, REQUEST_CODE_SELECT_NEW);
                break;
            case R.id.get_patch:
                genPatch();
                break;
            case R.id.get_new:
                genNewFile();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Log.d(TAG, "onActivityResult: 跪了");
            return;
        }
        Uri uri = data.getData();
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        Cursor cursor = mContentResolver.query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        File file = new File(filePath);
        if (requestCode == REQUEST_CODE_SELECT_OLD) {
            oldInfo.setText("路径：\n" + filePath + "\nMD5:\n" + getFileMD5(file));
            oldFilePath = filePath;
        } else if (requestCode == REQUEST_CODE_SELECT_NEW) {
            newInfo.setText("路径：\n" + filePath + "\nMD5:\n" + getFileMD5(file));
            newFilePath = filePath;
        }
    }

    private String getFileMD5(File file) {
        MessageDigest digest;
        FileInputStream in;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    private void genPatch() {
        if (TextUtils.isEmpty(oldFilePath) || TextUtils.isEmpty(newFilePath)) {
            Toast.makeText(this, TextUtils.isEmpty(oldFilePath) ? "oldFilePath " : "newFilePath " + "路径有问题", Toast.LENGTH_SHORT).show();
            return;
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                HDiffPatch.getInstance().hdiff(oldFilePath, newFilePath, patchFilePath);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(MainActivity.this, new File(patchFilePath).exists() ? "成功 " : "失败 ", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    private void genNewFile() {
        if (!new File(oldFilePath).exists() || !new File(patchFilePath).exists()) {
            Toast.makeText(MainActivity.this, !new File(patchFilePath).exists() ? "patchFile不存在 " : "oldFilePath不存在 ", Toast.LENGTH_SHORT).show();
            return;
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                HDiffPatch.getInstance().hpatch(oldFilePath, patchFilePath, genFilePath);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                File file = new File(genFilePath);
                if (file.exists()) {
                    Toast.makeText(MainActivity.this, "生成新文件成功 ", Toast.LENGTH_SHORT).show();
                    genInfo.setText("路径：\n" + genFilePath + "\nMD5:\n" + getFileMD5(file));
                } else {
                    Toast.makeText(MainActivity.this, "生成新文件失败 ", Toast.LENGTH_SHORT).show();
                }


            }
        }.execute();
    }
}
