package table;


import android.content.Context;
import android.widget.ScrollView;
/**
 * 
 * @author lauro abogne
 * http://justsimpleinfo.blogspot.com/2015/04/android-scrolling-table-with-fixed.html
 *
 */
public class CustomScrollView  extends ScrollView{
	
	Table table;
	public CustomScrollView(Context context, Table table) {
		super(context);
		this.table = table;
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		
		final String tag = this.getTag()+"";
		
		if(tag.equalsIgnoreCase(Table.RIGHT_BODY_SCROLLVIEW_TAG)){
			table.leftTable.bodyScrollView.scrollTo(0, t);
			
			
			
		}else{
			table.rightTable.bodyScrollView.scrollTo(0, t);
			
			
			
		}
		
	}
}
