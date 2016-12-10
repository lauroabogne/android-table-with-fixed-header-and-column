package table;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * 
 * @author lauro abogne
 * http://justsimpleinfo.blogspot.com/2015/04/android-scrolling-table-with-fixed.html
 *
 */
public class BodyTable extends HorizontalScrollView {
	LinearLayout generalVerticalLinearLayout;
	LinearLayout headerHorizontalLinearLayout;
	
	ScrollView bodyScrollView;
	LinearLayout bodyHorizontalLinearLayout;
	
	LinkedHashMap<Object, Object[]> headers;
	List<LinearLayout> bodyLinearLayoutTempMem = new ArrayList<LinearLayout>();
	Integer[] headerChildrenWidth;
	String scrollViewTag;
	Table table;
	HeaderRow headerRow;
	
	public BodyTable(Context context,Table table, LinkedHashMap<Object, Object[]> headers, String scrollViewTag ) {
		super(context);
		// TODO Auto-generated constructor stub
		this.headers = headers;
		this.scrollViewTag = scrollViewTag;
		this.table = table;
		
		this.init();
		this.initHeaders();
		this.addBodyVerticalLinearLayout();
	}
	
	public void setHeaderChildrenWidth(Integer[] headerChildrenWidth) {
		this.headerChildrenWidth = headerChildrenWidth;
		
		
	}
	/**
	 * initialization of layouts
	 */
	private void init(){
		this.generalVerticalLinearLayout = new LinearLayout(this.getContext());
		this.generalVerticalLinearLayout.setOrientation(LinearLayout.VERTICAL);
		
		
		this.headerHorizontalLinearLayout = new LinearLayout(this.getContext());
		this.headerHorizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		this.bodyScrollView = new CustomScrollView(this.getContext(),table);
		this.bodyScrollView.setTag(scrollViewTag);
		
		this.bodyHorizontalLinearLayout = new LinearLayout(this.getContext());
		this.bodyHorizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		// add child to each parent
		
		this.generalVerticalLinearLayout.addView(headerHorizontalLinearLayout);
		this.generalVerticalLinearLayout.addView(this.bodyScrollView);
		
		this.bodyScrollView.addView(this.bodyHorizontalLinearLayout);
		
		this.addView(this.generalVerticalLinearLayout);
	}
	
	private void initHeaders(){
		
		
		for(Entry<Object, Object[]> header : headers.entrySet()){
			String key = (String) header.getKey();
			String[] values = (String[]) header.getValue();
			
			
			headerRow = new HeaderRow(table,key, values,scrollViewTag );
			this.headerHorizontalLinearLayout.addView(headerRow);
		}
		
		
	}
	/**
	 * 
	 */
	private void addBodyVerticalLinearLayout(){
		
		int firstLvlHeaderCount = headers.size();
		
		for(int x = 0 ; x < firstLvlHeaderCount ; x++){
			
			LinearLayout bodyLinear = new LinearLayout(this.getContext());
			bodyLinear.setOrientation(LinearLayout.VERTICAL);
			
			bodyLinearLayoutTempMem.add(bodyLinear);
			
			this.bodyHorizontalLinearLayout.addView(bodyLinear);
		}
		
	}
	/**
	 * remove all view in table body
	 * 
	 */
	private void removeView(){
		
		for(LinearLayout lin : bodyLinearLayoutTempMem){
			lin.removeAllViews();
		}
	}
	
	
	final int bgColor = Color.GRAY;
	final int PADDING = 5;
	
	public void loadData(List<Table.Passenger> dataToBeLoad){
		
		this.removeView();
		
		
		int firstLvlHeaderCounts = headers.size();
		
		List<String[]> secondLvlHeader = new ArrayList<String[]>();
		
		for(Entry<Object, Object[]> header : headers.entrySet()){
			
			secondLvlHeader.add((String[]) header.getValue());
			
		}
		
		int passengerCount = dataToBeLoad.size();
		int numbering = ((table.pageNumber -1) * table.pagination) +1;
		
		for(int z = 0 ; z < passengerCount; z++){
			
			int childIndex = 0 ;
			
			for(int x = 0 ; x < firstLvlHeaderCounts ; x++){
			
				LinearLayout bodyLinear = this.bodyLinearLayoutTempMem.get(x);
				
				LinearLayout cellLinear = new LinearLayout(this.getContext());
				cellLinear.setOrientation(LinearLayout.HORIZONTAL);
				bodyLinear.addView(cellLinear);
			
				int secondLvlHeaderCount  = secondLvlHeader.get(x).length;
			
					for(int y = 0 ; y < secondLvlHeaderCount; y++){
						
						int width = headerChildrenWidth[childIndex];
						Table.Passenger passenger = dataToBeLoad.get(z);
						
						if(/*childIndex == 0 &&*/ scrollViewTag == Table.LEFT_BODY_SCROLLVIEW_TAG){
							
							if(y == 0){
								// name
								cellLinear.addView(this.textView((numbering++)+")\n"+passenger.name,width));
							}else if( y == 1){
								// gender
								cellLinear.addView(this.textView(passenger.gender+"",width));
							}
							
							
						}else{
							// child will be added in right 
							if(x == 0){
								// ticket info
								
								if(y== 0){
									
									cellLinear.addView(this.textView(passenger.validUntil,width));
									
								}else if(y== 1){
									
									cellLinear.addView(this.textView(passenger.ticketNum+"",width));
									
								}else if(y== 2){
									
									cellLinear.addView(this.textView(passenger.setSequence+"",width));
									
								}else if(y== 3){
									
									cellLinear.addView(this.textView(passenger.setSequence+"",width));
									
								}else{
									cellLinear.addView(this.textView("ddd",width));
								}
								
							}else if(x == 1){
								// country info
								
								if(y== 0){
									// country from
									cellLinear.addView(this.textView(passenger.originCountry,width));
									
								}else if(y== 1){
									// country to
									cellLinear.addView(this.textView(passenger.destinationCountry,width));
									
								}else{
									cellLinear.addView(this.textView("",width));
								}
								
							}else{
								cellLinear.addView(this.textView("",width));
							}
							
							
							
						}
						
				
						
					
						childIndex ++;
					
				}
			
			}
		}
		
		
		
		
		
	}
	
	
	/**
	 * 
	 * @param label
	 * @param width
	 * @return
	 */
	private TextView textView(String label, int width){
		LinearLayout.LayoutParams firstLvlTextViewParams = new LinearLayout.LayoutParams(width, LayoutParams.MATCH_PARENT);
		firstLvlTextViewParams.setMargins(1, 1, 1, 1);
		firstLvlTextViewParams.weight = 1;
		
		TextView textView = new TextView(this.getContext());
		textView.setText(label);
		textView.setPadding(PADDING, PADDING, PADDING, PADDING);
		textView.setBackgroundColor(Table.BODY_BACKROUND_COLOR);
		textView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		textView.setLayoutParams(firstLvlTextViewParams);
		
		return textView;
	}
	
}
