package ch.blinkenlights.android.vanilla;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyLibraryActivity extends PlaybackActivity {

    private ListView list;

    String[] playlist = new String[] { "Aloska", "Babulka" };

    @Override
    public void onCreate(Bundle state) {
        ThemeHelper.setTheme(this, R.style.Library);
        super.onCreate(state);

        setContentView(R.layout.my_library_content);

        list = (ListView) findViewById(R.id.my_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                playlist);

        QueryTask query = MediaUtils.buildFileQuery("/storage/sdcard1/od/" + playlist[position] + ".ogg",
                Song.FILLED_PROJECTION);
        query.mode = SongTimeline.MODE_ENQUEUE;
        PlaybackService.get(MyLibraryActivity.this).addSongs(query);

        list.setAdapter(adapter);
        list.setOnItemClickListener(listClickHandler);
    }

    private OnItemClickListener listClickHandler = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            PlaybackService.get(MyLibraryActivity.this).jumpToQueuePosition(position);
        }
    };
}
