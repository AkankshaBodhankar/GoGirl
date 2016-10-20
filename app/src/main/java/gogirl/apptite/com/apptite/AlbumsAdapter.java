package gogirl.apptite.com.apptite;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Album> albumList;

    static int c;
    static  Context ctx;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);

            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

        }
    }
public AlbumsAdapter(Context ctx)
{
    this.ctx=ctx;
}

    public AlbumsAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Album album = albumList.get(position);


        // loading album cover using Glide library
        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext,album.getName(), Toast.LENGTH_SHORT).show();

                Fragment newFrag=null;
                  if(album.getName().equals("rone"))
                {
                    new Album().mymet("rone");


                }
                else if(album.getName().equals("rtwo"))
                {
                    new Album().mymet("rtwo");
                }
                else if(album.getName().equals("rthree"))
                {
                    new Album().mymet("rthree");
                }
               else if(album.getName().equals("rfour"))
                {
                    new Album().mymet("rfour");
                }/*
                else if(album.getName().equals("Dot Net"))
                {
                    Intent intent = new Intent(v.getContext(),bDotNet.class);
                    v.getContext().startActivity(intent);

                }
                else if(album.getName().equals("Java"))
                {
                    Intent intent = new Intent(v.getContext(),bJ.class);
                    v.getContext().startActivity(intent);
                }
                else if(album.getName().equals("MySQL"))
                {
                    Intent intent = new Intent(v.getContext(),bSql.class);
                    v.getContext().startActivity(intent);
                }
                else if(album.getName().equals("Apt"))
                {
                    Intent intent = new Intent(v.getContext(),Apt.class);
                    v.getContext().startActivity(intent);
                }
                else if(album.getName().equals("Ver"))
                {
                    Intent intent = new Intent(v.getContext(),bDotNet.class);
                    v.getContext().startActivity(intent);

                }
                else if(album.getName().equals("Log"))
                {
                    Intent intent = new Intent(v.getContext(),bJ.class);
                    v.getContext().startActivity(intent);
                }
                else if(album.getName().equals("Tes"))
                {
                    Intent intent = new Intent(v.getContext(),bSql.class);
                    v.getContext().startActivity(intent);
                }
                else if(album.getName().equals("wat"))
                {
                    Intent intent = new Intent(v.getContext(),wat.class);
                    v.getContext().startActivity(intent);
                }
                else if(album.getName().equals("tad"))
                {
                    Intent intent = new Intent(v.getContext(),tad.class);
                    v.getContext().startActivity(intent);

                }
                else if(album.getName().equals("t"))
                {
                    Intent intent = new Intent(v.getContext(),t.class);
                    v.getContext().startActivity(intent);
                }
                else if(album.getName().equals("si"))
                {
                    Intent intent = new Intent(v.getContext(),si.class);
                    v.getContext().startActivity(intent);
                }
                else if(album.getName().equals("pal"))
                {
                    Intent intent = new Intent(v.getContext(),pal.class);
                    v.getContext().startActivity(intent);
                }
                else if(album.getName().equals("pac"))
                {
                    Intent intent = new Intent(v.getContext(),pac.class);
                      v.getContext().startActivity(intent);

                }
                else if(album.getName().equals("p"))
                {
                       Intent intent = new Intent(v.getContext(),p.class);
                    v.getContext().startActivity(intent);
                }
                else if(album.getName().equals("bas"))
                {
                    Intent intent = new Intent(v.getContext(),bas.class);
                    v.getContext().startActivity(intent);
                }
                else if(album.getName().equals("a"))
                {
                    Intent intent = new Intent(v.getContext(),a.class);
                    v.getContext().startActivity(intent);
                }
                else if(album.getName().equals("tt"))
                {
                    Intent intent = new Intent(v.getContext(),AptTest.class);
                    v.getContext().startActivity(intent);


                }*/
            }
        });

    }




    @Override
    public int getItemCount() {
        return albumList.size();
    }
}

