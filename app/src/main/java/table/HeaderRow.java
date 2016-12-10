package table;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 
 * @author lauro abogne
 * http://justsimpleinfo.blogspot.com/2015/04/android-scrolling-table-with-fixed.html
 *
 */
public class HeaderRow extends LinearLayout{
	
	protected final static int NEXT_PAGINATION_TAG = 19860116;
	protected final static int PREVIUOS_PAGINATION_TAG = 19860117;
	final int PADDING = 5;
	LinearLayout firtLvlLinearLayout;
	LinearLayout secondLvlLinearLayout;
	
	/**
	 * @nextTextView and @previousTextView = for pagination
	 */
	TextView nextTextView;
	TextView previousTextView;
	
	Object firstLvlLabel;
	Object[] secondLvlLabel;
	String scrollViewTag;
	
	OnClickListenerOfPagination onClickListenerOfPagination;
	Table table;
	public HeaderRow(Table table, Object firstLvlLabel, Object[] secondLvlLabel, String scrollViewTag) {
		super(table.getContext());
		this.table = table;
		this.firstLvlLabel = firstLvlLabel;
		this.secondLvlLabel = secondLvlLabel;
		this.scrollViewTag = scrollViewTag;
		
		
		this.properties();
		this.init();
		this.initFirstLvlHeaders();
		this.initSecondLvlHeader();
		this.analizeFirstAndSecondHeaderWidth();
	}
	
	private void properties(){
		
		this.setOrientation(LinearLayout.VERTICAL);
	}
	/**
	 * initialization
	 */
	private void init(){
		
		this.onClickListenerOfPagination = new OnClickListenerOfPagination(table);
		this.firtLvlLinearLayout = new LinearLayout(this.getContext());
		this.firtLvlLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		this.secondLvlLinearLayout = new LinearLayout(this.getContext());
		this.secondLvlLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		
		if(Table.IS_TWO_COLUMN_HEADER){
			
			this.addView(firtLvlLinearLayout);
			this.addView(secondLvlLinearLayout);
		
		}else{
			
			this.addView(secondLvlLinearLayout);
			
		}
		
		
	}
	/**
	 * top most header column
	 */
	private void initFirstLvlHeaders(){
		LayoutParams firstLvlTextViewParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		firstLvlTextViewParams.setMargins(1, 1, 1, 1);
		firstLvlTextViewParams.weight = 1;
		
		TextView firstLvlTextView = new TextView(this.getContext());
		firstLvlTextView.setText(firstLvlLabel.toString());
		firstLvlTextView.setPadding(PADDING, PADDING, PADDING, PADDING);
		firstLvlTextView.setBackgroundColor(Table.HEADER_BACKROUND_COLOR);
		firstLvlTextView.setGravity(Gravity.CENTER);
		firstLvlTextView.setLayoutParams(firstLvlTextViewParams);
		
		this.firtLvlLinearLayout.addView(firstLvlTextView);
	}
	
	/**
	 * second header column
	 */
	private void initSecondLvlHeader(){
		
		int secondLvlHeaderLblCount = this.secondLvlLabel.length;
		
	
		
		for(int x = 0 ; x < secondLvlHeaderLblCount ; x++){
			LayoutParams firstLvlTextViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
			firstLvlTextViewParams.setMargins(1, 1, 1, 1);
			
			String labelString = secondLvlLabel[x].toString();
			
			if(labelString.equalsIgnoreCase(Table.NAME)){
				
				
				LinearLayout paginationLinearLayout = this.paginationLinearLayout(labelString);
				
				paginationLinearLayout.setPadding(PADDING, PADDING, PADDING, PADDING);
				paginationLinearLayout.setBackgroundColor(Table.HEADER_BACKROUND_COLOR);
				
				paginationLinearLayout.setLayoutParams(firstLvlTextViewParams);
				
				this.secondLvlLinearLayout.addView(paginationLinearLayout);
				
			}else{
				TextView secondLvlTextView = new TextView(this.getContext());
				secondLvlTextView.setText(labelString.equalsIgnoreCase("Product") ? "Product": labelString);
				secondLvlTextView.setPadding(PADDING, PADDING, PADDING, PADDING);
				secondLvlTextView.setBackgroundColor(Table.HEADER_BACKROUND_COLOR);
				secondLvlTextView.setGravity(Gravity.CENTER);
				secondLvlTextView.setLayoutParams(firstLvlTextViewParams);
				
				this.secondLvlLinearLayout.addView(secondLvlTextView);
				
			}
			
		}
	}
	/**
	 * layout for pagination
	 * @param label
	 * @return
	 */
	private LinearLayout paginationLinearLayout(String label){
		
		LinearLayout paginationLinearLayout = new LinearLayout(this.getContext());
		
		TextView labelTextView = new TextView(this.getContext());
		labelTextView.setGravity(Gravity.CENTER);
		labelTextView.setText(label);
		LayoutParams labelTextViewParams = new LayoutParams(0,LayoutParams.MATCH_PARENT);
		labelTextViewParams.weight = 1;
		
		this.nextTextView = new TextView(this.getContext());
		this.nextTextView.setText("   "+Table.NEXT_ARROW+"   ");
		this.nextTextView.setGravity(Gravity.CENTER);
		this.nextTextView.setTag(NEXT_PAGINATION_TAG);
		this.nextTextView.setBackgroundDrawable(new CustomStateListDrawable(nextTextView));
		this.nextTextView.setOnClickListener(this.onClickListenerOfPagination);
		
		LayoutParams nextTextViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
		
		this.previousTextView = new TextView(this.getContext());
		this.previousTextView.setText("   "+Table.PREVIOUS_ARROW+"   ");
		this.previousTextView.setGravity(Gravity.CENTER);
		this.previousTextView.setTag(PREVIUOS_PAGINATION_TAG);
		this.previousTextView.setTextColor(Table.HEADER_BACKROUND_COLOR);
		this.previousTextView.setBackgroundDrawable(new CustomStateListDrawable(previousTextView));
		this.previousTextView.setOnClickListener(this.onClickListenerOfPagination);
		
		LayoutParams previousTextViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
		
		paginationLinearLayout.addView(previousTextView,previousTextViewParams);
		paginationLinearLayout.addView(labelTextView,labelTextViewParams);
		paginationLinearLayout.addView(nextTextView,nextTextViewParams);
		
		return paginationLinearLayout;
	}
	/**
	 * adjust the width of headers to match in screen size
	 */
	private void analizeFirstAndSecondHeaderWidth(){

		int headerSecondLvlChildrenCount = secondLvlLinearLayout.getChildCount();
		/**
		 * LEFT = 1;
		 * RIGHT = 1;
		 * get first lvl header width + (number of second lvl * (PADDING * (LEFT + RIGHT)) )
		 */
		int headerFirstLvlWidth = ViewSizeUtils.getViewWidth(firtLvlLinearLayout.getChildAt(0)) + (headerSecondLvlChildrenCount * (PADDING * 2));
		
		
		int headerSecondLvlChildrenTotalWidth = 0;
		
		for(int x = 0 ; x < headerSecondLvlChildrenCount ;x++){
				
			headerSecondLvlChildrenTotalWidth += ViewSizeUtils.getViewWidth(secondLvlLinearLayout.getChildAt(x));
		}
		
		

		int availableWidht = headerFirstLvlWidth - headerSecondLvlChildrenTotalWidth;
		int widhtForEachChild = (int) Math.ceil(availableWidht / headerSecondLvlChildrenCount);
		
		
		if(availableWidht <= 0){
			// if no available width, do nothing
			return;
		}
		
		for(int x = 0 ; x < headerSecondLvlChildrenCount ;x++){
			View view = secondLvlLinearLayout.getChildAt(x);
			LayoutParams params = (LayoutParams) view.getLayoutParams();
			params.width = params.width <=0 ? ViewSizeUtils.getViewWidth(view) + widhtForEachChild : params.width + widhtForEachChild;
		}
		
	}
}
