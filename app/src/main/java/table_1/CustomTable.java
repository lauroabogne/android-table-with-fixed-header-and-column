package table_1;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Lauro-PC on 4/16/2017.
 */

public class CustomTable extends LinearLayout {
    enum SCROLLVIEW_TAGS{
        LEFT_TABLE_SCROLLVIEW,
        RIGHT_TABLE_SCROLLVIEW
    }

    public static final  int HEADER_BACKROUND_COLOR = Color.parseColor("#339999");
    public static final  int BODY_BACKROUND_COLOR = Color.parseColor("#99cccc");
    /**
     * left views
     * @param context
     */
    LinearLayout mLeftFirstLevelLinear;
    LinearLayout mLeftSecondLevelHeaderLinear;
    ScrollView mLeftSecondLevelBodyScrollView;
    LinearLayout mLeftThirdLevelBodyLinear;

    /**
     * right views
     * @param context
     */

    HorizontalScrollView mRightFirstLevelHorizontalScrollView;
    LinearLayout mRightSecondLevelLinear;
    LinearLayout mRightThirdLevelHeaderLinear;
    ScrollView mRightThirdLevelBodyScrollView;
    LinearLayout mRightFourthLevelBodyLinear;

    CustomTableHeaderUtils mCustomTableHeaderUtils;
    CustomTableBodyUtils mCustomTableBodyUtils;


    public CustomTable(Context context) {
        super(context);
        this.init();
        mCustomTableHeaderUtils = new CustomTableHeaderUtils( this);
        mCustomTableBodyUtils = new CustomTableBodyUtils(this);

    }



    private void init(){
        /**
         * LEFT VIEWS
         */
        mLeftFirstLevelLinear = new LinearLayout(this.getContext());
        mLeftFirstLevelLinear.setOrientation(LinearLayout.VERTICAL);

        mLeftSecondLevelHeaderLinear = new LinearLayout(this.getContext());
        mLeftSecondLevelHeaderLinear.setOrientation(LinearLayout.HORIZONTAL);

        mLeftSecondLevelBodyScrollView = new CustomScrollView(this);
        mLeftSecondLevelBodyScrollView.setTag(SCROLLVIEW_TAGS.LEFT_TABLE_SCROLLVIEW);

        mLeftThirdLevelBodyLinear = new LinearLayout(this.getContext());
        mLeftThirdLevelBodyLinear.setOrientation(LinearLayout.VERTICAL);

        mLeftFirstLevelLinear.addView(mLeftSecondLevelHeaderLinear);
        mLeftFirstLevelLinear.addView(mLeftSecondLevelBodyScrollView);

        mLeftSecondLevelBodyScrollView.addView(mLeftThirdLevelBodyLinear);


        /**
         * RIGHT VIEWS
         */

        mRightFirstLevelHorizontalScrollView = new HorizontalScrollView(this.getContext());

        mRightSecondLevelLinear = new LinearLayout(this.getContext());
        mRightSecondLevelLinear.setOrientation(LinearLayout.VERTICAL);

        mRightFirstLevelHorizontalScrollView.addView(mRightSecondLevelLinear);

        mRightThirdLevelHeaderLinear = new LinearLayout(this.getContext());
        mRightThirdLevelHeaderLinear.setOrientation(LinearLayout.HORIZONTAL);

        mRightThirdLevelBodyScrollView = new CustomScrollView(this);
        mRightThirdLevelBodyScrollView.setTag(SCROLLVIEW_TAGS.RIGHT_TABLE_SCROLLVIEW);

        mRightSecondLevelLinear.addView(mRightThirdLevelHeaderLinear);
        mRightSecondLevelLinear.addView(mRightThirdLevelBodyScrollView);

        mRightFourthLevelBodyLinear = new LinearLayout(this.getContext());
        mRightFourthLevelBodyLinear.setOrientation(LinearLayout.VERTICAL);


        mRightThirdLevelBodyScrollView.addView(mRightFourthLevelBodyLinear);

        addView(mLeftFirstLevelLinear);
        addView(mRightFirstLevelHorizontalScrollView);


    }


}
