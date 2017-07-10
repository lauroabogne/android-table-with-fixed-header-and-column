package table_1;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
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


    ArrayList<Product> mProducts;



    CustomTableBodyUtils(CustomTable customTable){

        mCustomTable = customTable;
        mContext = customTable.getContext();

        mLeftThirdLevelBodyLinear = customTable.mLeftThirdLevelBodyLinear;
        mRightFourthLevelBodyLinear = customTable.mRightFourthLevelBodyLinear;

        mProducts = initData();

        this.setupBody();
    }

    private ArrayList<Product> initData(){

        ArrayList<Product> products = new ArrayList<>();

        for(int x = 0 ; x < 20 ;x++){

            Product product = new Product();

            if(x == 0){
                product.name = "Product  asdf asdf asdf asdf asdf asdf asdf asd fasdf  asdf asdf asf "+x;
            }else{
                product.name = "Product "+x;
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


            for(int y = 0 ; y <2 ; y++){

                ProductUnitOfMeasure productUnitOfMeasure = new ProductUnitOfMeasure();
                if(x == 1 && y==0){
                    productUnitOfMeasure.name = "Too long unit of measures";
                }else{
                    productUnitOfMeasure.name = "Unit "+x+" "+y;
                }

                productUnitOfMeasure.productItemCode  = "product code"+x;
                productUnitOfMeasure.priceWithTax = (y +1+x);

                unitOfMeasures.add(productUnitOfMeasure);


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


            LinearLayout leftProductLinearLayout = new LinearLayout(mContext);
            LinearLayout rightProductLinearLayout = new LinearLayout(mContext);



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

                    columnLinearLayoutTempStorage.add(leftBodyTableLinearLayout);


                    if(doProductHasUom){

                        for(int xy = 0 ; xy < unitOfMeasureCount ; xy++){

                            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams1.setMargins(1, 1, 1, 1);
                            layoutParams1.weight = 1;

                            String unitOfMeasure = unitOfMeasures.get(xy).name;

                            TextView textView = new TextView(mContext);

                            textView.setPadding(CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING);
                            textView.setText(unitOfMeasure+" ---");
                            textView.setBackgroundColor(CustomTable.BODY_BACKROUND_COLOR);

                            leftBodyTableLinearLayout.addView(textView,layoutParams1);

                        }

                    }else{

                        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams1.setMargins(1, 1, 1, 1);
                        layoutParams1.weight = 1;

                        String unitOfMeasure = "NO UNIT OF MEASURE";

                        TextView textView = new TextView(mContext);

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


                columnLinearLayoutTempStorage.add(leftBodyTableLinearLayout);



                if(label.equalsIgnoreCase(CustomTableHeaderUtils.WITH_STOCK_LBL)){

                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(mCustomTable.mCustomTableHeaderUtils.mSecondColumnsWidth.get(x + leftColumnLabelCount), LinearLayout.LayoutParams.MATCH_PARENT);
                    layoutParams1.setMargins(1, 1, 1, 1);
                    layoutParams1.weight = 1;

                    String unitOfMeasure = "ONE CELL ONLY";

                    TextView textView = new TextView(mContext);
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


                            TextView textView = new TextView(mContext);
                            textView.setPadding(CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING);
                            textView.setBackgroundColor(CustomTable.BODY_BACKROUND_COLOR);

                            if(xy == 0 && mProducts.get(0) == product ){

                                textView.setText(unitOfMeasure+" test data long uom  super long uom super uom long--- 1");


                            }else if(xy == 1 && mProducts.get(0) == product ){

                                textView.setText(unitOfMeasure+"\n\n\n\n 1");

                            }else{
                                textView.setText(unitOfMeasure+" ---");
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

            this.applyHeightTest(product,columnLinearLayoutTempStorage);



        }
    }

    private void applyHeightTest(Product product, List<LinearLayout> linearLayouts){

        int uomCount = product.unitOfMeasures.size();
        int widthIndex  = 0 ;
        int viewIndex = 0;

        LinkedHashMap<Integer,List<Integer>> heigthsTempStorage = new LinkedHashMap<Integer, List<Integer>>();

        /**
         * init temp storage
         */
        for(int x = 0 ; x < uomCount ; x++){

            List<Integer> heightTempStorage = new ArrayList<>();
            heigthsTempStorage.put(x,heightTempStorage);

        }
        if(uomCount <=0){

            List<Integer> heightTempStorage = new ArrayList<>();
            heigthsTempStorage.put(0,heightTempStorage);
        }



        for(LinearLayout linearLayout : linearLayouts){

            int childrenCount = linearLayout.getChildCount();
            int width =  mCustomTable.mCustomTableHeaderUtils.mSecondColumnsWidth.get(widthIndex+1);

            for(int x  = 0 ; x < childrenCount ; x++){

                int height = ViewSizeUtils.getViewHeight(linearLayout.getChildAt(x),width);

                List<Integer> height_ = heigthsTempStorage.get(viewIndex);

                if(height_.size() > 0){
                    /**
                     * compare
                     */
                    int recordedHeight = height_.get(0);

                    if(recordedHeight < height){

                        height_.set(0,height);

                    }
                }else{
                    /**
                     * add
                     */

                    height_.add(height);
                }

                viewIndex ++;

                if(viewIndex >= uomCount){

                    viewIndex = 0;

                }


            }

            widthIndex++;



        }


        int viewIndex_ = 0;
        for(LinearLayout linearLayout : linearLayouts){

            int childrenCount = linearLayout.getChildCount();

            for(int x  = 0 ; x < childrenCount ; x++){

                View view = linearLayout.getChildAt(x);



                view.getLayoutParams().height = heigthsTempStorage.get( getIndexOfHeighestHeight(heigthsTempStorage)).get(0);



                viewIndex_ ++;

                if(viewIndex_ >= uomCount){

                    viewIndex_ = 0;

                }
            }


        }


    }

    private int getIndexOfHeighestHeight(LinkedHashMap<Integer,List<Integer>> heigthsTempStorage){

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
  /*  private void applyHeightTest(Product product, List<LinearLayout> linearLayouts){

        int uomCount = product.unitOfMeasures.size();
        int widthIndex  = 0 ;
        int viewIndex = 0;

        LinkedHashMap<Integer,List<Integer>> heigthsTempStorage = new LinkedHashMap<Integer, List<Integer>>();

        *//**
         * init temp storage
         *//*
        for(int x = 0 ; x < uomCount ; x++){

            List<Integer> heightTempStorage = new ArrayList<>();
            heigthsTempStorage.put(x,heightTempStorage);

        }
        if(uomCount <=0){

            List<Integer> heightTempStorage = new ArrayList<>();
            heigthsTempStorage.put(0,heightTempStorage);
        }


        for(LinearLayout linearLayout : linearLayouts){

            int childrenCount = linearLayout.getChildCount();
            int width =  mCustomTable.mCustomTableHeaderUtils.mSecondColumnsWidth.get(widthIndex+1);

            for(int x  = 0 ; x < childrenCount ; x++){

                int height = ViewSizeUtils.getViewHeight(linearLayout.getChildAt(x),width);

                List<Integer> height_ = heigthsTempStorage.get(viewIndex);

                if(height_.size() > 0){
                    *//**
                     * compare
                     *//*
                    int recordedHeight = height_.get(0);

                    if(recordedHeight < height){

                        height_.set(0,height);

                    }
                }else{
                    *//**
                     * add
                     *//*

                    height_.add(height);
                }

                viewIndex ++;

                if(viewIndex >= uomCount){

                    viewIndex = 0;

                }


            }

            widthIndex++;



        }


        int viewIndex_ = 0;
        for(LinearLayout linearLayout : linearLayouts){

            int childrenCount = linearLayout.getChildCount();

            for(int x  = 0 ; x < childrenCount ; x++){

                View view = linearLayout.getChildAt(x);
                view.getLayoutParams().height = heigthsTempStorage.get(viewIndex_).get(0);

                viewIndex_ ++;

                if(viewIndex_ >= uomCount){

                    viewIndex_ = 0;

                }
            }

        }

    }*/
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
}
