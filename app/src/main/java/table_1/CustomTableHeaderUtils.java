package table_1;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Lauro-PC on 4/16/2017.
 */

class CustomTableHeaderUtils {


    public static final String PREVIOUS_ARROW = "\u2190";
    public static final String NEXT_ARROW = "\u2192";


    final static int PADDING = 10;


    protected final static int NEXT_PAGINATION_TAG = 19860116;
    protected final static int PREVIUOS_PAGINATION_TAG = 19860117;

    final static String PRODUCT_INFO_LBL = "PRODUCT INFO";
    final static String PRODUCT_NAME_LBL = "PRODUCT\nNAME";
    final static String PRODUCT_UNIT_LBL = "   UNIT   ";

    final static String STOCK_AVAILABILITY_LBL = "STOCK\nAVAILABILITY";
    final static String NO_STOCK_LBL = "NO STOCK";
    final static String WITH_STOCK_LBL = "WITH STOCK           ";

    final static String STOCK_WEIGHT_LBL = "STOCK WEIGHT";
    final static String LOW_LBL = "    LOW    ";
    final static String MEDIUM = "   MEDIUM   ";
    final static String HIGH = "    HIGH    ";


    LinearLayout mLeftSecondLevelHeaderLinear;
    LinearLayout mRightThirdLevelHeaderLinear;



    List<View> mHeaderFirstColumnViews = new ArrayList<>();
    List<View> mHeaderSecondColumnViews = new ArrayList<>();


    /**
     * @nextTextView and @previousTextView = for pagination
     */
    TextView nextTextView;
    TextView previousTextView;


    LinkedHashMap<Object, Object[]> mLeftHeaders = new LinkedHashMap<Object, Object[]>();
    LinkedHashMap<Object, Object[]> mRightHeaders = new LinkedHashMap<Object, Object[]>();

    Context mContext;

    boolean isTwoColumn = true;

    List<Integer> mSecondColumnsWidth = new ArrayList<>();

    CustomTableHeaderUtils(CustomTable customTable){


        mLeftSecondLevelHeaderLinear = customTable.mLeftSecondLevelHeaderLinear;
        mRightThirdLevelHeaderLinear = customTable.mRightThirdLevelHeaderLinear;

        mContext = customTable.getContext();

        this.initHeader();
        this.init2();


        this.resizeHeaderFirstColumnHeight(this.getViewWithHeighestHeight(mHeaderFirstColumnViews));
        this.resizeHeaderSecondColumnHeight(this.getViewWithHeighestHeight(mHeaderSecondColumnViews));

        fitLayoutWidthOnScreen();

    }

    private void initHeader(){
        mLeftHeaders.put(PRODUCT_INFO_LBL,new String[]{PRODUCT_NAME_LBL,PRODUCT_UNIT_LBL});
        mRightHeaders.put(STOCK_AVAILABILITY_LBL,new String[]{NO_STOCK_LBL,WITH_STOCK_LBL});
        mRightHeaders.put(STOCK_WEIGHT_LBL,new String[]{LOW_LBL,MEDIUM,HIGH});
    }

    private  void init2(){

        /**
         * left table setup
         */
        for(Map.Entry<Object, Object[]> header : mLeftHeaders.entrySet()){


            LinearLayout cellLayout = new LinearLayout(mContext);
            cellLayout.setOrientation(LinearLayout.VERTICAL);


            String firstColumnHeaderLbl = (String) header.getKey();
            String[] secondColumnHeaderLbls = (String[]) header.getValue();


            if(isTwoColumn){


                LinearLayout.LayoutParams firstColumnLinearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                firstColumnLinearParams.weight = 1;

                LinearLayout firstColumnLinear = new LinearLayout(mContext);
                firstColumnLinear.setOrientation(LinearLayout.HORIZONTAL);
                firstColumnLinear.setLayoutParams(firstColumnLinearParams);

                LinearLayout.LayoutParams firstColumnTextViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                firstColumnTextViewParams.weight = 1;
                firstColumnTextViewParams.setMargins(1,1,1,1);

                TextView firstColumnTextView = new TextView(mContext);
                firstColumnTextView.setText(firstColumnHeaderLbl);
                firstColumnTextView.setPadding(PADDING,PADDING,PADDING,PADDING);
                firstColumnTextView.setBackgroundColor(CustomTable.HEADER_BACKROUND_COLOR);
                firstColumnTextView.setLayoutParams(firstColumnTextViewParams);
                firstColumnTextView.setGravity(Gravity.CENTER);

                firstColumnLinear.addView(firstColumnTextView);


                mHeaderFirstColumnViews.add(firstColumnLinear);

                cellLayout.addView(firstColumnLinear);

            }
            LinearLayout secondColumnLinear = new LinearLayout(mContext);
            secondColumnLinear.setOrientation(LinearLayout.HORIZONTAL);;

            int secondColumnLabelCount = secondColumnHeaderLbls.length;

            for(int x = 0 ; x < secondColumnLabelCount ; x++){

                String secondColumnLabel = secondColumnHeaderLbls[x];
                LinearLayout.LayoutParams secondColumnTextViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                secondColumnTextViewParams.setMargins(1,1,1,1);
                secondColumnTextViewParams.weight = 1;

                if(secondColumnLabel.equals(PRODUCT_NAME_LBL)){

                    LinearLayout withPaginationLinear = this.paginationLinearLayout(secondColumnLabel);
                    withPaginationLinear.setBackgroundColor(CustomTable.HEADER_BACKROUND_COLOR);
                    withPaginationLinear.setLayoutParams(secondColumnTextViewParams);
                    secondColumnLinear.addView(withPaginationLinear);


                }else{

                    TextView secondColumnTextView = new TextView(mContext);
                    secondColumnTextView.setText(secondColumnLabel);
                    secondColumnTextView.setGravity(Gravity.CENTER);
                    secondColumnTextView.setPadding(PADDING,PADDING,PADDING,PADDING);
                    secondColumnTextView.setLayoutParams(secondColumnTextViewParams);
                    secondColumnTextView.setBackgroundColor(CustomTable.HEADER_BACKROUND_COLOR);

                    secondColumnLinear.addView(secondColumnTextView);

                }





            }

            mHeaderSecondColumnViews.add(secondColumnLinear);


            cellLayout.addView(secondColumnLinear);

            mLeftSecondLevelHeaderLinear.addView(cellLayout);


        }
        /**
         * right table setup
         */
        for(Map.Entry<Object, Object[]> header : mRightHeaders.entrySet()){


            LinearLayout cellLayout = new LinearLayout(mContext);
            cellLayout.setOrientation(LinearLayout.VERTICAL);


            String firstColumnHeaderLbl = (String) header.getKey();
            String[] secondColumnHeaderLbls = (String[]) header.getValue();


            if(isTwoColumn) {

                LinearLayout.LayoutParams firstColumnLinearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                firstColumnLinearParams.weight = 1;

                LinearLayout firstColumnLinear = new LinearLayout(mContext);
                firstColumnLinear.setOrientation(LinearLayout.HORIZONTAL);
                firstColumnLinear.setLayoutParams(firstColumnLinearParams);

                LinearLayout.LayoutParams firstColumnTextViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                firstColumnTextViewParams.weight = 1;
                firstColumnTextViewParams.setMargins(1, 1, 1, 1);

                TextView firstColumnTextView = new TextView(mContext);
                firstColumnTextView.setText(firstColumnHeaderLbl);
                firstColumnTextView.setPadding(PADDING, PADDING, PADDING, PADDING);
                firstColumnTextView.setBackgroundColor(CustomTable.HEADER_BACKROUND_COLOR);
                firstColumnLinear.addView(firstColumnTextView);
                firstColumnTextView.setLayoutParams(firstColumnTextViewParams);
                firstColumnTextView.setGravity(Gravity.CENTER);

                mHeaderFirstColumnViews.add(firstColumnLinear);
                cellLayout.addView(firstColumnLinear);
            }

            LinearLayout secondColumnLinear = new LinearLayout(mContext);
            secondColumnLinear.setOrientation(LinearLayout.HORIZONTAL);;

            int secondColumnLabelCount = secondColumnHeaderLbls.length;

            for(int x = 0 ; x < secondColumnLabelCount ; x++){

                LinearLayout.LayoutParams secondColumnTextViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                secondColumnTextViewParams.setMargins(1,1,1,1);
                secondColumnTextViewParams.weight = 1;

                TextView secondColumnTextView = new TextView(mContext);
                secondColumnTextView.setText(secondColumnHeaderLbls[x]);
                secondColumnTextView.setGravity(Gravity.CENTER);
                secondColumnTextView.setPadding(PADDING,PADDING,PADDING,PADDING);

                secondColumnTextView.setLayoutParams(secondColumnTextViewParams);
                secondColumnTextView.setBackgroundColor(CustomTable.HEADER_BACKROUND_COLOR);
                secondColumnLinear.addView(secondColumnTextView);




            }

            mHeaderSecondColumnViews.add(secondColumnLinear);


            cellLayout.addView(secondColumnLinear);

            mRightThirdLevelHeaderLinear.addView(cellLayout);


        }
    }

    /**
     * layout for pagination
     * @param label
     * @return
     */
    private LinearLayout paginationLinearLayout(String label){

        LinearLayout paginationLinearLayout = new LinearLayout(mContext);


        TextView labelTextView = new TextView(mContext);
        labelTextView.setGravity(Gravity.CENTER);
        labelTextView.setText(label);


        LinearLayout.LayoutParams labelTextViewParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        labelTextViewParams.weight = 1;

        this.nextTextView = new TextView(mContext);
        this.nextTextView.setText("   "+NEXT_ARROW+"   ");
        this.nextTextView.setGravity(Gravity.CENTER);
        this.nextTextView.setTag(NEXT_PAGINATION_TAG);

        LinearLayout.LayoutParams nextTextViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

        this.previousTextView = new TextView(mContext);
        this.previousTextView.setText("   "+PREVIOUS_ARROW+"   ");
        this.previousTextView.setGravity(Gravity.CENTER);
        this.previousTextView.setTag(PREVIUOS_PAGINATION_TAG);
        this.previousTextView.setTextColor(CustomTable.HEADER_BACKROUND_COLOR);

        LinearLayout.LayoutParams previousTextViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

        paginationLinearLayout.addView(previousTextView,previousTextViewParams);
        paginationLinearLayout.addView(labelTextView,labelTextViewParams);
        paginationLinearLayout.addView(nextTextView,nextTextViewParams);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            this.nextTextView.setBackground(new CustomStateListDrawable(nextTextView));
            this.previousTextView.setBackground(new CustomStateListDrawable(previousTextView));

        }else{

            this.nextTextView.setBackgroundDrawable(new CustomStateListDrawable(nextTextView));
            this.previousTextView.setBackgroundDrawable(new CustomStateListDrawable(previousTextView));
        }

        return paginationLinearLayout;
    }

    private int getViewWithHeighestHeight(List<View> views){

        int finalHeight = 0;

        for (View view : views){

            int height = ViewSizeUtils.getViewHeight(view);

            if( finalHeight < height){

                finalHeight = height;

            }

        }

        return  finalHeight;
    }
    private void resizeHeaderFirstColumnHeight(int height){


        for (View view : mHeaderFirstColumnViews){

            view.getLayoutParams().height = height;
        }
    }
    private void resizeHeaderSecondColumnHeight(int height){


        for (View view : mHeaderSecondColumnViews){


            view.getLayoutParams().height = height;

        }
    }

    private void fitLayoutWidthOnScreen(){

        int screenWidth = ScreenUtils.getScreenWidth(mContext);
        int totalViewWidth = 0;
        int secondColumnHeaderTotalChildrenViewCount = 0;

        for (View view : mHeaderSecondColumnViews){


            LinearLayout linearLayout = (LinearLayout) view;

            int linearChildrenViewCount = linearLayout.getChildCount();

            secondColumnHeaderTotalChildrenViewCount += linearChildrenViewCount;

            for(int x = 0 ; x < linearChildrenViewCount ; x++){

                totalViewWidth += ViewSizeUtils.getViewWidth(linearLayout.getChildAt(x));
            }

        }

        if(totalViewWidth < screenWidth){


            int availableScreenWidth = screenWidth - totalViewWidth;
            int remainder = screenWidth % totalViewWidth;
            int widthToAddInEachView = availableScreenWidth / secondColumnHeaderTotalChildrenViewCount;


            for (View view : mHeaderSecondColumnViews){


                LinearLayout linearLayout = (LinearLayout) view;

                int linearChildrenViewCount = linearLayout.getChildCount();


                for(int x = 0 ; x < linearChildrenViewCount ; x++){

                    View childView = linearLayout.getChildAt(x);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) childView.getLayoutParams();

                    int viewWidth = params.width <=0 ? ViewSizeUtils.getViewWidth(childView) + widthToAddInEachView : params.width;

                    params.width = viewWidth;

                    mSecondColumnsWidth.add(viewWidth);


                }

            }
        }else{
            /**
             * just get width
             */
            for (View view : mHeaderSecondColumnViews){


                LinearLayout linearLayout = (LinearLayout) view;

                int linearChildrenViewCount = linearLayout.getChildCount();


                for(int x = 0 ; x < linearChildrenViewCount ; x++){

                    View childView = linearLayout.getChildAt(x);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) childView.getLayoutParams();

                    int viewWidth = params.width <=0 ? ViewSizeUtils.getViewWidth(childView): params.width;

                    params.width = viewWidth;
                    mSecondColumnsWidth.add(viewWidth);


                }

            }
        }
    }

    protected String[] getSecondLeftHeaderLabel(){


        List<String> columns = new ArrayList<>();

        for(Map.Entry<Object, Object[]> header : mLeftHeaders.entrySet()){

            String firstColumnHeaderLbl = (String) header.getKey();
            String[] secondColumnHeaderLbls = (String[]) header.getValue();
            int secondColumnHeaderLblsCount = secondColumnHeaderLbls.length;

            for(int x = 0 ; x < secondColumnHeaderLblsCount ; x++){

                columns.add(secondColumnHeaderLbls[x]);
            }

        }


        return  columns.toArray(new String[columns.size()]);
    }

    protected String[] getSecondRightHeaderLabel(){


        List<String> columns = new ArrayList<>();

        for(Map.Entry<Object, Object[]> header : mRightHeaders.entrySet()){

            String firstColumnHeaderLbl = (String) header.getKey();
            String[] secondColumnHeaderLbls = (String[]) header.getValue();
            int secondColumnHeaderLblsCount = secondColumnHeaderLbls.length;

            for(int x = 0 ; x < secondColumnHeaderLblsCount ; x++){

                columns.add(secondColumnHeaderLbls[x]);
            }

        }


        return  columns.toArray(new String[columns.size()]);

    }

}
