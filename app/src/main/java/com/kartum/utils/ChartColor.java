package com.kartum.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Kartum Infotech (Bhavesh Hirpara) on 10/3/2017.
 */
public class ChartColor {

    public static ChartColor DEFAULT;
    public static ChartColor MATERIAL;

    static {

//        DEFAULT = create(Arrays.asList(
//                0xfff16364,
//                0xfff58559,
//                0xfff9a43e,
//                0xffe4c62e,
//                0xff67bf74,
//                0xff59a2be,
//                0xff2093cd,
//                0xffad62a7,
//                0xff805781
//        ));

        MATERIAL = create(Arrays.asList(
                0xff4fa59f, //blue
                0xffb4d336, //green
                0xfff19a20, //orange
                0xffcc246f, //red

                0xff7986cb,
                0xff64b5f6,
                0xff4fc3f7,
                0xff4dd0e1,
                0xff4db6ac,
                0xff81c784,
                0xffaed581,
                0xffff8a65,
                0xffd4e157,
                0xffffd54f,
                0xffffb74d,
                0xffa1887f,
                0xff90a4ae
        ));
    }

    private final List<Integer> mColors;
    private final Random mRandom;

    public static ChartColor create(List<Integer> colorList) {
        return new ChartColor(colorList);
    }

    private ChartColor(List<Integer> colorList) {
        mColors = colorList;
        mRandom = new Random(System.currentTimeMillis());
    }

    public int getRandomColor() {
        return mColors.get(mRandom.nextInt(mColors.size()));
    }

    public int getColor(Object key) {
        Debug.e("get Color", "" + mColors.get(Math.abs(key.hashCode()) % mColors.size()));
        return mColors.get(Math.abs(key.hashCode()) % mColors.size());
    }
}
