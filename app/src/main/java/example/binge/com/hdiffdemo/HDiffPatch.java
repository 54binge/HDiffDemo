package example.binge.com.hdiffdemo;

/**
 * Created by Administrator on 2017/7/26.
 */

public class HDiffPatch {

    private static HDiffPatch mHDiffPatch;

    public static HDiffPatch getInstance() {
        if (mHDiffPatch == null) {
            mHDiffPatch = new HDiffPatch();
        }
        return mHDiffPatch;
    }

    public native int hdiff(String oldFilePath, String newFilePath, String diffFilePath);

    public native int hpatch(String oldFilePath, String diffFilePath, String newFilePath);
}
