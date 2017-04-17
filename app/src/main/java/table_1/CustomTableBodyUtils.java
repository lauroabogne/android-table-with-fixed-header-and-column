package table_1;

import android.content.Context;
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
            product.name = "Product "+x;
            product.itemCode = "product code"+x;

            ArrayList<ProductUnitOfMeasure> unitOfMeasures = new ArrayList<>();

            for(int y = 0 ; y <5 ; y++){

                ProductUnitOfMeasure productUnitOfMeasure = new ProductUnitOfMeasure();
                if(x == 4){
                    productUnitOfMeasure.name = "Unit \n\n"+x+" "+y;
                }else{
                    productUnitOfMeasure.name = "Unit "+x+" "+y;
                }

                productUnitOfMeasure.productItemCode  = "product code"+x;
                productUnitOfMeasure.priceWithTax = (y +1+x);

                unitOfMeasures.add(productUnitOfMeasure);


            }

            product.unitOfMeasures = unitOfMeasures;

            products.add(product);

        }

        return products;
    }

    private void setupBody(){

        String[] leftColumnLabel = mCustomTable.mCustomTableHeaderUtils.getSecondLeftHeaderLabel();
        int leftColumnLabelCount = leftColumnLabel.length;

        String[] rightColumnLabel = mCustomTable.mCustomTableHeaderUtils.getSecondRightHeaderLabel();
        int rightColumnLabelCount = rightColumnLabel.length;


        for(Product product : mProducts ){


            LinkedHashMap<Product, List<View>> views = new LinkedHashMap<Product, List<View>>();

            LinearLayout leftProductLinearLayout = new LinearLayout(mContext);
            LinearLayout rightProductLinearLayout = new LinearLayout(mContext);

            ArrayList<ProductUnitOfMeasure> unitOfMeasures = product.unitOfMeasures;
            int unitOfMeasureCount = unitOfMeasures.size();

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


                    List<View> uomViews = new ArrayList<>();

                    for(int xy = 0 ; xy < unitOfMeasureCount ; xy++){

                        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams1.setMargins(1, 1, 1, 1);
                        layoutParams1.weight = 1;

                        String unitOfMeasure = unitOfMeasures.get(xy).name;

                        TextView textView = new TextView(mContext);

                        textView.setPadding(CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING);
                        textView.setText(unitOfMeasure+" ---");
                        textView.setBackgroundColor(CustomTable.BODY_BACKROUND_COLOR);

                        uomViews.add(textView);

                        leftBodyTableLinearLayout.addView(textView,layoutParams1);

                    }

                    views.put(product,uomViews);

                    leftProductLinearLayout.addView(leftBodyTableLinearLayout);


                }

            }








            mLeftThirdLevelBodyLinear.addView(leftProductLinearLayout);


            for(int x = 0 ; x < rightColumnLabelCount ; x++) {

                String label = rightColumnLabel[x];

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mCustomTable.mCustomTableHeaderUtils.mSecondColumnsWidth.get(x + leftColumnLabelCount), LinearLayout.LayoutParams.MATCH_PARENT);

                layoutParams.weight = 1;

                LinearLayout leftBodyTableLinearLayout = new LinearLayout(mContext);
                leftBodyTableLinearLayout.setLayoutParams(layoutParams);
                leftBodyTableLinearLayout.setOrientation(LinearLayout.VERTICAL);



                List<View> uomViews = views.get(product);

                for(int xy = 0 ; xy < unitOfMeasureCount ; xy++){

                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams1.setMargins(1, 1, 1, 1);
                    layoutParams1.weight = 1;

                    String unitOfMeasure = unitOfMeasures.get(xy).name;


                    TextView textView = new TextView(mContext);
                    textView.setPadding(CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING, CustomTableHeaderUtils.PADDING);
                    textView.setBackgroundColor(CustomTable.BODY_BACKROUND_COLOR);

                    uomViews.add(textView);

                    if(xy == 1 && x ==0){

                        textView.setText(unitOfMeasure+"\n\n test data---");

                    }else{
                        textView.setText(unitOfMeasure+" ---");
                    }

                    leftBodyTableLinearLayout.addView(textView,layoutParams1);
                }
                rightProductLinearLayout.addView(leftBodyTableLinearLayout);

            }
            mRightFourthLevelBodyLinear.addView(rightProductLinearLayout);

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
}
