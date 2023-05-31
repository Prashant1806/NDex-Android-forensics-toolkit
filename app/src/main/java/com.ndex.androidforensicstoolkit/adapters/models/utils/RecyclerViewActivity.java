import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<CallLogModel> callLogs;
    private List<ContactModel> contacts;
    private List<MessageModel> messages;
    private List<BrowserHistoryModel> browserHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        callLogs = new ArrayList<>();
        contacts = new ArrayList<>();
        messages = new ArrayList<>();
        browserHistory = new ArrayList<>();

        // Get the extracted data from the intent or any other source
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            callLogs = extras.getParcelableArrayList("callLogs");
            contacts = extras.getParcelableArrayList("contacts");
            messages = extras.getParcelableArrayList("messages");
            browserHistory = extras.getParcelableArrayList("browserHistory");
        }

        // Populate the RecyclerView with the extracted data
        recyclerViewAdapter = new RecyclerViewAdapter(callLogs, contacts, messages, browserHistory);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}
