package com.example.cse110_lab5;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse110_lab5.database.GraphDatabase;
import com.example.cse110_lab5.database.ZooData;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.util.Pair;

import java.util.Collections;
import java.util.List;

public class PathAdapter extends RecyclerView.Adapter<PathAdapter.ViewHolder>{
    private Context context;

    private String destination;
    private GraphPath<String, ZooData.IdentifiedEdge> path;

    public PathAdapter(Context context, String destination, GraphPath<String, ZooData.IdentifiedEdge> path) {
        this.context = context;
        this.destination = destination;
        this.path = path;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.exhibit_planned, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String vertex = path.getVertexList().get(position);
        ZooData.Node node = GraphDatabase.getSingleton(context).nodeDao().get(vertex);

        if(node == null) {
            holder.exhibitName.setText(vertex);
            Log.d("Nodes", "node " + vertex + " did not exist in database");
        } else {
            holder.exhibitName.setText(node.name);
        }
    }

    @Override
    public int getItemCount() {
        return path.getLength() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView exhibitName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exhibitName = (TextView) itemView.findViewById(R.id.exhibit_planned);
        }

    }
}
