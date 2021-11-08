package Database;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Database {

    public interface IOnCompleteGetEntries {
        void onComplete(List<Object> entries);
    }

    public void Database() {}

    public void getEntries(String collection, IOnCompleteGetEntries event) {
        List<Object> entries = new ArrayList<>();
        event.onComplete(entries);
    }

    public boolean isInCollection(Object entry) {


        return false;
    }

}