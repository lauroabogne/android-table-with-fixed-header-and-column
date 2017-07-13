package table_1;

import android.content.Context;
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

                product.name = "Product  asdf asdf nasdf asdf asdf asdf asdf asd fasdf  asdf asdf asf "+x;

            }else{

                product.name = "Product \n \n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\ndsfd"+x;

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

                    productUnitOfMeasure.name = "Unit " + x + " " + y;


                    productUnitOfMeasure.productItemCode = "product code" + x;
                    productUnitOfMeasure.priceWithTax = (y + 1 + x);

                    unitOfMeasures.add(productUnitOfMeasure);


                }

            }else {
                for (int y = 0; y < 3; y++) {

                    ProductUnitOfMeasure productUnitOfMeasure = new ProductUnitOfMeasure();
                    if (x == 0 && y == 0) {
                        productUnitOfMeasure.name = "Too long unit\n\n\n\n\nof measures";
                    } else {
                        productUnitOfMeasure.name = "Unit " + x + "" + y;
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
                    layoutParams.setMargins(1, 1, 1, 1);

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


                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mCustomTable.mCustomTableHeaderUtils.mSecondColumnsWidth.get(x), LinearLayout.LayoutParams.MATCH_PARENT);

                    layoutParams.weight = 1;

                    LinearLayout leftBodyTableLinearLayout = new LinearLayout(mContext);
                    leftBodyTableLinearLayout.setLayoutParams(layoutParams);
                    leftBodyTableLinearLayout.setOrientation(LinearLayout.VERTICAL);
                    leftBodyTableLinearLayout.setTag(mProducts.indexOf(product));

                    columnLinearLayoutTempStorage.add(leftBodyTableLinearLayout);


                    if(doProductHasUom){

                        for(int xy = 0 ; xy < unitOfMeasureCount ; xy++){

                            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams1.setMargins(1, 1, 1, 1);
                            layoutParams1.weight = 1;

                            String unitOfMeasure = unitOfMeasures.get(xy).name;



                            TextView textView = new CustomTextView(mContext);


                            textView.setPadding(CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING);
                            textView.setText(unitOfMeasure+"---");
                            textView.setBackgroundColor(CustomTable.BODY_BACKROUND_COLOR);

                            leftBodyTableLinearLayout.addView(textView,layoutParams1);



                        }

                    }else{

                        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams1.setMargins(1, 1, 1, 1);
                        layoutParams1.weight = 1;

                        String unitOfMeasure = "NO UNIT OF MEASURE";



                        TextView textView = new CustomTextView(mContext);

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

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mCustomTable.mCustomTableHeaderUtils.mSecondColumnsWidth.get(x + leftColumnLabelCount), LinearLayout.LayoutParams.MATCH_PARENT);

                layoutParams.weight = 1;

                LinearLayout leftBodyTableLinearLayout = new LinearLayout(mContext);
                leftBodyTableLinearLayout.setOrientation(LinearLayout.VERTICAL);
                leftBodyTableLinearLayout.setLayoutParams(layoutParams);
                leftBodyTableLinearLayout.setTag(mProducts.indexOf(product));



                columnLinearLayoutTempStorage.add(leftBodyTableLinearLayout);



                if(label.equalsIgnoreCase(CustomTableHeaderUtils.NO_STOCK_LBL)){

                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(mCustomTable.mCustomTableHeaderUtils.mSecondColumnsWidth.get(x + leftColumnLabelCount), LinearLayout.LayoutParams.MATCH_PARENT);
                    layoutParams1.setMargins(1, 1, 1, 1);
                    layoutParams1.weight = 1;

                    String unitOfMeasure = "ONE CELL ONLY";

                    TextView textView = new CustomTextView(mContext);

                    textView.setPadding(CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING);
                    textView.setBackgroundColor(CustomTable.BODY_BACKROUND_COLOR);
                    textView.setText(unitOfMeasure);
                    textView.setGravity(Gravity.CENTER);

                    leftBodyTableLinearLayout.addView(textView,layoutParams1);



                }else{

                    if(doProductHasUom){

                        for(int xy = 0 ; xy < unitOfMeasureCount ; xy++){

                            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(mCustomTable.mCustomTableHeaderUtils.mSecondColumnsWidth.get(x + leftColumnLabelCount), LinearLayout.LayoutParams.MATCH_PARENT);
                            layoutParams1.setMargins(1, 1, 1, 1);
                            layoutParams1.weight = 1;

                            String unitOfMeasure = unitOfMeasures.get(xy).name;



                            TextView textView = new CustomTextView(mContext);

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

                        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(mCustomTable.mCustomTableHeaderUtils.mSecondColumnsWidth.get(x + leftColumnLabelCount), LinearLayout.LayoutParams.MATCH_PARENT);
                        layoutParams1.setMargins(1, 1, 1, 1);
                        layoutParams1.weight = 1;

                        String unitOfMeasure = "NO UNIT OF MEASURE";

                        TextView textView = new TextView(mContext);

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


                if(linearLayoutChildrenCount < uomCount){

                    continue;
                }
                for(int xy = 0 ; xy < linearLayoutChildrenCount ;xy++){



                    CustomTableBodyUtils.CustomTextView textView = (CustomTableBodyUtils.CustomTextView) linearLayout.getChildAt(xy);
                    int height = textView.myHeight;


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

                for(int xy = 0 ; xy < linearLayoutChildrenCount ;xy++){



                    CustomTableBodyUtils.CustomTextView textView = (CustomTableBodyUtils.CustomTextView) linearLayout.getChildAt(xy);

                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
                    params.height = heigthsTempStorage.get(xy).get(0);

                    textView.setLayoutParams(params);


                }


            }


        }

    }

    /**
     *
     */
    /*class CustomTableDataObject{

        List<List<LinearLayout>> mColumnLinearLayoutTempStorages = new ArrayList<>();
        List<Product> productList = new ArrayList<>();

    }*/

    int numberOfViewRendered = 0;

    /**
     *
     */
    public class CustomTextView extends  TextView{

        int myHeight = 0;
        boolean drawned = false;
         public CustomTextView(Context context) {
            super(context);

             mChildrenViewNeedToRenderToMeasureAlignHeights++;


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
            this.myHeight = h;

            numberOfViewRendered++;

            if(numberOfViewRendered >= mChildrenViewNeedToRenderToMeasureAlignHeights){

                CustomTableBodyUtils.this.measureNow(mColumnLinearLayoutTempStorages, mProducts);


            }



        }
    }



}
