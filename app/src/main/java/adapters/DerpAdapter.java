
package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.justin.librarybooks.Book;
import com.example.justin.librarybooks.R;

import java.util.ArrayList;

/**
 * Created by thaso on 3/17/2017.
 * <p>Adapters provide a binding from an app-specific data set to views that are displayed within a RecyclerView.</p>
 @version 1.0
 */

public class DerpAdapter extends RecyclerView.Adapter<DerpAdapter.DerpHolder>
{
    private ArrayList<Book> listData;
    private LayoutInflater inflater;
    private ItemClickCallback itemClickCallback;
    /**Book items callable interface */
    public interface ItemClickCallback {
        void onItemClick(int p);
    }
    /**Setting book items clickable n the recyle view
     * @param itemClickCallback An ItemClickableCallback object
     * */
    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }
    /*
    * Constructor for the DerpAdpter class
    * @param listData ArrayList of libray book objects
    * @param c The context of the object constaruction
    * */
    public DerpAdapter(ArrayList<Book> listData, Context c){
        inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    /*
    /**A holder of the adpter items in a recycle view
     * @param parent A parent of the view group
     * @param viewType a view type specification
     * */
    @Override
    public DerpAdapter.DerpHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycle_item, parent, false);
        return new DerpHolder(view);
    }

    /**
     * <p>Called by RecyclerView to display the data at the specified position. This method should update the contents of the itemView to reflect the item at the given position.
          Note that unlike ListView, RecyclerView will not call this method again if the position of the item changes in the data set unless the item itself is invalidated or the new position cannot be determined. For this reason, you should only use the position parameter while acquiring the related data item inside this method and should not keep a copy of it. If you need the position of an item later on (e.g. in a click listener), use getAdapterPosition() which will have the updated adapter position. Override onBindViewHolder(ViewHolder, int, List) instead if Adapter can handle efficient partial bind.
     </p>

     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(DerpHolder holder, int position) {
        Book item = listData.get(position);
        String text = "Book ISBN: "+item.getIsbn()+"\nBook Title: "+item.getTitle() +"\nBook Author: "+item.getAuthor()+"\nNumber of books: "+ item.getCount() +"\nBook Edition: "+item.getEdition();
        if(!item.getBookReturnDate().equalsIgnoreCase("NA"))
        {
            text += "\n Your already reserved this book: \n Reserve date: "+item.getBookReserveDate()+"\n Book return date: "+item.getBookReturnDate();
        }
        holder.title.setText(text);

        holder.icon.setImageResource(item.getDrawable());
        holder.cardText.setText(item.getDescription());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return  The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return listData.size();
    }


    /**
     * A recycle view Holder class
     */
    class DerpHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title, cardText;
        private ImageView icon;
        private View container, cardView;

        /**
         * The constructor of class DerpHolder
         * @param itemView The view held by the DerpHolder object
         */
        public DerpHolder(View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.lbl_item_text);
            cardText = (TextView)itemView.findViewById(R.id.cardtext);
            icon = (ImageView)itemView.findViewById(R.id.im_item_icon);
            //We'll need the container later on, when we add an View.OnClickListener
            container = itemView.findViewById(R.id.cont_item_root);
            cardView = itemView.findViewById(R.id.card_item);
            cardView.setOnClickListener(this);
            container.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         * @param v the view that has been clicked
         */
        @Override
        public void onClick(View v)
        {
            if (v.getId() == R.id.cont_item_root || v.getId() == R.id.card_item) {
                itemClickCallback.onItemClick(getAdapterPosition());
            }
        }
    }
}
