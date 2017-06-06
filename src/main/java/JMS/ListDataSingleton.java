package JMS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Nekkyou on 17-5-2017.
 */
public class ListDataSingleton {
	private static ListDataSingleton Instance = null;

	public static ListDataSingleton getInstance() {
		if (Instance == null) {
			Instance = new ListDataSingleton();
		}

		return Instance;
	}

	private ObservableList<String> items = FXCollections.observableArrayList();

	public ObservableList<String> getItems() {
		return items;
	}

	private ListDataSingleton() {

	}

	public void addItem(String item) {
		items.add(item);
	}

	public void removeItem(String item) {
		items.remove(item);
	}



}
