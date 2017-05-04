package landing_pages;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.justin.librarybooks.Book;
import com.example.justin.librarybooks.R;

import java.util.ArrayList;
import java.util.Locale;

import adapters.TabsAdapter;
import fragments.BooksFragment;

import static fragments.BooksFragment.popUpSearchResult;
import static fragments.BooksFragment.showEventRecycleDialog;
import static landing_pages.DatabaseHelper.TABLE_NAME;

public class LandingPage extends AppCompatActivity implements View.OnClickListener{

    /*Declaration of variables*/
    private FloatingActionButton mainFab,helpFab,loansFab,pastPapersFab,editProfileFab;
    private LinearLayout helpLayout,loansLayout,pastPapersLayout,editProfileLayout;
    private Animation showMainButton, hideMainButton, showButton1, hideButton1, showButton2, hideButton2, showButton3, hideButton3, showButton4, hideButton4;
    public Dialog dialog;

    public static TextToSpeech toSpeech;
    public int result;
    public ArrayList<String> text;
    String name = "";


    protected static final int RESULT_SPEECH = 1;

    public static  LandingPage activity;
    public static  ArrayList<Book> booksList;
    public static  int RESERVED_BOOK_INDEX = -1;
    public static  String  BOOK_RETURN_DATE = "";
    public static DatabaseHelper db;
    public static String PREFS_NAME = "privateData";

    public static boolean speachFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*
         The state is passed back to onCreate if the activity needs to be recreated (e.g., orientation change)
         so that you don't lose any prior information. If no data was supplied, savedInstanceState is null.
       */
        super.onCreate(savedInstanceState);


        //Set the layout for the activity//
        setContentView(R.layout.activity_main);
        initializeDataBase();

        //Declaration of main Floating Action Button//
        mainFab = (FloatingActionButton) findViewById(R.id.mainFab);
        mainFab.setOnClickListener(this);

        //The is is where we initiate the transitioning animations for opening and closing the menu//

        showMainButton = AnimationUtils.loadAnimation(LandingPage.this, R.anim.show_main_button);
        hideMainButton = AnimationUtils.loadAnimation(LandingPage.this, R.anim.hide_main_button);

        showButton1 = AnimationUtils.loadAnimation(LandingPage.this, R.anim.show_button1);
        hideButton1 = AnimationUtils.loadAnimation(LandingPage.this, R.anim.hide_button1);

        showButton2 = AnimationUtils.loadAnimation(LandingPage.this, R.anim.show_button2);
        hideButton2 = AnimationUtils.loadAnimation(LandingPage.this, R.anim.hide_button2);

        showButton3 = AnimationUtils.loadAnimation(LandingPage.this, R.anim.show_button3);
        hideButton3 = AnimationUtils.loadAnimation(LandingPage.this, R.anim.hide_button3);

        showButton4 = AnimationUtils.loadAnimation(LandingPage.this, R.anim.show_button4);
        hideButton4 = AnimationUtils.loadAnimation(LandingPage.this, R.anim.hide_button4);

        // This is gives you the swipping and switching between tabs function//
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        //This creates the tabs using the 'include_list_viewpager.xml' for styling//
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
        }


        toSpeech = new TextToSpeech(LandingPage.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                if(i == TextToSpeech.SUCCESS)
                {
                    result = toSpeech.setLanguage(Locale.ENGLISH);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Feature not supported in your deavice", Toast.LENGTH_LONG).show();
                }


            }
        });

    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }


    //SET UP THE TABS ON THE VIEWPAGER//
    private void setupViewPager(ViewPager viewPager)
    {
        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager());

        //SET NUMBER OF TABS ALLOWED//
        viewPager.setOffscreenPageLimit(1);

        //ATTACH THE FRAGMENT CLASS AND A TITTLE//
        adapter.addFragment(new BooksFragment(), "UWC Library Books");

        viewPager.setAdapter(adapter);
    }


    //THIS CREATES THE FLOATING ACTION MENU WITH THE DIMMING OVERLAY ON THE SCREEN//
    public void CustomDialog()
    {
        dialog = new Dialog(LandingPage.this);

        //IT REMOVES THE DIALOG TITLE//
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //SET THE LAYOUT IN THE DIALOG
        dialog.setContentView(R.layout.fab_menu);

        //set the background partially transparent
        ColorDrawable dialogColor = new ColorDrawable(4);
        dialogColor.setAlpha((int) Long.parseLong("FF", 16));
        dialog.getWindow().setBackgroundDrawable(dialogColor);

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.7f;


        Window window = dialog.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();

        // set the layout at right bottom
        param.gravity = Gravity.BOTTOM | Gravity.RIGHT;

        // it dismiss the dialog when click outside the dialog frame
        dialog.setCanceledOnTouchOutside(true);

        // initialize the item of the dialog box, whose id is demo1
        View demodialog = dialog.findViewById(R.id.cross);

        /*
            THIS IS JUST SETTING UP THE DIFFERENT MENU ITEMS AND MAKING THEM CLICKABLE

         */
        helpLayout = (LinearLayout) dialog.findViewById(R.id.helpLayout);
        pastPapersLayout = (LinearLayout) dialog.findViewById(R.id.pastPapersLayout);
        editProfileLayout = (LinearLayout) dialog.findViewById(R.id.editProfileLayout);
        loansLayout = (LinearLayout) dialog.findViewById(R.id.loansLayout);

        helpFab = (FloatingActionButton) dialog.findViewById(R.id.helpFab);
        helpFab.setOnClickListener(this);
        editProfileFab = (FloatingActionButton) dialog.findViewById(R.id.editProfileFab);
        editProfileFab.setOnClickListener(this);
        pastPapersFab = (FloatingActionButton) dialog.findViewById(R.id.pastPapersFab);
        pastPapersFab.setOnClickListener(this);
        loansFab = (FloatingActionButton) dialog.findViewById(R.id.loansFab);
        loansFab.setOnClickListener(this);

        final FloatingActionButton fabDialog = (FloatingActionButton) dialog.findViewById(R.id.fabMainDialog);

        // it call when click on the item whose id is demo1.
        demodialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dismiss the dialog
                helpLayout.startAnimation(hideButton1);
                pastPapersLayout.startAnimation(hideButton3);
                editProfileLayout.startAnimation(hideButton4);
                loansLayout.startAnimation(hideButton2);

                fabDialog.startAnimation(hideMainButton);
                dialog.dismiss();
                mainFab.show();
                mainFab.startAnimation(hideMainButton);

            }
        });

        // it showS the dialog box
        helpLayout.startAnimation(showButton1);
        editProfileLayout.startAnimation(showButton4);
        pastPapersLayout.startAnimation(showButton3);
        loansLayout.startAnimation(showButton2);

        fabDialog.startAnimation(showMainButton);
        dialog.show();
        mainFab.hide();
        mainFab.startAnimation(hideMainButton);
        mainFab.show();
    }

    //THIS IS FOR CLICK EVENTS ON THE MAIN FAB AND ALSO TO DISMISS IT IF IT IS NOT PRESSED//
    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.mainFab:

                mainFab.startAnimation(showMainButton);
                CustomDialog();
                break;

            default:
                dialog.dismiss();
                mainFab.startAnimation(hideMainButton);
                break;
        }


    }




    public void initializeDataBase()
    {
        db = new DatabaseHelper(this);
        activity = this;
        booksList = new ArrayList<>();
        SharedPreferences settings = this.getSharedPreferences(PREFS_NAME, 0);
        String string = settings.getString("logged", "");
        if(!string.equalsIgnoreCase("logged"))
        {

            db.insertData("DN76875", "ALGORITHMS","ROBERT SEGWICK","3580034","Robert Segwick's book for algorithms is targeted for people who are familiarized with coding at an advanced stage","3",""+R.drawable.algorithms,"Fourth addition", "NA", "NA");
            db.insertData("DT76647", "BIO-PHYSICS","DETRIC BOER","3580034","Detrick Boer's book for bio-physics is targeted for people who are familiaridez with biology and physical science at an advanced stage","5",""+R.drawable.bio1,"sixth addition", "NA", "NA");
            db.insertData("CN76875", "Chemistry for Africa","BABECHAN FOX","3580034","Chemistry for Africa is a book targeted for high school students interested in chemistry ","4",""+R.drawable.chemisrty1,"seventh addition", "NA", "NA");
            db.insertData("GB76875", "O'Level Maths","ROBERT SEGWICK","3580034","Robert Segwick's book for algorithms is targeted for people who are familiarized with coding at an advanced stage","2",""+R.drawable.mathbook,"twelveth addition", "NA", "NA");
            db.insertData("DCX6875", "Calculus for Actors","JAMES BOND","3580034","Calculus for actors is a book written for people who are slow learners and need proper explanations and exapmles","1",""+R.drawable.maths1,"second addition", "NA", "NA");
            db.insertData("ZY76785", "Advanced Staistics","BOB NEWN","3580034","Bob Newn's book for advanced statistics covers a diverse field of statistical inference stragtegies","10",""+R.drawable.statistics,"eleventh addition", "NA", "NA");

        }
        else
        {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("logged", "logged");

            editor.apply();
        }

    }


    //SETS UP THE MENU ON THE ACTIONBAR//
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

        //noinspection SimplifiableIfStatement
        if (id == R.id.btnSpeak) {

            try {
                startActivityForResult(intent, RESULT_SPEECH);
            } catch (ActivityNotFoundException a) {
                Toast t = Toast.makeText(getApplicationContext(),
                        "Opps! Your device doesn't support Speech to Text",
                        Toast.LENGTH_SHORT);
                t.show();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //  THIS TAKES THE SPEECH AND PUTS IT IN A ARRAY LIST AND THEN DISPLAYS IT IN A TOAST FOR NOW//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                        Toast.makeText(getApplicationContext(), "Feature not supported in your deavice", Toast.LENGTH_LONG).show();
                    else
                    {
                        Log.i("voice", text.get(0));
                        Toast.makeText(activity,text.get(0), Toast.LENGTH_LONG).show();


                        if(text.get(0).equals("hello"))
                        {
                            name = text.get(0);
                            toSpeech.speak("Hello what is your name",TextToSpeech.QUEUE_FLUSH,null);
                        }
                   //     if(name.equals("hello"))
                    //    {
                     //       toSpeech.speak("Hello" + name.get(0),TextToSpeech.QUEUE_FLUSH,null);
                      //  }
                       // name = "";

                        else if(text.get(0).equals("what level is science"))
                        {
                            toSpeech.speak("It is on level 14 ",TextToSpeech.QUEUE_FLUSH,null);
                        }
                        else if(text.get(0).equals("who made you"))
                        {
                            toSpeech.speak("My creater is group 1",TextToSpeech.QUEUE_FLUSH,null);
                        }
                        else if(text.get(0).equals("how can I pass"))
                        {
                            toSpeech.speak("You have to time manage and be consistent. But most importantly you have to love what you do. hash tag goodluck",TextToSpeech.QUEUE_FLUSH,null);
                        }

                        else if(text.get(0).equals("what do you think of Kaylee"))
                        {
                            toSpeech.speak("I think she is very sexy and that she is really smart. You should kiss her",TextToSpeech.QUEUE_FLUSH,null);
                        }

                        else {
                            speachFlag = false;
                            popUpSearchResult("Book_table", "TITLE", text.get(0).toUpperCase());

                            if(speachFlag) {
                                toSpeech.speak("I dont understand that yet", TextToSpeech.QUEUE_FLUSH, null);
                                Toast.makeText(activity,text.get(0), Toast.LENGTH_LONG).show();
                            }
                            //Make this generic by reading whichever books descriptions
                            else if(text.get(0).equals("algorithms"))
                                toSpeech.speak("Robert Segwick's book for algorithms is targeted for people who are familiarized with coding at an advanced stage", TextToSpeech.QUEUE_FLUSH,null);


                            else
                                toSpeech.speak("Here's a list of books", TextToSpeech.QUEUE_FLUSH,null);

                        }

                    }

//                    Toast.makeText(this,text.get(0),Toast.LENGTH_LONG).show();
                }
                else
                {

                    //if(text == null)
                      //  toSpeech.speak("That was flames",TextToSpeech.QUEUE_FLUSH,null);
                    //else
                       toSpeech.speak("Sorry I could not hear you",TextToSpeech.QUEUE_FLUSH,null);




                }
                break;
            }
        }
    }


}
