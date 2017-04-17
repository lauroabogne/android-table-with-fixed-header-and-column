package table_1;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;

class CustomStateListDrawable extends StateListDrawable {

	/**
	 * 
	 * @param view = is the source of default background 
	 */
    protected CustomStateListDrawable(View view) {

        int stateFocused = android.R.attr.state_focused;
        int statePressed = android.R.attr.state_pressed;
        int stateSelected = android.R.attr.state_selected;
        
       
        this.addState(new int[]{ statePressed },new ColorDrawable(Color.LTGRAY));
        
        this.addState(new int[]{-stateFocused, -statePressed, -stateSelected}, view.getBackground());


    }
    
  
}
