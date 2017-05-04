package adapters;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.justin.librarybooks.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import static landing_pages.LandingPage.RESERVED_BOOK_INDEX;
import static landing_pages.LandingPage.activity;
import static landing_pages.LandingPage.booksList;

/**
 * Created by thaso on 3/17/2017.
 * <p>This  class inherits from DialogFragent
 * A fragment that displays a dialog window, floating on top of its activity's window. This fragment contains a Dialog object, which it displays as appropriate based on the fragment's state. Control of the dialog (deciding when to show, hide, dismiss it) should be done through the API here, not with direct calls on the dialog.
 Implementations should override this class and implement onCreateView(LayoutInflater, ViewGroup, Bundle) to supply the content of the dialog. Alternatively, they can override onCreateDialog(Bundle) to create an entirely custom dialog, such as an AlertDialog, with its own content.</p>
 The recyle view displays book items in the BooksFragment layout
 @version 1.0
 */

public class RecycleDialogActivity extends DialogFragment implements DerpAdapter.ItemClickCallback{
    private RecyclerView recView;
    private DerpAdapter adapter;

    /**
     *<p>XCalled to do initial creation of a fragment. This is called after onAttach(Activity) and before onCreateView(LayoutInflater, ViewGroup, Bundle), but is not called if the fragment instance is retained across Activity re-creation (see setRetainInstance(boolean)).
     Note that this can be called while the fragment's activity is still in the process of being created. As such, you can not rely on things like the activity's content view hierarchy being initialized at this point. If you want to do work once the activity itself is created, see onActivityCreated(Bundle).
     If your app's targetSdkVersion is M or lower, child fragments being restored from the savedInstanceState are restored after onCreate returns. When targeting N or above and running on an N or newer platform version they are restored by Fragment.onCreate.</p>
     * @param savedInstanceState  If the fragment is being re-created from a previous saved state, this is the state.
     * @return A dialog view
     */
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflator = getActivity().getLayoutInflater();
        final View view = inflator.inflate(R.layout.search_recylce_list, null);
        recView = (RecyclerView) view.findViewById(R.id.rec_list);
        //Check out GridLayoutManager and StaggeredGridLayoutManager for more options
        recView.setLayoutManager(new LinearLayoutManager(activity));

        adapter = new DerpAdapter(booksList, activity);
        recView.setAdapter(adapter);
        adapter.setItemClickCallback(this);
        builder.setView(view);
        builder.setNegativeButton("Don't reserve", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Cancelled book reserve!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if(RESERVED_BOOK_INDEX >=0 && RESERVED_BOOK_INDEX < booksList.size() && booksList.get(RESERVED_BOOK_INDEX).getBookReserveDate().equalsIgnoreCase("NA"))
                {
                    bookReturnDateDialog();
                }
                else if(RESERVED_BOOK_INDEX >= booksList.size())
                    Toast.makeText(activity, "No book selected!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(activity, "You already reserved the book", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setTitle("Search results:");
        Dialog dialog = builder.create();
        return dialog;

    }

    /**
     *Specified an action to be taken after a book item view has been clicked
     * @param position The position of the book item view clicked
     */
    @Override
    public void onItemClick(int position) {
        RESERVED_BOOK_INDEX      = position;
        //  newEvent = eventsRecycleList.get(p);
        Toast.makeText(activity, booksList.get(RESERVED_BOOK_INDEX).getTitle()+" selected", Toast.LENGTH_SHORT).show();

    }

    /**
     *Pops up a dialog for inputing the date , a reserved book is intended to be returned
     */
    public void bookReturnDateDialog()
    {
        ReturnDateDialog dialog = new ReturnDateDialog();
        dialog.show(activity.getFragmentManager(), "Entering book return date...");
    }

}
