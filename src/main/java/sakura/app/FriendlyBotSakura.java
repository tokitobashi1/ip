package sakura.app;

import sakura.storage.Storage;
import sakura.task.TaskList;
import sakura.ui.Ui;


/**
 * FriendlyBotSakura is a simple flower-themed task manager bot.
 */
public class FriendlyBotSakura {

    public static void main(String[] args) {
        Storage storage = new Storage("./data/SakuraStorage.txt");
        TaskList taskList = new TaskList(storage);
        Ui ui = new Ui(taskList);

        ui.start();
    }
}