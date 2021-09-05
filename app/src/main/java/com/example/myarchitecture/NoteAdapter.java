package com.example.myarchitecture;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ListAdapter<Note_DBTable, NoteAdapter.NoteHolder> {

//    private List<Note_DBTable> note = new ArrayList<>();

    private OnItemClickListener listener;

    //public NoteAdapter(@NonNull DiffUtil.ItemCallback<Note_DBTable> diffCallback)
    //delete the constructor because we don't want it to come
    // from outside
    public NoteAdapter() {
//to define comparison logic b/w 2 list we used list adapter
        super(DIFF_CALLBACK);
    }
//we have to make it static bcz we want to pass it to the superclass
    //constructor b4 the note adapter costructor is finished,
    //without static , we couldn't be able to access it b4 note adapter is completed
// it wouldn't be available here
    private static final DiffUtil.ItemCallback<Note_DBTable>DIFF_CALLBACK
    = new DiffUtil.ItemCallback<Note_DBTable>() {
    @Override
    public boolean areItemsTheSame(@NonNull Note_DBTable oldItem, @NonNull Note_DBTable newItem) {


        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Note_DBTable oldItem, @NonNull Note_DBTable newItem) {

        //this is our comparison logic, the rest will be take care by itself

        return oldItem.getTitle().equals(newItem.getTitle())
                && oldItem.getDescription().equals(newItem.getDescription())
                && oldItem.getPriority() == newItem.getPriority();
    }
};

    @NonNull
    @Override   //view group is recycler view itself

    //getcontext of main activity is called
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override //View mai data le kr aayega ye methods through java objects nodes k through
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        Note_DBTable currentNote = getItem(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));
    }

//    @Override
//    public int getItemCount() {
//        //returns note in our array list
//        return note.size();
//
//    }//

    //list of notes set krne k liye recycler view mai ye method banaya hai takey live data update le sake
//    public void setNote(List<Note_DBTable> note) {
//        this.note = note;
//        notifyDataSetChanged(); //dont used in recyler view
//
//    }

    public Note_DBTable getNoteAt(int position) {
        return getItem(position);
        //this way we get note from the adapter to the outside
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public NoteHolder(@NonNull View itemView) {
            super(itemView); //CardView is passing
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note_DBTable note_dbTable);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
