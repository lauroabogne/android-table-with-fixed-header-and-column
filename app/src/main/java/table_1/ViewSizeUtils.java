package table_1;

import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;

/**
 * 
 * @author lauro abogne
 * http://justsimpleinfo.blogspot.com/2015/04/android-scrolling-table-with-fixed.html
 *
 */
public class ViewSizeUtils {

	/**
	 * Get view width
	 * This is not applicable if you set the view width in params
	 * @param view
	 * @return
	 */
	final public static int getViewWidth(View view){
		view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		
		return view.getMeasuredWidth();
	}
	final public static int getViewWidth(View view, int height){
		int widthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		int heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
		
		view.measure(widthSpec, heightSpec);
		return view.getMeasuredWidth();
	}
	final public static int getViewHeight(View view,int width){
		
		int widthSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
		int heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		
		view.measure(widthSpec, heightSpec);
		return view.getMeasuredHeight();
	}
	/**
	 * Get view height
	 * This is not applicable if you set the view height in params
	 * @param view The view that will get the height
	 * @return View height
 	 */
	final public static int getViewHeight(View view){
		view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		return view.getMeasuredHeight();
	}
	
	final static float LOW_LEVEL=0.75f;
	final static float MEDIUM_LEVEL=1.0f;
	final static float HIGH_LEVEL=1.5f;
	final static float X_HIGH_LEVEL = 2.0f;
	final static float XX_HIGH_LEVEL = 3.0f;
	final static float XXX_HIGH_LEVEL = 4.0f;
	
	
	static public float computeForAllDeviceScreenWidth(float widthInMediumDensity, Context context){
		float level = context.getApplicationContext().getResources().getDisplayMetrics().density;
		
		if(level == LOW_LEVEL){
			return  widthInMediumDensity * LOW_LEVEL;
		}else if(level == MEDIUM_LEVEL){
			return widthInMediumDensity;
		}else if(level == HIGH_LEVEL){
			return widthInMediumDensity * HIGH_LEVEL;
		}else if(level == X_HIGH_LEVEL){
			return widthInMediumDensity * X_HIGH_LEVEL;
		}else if(level == XX_HIGH_LEVEL){
			return widthInMediumDensity * XX_HIGH_LEVEL;
		}else if(level == XXX_HIGH_LEVEL){
			return widthInMediumDensity * XXX_HIGH_LEVEL;
		}else{
			return widthInMediumDensity;
		}
	}
	
	static public float computeForAllDeviceHeight(float heightInMediumDensity, Context context){
		float level = context.getApplicationContext().getResources().getDisplayMetrics().density;
		
		if(level == LOW_LEVEL){
			return  heightInMediumDensity * LOW_LEVEL;
		}else if(level == MEDIUM_LEVEL){
			return heightInMediumDensity;
		}else if(level == HIGH_LEVEL){
			return heightInMediumDensity * HIGH_LEVEL;
		}else if(level == X_HIGH_LEVEL){
			return heightInMediumDensity * X_HIGH_LEVEL;
		}else if(level == XX_HIGH_LEVEL){
			return heightInMediumDensity * XX_HIGH_LEVEL;
		}else if(level == XXX_HIGH_LEVEL){
			return heightInMediumDensity * XXX_HIGH_LEVEL;
		}else{
			return heightInMediumDensity;
		}
	}
}
