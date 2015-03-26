package
com.project.mechanic.fragment;




import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.adapter.FroumListAdapter;
import com.project.mechanic.row_items.FroumItem;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class FroumFragment extends Fragment{
	
	ImageButton btn ;
	final private int Dialog_Reset=0;
	@SuppressLint("InflateParams")
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceStdataate) {
		 View view = inflater.inflate(R.layout.fragment_froum, null);
		 btn = (ImageButton)view.findViewById(R.id.imgBtnAddtitle);
			btn.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View arg0) {
					Dialog dialog = new Dialog(getActivity());
				//	dialog.show(R.layout.dialog_addcomment);
					
					
				}
			});
					
		
		 
	List<FroumItem> cmtlist1 = new ArrayList<FroumItem>();

		FroumItem p1 = new FroumItem();
		FroumItem p2 = new FroumItem();
		FroumItem p3 = new FroumItem();
		FroumItem p4 = new FroumItem();
		
		p1.setComment("‰Ÿ— 1");
		p2.setComment("‰Ÿ— 2");
		p3.setComment("‰Ÿ— 3");
		p4.setComment("‰Ÿ— 4");
		

	
		
		cmtlist1.add(p1);
		cmtlist1.add(p2);
		cmtlist1.add(p3);
		cmtlist1.add(p4);
		

		ListView lst = (ListView) view.findViewById(R.id.lstTitle);
		FroumListAdapter ListAdapter = new FroumListAdapter(getActivity(),R.layout.froumcmtitem, cmtlist1);

		lst.setAdapter(ListAdapter);
		return view;
	}

	 
		

}
