package com.project.mechanic.fragment;



import com.project.mechanic.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;



public class NewsFragment extends ListFragment implements OnItemClickListener {


	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    
        super.onActivityCreated(savedInstanceState);

ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.news, android.R.layout.simple_list_item_1);
      
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {

       // Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT)
       //         .show();

        selectItem(position);
	
		}

	private void selectItem(int position) {
		
		
		Fragment fragment;
		FragmentManager fragmentManager;
		switch (position) {
		case 0:

			fragment = new PublicationFragment();
			fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			break;

		case 1:
			fragment = new PaperFragment();
			fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			break;

		case 2:
			fragment = new NewspaperFragment();
			fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			break;
		
		}
		
		
		
	} 
		
	
	}

    	







	
	

	
	

