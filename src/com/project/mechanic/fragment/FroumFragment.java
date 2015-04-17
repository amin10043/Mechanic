package
com.project.mechanic.fragment;




import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.FroumListAdapter;
import com.project.mechanic.adapter.FroumtitleListadapter;
import com.project.mechanic.entity.Comment;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.DialogcmtInfroum.OnMyDialogResult;
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
import android.widget.TextView;


public class FroumFragment extends Fragment{
	
	
	DataBaseAdapter adapter;
	private int frmid;
	private ImageButton btnAddcmt;
	private ImageButton Like;
	private Button btncmt;
	private TextView txttitle;
	private TextView txttitleDes;
	private TextView NumofLike;
	private TextView NumofComment;
	ArrayList<CommentInFroum> mylist;
	DialogcmtInfroum  dialog;
	ImageButton Replytocm;
	FroumListAdapter froumListadapter;
	 int	id;
	
	ListView lst;
	ListView lstReply;
	
	@SuppressLint("InflateParams")
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceStdataate) {
		
		((MainActivity) getActivity()).setActivityTitle(R.string.Forums);
		 View view = inflater.inflate(R.layout.fragment_froum, null);
		 
		 btnAddcmt = (ImageButton)view.findViewById(R.id.imgBtnAddcmt_CmtFroum);
		 Like = (ImageButton)view.findViewById(R.id.imgbtnLike_CmtFroum);
		 btncmt = (Button)view.findViewById(R.id.btnComment);
		 txttitle=(TextView)view.findViewById(R.id.rawTitletxt);
		 txttitleDes =(TextView)view.findViewById(R.id.rawtxtDescription);
		 NumofComment =(TextView)view.findViewById(R.id.txtNumofCmt_CmtFroum);
		 NumofLike =(TextView)view.findViewById(R.id.txtNumofLike_CmtFroum);
		 
		    adapter= new DataBaseAdapter(getActivity());
			adapter.open();
		    id = Integer.valueOf(getArguments().getString("Id"));
		    NumofComment.setText(adapter.CommentInFroum_count().toString());
			NumofLike.setText(adapter.LikeInFroum_count().toString());
		    mylist = adapter.getCommentInFroumbyPaperid(id);
			Froum x =adapter.getFroumItembyid(id);
		    txttitle.setText(x.getTitle());
			txttitleDes.setText(x.getDescription());
			adapter.close();
			
			
			
		

		 lst = (ListView) view.findViewById(R.id.lstComment);
		 lstReply=(ListView) view.findViewById(R.id.lstReplytoCm);
		 FroumListAdapter ListAdapter = new FroumListAdapter(getActivity(),R.layout.raw_froumcmt, mylist);
         
			lst.setAdapter(ListAdapter);
			
			Like.setOnClickListener(new View.OnClickListener() {
				  
				  @Override public void onClick(View arg0) {
				  adapter.open();
				  adapter.insertLikeInFroumToDb( 1, 0,"",1);
				  NumofLike.setText(adapter.LikeInFroum_count().toString());
				  adapter.close();
				 
				  
				  } });

			Replytocm= (ImageButton)view.findViewById(R.id.imgvReplytoCm);
			/*Replytocm.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					
					  dialog = new Dialogcmt(getActivity(),R.layout.dialog_addcomment);
					  dialog.show();
					
				}
				
				
				
			});*/
		
		btnAddcmt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				

				
			  dialog = new DialogcmtInfroum(FroumFragment.this,getActivity(),R.layout.dialog_addcomment);
			  dialog.show();

				
				 
				
			}
	});
		return view;
	}

	 
	
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lst.deferNotifyDataSetChanged();
		
		
	}
	
	
	public void updateView2() {
		adapter.open();
		mylist =  adapter.getCommentInFroumbyPaperid(id);
		NumofComment.setText(adapter.CommentInFroum_count().toString());
		adapter.close();

		froumListadapter = new FroumListAdapter(getActivity(),
				R.layout.raw_froumcmt, mylist);
		froumListadapter.notifyDataSetChanged();
		lst.setAdapter(froumListadapter);

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
