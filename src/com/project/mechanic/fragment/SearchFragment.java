package com.project.mechanic.fragment;

import java.util.List;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;


public class SearchFragment extends Fragment {

    DataBaseAdapter     adapter;

    private List<Movie> mMovies;
    private List<Movie> mMoviesFiltered;
    private boolean     mSearchOpened;
    private String      mSearchQuery;
    private ListView    mMoviesLv;
    private Drawable    mIconOpenSearch;
    private Drawable    mIconCloseSearch;
    private EditText    mSearchEt;
    private MenuItem    mSearchAction;


    // @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActivityTitle(R.string.search);

        View view = inflater.inflate(R.layout.fragment_search, null);
        adapter = new DataBaseAdapter(getActivity());
        return view;

        // DB = new DataBaseAdapter(this);
        //		if (savedState == null) {
        //			mMovies = getInitialListOfMovies();
        //			mMoviesFiltered = mMovies;
        //			mSearchOpened = false;
        //			mSearchQuery = "";
        //		} else {
        //			mMovies = savedState.getParcelableArrayList(MOVIES);
        //			mMoviesFiltered = savedState
        //					.getParcelableArrayList(MOVIES_FILTERED);
        //			mSearchOpened = savedState.getBoolean(SEARCH_OPENED);
        //			mSearchQuery = savedState.getString(SEARCH_QUERY);
        //		}
        //
        //		mIconOpenSearch = getResources().getDrawable(R.drawable.ic_search);
        //		mIconCloseSearch = getResources().getDrawable(R.drawable.ic_favorite);
        //
        //		mMoviesLv = (ListView) view.findViewById(R.id.ListvCity);
        //
        //		MoviesListAdapter adapter = new MoviesListAdapter(mMoviesFiltered, this);
        //		mMoviesLv.setAdapter(adapter);
        //
        //		if (mSearchOpened) {
        //			openSearchBar(mSearchQuery);
        //		}
        //
        //		return view;
        //	}
        //
        //	@Override
        //	public void onSaveInstanceState(Bundle outState) {
        //		super.onSaveInstanceState(outState);
        //		outState.putParcelableArrayList(MOVIES, (ArrayList<Movie>) mMovies);
        //		outState.putParcelableArrayList(MOVIES_FILTERED,
        //				(ArrayList<Movie>) mMoviesFiltered);
        //		outState.putBoolean(SEARCH_OPENED, mSearchOpened);
        //		outState.putString(SEARCH_QUERY, mSearchQuery);
        //	}
        //
        //	public boolean onCreateOptionsMenu(Menu menu) {
        //		getMenuInflater().inflate(R.menu.main, menu);
        //		return true;
        //	}
        //
        //	@Override
        //	public boolean onPrepareOptionsMenu(Menu menu) {
        //		mSearchAction = menu.findItem(R.id.ic_search);
        //		return super.onPrepareOptionsMenu(menu);
        //	}
        //
        //	@Override
        //	public boolean onOptionsItemSelected(MenuItem item) {
        //		int id = item.getItemId();
        //		if (id == R.id.ic_search) {
        //			if (mSearchOpened) {
        //				closeSearchBar();
        //			} else {
        //				openSearchBar(mSearchQuery);
        //			}
        //			return true;
        //		}
        //		return super.onOptionsItemSelected(item);
        //	}
        //
        //	private void openSearchBar(String queryText) {
        //
        //		ActionBar actionBar = getSupportActionBar();
        //		actionBar.setDisplayShowCustomEnabled(true);
        //		actionBar.setCustomView(R.layout.search_bar);
        //
        //		mSearchEt = (EditText) actionBar.getCustomView().findViewById(
        //				R.id.etSearch);
        //		mSearchEt.addTextChangedListener(new SearchWatcher());
        //		mSearchEt.setText(queryText);
        //		mSearchEt.requestFocus();
        //
        //		mSearchAction.setIcon(mIconCloseSearch);
        //		mSearchOpened = true;
        //
        //	}
        //
        //	private void closeSearchBar() {
        //
        //		getSupportActionBar().setDisplayShowCustomEnabled(false);
        //
        //		mSearchAction.setIcon(mIconOpenSearch);
        //		mSearchOpened = false;
        //
        //	}
        //
        //	private class SearchWatcher implements TextWatcher {
        //
        //		public void beforeTextChanged(CharSequence c, int i, int i2, int i3) {
        //
        //		}
        //
        //		public void onTextChanged(CharSequence c, int i, int i2, int i3) {
        //
        //		}
        //
        //		@Override
        //		public void afterTextChanged(Editable editable) {
        //			mSearchQuery = mSearchEt.getText().toString();
        //			mMoviesFiltered = performSearch(mMovies, mSearchQuery);
        //			getListAdapter().update(mMoviesFiltered);
        //		}
        //
        //	}
        //
        //	private MoviesListAdapter getListAdapter() {
        //		return (MoviesListAdapter) mMoviesLv.getAdapter();
        //	}
        //
        //	private List<Movie> performSearch(List<Movie> movies, String query) {
        //
        //		String[] queryByWords = query.toLowerCase().split("\\s+");
        //
        //		List<Movie> moviesFiltered = new ArrayList<Movie>();
        //
        //		for (Movie movie : mMovies) {
        //
        //			String content = (movie.getTitle() + " " + movie.getDirector()
        //					+ " " + String.valueOf(movie.getYear())).toLowerCase();
        //
        //			for (String word : queryByWords) {
        //
        //				int numberOfMatches = queryByWords.length;
        //
        //				if (content.contains(word)) {
        //					numberOfMatches--;
        //				} else {
        //					break;
        //				}
        //
        //				if (numberOfMatches == 0) {
        //					moviesFiltered.add(movie);
        //				}
        //
        //			}
        //
        //		}

        //  return moviesFiltered;
    }
}
