package table;

import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
/**
 * 
 * @author lauro abogne
 * http://justsimpleinfo.blogspot.com/2015/04/android-scrolling-table-with-fixed.html
 *
 */
class OnClickListenerOfPagination implements OnClickListener {

	Table table;
	OnClickListenerOfPagination(Table table){
		this.table = table;
	}
	@Override
	public void onClick(View view) {
		

		// show loading dialog
		table.loadingDialog.show();
		
		int tag = (Integer) view.getTag();
				
		switch (tag) {
		case HeaderRow.NEXT_PAGINATION_TAG:
			
			
			
			table.pageNumber++;
			
			table.loadData();
			
			
			table.leftTable.headerRow.previousTextView.setTextColor(Color.BLACK);
			table.leftTable.headerRow.previousTextView.setEnabled(true);
			
			if(table.pageNumber == table.totalPage){
				table.leftTable.headerRow.nextTextView.setEnabled(false);
				table.leftTable.headerRow.nextTextView.setTextColor(Table.HEADER_BACKROUND_COLOR);
			}
			
			break;
		case HeaderRow.PREVIUOS_PAGINATION_TAG:
			
			
			
			table.pageNumber--;
			table.loadData();
			
			// set text color black
			table.leftTable.headerRow.nextTextView.setTextColor(Color.BLACK);
			table.leftTable.headerRow.nextTextView.setEnabled(true);
			
			if(table.pageNumber ==  1){
				table.leftTable.headerRow.previousTextView.setEnabled(false);
				table.leftTable.headerRow.previousTextView.setTextColor(Table.HEADER_BACKROUND_COLOR);
				
			}
			
			break;
		default:
			break;
		}		
		
		table.loadingDialog.dismiss();
		
	}

}
