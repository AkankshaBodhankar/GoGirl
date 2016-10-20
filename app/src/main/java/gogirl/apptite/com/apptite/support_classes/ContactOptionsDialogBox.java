package gogirl.apptite.com.apptite.support_classes;


import android.content.Context;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListAdapter;

/**
 * Created by Rohan on 13-03-2016.
 */
public class ContactOptionsDialogBox extends  ListDialogBox {

    public static ContactOptionsDialogBox newInstance(String title, Context context, AdapterView.OnItemClickListener clickListener )
    {
        ContactOptionsDialogBox customAlertDialogcontext = new ContactOptionsDialogBox();
        Bundle args = new Bundle();
        args.putString("title",title);
        customAlertDialogcontext.setArguments(args);
        customAlertDialogcontext.clickListener = clickListener;
        customAlertDialogcontext.context = context;
        return customAlertDialogcontext;
    }

    @Override
    protected ListAdapter getListAdapter() {
        return new CustomAdapter(context);
    }

    @Override
    protected AdapterView.OnItemClickListener getItemClickListener() {
        //Providing functionality to the listitems (Call and Message)
        return (AdapterView.OnItemClickListener) clickListener;
    }
}
