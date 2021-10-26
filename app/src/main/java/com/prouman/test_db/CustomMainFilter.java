package com.prouman.test_db;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcs on 12/18/2016.
 */
public class CustomMainFilter extends Filter {
    ComplexAdapter adapter;
    List<ContactTest> filterList;

    public CustomMainFilter(ComplexAdapter adapter, List<ContactTest> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }


    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint="1";
            //STORE OUR FILTERED PLAYERS
            ArrayList<ContactTest> filteredContactVOs=new ArrayList<>();
            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getName().toUpperCase().contains(constraint))
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
        adapter.contactVOList= (List<ContactTest>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
