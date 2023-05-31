import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (checkPermissions()) {
            extractData();
        } else {
            requestPermissions();
        }
    }

    private boolean checkPermissions() {
        int readCallLogPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG);
        int readContactsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        int readSMSPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        int readBrowserHistoryPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_HISTORY_BOOKMARKS);

        return readCallLogPermission == PackageManager.PERMISSION_GRANTED &&
                readContactsPermission == PackageManager.PERMISSION_GRANTED &&
                readSMSPermission == PackageManager.PERMISSION_GRANTED &&
                readBrowserHistoryPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_SMS, Manifest.permission.READ_HISTORY_BOOKMARKS},
                PERMISSION_REQUEST_CODE);
    }

    private void extractData() {
        List<CallLogModel> callLogs = DataExtractor.extractCallLogs(this);
        List<ContactModel> contacts = DataExtractor.extractContacts(this);
        List<MessageModel> messages = DataExtractor.extractMessages(this);
        List<BrowserHistoryModel> browserHistory = DataExtractor.extractBrowserHistory(this);

        // Perform any necessary operations with the extracted data (e.g., display in RecyclerView)
        recyclerViewAdapter = new RecyclerViewAdapter(callLogs, contacts, messages, browserHistory);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                extractData();
            } else {
                Toast.makeText(this, "Permissions denied. Cannot extract data.", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "Permissions denied.");
            }
        }
    }
}
