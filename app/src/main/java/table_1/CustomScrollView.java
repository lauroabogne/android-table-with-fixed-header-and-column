package table_1;

import android.widget.ScrollView;


/**
 * Created by Lauro-PC on 4/14/2017.
 */

 class CustomScrollView extends ScrollView {

    CustomTable mCustomTable;

     CustomScrollView(CustomTable customTable) {
        super(customTable.getContext());

         mCustomTable = customTable;


     }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {

        //super.onScrollChanged( l,  t,  oldl,  oldt);

        if(this.getTag() ==CustomTable.SCROLLVIEW_TAGS.LEFT_TABLE_SCROLLVIEW){

            mCustomTable.mRightThirdLevelBodyScrollView.scrollTo(0, t);


        }else{



            mCustomTable.mLeftSecondLevelBodyScrollView.scrollTo(0,t);


        }

    }
}
