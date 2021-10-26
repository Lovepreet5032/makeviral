package com.prouman.contactmanager;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aseemchoudhary on 09/05/18.
 */

public class ContactManagerFilter extends Filter {
    ContactManagerAdapter adapter;
    List<ContactManagerDataObject.ContactManagerListData> filterList;
    public ContactManagerFilter(List<ContactManagerDataObject.ContactManagerListData> filterList, ContactManagerAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;
    }
    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<ContactManagerDataObject.ContactManagerListData> filteredContactVOs=new ArrayList<>();
            for (int i=0;i<filterList.size();i++)
            {
                String name=filterList.get(i).getFirstName()+filterList.get(i).getLastName();
                //CHECK
                if(name.toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredContactVOs.add(filterList.get(i));
                }
            }
            results.count=filteredContactVOs.size();
            results.values=filteredContactVOs;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.mNotifcationList= (List<ContactManagerDataObject.ContactManagerListData>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
