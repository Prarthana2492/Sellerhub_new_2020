package com.SevenNine.Partnercode.TabLayoutAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.SevenNine.Partnercode.Fragment.AcceptedListFragment;
import com.SevenNine.Partnercode.Fragment.DispatchedFragment;
import com.SevenNine.Partnercode.Fragment.NewOrderFragment;
import com.SevenNine.Partnercode.Fragment.NotificationList;
import com.SevenNine.Partnercode.Fragment.OrderDetailsFragment;


/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class PagerOrder extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public PagerOrder(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs

        System.out.println("llllllllllllllllllll1888"+" "+position);

        switch (position) {
           /* case 0:
                ScheduledTabFragment scheduledTabFragment=new ScheduledTabFragment();
                return scheduledTabFragment;*/

            case 0:
                NewOrderFragment tab1 = new NewOrderFragment();
                return tab1;
            case 1:
                AcceptedListFragment scheduledTabFragment=new AcceptedListFragment();
                return scheduledTabFragment;
            case 2:
                DispatchedFragment tab2 = new DispatchedFragment();
                return tab2;
            case 3:
                DispatchedFragment tab3 = new DispatchedFragment();
                return tab3;
           /* case 5:
                FarmLocationItemFragment tab5=new FarmLocationItemFragment();
                return  tab5;*/

            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}