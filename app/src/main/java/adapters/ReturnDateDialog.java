package adapters;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justin.librarybooks.Book;
import com.example.justin.librarybooks.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import static landing_pages.LandingPage.BOOK_RETURN_DATE;
import static landing_pages.LandingPage.RESERVED_BOOK_INDEX;
import static landing_pages.LandingPage.booksList;
import static landing_pages.LandingPage.db;

/**
 * Created by thaso on 4/27/2017.
 */

public class ReturnDateDialog extends DialogFragment
{
    private EditText returnDate;
    /**
     *<p>XCalled to do initial creation of a fragment. This is called after onAttach(Activity) and before onCreateView(LayoutInflater, ViewGroup, Bundle), but is not called if the fragment instance is retained across Activity re-creation (see setRetainInstance(boolean)).
     Note that this can be called while the fragment's activity is still in the process of being created. As such, you can not rely on things like the activity's content view hierarchy being initialized at this point. If you want to do work once the activity itself is created, see onActivityCreated(Bundle).
     If your app's targetSdkVersion is M or lower, child fragments being restored from the savedInstanceState are restored after onCreate returns. When targeting N or above and running on an N or newer platform version they are restored by Fragment.onCreate.</p>
     * @param savedInstanceState  If the fragment is being re-created from a previous saved state, this is the state.
     * @return A dialog view
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflator = getActivity().getLayoutInflater();
        final View view = inflator.inflate(R.layout.activity_return_date, null);
        returnDate = (EditText)view.findViewById(R.id.editTextDate);
        builder.setView(view);
        builder.setNegativeButton("Don't reserve", new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(),"Cancelled book reserve!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
               BOOK_RETURN_DATE = returnDate.getText().toString();
                String reservedDate = date(System.currentTimeMillis());
                if(milliseconds(BOOK_RETURN_DATE) < System.currentTimeMillis())
                    Toast.makeText(getActivity(),"Invalid return date: "+BOOK_RETURN_DATE, Toast.LENGTH_SHORT).show();
                else
                {
                    Toast.makeText(getActivity(),"Your book return date is: "+BOOK_RETURN_DATE, Toast.LENGTH_SHORT).show();
                    Book book = booksList.get(RESERVED_BOOK_INDEX);
                    boolean updateSuccessfull = db.updateData(""+book.getId(), book.getIsbn(),book.getTitle(),book.getAuthor(), book.getStudentNumber(),book.getDescription(),""+(book.getCount()-1), book.getDrawable()+"",book.getEdition(), reservedDate+"", ""+BOOK_RETURN_DATE);
                    if(updateSuccessfull)
                        Toast.makeText(getActivity(),"Book reservation successfull: "+BOOK_RETURN_DATE, Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(),"Book reservation  not successfull: "+BOOK_RETURN_DATE, Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setTitle("Entering book return date...");
        Dialog dialog = builder.create();
        return dialog;

    }

    /**
     * Converts a string date YYYY-MM-DD to current milli seconds date
     * @param date The string date
     * @return The current milli seconds date
     */
    public long milliseconds(String date) {
        //String date_ = date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date mDate = sdf.parse(date);
            long timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
            return timeInMilliseconds;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Converts milliseconds date to a YYYY-MM-DD formated date
     * @param millis The date in current milli seconds
     * @return String date in the format YYYY-MM-DD
     */
    public String date(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(millis);
        return date;
    }

}
