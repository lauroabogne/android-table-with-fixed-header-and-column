package table_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Lauro-PC on 4/16/2017.
 */

public class CustomTableBodyUtils {

    private final static int MARGIN_TOP = 1;
    private final static int MARGIN_BOTTOM = 1;
    private final static int MARGIN_LEFT = 1;
    private final static int MARGIN_RIGHT = 1;
    LinearLayout mLeftThirdLevelBodyLinear;
    LinearLayout mRightFourthLevelBodyLinear;

    CustomTable mCustomTable;

    Context mContext;


    List<List<LinearLayout>> mColumnLinearLayoutTempStorages = new ArrayList<>();
    ArrayList<Product> mProducts = new ArrayList<>();

    int mChildrenViewNeedToRenderToMeasureAlignHeights = 0;
    int waitToRenderNumberOfView = 0;

    CustomTableBodyUtils(CustomTable customTable){

        mCustomTable = customTable;
        mContext = customTable.getContext();

        mProducts = initData();
        mLeftThirdLevelBodyLinear = customTable.mLeftThirdLevelBodyLinear;
        mRightFourthLevelBodyLinear = customTable.mRightFourthLevelBodyLinear;



        this.waitToRenderNumberOfView = renderNumberOfViewBeforeMeasuring(mProducts);

        this.setupBody();
    }

    private int renderNumberOfViewBeforeMeasuring(ArrayList<Product> mProducts){
        int productCount = mProducts.size();

        int numberOfUom = 0;
        for(int x = 0 ; x < productCount ; x++){

            Product product = mProducts.get(x);
            int uomCount = product.unitOfMeasures.size();

            if(uomCount > 0){

                numberOfUom = numberOfUom + uomCount;



            }else{
                numberOfUom = numberOfUom + 1;
            }
        }

        /**
         * -1 for product header
         * -mProducts.size() for product row
         */

        Log.e("number of views",numberOfUom+"*"+ mCustomTable.mCustomTableHeaderUtils.mSecondColumnsWidth.size()+"- 1)+ -"+ mProducts.size());
        return  numberOfUom * (mCustomTable.mCustomTableHeaderUtils.mSecondColumnsWidth.size() - 1);
    }
    private ArrayList<Product> initData(){

        ArrayList<Product> products = new ArrayList<>();

        for(int x = 0 ; x <20  ;x++){

            Product product = new Product();

            if(x == 0){

                product.name = "Product "+x;

            }else{

                product.name = "Product ndsfd"+x;

            }

            product.itemCode = "product code"+x;

            ArrayList<ProductUnitOfMeasure> unitOfMeasures = new ArrayList<>();
            product.unitOfMeasures = unitOfMeasures;
            products.add(product);

           /*if(x==0){

               *//**
                * just to simulate that product has no unit of measure
                *//*
                continue;
            }*/


            if(x==1){

                for (int y = 0; y < 1; y++) {

                    ProductUnitOfMeasure productUnitOfMeasure = new ProductUnitOfMeasure();

                    productUnitOfMeasure.name = "Unit " + x + "" + y;


                    productUnitOfMeasure.productItemCode = "product code" + x;
                    productUnitOfMeasure.priceWithTax = (y + 1 + x);

                    unitOfMeasures.add(productUnitOfMeasure);


                }

            }else {
                for (int y = 0; y < 2; y++) {

                    ProductUnitOfMeasure productUnitOfMeasure = new ProductUnitOfMeasure();
                    if (x == 0 && y == 0) {
                        productUnitOfMeasure.name = "Too long unit of measures";
                    } else {
                        productUnitOfMeasure.name = "Unit ";
                    }

                    productUnitOfMeasure.productItemCode = "product code" + x;
                    productUnitOfMeasure.priceWithTax = (y + 1 + x);

                    unitOfMeasures.add(productUnitOfMeasure);


                }

            }





        }

        return products;
    }





    private void setupBody(){

        String[] leftColumnLabel = mCustomTable.mCustomTableHeaderUtils.getSecondLeftHeaderLabel();
        int leftColumnLabelCount = leftColumnLabel.length;

        String[] rightColumnLabel = mCustomTable.mCustomTableHeaderUtils.getSecondRightHeaderLabel();
        int rightColumnLabelCount = rightColumnLabel.length;


        for(Product product : mProducts ){


            List<LinearLayout> columnLinearLayoutTempStorage = new ArrayList<>();

            mColumnLinearLayoutTempStorages.add(columnLinearLayoutTempStorage);


            LinearLayout leftProductLinearLayout = new LinearLayout(mContext);
            leftProductLinearLayout.setTag(product);

            LinearLayout rightProductLinearLayout = new LinearLayout(mContext);
            rightProductLinearLayout.setTag(product);


            ArrayList<ProductUnitOfMeasure> unitOfMeasures = product.unitOfMeasures;
            int unitOfMeasureCount = unitOfMeasures.size();

            boolean doProductHasUom  = unitOfMeasureCount > 0 ? true :false;

            /**
             * for left table body
             */

            for(int x = 0 ; x < leftColumnLabelCount ; x++){

                String label = leftColumnLabel[x];


                if(label.equals(CustomTableHeaderUtils.PRODUCT_NAME_LBL)){


                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mCustomTable.mCustomTableHeaderUtils.mSecondColumnsWidth.get(x), LinearLayout.LayoutParams.MATCH_PARENT);
                    layoutParams.setMargins(CustomTableBodyUtils.MARGIN_LEFT, CustomTableBodyUtils.MARGIN_TOP, CustomTableBodyUtils.MARGIN_RIGHT, CustomTableBodyUtils.MARGIN_BOTTOM);

                    LinearLayout leftBodyTableLinearLayout = new LinearLayout(mContext);
                    leftBodyTableLinearLayout.setLayoutParams(layoutParams);


                    LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


                    TextView textView = new TextView(mContext);
                    textView.setText(product.name);
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                    textView.setBackgroundColor(CustomTable.BODY_BACKROUND_COLOR);
                    textView.setLayoutParams(textViewParams);
                    leftBodyTableLinearLayout.addView(textView);

                    leftProductLinearLayout.addView(leftBodyTableLinearLayout);


                }else if(label.equals(CustomTableHeaderUtils.PRODUCT_UNIT_LBL)){


                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mCustomTable.mCustomTableHeaderUtils.mSecondColumnsWidth.get(x), LinearLayout.LayoutParams.WRAP_CONTENT);
                    //layoutParams.setMargins(CustomTableBodyUtils.MARGIN_LEFT, CustomTableBodyUtils.MARGIN_TOP, CustomTableBodyUtils.MARGIN_RIGHT, CustomTableBodyUtils.MARGIN_BOTTOM);

                    layoutParams.weight = 1;

                    LinearLayout leftBodyTableLinearLayout = new LinearLayout(mContext);
                    leftBodyTableLinearLayout.setLayoutParams(layoutParams);
                    leftBodyTableLinearLayout.setOrientation(LinearLayout.VERTICAL);
                    leftBodyTableLinearLayout.setTag(mProducts.indexOf(product));

                    columnLinearLayoutTempStorage.add(leftBodyTableLinearLayout);


                    if(doProductHasUom){

                        for(int xy = 0 ; xy < unitOfMeasureCount ; xy++){

                            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams1.setMargins(CustomTableBodyUtils.MARGIN_LEFT, CustomTableBodyUtils.MARGIN_TOP, CustomTableBodyUtils.MARGIN_RIGHT, CustomTableBodyUtils.MARGIN_BOTTOM);
                            layoutParams1.weight = 1;

                            String unitOfMeasure = unitOfMeasures.get(xy).name;



                            TextView textView = new CustomTextView(mContext,false);


                            textView.setPadding(CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING);
                            textView.setText(unitOfMeasure+"---");
                            textView.setBackgroundColor(CustomTable.BODY_BACKROUND_COLOR);

                            leftBodyTableLinearLayout.addView(textView,layoutParams1);



                        }

                    }else{

                        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams1.setMargins(CustomTableBodyUtils.MARGIN_LEFT, CustomTableBodyUtils.MARGIN_TOP, CustomTableBodyUtils.MARGIN_RIGHT, CustomTableBodyUtils.MARGIN_BOTTOM);
                        layoutParams1.weight = 1;

                        String unitOfMeasure = "NO UNIT OF MEASURE";



                        TextView textView = new CustomTextView(mContext,true);

                        textView.setPadding(CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING);
                        textView.setText(unitOfMeasure);
                        textView.setBackgroundColor(CustomTable.BODY_BACKROUND_COLOR);

                        leftBodyTableLinearLayout.addView(textView,layoutParams1);



                    }



                    leftProductLinearLayout.addView(leftBodyTableLinearLayout);


                }

            }





            mLeftThirdLevelBodyLinear.addView(leftProductLinearLayout);


            for(int x = 0 ; x < rightColumnLabelCount ; x++) {

                String label = rightColumnLabel[x];

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mCustomTable.mCustomTableHeaderUtils.mSecondColumnsWidth.get(x + leftColumnLabelCount), LinearLayout.LayoutParams.WRAP_CONTENT);

                layoutParams.weight = 1;

                LinearLayout leftBodyTableLinearLayout = new LinearLayout(mContext);
                leftBodyTableLinearLayout.setOrientation(LinearLayout.VERTICAL);
                leftBodyTableLinearLayout.setLayoutParams(layoutParams);
                leftBodyTableLinearLayout.setTag(mProducts.indexOf(product));



                columnLinearLayoutTempStorage.add(leftBodyTableLinearLayout);



                if(label.equalsIgnoreCase(CustomTableHeaderUtils.NO_STOCK_LBL)){

                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(mCustomTable.mCustomTableHeaderUtils.mSecondColumnsWidth.get(x + leftColumnLabelCount), LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams1.setMargins(CustomTableBodyUtils.MARGIN_LEFT, CustomTableBodyUtils.MARGIN_TOP, CustomTableBodyUtils.MARGIN_RIGHT, CustomTableBodyUtils.MARGIN_BOTTOM);
                    layoutParams1.weight = 1;

                    String unitOfMeasure = "ONE CELL ONE CELL";

                    TextView textView = new CustomTextView(mContext,true);

                    textView.setPadding(CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING);
                    textView.setBackgroundColor(CustomTable.BODY_BACKROUND_COLOR);
                    textView.setText(unitOfMeasure);
                    textView.setGravity(Gravity.CENTER);

                    leftBodyTableLinearLayout.addView(textView,layoutParams1);



                }else if(label.equalsIgnoreCase(CustomTableHeaderUtils.WITH_STOCK_LBL)){

                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(mCustomTable.mCustomTableHeaderUtils.mSecondColumnsWidth.get(x + leftColumnLabelCount), LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams1.setMargins(CustomTableBodyUtils.MARGIN_LEFT, CustomTableBodyUtils.MARGIN_TOP, CustomTableBodyUtils.MARGIN_RIGHT, CustomTableBodyUtils.MARGIN_BOTTOM);
                    layoutParams1.weight = 1;

                    String unitOfMeasure = "ONE CELL\n\n\n\n\n\nONE CELL";

                    TextView textView = new CustomTextView(mContext,true);

                    textView.setPadding(CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING);
                    textView.setBackgroundColor(CustomTable.BODY_BACKROUND_COLOR);
                    textView.setText(unitOfMeasure);
                    textView.setGravity(Gravity.CENTER);

                    leftBodyTableLinearLayout.addView(textView,layoutParams1);
                }else{

                    if(doProductHasUom){

                        for(int xy = 0 ; xy < unitOfMeasureCount ; xy++){

                            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(mCustomTable.mCustomTableHeaderUtils.mSecondColumnsWidth.get(x + leftColumnLabelCount), LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams1.setMargins(CustomTableBodyUtils.MARGIN_LEFT, CustomTableBodyUtils.MARGIN_TOP, CustomTableBodyUtils.MARGIN_RIGHT, CustomTableBodyUtils.MARGIN_BOTTOM);
                            layoutParams1.weight = 1;

                            String unitOfMeasure = unitOfMeasures.get(xy).name;



                            CustomTextView textView = new CustomTextView(mContext,false);

                            textView.setPadding(CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING);
                            textView.setBackgroundColor(CustomTable.BODY_BACKROUND_COLOR);

                            if(xy == 0 && mProducts.get(0) == product ){

                                textView.setText("");


                            }else if(xy == 1 && mProducts.get(0) == product ){

                                textView.setText("");

                            }else{
                                textView.setText("");
                            }

                            leftBodyTableLinearLayout.addView(textView,layoutParams1);


                        }

                    }else{
                        /**
                         * product no unit of measures
                         */

                        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(mCustomTable.mCustomTableHeaderUtils.mSecondColumnsWidth.get(x + leftColumnLabelCount), LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams1.setMargins(CustomTableBodyUtils.MARGIN_LEFT, CustomTableBodyUtils.MARGIN_TOP, CustomTableBodyUtils.MARGIN_RIGHT, CustomTableBodyUtils.MARGIN_BOTTOM);
                        layoutParams1.weight = 1;

                        String unitOfMeasure = "NO UNIT OF MEASURE";

                        CustomTextView textView = new CustomTextView(mContext,false);

                        textView.setPadding(CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING);
                        textView.setBackgroundColor(CustomTable.BODY_BACKROUND_COLOR);
                        textView.setText(unitOfMeasure);


                        leftBodyTableLinearLayout.addView(textView,layoutParams1);

                    }
               }



                rightProductLinearLayout.addView(leftBodyTableLinearLayout);

            }
            mRightFourthLevelBodyLinear.addView(rightProductLinearLayout);






        }
    }


    protected static int getIndexOfHeighestHeight(LinkedHashMap<Integer,List<Integer>> heigthsTempStorage){

        int size = heigthsTempStorage.size();

        int heighestHeight = 0 ;
        int indexOfHeighestHeight = 0;

        for(int x = 0 ; x < size; x++){

            int heigthsTempStorage_ = heigthsTempStorage.get(x).get(0);
            if(heighestHeight <= heigthsTempStorage_){

                heighestHeight = heigthsTempStorage_;
                indexOfHeighestHeight = x;

            }


        }

        return indexOfHeighestHeight;

    }
    private void applyHeight(LinkedHashMap<Product, List<View>> views){
        for(Map.Entry<Product, List<View>> header : views.entrySet()){


            List<View> viewValues = header.getValue();

            int finalHeight = 0;

            for(View view : viewValues){

                int height = ViewSizeUtils.getViewHeight(view);

                if( finalHeight < height){
                    finalHeight = height;
                }

            }

            for(View view : viewValues){

                view.getLayoutParams().height = finalHeight;

            }
        }
    }

    protected void measureNow(List<List<LinearLayout>> mColumnLinearLayoutTempStorages, List<Product> productList){


        int mColumnLinearLayoutTempStoragesCount = mColumnLinearLayoutTempStorages.size();

        for(int x = 0 ; x < mColumnLinearLayoutTempStoragesCount ; x++){

            List<LinearLayout> linearLayouts = mColumnLinearLayoutTempStorages.get(x);
            int  linearLayoutCount  = linearLayouts.size();

            LinkedHashMap<Integer,List<Integer>> heigthsTempStorage = new LinkedHashMap<Integer, List<Integer>>();
            int oneCellViewHeight = 0;

            Product product = productList.get(x);
            int uomCount = product.unitOfMeasures.size();

            if(uomCount > 0){

                for(int xy = 0 ; xy < uomCount ; xy++){

                    List<Integer> heightTempStorage = new ArrayList<>();
                    heigthsTempStorage.put(xy,heightTempStorage);

                }

            }else{

                List<Integer> heightTempStorage = new ArrayList<>();
                heigthsTempStorage.put(0,heightTempStorage);
            }



            for(int y = 0 ; y < linearLayoutCount ; y++){

                LinearLayout linearLayout = linearLayouts.get(y);

                int linearLayoutChildrenCount = linearLayout.getChildCount();

                for(int xy = 0 ; xy < linearLayoutChildrenCount ;xy++){



                    CustomTableBodyUtils.CustomTextView textView = (CustomTableBodyUtils.CustomTextView) linearLayout.getChildAt(xy);
                    int height = textView.mMyHeight;

                    if(textView.mIsOneCell){

                        if(oneCellViewHeight < textView.mMyHeight){

                            oneCellViewHeight =  textView.mMyHeight;
                        }


                        continue;
                    }



                    if(heigthsTempStorage.get(xy).size() > 0){

                        int previousHeight = heigthsTempStorage.get(xy).get(0);

                        if(previousHeight < height){
                            heigthsTempStorage.get(xy).clear();
                            heigthsTempStorage.get(xy).add(height);
                        }


                    }else{

                        heigthsTempStorage.get(xy).add(height);

                    }



                }


            }


            int uomViewsTotalHeight = this.totalHeightInEachProductRow(heigthsTempStorage);
            int perUomViewsTotalHeightAndOneCellHeightDifference = perUomViewsTotalHeightAndOneCellHeightDifference(oneCellViewHeight,uomViewsTotalHeight);

            if(perUomViewsTotalHeightAndOneCellHeightDifference > 0){
                /**
                 * one cell textview height is bigger than total uom view height
                 */
                int estimatedNeedToAddHeightToEachRow = perUomViewsTotalHeightAndOneCellHeightDifference / uomCount;
                int remainder = perUomViewsTotalHeightAndOneCellHeightDifference % uomCount;
                int needToAddHeightToEachRow = estimatedNeedToAddHeightToEachRow + (remainder ==0 ? 0 :  1);

                this.addModifyHeights(heigthsTempStorage,needToAddHeightToEachRow,oneCellViewHeight);
                /**
                 * important
                 */
                oneCellViewHeight = oneCellViewHeight + (remainder == 0 ? 0 : uomCount -1 );
            }

            for(int y = 0 ; y < linearLayoutCount ; y++){

                LinearLayout linearLayout = linearLayouts.get(y);

                int linearLayoutChildrenCount = linearLayout.getChildCount();

                boolean hasOneViewOnly = linearLayoutChildrenCount == 1;






                for(int xy = 0 ; xy < linearLayoutChildrenCount ;xy++){



                    CustomTableBodyUtils.CustomTextView textView = (CustomTableBodyUtils.CustomTextView) linearLayout.getChildAt(xy);

                    /*if(textView.mIsOneCell){
                        continue;
                    }*/

                    if(textView.mIsOneCell){

                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();


                        if(uomViewsTotalHeight > oneCellViewHeight){



                            int perUomMarginTopAndBottom = MARGIN_TOP + MARGIN_BOTTOM;
                            int oneCellTopAndBottomMargin = params.topMargin + params.bottomMargin;
                            int totalUomMargin = (uomCount * perUomMarginTopAndBottom) - oneCellTopAndBottomMargin;


                            params.height = (uomViewsTotalHeight + totalUomMargin) ;

                            textView.setLayoutParams(params);


                        }else{

                            int perUomMarginTopAndBottom = MARGIN_TOP + MARGIN_BOTTOM;
                            int oneCellTopAndBottomMargin = params.topMargin + params.bottomMargin;
                            int totalUomMargin = (uomCount * perUomMarginTopAndBottom) - oneCellTopAndBottomMargin;

                            params.height = oneCellViewHeight + totalUomMargin;
                            textView.setLayoutParams(params);



                        }

                        continue;
                    }

                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
                    params.height = heigthsTempStorage.get(xy).get(0);

                    textView.setLayoutParams(params);




                }




            }


        }

    }

    private int totalHeightInEachProductRow(LinkedHashMap<Integer,List<Integer>> heigthsTempStorage){

        int totalHeight = 0;

        for (Integer key : heigthsTempStorage.keySet())
        {
            if(heigthsTempStorage.get(key).size() > 0)
            {
                totalHeight += heigthsTempStorage.get(key).get(0);

            }

        }

        return totalHeight;

    }
    private int perUomViewsTotalHeightAndOneCellHeightDifference( int oneCellHeighestHeight,int totalHeightInEachProductRow){

        return oneCellHeighestHeight - totalHeightInEachProductRow ;
    }

    private void addModifyHeights(LinkedHashMap<Integer,List<Integer>> heigthsTempStorage, int needToAddHeightToEachRow, int heightOfOneCell){

        Log.e("heigthsTempStorage",heigthsTempStorage+"a");
        Log.e("heigthsTempStorage",needToAddHeightToEachRow+" height need to add");
        Log.e("heigthsTempStorage",heightOfOneCell+" heightOfOneCell");

        int tempHeightCount = heigthsTempStorage.size();

        for(int x = 0 ; x < tempHeightCount ; x++){

            List<Integer> heights = heigthsTempStorage.get(x);

            int heightCount = heights.size();

            for(int y =0 ; y < heightCount; y++){

                heights.add(y,heights.get(y)+needToAddHeightToEachRow);


            }

           // Log.e("heigthsTempStorage",heights+" temp");
        }

        /*for (Integer key : heigthsTempStorage.keySet())
        {
            if(heigthsTempStorage.get(key).size() > 0)
            {
                List<Integer> newHeights = new ArrayList<>();

                newHeights.add(heigthsTempStorage.get(key).get(0)+needToAddHeightToEachRow);
                heigthsTempStorage.remove(key);
                heigthsTempStorage.put(key,newHeights);

                Log.e("heigthsTempStorage",heigthsTempStorage.get(key)+"");


            }

        }*/

        Log.e("heigthsTempStorage",heigthsTempStorage+" b");


    }




    int numberOfViewRendered = 0;

    /**
     *
     */
    public class CustomTextView extends  TextView{

        boolean mIsOneCell = false;
        int mMyHeight = 0;
        boolean drawned = false;
         public CustomTextView(Context context,boolean isOneCell) {
            super(context);

             mIsOneCell = isOneCell;
             mChildrenViewNeedToRenderToMeasureAlignHeights++;


        }


        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            final int newHeight= MeasureSpec.getSize(heightMeasureSpec);
            final int newWidth= MeasureSpec.getSize(widthMeasureSpec);

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);


            if(mIsOneCell && drawned){

            }

        }



        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);

            if(mIsOneCell){

            }
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            if(mIsOneCell){

            }

            drawned = true;



        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);




            if(drawned){

                return;
            }

            drawned = true;
            /**
             * set height
             */
            this.mMyHeight = h;


            if(mIsOneCell){

                Log.e("one cell height",h+"");

            }

            numberOfViewRendered++;

            if(numberOfViewRendered >= mChildrenViewNeedToRenderToMeasureAlignHeights){

               CustomTableBodyUtils.this.measureNow(mColumnLinearLayoutTempStorages, mProducts);


            }



        }
    }



}
