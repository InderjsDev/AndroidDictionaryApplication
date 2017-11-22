package inducesmile.com.androiddictionaryapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private EditText filterText;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        filterText = (EditText) findViewById(R.id.editText);
        ListView itemList = (ListView) findViewById(R.id.listView);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mDrawer);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        NavigationView mNavigationView = (NavigationView) findViewById(R.id.navigationView);

        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }

        DbBackend dbBackend = new DbBackend(MainActivity.this);
        String[] terms = dbBackend.dictionaryWords();

        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, terms);

        itemList.setAdapter(listAdapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // make Toast when click
                Toast.makeText(getApplicationContext(), "Position " + position, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, DictionaryActivity.class);
                intent.putExtra("DICTIONARY_ID", position);
                startActivity(intent);
            }
        });







    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView search = (SearchView) menu.findItem(R.id.menu_search).getActionView();

       // search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        if (search != null) {
            search.setQueryHint("Type word here...");
            search.setMaxWidth(2129960); // https://stackoverflow.com/questions/18063103/searchview-in-optionsmenu-not-full-width
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    MainActivity.this.listAdapter.getFilter().filter(s);
                    return false;
                }
            });
        }



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_dic) {

            Intent i = new Intent(MainActivity.this, DictionaryAbout.class);
            startActivity(i);

            return true;
        }

        if (id == R.id.nav_about) {

            Intent i = new Intent(MainActivity.this, About.class);
            startActivity(i);

            return true;
        }

        if (id == R.id.nav_dev) {

            Intent i = new Intent(MainActivity.this, Dev.class);
            startActivity(i);

            return true;
        }

        return true;
    }
}
