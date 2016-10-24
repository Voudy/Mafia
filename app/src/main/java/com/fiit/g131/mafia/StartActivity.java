package com.fiit.g131.mafia;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.ExpandedMenuView;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Vector;

public class StartActivity extends Activity {

    ListView lv_player;
    EditText add_edt;
    Button add_btn, next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        add_btn = (Button) findViewById(R.id.add_btn);
        add_edt = (EditText) findViewById(R.id.add_edt);
        lv_player = (ListView) findViewById(R.id.lv);
        next_btn = (Button) findViewById(R.id.start_next);

        final Vector <String> players = new Vector();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, players);
        lv_player.setAdapter(adapter);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_player = add_edt.getText().toString();
                if (new_player.equals("")){
                    String noname=getResources().getString(R.string.no_name);
                    Toast toast = Toast.makeText(getApplicationContext(), noname, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
                    boolean b=true;
                    for (int i=0; i<players.size(); ++i)
                        if (players.elementAt(i).equals(new_player)) b=false;
                    if (b) {
                        players.add(new_player);
                        adapter.notifyDataSetInvalidated();
                    }else{
                        String samename=getResources().getString(R.string.same_name);
                        Toast toast = Toast.makeText(getApplicationContext(), samename, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
                add_edt.setText("");
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (players.size()<4){
                    String few_players=getResources().getString(R.string.few_players);
                    Toast toast = Toast.makeText(getApplicationContext(), few_players, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
                    Intent intent = new Intent(StartActivity.this, PlayersActivity.class);
                    final ArrayList<String> names = new ArrayList<String>();
                    for (int i=0; i<players.size(); ++i)
                        names.add(players.elementAt(i));
                    intent.putExtra("names", names);
                    startActivity(intent);
                }
            }
        });

        lv_player.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String edit = adapter.getItem(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                builder.setTitle(getResources().getString(R.string.edit_menu)).setCancelable(false).
                        setNegativeButton(getResources().getString(R.string.edit), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).setPositiveButton(getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                RelativeLayout rv = (RelativeLayout) getLayoutInflater().inflate(R.layout.dialog, null);
                AlertDialog alert = builder.create();
                alert.setView(rv);
                EditText edt = (EditText) findViewById(R.id.edit);
                edt.setText(edit);
                alert.show();
            }
        });
    }

}
