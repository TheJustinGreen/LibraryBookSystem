package fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.justin.librarybooks.Book;
import com.example.justin.librarybooks.R;

import java.util.ArrayList;

import adapters.DerpAdapter;
import adapters.RecycleDialogActivity;
import adapters.ReturnDateDialog;

import static landing_pages.LandingPage.RESERVED_BOOK_INDEX;
import static landing_pages.LandingPage.activity;
import static landing_pages.LandingPage.booksList;
import static landing_pages.LandingPage.db;
import static landing_pages.LandingPage.speachFlag;

/**
 * Created by thaso on 4/23/2017.
 * <h3>A Fragment for displaying books available in the library system inheriting from android Fragment class</h3>
 * <p>A Fragment is a piece of an application's user interface or behavior that can be placed in an Activity. Interaction with fragments is done through FragmentManager, which can be obtained via Activity.getFragmentManager() and Fragment.getFragmentManager().
 The Fragment class can be used many ways to achieve a wide variety of results. In its core, it represents a particular operation or interface that is running within a larger Activity. A Fragment is closely tied to the Activity it is in, and can not be used apart from one. Though Fragment defines its own lifecycle, that lifecycle is dependent on its activity: if the activity is stopped, no fragments inside of it can be started; when the activity is destroyed, all fragments will be destroyed.
 All subclasses of Fragment must include a public no-argument constructor. The framework will often re-instantiate a fragment class when needed, in particular during state restore, and needs to be able to find this constructor to instantiate it. If the no-argument constructor is not available, a runtime exception will occur in some cases during state restore.</p>
 *
 */

public class BooksFragment extends Fragment implements DerpAdapter.ItemClickCallback,AdapterView.OnItemSelectedListener{
    private View view;
    private RecyclerView recView;
    private DerpAdapter adapter;
    private SearchView searchView;
    private Spinner spinnerView;
    private String sSelected="";

    @Nullable
    @Override
    /**
     * <p>Called to have the fragment instantiate its user interface view. This is optional, and non-graphical fragments can return null (which is the default implementation). This will be called between onCreate(Bundle) and onActivityCreated(Bundle).
     *If you return a View from here, you will later be called in onDestroyView() when the view is being released.</p>
     * @param inflater A fragment layout inflater
     * @param container A container of the fragment
     * @param savedInstanceState the fragment object's context's saved state
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.feeds_fragment, container, false);
            recView = (RecyclerView)view.findViewById(R.id.recyclerView);
            //Check out GridLayoutManager and StaggeredGridLayoutManager for more options
            recView.setLayoutManager(new LinearLayoutManager(activity));


            Cursor res = db.getAllData("Book_table");//getAllData("Book_table");
            if(res.getCount() == 0) {
                // show message
                Toast.makeText(getActivity(),"Nothing found", Toast.LENGTH_SHORT).show();
            }
            booksList = new ArrayList<Book>();
            while (res.moveToNext()) {
                Book book = new Book(res.getString(3), Integer.parseInt(res.getString(6)), res.getString(5), Integer.parseInt(res.getString(7)), res.getString(8), res.getString(1), "3580034", res.getString(2));
                book.setBookReserveDate(res.getString(9));
                book.setBookReturnDate(res.getString(10));
                book.setId(Integer.parseInt(res.getString(0)));
                booksList.add(book);
            }

            adapter = new DerpAdapter(booksList, activity);
            recView.setAdapter(adapter);
            adapter.setItemClickCallback(this);

        } catch (InflateException e) {
           /* map is already there, just return view as it is */
        }

        //Added By Justin Green 24/09/2016 ref http://stackoverflow.com/questions/30721664/android-toolbar-adding-menu-items-for-different-fragme
        setHasOptionsMenu(true);
        return view;
    }

    /**
     *<p>Called to do initial creation of a fragment. This is called after onAttach(Activity) and before onCreateView(LayoutInflater, ViewGroup, Bundle), but is not called if the fragment instance is retained across Activity re-creation (see setRetainInstance(boolean)).
     Note that this can be called while the fragment's activity is still in the process of being created. As such, you can not rely on things like the activity's content view hierarchy being initialized at this point. If you want to do work once the activity itself is created, see onActivityCreated(Bundle).</p>
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * <p>Initialize the contents of the Activity's standard options menu. You should place your menu items in to menu. For this method to be called, you must have first called setHasOptionsMenu(boolean). See Activity.onCreateOptionsMenu for more information.</p>
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_toolbar, menu);

        //Spinner code
        MenuItem spinnerItem = menu.findItem(R.id.spinner);
        spinnerView = new Spinner((activity).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(spinnerItem, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(spinnerItem, spinnerView);

        //Spinner spinner = (Spinner) activity.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerView.setAdapter(adapter);

        spinnerView.setOnItemSelectedListener(this);
        //end of spinner code


        MenuItem item = menu.findItem(R.id.searchable);
        searchView = new SearchView((activity).getSupportActionBar().getThemedContext());
        searchView.setQueryRefinementEnabled(true);
//        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Cursor res = db.getDataWithParam("Book_table",sSelected,query);//getAllData("Book_table");
                if(res.getCount() == 0) {
                    // show message
                    Toast.makeText(getActivity(),"Nothing found", Toast.LENGTH_SHORT).show();
                }
                booksList = new ArrayList<Book>();
                while (res.moveToNext()) {
                    Book book = new Book(res.getString(3), Integer.parseInt(res.getString(6)), res.getString(5), Integer.parseInt(res.getString(7)), res.getString(8), res.getString(1), "3580034", res.getString(2));
                    book.setBookReserveDate(res.getString(9));
                    book.setBookReturnDate(res.getString(10));
                    book.setId(Integer.parseInt(res.getString(0)));
                    booksList.add(book);
                }
                showEventRecycleDialog();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }

    /**
     * Specifies what action follows after an item view has been clicked
     * @param p Position of an item view clicked
     */
    @Override
    public void onItemClick(int p) {
        //selectedBook = bookssRecycleList.get(p);
        //Toast.makeText(activity, "@ "+ selectedBook.getTitle, Toast.LENGTH_SHORT).show();
        RESERVED_BOOK_INDEX      = p;
        //  newEvent = eventsRecycleList.get(p);
        Toast.makeText(activity, booksList.get(RESERVED_BOOK_INDEX).getTitle()+" selected", Toast.LENGTH_SHORT).show();
        if(RESERVED_BOOK_INDEX >=0 && booksList.get(RESERVED_BOOK_INDEX).getBookReserveDate().equalsIgnoreCase("NA"))
        {
            bookReturnDateDialog();
        }
        else
            Toast.makeText(activity, "You already reserved "+booksList.get(RESERVED_BOOK_INDEX).getTitle(), Toast.LENGTH_SHORT).show();


    }

    /**
     * Sets the reserve date of a book item view that has been clicked
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sSelected=parent.getItemAtPosition(position).toString().substring(10);
        Toast.makeText(this.getContext(),sSelected,Toast.LENGTH_SHORT).show();}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static void showEventRecycleDialog()
    {
        RecycleDialogActivity dialog = new RecycleDialogActivity();
        dialog.show(activity.getFragmentManager(), "Your search result...");
    }
    public void bookReturnDateDialog()
    {
        ReturnDateDialog dialog = new ReturnDateDialog();
        dialog.show(activity.getFragmentManager(), "Entering book return date...");
    }

    public static void popUpSearchResult(String table, String searchCategory, String query)
    {
        Cursor res = db.getDataWithParam(table,searchCategory,query);//getAllData("Book_table");
        if(res.getCount() == 0) {
            // show message

            Toast.makeText(activity,"Nothing found", Toast.LENGTH_SHORT).show();
            speachFlag = true;

        }
        booksList = new ArrayList<Book>();
        while (res.moveToNext()) {
            Book book = new Book(res.getString(3), Integer.parseInt(res.getString(6)), res.getString(5), Integer.parseInt(res.getString(7)), res.getString(8), res.getString(1), "3580034", res.getString(2));
            book.setBookReserveDate(res.getString(9));
            book.setBookReturnDate(res.getString(10));
            book.setId(Integer.parseInt(res.getString(0)));
            booksList.add(book);
        }
        showEventRecycleDialog();
    }


}
