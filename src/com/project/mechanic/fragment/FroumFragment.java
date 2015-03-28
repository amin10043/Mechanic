package
com.project.mechanic.fragment;




import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.adapter.FroumListAdapter;
import com.project.mechanic.entity.Comment;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.Dialogcmt.OnMyDialogResult;
import com.project.mechanic.model.DataBaseAdapter;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class FroumFragment extends Fragment{
	
	
	DataBaseAdapter adapter;
	private int frmid;
	private ImageButton btnAddcmt;
	private Button btncmt;
	
	 Dialogcmt  dialog;
	
	
	ListView lst;
	
	@SuppressLint("InflateParams")
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceStdataate) {
		 View view = inflater.inflate(R.layout.fragment_froum, null);
		 
		 btnAddcmt = (ImageButton)view.findViewById(R.id.imgBtnAddcmt);
		 btncmt = (Button)view.findViewById(R.id.btnComment);
		 
		    adapter= new DataBaseAdapter(getActivity());
			adapter.open();
			//ArrayList<Comment> allFroum =  adapter.getAllCommentByPapaerId(paperId);
			adapter.close();
			
			
		 
	List<FroumItem> cmtlist1 = new ArrayList<FroumItem>();
	
	
	
	
		final FroumItem p1 = new FroumItem();
		FroumItem p2 = new FroumItem();
		FroumItem p3 = new FroumItem();
		FroumItem p4 = new FroumItem();
		
		
		/*p1.setComment();
		p2.setComment("نظر 2");
		p3.setComment("نظر 3");
		p4.setComment("نظر 4");*/
		p1.setUsername("شقایق کلالی");
		p2.setUsername("شقایق کلالی");
		p3.setUsername("شقایق کلالی");
		p4.setUsername("شقایق کلالی");
		
		cmtlist1.add(p1);
		cmtlist1.add(p2);
		cmtlist1.add(p3);
		cmtlist1.add(p4);
		

		 lst = (ListView) view.findViewById(R.id.lstComment);
		 FroumListAdapter ListAdapter = new FroumListAdapter(getActivity(),R.layout.froumcmtitem, cmtlist1);

			lst.setAdapter(ListAdapter);
		
		
		btnAddcmt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				

				
			  dialog = new Dialogcmt(getActivity());
			  dialog.setContentView(R.layout.dialog_addcomment);
			  dialog.show();

				
				 
				
			}
	});
		return view;
	}

	 
	
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 
		/*dialog.setDialogResult(new OnMyDialogResult(){
		    public void finish(String result){
		        // now you can use the 'result' on your activity
		     
		     p1.setComment(result);
		    }
		});*/
		lst.deferNotifyDataSetChanged();
		
		
	}
	
	/*public void refresh(){
        adapter.open();
        int count=adapter.Tablecommentcount();
        String[] cmt=new String[count];
        for(int i=0;i<count;i++){
        cmt[i]=adapter.DisplayComment(i,3);
        }
        adapter.close();
       // list1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cmt));
        
        }
	*/
	

}
