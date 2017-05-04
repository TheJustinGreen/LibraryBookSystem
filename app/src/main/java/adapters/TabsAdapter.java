package adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by green on 09/08/2016.
 * <p>Implementation of TabsAdapter which extends PagerAdapter that represents each page as a Fragment that is persistently kept in the fragment manager as long as the user can return to the page.
 This version of the pager is best for use when there are a handful of typically more static fragments to be paged through, such as a set of tabs. The fragment of each page the user visits will be kept in memory, though its view hierarchy may be destroyed when not visible. This can result in using a significant amount of memory since fragment instances can hold on to an arbitrary amount of state. For larger sets of pages, consider FragmentStatePagerAdapter.
 When using FragmentPagerAdapter the host ViewPager must have a valid ID set.
 Subclasses only need to implement getItem(int) and getCount() to have a working adapter as I have done</p>
 */
public class TabsAdapter extends FragmentPagerAdapter
{
    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    /**
     * A constractor of a TabsAdapter object
     * @param fm
     */
    public TabsAdapter(FragmentManager fm)
    {
        super(fm);
    }

    /**
     * Adds a fragment object to the list of Fragments objects
     * @param fragment fragment to be included in the fragment adapter
     * @param title The title of a fragment view
     */
    public void addFragment(Fragment fragment, String title)
    {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    /**
     *Returns the fragment item at specified position
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position)
    {
        return mFragments.get(position);
    }

    /**
     *Return size of the list of fragment objects
     * @return
     */
    @Override
    public int getCount()
    {
        return mFragments.size();
    }

    /**
     *Returns the title of the fragment at a specified position
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position)
    {
        return mFragmentTitles.get(position);
    }
}