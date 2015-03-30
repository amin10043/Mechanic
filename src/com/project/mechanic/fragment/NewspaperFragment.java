package com.project.mechanic.fragment;




import com.project.mechanic.MainActivity;
import com.project.mechanic.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class NewspaperFragment extends Fragment {

	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		((MainActivity) getActivity()).setActivityTitle(R.string.News);
		View view = inflater.inflate(R.layout.fragment_newspaper, null);


        ImageButton btnim01=(ImageButton) view.findViewById(R.id.imageButton01);
			btnim01.setOnClickListener(new OnClickListener() {
				private MainActivity context;

				@Override
				public void onClick(View arg0) {
					
					FragmentTransaction trans = ((MainActivity) context)
							.getSupportFragmentManager().beginTransaction();
					trans.replace(R.id.content_frame, new Newsp1Fragment());
					trans.addToBackStack(null);
					trans.commit();
					
					//next.putExtra("btn", 101);
				//	startActivity(next);	
			}
		});	
			
		/*	
	      //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
          ImageButton btnim02=(ImageButton) view.findViewById(R.id.imageButton02);
  			btnim02.setOnClickListener(new OnClickListener() {
  				@Override
  				public void onClick(View arg0) {
  					Intent next= new Intent(MainActivity.this, P2.class );
  					next.putExtra("btn", 102);
  					startActivity(next);	
  			}
  		});	
  		      //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
              ImageButton btnim03=(ImageButton) view.findViewById(R.id.imageButton03);
      			btnim03.setOnClickListener(new OnClickListener() {
      				@Override
      				public void onClick(View arg0) {
      					Intent next= new Intent(MainActivity.this, P3.class );
      					next.putExtra("btn", 103);
      					startActivity(next);	
      			}
      		});	
      		      //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
                  ImageButton btnim04=(ImageButton) view.findViewById(R.id.imageButton04);
          			btnim04.setOnClickListener(new OnClickListener() {
          				@Override
          				public void onClick(View arg0) {
          					Intent next= new Intent(MainActivity.this, P4.class );
          					next.putExtra("btn", 104);
          					startActivity(next);	
          			}
		});	


		        */ 
		
		return view;
	
		}


	
	
	}




	
	

	
	

